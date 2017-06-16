package com.app.football.main.sort.data.playerList;

import com.app.football.model.data.playerList.BaseForPerson;
import com.app.football.model.data.playerList.PlayerManager;
import com.app.football.model.data.playerList.RankingType;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/3/30.
 *
 */

class PlayerListPresenter implements PlayerListContract.Presenter {

    private PlayerListContract.View mView;
    private PlayerManager mManager;

    PlayerListPresenter(PlayerListContract.View view) {
        mView = view;
        mManager = new PlayerManager(new PlayerManager.Listener() {
            @Override
            public void success1(ArrayList<RankingType> list) {
                mView.sendListViewMsg(list);
            }

            @Override
            public void success2(int cate, ArrayList<BaseForPerson> list) {
                if (cate == PlayerListFragment.WHAT2) {
                    mView.sendRecyclerViewMsg(list);
                }
                if (cate == PlayerListFragment.WHAT3) {
                    mView.sendUpdateRecyclerViewMsg();
                }
            }

        });
    }

    // 初始化加载排名类型
    @Override
    public void loadRankingType(String url) {
        mManager.fetchRankingType(url);
    }

    // 加载射手榜，助攻榜....
    public void loadOther(int cate, String url) {
        mManager.fetchDetail(cate, url);
    }
}
