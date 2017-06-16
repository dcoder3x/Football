package com.app.football.model.data.playerList;

/**
 * Created by Administrator on 2017/3/30.
 *
 */

public class RankingForPerson implements BaseForPerson {
    private boolean top;
    private boolean end;
    private int mRank;
    private String mPersonLogo;
    private String mPersonName;
    private String mTeamName;
    private int mCount;

    public boolean isTop() {
        return top;
    }

    public void setTop(boolean top) {
        this.top = top;
    }

    public boolean isEnd() {
        return end;
    }

    public void setEnd(boolean end) {
        this.end = end;
    }

    public int getRank() {
        return mRank;
    }

    public void setRank(int rank) {
        mRank = rank;
    }

    public String getPersonLogo() {
        return mPersonLogo;
    }

    public void setPersonLogo(String personLogo) {
        mPersonLogo = personLogo;
    }

    public String getPersonName() {
        return mPersonName;
    }

    public void setPersonName(String personName) {
        mPersonName = personName;
    }

    public String getTeamName() {
        return mTeamName;
    }

    public void setTeamName(String teamName) {
        mTeamName = teamName;
    }

    public int getCount() {
        return mCount;
    }

    public void setCount(int count) {
        mCount = count;
    }
}
