package com.app.football.model.data.playerList;

import com.app.football.model.OkHttp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Administrator on 2017/3/30.
 *
 */

public class PlayerManager {

    private OkHttp mOkHttp;
    private ArrayList<RankingType> mList1;
    private ArrayList<BaseForPerson> mList2;
    private Listener mListener;

    public interface Listener {
        void success1(ArrayList<RankingType> list);
        void success2(int cate, ArrayList<BaseForPerson> list);
    }

    public PlayerManager(Listener listener) {
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
        HeaderForPerson item = new HeaderForPerson();
        for (int i = 0; i < header.length(); i++) {
            String s = header.getString(i);
            item.setStr(i, s);
        }
        mList2.add(item);
        //
        JSONArray data =content.getJSONArray("data");
        for (int i = 0; i < data.length(); i++) {
            RankingForPerson person = new RankingForPerson();
            JSONObject obj = data.getJSONObject(i);
            if (i < top) {
                person.setTop(true);
            }
            if (i >= data.length() - end) {
                person.setEnd(true);
            }
            person.setRank(obj.getInt("rank"));
            person.setPersonLogo(obj.getString("person_logo"));
            person.setPersonName(obj.getString("person_name"));
            person.setTeamName(obj.getString("team_name"));
            person.setCount(obj.getInt("count"));
            mList2.add(person);
        }

    }
}
