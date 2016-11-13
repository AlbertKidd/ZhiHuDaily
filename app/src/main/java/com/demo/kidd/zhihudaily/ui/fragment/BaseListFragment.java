package com.demo.kidd.zhihudaily.ui.fragment;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.demo.kidd.zhihudaily.R;
import com.demo.kidd.zhihudaily.bean.Story;
import com.demo.kidd.zhihudaily.ui.adapter.NewsAdapter;
import com.demo.kidd.zhihudaily.utils.Utility;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by niuwa on 2016/6/27.
 */
public class BaseListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    protected List<Story> mStoryList = new ArrayList<>();

    protected NewsAdapter mNewsAdapter;

    protected View v;

    private ConnectionReceiver receiver;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        receiver = new ConnectionReceiver();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        activity.registerReceiver(receiver, filter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_news_list, container, false);

        ButterKnife.bind(this, v);

        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(llm);

        mNewsAdapter = new NewsAdapter(getActivity(), mStoryList);
        mRecyclerView.setAdapter(mNewsAdapter);

        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
    }

    @Override
    public void onResume() {
        super.onResume();

        doRefresh();
    }

    @Override
    public void onRefresh() {
        doRefresh();
    }

    @Override
    public void onDestroyView() {
        getActivity().unregisterReceiver(receiver);
        super.onDestroyView();
    }

    protected void doRefresh(){}

    public class ConnectionReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            if (Utility.checkNetworkConnection(context))
                doRefresh();
        }
    }


}
