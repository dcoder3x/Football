package com.app.football.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.app.football.BaseView;
import com.app.football.R;
import com.app.football.main.schedule.ScheduleFragment;
import com.app.football.main.more.MatchVideosFragment;
import com.app.football.main.sort.SortFragment;
import com.app.football.main.whatshot.TrendingFragment;
import com.sohu.cyan.android.sdk.activity.RepliesActivity;
import com.sohu.cyan.android.sdk.api.CyanSdk;
import com.sohu.cyan.android.sdk.exception.CyanException;
import com.sohu.cyan.android.sdk.http.CyanRequestListener;
import com.sohu.cyan.android.sdk.http.response.UserInfoResp;
import com.sohu.cyan.android.sdk.util.Constants;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, BaseView, LoginChooseFragment.ChooseListener {

    public static final String TAG = "MainActivity";

    private Toolbar mToolbar;
    private ProgressBar mProgressBar;
    private FragmentManager mFragmentManager;
    private Fragment mWhatsHotFragment;
    private static final String WHATS_HOT_TAG = "mWhatsHotFragment";
    private Fragment mLiveTvFragment;
    private static final String LIVE_TV_TAG = "mLiveTvFragment";
    private Fragment mSortFragment;
    private static final String SORT_TAG = "mSortFragment";
    private Fragment mMoreFragment;
    private static final String MORE_TAG = "mMoreFragment";
    private CyanSdk mCyanSdk;
    private DrawerLayout mDrawer;

    // 侧边栏header部分用户信息
    private ImageView mUserPic;
    private TextView mUsername;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        // 畅言实例
        mCyanSdk = CyanSdk.getInstance(this);

        // progressbar
        mProgressBar = (ProgressBar) findViewById(R.id.main_progress_bar);

        // bottom nav
        mFragmentManager = getSupportFragmentManager();
        BottomNavItemListener listener = new BottomNavItemListener();
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(listener);
        BottomNavigationItemView item = (BottomNavigationItemView) bottomNavigationView.findViewById(R.id.bottom_menu_whatshot);
        item.setOnTouchListener(new ScrollToTop());

        //启动后设置底部导航栏的第一个fragment处于选中状态
        // mToolbar.setTitle()在这里不起作用
        getSupportActionBar().setTitle("动态");

        //屏幕旋转fragment未销毁，不重新创建
        firstFragment();


        // slide nav
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        // 注意nav view获取子视图id的使用
        View v = navigationView.getHeaderView(0);
        mUserPic = (ImageView) v.findViewById(R.id.user_pic);
        mUsername = (TextView) v.findViewById(R.id.username);
        mUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tv = (TextView) v;
                if (tv.getText().equals("点击登录")) {
                    showSignInDialog();
                }
            }
        });
        initUserInfo();
    }



    // 进入应用后显示第一个Fragment
    private void firstFragment() {
        // 避免屏幕旋转，导致fragment未销毁但成员变量为空的情况
        mWhatsHotFragment = mFragmentManager.findFragmentByTag(WHATS_HOT_TAG);
        mLiveTvFragment = mFragmentManager.findFragmentByTag(LIVE_TV_TAG);
        mSortFragment = mFragmentManager.findFragmentByTag(SORT_TAG);
        mMoreFragment = mFragmentManager.findFragmentByTag(MORE_TAG);
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        if (mWhatsHotFragment == null) {
            mWhatsHotFragment = new TrendingFragment();
            fragmentTransaction.add(R.id.content_for_fragment, mWhatsHotFragment, WHATS_HOT_TAG);
        } else {
            hideAllFragment(fragmentTransaction);
            fragmentTransaction.show(mWhatsHotFragment);
        }
        fragmentTransaction.commit();
    }

    // hide fragment while click bottom tab
    private void hideAllFragment(FragmentTransaction fragmentTransaction) {

        if (mWhatsHotFragment != null)  fragmentTransaction.hide(mWhatsHotFragment);
        if (mLiveTvFragment != null)    fragmentTransaction.hide(mLiveTvFragment);
        if (mSortFragment != null)  fragmentTransaction.hide(mSortFragment);
        if (mMoreFragment != null)  fragmentTransaction.hide(mMoreFragment);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CyanSdk.OAUTH_RESULT_CODE && resultCode == RESULT_OK) {
            initUserInfo();
            Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_user:
                // TODO
                break;
            case R.id.nav_comments:
                if (isLogin()) {
                    startActivity(new Intent(this, RepliesActivity.class));
                } else {
                    Toast.makeText(this, "还没登录!", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.nav_logout:
                if (isLogin()) {
                    try {
                        mCyanSdk.logOut();
                        initUserInfo();
                    } catch (CyanException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(this, "还没登录!", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }

    // BaseView

    @Override
    public void showProgressBar() {
        // 在屏幕旋转时，fragment在onCreate中调用mProgressBar为null,导致空指针异常
        if (mProgressBar != null) {
            mProgressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void closeProgressBar() {
        if (mProgressBar != null) {
            mProgressBar.setVisibility(View.GONE);
        }
    }

    // 畅言

    private boolean isLogin() {
        return mCyanSdk.getAccessToken() != null;
    }

    // 加载登录的用户信息
    private void initUserInfo() {
        if (isLogin()) {
            try {
                mCyanSdk.getUserInfo(new CyanRequestListener<UserInfoResp>() {
                    @Override
                    public void onRequestSucceeded(UserInfoResp userInfoResp) {
                        Picasso.with(MainActivity.this).load(userInfoResp.img_url).into(mUserPic);
                        mUsername.setText(userInfoResp.nickname);
                    }

                    @Override
                    public void onRequestFailed(CyanException e) {
                        // 正常情况下是没有网络
                        mUsername.setText("");
                        Toast.makeText(MainActivity.this, "没有网络！", Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (CyanException e) {
                // 用户没登录
                e.printStackTrace();
            }
        } else {
            mUsername.setText("点击登录");
            mUserPic.setImageResource(R.drawable.ic_soccer);
        }
    }

    private void showSignInDialog() {
        DialogFragment dialog = new LoginChooseFragment();
        dialog.show(getSupportFragmentManager(), "sign in");
    }

    @Override
    public void useQQ(DialogFragment dialog) {
        dialog.dismiss();
        mCyanSdk.startAuthorize(Constants.PlatFormId.PLATFORM_QQ, this);
    }

    @Override
    public void useWeiBo(DialogFragment dialog) {
        dialog.dismiss();
        mCyanSdk.startAuthorize(Constants.PlatFormId.PLATFORM_WEIBO, this);
    }

    @Override
    public void useSoHu(DialogFragment dialog) {
        dialog.dismiss();
        // 提供了qq,weibo,sohu,人人四个登录选择
        //mCyanSdk.startAuthorize(this);

        mCyanSdk.startAuthorize(Constants.PlatFormId.PLATFORM_SOHU, this);
    }

    // bottom nav item listener.
    private class BottomNavItemListener implements BottomNavigationView.OnNavigationItemSelectedListener {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
            hideAllFragment(fragmentTransaction);
            switch (item.getItemId()) {
                case R.id.bottom_menu_whatshot :
                    mToolbar.setTitle("动态");
                    if (mWhatsHotFragment == null) {
                        mWhatsHotFragment = new TrendingFragment();
                        fragmentTransaction.add(R.id.content_for_fragment, mWhatsHotFragment, WHATS_HOT_TAG);
                    } else {
                        fragmentTransaction.show(mWhatsHotFragment);
                    }
                    break;
                case R.id.bottom_menu_live_tv :
                    mToolbar.setTitle("赛程");
                    if (mLiveTvFragment == null) {
                        mLiveTvFragment = new ScheduleFragment();
                        fragmentTransaction.add(R.id.content_for_fragment, mLiveTvFragment, LIVE_TV_TAG);
                    } else {
                        fragmentTransaction.show(mLiveTvFragment);
                    }
                    break;
                case R.id.bottom_menu_sort :
                    mToolbar.setTitle("排名");
                    if (mSortFragment == null) {
                        mSortFragment = new SortFragment();
                        fragmentTransaction.add(R.id.content_for_fragment, mSortFragment, SORT_TAG);
                    } else {
                        fragmentTransaction.show(mSortFragment);
                    }
                    break;
                case R.id.bottom_menu_more :
                    mToolbar.setTitle("比赛集锦或录像");
                    if (mMoreFragment == null) {
                        mMoreFragment = new MatchVideosFragment();
                        fragmentTransaction.add(R.id.content_for_fragment, mMoreFragment, MORE_TAG);
                    } else {
                        fragmentTransaction.show(mMoreFragment);
                    }
                    break;
            }
            fragmentTransaction.commit();
            return true;
        }
    }

    // scroll to top listener
    private class ScrollToTop implements View.OnTouchListener {
        private long clickTime = 0;
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                Log.d(TAG + "menu item", "clicked");
                long now = System.currentTimeMillis();
                long interval = now - clickTime;
                clickTime = now;
                if (interval < 500 && mWhatsHotFragment != null) {
                    TrendingFragment fragment = (TrendingFragment) mWhatsHotFragment;
                    fragment.scrollToTop();
                }
            }
            return false;
        }
    }
}