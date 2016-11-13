package com.demo.kidd.zhihudaily.utils;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.demo.kidd.zhihudaily.bean.Story;
import com.demo.kidd.zhihudaily.ui.adapter.NewsAdapter;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by niuwa on 2016/6/22.
 */
public class HttpUtil {
    public static String NEWSLIST_LATEST = "http://news-at.zhihu.com/api/4/news/latest";
    public static String NEWSLIST_BEFORE = "http://news-at.zhihu.com/api/4/news/before/";
    public static String STORY_VIEW = "http://daily.zhihu.com/story/";
    public static String NEWSDETAIL = "http://news-at.zhihu.com/api/4/news/";

    private static Gson mGson = new Gson();

    public static String get(String urlAddr) throws IOException{
        HttpURLConnection connection = null;
        try {
            URL url = new URL(urlAddr);
            connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                BufferedReader in =
                        new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                StringBuilder response = new StringBuilder();

                while ((line = in.readLine()) != null){
                    response.append(line);
                }
                in.close();
                return response.toString();
            }else {
                throw new IOException("Network Error, response code:" + connection.getResponseCode());
            }
        }finally {
            if (connection != null)
                connection.disconnect();
        }
    }

//    public static ArrayList<Story> get(Context context, String address){
//        RequestQueue queue = Volley.newRequestQueue(context);
//        storyList = null;
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(address, null,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        try {
//                            Log.d("Kidd", response.toString());
//                            JSONArray stories = response.getJSONArray("stories");
//                            storyList = new ArrayList<>();
//                            for (int i = 0; i < stories.length(); i++){
//                                Story story = mGson.fromJson(stories.getJSONObject(i).toString(), Story.class);
//                                Log.d("Kidd", story.getTitle().toString());
//                                storyList.add(story);
//                            }
//                            Log.d("Kidd", storyList.toString());
//                        }catch (Exception e){
//                            e.printStackTrace();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        });
//        queue.add(jsonObjectRequest);
//        return storyList;
//    }
//
//    public static ArrayList<Story> get(Context context){
//        return get(context, NEWSLIST_LATEST);
//    }
//
//    public static ArrayList<Story> get(Context context, int id){
//        return get(context, NEWSDETAIL + id);
//    }

    public static String get() throws IOException{
        return  get(NEWSLIST_LATEST);
    }

    public static String getNewsDetail(int id) throws IOException{
        return get(NEWSDETAIL + id);
    }

    public static void load(Context context, String date, boolean isToday, final NewsAdapter adapter){

        RequestQueue queue = Volley.newRequestQueue(context);
        String address;
        if (isToday)
            address = NEWSLIST_LATEST;
        else
            address = NEWSLIST_BEFORE + date;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(address, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray stories = response.getJSONArray("stories");
                            List<Story> storyList = new ArrayList<>();
                            for (int i = 0; i < stories.length(); i++){
                                JSONObject singleStory = stories.getJSONObject(i);
                                Story story = mGson.fromJson(stories.getJSONObject(i).toString(), Story.class);
                                if (singleStory.has("images"))
                                    story.setImage(singleStory.getJSONArray("images").getString(0));
                                storyList.add(story);
                            }
                            adapter.refreshStoryList(storyList);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        queue.add(jsonObjectRequest);
    }

}
