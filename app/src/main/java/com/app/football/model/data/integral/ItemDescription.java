package com.app.football.model.data.integral;

/**
 * Created by Administrator on 2017/3/26.
 * 对于某个积分榜的说明
 */

public class ItemDescription implements BaseIntegralModel {
    private String mDescription;

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }
}
