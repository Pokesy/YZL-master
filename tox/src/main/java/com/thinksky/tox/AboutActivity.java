package com.thinksky.tox;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.thinksky.holder.BaseBActivity;

public class AboutActivity extends BaseBActivity {
  private View fanhui;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.setting_guanyu);

    fanhui = findViewById(R.id.guanyuqibai_back);
    fanhui.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });
    PackageManager manager = getPackageManager();
    try {
      PackageInfo info = manager.getPackageInfo(getPackageName(), 0);
      String versionName = info.versionName;
      ((TextView) findViewById(R.id.version)).setText(versionName);
    } catch (PackageManager.NameNotFoundException e) {
      e.printStackTrace();
    }
  }


}
