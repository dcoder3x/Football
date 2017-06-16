package com.app.football.main.sort.data.playerList;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.football.R;
import com.app.football.model.data.playerList.BaseForPerson;
import com.app.football.model.data.playerList.HeaderForPerson;
import com.app.football.model.data.playerList.RankingForPerson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/3/30.
 *
 */

class RightAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<BaseForPerson> mList;

    public RightAdapter(Context context, ArrayList<BaseForPerson> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public int getItemViewType(int position) {
        // 0时view为header
        return position == 0 ? 0 : 1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == 1) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_player_list_content, parent, false);
            return new ViewHolder2(view);
        } else {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_player_list_header, parent, false);
            return new ViewHolder1(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder1) {
            HeaderForPerson item = (HeaderForPerson) mList.get(position);
            String[] str = item.getStr();
            ViewHolder1 holder1 = (ViewHolder1) holder;
            holder1.mTextView0.setText(str[0]);
            holder1.mTextView1.setText(str[1]);
            holder1.mTextView2.setText(str[2]);
        }
        if (holder instanceof ViewHolder2) {
            RankingForPerson item = (RankingForPerson) mList.get(position);
            ViewHolder2 holder2 = (ViewHolder2) holder;
            holder2.mTextView0.setText(item.getRank() + "");
            Picasso.with(mContext).load(item.getPersonLogo()).into(holder2.mImageView);
            holder2.mTextView1.setText(item.getPersonName());
            holder2.mTextView2.setText(item.getTeamName());
            holder2.mTextView3.setText(item.getCount() + "");

        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    // header
    private class ViewHolder1 extends RecyclerView.ViewHolder {

        TextView mTextView0;
        TextView mTextView1;
        TextView mTextView2;
        public ViewHolder1(View itemView) {
            super(itemView);
            mTextView0 = (TextView) itemView.findViewById(R.id.tv_1_0);
            mTextView1 = (TextView) itemView.findViewById(R.id.tv_1_1);
            mTextView2 = (TextView) itemView.findViewById(R.id.tv_1_2);
        }
    }
    // content
    private class ViewHolder2 extends RecyclerView.ViewHolder {

        TextView mTextView0;
        ImageView mImageView;
        TextView mTextView1;
        TextView mTextView2;
        TextView mTextView3;

        public ViewHolder2(View itemView) {
            super(itemView);
            mTextView0 = (TextView) itemView.findViewById(R.id.tv_0_0);
            mImageView = (ImageView) itemView.findViewById(R.id.iv_0_0);
            mTextView1 = (TextView) itemView.findViewById(R.id.tv_0_1);
            mTextView2 = (TextView) itemView.findViewById(R.id.tv_0_2);
            mTextView3 = (TextView) itemView.findViewById(R.id.tv_0_3);
        }
    }
}
