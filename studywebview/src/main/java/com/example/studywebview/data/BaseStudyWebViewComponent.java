package com.example.studywebview.data;

import android.view.ViewGroup;

import com.example.studywebview.activity.StudyWebViewActivity;
import com.example.studywebview.mgr.WebViewProcessManager;

/**
 * author: xujiajia
 * created on: 2021/1/30 2:50 PM
 * description:
 */
public abstract class BaseStudyWebViewComponent implements IComponentLifeCycle {
  public ViewGroup mContentView = null;
  public StudyWebViewActivity mActivity = null;

  public abstract String[] supportCmds();

  public abstract void notify(String cmd, String param, INotifyListener listener);

  public void notifyBridges(String cmd, String param, INotifyListener listener) {
    WebViewProcessManager.getInstance().notifyBridges(cmd, param, listener);
  }

  @Override public void onInit() {

  }

  @Override public void onResume() {

  }

  @Override public void onPause() {

  }

  @Override public void onStart() {

  }

  @Override public void onStop() {

  }

  @Override public void onPageStarted(String url) {

  }

  @Override public void onPageFinished(String url) {

  }

  @Override public void onError(String url, String errorMessage) {

  }
}
