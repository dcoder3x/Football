package com.app.football.main.schedule;


import com.app.football.model.ScheduleModel;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/3/13.
 *
 */

interface ScheduleContract {
    interface Presenter {
        void load(final byte tag);
    }

    interface View {
        void sendMsg(final byte tag, ArrayList<ScheduleModel> list);
        void showView(byte tag);
    }
}
