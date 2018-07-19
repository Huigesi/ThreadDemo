package com.example.administrator.threaddemo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WebViewActivity extends AppCompatActivity {
    @BindView(R.id.web_view)
    WebView webView;
    private CookieManager mCookieMgr;
    private String url;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_view);
        ButterKnife.bind(this);
        initWebView();
        Intent intent =getIntent();
        url=intent.getStringExtra("url");
        webView.loadUrl(url);

    }

    private void initWebView() {
            mCookieMgr = CookieManager.getInstance();
            mCookieMgr.setAcceptCookie(true);
            WebSettings webSettings = webView.getSettings();
            webSettings.setJavaScriptEnabled(true); // 启用支持javascript
            webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);// 优先使用缓存
            webSettings.setAllowFileAccess(true);// 可以访问文件
            webSettings.setBuiltInZoomControls(true);// 支持缩放
            webSettings.setDomStorageEnabled(true);
            webSettings.setSupportZoom(true);
            webSettings.setUseWideViewPort(true);
            webSettings.setLoadWithOverviewMode(true);
            if (android.os.Build.VERSION.SDK_INT >= 11) {
                webSettings.setPluginState(WebSettings.PluginState.ON);
                webView.getSettings().setDisplayZoomControls(false);// 支持缩放
            }
        webView.setWebViewClient(mWebViewClient);
        webView.setWebChromeClient(mChromeClient);

    }
    private WebChromeClient mChromeClient = new WebChromeClient() {
        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
        }

        @Override
        public void onReceivedIcon(WebView view, Bitmap icon) {
            super.onReceivedIcon(view, icon);
            // onWebIcon(view, icon);
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) { // 进度
            super.onProgressChanged(view, newProgress);
        }
    };
    private WebViewClient mWebViewClient = new WebViewClient() {
        @SuppressLint("NewApi")
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, final String url) {
            return false;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);

            view.clearView();
        }
    };
}
