package com.app.football.model.data.integral;

/**
 * Created by Administrator on 2017/3/27.
 */

public abstract class AbstractItemTitle implements BaseIntegralModel {
    private String mTitle;

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }
}
