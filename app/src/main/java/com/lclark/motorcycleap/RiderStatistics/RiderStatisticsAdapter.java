package com.lclark.motorcycleap.RiderStatistics;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lclark.motorcycleap.R;

import java.util.List;


public class RiderStatisticsAdapter extends BaseAdapter {

    private Context mContext;
    private List<Rides> ridesList;

    public RiderStatisticsAdapter(Context context) {
        mContext = context;
    }


    @Override
    public int getCount() {
        return ridesList.size();
    }

    @Override
    public Object getItem(int position) {
        return ridesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return ridesList.get(position).getLong_id();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(mContext, R.layout.fragment_rider_stats_cardview, null);
        TextView frontPsi = (TextView) v.findViewById(R.id.rider_stats_card_view_Front_PSI);

        frontPsi.setText(ridesList.get(position).getBackPsi() + "");
        return v;
    }

}
