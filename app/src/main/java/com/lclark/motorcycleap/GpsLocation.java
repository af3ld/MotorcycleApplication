package com.lclark.motorcycleap;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.LocationListener;

/**
 * Created by alexfeldman on 4/15/16.
 */
public class GpsLocation extends Service implements android.location.LocationListener {

    public static final String TAG = GpsLocation.class.getSimpleName();
    private Context mContext;
    protected LocationManager locationManager;
    private boolean isGpsEnabled = false;
    private boolean isNetworkEnabled = false;
    private boolean canGetLocation = false;
    private Location mLocation;
    private double mLatitude;
    private double mLongitude;
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATE = 5;
    /**
     * min time for location update
     * 60000 = 1min
     */
    private static final long MIN_TIME_FOR_UPDATE = 5000;

    public GpsLocation(Context context){
        mContext = context;
        getLocation();
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public Location getLocation() {
        try {
            locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
            isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            if (!isGpsEnabled && !isNetworkEnabled) {
                Toast.makeText(getApplicationContext(), getString(R.string.common_google_play_services_network_error_text), Toast.LENGTH_SHORT).show();
            } else {
                canGetLocation = true;
                if (isNetworkEnabled) {
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_FOR_UPDATE, MIN_DISTANCE_CHANGE_FOR_UPDATE, this);
                    if (mLocation != null) {
                        mLatitude = mLocation.getLatitude();
                        mLongitude = mLocation.getLongitude();
                    }
                    if (isGpsEnabled) {
                        if (mLocation == null) {
                            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                                    MIN_TIME_FOR_UPDATE, MIN_DISTANCE_CHANGE_FOR_UPDATE, this);
                            if (locationManager != null) {
                                mLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                                if (mLocation != null) {
                                    mLatitude = mLocation.getLatitude();
                                    mLongitude = mLocation.getLongitude();
                                }
                            }

                        }
                    }
                }
            }
        } catch (Exception e) {
            Log.e(TAG, e.getLocalizedMessage());
        }
        return mLocation;
    }

    public boolean isCanGetLocation() {
        return canGetLocation;
    }

    public double getmLatitude() {
        if (mLocation != null){
            mLatitude = mLocation.getLatitude();
        }
        return mLatitude;
    }

    public double getmLongitude() {
        if (mLocation != null){
            mLongitude = mLocation.getLatitude();
        }
        return mLongitude;
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
