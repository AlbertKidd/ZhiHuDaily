package com.demo.kidd.zhihudaily.task;

import android.content.Context;
import android.os.AsyncTask;

import com.demo.kidd.zhihudaily.bean.Story;
import com.demo.kidd.zhihudaily.ui.adapter.NewsAdapter;
import com.demo.kidd.zhihudaily.utils.HttpUtil;
import com.demo.kidd.zhihudaily.utils.JsonHelper;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

/**
 * Created by niuwa on 2016/6/22.
 */
public class LoadNewsTask extends AsyncTask<Void, Void, List<Story>> {

    private NewsAdapter mNewsAdapter;
    private OnFinishedListener mOnFinishedListener;
    private String date;
    private boolean istoday;
    private Context mContext;

    public LoadNewsTask(Context context, String date, boolean isToday, NewsAdapter adapter){
        super();
        this.date = date;
        this.istoday = isToday;
        this.mNewsAdapter = adapter;
        mContext = context;
    }


    @Override
    protected List<Story> doInBackground(Void... params) {
        List<Story> newsList = null;
        try {
            if (istoday)
                newsList = JsonHelper.parseJsonToList(HttpUtil.get());
            else
                newsList = JsonHelper.parseJsonToList(HttpUtil.get(HttpUtil.NEWSLIST_BEFORE+date));

        }catch (IOException | JSONException e){
            e.printStackTrace();
        }finally {
            return newsList;
        }

//        if (istoday)
//            return HttpUtil.get(mContext);
//        else
//            return HttpUtil.get(mContext, HttpUtil.NEWSLIST_BEFORE + date);
    }

    @Override
    protected void onPostExecute(List<Story> stories) {
        mNewsAdapter.refreshStoryList(stories);
    }

    public interface OnFinishedListener{
        public void afterTaskFinished();
    }
}
