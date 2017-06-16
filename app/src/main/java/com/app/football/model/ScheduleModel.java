package com.app.football.model;

/**
 * Created by Administrator on 2017/3/13.
 * https://api.dongqiudi.com/data/tab/important?start=2017-03-1116%3A00%3A00&init=1
 * %3A是:。也就是16:00:00。
 */

public class ScheduleModel {

    private String team_A_name;
    private String team_A_logo;
    private String team_B_name;
    private String team_B_logo;
    private String start_play;
    private String status;
    private byte fs_A;
    private byte fs_B;
    private byte minute;



    public String getTeam_A_name() {
        return team_A_name;
    }

    public void setTeam_A_name(String team_A_name) {
        this.team_A_name = team_A_name;
    }

    public String getTeam_A_logo() {
        return team_A_logo;
    }

    public void setTeam_A_logo(String team_A_logo) {
        this.team_A_logo = team_A_logo;
    }

    public String getTeam_B_name() {
        return team_B_name;
    }

    public void setTeam_B_name(String team_B_name) {
        this.team_B_name = team_B_name;
    }

    public String getTeam_B_logo() {
        return team_B_logo;
    }

    public void setTeam_B_logo(String team_B_logo) {
        this.team_B_logo = team_B_logo;
    }

    public String getStart_play() {
        return start_play;
    }

    public void setStart_play(String start_play) {
        this.start_play = start_play;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public byte getFs_A() {
        return fs_A;
    }

    public void setFs_A(byte fs_A) {
        this.fs_A = fs_A;
    }

    public byte getFs_B() {
        return fs_B;
    }

    public void setFs_B(byte fs_B) {
        this.fs_B = fs_B;
    }


    public byte getMinute() {
        return minute;
    }

    public void setMinute(byte minute) {
        this.minute = minute;
    }


}
