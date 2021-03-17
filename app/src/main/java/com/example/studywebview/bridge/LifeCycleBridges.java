package com.example.studywebview.bridge;

import android.util.Log;

import com.example.studywebview.constant.WebViewConstant;
import com.example.studywebview.data.BaseStudyWebViewBridges;
import com.example.studywebview.data.INotifyListener;

/**
 * author: xujiajia
 * created on: 2021/3/16 8:45 PM
 * description:
 */
public class LifeCycleBridges extends BaseStudyWebViewBridges {
  //constant
  private static final String TAG = "LifeCycleBridges";

  @Override public String[] supportCmds() {
    return new String[] {
        WebViewConstant.CMD_ON_START,
        WebViewConstant.CMD_ON_STOP
    };
  }

  @Override public void notify(String cmd, String param, INotifyListener listener) {
    switch (cmd) {
      case WebViewConstant.CMD_ON_START:
        handleOnStart(cmd, param, listener);
        break;
      case WebViewConstant.CMD_ON_STOP:
        handleOnStop(cmd, param, listener);
        break;
    }
  }

  private void handleOnStart(String cmd, String param, INotifyListener listener) {
    notifyComponent(WebViewConstant.CMD_SHOW_TOAST, "toast onStart", new INotifyListener() {
      @Override public void onResponse(String response) {
        Log.i(TAG, "on start response :" + response);
      }
    });
  }

  private void handleOnStop(String cmd, String param, INotifyListener listener) {
    notifyComponent(WebViewConstant.CMD_SHOW_TOAST, "toast onStop", new INotifyListener() {
      @Override public void onResponse(String response) {
        Log.i(TAG, "on stop response :" + response);
      }
    });
  }
}
