package com.lclark.motorcycleap;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.lclark.motorcycleap.RiderStatistics.Rides;

import java.io.IOException;

/**
 * Created by student22 on 4/14/16.
 */
public class SettingsFragment extends Fragment implements View.OnClickListener {

    EditText name;
    EditText make;
    EditText model;
    EditText tires;
    EditText frontPsi;
    EditText backPsi;
    Button save;
    Button clear;
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
        if (v.getId() == R.id.fragment_settings_save_button) {
            makeCheck(make.getText().toString());

            Toast.makeText(getContext() , getString(R.string.saved),Toast.LENGTH_SHORT ).show();

            Rides rideSaver = new Rides(getContext(), "name" );
            rideSaver.setMake(make.getText().toString());
            rideSaver.setModel(model.getText().toString());
            rideSaver.setTires(model.getText().toString());
            rideSaver.setBackPsi(Long.parseLong(frontPsi.getText().toString()));
            rideSaver.setFrontPsi(Long.parseLong(backPsi.getText().toString()));
            try {
                rideSaver.save(getContext());
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        if (v.getId() == R.id.fragment_settings_clear_button) {
            make.setText("");
            model.setText("");
            tires.setText("");
            frontPsi.setText("");
            backPsi.setText("");
            Toast.makeText(getContext() , R.string.cleared ,Toast.LENGTH_SHORT ).show();

        }
    }

    void makeCheck(String makeString) {
        makeString = makeString.toLowerCase();
        makeString = makeString.replaceAll("\\s+", "");
        if (makeString.equals("kawasaki")) {
            make.setTextColor(Color.parseColor("#4AF400"));
//            make.setTextColor(ContextCompat.getColor(getContext(), R.color.kawasakiTextColor));
        }
        if (makeString.equals("ktm")) {
            make.setTextColor(Color.parseColor("#f27620"));
        }

        if (makeString.equals("suzuki")) {
            make.setTextColor(Color.parseColor("#52c6fd"));
        }
        if (makeString.equals("ducati")) {
            make.setTextColor(Color.parseColor("#FF0000"));
        }

        if (makeString.equals("honda")) {
            make.setTextColor(Color.parseColor("#f7db22"));
        }

        if (makeString.equals("yamaha")) {
            make.setTextColor(Color.parseColor("#0c06d8"));

        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        name = (EditText) getActivity().findViewById(R.id.fragment_settings_name);
        make = (EditText) getActivity().findViewById(R.id.fragment_settings_make_edittext);
        model = (EditText) getActivity().findViewById(R.id.fragment_settings_model_edittext);
        tires = (EditText) getActivity().findViewById(R.id.fragment_settings_tires_edittext);
        frontPsi = (EditText) getActivity().findViewById(R.id.fragment_settings_psi_front_edittext);
        backPsi = (EditText) getActivity().findViewById(R.id.fragment_settings_psi_back_edittext);
        save = (Button) getActivity().findViewById(R.id.fragment_settings_save_button);
        clear = (Button) getActivity().findViewById(R.id.fragment_settings_clear_button);


        make.setOnClickListener(this);
        model.setOnClickListener(this);
        tires.setOnClickListener(this);
        frontPsi.setOnClickListener(this);
        backPsi.setOnClickListener(this);

        save.setOnClickListener(this);
        clear.setOnClickListener(this);

        super.onActivityCreated(savedInstanceState);
    }
}
