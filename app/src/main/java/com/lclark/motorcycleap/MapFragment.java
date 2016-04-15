package com.lclark.motorcycleap;



import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;


/**
 * Created by alexfeldman on 4/13/16.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback{

    public static final String TAG = MapFragment.class.getSimpleName();
    public static final String ARG_COLOR = "Color";
    public static final String ARG_INDEX = "Index";
    public static final String ARG_LOCATION = "Location";
    private MapView mapFragment;
    private GoogleMap mMap;
    private LatLng startingLatLng;
    private Boolean isRideTracking = false;


    public static MapFragment newInstance(@ColorInt int color, int index, LatLng latLng) {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLOR, color);
        args.putInt(ARG_INDEX, index);
        args.putParcelable(ARG_LOCATION, latLng);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);
        setUpFAB(rootView);
        mapFragment = (MapView) rootView.findViewById(R.id.map_fragment);
        mapFragment.onCreate(savedInstanceState);
        mapFragment.getMapAsync(this);

        Bundle args = getArguments();
        int index = args.getInt(ARG_INDEX);
        startingLatLng = args.getParcelable(ARG_LOCATION);
        Log.d(TAG, "Fragment at " + index);
        return rootView;
    }

    /*
    * Sets up the Floating action button with an onclick*/
    public void setUpFAB(View rootView) {
        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isRideTracking) {
                    Snackbar.make(view, "Ride begun", Snackbar.LENGTH_SHORT).show();
                    isRideTracking = true;
                } else {
                    Snackbar.make(view, "Ride over", Snackbar.LENGTH_SHORT).show();
                    isRideTracking = false;
                }
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

//        mMap.addMarker(new MarkerOptions().position(startingLatLng));
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(startingLatLng, 15));
    }

    public void setUpLocation(){
    }


    @Override
    public void onResume() {
        super.onResume();
        mapFragment.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
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
}
