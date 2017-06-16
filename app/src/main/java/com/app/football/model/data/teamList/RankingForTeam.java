package com.app.football.model.data.teamList;

/**
 * Created by Administrator on 2017/3/31.
 *
 */

public class RankingForTeam implements BaseForTeam {

    private boolean mTop;
    private boolean mEnd;

    private String mRank;
    private String mTeamLogo;
    private String mTeamName;
    private String mCount;

    public String getRank() {
        return mRank;
    }

    public void setRank(String rank) {
        mRank = rank;
    }

    public String getTeamLogo() {
        return mTeamLogo;
    }

    public void setTeamLogo(String teamLogo) {
        mTeamLogo = teamLogo;
    }

    public String getTeamName() {
        return mTeamName;
    }

    public void setTeamName(String teamName) {
        mTeamName = teamName;
    }

    public String getCount() {
        return mCount;
    }

    public void setCount(String count) {
        mCount = count;
    }

    public boolean isTop() {
        return mTop;
    }

    public void setTop(boolean top) {
        mTop = top;
    }

    public boolean isEnd() {
        return mEnd;
    }

    public void setEnd(boolean end) {
        mEnd = end;
    }
}
