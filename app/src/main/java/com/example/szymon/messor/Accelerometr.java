package com.example.szymon.messor;

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
import android.widget.TextView;

import org.w3c.dom.Text;

public class Accelerometr extends Fragment implements SensorEventListener{

    View myView;
TextView xa,ya,za,axisX,axisY,axisZ;
    private SensorManager mSensorManager;
    private SensorManager mSensorManager2;
    private Sensor mAccelerometr;
    private Sensor mOrientation;
    float g1,g2,g3 = 0;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.accelerometr, container, false);

        mSensorManager=(SensorManager)getActivity().getSystemService(Context.SENSOR_SERVICE);
        mSensorManager2=(SensorManager)getActivity().getSystemService(Context.SENSOR_SERVICE);

        mAccelerometr = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        mSensorManager.registerListener(this, mAccelerometr,
                SensorManager.SENSOR_DELAY_NORMAL);


    xa=(TextView)myView.findViewById(R.id.x_acc);
        ya=(TextView)myView.findViewById(R.id.y_acc);
        za=(TextView)myView.findViewById(R.id.z_acc);
        axisX=(TextView)myView.findViewById(R.id.axisX);
        axisY=(TextView)myView.findViewById(R.id.axisY);
        axisZ=(TextView)myView.findViewById(R.id.axisZ);


        return myView;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {


     


            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];

        g1=(float)(0.9*g1+0.1*x);
        g2=(float)(0.9*g2+0.1*y);
        g3=(float)(0.9*g3+0.1*z);


        float x_val = x-g1;
        float y_val = y-g2;
        float z_val = z-g3;
        xa.setText(Float.toString(x));
        ya.setText(Float.toString(y));
        za.setText(Float.toString(z));

            axisX.setText(Float.toString(x_val));
            axisY.setText(Float.toString(y_val));
            axisZ.setText(Float.toString(z_val));



    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }



}
