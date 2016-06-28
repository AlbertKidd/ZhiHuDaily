package com.demo.kidd.zhihudaily.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.demo.kidd.zhihudaily.bean.Story;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by niuwa on 2016/6/28.
 */
public class FavoriteDB {
    private DBHelper mDBHelper;
    public SQLiteDatabase mDatabase;

    public FavoriteDB(Context context){
        mDBHelper = DBHelper.getInstance(context);
        mDatabase = mDBHelper.getWritableDatabase();
    }

    public void saveNews(Story story){
        if (story != null){
            ContentValues values = new ContentValues();
            values.put(DBHelper.COLUMN_ID, story.getId());
            values.put(DBHelper.COLUMN_TITLE, story.getTitle());
            values.put(DBHelper.COLUMN_IMAGE, story.getImage());
            mDatabase.insert(DBHelper.TABLE_NAME, null, values);
        }
    }

    public void addFavorite(Story story){
        if (story != null){
            ContentValues values = new ContentValues();
            values.put(DBHelper.COLUMN_NEWS_ID, story.getId());
            values.put(DBHelper.COLUMN_TITLE, story.getTitle());
            values.put(DBHelper.COLUMN_IMAGE, story.getImage());
            mDatabase.insert(DBHelper.TABLE_NAME, null, values);
        }
    }

    public List<Story> loadFavorite(){
        List<Story> favoriteList = new ArrayList<>();
        Cursor cursor = mDatabase.query(DBHelper.TABLE_NAME, null, null, null, null, null, null);
        if (cursor.moveToFirst()){
            do {
                Story story = new Story(cursor.getInt(1), cursor.getString(3), cursor.getString(2));
                favoriteList.add(story);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return favoriteList;
    }

    public boolean isFavorite(Story story){
        Cursor cursor = mDatabase.query(DBHelper.TABLE_NAME, null, DBHelper.COLUMN_NEWS_ID + " = ?",
                new String[]{story.getId() + " "}, null, null, null);
        if (cursor.moveToNext()){
            cursor.close();
            return true;
        }else
            return false;
    }

    public void deleteFavorite(Story story){
        if (story != null)
            mDatabase.delete(DBHelper.TABLE_NAME, DBHelper.COLUMN_NEWS_ID + " = ?",
                    new String[]{story.getId() + ""});
    }
}
