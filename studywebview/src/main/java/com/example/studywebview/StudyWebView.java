package com.example.studywebview;

import android.content.Context;

import com.example.studywebview.data.BaseStudyWebViewBridges;
import com.example.studywebview.data.BaseStudyWebViewComponent;
import com.example.studywebview.mgr.MainProcessManager;

/**
 * author: xujiajia
 * created on: 2021/3/13 2:52 PM
 * description:
 */
public class StudyWebView {
  //启动webview，并且加载某个url
  public static void launchUrl(Context context, String url) {
    MainProcessManager.getInstance().launchUrl(context, url);
  }

  //注册bridges，运行在主进程
  public static void registerBridges(BaseStudyWebViewBridges bridges) {
    MainProcessManager.getInstance().registerBridges(bridges);
  }

  //注册component，运行在webview进程
  public static void registerComponent(Class<? extends BaseStudyWebViewComponent> componentClass) {
    MainProcessManager.getInstance().registerComponent(componentClass);
  }
}
