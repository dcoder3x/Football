package com.app.football.model;


import android.util.Log;

import com.app.football.main.schedule.ScheduleFragment;
import com.app.football.util.MyUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;


/**
 * Created by Administrator on 2017/3/13.
 * https://api.dongqiudi.com/data/tab/important?start=2017-03-1116%3A00%3A00&init=1
 */

public class ScheduleManager {

    private static final String TAG = "Schedule2Manager";
    // 上一次ArrayList大小
    public static int sSize = 0;
    private OkHttp mOkHttp;
    private static String sPreUrl = "";
    private static String sUrl = "";
    private static String sNextUrl = "";
    private ArrayList<ScheduleModel> mList;


    public interface Listener {
        void loadedSuccess(ArrayList<ScheduleModel> list);
    }

    public ScheduleManager() {
        mOkHttp = new OkHttp();
        mList = new ArrayList<>();
    }

    //
    public void refresh(final byte tag, final Listener listener) {
        Runnable run = new Runnable() {
            @Override
            public void run() {
                try {
                    String str = null;
                    switch (tag) {
                        case ScheduleFragment.TAG0:
                            //
                            sUrl = "https://api.dongqiudi.com/data/tab/important?start=" + MyUtil.today() + "%3A00%3A00&init=1";
                            str = mOkHttp.getData(sUrl);
                            break;
                        case ScheduleFragment.TAG1:
                            mList.clear();
                            sUrl = "https://api.dongqiudi.com/data/tab/important?start=" + MyUtil.today() + "%3A00%3A00&init=1";
                            str = mOkHttp.getData(sUrl);
                            break;
                        case ScheduleFragment.TAG2:
                            mList.clear();
                            str = mOkHttp.getData(sPreUrl);
                            break;
                        case ScheduleFragment.TAG3:
                            sSize = mList.size();
                            str = mOkHttp.getData(sNextUrl);
                    }
                    parse(str);
                } catch (IOException e) {
                    Log.d("get json", "error");
                }
                // notify presenter.
                Log.d("count", mList.size() + "");
                listener.loadedSuccess(mList);
            }
        };
        new Thread(run).start();
    }

    // parse json
    private void parse(String str) {
        String status;
        String relate_type;
        try {
            JSONObject object = new JSONObject(str);
            sPreUrl = "https://api.dongqiudi.com/data/tab/important?start=" + object.getString("prevDate").substring(0, 10) + "16%3A00%3A00&init=1";
            sNextUrl = "https://api.dongqiudi.com/data/tab/important?start=" + object.getString("nextDate").substring(0, 10) + "16%3A00%3A00&init=1";
            JSONArray array = object.getJSONArray("list");
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                ScheduleModel model = new ScheduleModel();
                relate_type = obj.getString("relate_type");
                if (relate_type.equals("match")) {
                    model.setTeam_A_name(obj.getString("team_A_name"));
                    model.setTeam_A_logo(obj.getString("team_A_logo"));
                    model.setTeam_B_name(obj.getString("team_B_name"));
                    model.setTeam_B_logo(obj.getString("team_B_logo"));
                    model.setStart_play(MyUtil.UTCToCST(obj.getString("start_play")));
                    status = obj.getString("status");
                    model.setStatus(status);
                    if (status.equals("Playing") || status.equals("Played")) {
                        model.setFs_A(Byte.parseByte(obj.getString("fs_A")));
                        model.setFs_B(Byte.parseByte(obj.getString("fs_B")));
                        model.setMinute(Byte.parseByte(obj.getString("minute")));
                    }
                    mList.add(model);
                }
            }
        } catch (JSONException e) {
            Log.d(TAG, e + "");
        }

    }

}
