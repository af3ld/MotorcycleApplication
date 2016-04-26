package com.lclark.motorcycleap;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by 2yan on 26-Apr-16.
 */
public class Rides {

    static String id;

    double max_speed;
    double average_speed;





    static String tires;

    static long frontPsi;
    static long backPsi;

    static String make;
    static String model;



    public Rides(Context context, String id_in){
        id = id_in;
        SharedPreferences sharedPreferences = context.getSharedPreferences(id, Context.MODE_PRIVATE);
        make = sharedPreferences.getString(R.string.make + "", "" );
        model = sharedPreferences.getString(R.string.model + "", "");
        tires = sharedPreferences.getString(R.string.tires + "", "");
        frontPsi = sharedPreferences.getLong(R.string.frontPSI_hint + "", 0);
        backPsi =  sharedPreferences.getLong(R.string.backPSI_hint + "", 0);


    }

   public void save(Context context ){
    SharedPreferences sharedPreferences = context.getSharedPreferences(id, Context.MODE_PRIVATE);
       SharedPreferences.Editor editor = sharedPreferences.edit();

       editor.putString(R.string.make + "", make);
       editor.putString(R.string.model + "", model);
       editor.putString(R.string.tires + "", tires);
       editor.putLong(R.string.backPSI_hint + "", backPsi);
       editor.putLong(R.string.frontPSI_hint + "", frontPsi);


    }



    public static String getModel() {
        return model;
    }

    public static void setModel(String model) {
        Rides.model = model;
    }

    public double getMax_speed() {
        return max_speed;
    }

    public void setMax_speed(double max_speed) {
        this.max_speed = max_speed;
    }

    public double getAverage_speed() {
        return average_speed;
    }

    public void setAverage_speed(double average_speed) {
        this.average_speed = average_speed;
    }


    public String getTires() {
        return tires;
    }

    public void setTires(String tires) {
        this.tires = tires;
    }

    public static long getFrontPsi() {
        return frontPsi;
    }

    public static void setFrontPsi(long frontPsi) {
        Rides.frontPsi = frontPsi;
    }

    public double getBackPsi() {
        return backPsi;
    }

    public void setBackPsi(long backPsi) {
        this.backPsi = backPsi;
    }

    public static String getMake() {
        return make;
    }

    public static void setMake(String make) {
        Rides.make = make;
    }
}
