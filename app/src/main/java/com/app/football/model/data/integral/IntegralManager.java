package com.app.football.model.data.integral;

import android.util.Log;

import com.app.football.main.sort.data.integral.IntegralAdapter;
import com.app.football.model.OkHttp;
import com.app.football.util.MyUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Administrator on 2017/3/26.
 *
 */

public class IntegralManager {

    private static final String TAG = "IntegralManager";

    private String mUrl;
    private OkHttp mOkHttp;
    private ArrayList<BaseIntegralModel> mList;
    private Listener mListener;

    public interface Listener {
        void success(ArrayList<BaseIntegralModel> list);
    }

    public IntegralManager(String url, Listener listener) {
        mUrl = url;
        mOkHttp = new OkHttp();
        mList = new ArrayList<>();
        mListener = listener;
    }

    public void fetchData() {
        try {
            String str = mOkHttp.getData(mUrl);
            if (str.isEmpty()) {
                // TODO 没有获取到任何数据，无法解析
                Log.d(TAG, "没有获得网络数据");
            } else {
                parseJson(str);
                mListener.success(mList);
            }
        } catch (IOException e) {
            // TODO 获取网络数据出错，无法解析
            Log.d(TAG, e.toString());
        } catch (JSONException e) {
            // TODO 得到的JSON格式有误，无法向下解析
            Log.d(TAG, e.toString());
        }
    }

    private void parseJson(String str) throws JSONException {
        mList.clear();
        JSONObject object = new JSONObject(str);
        JSONObject content = object.getJSONObject("content");

        // parse rounds
        JSONArray rounds = content.getJSONArray("rounds");
        for (int i = 0; i < rounds.length(); i++) {
            JSONObject round = rounds.getJSONObject(i);
            String template = round.getString("template");
            JSONObject obj = round.getJSONObject("content");
            // 一级标题
            String name = obj.getString("name");
            if (!name.isEmpty()) {
                ItemFirstLevelTitle item = new ItemFirstLevelTitle();
                item.setTitle(name);
                mList.add(item);
            }
            switch (template) {
                case "team_point_ranking_aggregate":
                    aggregate(obj);
                    break;
                case "team_point_ranking_group":
                    group(obj);
                    break;
                case "team_point_ranking_regular":
                    regular(obj);
                    break;
                default:
                    // 未知模板,不知道json结构无法解析
                    break;
            }
        }

        // description
        String description = content.getString("description");
        if (!description.isEmpty()) {
            ItemDescription item = new ItemDescription();
            item.setDescription(description);
            mList.add(item);
        }
    }

    // 解析team_point_ranking_regular结构的数据
    private void regular(JSONObject content) throws JSONException {
        // 常规积分榜可能有升级降级item
        int top = content.getInt("top");
        int end = content.getInt("end");

        // 解析常规积分结构的header
        JSONArray header = content.getJSONArray("header");
        if (header != null) {
            mList.add(new ItemHeader());
        }

        // 开始解析item regular
        JSONArray data = content.getJSONArray("data");
        for (int i = 0; i < data.length(); i++) {
            JSONObject obj = data.getJSONObject(i);
            // top regular
            if (i < top) {
                ItemRegularTop item = new ItemRegularTop();
                setItemRegularAll(obj, item);
                continue;
            }
            // end regular
            if (i >= data.length() - end) {
                ItemRegularEnd item = new ItemRegularEnd();
                setItemRegularAll(obj, item);
                continue;
            }
            // other
            ItemRegular item = new ItemRegular();
            setItemRegularAll(obj, item);
        }
    }

    // 设置三种ItemRegular
    private void setItemRegularAll(JSONObject obj, AbstractItemRegular item) throws JSONException {
        item.setRank(obj.getString("rank"));
        item.setTeamLogo(obj.getString("team_logo"));
        item.setTeamName(obj.getString("team_name"));
        item.setMatchesTotal(obj.getString("matches_total"));
        item.setMatchesWon(obj.getString("matches_won"));
        item.setMatchesDraw(obj.getString("matches_draw"));
        item.setMatchesLost(obj.getString("matches_lost"));
        item.setGoalsPro(obj.getString("goals_pro"));
        item.setGoalsAgainst(obj.getString("goals_against"));
        item.setPoints(obj.getString("points"));
        mList.add(item);
    }

    // 解析team_point_ranking_aggregate结构的数据
    private void aggregate(JSONObject content) throws JSONException {

        JSONArray data = content.getJSONArray("data");
        for (int i = 0; i < data.length(); i++) {
            JSONObject obj = data.getJSONObject(i);

            JSONObject total = obj.getJSONObject("total");
            JSONObject match1 = obj.getJSONObject("match1");
            JSONObject match2 = obj.getJSONObject("match2");

            ItemTotalMatchScore item = new ItemTotalMatchScore();
            ItemMatchScore item1 = new ItemMatchScore();
            ItemMatchScore item2 = new ItemMatchScore();

            setItemMatchScore(total, item, IntegralAdapter.ITEM_4);
            setItemMatchScore(match1, item1, IntegralAdapter.ITEM_1);
            setItemMatchScore(match2, item2, IntegralAdapter.ITEM_1);
        }
    }

    // 设置单场及两回合比分
    private void setItemMatchScore(JSONObject obj, AbstractItemMatchScore item, int tag) throws JSONException {

        item.setTeamALogo(obj.getString("team_A_logo"));
        item.setTeamAName(obj.getString("team_A_name"));
        item.setFsA(obj.getString("fs_A"));
        item.setFsB(obj.getString("fs_B"));
        item.setTeamBLogo(obj.getString("team_B_logo"));
        item.setTeamBName(obj.getString("team_B_name"));
        if (tag == IntegralAdapter.ITEM_1) {
            // 2017-04-12 18:45:00格式转变为04-12 18:45,utc转当地时间暂时不管
            String time = MyUtil.UTCToCST(obj.getString("start_play"));
            item.setStartPlay(time);
            // Fixture or Played
            item.setStatus(obj.getString("status"));
        }
        mList.add(item);
    }

    // 解析team_point_ranking_group结构的数据
    private void group(JSONObject content) throws JSONException {
        JSONArray data = content.getJSONArray("data");
        for (int i = 0; i < data.length(); i++) {
            JSONObject obj = data.getJSONObject(i);
            String name = obj.getString("name");
            if (!name.isEmpty()) {
                ItemSecondLevelTitle item = new ItemSecondLevelTitle();
                item.setTitle(name);
                mList.add(item);
            }
            // 跟regular一样
            regular(obj);
        }

    }
}
