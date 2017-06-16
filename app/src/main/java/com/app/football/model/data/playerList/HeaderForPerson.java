package com.app.football.model.data.playerList;

/**
 * Created by Administrator on 2017/3/30.
 *
 */

public class HeaderForPerson implements BaseForPerson {

    private String[] mStr = new String[3];

    public String[] getStr() {
        return mStr;
    }

    public void setStr(int i, String str) {
        mStr[i] = str;
    }
}
