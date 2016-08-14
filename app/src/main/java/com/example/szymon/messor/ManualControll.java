package com.example.szymon.messor;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by pawel on 21.04.16.
 */
public class ManualControll extends Fragment implements AdapterView.OnItemSelectedListener{

    View myView;
    Spinner spinner;
    ArrayAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.manualcontroll, container, false);
        spinner = (Spinner)myView.findViewById(R.id.spinner);
adapter = ArrayAdapter.createFromResource(this.getActivity(),R.array.lista_komend_sterowanie_manualne,android.R.layout.simple_list_item_multiple_choice);
       spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
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
}
