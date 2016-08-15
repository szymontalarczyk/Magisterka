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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Accelerometr extends Fragment implements SensorEventListener, AdapterView.OnItemSelectedListener{


    public Settings_Fragment.InterfaceDataCommunicator interfaceDataCommunicator;

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public interface InterfaceDataCommunicator {
        public void updateData(String ip,int port,int flaga,float x,float y, float z, float alpha, float beta, float gamma, float speed, int id);
    }








    //Accelerometr id = 3;
    int id = 3 ;
    String Ip;
    int port;





    View myView;
TextView xa,ya,za,axisX,axisY,axisZ;
    private SensorManager mSensorManager;
    private Sensor mAccelerometr;
    float gravity[]=new float[3];
    float linear_acceleration[]=new float[3];
    Spinner spinner_acc;
    ArrayAdapter adapter_acc;
    Button sendbutton_acc;

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


        spinner_acc.setOnItemSelectedListener(this);
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


        sendbutton_acc=(Button)myView.findViewById(R.id.sendbutton_acc);





        return myView;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {


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



        xa.setText(Float.toString(x));
        ya.setText(Float.toString(y));
        za.setText(Float.toString(z));

            axisX.setText(Float.toString(linear_acceleration[0]));
            axisY.setText(Float.toString(linear_acceleration[1]));
            axisZ.setText(Float.toString(linear_acceleration[2]));





    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }



}
