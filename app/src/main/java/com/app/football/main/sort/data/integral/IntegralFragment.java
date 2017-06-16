package com.app.football.main.sort.data.integral;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.football.R;
import com.app.football.main.MainActivity;
import com.app.football.model.data.integral.BaseIntegralModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class IntegralFragment extends Fragment implements IntegralContract.View {

    private static final String ARG_PARAM = "url";

    private String mUrl;
    private RecyclerView mRecyclerView;
    private IntegralContract.Presenter mPresenter;

    public IntegralFragment() {
        // Required empty public constructor
    }

    public static IntegralFragment newInstance(String url) {
        IntegralFragment fragment = new IntegralFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM, url);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUrl = getArguments().getString(ARG_PARAM);
        }
        mPresenter = new IntegralPresenter(mUrl, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_integral, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.integral_recycler_view);
        ((MainActivity)getActivity()).showProgressBar();
        mPresenter.load();
        return view;
    }

    @Override
    public void showIntegral(ArrayList<BaseIntegralModel> list) {
        IntegralAdapter adapter = new IntegralAdapter(getActivity(), list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(adapter);
        ((MainActivity)getActivity()).closeProgressBar();
    }
}
