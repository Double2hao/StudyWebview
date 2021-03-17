package com.example.studywebview.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.studywebview.R;
import com.example.studywebview.StudyWebView;
import com.example.studywebview.bridge.LifeCycleBridges;
import com.example.studywebview.component.LifeCycleComponent;

public class MainActivity extends Activity {

  //constants
  private static final String URL_TEST = "https://www.bilibili.com";
  //ui
  private Button btnDemo;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    initViews();
    initStudyWebView();
  }

  private void initViews() {
    btnDemo = findViewById(R.id.btn_demo);
    btnDemo.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        StudyWebView.launchUrl(MainActivity.this, URL_TEST);
      }
    });
  }

  private void initStudyWebView() {
    //bridges
    StudyWebView.registerBridges(new LifeCycleBridges());
    //component
    StudyWebView.registerComponent(LifeCycleComponent.class);
  }
}