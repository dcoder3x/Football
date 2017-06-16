package com.app.football.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.IntDef;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import okhttp3.Cache;

/**
 * Created by Administrator on 2017/3/12.
 *
 */

public class MyUtil {

    public static int sScreenPPI = 320;
    public static int sScreenWidthPixel = 720;
    public static Cache sCache = null;
    // matches videos list thumb size
    public static int sWidthPx = sScreenWidthPixel - (12 * sScreenPPI / 160);
    public static int sHeightPx = 160 * sScreenPPI / 160;

    /**
     *
     * @param url default image url.
     * @return String:suitable size image url
     */
    public static String adjustImageSize(String url) {
        String size = sScreenWidthPixel + "x" + sScreenPPI;
        return url.replace("180x135", size);
    }

    public static boolean isNetworkConnected() {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) MyApp.sContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
        if (mNetworkInfo != null) {
            return mNetworkInfo.isAvailable();
        } else {
            return false;
        }

    }

    // 当前时间的比赛，链接需要转变为utc时间并向前两个小时以便获得正在进行的赛程
    public static String today() {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.add(GregorianCalendar.HOUR_OF_DAY, -10);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-ddHH");
        return format.format(calendar.getTime());
    }

    // utc to cst 2017-04-01 11:30:00 > 04-01 19:30:00
    public static String UTCToCST(String utc) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        GregorianCalendar calendar = new GregorianCalendar();
        try {
            calendar.setTime(format.parse(utc));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendar.add(GregorianCalendar.HOUR_OF_DAY, 8);
        format.applyPattern("MM-dd HH:mm:ss");
        return format.format(calendar.getTime());
    }
}
