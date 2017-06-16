package com.app.football.main.schedule;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.football.R;
import com.app.football.model.ScheduleManager;
import com.app.football.model.ScheduleModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


/**
 * Created by Administrator on 2017/3/13.
 *
 */

class ScheduleRVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<ScheduleModel> mList;
    private ScrollListener mScrollListener;

    interface ScrollListener {
        /*
         * 加载上一次更新的赛程，下拉加载。这个好像不太好搞，还是使用SwipeRefreshLayout吧
         */
        //void loadForPrev();

        /**
         * 加载下一次更新的赛程，上拉加载。
         */
        void loadForNext();
    }

    ScheduleRVAdapter(Context context, ArrayList<ScheduleModel> list, ScrollListener listener) {
        mContext = context;
        mList = list;
        mScrollListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_rv_live_tv, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyHolder view = (MyHolder) holder;
        ScheduleModel model = mList.get(position);
        Picasso.with(mContext).load(model.getTeam_A_logo()).into(view.mTeamA);
        Picasso.with(mContext).load(model.getTeam_B_logo()).into(view.mTeamB);
        view.mMatchTime.setText(model.getStart_play());
        view.mAName.setText(model.getTeam_A_name());
        view.mBName.setText(model.getTeam_B_name());
        String str = model.getStatus();
        byte a = model.getFs_A();
        byte b = model.getFs_B();
        switch (str) {
            case "Played" :
                view.mStatus.setText("已完赛\n" + a + ":" + b);
                break;
            case "Playing" :
                view.mStatus.setText(model.getMinute() + "\n" + a + ":" + b);
                view.mStatus.setTextColor(Color.parseColor("#43CD80"));
                break;
            default:
                view.mStatus.setText("未进行");
                break;
        }
        if (position >= getItemCount() - 1 && getItemCount() > ScheduleManager.sSize) {
            mScrollListener.loadForNext();
        }
    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        super.onViewRecycled(holder);
        ((MyHolder)holder).mStatus.setTextColor(Color.GRAY);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    private class MyHolder extends RecyclerView.ViewHolder {
        private ImageView mTeamA;
        private ImageView mTeamB;
        private TextView mMatchTime;
        private TextView mAName;
        private TextView mBName;
        private TextView mStatus;
        MyHolder(View itemView) {
            super(itemView);
            mTeamA = (ImageView) itemView.findViewById(R.id.team_a_logo);
            mTeamB = (ImageView) itemView.findViewById(R.id.team_b_logo);
            mMatchTime = (TextView) itemView.findViewById(R.id.match_time);
            mAName = (TextView) itemView.findViewById(R.id.team_a_name);
            mBName = (TextView) itemView.findViewById(R.id.team_b_name);
            mStatus = (TextView) itemView.findViewById(R.id.status);
        }
    }
}
