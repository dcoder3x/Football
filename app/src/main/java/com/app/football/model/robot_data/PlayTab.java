package com.app.football.model.robot_data;

/**
 * Created by Administrator on 2017/4/10.
 */

public class PlayTab {
    private String mVideoUrl;
    private String[] mTabText;
    private String[] mTabUrl;

    public String getVideoUrl() {
        return mVideoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        mVideoUrl = videoUrl;
    }

    public String[] getTabText() {
        return mTabText;
    }

    public void setTabText(String[] tabText) {
        mTabText = tabText;
    }

    public String[] getTabUrl() {
        return mTabUrl;
    }

    public void setTabUrl(String[] tabUrl) {
        mTabUrl = tabUrl;
    }
}
