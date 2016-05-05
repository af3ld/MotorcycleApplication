package com.lclark.motorcycleap.RiderStatistics;

import android.view.View;
import android.widget.Toast;

import java.io.IOException;

/**
 * Created by Sweeney Todd on 5/5/2016.
 */
public class FabClickListener implements View.OnClickListener{

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
                ride.save(v.getContext());
            } catch (IOException e) {
                e.printStackTrace();
            }
            ridingNow = 0;
        }
    }



}
