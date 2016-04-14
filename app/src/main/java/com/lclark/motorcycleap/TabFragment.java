package com.lclark.motorcycleap;

import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by alexfeldman on 4/13/16.
 */
public class TabFragment extends Fragment {
    public static final String TAG = TabFragment.class.getSimpleName();
    public static final String ARG_COLOR = "Color";
    public static final String ARG_INDEX = "Index";


    public static TabFragment newInstance(@ColorInt int color, int index) {
        TabFragment fragment = new TabFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLOR, color);
        args.putInt(ARG_INDEX, index);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tab, container, false);

        Bundle args = getArguments();

        int index = args.getInt(ARG_INDEX);
        Log.d(TAG, "Fragment at " + index);
        return rootView;
    }
}
