package com.app.football.main.sort.data.teamList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.app.football.model.data.playerList.RankingType;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/3/31.
 *
 */

class LeftAdapter extends BaseAdapter {

    private ArrayList<RankingType> mList;
    private Context mContext;

    LeftAdapter(Context context, ArrayList<RankingType> list) {
        mList = list;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        //
        return mList.get(position).getUrl();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(android.R.layout.simple_list_item_1, parent, false);
            holder = new ViewHolder();
            holder.mTextView = (TextView) convertView.findViewById(android.R.id.text1);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.mTextView.setText(mList.get(position).getName());
        return convertView;
    }

    private class ViewHolder {
        TextView mTextView;
    }
}
