package com.app.football.main.whatshot.detail;



import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.app.football.R;
import com.app.football.util.MyUtil;
import com.sohu.cyan.android.sdk.api.CyanSdk;
import com.squareup.picasso.Picasso;



public class TrendingDetailActivity extends AppCompatActivity implements DetailContract.View {

    //private static final String TAG = "TrendingDetailActivity";
    private WebView mWebView;
    private ProgressBar mProgressBar;
    private Bundle mBundle;
    private CollapsingToolbarLayout mToolbarLayout;
    private ImageView mImageView;
    private FloatingActionButton mFloatingActionButton;
    private DetailPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whats_hot_detail);
        initView();
        shareArticle();

        // 加载文章内容
        mPresenter.init(mBundle.getString("url"));
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // 网页视频闪烁问题
        getWindow().setFormat(PixelFormat.TRANSLUCENT);

        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar_2);
        //mToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        mWebView = (WebView) findViewById(R.id.web_view);
        mBundle = getIntent().getExtras();
        mToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        mImageView = (ImageView) findViewById(R.id.toolbar_image);
        mPresenter = new DetailPresenter(this);
        // 加载评论框
        CyanSdk.getInstance(this).addCommentToolbar(this, mBundle.getString("id"), mBundle.getString("title"), mBundle.getString("url"));
    }


    @Override
    public void showWeb(String html) {
        // title and top image.
        mToolbarLayout.setTitle(mBundle.getString("title"));
        // 跟顶部slider尺寸一致, 有的时候会崩溃，可能是图片链接的问题。
        Picasso.with(this).load(MyUtil.adjustImageSize(mBundle.getString("thumb"))).into(mImageView);

        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        // 允许使用混合模式
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        // 编码问题
        settings.setDefaultTextEncodingName("utf8");
        mWebView.setWebViewClient(new WebClient2());
        // loadData()不加载图片
        mWebView.loadDataWithBaseURL(null, html, "text/html", "charset=utf8", null);
    }

    @Override
    public void shareArticle() {
        // TODO
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, mBundle.getString("url"));
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });

    }

    @Override
    public void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void closeProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mWebView = null;
        mBundle = null;
    }

    private class WebClient2 extends WebViewClient {

        @SuppressWarnings("deprecation")
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            view.loadUrl(request.getUrl().toString());
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            closeProgressBar();
        }

    }

}

