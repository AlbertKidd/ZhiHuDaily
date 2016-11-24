package com.demo.kidd.zhihudaily.utils;

import com.demo.kidd.zhihudaily.Constants;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by niuwa on 2016/11/24.
 */

public class ObservableManager {

    public static Observable<String> getStoryListObservable(final String date, final boolean isToday){
        return Observable.create(new Observable.OnSubscribe<String>(){
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    String response = OkHttpManager.getInstance().getBody(isToday ? Constants.URLs.NEWSLIST_LATEST : Constants.URLs.NEWSLIST_BEFORE + date);
                    subscriber.onNext(response);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    public static Observable<String> getDetailObservable(final int id){
        return Observable.create(new Observable.OnSubscribe<String>(){
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    String response = OkHttpManager.getInstance().getBody(Constants.URLs.NEWSDETAIL + id);
                    subscriber.onNext(response);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
}
