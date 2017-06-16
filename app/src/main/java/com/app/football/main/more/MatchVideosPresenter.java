package com.app.football.main.more;

import android.os.AsyncTask;

import com.app.football.model.robot_data.PlayTab;
import com.app.football.model.robot_data.Spider;
import com.app.football.model.robot_data.VideosTitleAndThumb;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/9.
 *
 */

class MatchVideosPresenter implements MatchVideosContract.Presenter {

    private MatchVideosContract.View mView;
    private MatchVideosContract.View2 mView2;
    private String mUrl;
    private Spider mSpider = new Spider();

    MatchVideosPresenter(MatchVideosContract.View view) {
        mView = view;
    }

    MatchVideosPresenter(MatchVideosContract.View2 view, String url) {
        mView2 = view;
        mUrl = url;
    }

    @Override
    public void fetchMatches() {
        new AsyncTask<Void, Void, ArrayList<VideosTitleAndThumb>>() {
            @Override
            protected ArrayList<VideosTitleAndThumb> doInBackground(Void... params) {
                ArrayList<VideosTitleAndThumb> list = null;
                try {
                    list = mSpider.parseHome();
                } catch (IOException e) {
                    // TODO
                    e.printStackTrace();
                }
                return list;
            }

            @Override
            protected void onPostExecute(ArrayList<VideosTitleAndThumb> fullMatchesAndShowses) {
                mView.showMatches(fullMatchesAndShowses);
            }
        }.execute();
    }

    @Override
    public void findTab() {
        new AsyncTask<Void, Void, PlayTab>() {
            @Override
            protected PlayTab doInBackground(Void... params) {
                PlayTab tab = new PlayTab();
                try {
                    tab = mSpider.findTab(mUrl);
                } catch (IOException e) {
                    // TODO
                    e.printStackTrace();
                }
                return tab;
            }

            @Override
            protected void onPostExecute(PlayTab playTab) {
                mView2.showTab(playTab);
            }
        }.execute();
    }

    @Override
    public void findVideoUrl(final String url) {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String videoUrl = null;
                try {
                    videoUrl = mSpider.findVideoUrl(url);
                } catch (IOException e) {
                    // TODO
                    e.printStackTrace();
                }
                return videoUrl;
            }

            @Override
            protected void onPostExecute(String s) {
                mView2.showVideo(s);
            }
        }.execute();
    }

}
