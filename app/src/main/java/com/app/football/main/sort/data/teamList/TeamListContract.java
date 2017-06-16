package com.app.football.main.sort.data.teamList;

import com.app.football.BaseView;
import com.app.football.model.data.playerList.RankingType;
import com.app.football.model.data.teamList.BaseForTeam;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/3/31.
 *
 */

interface TeamListContract {
    interface Presenter {
        void loadRankingType(String url);
        void loadOther(int cate, String url);
    }

    interface View extends BaseView {
        //
        void showListView(ArrayList<RankingType> list);
        void showRecyclerView(ArrayList<BaseForTeam> list);
        void updateRecyclerView();
        void handle(int what);
    }
}
