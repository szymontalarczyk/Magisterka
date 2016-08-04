package com.example.szymon.messor;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.WritableByteChannel;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{
    FragmentManager fragmentManager = getFragmentManager();
NavigationView navigationView = null;
Toolbar toolbar = null;

     int x;
    float y;
    float z;
    float alpha;
    float beta;
    float gamma;
    float speed;
    float accel;

    byte[] data;
    byte[] id;
    byte[] dane;
    Bundle bundleMainScreen;
    Bundle bundleCrawl;
    Bundle bundleAcc;
    Bundle bundleManual;
    Bundle bundleRobotState;
    Bundle bundleSettings;

    MainScreen MainScreen;
    Crawl Crawl;
    Accelerometr Accelerometr;
    ManualControll ManualControll;
    RobotState RobotState;
    Settings_Fragment Settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        init_screens();


        MainScreen.setArguments(bundleMainScreen);
      //  Settings.setArguments(bundle);
        fragmentManager.beginTransaction().replace(R.id.content_frame, MainScreen).commit();
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.MainScreen) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, MainScreen).commit();
        } else if (id == R.id.ManualControll) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, ManualControll).commit();
        } else if (id == R.id.CrawlControll) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, Crawl).commit();
        } else if (id == R.id.Accelerometr) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, Crawl).commit();
        } else if (id == R.id.Settings) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, Settings).commit();
        } else if (id == R.id.robot_state) {

            MyClientTask myClientTask = new MyClientTask("192.168.1.112",1313);
            myClientTask.execute();


            fragmentManager.beginTransaction().replace(R.id.content_frame, RobotState).commit();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }






    void init_screens() {

        MainScreen = new MainScreen();
        Crawl = new Crawl();
        Accelerometr = new Accelerometr();
        ManualControll = new ManualControll();
        RobotState= new  RobotState();
        Settings= new Settings_Fragment();
        bundleMainScreen = new Bundle();
        bundleMainScreen= new Bundle();
        bundleCrawl= new Bundle();
        bundleAcc= new Bundle();
        bundleManual= new Bundle();
        bundleRobotState= new Bundle();
        bundleSettings= new Bundle();

    }

    public byte[] data_to_robot(int flaga,float x,float y, float z, float alpha, float beta, float gamma, float speed )
    {
        data = new  byte[32];
        id = new byte[4];
        dane = new byte[28];

        id=intToBytes(flaga);
        dane=floatTobytes(x,y,z,alpha,beta,gamma,speed);



        for (int i=0;i<data.length;i++)
        {
            if(i<4)
            {
                data[i]=id[3-i];

            }
            else
                data[i]=dane[31-i];

        }

        return data;
    }



    public void writeBuffer (ByteBuffer buffer, OutputStream stream) {
        WritableByteChannel channel = Channels.newChannel(stream);

        try {
            channel.write(buffer);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
    public byte[] intToBytes( final int i ) {
        ByteBuffer intbytes = ByteBuffer.allocate(4);
        intbytes.putInt(i);
        return intbytes.array();
    }

    public byte[] floatTobytes ( final float x,final float y,final float z,final float alpha,final float beta,final float gamma,final float speed)
    {
        ByteBuffer floatbytes = ByteBuffer.allocate(28);

        floatbytes.putFloat(speed);
        floatbytes.putFloat(gamma);
        floatbytes.putFloat(beta);
        floatbytes.putFloat(alpha);
        floatbytes.putFloat(z);
        floatbytes.putFloat(y);
        floatbytes.putFloat(x);


        return floatbytes.array();

    }



    public class MyClientTask extends AsyncTask<Void, Void,Void> {

        String dstAddress;
        int dstPort;
        String response = "";


        MyClientTask(String addr, int port) {
            dstAddress = addr;
            dstPort = port;

        }

        @Override
        protected Void doInBackground(Void... arg0) {

            Socket socket = null;
            DataOutputStream dataOutputStream = null;
            DataInputStream dataInputStream = null;




            byte[] data = data_to_robot(1,0,0,0,0,0,0,0);
            ByteBuffer buffer2=ByteBuffer.wrap(data);
            try {
                socket = new Socket(dstAddress, dstPort);
                dataOutputStream = new DataOutputStream(
                        socket.getOutputStream());
                dataInputStream = new DataInputStream(socket.getInputStream());
                writeBuffer(buffer2,dataOutputStream);

                response = dataInputStream.readUTF();

            } catch (UnknownHostException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                response = "UnknownHostException: " + e.toString();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                response = "IOException: " + e.toString();
            } finally {
                if (socket != null) {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }

                if (dataOutputStream != null) {
                    try {
                        dataOutputStream.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }

                if (dataInputStream != null) {
                    try {
                        dataInputStream.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            //   textResponse.setText(response);
            super.onPostExecute(result);
        }

    }
}





