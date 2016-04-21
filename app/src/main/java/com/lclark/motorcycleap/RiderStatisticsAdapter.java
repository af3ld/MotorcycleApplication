package com.lclark.motorcycleap;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by alexfeldman on 4/21/16.
 */
public class RiderStatisticsAdapter extends BaseAdapter {

    private Context mContext;

    public static class ViewHolder {
        TextView title;
    }


    public RiderStatisticsAdapter(Context context) {
        mContext = context;
    }


    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.fragment_recyclerview_rider_stats, parent, false);
            TextView titleTextView = (TextView) convertView.findViewById(R.id.row_title);
            viewHolder = new ViewHolder();
            viewHolder.title = titleTextView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }
}