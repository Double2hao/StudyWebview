package com.example.studywebview.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

/**
 * author: xujiajia
 * created on: 2021/3/13 4:18 PM
 * description:
 */
public class MainProcessService extends Service {
  @Nullable @Override public IBinder onBind(Intent intent) {
    return null;
  }
}
