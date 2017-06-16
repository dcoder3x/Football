package com.app.football.main.sort.data.integral;

import android.os.AsyncTask;
import android.util.Log;

import com.app.football.model.data.integral.BaseIntegralModel;
import com.app.football.model.data.integral.IntegralManager;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/3/26.
 *
 */

class IntegralPresenter implements IntegralContract.Presenter {

    private IntegralContract.View mView;
    private String mUrl;
    private ArrayList<BaseIntegralModel> mList;

    IntegralPresenter(String url, IntegralContract.View view) {
        mView = view;
        mUrl = url;
        // 初始化mList，防止出现空指针异常
        mList = new ArrayList<>();
    }


    @Override
    public void load() {
        IntegralManager manager = new IntegralManager(mUrl, new IntegralManager.Listener() {

            @Override
            public void success(ArrayList<BaseIntegralModel> list) {
                mList = list;
            }
        });

        new AsyncTask<IntegralManager, Void, Void>() {
            @Override
            protected Void doInBackground(IntegralManager... params) {
                params[0].fetchData();
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                mView.showIntegral(mList);
            }
        }.execute(manager);
    }
}
