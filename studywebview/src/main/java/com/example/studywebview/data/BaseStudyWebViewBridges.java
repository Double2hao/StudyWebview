package com.example.studywebview.data;

import com.example.studywebview.mgr.MainProcessManager;

/**
 * author: xujiajia
 * created on: 2021/3/13 6:32 PM
 * description:
 */
public abstract class BaseStudyWebViewBridges {

  public abstract String[] supportCmds();

  public abstract void notify(String cmd, String param, INotifyListener listener);

  public void notifyComponent(String cmd, String param, INotifyListener listener) {
    MainProcessManager.getInstance().notifyComponent(cmd, param, listener);
  }
}
