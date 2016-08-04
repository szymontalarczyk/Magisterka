package com.example.szymon.messor;


        import android.net.wifi.WifiManager;
        import android.os.Bundle;
        import android.support.annotation.Nullable;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.TextView;
        import android.widget.Toast;

        import org.w3c.dom.Text;

        import java.net.InetAddress;
        import java.net.NetworkInterface;
        import java.net.ServerSocket;
        import java.net.SocketException;
        import java.util.Enumeration;

/**
 * Created by pawel on 21.04.16.
 */
public class MainScreen extends android.app.Fragment{

    View myView;
TextView ip;
    TextView ipsign;
    TextView iks;
Button buttonConnect;
EditText editTextAddress;
EditText X_send;
int data;
    String wyswietl;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.mainscreen, container, false);





        editTextAddress= (EditText)myView.findViewById(R.id.ip_connect);
        ip = (TextView) myView.findViewById(R.id.showIP);
       // X_send = (EditText)myView.findViewById(R.id.sendx);
        buttonConnect= (Button)myView.findViewById(R.id.connect_to_robot);
        buttonConnect.setOnClickListener(buttonConnectOnClickListener);
//iks =(TextView)myView.findViewById(R.id.wpisz_x) ;
ipsign=(TextView)myView.findViewById(R.id.IPPP);


ip.setText(getIpAddress());

        return myView;
    }

    private String getIpAddress() {
        String ip = "";
        try {
            Enumeration<NetworkInterface> enumNetworkInterfaces = NetworkInterface
                    .getNetworkInterfaces();
            while (enumNetworkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = enumNetworkInterfaces
                        .nextElement();
                Enumeration<InetAddress> enumInetAddress = networkInterface
                        .getInetAddresses();
                while (enumInetAddress.hasMoreElements()) {
                    InetAddress inetAddress = enumInetAddress.nextElement();

                    if (inetAddress.isSiteLocalAddress()) {
                        ip =inetAddress.getHostAddress();
                    }

                }

            }

        } catch (SocketException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            ip += "Something Wrong! " + e.toString() + "\n";
        }

        return ip;
    }


    View.OnClickListener buttonConnectOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View arg0) {
//data = Integer.parseInt(X_send.getText().toString());
    //        wyswietl =getArguments().getString("key");
    //        iks.setText(wyswietl);
     //       ipsign.setText(String.valueOf(getArguments().getInt("key1")));
        }
    };
}

