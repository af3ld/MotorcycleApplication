package com.lclark.motorcycleap;

import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by student22 on 4/14/16.
 */
public class RiderStatisticsFragment extends Fragment {
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
        return rootView;

    }

}
