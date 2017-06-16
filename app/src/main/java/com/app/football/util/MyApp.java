package com.app.football.util;

import android.app.Application;
import android.content.Context;
import android.graphics.Color;

import com.sohu.cyan.android.sdk.api.Config;
import com.sohu.cyan.android.sdk.api.CyanSdk;
import com.sohu.cyan.android.sdk.exception.CyanException;


/**
 * Created by Administrator on 2017/3/10.
 * 不能使用单例模式，系统要调用构造方法。只能通过static方式保存context了。
 */

public class MyApp extends Application {

    public static Context sContext;

    public MyApp() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();

        // 畅言sdk注册
        initCySdk();
    }

    private void initCySdk() {
        Config config = new Config();
        // 缩进评论显示
        config.ui.style = "indent";
        config.ui.depth = 1;
        config.ui.sub_size = 20;
        config.ui.toolbar_bg = Color.WHITE;

        config.comment.showScore = false;
        config.comment.uploadFiles = true;
        config.comment.useFace = true;
        config.comment.emoji = true;
        // 如果评论框背景白色导致字体看不见
        config.ui.edit_cmt_bg = Color.GRAY;

        try {
            CyanSdk.register(this, "cysVsNcbq", "64de7bddc6c29f1e49bdefd220b146fe", "https://test.com", config);
        } catch (CyanException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
