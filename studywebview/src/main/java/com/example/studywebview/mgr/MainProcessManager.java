package com.example.studywebview.mgr;

import java.util.HashSet;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.example.studywebview.INotifyListener;
import com.example.studywebview.IWebViewInterface;
import com.example.studywebview.activity.StudyWebViewActivity;
import com.example.studywebview.binder.NotifyComponentBinder;
import com.example.studywebview.data.BaseStudyWebViewBridges;
import com.example.studywebview.data.WebViewStartParams;
import com.example.studywebview.service.WebViewProcessService;

/**
 * author: xujiajia
 * created on: 2021/1/30 11:33 AM
 * description:
 * 主进程
 */
public class MainProcessManager {

  //data
  private final HashSet<BaseStudyWebViewBridges> setBridges = new HashSet<>();
  //binder
  private NotifyComponentBinder notifyComponentBinder = null;
  private ServiceConnection serviceConnection = new ServiceConnection() {
    @Override public void onServiceConnected(ComponentName name, IBinder service) {
      notifyComponentBinder = (NotifyComponentBinder) IWebViewInterface.Stub.asInterface(service);
    }

    @Override public void onServiceDisconnected(ComponentName name) {

    }
  };

  private MainProcessManager() {
  }

  private static class Host {
    private static final MainProcessManager instance = new MainProcessManager();
  }

  public static MainProcessManager getInstance() {
    return Host.instance;
  }

  public void launchUrl(Context context) {
    if (context == null) {
      return;
    }
    Intent intent = new Intent(context, StudyWebViewActivity.class);
    context.startActivity(intent);
    bindWebViewProcessService(context);
  }

  private void bindWebViewProcessService(Context context) {
    if (context == null) {
      return;
    }
    Intent intent = new Intent(context, WebViewProcessService.class);
    context.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
  }

  //调用webview进程的component
  public void notifyComponent(String cmd, String param, INotifyListener listener) {
    if (notifyComponentBinder == null) {
      return;
    }
    try {
      notifyComponentBinder.notifyComponent(cmd, param, listener);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  //调用当前进程的bridges
  public void notifyBridge(String cmd, String param, INotifyListener listener) {

  }
}
