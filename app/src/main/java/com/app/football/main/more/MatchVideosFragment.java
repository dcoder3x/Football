package com.app.football.main.more;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.app.football.R;
import com.app.football.main.MainActivity;
import com.app.football.model.robot_data.VideosTitleAndThumb;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MatchVideosFragment extends Fragment implements MatchVideosContract.View {

    private RecyclerView mRecyclerView;


    public MatchVideosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_match_videos, container, false);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.matches_videos);
        showProgressBar();
        MatchVideosPresenter presenter = new MatchVideosPresenter(MatchVideosFragment.this);
        presenter.fetchMatches();
        return v;
    }

    private void showProgressBar() {
        MainActivity activity = (MainActivity) getActivity();
        if (activity != null) {
            activity.showProgressBar();
        }
    }

    private void closeProgressBar() {
        MainActivity activity = (MainActivity) getActivity();
        if (activity != null) {
            activity.closeProgressBar();
        }
    }

    @Override
    public void showMatches(ArrayList<VideosTitleAndThumb> list) {
        if (list != null) {
            MatchVideosViewAdapter adapter = new MatchVideosViewAdapter(getActivity(), list);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            mRecyclerView.setAdapter(adapter);

        } else {
            Toast.makeText(getActivity(), "糟糕，好像没有找到任何信息!", Toast.LENGTH_SHORT).show();
        }
        closeProgressBar();
    }
}
