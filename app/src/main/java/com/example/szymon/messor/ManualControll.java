package com.example.szymon.messor;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ManualControll extends Fragment implements AdapterView.OnItemSelectedListener{


    public Settings_Fragment.InterfaceDataCommunicator interfaceDataCommunicator;


//ManualControll id = 2;
static int id = 2 ;
    View myView;
    Spinner spinner;
    ArrayAdapter adapter;
    EditText x,y,z,alfa,beta,gamma,speed;
    FloatingActionButton sendbutton_manual;
static final float offset = (float) 0.1;
    int flaga;
    float x_send,y_send,z_send,alfa_send,beta_send,gamma_send,speed_send;

    Button plusx,plusy,plusz,plusalfa,plusbeta,plusgamma,plusspeed;
    Button minusx,minusy,minusz,minusalfa,minusbeta,minusgamma,minusspeed;

String Ip;
    int port;

    @Override
    public void onAttach (Activity activity)
    {
        super.onAttach(activity);
        try{
            interfaceDataCommunicator = (Settings_Fragment.InterfaceDataCommunicator) activity;
        }
        catch (ClassCastException e) {
            throw new ClassCastException(activity.toString());}

    }




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.manualcontroll, container, false);
        spinner = (Spinner)myView.findViewById(R.id.spinner);
adapter = ArrayAdapter.createFromResource(this.getActivity(),R.array.lista_komend_sterowanie_manualne,android.R.layout.simple_list_item_multiple_choice);
       spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        x=(EditText)myView.findViewById(R.id.setX);
        y=(EditText)myView.findViewById(R.id.setY);
        z=(EditText)myView.findViewById(R.id.setZ);
        alfa=(EditText)myView.findViewById(R.id.setAlfa);
        beta=(EditText)myView.findViewById(R.id.setBeta);
        gamma=(EditText)myView.findViewById(R.id.setGamma);
        speed=(EditText)myView.findViewById(R.id.setSpeed);

        plusx=(Button) myView.findViewById(R.id.plusX);
        plusy=(Button)myView.findViewById(R.id.plusY);
        plusz=(Button)myView.findViewById(R.id.plusZ);
        plusalfa=(Button)myView.findViewById(R.id.plusAlfa);
        plusbeta=(Button)myView.findViewById(R.id.plusBeta);
        plusgamma=(Button)myView.findViewById(R.id.plusGamma);
        plusspeed=(Button)myView.findViewById(R.id.plusSpeed);


        minusx=(Button) myView.findViewById(R.id.minusX);
        minusy=(Button)myView.findViewById(R.id.minusY);
        minusz=(Button)myView.findViewById(R.id.minusZ);
        minusalfa=(Button)myView.findViewById(R.id.minusAlfa);
        minusbeta=(Button)myView.findViewById(R.id.minusBeta);
        minusgamma=(Button)myView.findViewById(R.id.minusGamma);
        minusspeed=(Button)myView.findViewById(R.id.minusSpeed);

        sendbutton_manual=(FloatingActionButton) myView.findViewById(R.id.send_button);
       Ip = getArguments().getString("ip");
        port = getArguments().getInt("port");
        sendbutton_manual.setOnClickListener(sendbuttonOnClickListener);

plusx.setOnClickListener(buttons_listener);
minusx.setOnClickListener(buttons_listener);
        plusy.setOnClickListener(buttons_listener);
        minusy.setOnClickListener(buttons_listener);
        plusz.setOnClickListener(buttons_listener);
        minusz.setOnClickListener(buttons_listener);
        plusalfa.setOnClickListener(buttons_listener);
        minusalfa.setOnClickListener(buttons_listener);
        plusbeta.setOnClickListener(buttons_listener);
        minusbeta.setOnClickListener(buttons_listener);
        plusgamma.setOnClickListener(buttons_listener);
        minusgamma.setOnClickListener(buttons_listener);
        plusspeed.setOnClickListener(buttons_listener);
        minusspeed.setOnClickListener(buttons_listener);
        return myView;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        TextView selected_option = (TextView) view;
        Toast.makeText(this.getActivity(),"Wybrano: "+selected_option.getText(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    View.OnClickListener sendbuttonOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View arg0) {



            flaga = spinner.getSelectedItemPosition()+1;
            x_send=Float.parseFloat(x.getText().toString());
            y_send=Float.parseFloat(y.getText().toString());
            z_send=Float.parseFloat(z.getText().toString());
            alfa_send=Float.parseFloat(alfa.getText().toString());
            beta_send=Float.parseFloat(beta.getText().toString());
            gamma_send=Float.parseFloat(gamma.getText().toString());
           speed_send=Float.parseFloat(speed.getText().toString());



            interfaceDataCommunicator.updateData(Ip, port, flaga, x_send, y_send, z_send, alfa_send, beta_send, gamma_send, speed_send, id);


        }
    };





View.OnClickListener buttons_listener = new View.OnClickListener(){

    public void onClick(View view)
    {

        switch(view.getId()){
            case R.id.plusX:
                x_send=x_send + offset;
                break;
            case R.id.minusX:
                x_send= x_send-offset;
                break;
            case R.id.plusY:
                y_send= y_send+offset;
                break;
            case R.id.minusY:
                y_send=y_send-offset;
                break;
            case R.id.plusZ:
                z_send= z_send+offset;
                break;
            case R.id.minusZ:
                z_send=z_send-offset;
                break;
            case R.id.plusAlfa:
                alfa_send= alfa_send+offset;
                break;
            case R.id.minusAlfa:
                alfa_send=alfa_send-offset;
                break;
            case R.id.plusBeta:
                beta_send= beta_send+offset;
                break;
            case R.id.minusBeta:
                beta_send=beta_send-offset;
                break;
            case R.id.plusGamma:
                gamma_send= gamma_send+offset;
                break;
            case R.id.minusGamma:
                gamma_send=gamma_send-offset;
                break;
            case R.id.plusSpeed:
                speed_send= speed_send+offset;
                break;
            case R.id.minusSpeed:
                speed_send=speed_send-offset;
                break;


        }
        x.setText(Float.toString(x_send));
        y.setText(Float.toString(y_send));
        z.setText(Float.toString(z_send));
        alfa.setText(Float.toString(alfa_send));
        beta.setText(Float.toString(beta_send));
        gamma.setText(Float.toString(gamma_send));
        speed.setText(Float.toString(speed_send));

    }

};




}
