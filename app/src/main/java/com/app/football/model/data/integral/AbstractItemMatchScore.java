package com.app.football.model.data.integral;

/**
 * Created by Administrator on 2017/3/27.
 *
 */

public abstract class AbstractItemMatchScore implements BaseIntegralModel {
    private String mStartPlay;
    private String mTeamAName;
    private String mTeamALogo;
    private String mStatus;
    private String mFsA;
    private String mFsB;
    private String mTeamBName;
    private String mTeamBLogo;

    public String getStartPlay() {
        return mStartPlay;
    }

    public void setStartPlay(String startPlay) {
        mStartPlay = startPlay;
    }

    public String getTeamAName() {
        return mTeamAName;
    }

    public void setTeamAName(String teamAName) {
        mTeamAName = teamAName;
    }

    public String getTeamALogo() {
        return mTeamALogo;
    }

    public void setTeamALogo(String teamALogo) {
        mTeamALogo = teamALogo;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

    public String getFsA() {
        return mFsA;
    }

    public void setFsA(String fsA) {
        mFsA = fsA;
    }

    public String getFsB() {
        return mFsB;
    }

    public void setFsB(String fsB) {
        mFsB = fsB;
    }

    public String getTeamBName() {
        return mTeamBName;
    }

    public void setTeamBName(String teamBName) {
        mTeamBName = teamBName;
    }

    public String getTeamBLogo() {
        return mTeamBLogo;
    }

    public void setTeamBLogo(String teamBLogo) {
        mTeamBLogo = teamBLogo;
    }
}
