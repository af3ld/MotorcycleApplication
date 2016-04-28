package com.lclark.motorcycleap;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.IntentSender;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;


/**
 * Created by alexfeldman on 4/13/16.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback,
        com.google.android.gms.location.LocationListener,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    public static final String TAG = MapFragment.class.getSimpleName();
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private final static int milliseconds = 1000;
    public static final String ARG_COLOR = "Color";
    public static final String ARG_INDEX = "Index";
    public static final String ARG_LOCATION = "Location";


    private Context mContext;
    private MapView mapFragment;
    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;

    public Boolean isRideTracking = false;
    private Boolean isFirstMarker = false;
    private Boolean isLastMarker = false;

    public ArrayList<LatLng> places;
    private int placesIndex = 0;
    private int startIndex;

    public static MapFragment newInstance(@ColorInt int color, int index, LatLng latLng) {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLOR, color);
        args.putInt(ARG_INDEX, index);
        args.putParcelable(ARG_LOCATION, latLng);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);
        setUpFAB(rootView);
        mapFragment = (MapView) rootView.findViewById(R.id.map_fragment);
        mapFragment.onCreate(savedInstanceState);
        mapFragment.getMapAsync(this);

        Bundle args = getArguments();
        int index = args.getInt(ARG_INDEX);
        Log.d(TAG, "Fragment at " + index);

        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * milliseconds)
                .setFastestInterval(milliseconds);

        return rootView;
    }

    /*
    * Sets up the Floating action button with an onclick*/
    public void setUpFAB(View rootView) {
        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.sand));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isRideTracking) {
                    Snackbar.make(view, "Rides begun", Snackbar.LENGTH_SHORT).show();
                    isRideTracking = true;
                    isFirstMarker = true;
                    places = new ArrayList<>();
                } else {
                    Snackbar.make(view, "Rides over", Snackbar.LENGTH_SHORT).show();
                    isRideTracking = false;
                    if (places != null && !places.isEmpty() && placesIndex >= 1) {
                        MarkerOptions options = new MarkerOptions()
                                .position(places.get(placesIndex))
                                .draggable(false)
                                .visible(true);
                        mMap.addMarker(options);
                    }
                    LatLngBounds.Builder builder = new LatLngBounds.Builder();
                    for (LatLng loc : places) {
                        builder.include(new MarkerOptions().position(loc).getPosition());
                    }
                    int padding = 70;
                    mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), padding));
                    mapAlert();
                }
            }
        });
    }

    private void mapAlert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                .setTitle(R.string.saveRide)
                .setMessage(R.string.saveRideMessage)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        mMap.clear();
                        Log.d(TAG, mContext.getString(R.string.onMapCleared));
                    }
                })
                .setIcon(ContextCompat.getDrawable(getActivity(), R.drawable.motorcycle));

        AlertDialog alertDialog = builder.create();
        Window window = alertDialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();

        wlp.gravity = Gravity.BOTTOM;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);
        alertDialog.show();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
    }

    public void polylinesUpdate() {
        if (places.size() >= 2) {
            startIndex = placesIndex;
            mMap.addPolyline(new PolylineOptions()
                    .add(places.get(placesIndex), places.get(placesIndex + 1))
                    .width(7)
                    .geodesic(true)
                    .color(ContextCompat.getColor(getContext(), R.color.muted_fuchsia)));
            placesIndex++;
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        mGoogleApiClient.connect();
        mapFragment.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
        mapFragment.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mapFragment.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapFragment.onLowMemory();
    }

    @Override
    public void onLocationChanged(Location location) {
        handleNewLocation(location);
        Log.d(TAG, "Location: " + location.getLatitude() + ", " + location.getLongitude());
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.i(TAG, getString(R.string.location_success));
        LocationServices.FusedLocationApi
                .requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, getString(R.string.location_disconnect));
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()) {
            try {
                connectionResult.startResolutionForResult(getActivity(), CONNECTION_FAILURE_RESOLUTION_REQUEST);
            } catch (IntentSender.SendIntentException e) {
                Log.e(TAG, e.getLocalizedMessage());
            }
        } else {
            Log.i(TAG, getString(R.string.location_failure) + connectionResult.getErrorCode());
        }
    }

    private void handleNewLocation(Location location) {
        Log.d(TAG, location.toString());
        double currentLatitude = location.getLatitude();
        double currentLongitude = location.getLongitude();
        LatLng latLng = new LatLng(currentLatitude, currentLongitude);
        if (isFirstMarker) {
            MarkerOptions options = new MarkerOptions()
                    .position(latLng)
                    .draggable(false)
                    .visible(true);
            isFirstMarker = false;
            mMap.addMarker(options);
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
        if (isRideTracking) {
            places.add(latLng);
            Log.d(TAG, Integer.toString(places.size()));
            polylinesUpdate();
        }
    }
}
