package com.example.studywebview.data;

/**
 * author: xujiajia
 * created on: 2021/1/30 3:53 PM
 * description:
 */
public interface IDelegateLifeCycle {

  void onInit();

  void onResume();

  void onPause();

  void onStart();

  void onStop();

  void onPageStarted(String url);

  void onPageFinished(String url);

  void onError(String url, String errorMessage);
}
