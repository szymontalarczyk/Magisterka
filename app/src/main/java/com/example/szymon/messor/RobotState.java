package com.example.szymon.messor;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class RobotState extends android.app.Fragment implements AdapterView.OnItemSelectedListener {


    public Settings_Fragment.InterfaceDataCommunicator interfaceDataCommunicator;


    //RobotState id = 4;
    static int id = 4;
    View myView;
    Spinner spinner_rs;
    ArrayAdapter adapter;
    FloatingActionButton sendbutton_rs;
    FloatingActionButton emergency_rs;
    TextView rs_response,dataresponse0,dataresponse1,dataresponse2,dataresponse3,dataresponse4,dataresponse5,dataresponse6,dataresponse7;
    String response;
    Switch autoswitch_robotstate;
    int flaga;
    float x_send,y_send,z_send,alfa_send,beta_send,gamma_send,speed_send;
    float response_1,response_2,response_3,response_4,response_5,response_6,response_0,response_7;
    String Ip;
    int port;



    Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable() {

        @Override
        public void run() {
            flaga = spinner_rs.getSelectedItemPosition()+10;
            response="odebrano  "+response_0 + "  " + response_1+ "  " + response_2+ "  " + response_3+ "  " + response_4+ "  " + response_5+ "  " + response_6+"  " + response_7;
            set_response();
            rs_response.setText(response);





            interfaceDataCommunicator.updateData(Ip, port, flaga, x_send, y_send, z_send, alfa_send, beta_send, gamma_send, speed_send, id);

            timerHandler.postDelayed(this, 1);

        }
    };




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
        myView = inflater.inflate(R.layout.robotstate, container, false);
        spinner_rs = (Spinner)myView.findViewById(R.id.spinner_rc);
        adapter = ArrayAdapter.createFromResource(this.getActivity(),R.array.lista_komend_odczyt_stanu_robota,android.R.layout.simple_list_item_multiple_choice);
        spinner_rs.setAdapter(adapter);
        spinner_rs.setOnItemSelectedListener(this);
        autoswitch_robotstate=(Switch)myView.findViewById(R.id.switch_robotstate);

        autoswitch_robotstate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

               if(isChecked==true)
               {
                 timerHandler.postDelayed(timerRunnable,0);

               }
                else
               {
                   timerHandler.removeCallbacks(timerRunnable);

               }



            }
        });



       rs_response=(TextView)myView.findViewById(R.id.response_robotstate);
        dataresponse0=(TextView)myView.findViewById(R.id.dataview0);
        dataresponse1=(TextView)myView.findViewById(R.id.dataview1);
        dataresponse2=(TextView)myView.findViewById(R.id.dataview2);
        dataresponse3=(TextView)myView.findViewById(R.id.dataview3);
        dataresponse4=(TextView)myView.findViewById(R.id.dataview4);
        dataresponse5=(TextView)myView.findViewById(R.id.dataview5);
        dataresponse6=(TextView)myView.findViewById(R.id.dataview6);
        dataresponse7=(TextView)myView.findViewById(R.id.dataview7);

      sendbutton_rs=(FloatingActionButton) myView.findViewById(R.id.send_button_robotstate);
        emergency_rs=(FloatingActionButton) myView.findViewById(R.id.emergency_STOP_robotstate);

        Ip = getArguments().getString("ip");
        port = getArguments().getInt("port");
        sendbutton_rs.setOnClickListener(sendbuttonOnClickListener);
        emergency_rs.setOnClickListener(emergency_listener);







        return myView;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        TextView selected_option = (TextView) view;
        Toast.makeText(this.getActivity(),"Wybrano: "+selected_option.getText(),Toast.LENGTH_SHORT).show();
        set_response_zero();

        //interfaceDataCommunicator.updateData(Ip, port, flaga, x_send, y_send, z_send, alfa_send, beta_send, gamma_send, speed_send, id);

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    View.OnClickListener sendbuttonOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View arg0) {



            flaga = spinner_rs.getSelectedItemPosition()+10;


            response="odebrano  "+response_0 + "  " + response_1+ "  " + response_2+ "  " + response_3+ "  " + response_4+ "  " + response_5+ "  " + response_6+"  " + response_7;



            interfaceDataCommunicator.updateData(Ip, port, flaga, x_send, y_send, z_send, alfa_send, beta_send, gamma_send, speed_send, id);
set_response();


            rs_response.setText(response);


        }
    };


void set_response()
{

    dataresponse0.setText(String.valueOf(response_0));
    dataresponse1.setText(String.valueOf(response_1));
    dataresponse2.setText(String.valueOf(response_2));
    dataresponse3.setText(String.valueOf(response_3));
    dataresponse4.setText(String.valueOf(response_4));
    dataresponse5.setText(String.valueOf(response_5));
    dataresponse6.setText(String.valueOf(response_6));
    dataresponse7.setText(String.valueOf(response_7));

}
static float zero = 0;
void set_response_zero()
{

    dataresponse0.setText(String.valueOf(zero));
    dataresponse1.setText(String.valueOf(zero));
    dataresponse2.setText(String.valueOf(zero));
    dataresponse3.setText(String.valueOf(zero));
    dataresponse4.setText(String.valueOf(zero));
    dataresponse5.setText(String.valueOf(zero));
    dataresponse6.setText(String.valueOf(zero));
    dataresponse7.setText(String.valueOf(zero));

}


    View.OnClickListener emergency_listener = new View.OnClickListener(){

        public void onClick(View view)
        {

            interfaceDataCommunicator.updateData(Ip, port, 1, 0, 0, 0, 0, 0, 0, (float)0.5, id);

        }

    };


    public void setResponse(String response) {
        this.response = response;
    }

    public void sendvalues(float response_data0, float response_data1, float response_data2, float response_data3, float response_data4, float response_data5, float response_data6,float response_data7) {

        response_0=response_data0;
        response_1=response_data1;
        response_2=response_data2;
        response_3=response_data3;
        response_4=response_data4;
        response_5=response_data5;
        response_6=response_data6;
        response_7=response_data7;

    }





}
