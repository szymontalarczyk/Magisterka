package com.example.szymon.messor;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;
public class Settings_Fragment extends android.app.Fragment {

    public interface SettingInterface
    {
public void send (int x);

    }




    Button buttonConnect, buttonClear;

    View myView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.settings_fragment, container, false);
TextView msg = (TextView) myView.findViewById(R.id.pokaz_tekst);
msg.setText("sdsddsds");
        return myView;

    }




}