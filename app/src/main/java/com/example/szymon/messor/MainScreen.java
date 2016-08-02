package com.example.szymon.messor;


        import android.os.Bundle;
        import android.support.annotation.Nullable;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;

/**
 * Created by pawel on 21.04.16.
 */
public class MainScreen extends android.app.Fragment{

    View myView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.mainscreen, container, false);
        return myView;
    }
}
