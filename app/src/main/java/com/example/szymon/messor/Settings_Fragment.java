package com.example.szymon.messor;

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
public class Settings_Fragment extends android.app.Fragment {




    TextView textResponse;
    EditText editTextAddress, editTextPort;
    Button buttonConnect, buttonClear;
   

    View myView;

    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.settings_fragment, container, false);
TextView msg = (TextView) myView.findViewById(R.id.pokaz_tekst);



        editTextAddress = (EditText) myView.findViewById(R.id.address);
        editTextPort = (EditText) myView.findViewById(R.id.port);
        buttonConnect = (Button) myView.findViewById(R.id.connect);
        buttonClear = (Button) myView.findViewById(R.id.clear);
        textResponse = (TextView) myView.findViewById(R.id.response);
        View.OnClickListener buttonClearOnClickListener;


         buttonConnect.setOnClickListener(buttonConnectOnClickListener);


        buttonClear.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                textResponse.setText("");
                editTextAddress.setText("");
                editTextPort.setText("");
            }
        });








        return myView;

    }

    View.OnClickListener buttonConnectOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View arg0) {


         //   MainActivity.MyClientTask myClientTask = new MainActivity.MyClientTask(editTextAddress
           //         .getText().toString(), Integer.parseInt(editTextPort
           //         .getText().toString()));
         //   myClientTask.execute();



        }
    };


};


