package com.demo.kidd.zhihudaily.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.demo.kidd.zhihudaily.Constants;
import com.demo.kidd.zhihudaily.LoadNewsTask;
import com.demo.kidd.zhihudaily.R;
import com.demo.kidd.zhihudaily.bean.Story;
import com.demo.kidd.zhihudaily.utils.Utility;
import com.demo.kidd.zhihudaily.ui.activity.MainActivity;
import com.demo.kidd.zhihudaily.ui.adapter.NewsAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by niuwa on 2016/6/21.
 */
public class ListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    private List<Story> mStoryList = new ArrayList<>();

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private NewsAdapter mNewsAdapter;

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
        View v = inflater.inflate(R.layout.fragment_news_list, container, false);
        ButterKnife.bind(this, v);

        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(llm);

        mNewsAdapter = new NewsAdapter(getActivity(), mStoryList);
        mRecyclerView.setAdapter(mNewsAdapter);

        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);

        isVisible = true;

        doRefresh();

        return v;
    }

    @Override
    public void onResume(){
        super.onResume();
        doRefresh();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isVisible)
            doRefresh();
    }

    @Override
    public void onRefresh(){
        doRefresh();
    }

    private void doRefresh(){
        isConnected = Utility.checkNetworkConnection(getActivity());

        if(mSwipeRefreshLayout != null)
            mSwipeRefreshLayout.setRefreshing(true);
        if (isConnected){
            new LoadNewsTask(date, isToday, mNewsAdapter).execute();
        }
        else
            ((MainActivity)getActivity()).showSnackBar(R.string.unconnected);

        mSwipeRefreshLayout.setRefreshing(false);
    }

}
