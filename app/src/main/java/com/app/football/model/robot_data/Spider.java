package com.app.football.model.robot_data;

import android.util.Log;

import com.app.football.util.MyUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/9.
 *
 */

public class Spider {

    private static final String HOME_URL = "https://www.fullmatchesandshows.com/";


    private ArrayList<VideosTitleAndThumb> mList = new ArrayList<>();

    // 爬取full matches and shows 首页的16个赛事条目
    public ArrayList<VideosTitleAndThumb> parseHome() throws IOException {

        Document home = Jsoup.connect(HOME_URL).get();
        Elements tagAS = home.select("div.td-module-image div.td-module-thumb a");
        Elements tagImgS = tagAS.select("noscript img[sizes]");
        // a标签下的img是一一对应的
        if (tagAS.size() == tagImgS.size()) {
            for (int i =0; i < tagAS.size(); i++) {
                VideosTitleAndThumb item = new VideosTitleAndThumb();
                Element tagA = tagAS.get(i);
                item.setTitle(tagA.attr("title"));
                item.setUrl(tagA.attr("href"));
                String str = suitableThumbUrl(tagImgS.get(i).attr("src"));
                item.setThumbUrl(str);
                mList.add(item);
            }
        }
        return mList;
    }

    private String suitableThumbUrl(String str) {
        String resize = MyUtil.sWidthPx + "%2C" + MyUtil.sHeightPx;
        return str.replace("324%2C160", resize);
    }

    // 爬取视频播放页tab
    public PlayTab findTab(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
        Elements tabTexts = doc.select("#acp_paging_menu div.acp_title");

        PlayTab tab = new PlayTab();
        String[] str1 = new String[5];
        String[] str2 = new String[5];

        //
        for (int i = 0; i < tabTexts.size(); i++) {
            str1[i] = tabTexts.get(i).text();
            if (i == 0) {
                str2[i] = url;
            } else {
                str2[i] = url + "/" + (i + 1);
            }
            // 最多只处理5个
            if (i == 4)
                break;
        }

        // 默认的视频链接
        Elements videoInfos = doc.select("script[data-config]");
        if (!videoInfos.isEmpty()) {
            tab.setVideoUrl("https://cdn.video.playwire.com/21772/videos/" + videoInfos.get(0).attr("data-config").split("/")[6] + "/video-sd.m3u8");
        } else {
            videoInfos = doc.select("iframe[data-lazy-src]");
            if (!videoInfos.isEmpty()) {
                String vId = videoInfos.get(0).attr("data-lazy-src").split("/")[4];
                tab.setVideoUrl("https://cf-e2.streamablevideo.com/video/mp4/"+ vId + "_1.mp4");
            }
        }

        tab.setTabText(str1);
        tab.setTabUrl(str2);
        return tab;
    }

    // 其它tab video url
    // https://cf-e2.streamablevideo.com/video/mp4/h4ou0_1.mp4
    // https://cdn.video.playwire.com/21772/videos/xxxxxx/video-sd.m3u8
    public String findVideoUrl(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
        // scritp data-config

        // 第一种视频链接类型
        Elements videoInfos = doc.select("script[data-config]");
        if (!videoInfos.isEmpty()) {
            return "https://cdn.video.playwire.com/21772/videos/" + videoInfos.get(0).attr("data-config").split("/")[6] + "/video-sd.m3u8";
        }

        // streamable (iframe[data-lazy-src])
        videoInfos = doc.select("iframe[data-lazy-src]");
        if (!videoInfos.isEmpty()) {
            String vId = videoInfos.get(0).attr("data-lazy-src").split("/")[4];
            Log.d("Spider", vId);
            return "https://cf-e2.streamablevideo.com/video/mp4/"+ vId + "_1.mp4";
        } else {
            Log.d("Spider", "找不到视频链接");
        }


        return null;
    }

}
