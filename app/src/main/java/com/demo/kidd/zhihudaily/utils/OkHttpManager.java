package com.demo.kidd.zhihudaily.utils;

import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by niuwa on 2016/11/24.
 */

public class OkHttpManager {

    private OkHttpClient mOkHttpClient;
    private Gson mGson;
    private static OkHttpManager mInstance;

    private OkHttpManager(){
        mOkHttpClient = new OkHttpClient();
        mGson = new Gson();
    }

    public static OkHttpManager getInstance(){
        if (mInstance == null){
            synchronized (OkHttpManager.class){
                if (mInstance == null)
                    mInstance = new OkHttpManager();
            }
        }
        return mInstance;
    }

    public Response getResponse(String url) throws IOException {
        return mOkHttpClient.newCall(new Request.Builder().url(url).build()).execute();
    }

    public String getBody(String url) throws IOException{
        return mOkHttpClient.newCall(new Request.Builder().url(url).build()).execute().body().string();
    }

}
