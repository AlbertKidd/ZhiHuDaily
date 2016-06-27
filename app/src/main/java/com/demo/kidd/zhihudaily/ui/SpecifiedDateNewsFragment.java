package com.demo.kidd.zhihudaily.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.demo.kidd.zhihudaily.Constants;
import com.demo.kidd.zhihudaily.LoadNewsTask;
import com.demo.kidd.zhihudaily.R;
import com.demo.kidd.zhihudaily.bean.Story;
import com.demo.kidd.zhihudaily.ui.activity.PickDateActivity;
import com.demo.kidd.zhihudaily.ui.adapter.NewsAdapter;
import com.demo.kidd.zhihudaily.utils.Utility;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by niuwa on 2016/6/27.
 */
public class SpecifiedDateNewsFragment extends Fragment {
    private List<Story> mStoryList = new ArrayList<>();

    @BindView(R.id.specified_date_news_view)
    RecyclerView mSpecifiedDateNewsView;

    private Date date;
    private String formatedDate;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null)
            date = (Date)getArguments().getSerializable(Constants.BundleKeys.DATE);

        formatedDate = Constants.Dates.simpleDateFormat.format(date);
        String date_y = Constants.Dates.cnDateFormat_year.format(date);
        ((PickDateActivity)getActivity()).getSupportActionBar().setTitle(date_y);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_specified_date_news, container, false);
        ButterKnife.bind(this, v);

        //mSpecifiedDateNewsView.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        mSpecifiedDateNewsView.setLayoutManager(llm);

        NewsAdapter newsAdapter = new NewsAdapter(getActivity(), mStoryList);
        mSpecifiedDateNewsView.setAdapter(newsAdapter);

        if (Utility.checkNetworkConnection(getActivity()))
            new LoadNewsTask(formatedDate, false, newsAdapter).execute();
        else
            ((PickDateActivity)getActivity()).showSnackBar(R.string.unconnected);

        return v;
    }
}
