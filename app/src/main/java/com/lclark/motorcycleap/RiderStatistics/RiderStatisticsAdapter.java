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

    public RiderStatisticsAdapter(Context context) {
        mContext = context;
    }


    @Override
    public int getCount() {
        SharedPreferences sharedPref = mContext.getSharedPreferences("Ride_id", Context.MODE_PRIVATE);
        int temp = sharedPref.getInt("ride_id", 0);
return temp;
    }

    @Override
    public Object getItem(int position) {
        return Rides.load(mContext, position +"");
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ride = Rides.load(mContext, position +"");

        View v = View.inflate(mContext, R.layout.fragment_rider_stats_cardview, null);
        TextView maxLeanTextView = (TextView) v.findViewById(R.id.card_view_max_lean);
        maxLeanTextView.setText(ride.getMax_speed() + "");
        return v;
    }

}
