package com.app.football.main.schedule;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.football.R;
import com.app.football.main.MainActivity;
import com.app.football.model.ScheduleModel;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class ScheduleFragment extends Fragment implements ScheduleContract.View, ScheduleRVAdapter.ScrollListener {

    //public static final String TAG = "Schedule2Fragment";
    // 初始化
    public static final byte TAG0 = 0;
    // 刷新
    public static final byte TAG1 = 1;
    // 下拉加载以前
    public static final byte TAG2 = 2;
    // 上拉加载更多
    public static final byte TAG3 = 3;

    private SchedulePresenter mPresenter;
    private RecyclerView mRecyclerViewView;
    private ScheduleRVAdapter mAdapter;
    private SwipeRefreshLayout mSwipeRefresh;
    private static Handler mHandler;


    public ScheduleFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new SchedulePresenter(this);
        mPresenter.load(TAG0);
        mHandler = new MyHandler(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_live_tv, container, false);
        mRecyclerViewView = (RecyclerView) view.findViewById(R.id.live_tv_recycler_view);
        mRecyclerViewView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mSwipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.load(TAG2);
            }
        });
        FloatingActionButton button = (FloatingActionButton) view.findViewById(R.id.refresh_live_tv);
        button.setOnClickListener(new RefreshListener());
        ((MainActivity)getActivity()).showProgressBar();
        return view;
    }


    @Override
    public void sendMsg(byte tag, ArrayList<ScheduleModel> list) {
        Message message = new Message();
        switch (tag) {
            case TAG0 :
                message.what = TAG0;
                //不能在子线程设置适配器
                mAdapter = new ScheduleRVAdapter(getActivity(), list, this);
                break;
            case TAG1 :
                message.what = TAG1;
                break;
            case TAG2 :
                message.what = TAG2;
                break;
            case TAG3 :
                message.what = TAG3;
                break;
        }
        mHandler.sendMessage(message);
    }

    @Override
    public void showView(byte tag) {
        switch (tag) {
            case TAG0 :
                mRecyclerViewView.setAdapter(mAdapter);
                break;
            case TAG1 :
                mAdapter.notifyDataSetChanged();
                break;
            case TAG2 :
                mAdapter.notifyDataSetChanged();
                // TODO 不管用？需要实现LayoutManager的方法？
                mRecyclerViewView.scrollToPosition(mAdapter.getItemCount());
                mSwipeRefresh.setRefreshing(false);
                break;
            case TAG3 :
                mAdapter.notifyDataSetChanged();
                break;
        }
        ((MainActivity)getActivity()).closeProgressBar();
    }

    @Override
    public void loadForNext() {
        ((MainActivity)getActivity()).showProgressBar();
        mPresenter.load(TAG3);
    }

    private class RefreshListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            ((MainActivity)getActivity()).showProgressBar();
            mPresenter.load(TAG1);
        }
    }

    private static class MyHandler extends Handler {
        WeakReference<ScheduleContract.View> mView;
        MyHandler(ScheduleContract.View view) {
            mView = new WeakReference<>(view);
        }
        @Override
        public void handleMessage(Message msg) {
            ScheduleContract.View view = mView.get();
            super.handleMessage(msg);
            view.showView((byte)msg.what);
        }
    }
}
