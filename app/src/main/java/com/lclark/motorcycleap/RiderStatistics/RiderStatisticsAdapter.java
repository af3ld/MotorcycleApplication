package com.lclark.motorcycleap.RiderStatistics;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lclark.motorcycleap.R;

import java.util.List;


public class RiderStatisticsAdapter extends BaseAdapter {

    private Context mContext;
    private Rides ride;
    private  Rides settings;

    public RiderStatisticsAdapter(Context context) {
        mContext = context;
        ride = new Rides();

        settings = Rides.load(context,"settings", settings);
    }


    @Override
    public int getCount() {
        SharedPreferences sharedPref = mContext.getSharedPreferences("Ride_id", Context.MODE_PRIVATE);
        int temp = sharedPref.getInt("ride_id", -1);
return temp + 1;
    }

    @Override
    public Object getItem(int position) {
        return Rides.load(mContext, position +"", ride);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ride = Rides.load(mContext, position +"", ride);

        View v = View.inflate(mContext, R.layout.fragment_rider_stats_cardview, null);
        TextView maxLeanTextView = (TextView) v.findViewById(R.id.card_view_max_lean);
        TextView rideTime = (TextView) v.findViewById(R.id.card_view_ride_length);
        TextView psi = (TextView) v.findViewById(R.id.card_view_psi);

        long minutes=((ride.startTime)/1000)/60;
        int seconds = (int) (ride.startTime / 1000) % 60 ;
        rideTime.setText(minutes +"m " + seconds + "s" );
        maxLeanTextView.setText("MAX LEAN ANGLE = " + ride.max_lean + "");
        psi.setText("Psi Front:" + settings.frontPsi + " Psi Back: " + settings.getBackPsi());
        return v;
    }

}
