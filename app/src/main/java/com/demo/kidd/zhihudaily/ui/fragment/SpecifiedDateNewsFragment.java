package com.demo.kidd.zhihudaily.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.demo.kidd.zhihudaily.Constants;
import com.demo.kidd.zhihudaily.R;
import com.demo.kidd.zhihudaily.ui.activity.PickDateActivity;
import com.demo.kidd.zhihudaily.utils.HttpUtil;
import com.demo.kidd.zhihudaily.utils.Utility;

import java.util.Date;

/**
 * Created by niuwa on 2016/6/27.
 */
public class SpecifiedDateNewsFragment extends BaseListFragment {

    private Date date;
    private String formatDate;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null)
            date = (Date)getArguments().getSerializable(Constants.BundleKeys.DATE);

        formatDate = Constants.Dates.simpleDateFormat.format(date);
        String date_y = Constants.Dates.cnDateFormat_year.format(date);
        ((PickDateActivity)getActivity()).getSupportActionBar().setTitle(date_y);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (Utility.checkNetworkConnection(getActivity()))
//            new LoadNewsTask(getActivity(), formatDate, false, mNewsAdapter).execute();
            HttpUtil.load(getActivity(), formatDate, false, mNewsAdapter);
        else
            ((PickDateActivity)getActivity()).showSnackBar(R.string.unconnected);
    }

    @Override
    public void onRefresh() {
        boolean isConnected = Utility.checkNetworkConnection(getActivity());

        if(mSwipeRefreshLayout != null)
            mSwipeRefreshLayout.setRefreshing(true);
        if (isConnected){
//            new LoadNewsTask(getActivity(), formatDate, false, mNewsAdapter).execute();
            HttpUtil.load(getActivity(), formatDate, false, mNewsAdapter);
        }
        else
            ((PickDateActivity)getActivity()).showSnackBar(R.string.unconnected);

        mSwipeRefreshLayout.setRefreshing(false);
    }
}
