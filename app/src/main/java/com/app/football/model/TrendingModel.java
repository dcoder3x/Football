package com.app.football.model;

/**
 * Created by Administrator on 2017/3/8.
 *
 */

/**
 * whats hot fragment data model.
 */
public class TrendingModel {
    private int mId;
    private String mTitle;
    private String mDescription;
    private String mThumb;
    private boolean mTop;
    private String mUrl;


    public TrendingModel() {

    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDescription() {
        if (mDescription.equalsIgnoreCase("null")) {
            return "";
        }
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }


    public String getThumb() {
        return mThumb;
    }

    public void setThumb(String thumb) {
        mThumb = thumb;
    }

    public boolean isTop() {
        return mTop;
    }

    public void setTop(boolean top) {
        mTop = top;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

}
