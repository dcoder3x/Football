package com.app.football.main.sort.data.playerList;

import com.app.football.model.data.playerList.BaseForPerson;
import com.app.football.model.data.playerList.RankingType;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/3/30.
 *
 */

interface PlayerListContract {
    interface Presenter {
        void loadRankingType(String url);
        void loadOther(int cate, String url);
    }
    interface View {
        void sendListViewMsg(ArrayList<RankingType> list);
        void sendRecyclerViewMsg(ArrayList<BaseForPerson> list);
        void sendUpdateRecyclerViewMsg();
        void show(int what);
    }
}
