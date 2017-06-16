package com.app.football.model.data.integral;

/**
 * Created by Administrator on 2017/3/26.
 */

public abstract class AbstractItemRegular implements BaseIntegralModel {
    private String mRank;
    private String mTeamLogo;
    private String mTeamName;
    private String mMatchesTotal;
    private String mMatchesWon;
    private String mMatchesDraw;
    private String mMatchesLost;
    private String mGoalsPro;
    private String mGoalsAgainst;
    private String mPoints;

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

    public String getMatchesTotal() {
        return mMatchesTotal;
    }

    public void setMatchesTotal(String matchesTotal) {
        mMatchesTotal = matchesTotal;
    }

    public String getMatchesWon() {
        return mMatchesWon;
    }

    public void setMatchesWon(String matchesWon) {
        mMatchesWon = matchesWon;
    }

    public String getMatchesDraw() {
        return mMatchesDraw;
    }

    public void setMatchesDraw(String matchesDraw) {
        mMatchesDraw = matchesDraw;
    }

    public String getMatchesLost() {
        return mMatchesLost;
    }

    public void setMatchesLost(String matchesLost) {
        mMatchesLost = matchesLost;
    }

    public String getGoalsPro() {
        return mGoalsPro;
    }

    public void setGoalsPro(String goalsPro) {
        mGoalsPro = goalsPro;
    }

    public String getGoalsAgainst() {
        return mGoalsAgainst;
    }

    public void setGoalsAgainst(String goalsAgainst) {
        mGoalsAgainst = goalsAgainst;
    }

    public String getPoints() {
        return mPoints;
    }

    public void setPoints(String points) {
        mPoints = points;
    }
}
