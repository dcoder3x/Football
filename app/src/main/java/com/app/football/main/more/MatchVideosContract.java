package com.app.football.main.more;

import com.app.football.BaseView;
import com.app.football.model.robot_data.PlayTab;
import com.app.football.model.robot_data.VideosTitleAndThumb;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/9.
 *
 */

interface MatchVideosContract {
    interface Presenter {
        void fetchMatches();
        void findTab();
        void findVideoUrl(String url);
    }
    interface View {
        void showMatches(ArrayList<VideosTitleAndThumb> list);
    }
    interface View2 extends BaseView {
        void showTab(PlayTab tab);
        void showVideo(String url);
    }
}
