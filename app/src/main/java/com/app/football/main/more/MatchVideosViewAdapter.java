package com.app.football.main.more;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.football.R;
import com.app.football.model.robot_data.VideosTitleAndThumb;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2017/4/9.
 *
 */

class MatchVideosViewAdapter extends RecyclerView.Adapter<ViewHolder> {

    private Context mContext;
    private ArrayList<VideosTitleAndThumb> mList;

    public MatchVideosViewAdapter(Context context, ArrayList<VideosTitleAndThumb> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyHolder(LayoutInflater.from(mContext).inflate(R.layout.item_matches_videos, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (holder instanceof MyHolder) {
            MyHolder myHolder = (MyHolder) holder;
            final VideosTitleAndThumb item = mList.get(position);
            myHolder.mTitle.setText(item.getTitle());
            Picasso.with(mContext).load(item.getThumbUrl()).into(myHolder.mImageView);
            myHolder.mCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString("url", item.getUrl());
                    Intent i = new Intent(mContext, VideoActivity.class);
                    i.putExtras(bundle);
                    mContext.startActivity(i);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    private class MyHolder extends ViewHolder {

        ImageView mImageView;
        TextView mTitle;
        CardView mCardView;

        MyHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.matches_videos_thumb);
            mTitle = (TextView) itemView.findViewById(R.id.matches_videos_title);
            mCardView = (CardView) itemView.findViewById(R.id.matches_videos_card);
        }
    }
}
