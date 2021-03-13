package com.example.studywebview.binder;

import android.os.IBinder;
import android.os.RemoteException;

import com.example.studywebview.IMainInterface;
import com.example.studywebview.INotifyListener;
import com.example.studywebview.IWebViewInterface;
import com.example.studywebview.mgr.MainProcessManager;
import com.example.studywebview.mgr.WebViewProcessManager;

/**
 * author: xujiajia
 * created on: 2021/3/13 4:25 PM
 * description:
 */
public class NotifyBridgesBinder extends IMainInterface.Stub {

  @Override public void notifyBridge(String cmd, String param, INotifyListener listener)
      throws RemoteException {
    MainProcessManager.getInstance().notifyBridge(cmd, param, listener);
  }

}
