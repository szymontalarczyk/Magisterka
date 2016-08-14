package com.example.szymon.messor;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

    public interface InterfaceDataCommunicator {
        public void updateData(String ip,int port,int flaga,float x,float y, float z, float alpha, float beta, float gamma, float speed, int id);
    }






//ManualControll id = 2;
    int id = 2 ;
    View myView;
    Spinner spinner;
    ArrayAdapter adapter;
    EditText x,y,z,alfa,beta,gamma,speed;
    Button sendbutton;

    int flaga;
    float x_send,y_send,z_send,alfa_send,beta_send,gamma_send,speed_send;



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
       sendbutton=(Button)myView.findViewById(R.id.send_button);
       Ip = getArguments().getString("ip");
        port = getArguments().getInt("port");
sendbutton.setOnClickListener(sendbuttonOnClickListener);




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



}
