package com.app.football.model.data.schedule;

import com.app.football.model.OkHttp;
import com.app.football.util.MyUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/1.
 *
 */

public class Schedule2Manager {

    private ArrayList<BaseSchedule2> mList;
    private OkHttp mOkHttp;

    public interface Listener {
        void success(ArrayList<BaseSchedule2> list);
    }

    public Schedule2Manager() {
        mList = new ArrayList<>();
        mOkHttp = new OkHttp();
    }

    public void fetchData(final String url, final Listener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String str = mOkHttp.getData(url);
                    parseJson(str);
                    listener.success(mList);
                } catch (IOException e) {
                    // TODO
                    e.printStackTrace();
                } catch (JSONException e) {
                    // TODO
                }
            }
        }).start();
    }

    private void parseJson(String str) throws JSONException {
        mList.clear();
        JSONObject object = new JSONObject(str);
        JSONObject content = object.getJSONObject("content");
        JSONArray rounds = content.getJSONArray("rounds");
        // 遍历找到当前轮数
        for (int i = 0; i < rounds.length(); i++) {
            JSONObject obj = rounds.getJSONObject(i);
            boolean current = false;
            try {
                current = obj.getBoolean("current");
            } catch (JSONException e) {
                // do nothing
            }
            if (current) {
                Schedule2Header header = new Schedule2Header();
                header.setName(obj.getString("name"));
                header.setUrlCurrent(obj.getString("url"));
                // 第一轮时没有上一轮
                if (i > 0) {
                    JSONObject obj1 = rounds.getJSONObject(i - 1);
                    header.setUrlPrev(obj1.getString("url"));
                }
                // 最后一轮没有下一轮
                if (i < rounds.length() - 1) {
                    JSONObject obj2 = rounds.getJSONObject(i + 1);
                    header.setUrlNext(obj2.getString("url"));
                }
                mList.add(header);
                break;
            }
        }
        // 遍历赛程
        JSONArray matches = content.getJSONArray("matches");
        for (int i = 0; i < matches.length(); i++) {
            JSONObject obj = matches.getJSONObject(i);
            Schedule2Item item = new Schedule2Item();
            item.setStatus(obj.getString("status"));
            item.setTeamAName(obj.getString("team_A_name"));
            item.setTeamALogo(obj.getString("team_A_logo"));
            item.setFsA(obj.getString("fs_A"));
            item.setFsB(obj.getString("fs_B"));
            item.setTeamBLogo(obj.getString("team_B_logo"));
            item.setTeamBName(obj.getString("team_B_name"));
            item.setStartPlay(MyUtil.UTCToCST(obj.getString("start_play")));
            mList.add(item);
        }
    }

}
