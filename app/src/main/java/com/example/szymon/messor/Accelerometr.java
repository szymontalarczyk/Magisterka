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

/**
 * Created by pawel on 21.04.16.
 */
public class Accelerometr extends Fragment implements SensorEventListener{

    View myView;
TextView xa,ya,za;
    private SensorManager mSensorManager;
    private Sensor mAccelerometr;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.accelerometr, container, false);

        mSensorManager=(SensorManager)getActivity().getSystemService(Context.SENSOR_SERVICE);

        mAccelerometr = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(this, mAccelerometr,
                SensorManager.SENSOR_DELAY_NORMAL);
    xa=(TextView)myView.findViewById(R.id.x_acc);
        ya=(TextView)myView.findViewById(R.id.y_acc);
        za=(TextView)myView.findViewById(R.id.z_acc);



        return myView;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float x = sensorEvent.values[0];
        float y = sensorEvent.values[1];
        float z = sensorEvent.values[2];

        xa.setText(Float.toString(x));
        za.setText(Float.toString(z));
        ya.setText(Float.toString(y));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }



}
