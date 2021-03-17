package com.example.studywebview.binder;

import android.os.RemoteException;

import com.example.studywebview.ICallBack;
import com.example.studywebview.IMainInterface;
import com.example.studywebview.mgr.MainProcessManager;

/**
 * author: xujiajia
 * created on: 2021/3/13 4:25 PM
 * description:
 */
public class NotifyBridgesBinder extends IMainInterface.Stub {

  @Override public void notifyBridge(String cmd, String param, ICallBack callBack)
      throws RemoteException {
    MainProcessManager.getInstance().notifyBridge(cmd, param, callBack);
  }
}
