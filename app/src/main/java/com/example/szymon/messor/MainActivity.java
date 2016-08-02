package com.example.szymon.messor;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,Settings_Fragment.SettingInterface{
    FragmentManager fragmentManager = getFragmentManager();
NavigationView navigationView = null;
Toolbar toolbar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager.beginTransaction().replace(R.id.content_frame, new MainScreen()).commit();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent intent;

        if (id == R.id.MainScreen) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new MainScreen()).commit();
        } else if (id == R.id.ManualControll) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new ManualControll()).commit();
        } else if (id == R.id.CrawlControll) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new Crawl()).commit();
        } else if (id == R.id.Accelerometr) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new Accelerometr()).commit();
        } else if (id == R.id.Settings) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new Settings_Fragment()).commit();

        } else if (id == R.id.robot_state) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new RobotState()).commit();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void send(int x) {

    }
}
