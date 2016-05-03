package com.lclark.motorcycleap.RiderStatistics;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.lclark.motorcycleap.R;

/**
 * Created by student22 on 4/14/16.
 */
public class RiderStatisticsFragment extends Fragment implements SensorEventListener {
    SensorManager sensorManager;
    TextView speedometer;

    public Context mContext;
    public static final String TAG = RiderStatisticsFragment.class.getSimpleName();
    public static final String ARG_COLOR = "Color";
    public static final String ARG_INDEX = "Index";

    public static RiderStatisticsFragment newInstance(int index) {

        RiderStatisticsFragment fragment = new RiderStatisticsFragment();
        Bundle args = new Bundle();
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

        RiderStatisticsAdapter mAdapter = new RiderStatisticsAdapter(getContext());
        listView.setAdapter(mAdapter);

        return rootView;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        Sensor gravitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        Sensor rotationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);

        sensorManager.registerListener(this, rotationSensor, SensorManager.SENSOR_DELAY_FASTEST);
        sensorManager.registerListener(this, gravitySensor, SensorManager.SENSOR_DELAY_FASTEST);

        speedometer = (TextView) getActivity().findViewById(R.id.fragment_rider_stats_currentspeed_textView);





        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        speedometer.setText(String.format("%f", (event.values[0] * 60 * 60)/ 1000  ) + " km/h");
       // sensorManager.getOrientation(sensorManager.getRotationMatrix());
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
