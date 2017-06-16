package com.app.football.main.sort;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.football.R;
import com.app.football.main.sort.data.integral.IntegralFragment;
import com.app.football.main.sort.data.playerList.PlayerListFragment;
import com.app.football.main.sort.data.schedule.Schedule2Fragment;
import com.app.football.main.sort.data.teamList.TeamListFragment;


public class PagerFragment extends Fragment {

    private static final String ARG_PARAM1 = "category";
    private static final String ARG_PARAM2 = "competition";
    // 积分
    private static final String TAG1 = "Integral";
    private static final String TAG2 = "PlayerList";
    private static final String TAG3 = "TeamList";
    private static final String TAG4 = "schedule";

    private byte mCate;
    private String mCompetition;
    private FragmentManager mFragmentManager;


    public PagerFragment() {
        // Required empty public constructor
    }

    public static PagerFragment newInstance(byte cate, String competition) {
        PagerFragment fragment = new PagerFragment();
        Bundle args = new Bundle();
        args.putByte(ARG_PARAM1, cate);
        args.putString(ARG_PARAM2, competition);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCate = getArguments().getByte(ARG_PARAM1);
            mCompetition = getArguments().getString(ARG_PARAM2);
        }
        mFragmentManager = getChildFragmentManager();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ranking, container, false);
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.ranking_tab);
        tabLayout.addOnTabSelectedListener(new MyTabListener());

        switch (mCate) {
            case SortFragment.CATE_1:
                break;
            case SortFragment.CATE_2:
                tabLayout.getTabAt(1).setText("射手");
                tabLayout.getTabAt(2).setText("助攻");
                break;
            default:
                break;
        }
        createFragment(TAG1);
        return view;
    }

    // 创建对应类型的fragment
    private void createFragment(String tag) {
        Fragment fragment = mFragmentManager.findFragmentByTag(tag);

        if (fragment == null) {
            switch (tag) {
                case TAG1:
                    fragment = IntegralFragment.newInstance(generateUrl(tag));
                    break;
                case TAG2:
                    fragment = PlayerListFragment.newInstance(generateUrl(tag));
                    break;
                case TAG3:
                    fragment = TeamListFragment.newInstance(generateUrl(tag));
                    break;
                case TAG4:
                    fragment = Schedule2Fragment.newInstance(generateUrl(tag));
                default:
                    break;
            }
        }
        mFragmentManager.beginTransaction()
                .replace(R.id.content_for_ranking, fragment, tag)
                .commit();
    }

    private String generateUrl(String tag) {
        switch (tag) {
            case TAG1:
                return "https://api.dongqiudi.com/data/v1/team_ranking/" + mCompetition + "?version=99&type=total_ranking&refer=data_tab";
            case TAG2:
                return "https://api.dongqiudi.com/data/v1/ranking_type/" + mCompetition + "?version=99&type=person&refer=data_tab";
            case TAG3:
                return "https://api.dongqiudi.com/data/v1/ranking_type/" + mCompetition + "?version=99&type=team&refer=data_tab";
            case TAG4:
                return "https://api.dongqiudi.com/data/v1/schedule/" + mCompetition + "?version=99";
            default:
                return "";
        }
    }

    private class MyTabListener implements TabLayout.OnTabSelectedListener {

        MyTabListener() {

        }

        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            String str = (String) tab.getText();
            if (str == null) {
                str = "";
            }
            switch (str) {
                case "积分":
                    createFragment(TAG1);
                    break;
                case "球员榜":
                    createFragment(TAG2);
                    break;
                case "射手":
                    break;
                case "球队榜":
                    createFragment(TAG3);
                    break;
                case "赛程":
                    createFragment(TAG4);
                    break;
                case "助攻":
                    break;
                default:
                    break;
            }
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    }

}
