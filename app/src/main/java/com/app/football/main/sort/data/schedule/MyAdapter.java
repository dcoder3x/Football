package com.app.football.main.sort.data.schedule;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.football.R;
import com.app.football.model.data.schedule.BaseSchedule2;
import com.app.football.model.data.schedule.Schedule2Header;
import com.app.football.model.data.schedule.Schedule2Item;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/1.
 *
 */

class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<BaseSchedule2> mList;
    private Context mContext;
    private ClickListener mListener;
    // 让创建adapter的类实现比赛轮次切换的方法
    interface ClickListener {
        void clickLeft(String url);
        void clickCenter(String url);
        void clickRight(String url);
    }

    public MyAdapter(ArrayList<BaseSchedule2> list, Context context, ClickListener listener) {
        mList = list;
        mContext = context;
        mListener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? 0 : 1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 1) {
            return new ViewHolder2(LayoutInflater.from(mContext).inflate(R.layout.item_integral_single_match_score, parent, false));
        } else {
            return new ViewHolder1(LayoutInflater.from(mContext).inflate(R.layout.item_schedule_header, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder2) {
            Schedule2Item item = (Schedule2Item) mList.get(position);
            ViewHolder2 holder2 = (ViewHolder2) holder;
            holder2.mStartPlay.setText(item.getStartPlay());
            holder2.mTeamAName.setText(item.getTeamAName());
            Picasso.with(mContext).load(item.getTeamALogo()).into(holder2.mTeamALogo);
            if (item.getStatus().equals("Fixture")) {
                holder2.mMatchScore.setText("VS");
            } else {
                holder2.mMatchScore.setText(item.getFsA() + ":" + item.getFsB());
            }
            Picasso.with(mContext).load(item.getTeamBLogo()).into(holder2.mTeamBLogo);
            holder2.mTeamBName.setText(item.getTeamBName());
        }
        if (holder instanceof ViewHolder1) {
            ViewHolder1 holder1 = (ViewHolder1) holder;
            final Schedule2Header item = (Schedule2Header) mList.get(position);
            holder1.mHeaderLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.clickLeft(item.getUrlPrev());
                }
            });
            holder1.mHeaderCenter.setText(item.getName());
            holder1.mHeaderCenter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.clickCenter(item.getUrlCurrent());
                }
            });
            holder1.mHeaderRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.clickRight(item.getUrlNext());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    private class ViewHolder1 extends RecyclerView.ViewHolder {

        TextView mHeaderLeft;
        TextView mHeaderCenter;
        TextView mHeaderRight;

        public ViewHolder1(View itemView) {
            super(itemView);
            mHeaderLeft = (TextView) itemView.findViewById(R.id.tv_schedule_header_left);
            mHeaderCenter = (TextView) itemView.findViewById(R.id.tv_schedule_header_center);
            mHeaderRight = (TextView) itemView.findViewById(R.id.tv_schedule_header_right);
        }
    }

    private class ViewHolder2 extends RecyclerView.ViewHolder {
        TextView mStartPlay;
        TextView mTeamAName;
        ImageView mTeamALogo;
        TextView mMatchScore;
        ImageView mTeamBLogo;
        TextView mTeamBName;

        ViewHolder2(View itemView) {
            super(itemView);
            mStartPlay = (TextView) itemView.findViewById(R.id.item_1_start_play);
            mTeamAName = (TextView) itemView.findViewById(R.id.item_1_team_A_name);
            mTeamALogo = (ImageView) itemView.findViewById(R.id.item_1_team_A_logo);
            mTeamBLogo = (ImageView) itemView.findViewById(R.id.item_1_team_B_logo);
            mTeamBName = (TextView) itemView.findViewById(R.id.item_1_team_B_name);
            mMatchScore = (TextView) itemView.findViewById(R.id.item_1_match_score);
        }
    }
}
