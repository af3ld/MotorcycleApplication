package com.lclark.motorcycleap;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by student22 on 4/14/16.
 */
public class RiderStatisticsFragment extends Fragment implements SensorEventListener {

    TextView speedometer;

    public Context mContext;
    public static final String TAG = RiderStatisticsFragment.class.getSimpleName();
    public static final String ARG_COLOR = "Color";
    public static final String ARG_INDEX = "Index";

    public static RiderStatisticsFragment newInstance(@ColorInt int color, int index) {

        RiderStatisticsFragment fragment = new RiderStatisticsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLOR, color);
        args.putInt(ARG_INDEX, index);
        fragment.setArguments(args);
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_rider_stats, container, false);
        Bundle args = getArguments();
        int index = args.getInt(ARG_INDEX);
        Log.d(TAG, "Fragment at " + index);

        rootView.setTag(TAG);
        ListView listView = (ListView) rootView.findViewById(R.id.fragment_rider_stats_listView);

        RiderStatisticsAdapter mAdapter = new RiderStatisticsAdapter(mContext);
        listView.setAdapter(mAdapter);

        return rootView;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        SensorManager sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        Sensor gravitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        sensorManager.registerListener(this, gravitySensor, sensorManager.SENSOR_DELAY_FASTEST);
        speedometer = (TextView) getActivity().findViewById(R.id.fragment_rider_stats_currentspeed_textView);


        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        speedometer.setText( Float.toString(event.values[0]) );

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
