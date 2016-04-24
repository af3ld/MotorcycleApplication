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

    private final int SETTINGS = 0;
    private final int MAIN = 1;
    private final int STATS = 2;

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
        switch (position){
            case SETTINGS:
                return SettingsFragment.newInstance(colors[position], position);
            case MAIN:
                return MapFragment.newInstance(colors[position], position, new LatLng(-34, 151));
            default:
                return RiderStatisticsFragment.newInstance(position);
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return (position == SETTINGS) ? mContext.getString(R.string.settings_page_title) :
                (position == MAIN) ? mContext.getString(R.string.main_page_title) :
                        mContext.getString(R.string.stats_page_title);

    }
}
