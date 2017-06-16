package com.app.football.main.whatshot;


import android.support.annotation.NonNull;

import com.app.football.model.TrendingManager;
import com.app.football.model.TrendingModel;
import java.util.ArrayList;

/**
 * Created by Administrator on 2017/3/10.
 *
 */

class TrendingPresenter implements TrendingContract.Presenter {

    //public static final String TAG = "TrendingPresenter";
    private final TrendingContract.View mView;
    private TrendingManager mManager;
    TrendingPresenter(@NonNull TrendingContract.View view) {
        mView = view;
        mManager = new TrendingManager();
    }
    @Override
    public void load(final byte flag) {
        mManager.load(flag, new TrendingManager.Listener() {
            @Override
            public void loaded(ArrayList<TrendingModel> all, ArrayList<TrendingModel> slider) {
                    mView.sendMsg(flag, all, slider);
            }
        });
    }
}
