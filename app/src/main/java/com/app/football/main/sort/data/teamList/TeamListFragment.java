package com.app.football.main.sort.data.teamList;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.app.football.R;
import com.app.football.main.MainActivity;
import com.app.football.model.data.playerList.RankingType;
import com.app.football.model.data.teamList.BaseForTeam;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class TeamListFragment extends Fragment implements TeamListContract.View {

    private static final String ARG1 = "param1";
    public static final int WHAT1 = 1;
    public static final int WHAT2 = 2;
    public static final int WHAT3 = 3;

    private String mUrl;
    private ListView mListView;
    private LeftAdapter mAdapter1;
    private RecyclerView mRecyclerView;
    private RightAdapter mAdapter2;

    private Handler mHandler;
    private TeamListContract.Presenter mPresenter;


    public TeamListFragment() {
        // Required empty public constructor
    }

    public static TeamListFragment newInstance(String url) {
        Bundle bundle = new Bundle();
        bundle.putString(ARG1, url);
        TeamListFragment fragment = new TeamListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHandler = new MyHandler(this);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mUrl = bundle.getString(ARG1);
        }
        mPresenter = new TeamListPresenter(this);
        ((MainActivity) getActivity()).showProgressBar();
        mPresenter.loadRankingType(mUrl);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_player_list, container, false);
        mListView = (ListView) view.findViewById(R.id.player_list_left);
        mListView.setOnItemClickListener(new MyListViewListener());
        mRecyclerView = (RecyclerView) view.findViewById(R.id.player_list_right);
        return view;
    }

    @Override
    public void showListView(ArrayList<RankingType> list) {
        mAdapter1 = new LeftAdapter(getActivity(), list);
        Message message = new Message();
        message.what = WHAT1;
        mHandler.sendMessage(message);
    }

    @Override
    public void showRecyclerView(ArrayList<BaseForTeam> list) {
        mAdapter2 = new RightAdapter(getActivity(), list);
        Message message = new Message();
        message.what = WHAT2;
        mHandler.sendMessage(message);
    }

    @Override
    public void updateRecyclerView() {
        Message message = new Message();
        message.what = WHAT3;
        mHandler.sendMessage(message);
    }

    @Override
    public void handle(int what) {
        MainActivity main = (MainActivity) getActivity();
        switch (what) {
            case WHAT1:
                mListView.setAdapter(mAdapter1);
                // 初始化进球榜
                mListView.setSelection(0);
                mPresenter.loadOther(WHAT2, (String)mAdapter1.getItem(0));
                // TODO 应该让第一个item选中

                break;
            case WHAT2:
                mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                mRecyclerView.setAdapter(mAdapter2);
                break;
            case WHAT3:
                mAdapter2.notifyDataSetChanged();
            default:
                break;
        }
    }

    @Override
    public void showProgressBar() {
        // 注意空对象
        MainActivity main = (MainActivity) getActivity();
        if (main != null) {
            main.showProgressBar();
        }
    }

    @Override
    public void closeProgressBar() {
        MainActivity main = (MainActivity) getActivity();
        if (main != null) {
            main.closeProgressBar();
        }
    }

    private class MyListViewListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            view.setSelected(true);
            showProgressBar();
            mPresenter.loadOther(WHAT3, (String)parent.getItemAtPosition(position));
        }

    }

    private static class MyHandler extends Handler {
        WeakReference<TeamListContract.View> mViewWeakReference;

        MyHandler(TeamListContract.View view) {
            mViewWeakReference = new WeakReference<>(view);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            TeamListContract.View view = mViewWeakReference.get();
            view.handle(msg.what);
            view.closeProgressBar();
        }
    }
}
