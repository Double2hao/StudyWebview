package com.example.studywebview.data;

import android.view.ViewGroup;

import com.example.studywebview.INotifyListener;

/**
 * author: xujiajia
 * created on: 2021/1/30 2:50 PM
 * description:
 */
public class BaseStudyWebViewComponent implements IDelegateLifeCycle {
  ViewGroup contentView = null;

  public String[] supportCmds(){
    return null;
  }

  public void notify(String cmd, String param, INotifyListener listener){

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
