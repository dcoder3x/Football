package com.app.football.main.sort.data.teamList;

import com.app.football.model.data.playerList.RankingType;
import com.app.football.model.data.teamList.BaseForTeam;
import com.app.football.model.data.teamList.TeamManager;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/3/31.
 *
 */

class TeamListPresenter implements TeamListContract.Presenter {

    private TeamListContract.View mView;
    private TeamManager mManager;

    TeamListPresenter(TeamListContract.View view) {
        mView = view;
        mManager = new TeamManager(new TeamManager.Listener() {
            @Override
            public void success1(ArrayList<RankingType> list) {
                mView.showListView(list);
            }

            @Override
            public void success2(int cate, ArrayList<BaseForTeam> list) {
                if (cate == TeamListFragment.WHAT2) {
                    mView.showRecyclerView(list);
                }
                if (cate == TeamListFragment.WHAT3) {
                    mView.updateRecyclerView();
                }
            }
        });
    }

    @Override
    public void loadRankingType(String url) {
        mManager.fetchRankingType(url);
    }

    @Override
    public void loadOther(int cate, String url) {
        mManager.fetchDetail(cate, url);
    }
}
