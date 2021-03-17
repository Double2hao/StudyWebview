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
import android.view.ViewGroup;

import com.example.studywebview.ICallBack;
import com.example.studywebview.IMainInterface;
import com.example.studywebview.activity.StudyWebViewActivity;
import com.example.studywebview.data.BaseStudyWebViewComponent;
import com.example.studywebview.data.IComponentLifeCycle;
import com.example.studywebview.data.INotifyListener;
import com.example.studywebview.data.WebViewStartParams;
import com.example.studywebview.service.MainProcessService;

import androidx.annotation.NonNull;

/**
 * author: xujiajia
 * created on: 2021/1/30 11:32 AM
 * description:
 * 子进程
 */
public class WebViewProcessManager implements IComponentLifeCycle {

  //constant
  private static final String TAG = "WebViewProcessManager";
  //data
  private StudyWebViewActivity activity;//不需要弱引用，delegate与activity生命周期一样长
  private WebViewStartParams webViewStartParams;
  private final HashSet<BaseStudyWebViewComponent> setComponent = new HashSet<>();

  //binder
  private IMainInterface notifyBridgesBinder = null;
  private final ServiceConnection serviceConnection = new ServiceConnection() {
    @Override public void onServiceConnected(ComponentName name, IBinder service) {
      Log.d(TAG, "onServiceConnected");
      notifyBridgesBinder = IMainInterface.Stub.asInterface(service);
    }

    @Override public void onServiceDisconnected(ComponentName name) {

    }
  };

  private WebViewProcessManager() {
  }

  private static class Host {
    private static final WebViewProcessManager instance = new WebViewProcessManager();
  }

  public static WebViewProcessManager getInstance() {
    return Host.instance;
  }

  public void init(@NonNull StudyWebViewActivity activity, WebViewStartParams webViewStartParams) {
    this.activity = activity;
    this.webViewStartParams = webViewStartParams;

    bindMainProcessService();
    registerComponents();
    onInit();
  }

  private void bindMainProcessService() {
    Log.d(TAG, "bindMainProcessService");
    Intent intent = new Intent(this.activity, MainProcessService.class);
    this.activity.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
  }

  public void registerComponents() {
    if (webViewStartParams == null
        || webViewStartParams.arrayComponentClassName == null
        || webViewStartParams.arrayComponentClassName.length == 0) {
      return;
    }
    try {
      for (String className : webViewStartParams.arrayComponentClassName) {
        Class recoveryClass = Class.forName(className);
        BaseStudyWebViewComponent component =
            (BaseStudyWebViewComponent) recoveryClass.newInstance();
        component.mActivity = activity;
        component.mContentView = (ViewGroup) activity.getWindow().getDecorView().getRootView();
        setComponent.add(component);
        Log.i(TAG, "registerComponent: " + className);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  //处理当前进程的component
  public void notifyComponent(String cmd, String param, ICallBack callBack) {
    for (BaseStudyWebViewComponent component : setComponent) {
      String[] supportCmds = component.supportCmds();
      if (supportCmds == null) {
        return;
      }
      for (String i : supportCmds) {
        if (TextUtils.equals(i, cmd)) {
          component.notify(cmd, param, new INotifyListener() {
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

  //调用main进程的bridges
  public void notifyBridges(String cmd, String param, INotifyListener listener) {
    if (notifyBridgesBinder == null) {
      Log.d(TAG,"notifyBridges notifyBridgesBinder==null");
      return;
    }
    try {
      notifyBridgesBinder.notifyBridge(cmd, param, new ICallBack.Stub() {
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

  @Override public void onInit() {
    for (BaseStudyWebViewComponent component : setComponent) {
      component.onInit();
    }
  }

  @Override public void onResume() {
    for (BaseStudyWebViewComponent component : setComponent) {
      component.onResume();
    }
  }

  @Override public void onPause() {
    for (BaseStudyWebViewComponent component : setComponent) {
      component.onPause();
    }
  }

  @Override public void onStart() {
    for (BaseStudyWebViewComponent component : setComponent) {
      component.onStart();
    }
  }

  @Override public void onStop() {
    for (BaseStudyWebViewComponent component : setComponent) {
      component.onStop();
    }
  }

  @Override public void onPageStarted(String url) {
    for (BaseStudyWebViewComponent component : setComponent) {
      component.onPageStarted(url);
    }
  }

  @Override public void onPageFinished(String url) {
    for (BaseStudyWebViewComponent component : setComponent) {
      component.onPageFinished(url);
    }
  }

  @Override public void onError(String url, String errorMessage) {
    for (BaseStudyWebViewComponent component : setComponent) {
      component.onError(url, errorMessage);
    }
  }
}
