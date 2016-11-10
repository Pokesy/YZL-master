package com.thinksky.fragment;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.thinksky.holder.BaseBActivity;
import com.thinksky.tox.R;


public class VideoPlayActivity extends BaseBActivity {
  @Bind(R.id.video_web)
  WebView videoWeb;
  private String url;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.video_play);
    ButterKnife.bind(this);

    Bundle id = this.getIntent().getExtras();
    url = id.getString("url");
    showProgressDialog("", true);
    initVideoWeb();
  }

  private void initVideoWeb() {
    WebSettings webSettings = videoWeb.getSettings();
    //设置支持JS
    webSettings.setJavaScriptEnabled(true);
    //设置加载进来的页面自适应手机屏幕
    webSettings.setUseWideViewPort(true);
    //设置WebView加载的页面的模式，适应屏幕宽度
    webSettings.setLoadWithOverviewMode(true);
    //设置支持缩放
    webSettings.setSupportZoom(true);
    webSettings.setBuiltInZoomControls(true);
    webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
    webSettings.setAllowFileAccess(true);
    webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
    webSettings.setDomStorageEnabled(true);
    webSettings.setDatabaseEnabled(true);

    //添加JS调用java接口
    //mWebView.addJavascriptInterface(new DealJs(), JS_INTERFACE_NAME);
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
      //不显示缩放的按钮
      webSettings.setDisplayZoomControls(false);
    }
    videoWeb.loadUrl(url);
    videoWeb.setWebViewClient(new WebViewClient() {
      @Override
      public boolean shouldOverrideUrlLoading(final WebView view, final String url) {
        //载入网页  根据URL里的关键字符跳转到应用内的页面
        //videoWeb.loadUrl("http://player.youku.com/embed/XNzM5OTA1NjIw");
        //videoWeb.loadUrl("http://www.baidu.com");
        return true;
      }

      @Override
      public void onReceivedError(WebView view, int errorCode, String description,
                                  String failingUrl) {
        //错误提示
        Toast.makeText(VideoPlayActivity.this, "error", Toast.LENGTH_SHORT).show();
      }
    });
    videoWeb.setWebChromeClient(new WebChromeClient() {
      @Override
      public void onProgressChanged(WebView view, int progress) {
        //载入进度改变而触发
        if (progress == 100) {
          //如果全部载入,隐藏进度对话框
          closeProgressDialog();
        }
        super.onProgressChanged(view, progress);
      }

      @Override
      public void onReceivedTitle(WebView view, String title) {
        super.onReceivedTitle(view, title);
        //setMiddleTitle(title);
      }
    });
  }

  @Override
  public void onBackPressed() {
    super.onBackPressed();
  }

  @Override
  protected void onDestroy() {
    videoWeb.destroy();
    super.onDestroy();
  }
}