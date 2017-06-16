package com.app.football.main.sort.data.integral;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.football.R;
import com.app.football.model.data.integral.BaseIntegralModel;
import com.app.football.model.data.integral.AbstractItemMatchScore;
import com.app.football.model.data.integral.AbstractItemRegular;
import com.app.football.model.data.integral.AbstractItemTitle;
import com.app.football.model.data.integral.ItemDescription;
import com.app.football.model.data.integral.ItemFirstLevelTitle;
import com.app.football.model.data.integral.ItemHeader;
import com.app.football.model.data.integral.ItemRegular;
import com.app.football.model.data.integral.ItemRegularEnd;
import com.app.football.model.data.integral.ItemRegularTop;
import com.app.football.model.data.integral.ItemSecondLevelTitle;
import com.app.football.model.data.integral.ItemMatchScore;
import com.app.football.model.data.integral.ItemTotalMatchScore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/3/26.
 *
 */

public class IntegralAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM_ERROR = -1;
    private static final int ITEM_0 = 0;
    public static final int ITEM_1 = 1;
    private static final int ITEM_2 = 2;
    private static final int ITEM_3 = 3;
    public static final int ITEM_4 = 4;
    private static final int ITEM_5 = 5;
    private static final int ITEM_6 = 6;
    private static final int ITEM_7 = 7;
    private static final int ITEM_8 = 8;


    private ArrayList<BaseIntegralModel> mList;
    private Context mContext;

    IntegralAdapter(Context context, ArrayList<BaseIntegralModel> list) {
        mList = list;
        mContext = context;
    }

    @Override
    public int getItemViewType(int position) {
        // ItemRegular,ItemRegularEnd,ItemRegularTop使用同一个ViewHolder
        if (mList.get(position) instanceof ItemRegular) {
            return ITEM_0;
        } else if (mList.get(position) instanceof ItemMatchScore) {
            return ITEM_1;
        } else if (mList.get(position) instanceof ItemRegularTop) {
            return ITEM_2;
        } else if (mList.get(position) instanceof ItemRegularEnd) {
            return ITEM_3;
        } else if (mList.get(position) instanceof ItemTotalMatchScore) {
            return ITEM_4;
        } else if (mList.get(position) instanceof ItemHeader) {
            return ITEM_5;
        } else if (mList.get(position) instanceof ItemSecondLevelTitle) {
            return ITEM_6;
        } else if (mList.get(position) instanceof ItemFirstLevelTitle) {
            return ITEM_7;
        } else if (mList.get(position) instanceof ItemDescription) {
            return ITEM_8;
        } else {
            return ITEM_ERROR;
        }
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // TODO
        switch (viewType) {
            case ITEM_0:
                return new Item0ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_integral_regular, parent, false), ITEM_0);
            case ITEM_1:
                return new Item1ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_integral_single_match_score, parent, false));
            case ITEM_2:
                return new Item0ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_integral_regular, parent, false), ITEM_2);
            case ITEM_3:
                return new Item0ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_integral_regular, parent, false), ITEM_3);
            case ITEM_4:
                return new Item1ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_integral_single_match_score, parent, false));
            case ITEM_5:
                return new Item5ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_integral_header, parent, false));
            case ITEM_6:
                return new Item6Or7ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_integral_title, parent, false), ITEM_6);
            case ITEM_7:
                return new Item6Or7ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_integral_title, parent, false), ITEM_7);
            case ITEM_8:
                return new Item8ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_integral_description, parent, false));
            case ITEM_ERROR:
                return new ItemErrorViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_integral_error, parent, false));
            default:
                return new ItemErrorViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_integral_error, parent, false));
        }
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        // TODO
        if (holder instanceof Item0ViewHolder) {
            bindItem0Or2Or3((Item0ViewHolder) holder, (AbstractItemRegular) mList.get(position));
        } else if (holder instanceof Item1ViewHolder) {
            bindItem1Or4((Item1ViewHolder) holder, (AbstractItemMatchScore) mList.get(position));

        } else if (holder instanceof Item5ViewHolder) {
            // do nothing
        } else if (holder instanceof Item8ViewHolder) {
            Item8ViewHolder viewHolder = (Item8ViewHolder) holder;
            ItemDescription item = (ItemDescription) mList.get(position);
            viewHolder.mDescription.setText(item.getDescription());
        } else if (holder instanceof Item6Or7ViewHolder) {
            Item6Or7ViewHolder viewHolder = (Item6Or7ViewHolder) holder;
            AbstractItemTitle item = (AbstractItemTitle) mList.get(position);
            // viewHolder是根据position对应的ViewType获得的对象，所以viewHolder是与position对应的。不用再判断
            viewHolder.mName.setText(item.getTitle());
        } else if (holder instanceof ItemErrorViewHolder){
            // do nothing
        } else {
            // do nothing
        }
    }
    private void bindItem0Or2Or3(Item0ViewHolder viewHolder, AbstractItemRegular item) {

        viewHolder.mTeamRank.setText(item.getRank());
        Picasso.with(mContext).load(item.getTeamLogo()).into(viewHolder.mTeamLogo);
        viewHolder.mTeamName.setText(item.getTeamName());
        viewHolder.mMatchesTotal.setText(item.getMatchesTotal());
        viewHolder.mMatchesWon.setText(item.getMatchesWon());
        viewHolder.mMatchesDraw.setText(item.getMatchesDraw());
        viewHolder.mMatchesLost.setText(item.getMatchesLost());
        viewHolder.mGoalsScale.setText(item.getGoalsPro() + "/" + item.getGoalsAgainst());
        viewHolder.mPoints.setText(item.getPoints());
    }
    private void bindItem1Or4(Item1ViewHolder viewHolder, AbstractItemMatchScore item) {
        viewHolder.mTeamAName.setText(item.getTeamAName());
        Picasso.with(mContext).load(item.getTeamALogo()).into(viewHolder.mTeamALogo);
        Picasso.with(mContext).load(item.getTeamBLogo()).into(viewHolder.mTeamBLogo);
        viewHolder.mTeamBName.setText(item.getTeamBName());

        if (item instanceof ItemMatchScore) {
            // 时间格式太长的话，可以在解析json时截取一部分
            viewHolder.mStartPlay.setText(item.getStartPlay());
            if (item.getStatus().equalsIgnoreCase("Fixture")) {
                viewHolder.mMatchScore.setText("VS");
            } else {
                viewHolder.mMatchScore.setText(item.getFsA() + ":" + item.getFsB());
            }
        }
        if (item instanceof ItemTotalMatchScore) {
            viewHolder.mStartPlay.setText("总比分");
            viewHolder.mMatchScore.setText(item.getFsA() + ":" + item.getFsB());
        }
    }
    @Override
    public int getItemCount() {
        return mList.size();
    }
    // item_0 item_2 item_3使用同一个ViewHolder但是具有不同的背景颜色
    private class Item0ViewHolder extends RecyclerView.ViewHolder {

        int mTag = 0;
        LinearLayout mLinearLayout;

        TextView mTeamRank;
        ImageView mTeamLogo;
        TextView mTeamName;
        TextView mMatchesTotal;
        TextView mMatchesWon;
        TextView mMatchesDraw;
        TextView mMatchesLost;
        TextView mGoalsScale;
        TextView mPoints;

        // 获得三个不同背景色的ItemRegular，在bindView时候设置背景色会导致滑动卡顿
        Item0ViewHolder(View itemView, int tag) {
            super(itemView);
            mTeamRank = (TextView) itemView.findViewById(R.id.item_0_rank);
            mTeamLogo = (ImageView) itemView.findViewById(R.id.item_0_team_logo);
            mTeamName = (TextView) itemView.findViewById(R.id.item_0_team_name);
            mMatchesTotal = (TextView) itemView.findViewById(R.id.item_0_match_total);
            mMatchesWon = (TextView) itemView.findViewById(R.id.item_0_match_won);
            mMatchesDraw = (TextView) itemView.findViewById(R.id.item_0_match_draw);
            mMatchesLost = (TextView) itemView.findViewById(R.id.item_0_match_lost);
            mGoalsScale = (TextView) itemView.findViewById(R.id.item_0_goals_scale);
            mPoints = (TextView) itemView.findViewById(R.id.item_0_points);
            mLinearLayout = (LinearLayout) itemView.findViewById(R.id.integral_regular_row);
            mTag = tag;
            String color;
            if (mTag == ITEM_0) {
                color = "#3F51B5";
            } else if (mTag == ITEM_2) {
                color = "#FF4081";
            } else {
                color = "#8d6e63";
            }
            mLinearLayout.setBackgroundColor(Color.parseColor(color));

        }
    }
    // item_1 item_4使用同一个ViewHolder
    private class Item1ViewHolder extends RecyclerView.ViewHolder {

        TextView mStartPlay;
        TextView mTeamAName;
        ImageView mTeamALogo;
        TextView mMatchScore;
        ImageView mTeamBLogo;
        TextView mTeamBName;

        Item1ViewHolder(View itemView) {
            super(itemView);
            mStartPlay = (TextView) itemView.findViewById(R.id.item_1_start_play);
            mTeamAName = (TextView) itemView.findViewById(R.id.item_1_team_A_name);
            mTeamALogo = (ImageView) itemView.findViewById(R.id.item_1_team_A_logo);
            mTeamBLogo = (ImageView) itemView.findViewById(R.id.item_1_team_B_logo);
            mTeamBName = (TextView) itemView.findViewById(R.id.item_1_team_B_name);
            mMatchScore = (TextView) itemView.findViewById(R.id.item_1_match_score);
        }
    }
    private class Item5ViewHolder extends RecyclerView.ViewHolder {

        public Item5ViewHolder(View itemView) {
            super(itemView);
        }
    }
    // item_6 item_7使用同一个ViewHolder但字体大小不同
    private class Item6Or7ViewHolder extends RecyclerView.ViewHolder {

        TextView mName;

        public Item6Or7ViewHolder(View itemView, int tag) {
            super(itemView);
            mName = (TextView) itemView.findViewById(R.id.item_6_7_title);
            if (tag == ITEM_6) {
                mName.setTextSize(16);
            } else {
                mName.setTextSize(18);
            }
        }
    }
    private class Item8ViewHolder extends RecyclerView.ViewHolder {

        TextView mDescription;
        public Item8ViewHolder(View itemView) {
            super(itemView);
            mDescription = (TextView) itemView.findViewById(R.id.item_8_description);
        }
    }
    private class ItemErrorViewHolder extends RecyclerView.ViewHolder {
        public ItemErrorViewHolder(View itemView) {
            super(itemView);
        }
    }
}
