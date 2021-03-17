package com.example.studywebview.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.example.studywebview.binder.NotifyComponentBinder;

import androidx.annotation.Nullable;

/**
 * author: xujiajia
 * created on: 2021/3/13 2:31 PM
 * description:
 */
public class WebViewProcessService extends Service {

  @Nullable @Override
  public IBinder onBind(Intent intent) {
    return new NotifyComponentBinder();
  }
}
