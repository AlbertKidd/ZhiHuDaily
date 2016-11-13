package com.demo.kidd.zhihudaily.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.demo.kidd.zhihudaily.Constants;
import com.demo.kidd.zhihudaily.R;
import com.demo.kidd.zhihudaily.ui.activity.MainActivity;
import com.demo.kidd.zhihudaily.utils.HttpUtil;
import com.demo.kidd.zhihudaily.utils.Utility;

/**
 * Created by niuwa on 2016/6/21.
 */
public class NewsListFragment extends BaseListFragment{

    private String date;
    private boolean isToday;
    private boolean isConnected;

    private boolean isVisible;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            Bundle bundle = getArguments();
            date = bundle.getString(Constants.BundleKeys.DATE);
            isToday = bundle.getBoolean(Constants.BundleKeys.IS_FIRST_PAGE);

            setRetainInstance(true);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        isVisible = true;

        if (v != null){
            ViewGroup parent = (ViewGroup) v.getParent();
            if (parent != null)
                parent.removeView(v);
            return v;
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isVisible)
            doRefresh();
    }

    @Override
    protected void doRefresh(){
        isConnected = Utility.checkNetworkConnection(getActivity());

        if(mSwipeRefreshLayout != null)
            mSwipeRefreshLayout.setRefreshing(true);
        if (isConnected){
//            new LoadNewsTask(getActivity(), date, isToday, mNewsAdapter).execute();
            HttpUtil.load(getActivity(), date, isToday, mNewsAdapter);
        }
        else
            ((MainActivity)getActivity()).showSnackBar(R.string.unconnected);

        mSwipeRefreshLayout.setRefreshing(false);
    }

}
