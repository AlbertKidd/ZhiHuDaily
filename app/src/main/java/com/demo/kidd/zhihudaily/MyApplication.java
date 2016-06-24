package com.demo.kidd.zhihudaily;

import android.app.Application;
import android.content.Context;

/**
 * Created by niuwa on 2016/6/23.
 */
public class MyApplication extends Application{
    public static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }

    public static Context getWholeContext(){
        return mContext;
    }
}

