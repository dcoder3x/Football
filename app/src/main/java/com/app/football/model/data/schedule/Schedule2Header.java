package com.app.football.model.data.schedule;

/**
 * Created by Administrator on 2017/4/1.
 *
 */

public class Schedule2Header implements BaseSchedule2 {
    private String mName;
    private String mUrlPrev;
    private String mUrlCurrent;
    private String mUrlNext;

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getUrlPrev() {
        return mUrlPrev;
    }

    public void setUrlPrev(String urlPrev) {
        mUrlPrev = urlPrev;
    }

    public String getUrlCurrent() {
        return mUrlCurrent;
    }

    public void setUrlCurrent(String urlCurrent) {
        mUrlCurrent = urlCurrent;
    }

    public String getUrlNext() {
        return mUrlNext;
    }

    public void setUrlNext(String urlNext) {
        mUrlNext = urlNext;
    }
}
