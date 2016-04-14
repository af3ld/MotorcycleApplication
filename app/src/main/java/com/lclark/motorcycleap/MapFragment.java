package com.lclark.motorcycleap;


import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by alexfeldman on 4/13/16.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback{

    public static final String TAG = MapFragment.class.getSimpleName();
    public static final String ARG_COLOR = "Color";
    public static final String ARG_INDEX = "Index";
    public static final String ARG_LOCATION = "Location";
    public MapView mMapView;
    private GoogleMap mMap;
    private LatLng startingLatLng;


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
        mMapView = (MapView) rootView.findViewById(R.id.map_frag);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();
        Bundle args = getArguments();
        int index = args.getInt(ARG_INDEX);
        startingLatLng = args.getParcelable(ARG_LOCATION);
        Log.d(TAG, "Fragment at " + index);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.addMarker(new MarkerOptions().position(startingLatLng));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(startingLatLng));
    }
}
