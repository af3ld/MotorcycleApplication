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

import static java.lang.StrictMath.abs;
import static java.lang.StrictMath.round;


/**
 * Created by student22 on 4/14/16.
 */
public class RiderStatisticsFragment extends Fragment implements SensorEventListener {
    SensorManager sensorManager;
    TextView leanAngleTextView;
    TextView maxLeanAngleTextView;

double maxLean;
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
maxLean = 0;

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

        if (event.sensor.getType() == Sensor.TYPE_GRAVITY) {
            double x = event.values[0] * 90.0 / sensorManager.GRAVITY_EARTH;
            x = x * 100;
            x = round(x);
            x = x / 100;
            if (x > 0) {
                leanAngleTextView.setText("← " + x + "°");
            }
            if (x < 0) {
                leanAngleTextView.setText(-x + "°" + " →");
            }
            if (x == 0) {
                leanAngleTextView.setText(" 0° ");

            }

            if ( abs(x) > maxLean ) {
                maxLean = abs(x);

                maxLeanAngleTextView.setText("Max Lean = " + maxLean);

            }




        }


    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
