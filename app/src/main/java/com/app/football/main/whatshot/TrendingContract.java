package com.app.football.main.whatshot;


import com.app.football.model.TrendingModel;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/3/10.
 *
 */

interface TrendingContract {

    interface Presenter {
        void load(final byte flag);
    }

    interface View {
        void sendMsg(final byte flag, ArrayList<TrendingModel> all, ArrayList<TrendingModel> slider);
        void showView(final byte flag);
    }
}
