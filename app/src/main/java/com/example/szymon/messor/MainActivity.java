package com.example.szymon.messor;

import android.app.Activity;
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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.WritableByteChannel;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,Settings_Fragment.InterfaceDataCommunicator{
    FragmentManager fragmentManager = getFragmentManager();
NavigationView navigationView = null;
Toolbar toolbar = null;

    String dstAddress;
    int dstPort;
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

    int dstport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        init();
        dstAddress="192.168.1.107";
        dstport=2426;
        MainScreen = new MainScreen();
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
            fragmentManager.beginTransaction().replace(R.id.content_frame, new MainScreen()).commit();
        } else if (id == R.id.ManualControll) {
            ManualControll = new ManualControll();
            bundleManual.putString("ip",dstAddress);
            bundleManual.putInt("port",dstPort);
            ManualControll.setArguments(bundleManual);
            fragmentManager.beginTransaction().replace(R.id.content_frame, ManualControll).commit();
        } else if (id == R.id.CrawlControll) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new Crawl()).commit();
        } else if (id == R.id.Accelerometr) {

            Accelerometr = new Accelerometr();
            bundleAcc.putString("ip",dstAddress);
            bundleAcc.putInt("port",dstPort);
            Accelerometr.setArguments(bundleAcc);

            fragmentManager.beginTransaction().replace(R.id.content_frame,Accelerometr).commit();

        } else if (id == R.id.Settings) {
            Settings= new Settings_Fragment();
            fragmentManager.beginTransaction().replace(R.id.content_frame, Settings).commit();



        } else if (id == R.id.robot_state) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new RobotState()).commit();

        }

        else if (id == R.id.CloseApp) {
            System.exit(0);

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }






    void init() {


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





        MyClientTask(String addr, int port,byte[] data) {
            dstAddress = addr;
            dstPort = port;

        }



        @Override
        protected Void doInBackground(Void... arg0) {

            Socket socket = null;
            DataOutputStream dataOutputStream = null;
            DataInputStream dataInputStream = null;
            ByteBuffer buffer2=ByteBuffer.wrap(data);
            try {
                socket = new Socket(dstAddress, dstPort);
                dataOutputStream = new DataOutputStream(
                        socket.getOutputStream());
                dataInputStream = new DataInputStream(socket.getInputStream());
                writeBuffer(buffer2,dataOutputStream);


            } catch (UnknownHostException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
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
            super.onPostExecute(result);
        }

    }



    @Override
    public void updateData(String ip,int port,int flaga,float x,float y, float z, float alpha, float beta, float gamma, float speed, int id) {

        dstAddress=ip;
        dstport=port;


        //choice ==1 settings data
        if (id ==1) {

            data = data_to_robot(flaga, x, y, z, alpha, beta, gamma, speed);
            MyClientTask myClientTask = new MyClientTask(dstAddress,dstport,data);
            myClientTask.execute();

        }
        //id==2 manualcontroll

       if (id ==2) {

            data = data_to_robot(flaga, x, y, z, alpha, beta, gamma, speed);
           MyClientTask myClientTask = new MyClientTask(dstAddress,dstport,data);
           myClientTask.execute();

        }

        //id==3 Accelerometr
        if (id==3)
        {
            data = data_to_robot(flaga, x, y, z, alpha, beta, gamma, speed);
            MyClientTask myClientTask = new MyClientTask(dstAddress,dstport,data);
            myClientTask.execute();

        }


    }
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

}





