package com.lclark.motorcycleap;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by alexfeldman on 4/13/16.
 */
public class TabAdapter extends FragmentPagerAdapter {

    private final Context mContext;


    public TabAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {

        @ColorInt int[] colors = {
                Color.BLUE, Color.GREEN, Color.RED
        };
        if (position == 1) {
            MapFragment.newInstance(colors[position], position, new LatLng(-34, 151));
        }
        return TabFragment.newInstance(colors[position], position);


    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return (position == 0) ? mContext.getString(R.string.settings_page_title) :
                (position == 1) ? mContext.getString(R.string.main_page_title) :
                        mContext.getString(R.string.stats_page_title);

    }
}
