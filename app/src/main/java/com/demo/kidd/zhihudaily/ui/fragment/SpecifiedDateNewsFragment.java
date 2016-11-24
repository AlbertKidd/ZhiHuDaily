package com.demo.kidd.zhihudaily.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.demo.kidd.zhihudaily.Constants;
import com.demo.kidd.zhihudaily.R;
import com.demo.kidd.zhihudaily.bean.Story;
import com.demo.kidd.zhihudaily.ui.activity.PickDateActivity;
import com.demo.kidd.zhihudaily.utils.JsonHelper;
import com.demo.kidd.zhihudaily.utils.ObservableManager;
import com.demo.kidd.zhihudaily.utils.Utility;

import java.util.Date;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

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
            load();
//            new LoadNewsTask(getActivity(), formatDate, false, mNewsAdapter).execute();
//            HttpUtil.load(getActivity(), formatDate, false, mNewsAdapter);
//            Observable.create(new Observable.OnSubscribe<String>(){
//                @Override
//                public void call(Subscriber<? super String> subscriber) {
//                    try {
//                        String response = OkHttpManager.getInstance().getBody(Constants.URLs.NEWSLIST_BEFORE + formatDate);
//                        subscriber.onNext(response);
//                    }catch (Exception e){
//                        e.printStackTrace();
//                    }
//                }
//            }).subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(new Action1<String>() {
//                @Override
//                public void call(String s) {
//                    try {
//                        List<Story> storyList = JsonHelper.parseJsonToList(s);
//                        mNewsAdapter.refreshStoryList(storyList);
//                    }catch (Exception e){
//                        e.printStackTrace();
//                    }
//                }
//            });
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
//            HttpUtil.load(getActivity(), formatDate, false, mNewsAdapter);
            load();
        }
        else
            ((PickDateActivity)getActivity()).showSnackBar(R.string.unconnected);

        mSwipeRefreshLayout.setRefreshing(false);
    }

    private void load(){
        ObservableManager.getStoryListObservable(formatDate, false)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        try {
                            List<Story> storyList = JsonHelper.parseJsonToList(s);
                            mNewsAdapter.refreshStoryList(storyList);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });
    }
}
