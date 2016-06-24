package com.demo.kidd.zhihudaily;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by niuwa on 2016/6/21.
 */
public class Constants {

    public static final class Strings{

    }

    public static final class Dates{
        public static final SimpleDateFormat simpleDateFormat =
                new SimpleDateFormat("yyyyMMdd", Locale.CHINA);
        public static final SimpleDateFormat cnDateFormat =
                new SimpleDateFormat("MM月dd日", Locale.CHINA);
    }

    public static final class BundleKeys{
        public static final String DATE = "date";
        public static final String IS_SINGLE = "single?";
        public static final String IS_FIRST_PAGE = "first_page?";
    }
}
