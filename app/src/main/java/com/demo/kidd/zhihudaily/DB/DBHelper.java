package com.demo.kidd.zhihudaily.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by niuwa on 2016/6/28.
 */
public class DBHelper extends SQLiteOpenHelper{

    private static DBHelper sDBHelper;

    public static final String DB_NAME = "ZhihuDaily.db";
    public static final String TABLE_NAME = "favorite_news";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NEWS_ID = "news_id";
    public static final String COLUMN_TITLE = "news_title";
    public static final String COLUMN_IMAGE = "news_image";

    public static final String CREATE_TABLE_NEWS = "create table " + TABLE_NAME + "(" +
            COLUMN_ID + " integer primary key autoincrement," +
            COLUMN_NEWS_ID + " integer," +
            COLUMN_TITLE + " text," +
            COLUMN_IMAGE + " text)";

    private DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
    }

    public static DBHelper getInstance(Context context){
        if (sDBHelper == null){
            synchronized (DBHelper.class){
                sDBHelper = new DBHelper(context, DB_NAME, null, 1);
            }
        }
        return sDBHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_NEWS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists" + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
