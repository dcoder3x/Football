package com.app.football.main.sort.data.schedule;


import com.app.football.model.data.schedule.BaseSchedule2;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/1.
 *
 */

interface Schedule2Contract {

    interface Presenter {
        void load(final String url, final int what);
    }
    interface View {
        void sendCreateAdapterMsg(ArrayList<BaseSchedule2> list);
        void sendUpdateAdapterMsg();
        void showView(int what);
    }
}
