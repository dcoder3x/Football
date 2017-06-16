package com.app.football.main.whatshot;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.football.R;
import com.app.football.main.whatshot.detail.TrendingDetailActivity;
import com.app.football.model.TrendingModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


/**
 * Created by Administrator on 2017/3/11.
 *
 */

class TrendingRVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<TrendingModel> mAll;
    private ArrayList<TrendingModel> mSlider;
    private Context mContext;
    private final LoadMoreListener mListener;
    private CustomSliderAdapter mAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private View mHeaderItem;
    private int lastPosition;
    private int lastOffset;

    // 在adapter内部实现加载更多
    interface LoadMoreListener {
        void loadMore();
    }

    TrendingRVAdapter(LoadMoreListener listener, Context context, ArrayList<TrendingModel> all, ArrayList<TrendingModel> slider) {
        mAll = all;
        mSlider = slider;
        mContext = context;
        mListener = listener;
        mLinearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        mAdapter = new CustomSliderAdapter();
    }

    // 刷新slider
    void refreshSlider() {
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public int getItemViewType(int position) {
        // 头部添加slider
        return position == 0 ? 0 : 1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            return new HeaderHolder(LayoutInflater.from(mContext).inflate(R.layout.whats_hot_top_slider, parent, false));
        } else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_rv_whats_hot, parent, false);
            return new ListHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ListHolder) {
            // 在构建TrendingModel对象时处理thumb或title为空的情况
            final TrendingModel model = mAll.get(position - 1);
            ListHolder view = (ListHolder) holder;
            Picasso.with(mContext)
                    .load(model.getThumb())
                    .into(view.mIv);
            view.mTv.setText(model.getTitle());
            view.mDesc.setText(model.getDescription());
            view.mCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString("id", model.getId() + "");
                    bundle.putString("title", model.getTitle());
                    bundle.putString("thumb", model.getThumb());
                    bundle.putString("url", model.getUrl());
                    Intent intent = new Intent(mContext, TrendingDetailActivity.class);
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);
                }
            });
            if ((position >= getItemCount() - 1))
                mListener.loadMore();
        }
        if (holder instanceof HeaderHolder) {
            HeaderHolder headerHolder = (HeaderHolder) holder;
            headerHolder.mRecyclerView.setLayoutManager(mLinearLayoutManager);
            headerHolder.mRecyclerView.setAdapter(mAdapter);
            // 定位到上次浏览的位置
            if (lastPosition > 0)
                lastPosition--;
            mLinearLayoutManager.scrollToPositionWithOffset(lastPosition, lastOffset);

        }
    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        super.onViewRecycled(holder);
        View v = mLinearLayoutManager.getChildAt(lastPosition);
        if (v != null) {
            lastOffset = v.getTop();
            Log.d("position and offset", lastPosition + ",,," + lastOffset);
        }
    }

    @Override
    public int getItemCount() {
        return mAll.size() + 1;
    }

    // list holder.
    private class ListHolder extends RecyclerView.ViewHolder {

        ImageView mIv;
        TextView mTv;
        TextView mDesc;
        CardView mCardView;

        ListHolder(View itemView) {
            super(itemView);
            mIv = (ImageView) itemView.findViewById(R.id.rv_item_image);
            mTv = (TextView) itemView.findViewById(R.id.rv_item_text);
            mDesc = (TextView) itemView.findViewById(R.id.rv_item_desc);
            mCardView = (CardView) itemView.findViewById(R.id.card_view_whats_hot);
        }
    }

    private class HeaderHolder extends RecyclerView.ViewHolder {

        RecyclerView mRecyclerView;
        public HeaderHolder(View itemView) {
            super(itemView);
            mRecyclerView = (RecyclerView) itemView.findViewById(R.id.whats_hot_top);
        }
    }

    // top slider adapter
    private class CustomSliderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            mHeaderItem = LayoutInflater.from(mContext).inflate(R.layout.item_my_custom_slider, parent, false);
            return new MyHolder(mHeaderItem);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof MyHolder) {
                final TrendingModel model = mSlider.get(position);
                MyHolder myHolder = (MyHolder) holder;
                myHolder.mNum.setText((position + 1) + "/" + getItemCount());
                myHolder.mTitle.setText(model.getTitle());
                Picasso.with(mContext).load(model.getThumb()).into(myHolder.mImage);
                myHolder.mImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putString("id", model.getId() + "");
                        bundle.putString("thumb", model.getThumb());
                        bundle.putString("url", model.getUrl());
                        bundle.putString("title", model.getTitle());
                        Intent intent = new Intent(mContext, TrendingDetailActivity.class);
                        intent.putExtras(bundle);
                        mContext.startActivity(intent);
                    }
                });
            }
            lastPosition = position;
        }


        @Override
        public int getItemCount() {
            return mSlider.size();
        }

        private class MyHolder extends RecyclerView.ViewHolder {
            ImageView mImage;
            TextView mTitle;
            TextView mNum;
            public MyHolder(View itemView) {
                super(itemView);
                mImage = (ImageView) itemView.findViewById(R.id.top_slider_pic);
                mTitle = (TextView) itemView.findViewById(R.id.top_slider_title);
                mNum = (TextView) itemView.findViewById(R.id.top_slider_num);
            }
        }
    }
}


