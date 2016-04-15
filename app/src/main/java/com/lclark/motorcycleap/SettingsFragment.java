package com.lclark.motorcycleap;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.android.gms.maps.MapView;

/**
 * Created by student22 on 4/14/16.
 */
public class SettingsFragment extends Fragment implements View.OnClickListener {

    EditText make;
    EditText model;
    EditText tires;
    EditText frontPsi;
    EditText backPsi;

    public static final String TAG = SettingsFragment.class.getSimpleName();
    public static final String ARG_COLOR = "Color";
    public static final String ARG_INDEX = "Index";

    public static SettingsFragment newInstance(@ColorInt int color, int index) {

        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLOR, color);
        args.putInt(ARG_INDEX, index);
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);
        Bundle args = getArguments();
        int index = args.getInt(ARG_INDEX);
        Log.d(TAG, "Fragment at " + index);
        return rootView;

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onActivityCreated( Bundle savedInstanceState) {

        make = (EditText) getActivity().findViewById(R.id.fragment_settings_make_edittext);
        model = (EditText) getActivity().findViewById(R.id.fragment_settings_model_edittext);
        tires = (EditText) getActivity().findViewById(R.id.fragment_settings_tires_edittext);
        frontPsi = (EditText) getActivity().findViewById(R.id.fragment_settings_psi_front_edittext);
        backPsi = (EditText) getActivity().findViewById(R.id.fragment_settings_psi_back_edittext);

        make.setOnClickListener(this);
        model.setOnClickListener(this);
        tires.setOnClickListener(this);
        frontPsi.setOnClickListener(this);
        backPsi.setOnClickListener(this);

        super.onActivityCreated(savedInstanceState);
    }
}
