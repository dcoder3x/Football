package com.app.football.main.sort;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.*;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.football.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class SortFragment extends Fragment {

    // 子fragment的顶部tab为类型1：积分，球员榜，球队榜，赛程
    public static final byte CATE_1 = 1;
    // 子fragment的顶部tab为类型2：积分，射手，助攻，赛程
    public static final byte CATE_2 = 2;

    private ViewPager mViewPager;
    private PagerAdapter mAdapter;
    private TabLayout mTabLayout;
    private Toolbar mToolbar;

    public SortFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // test
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(PagerFragment.newInstance(CATE_1, "51"));
        fragments.add(PagerFragment.newInstance(CATE_1, "8"));
        fragments.add(PagerFragment.newInstance(CATE_1, "7"));
        fragments.add(PagerFragment.newInstance(CATE_1, "9"));
        fragments.add(PagerFragment.newInstance(CATE_1, "13"));
        fragments.add(PagerFragment.newInstance(CATE_1, "10"));
        fragments.add(PagerFragment.newInstance(CATE_1, "18"));
        fragments.add(PagerFragment.newInstance(CATE_2, "229"));
        fragments.add(PagerFragment.newInstance(CATE_2, "224"));
        FragmentManager fm = getChildFragmentManager();
        mAdapter = new PagerAdapter(fm, fragments);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sort, container, false);
        mViewPager = (ViewPager) view.findViewById(R.id.view_pager);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(5);
        mTabLayout = (TabLayout) getActivity().findViewById(R.id.lian_sai_tab);
        mToolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        mTabLayout.setupWithViewPager(mViewPager);
        // TODO 选中一个tab后直接定位到，而不是滑动至选中的位置.如何实现？

        return view;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        // add() show() hide()时使用，但在fragment创建时不会调用。所以搭配onResume()方法使用
        super.onHiddenChanged(hidden);
        if (hidden) {
            mToolbar.setVisibility(View.VISIBLE);
            mTabLayout.setVisibility(View.GONE);
        } else {
            mToolbar.setVisibility(View.GONE);
            mTabLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //fragment在可见或不可见状态下都有可能执行onResume()
        if (isVisible()) {
            mToolbar.setVisibility(View.GONE);
            mTabLayout.setVisibility(View.VISIBLE);
        }
    }
}
