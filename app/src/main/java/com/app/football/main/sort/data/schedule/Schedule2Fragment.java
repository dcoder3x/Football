package com.app.football.main.sort.data.schedule;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.football.R;
import com.app.football.main.MainActivity;
import com.app.football.model.data.schedule.BaseSchedule2;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment
 * Use the {@link Schedule2Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Schedule2Fragment extends Fragment implements Schedule2Contract.View {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    public static final int WHAT0 = 0;
    public static final int WHAT1 = 1;

    // TODO: Rename and change types of parameters
    private String mUrl;

    private MyAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private MyHandler mHandler;
    private Schedule2Contract.Presenter mPresenter;

    public Schedule2Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment Schedule2Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Schedule2Fragment newInstance(String url) {
        Schedule2Fragment fragment = new Schedule2Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, url);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUrl = getArguments().getString(ARG_PARAM1);
        }
        mHandler = new MyHandler(this);
        mPresenter = new Schedule2Presenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_schedule, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_schedule_all);
        showProgressBar();
        mPresenter.load(mUrl, WHAT0);
        return view;
    }


    private void showProgressBar() {
        MainActivity main = (MainActivity) getActivity();
        if (main != null) {
            main.showProgressBar();
        }
    }

    private void closeProgressBar() {
        MainActivity main = (MainActivity) getActivity();
        if (main != null) {
            main.closeProgressBar();
        }
    }

    @Override
    public void sendCreateAdapterMsg(ArrayList<BaseSchedule2> list) {
        if (mAdapter == null) {
            mAdapter = new MyAdapter(list, getActivity(), new MyAdapter.ClickListener() {
                @Override
                public void clickLeft(String url) {
                    mPresenter.load(url, WHAT1);
                }

                @Override
                public void clickCenter(String url) {
                    // TODO
                }

                @Override
                public void clickRight(String url) {
                    mPresenter.load(url, WHAT1);
                }
            });
        }
        Message message = new Message();
        message.what = WHAT0;
        mHandler.sendMessage(message);
    }

    @Override
    public void sendUpdateAdapterMsg() {
        Message message = new Message();
        message.what = WHAT1;
        mHandler.sendMessage(message);
    }

    @Override
    public void showView(int what) {
        if (what == WHAT0) {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            mRecyclerView.setAdapter(mAdapter);
        }
        if (what == WHAT1) {
            mAdapter.notifyDataSetChanged();
        }
        closeProgressBar();
    }

    private static class MyHandler extends Handler {
        WeakReference<Schedule2Contract.View> mViewWeakReference;

        MyHandler(Schedule2Contract.View view) {
            mViewWeakReference = new WeakReference<>(view);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Schedule2Contract.View view = mViewWeakReference.get();
            view.showView(msg.what);
        }
    }
}
