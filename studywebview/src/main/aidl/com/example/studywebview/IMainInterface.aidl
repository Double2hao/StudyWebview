// IMainInterface.aidl
package com.example.studywebview;

//用来调用主进程的Interface
interface IMainInterface {
void notifyBridge(String cmd,String param,com.example.studywebview.ICallBack callback);
}