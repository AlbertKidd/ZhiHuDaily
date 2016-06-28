package com.demo.kidd.zhihudaily.ui.fragment;

import android.os.Bundle;

import com.demo.kidd.zhihudaily.DB.FavoriteDB;

/**
 * Created by niuwa on 2016/6/27.
 */
public class FavoriteNewsFragment extends BaseListFragment{

    FavoriteDB favoriteDB;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         favoriteDB = new FavoriteDB(getActivity());
        mStoryList = favoriteDB.loadFavorite();
    }

    @Override
    protected void doRefresh() {
        mStoryList = favoriteDB.loadFavorite();
        mNewsAdapter.refreshStoryList(mStoryList);
        mSwipeRefreshLayout.setRefreshing(false);
    }
}
