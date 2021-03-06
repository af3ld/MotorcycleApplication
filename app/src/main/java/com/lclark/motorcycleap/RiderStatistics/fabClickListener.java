package com.lclark.motorcycleap.RiderStatistics;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * Created by Sweeney Todd on 5/5/2016.
 */
public class FabClickListener implements View.OnClickListener, View.OnLongClickListener {

    Rides ride;
   public int ridingNow;


    public void setRidingNow(int ridingNow) {
        this.ridingNow = ridingNow;
    }

    public void onClick(View v) {

        if (ridingNow ==0) {
            Toast.makeText(v.getContext(), "STARTING RIDE", Toast.LENGTH_SHORT).show();
            ride = new Rides(v.getContext());
            ride.setMax_speed(0);
            ridingNow = 1;
        }


        else if (ridingNow == 1) {
            Toast.makeText(v.getContext(), "ENDING RIDE", Toast.LENGTH_SHORT).show();
            try {
                ride.startTime = System.currentTimeMillis() - ride.startTime;
                ride.save(v.getContext());
            } catch (IOException e) {
                e.printStackTrace();
            }
            ridingNow = 0;
        }
    }


    @Override
    public boolean onLongClick(View v) {
        SharedPreferences sharedPref = v.getContext().getSharedPreferences("Ride_id", Context.MODE_APPEND);
        SharedPreferences.Editor editor = sharedPref.edit();

        Toast.makeText(v.getContext(), "RIDES DELETED", Toast.LENGTH_SHORT).show();
        editor.putInt("ride_id", -1 );
        editor.commit();
        return true;

    }
}
