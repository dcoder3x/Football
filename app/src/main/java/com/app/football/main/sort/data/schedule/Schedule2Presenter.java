package com.app.football.main.sort.data.schedule;

import com.app.football.model.data.schedule.BaseSchedule2;
import com.app.football.model.data.schedule.Schedule2Manager;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/1.
 *
 */

class Schedule2Presenter implements Schedule2Contract.Presenter {

    private Schedule2Contract.View mView;
    private Schedule2Manager mManager;

    Schedule2Presenter(Schedule2Contract.View view) {
        mView = view;
        mManager = new Schedule2Manager();
    }

    @Override
    public void load(final String url, final int what) {
        mManager.fetchData(url, new Schedule2Manager.Listener() {

            @Override
            public void success(ArrayList<BaseSchedule2> list) {
                if (what == Schedule2Fragment.WHAT0) {
                    mView.sendCreateAdapterMsg(list);
                }
                if (what == Schedule2Fragment.WHAT1) {
                    mView.sendUpdateAdapterMsg();
                }
            }
        });
    }
}
