package com.app.football.main.whatshot.detail;

import android.os.Bundle;

import com.app.football.BaseView;

/**
 * Created by Administrator on 2017/3/17.
 *
 */

class DetailContract {
    interface Presenter {
        void init(String url);
    }
    interface View extends BaseView {
        void showWeb(String html);
    }
}
