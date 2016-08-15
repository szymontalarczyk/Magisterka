package com.example.szymon.messor;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
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
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class Accelerometr extends Fragment implements SensorEventListener, AdapterView.OnItemSelectedListener{


    public Settings_Fragment.InterfaceDataCommunicator interfaceDataCommunicator;



    View myView;
    TextView xa,ya,za,axisX,axisY,axisZ;
    private SensorManager mSensorManager;
    private Sensor mAccelerometr;
    float gravity[]=new float[3];
    float linear_acceleration[]=new float[3];
    Spinner spinner_acc,spinner_controll_mode;
    ArrayAdapter adapter_acc,adapter_mode;

    EditText speed_acc;
FloatingActionButton sendbutton_acc;

    int flaga;
    float x_send,y_send,z_send,alfa_send,beta_send,gamma_send,speed_send;
    //Accelerometr id = 3;
    int id = 3 ;
    String Ip;
    int port;
    Switch switch_auto,switch_stop;


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
        myView = inflater.inflate(R.layout.accelerometr, container, false);

        spinner_acc = (Spinner)myView.findViewById(R.id.spinner_acc);
        adapter_acc = ArrayAdapter.createFromResource(this.getActivity(),R.array.lista_komend_sterowanie_manualne,android.R.layout.simple_list_item_multiple_choice);
        spinner_acc.setAdapter(adapter_acc);

        spinner_controll_mode=(Spinner)myView.findViewById(R.id.spinner_controll_mode);
        adapter_mode = ArrayAdapter.createFromResource(this.getActivity(),R.array.tryby_sterowania,android.R.layout.simple_dropdown_item_1line);
        spinner_controll_mode.setAdapter(adapter_mode);

        spinner_acc.setOnItemSelectedListener(this);

        spinner_controll_mode.setOnItemSelectedListener(control_mode_listener);

        mSensorManager=(SensorManager)getActivity().getSystemService(Context.SENSOR_SERVICE);

        mAccelerometr = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        mSensorManager.registerListener(this, mAccelerometr,
                SensorManager.SENSOR_DELAY_NORMAL);

        xa=(TextView)myView.findViewById(R.id.x_acc);
        ya=(TextView)myView.findViewById(R.id.y_acc);
        za=(TextView)myView.findViewById(R.id.z_acc);
        axisX=(TextView)myView.findViewById(R.id.axisX);
        axisY=(TextView)myView.findViewById(R.id.axisY);
        axisZ=(TextView)myView.findViewById(R.id.axisZ);
        switch_auto=(Switch)myView.findViewById(R.id.switch_auto);
        switch_stop=(Switch)myView.findViewById(R.id.stop_switch);
        speed_acc=(EditText)myView.findViewById(R.id.speed_acc);
        sendbutton_acc=(FloatingActionButton)myView.findViewById(R.id.sendbutton_acc1);

        sendbutton_acc.setOnClickListener(sendbuttonAccOnClickListener);
        Ip = getArguments().getString("ip");
        port = getArguments().getInt("port");



        return myView;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(switch_stop.isChecked()==false) {

        final float alpha = (float)0.8;


            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];



    gravity[0] = alpha * gravity[0] + (1 - alpha) * x;
    gravity[1] = alpha * gravity[1] + (1 - alpha) * y;
    gravity[2] = alpha * gravity[2] + (1 - alpha) * z;


    linear_acceleration[0] = x - gravity[0];
    linear_acceleration[1] = y - gravity[1];
    linear_acceleration[2] = z - gravity[2];



            x= (float) MainActivity.round(x,2);
            y= (float) MainActivity.round(y,2);
            z= (float) MainActivity.round(z,2);
            linear_acceleration[0]= (float) MainActivity.round(linear_acceleration[0],2);
            linear_acceleration[1]= (float) MainActivity.round(linear_acceleration[1],2);
            linear_acceleration[2]= (float) MainActivity.round(linear_acceleration[2],2);




    xa.setText(Float.toString(x));
    ya.setText(Float.toString(y));
    za.setText(Float.toString(z));




    axisX.setText(Float.toString(linear_acceleration[0]));
    axisY.setText(Float.toString(linear_acceleration[1]));
    axisZ.setText(Float.toString(linear_acceleration[2]));
}




    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


    TextView selected_option = (TextView) view;
    Toast.makeText(this.getActivity(), "Wybrano: " + selected_option.getText(), Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }



    View.OnClickListener sendbuttonAccOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View arg0) {


            flaga = spinner_acc.getSelectedItemPosition()+1;
            if(spinner_controll_mode.getSelectedItemPosition()==0) {
                x_send = Float.parseFloat(axisX.getText().toString());
                y_send = Float.parseFloat(axisY.getText().toString());
                z_send = Float.parseFloat(axisZ.getText().toString());
                alfa_send = 0;
                beta_send = 0;
                gamma_send = 0;
                speed_send = Float.parseFloat(speed_acc.getText().toString());
            }

            if(spinner_controll_mode.getSelectedItemPosition()==1) {
                x_send = 0;
                y_send = 0;
                z_send = 0;
                alfa_send = Float.parseFloat(axisX.getText().toString());
                beta_send =Float.parseFloat(axisY.getText().toString());
                gamma_send =Float.parseFloat(axisZ.getText().toString());
                speed_send = Float.parseFloat(speed_acc.getText().toString());
            }





            interfaceDataCommunicator.updateData(Ip, port, flaga, x_send, y_send, z_send, alfa_send, beta_send, gamma_send, speed_send, id);


        }
    };

    public AdapterView.OnItemSelectedListener control_mode_listener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            TextView selected_option = (TextView) view;
            Toast.makeText(getActivity(), "Wybrano: " + selected_option.getText(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };

}
