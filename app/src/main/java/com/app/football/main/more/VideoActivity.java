package com.app.football.main.more;

import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.content.res.ConfigurationHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.VideoView;

import com.app.football.R;
import com.app.football.model.robot_data.PlayTab;

public class VideoActivity extends AppCompatActivity implements MatchVideosContract.View2 {

    private MatchVideosPresenter mPresenter;
    private TabLayout mTabLayout;
    private VideoView mVideoView;
    private MediaController mController;
    private ProgressBar mProgressBar;
    private boolean isFirst = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        mTabLayout = (TabLayout) findViewById(R.id.tab);
        mVideoView = (VideoView) findViewById(R.id.video_view);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);

        if (mController == null) {
            mController = new MediaController(this);
        }
        mVideoView.setMediaController(mController);
        showProgressBar();
        mPresenter = new MatchVideosPresenter(this, getIntent().getExtras().getString("url"));
        mPresenter.findTab();
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                    if (!isFirst) {
                        if (mVideoView.isPlaying()) {
                            mVideoView.stopPlayback();
                        }
                        showProgressBar();
                        mPresenter.findVideoUrl((String) tab.getTag());
                    }
                    isFirst = false;
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }


    @Override
    public void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void closeProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showTab(PlayTab tab) {
        String[] tabText = tab.getTabText();
        String[] tabUrl = tab.getTabUrl();
        for (int i = 0; i < tabText.length; i++) {
            if (tabText[i] != null && tabUrl[i] != null)
                mTabLayout.addTab(mTabLayout.newTab().setText(tabText[i]).setTag(tabUrl[i]));
        }
        showVideo(tab.getVideoUrl());
    }

    @Override
    public void showVideo(String url) {
        if (url != null && !url.isEmpty()) {
            mVideoView.setVideoURI(Uri.parse(url));
            mVideoView.start();
        } else {
            // TODO
            Toast.makeText(this, "好像没有相关视频", Toast.LENGTH_SHORT).show();
        }
        closeProgressBar();
    }
}
