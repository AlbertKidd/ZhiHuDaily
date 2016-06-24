package com.demo.kidd.zhihudaily.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by niuwa on 2016/6/22.
 */
public class HttpUtil {
    public static String NEWSLIST_LATEST = "http://news-at.zhihu.com/api/4/news/latest";
    public static String NEWSLIST_BEFORE = "http://news-at.zhihu.com/api/4/news/before/";
    public static String STORY_VIEW = "http://daily.zhihu.com/story/";
    public static String NEWSDETAIL = "http://news-at.zhihu.com/api/4/news/";

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

    public static String get() throws IOException{
        return  get(NEWSLIST_LATEST);
    }

    public static String getNewsDetail(int id) throws IOException{
        return get(NEWSDETAIL + id);
    }
}
