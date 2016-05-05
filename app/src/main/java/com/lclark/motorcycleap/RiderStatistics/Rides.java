package com.lclark.motorcycleap.RiderStatistics;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.android.gms.maps.model.LatLng;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by 2yan on 26-Apr-16.
 */
public class Rides {

static String fileName;

    double max_lean;
    double max_speed;
    double average_speed;


    static String tires;

    static long frontPsi;
    static long backPsi;

    static String make;
    static String model;

    public ArrayList<LatLng> getCordinates() {
        return cordinates;
    }

    ArrayList<LatLng> cordinates;

    public void setCordinates(ArrayList<LatLng> cordinates) {
        this.cordinates = new ArrayList<LatLng>(cordinates);
    }


    public Rides load(Context context, String ID) throws IOException, ClassNotFoundException {
    FileInputStream fis = context.openFileInput(ID +"");
    ObjectInputStream is = new ObjectInputStream(fis);
    Rides returnme = (Rides) is.readObject();
    is.close();
    fis.close();
    return returnme;
    }

    public Rides(Context context){

        SharedPreferences sharedPref = context.getSharedPreferences("Ride_id", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        int temp = sharedPref.getInt("ride_id", -1);
        temp = temp++;
        editor.putInt("ride_id", temp );
        editor.commit();
        fileName = temp + "";
    }

    public Rides(Context context, String ID){
        fileName = ID;

    }

   public void save(Context context ) throws IOException {

       FileOutputStream fos = context.openFileOutput(fileName, MODE_PRIVATE);
       ObjectOutputStream os = new ObjectOutputStream(fos);
       os.writeObject(this);
       os.close();
       fos.close();
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

    public static long getID() {
        long retme = Long.getLong(fileName);
        return retme;
    }
}
