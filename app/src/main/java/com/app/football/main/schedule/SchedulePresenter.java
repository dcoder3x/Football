package com.app.football.main.schedule;

import com.app.football.model.ScheduleManager;
import com.app.football.model.ScheduleModel;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/3/13.
 *
 */

class SchedulePresenter implements ScheduleContract.Presenter {

    private ScheduleContract.View mView;
    private ScheduleManager mManager;

    SchedulePresenter(ScheduleContract.View view) {
        mManager = new ScheduleManager();
        mView = view;
    }

    @Override
    public void load(final byte tag) {

        mManager.refresh(tag, new ScheduleManager.Listener() {
            @Override
            public void loadedSuccess(ArrayList<ScheduleModel> list) {
                mView.sendMsg(tag, list);
            }
        });
    }
}
