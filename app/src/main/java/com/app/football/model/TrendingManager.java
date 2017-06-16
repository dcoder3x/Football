package com.app.football.model;

import android.util.Log;

import com.app.football.main.whatshot.TrendingFragment;
import com.app.football.util.MyUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;


/**
 * Created by Administrator on 2017/3/9.
 * http://api.dongqiudi.com/app/tabs/android/1.json?mark=gif
 * 下拉刷新
 * 上拉加载
 */

public class TrendingManager {

    private static final String TAG = "TrendingManager";

    private static final String TRENDING_URL = "http://api.dongqiudi.com/app/tabs/android/1.json?mark=gif";

    private static String REFRESH_URL = "";

    private static String LOAD_MORE_URL = "";

    private final OkHttp mOkHttp;
    private final ArrayList<TrendingModel> slider = new ArrayList<>();
    private final ArrayList<TrendingModel> all = new ArrayList<>();
    public interface Listener {
        void loaded(ArrayList<TrendingModel> all, ArrayList<TrendingModel> slider);
    }

    public TrendingManager() {
        mOkHttp = new OkHttp();
    }


    public void load(final byte flag, final Listener listener) {
        Runnable run = new Runnable() {
            @Override
            public void run() {
                String str;
                switch (flag) {
                    case TrendingFragment.FLAG1:
                        try {
                            str = mOkHttp.getData(TRENDING_URL);
                            parseJson(str, flag);
                        } catch (IOException e) {
                            Log.d(TAG, e.toString());
                        }
                        break;
                    case TrendingFragment.FLAG2:
                        try {
                            if (REFRESH_URL.isEmpty()) {
                                str = mOkHttp.getData(TRENDING_URL);
                                parseJson(str, flag);
                            } else {
                                str = mOkHttp.getData(REFRESH_URL);
                                parseJson(str, flag);
                            }
                        } catch (IOException e) {
                            Log.d(TAG, e.toString());
                        }
                        break;
                    case TrendingFragment.FLAG3:
                        try {
                            str = mOkHttp.getData(LOAD_MORE_URL);
                            parseJson(str, flag);
                        } catch (IOException e) {
                            Log.d(TAG, e.toString());
                        }
                        break;
                    default:
                        break;
                }
                listener.loaded(all, slider);
            }
        };
        new Thread(run).start();
    }

    // parse json
    private void parseJson(String str, byte flag) {
        int id;
        String title;
        String thumb;
        String desc;
        String url;
        JSONArray array = null;
        boolean top;
        try {
            JSONObject json = new JSONObject(str);
            if (flag == TrendingFragment.FLAG1 || flag == TrendingFragment.FLAG2) {
                all.clear();
                REFRESH_URL = json.getString("prev");
                LOAD_MORE_URL = json.getString("next");
                parseForSlider(json);
            }
            if (flag == TrendingFragment.FLAG3) {
                LOAD_MORE_URL = json.getString("next");
            }
            array = json.getJSONArray("articles");
        } catch (JSONException e) {
            Log.d(TAG, e + "");
        }
        if (array != null) {
            for (int i = 0; i < array.length(); i++) {
                try {
                    JSONObject obj = array.getJSONObject(i);
                    id = obj.getInt("id");
                    title = obj.getString("title");
                    thumb = obj.getString("thumb");
                    desc = obj.getString("description");
                    top = obj.getBoolean("top");
                    url = obj.getString("url");
                    // 避免thumb没有值在使用Picasso时使应用崩溃
                    if (!title.isEmpty() && !thumb.isEmpty()) {
                        TrendingModel model = new TrendingModel();
                        model.setId(id);
                        model.setTitle(title);
                        model.setDescription(desc);
                        model.setThumb(thumb);
                        model.setTop(top);
                        model.setUrl(url);
                        if (!top) {
                            all.add(model);
                        }
                    }
                } catch (JSONException e) {
                    Log.d(TAG, e + "");
                }
            }
        }

    }

    // 单独添加slider
    private void parseForSlider(JSONObject json) throws JSONException {
        slider.clear();
        JSONArray array = json.getJSONArray("recommend");
        int id;
        String title;
        String thumb;
        String url;
        if (array != null) {
            for (int i = 0; i < array.length(); i++) {
                try {
                    JSONObject obj = array.getJSONObject(i);
                    id = obj.getInt("id");
                    title = obj.getString("title");
                    thumb = obj.getString("thumb");
                    url = obj.getString("url");
                    // 避免thumb没有值在使用Picasso时使应用崩溃
                    if (!title.isEmpty() && !thumb.isEmpty()) {
                        TrendingModel model = new TrendingModel();
                        model.setId(id);
                        model.setTitle(title);
                        model.setThumb(thumb);
                        model.setUrl(url);
                        model.setThumb(MyUtil.adjustImageSize(model.getThumb()));
                        slider.add(model);
                    }
                } catch (JSONException e) {
                    Log.d(TAG, e + "");
                }
            }
        }
    }
}
