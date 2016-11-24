package com.demo.kidd.zhihudaily;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by niuwa on 2016/6/21.
 */
public class Constants {

    public static final class URLs{
        public static String NEWSLIST_LATEST = "http://news-at.zhihu.com/api/4/news/latest";
        public static String NEWSLIST_BEFORE = "http://news-at.zhihu.com/api/4/news/before/";
        public static String STORY_VIEW = "http://daily.zhihu.com/story/";
        public static String NEWSDETAIL = "http://news-at.zhihu.com/api/4/news/";
        public static String DEFAULT_HEAD_IMAGE  = "file:///android_asset/news_detail_header_image.jpg";
    }

    public static final class Dates{
        public static final SimpleDateFormat simpleDateFormat =
                new SimpleDateFormat("yyyyMMdd", Locale.CHINA);
        public static final SimpleDateFormat cnDateFormat =
                new SimpleDateFormat("MM月dd日", Locale.CHINA);
        public static final SimpleDateFormat cnDateFormat_year =
                new SimpleDateFormat("yyyy年MM月dd日", Locale.CHINA);
        public static final Date birthday = new Date(113, 4, 19);
    }

    public static final class BundleKeys{
        public static final String DATE = "date";
        public static final String IS_SINGLE = "single?";
        public static final String IS_FIRST_PAGE = "first_page?";
    }
}
