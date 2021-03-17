// IWebViewInterface.aidl
package com.example.studywebview;

//用来调用webview进程的interface
interface IWebViewInterface {
void notifyComponent(String cmd,String param,com.example.studywebview.ICallBack callback);
}