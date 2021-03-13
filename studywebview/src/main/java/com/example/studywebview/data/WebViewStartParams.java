package com.example.studywebview.data;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/**
 * author: xujiajia
 * created on: 2021/1/30 3:33 PM
 * description:
 */
public class WebViewStartParams implements Parcelable {
  //constants
  private static final String TAG = "WebViewStartParams";
  //data
  public String startUrl;
  public String[] arrayComponentClassName;

  public WebViewStartParams(String startUrl) {
    this.startUrl = startUrl;
  }

  private WebViewStartParams(Parcel in) {
    try {
      startUrl = in.readString();
      in.readStringArray(arrayComponentClassName);
    } catch (Exception e) {
      Log.e(TAG, "WebViewStartParams error:" + e.getMessage());
    }
  }

  private static final Creator<WebViewStartParams> CREATOR = new Creator<WebViewStartParams>() {
    @Override
    public WebViewStartParams createFromParcel(Parcel in) {
      return new WebViewStartParams(in);
    }

    @Override
    public WebViewStartParams[] newArray(int size) {
      return new WebViewStartParams[size];
    }
  };

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(startUrl);
    dest.writeStringArray(arrayComponentClassName);
  }
}
