package com.lclark.motorcycleap.RiderStatistics;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.lclark.motorcycleap.R;

import static java.lang.StrictMath.abs;
import static java.lang.StrictMath.round;


/**
 * Created by student22 on 4/14/16.
 */
public class RiderStatisticsFragment extends Fragment implements SensorEventListener {



    SensorManager sensorManager;
    TextView leanAngleTextView;
    TextView maxLeanAngleTextView;
    FabClickListener fabClickListener;

    public static final String TAG = RiderStatisticsFragment.class.getSimpleName();
    public static final String ARG_INDEX = "Index";

    public static RiderStatisticsFragment newInstance(int index) {

        RiderStatisticsFragment fragment = new RiderStatisticsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_INDEX, index);
        fragment.setArguments(args);
        return fragment;
    }



    public void setUpFAB(View rootView) {
        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.stats_fab);
        fab.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.sand));
        fab.setOnClickListener(fabClickListener);
        fab.setOnLongClickListener(fabClickListener);

    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fabClickListener = new FabClickListener();
        fabClickListener.setRidingNow( 0 );


        View rootView = inflater.inflate(R.layout.fragment_rider_stats, container, false);
        setUpFAB(rootView);
        Bundle args = getArguments();
        int index = args.getInt(ARG_INDEX);
        Log.d(TAG, "Fragment at " + index);

        rootView.setTag(TAG);
        ListView listView = (ListView) rootView.findViewById(R.id.fragment_rider_stats_listView);
        RiderStatisticsAdapter mAdapter = new RiderStatisticsAdapter(getContext());
        listView.setAdapter(mAdapter);


        return rootView;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {




        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        Sensor gravitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        sensorManager.registerListener(this, gravitySensor, SensorManager.SENSOR_DELAY_FASTEST);

        leanAngleTextView = (TextView) getActivity().findViewById(R.id.fragment_rider_stats_current_lean_angle_text_view);
        maxLeanAngleTextView = (TextView) getActivity().findViewById(R.id.fragment_rider_stats_current_max_lean_textview);


        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {


        if (event.sensor.getType() == Sensor.TYPE_GRAVITY && fabClickListener.ridingNow==0) {
            leanAngleTextView.setTextSize(20);
            maxLeanAngleTextView.setTextSize(20);
            leanAngleTextView.setText(R.string.beginRide);
            maxLeanAngleTextView.setText(R.string.AttachInst);


        }
        if (event.sensor.getType() == Sensor.TYPE_GRAVITY && fabClickListener.ridingNow==1) {

            leanAngleTextView.setTextSize(30);
            maxLeanAngleTextView.setTextSize(30);
            double x = event.values[0] * 90.0 / sensorManager.GRAVITY_EARTH;
            x = x * 100;
            x = round(x);
            x = x / 100;
            if (x > 0) {
                leanAngleTextView.setText(
                        String.format(getResources().getString(R.string.leanLeft), x));
            }
            if (x < 0) {
                leanAngleTextView.setText(
                        String.format(getResources().getString(R.string.leanRight), -x));
            }
            if (x == 0) {
                leanAngleTextView.setText(" 0° ");

            }

            if ( abs(x) > fabClickListener.ride.max_lean ) {
                fabClickListener.ride.max_lean = abs(x);
                maxLeanAngleTextView.setText("Max Lean = " + fabClickListener.ride.max_lean);

            }




        }


    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
