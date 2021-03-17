package com.example.studywebview.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.studywebview.R;
import com.example.studywebview.data.StudyWebViewConstant;
import com.example.studywebview.data.WebViewStartParams;
import com.example.studywebview.mgr.WebViewProcessManager;

import androidx.annotation.Nullable;

/**
 * author: xujiajia
 * created on: 2021/1/30 11:16 AM
 * description:
 */
public class StudyWebViewActivity extends Activity {
  //constants
  private static final String TAG = "StudyWebViewActivity";
  //data
  private WebViewStartParams webViewStartParams;
  //ui
  private WebView wvContent;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_webview);

    processIntent();
    initViews();
    WebViewProcessManager.getInstance().init(this, webViewStartParams);
    initWebView();
    //打开页面,可以接受url==null，即将页面设置成空白
    if (webViewStartParams != null) {
      wvContent.loadUrl(webViewStartParams.startUrl);
    }
  }

  private void processIntent() {
    Intent intent = getIntent();
    if (intent == null) {
      return;
    }
    webViewStartParams = intent.getParcelableExtra(StudyWebViewConstant.PARAM_WEBVIEW_START_PARAMS);
  }

  @Override protected void onResume() {
    super.onResume();
    WebViewProcessManager.getInstance().onResume();
  }

  @Override protected void onPause() {
    super.onPause();
    WebViewProcessManager.getInstance().onPause();
  }

  @Override protected void onStart() {
    super.onStart();
    WebViewProcessManager.getInstance().onStart();
  }

  @Override protected void onStop() {
    super.onStop();
    WebViewProcessManager.getInstance().onStop();
  }

  private void initViews() {
    wvContent = findViewById(R.id.wv_content);
  }

  private void initWebView() {
    wvContent.setWebViewClient(new WebViewClient() {
      @Override public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        WebViewProcessManager.getInstance().onPageStarted(url);
      }

      @Override public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        WebViewProcessManager.getInstance().onPageFinished(url);
      }

      @Override public void onReceivedError(WebView view, WebResourceRequest request,
          WebResourceError error) {
        super.onReceivedError(view, request, error);
        WebViewProcessManager.getInstance()
            .onError(request.getUrl().toString(), error.getDescription().toString());
      }
    });
  }
}
