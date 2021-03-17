package com.example.studywebview.binder;

import android.os.RemoteException;

import com.example.studywebview.ICallBack;
import com.example.studywebview.IWebViewInterface;
import com.example.studywebview.mgr.WebViewProcessManager;

/**
 * author: xujiajia
 * created on: 2021/3/13 4:25 PM
 * description:
 */
public class NotifyComponentBinder extends IWebViewInterface.Stub {

  @Override public void notifyComponent(String cmd, String param, ICallBack callBack)
      throws RemoteException {
    WebViewProcessManager.getInstance().notifyComponent(cmd, param, callBack);
  }
}
