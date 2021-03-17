package com.example.studywebview.component;

import android.widget.Toast;

import com.example.studywebview.constant.WebViewConstant;
import com.example.studywebview.data.BaseStudyWebViewComponent;
import com.example.studywebview.data.INotifyListener;

/**
 * author: xujiajia
 * created on: 2021/3/16 8:46 PM
 * description:
 */
public class LifeCycleComponent extends BaseStudyWebViewComponent {
  @Override public String[] supportCmds() {
    return new String[] { WebViewConstant.CMD_SHOW_TOAST };
  }

  @Override public void notify(String cmd, String param, INotifyListener listener) {
    switch (cmd) {
      case WebViewConstant.CMD_SHOW_TOAST:
        handleShowToast(cmd, param, listener);
        break;
    }
  }

  private void handleShowToast(String cmd, String param, INotifyListener listener) {
    Toast.makeText(mActivity, param, Toast.LENGTH_LONG).show();
    if (listener != null) {
      listener.onResponse("show toast success");
    }
  }

  @Override public void onStart() {
    notifyBridges(WebViewConstant.CMD_ON_START, null, null);
  }

  @Override public void onStop() {
    notifyBridges(WebViewConstant.CMD_ON_STOP, null, null);
  }
}
