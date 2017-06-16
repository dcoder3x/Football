package com.app.football;


import com.app.football.main.whatshot.TrendingFragment;
import com.app.football.model.TrendingManager;
import com.app.football.model.TrendingModel;
import com.app.football.model.robot_data.PlayTab;
import com.app.football.model.robot_data.Spider;
import com.app.football.model.robot_data.VideosTitleAndThumb;
import com.app.football.util.MyUtil;

import org.junit.Test;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {

        assertEquals(4, 2 + 2);

    }

    /**
     * 测试
     */
    @Test
    public void test1() {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.add(GregorianCalendar.HOUR_OF_DAY, -10);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-ddHH");
        System.out.println(format.format(calendar.getTime()));
    }

    @Test
    public void test2() {
        System.out.println(MyUtil.UTCToCST("2017-04-01 11:30:00"));
    }

    @Test
    public void test3() {
        Spider spider = new Spider();
        ArrayList<VideosTitleAndThumb> list = new ArrayList<>();
        try {
            list = spider.parseHome();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (VideosTitleAndThumb item : list) {
            System.out.print(item.getTitle() + ":");
            System.out.print(item.getThumbUrl() + ":");
            System.out.println(item.getUrl());
        }
    }

    @Test
    public void test4() {
        Spider spider = new Spider();
        PlayTab tab = new PlayTab();
        try {
            tab = spider.findTab("http://www.fullmatchesandshows.com/2017/04/09/sampdoria-vs-fiorentina-highlights-2/");
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (String s : tab.getTabText()) {
            System.out.print(s + "----");
        }
        System.out.println("*****************");
        for (String s : tab.getTabUrl()) {
            System.out.print(s + "----");
        }
        System.out.println("*****************");
        System.out.println(tab.getVideoUrl());
    }

    @Test
    public void test5() {
        Spider spider = new Spider();
        try {
            System.out.println(spider.findVideoUrl("http://www.fullmatchesandshows.com/2017/04/09/sampdoria-vs-fiorentina-highlights-2/"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testTrendingManager() throws Exception {
        TrendingManager tm = new TrendingManager();
        TrendingManager.Listener listener = new TrendingManager.Listener() {
            @Override
            public void loaded(ArrayList<TrendingModel> all, ArrayList<TrendingModel> slider) {
                System.out.println("news count:" + all.size() + "top news count:" + slider.size());
            }
        };
        // load方法里的okhttp用到了context,无法进行单元测试
        tm.load(TrendingFragment.FLAG1, listener);
        tm.load(TrendingFragment.FLAG2, listener);
        tm.load(TrendingFragment.FLAG3, listener);
        // junit不支持多线程,load里开启了子线程
        Thread.sleep(5000);
    }
}