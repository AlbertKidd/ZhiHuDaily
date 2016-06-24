package com.demo.kidd.zhihudaily.ui.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.demo.kidd.zhihudaily.Constants;
import com.demo.kidd.zhihudaily.ui.ListFragment;

import java.util.Calendar;

/**
 * Created by niuwa on 2016/6/21.
 */
public class MainPagerAdapter extends FragmentStatePagerAdapter{

    public static final int PAGE_COUNT = 7;

    public MainPagerAdapter(FragmentManager fm){
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        Fragment newListFragment = new ListFragment();

        Calendar date = Calendar.getInstance();
        date.add(Calendar.DAY_OF_YEAR, -position);
        String formatDate = Constants.Dates.simpleDateFormat.format(date.getTime());

        bundle.putString(Constants.BundleKeys.DATE, formatDate);
        bundle.putBoolean(Constants.BundleKeys.IS_SINGLE, false);
        bundle.putBoolean(Constants.BundleKeys.IS_FIRST_PAGE, position==0);

        newListFragment.setArguments(bundle);
        return newListFragment;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position){
        Calendar date = Calendar.getInstance();
        date.add(Calendar.DAY_OF_YEAR, -position);
        String showDate = Constants.Dates.cnDateFormat.format(date.getTime());
        return position == 0 ? "今天" : showDate;
    }
}