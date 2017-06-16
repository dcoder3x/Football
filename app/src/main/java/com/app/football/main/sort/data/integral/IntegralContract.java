package com.app.football.main.sort.data.integral;

import com.app.football.model.data.integral.BaseIntegralModel;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/3/26.
 *
 */

interface IntegralContract {
    interface Presenter {
        void load();
    }
    interface View {
        void showIntegral(ArrayList<BaseIntegralModel> list);
    }
}
