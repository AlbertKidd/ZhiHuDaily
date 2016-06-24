package com.demo.kidd.zhihudaily.bean;

import java.util.List;

/**
 * Created by niuwa on 2016/6/22.
 */
public class DailyNews {
    private String date;
    private String dailyTitle;
    private String thumbnailUrl;
    private List<Question> questions;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDailyTitle() {
        return dailyTitle;
    }

    public void setDailyTitle(String dailyTitle) {
        this.dailyTitle = dailyTitle;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public boolean hasMutipleQuestions(){
        return this.getQuestions().size() > 1;
    }
}
