package com.lclark.motorcycleap;


import android.app.ActionBar;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;

import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;


public class MainActivity extends FragmentActivity implements OnMapReadyCallback{

    public static final String API_KEY = "AIzaSyC2WPTne8JjQTUEmW5ck9ymeiZFJ3LQjL0";
    public static final String API_KEY_TITLE = "Android_Maps_key_1";
    public static final String TAG = MainActivity.class.getSimpleName();
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);


        ViewPager viewPager = (ViewPager) findViewById(R.id.activity_main_viewpager);

        // SENSOR LOGIC: RYAN
        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor gravitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        sensorManager.registerListener(new LeanAngleCalculator(), gravitySensor, sensorManager.SENSOR_DELAY_FASTEST);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Start recording ride
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }
}
