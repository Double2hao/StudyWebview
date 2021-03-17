package com.example.studywebview.mgr;

import java.util.HashSet;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;

import com.example.studywebview.ICallBack;
import com.example.studywebview.IWebViewInterface;
import com.example.studywebview.activity.StudyWebViewActivity;
import com.example.studywebview.data.BaseStudyWebViewBridges;
import com.example.studywebview.data.BaseStudyWebViewComponent;
import com.example.studywebview.data.INotifyListener;
import com.example.studywebview.data.StudyWebViewConstant;
import com.example.studywebview.data.WebViewStartParams;
import com.example.studywebview.service.WebViewProcessService;

/**
 * author: xujiajia
 * created on: 2021/1/30 11:33 AM
 * description:
 * 主进程
 */
public class MainProcessManager {

  //constants
  private static final String TAG = "MainProcessManager";
  //data
  private final HashSet<BaseStudyWebViewBridges> setBridges = new HashSet<>();
  private final HashSet<String> setComponentClassName = new HashSet<>();
  //binder
  private IWebViewInterface notifyComponentBinder = null;
  private final ServiceConnection serviceConnection = new ServiceConnection() {
    @Override public void onServiceConnected(ComponentName name, IBinder service) {
      Log.d(TAG, "onServiceConnected");
      notifyComponentBinder = IWebViewInterface.Stub.asInterface(service);
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

  public void launchUrl(Context context, String url) {
    if (context == null) {
      return;
    }
    Intent intent = new Intent(context, StudyWebViewActivity.class);
    intent.putExtra(StudyWebViewConstant.PARAM_WEBVIEW_START_PARAMS, createStartParams(url));
    context.startActivity(intent);
    bindWebViewProcessService(context);
  }

  public void registerBridges(BaseStudyWebViewBridges bridges) {
    setBridges.add(bridges);
    Log.i(TAG, "registerBridges: " + bridges.getClass().getName());
  }

  public void registerComponent(Class<? extends BaseStudyWebViewComponent> componentClass) {
    setComponentClassName.add(componentClass.getName());
  }

  private WebViewStartParams createStartParams(String url) {
    WebViewStartParams params = new WebViewStartParams(url);
    params.arrayComponentClassName = setComponentClassName.toArray(new String[0]);
    return params;
  }

  private void bindWebViewProcessService(Context context) {
    if (context == null) {
      return;
    }
    Log.d(TAG, "bindWebViewProcessService");
    Intent intent = new Intent(context, WebViewProcessService.class);
    context.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
  }

  //调用webview进程的component
  public void notifyComponent(String cmd, String param, INotifyListener listener) {
    if (notifyComponentBinder == null) {
      Log.d(TAG, "notifyComponent notifyComponentBinder==null");
      return;
    }
    try {
      notifyComponentBinder.notifyComponent(cmd, param, new ICallBack.Stub() {
        @Override public void callback(String response) throws RemoteException {
          if (listener != null) {
            listener.onResponse(response);
          }
        }
      });
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  //调用当前进程的bridges
  public void notifyBridge(String cmd, String param, ICallBack callBack) {
    for (BaseStudyWebViewBridges bridges : setBridges) {
      String[] supportCmds = bridges.supportCmds();
      if (supportCmds == null) {
        return;
      }
      for (String i : supportCmds) {
        if (TextUtils.equals(i, cmd)) {
          bridges.notify(cmd, param, new INotifyListener() {
            @Override public void onResponse(String response) {
              if (callBack == null) {
                return;
              }
              try {
                callBack.callback(response);
              } catch (Exception e) {
                e.printStackTrace();
              }
            }
          });
          break;
        }
      }
    }
  }
}
