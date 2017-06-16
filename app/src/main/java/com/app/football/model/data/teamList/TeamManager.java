package com.app.football.model.data.teamList;

import com.app.football.model.OkHttp;
import com.app.football.model.data.playerList.RankingType;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Administrator on 2017/3/31.
 */

public class TeamManager {
    private OkHttp mOkHttp;
    private ArrayList<RankingType> mList1;
    private ArrayList<BaseForTeam> mList2;
    private Listener mListener;

    public interface Listener {
        void success1(ArrayList<RankingType> list);
        void success2(int cate, ArrayList<BaseForTeam> list);
    }

    public TeamManager(Listener listener) {
        mListener  = listener;
        mOkHttp = new OkHttp();
        mList1 = new ArrayList<>();
        mList2 = new ArrayList<>();
    }

    public void fetchRankingType(final String url) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String str = mOkHttp.getData(url);
                    parseRankingType(str);
                    mListener.success1(mList1);
                } catch (IOException e) {
                    // TODO
                    e.printStackTrace();
                } catch (JSONException e) {
                    // TODO
                }
            }
        }).start();
    }

    private void parseRankingType(String str) throws JSONException {
        mList1.clear();
        JSONObject obj = new JSONObject(str);
        JSONObject content = obj.getJSONObject("content");
        JSONArray data = content.getJSONArray("data");
        for (int i = 0; i < data.length(); i++) {
            JSONObject object = data.getJSONObject(i);
            RankingType item = new RankingType();
            item.setName(object.getString("name"));
            item.setUrl(object.getString("url"));
            mList1.add(item);
        }

    }

    public void fetchDetail(final int cate, final String url) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String str = mOkHttp.getData(url);
                    parseDetail(str);
                    mListener.success2(cate, mList2);
                } catch (IOException e) {
                    // TODO
                    e.printStackTrace();
                } catch (JSONException e) {
                    // TODO
                }
            }
        }).start();
    }
    private void parseDetail(String str) throws JSONException {
        mList2.clear();
        JSONObject object = new JSONObject(str);
        JSONObject content = object.getJSONObject("content");
        //
        int top = content.getInt("top");
        int end = content.getInt("end");
        //
        JSONArray header = content.getJSONArray("header");
        HeaderForTeam item = new HeaderForTeam();
        item.setString1(header.getString(0));
        item.setString2(header.getString(1));
        mList2.add(item);
        //
        JSONArray data =content.getJSONArray("data");
        for (int i = 0; i < data.length(); i++) {
            RankingForTeam team = new RankingForTeam();
            JSONObject obj = data.getJSONObject(i);
            if (i < top) {
                team.setTop(true);
            }
            if (i >= data.length() - end) {
                team.setEnd(true);
            }
            team.setRank(obj.getString("rank"));
            team.setTeamLogo(obj.getString("team_logo"));
            team.setTeamName(obj.getString("team_name"));
            team.setCount(obj.getString("count"));
            mList2.add(team);
        }

    }
}
