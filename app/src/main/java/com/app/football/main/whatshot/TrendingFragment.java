package com.app.football.main.whatshot;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



import com.app.football.R;
import com.app.football.main.MainActivity;
import com.app.football.model.TrendingModel;

import java.lang.ref.WeakReference;
import java.util.ArrayList;


public class TrendingFragment extends Fragment implements TrendingContract.View, TrendingRVAdapter.LoadMoreListener {

    // 进入主页后，请求网络数据
    public static final byte FLAG1 = 1;
    // 下拉刷新
    public static final byte FLAG2 = 2;
    // 上拉加载更多
    public static final byte FLAG3 = 3;

    private SwipeRefreshLayout mSwipeRefresh;
    private TrendingPresenter mTrendingPresenter;
    private RecyclerView mWhatsHotRecyclerView;
    private TrendingRVAdapter mAdapter;
    private Handler mHandler;

    public TrendingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTrendingPresenter = new TrendingPresenter(this);
        mHandler = new MyHandler(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_whats_hot, container, false);
        mWhatsHotRecyclerView = (RecyclerView) view.findViewById(R.id.whats_hot_recycler_view);
        mWhatsHotRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mSwipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.whats_hot_refresh);
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mTrendingPresenter.load(FLAG2);
            }
        });

        showProgressBar();
        mTrendingPresenter.load(FLAG1);
        return view;
    }

    /**
     *  scroll to top when double click bottom first item.
     */
    public void scrollToTop() {
        if (mWhatsHotRecyclerView != null) {
            mWhatsHotRecyclerView.smoothScrollToPosition(0);
        }
    }

    private void showProgressBar() {
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null)
            mainActivity.showProgressBar();
    }

    private void closeProgressBar() {
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null)
            mainActivity.closeProgressBar();
    }

    // recycler view 加载更多
    @Override
    public void loadMore() {
        mTrendingPresenter.load(FLAG3);
    }

    @Override
    public void sendMsg(byte flag, ArrayList<TrendingModel> all, ArrayList<TrendingModel> slider) {
        Message message = new Message();
        message.what = flag;
        if (flag == FLAG1) {
            mAdapter = new TrendingRVAdapter(this, getActivity(), all, slider);
        }
        mHandler.sendMessage(message);
    }

    @Override
    public void showView(byte flag) {
        switch (flag) {

            case FLAG1:
                mWhatsHotRecyclerView.setAdapter(mAdapter);
                closeProgressBar();
                break;
            case FLAG2:
                mAdapter.notifyDataSetChanged();
                mAdapter.refreshSlider();
                mSwipeRefresh.setRefreshing(false);
                break;
            case FLAG3:
                mAdapter.notifyDataSetChanged();
                break;
            default:break;
        }
    }

    // 处理ui更新
    private static class MyHandler extends Handler {
        WeakReference<TrendingContract.View> mView;
        MyHandler(TrendingContract.View view) {
            mView = new WeakReference<>(view);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            byte flag = (byte) msg.what;
            TrendingContract.View view = mView.get();
            view.showView(flag);
        }
    }
}
