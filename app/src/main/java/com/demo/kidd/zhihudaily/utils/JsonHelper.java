package com.demo.kidd.zhihudaily.utils;

import com.demo.kidd.zhihudaily.bean.Question;
import com.demo.kidd.zhihudaily.bean.Story;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by niuwa on 2016/6/22.
 */
public class JsonHelper {
    public static List<Story> parseJsonToList(String json) throws JSONException{
        JSONObject content = new JSONObject(json);
        JSONArray newsArray = content.getJSONArray("stories");
        List<Story> storyList = new ArrayList<Story>();
        for (int i=0; i<newsArray.length(); i++){
            JSONObject singleNews = newsArray.getJSONObject(i);
            int id = singleNews.optInt("id");
            String title = singleNews.optString("title");
            String image = "";
            if (singleNews.has("images"))
                image = (String)singleNews.getJSONArray("images").get(0);

            Story story = new Story(id, image, title);
            storyList.add(story);
        }
        return storyList;
    }

    public static Question parseJsonToDetail(String json) throws JSONException{
        Gson gson = new Gson();
        return gson.fromJson(json, Question.class);
    }
}
