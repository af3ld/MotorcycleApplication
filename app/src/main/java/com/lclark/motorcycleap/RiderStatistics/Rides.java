package com.lclark.motorcycleap.RiderStatistics;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.Serializable;
import java.io.StreamCorruptedException;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by 2yan on 26-Apr-16.
 */
public class Rides implements Serializable {

    static String fileName;
    double max_lean;
    double max_speed;
    double average_speed;
    private double minutes, seconds = 60;


    static String tires;

    static long frontPsi;
    static long backPsi;

    static String make;
    static String model;

    long startTime;

    double CordinateX;
    double CordinateY;

    public ArrayList<LatLng> getCordinates() {
        return new ArrayList<LatLng>(0);
    }


    public void setCordinates(ArrayList<LatLng> cordinates) {


    }


    public static Rides load(Context context, String ID, Rides returnme) {

        try {
            FileInputStream fis = context.openFileInput(ID);
            ObjectInputStream is = new ObjectInputStream(fis);
            returnme = (Rides) is.readObject();
            is.close();
            fis.close();
            return returnme;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return returnme;
    }

    public Rides(Context context,ArrayList<LatLng> places) {

        startTime = System.currentTimeMillis();

    }

    public Rides(Context context) {

        startTime = System.currentTimeMillis();

    }

    public Rides() {
    }

    public static int getCount(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences("Ride_id", Context.MODE_APPEND);
        return sharedPref.getInt("ride_id", 0);


    }

    public Rides(Context context, String ID) {
        fileName = ID;

    }

    public void save(Context context) throws IOException {


        SharedPreferences sharedPref = context.getSharedPreferences("Ride_id", Context.MODE_APPEND);
        SharedPreferences.Editor editor = sharedPref.edit();

        int temp = sharedPref.getInt("ride_id", 0);
        temp++;
        editor.putInt("ride_id", temp);
        editor.commit();
        fileName = temp + "";

        FileOutputStream fos = context.openFileOutput(fileName, MODE_PRIVATE);
        ObjectOutputStream os = new ObjectOutputStream(fos);
        os.writeObject(this);
        os.close();
        fos.close();
    }

    public void saveSettings(Context context) throws IOException {

        FileOutputStream fos = context.openFileOutput("settings", MODE_PRIVATE);
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

    public Location latLngToLocation(LatLng ll){
        Location loc = new Location(LocationManager.GPS_PROVIDER);
        loc.setLongitude(ll.longitude);
        loc.setLatitude(ll.latitude);
        return loc;
    }

    public double getAverage_speed(ArrayList<LatLng> places) {
        for (int i = 0; i < places.size() - 1; i++){
            Location loc1 = latLngToLocation(places.get(i));
            Location loc2 = latLngToLocation(places.get(i + 1));
            double speed = loc1.distanceTo(loc2) / (10 * minutes * seconds);
            average_speed += speed;
        }

        return average_speed / (places.size() - 1);
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
