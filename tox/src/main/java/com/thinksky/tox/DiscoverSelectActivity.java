package com.thinksky.tox;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.thinksky.holder.BaseBActivity;
import com.tox.BaseApi;


public class DiscoverSelectActivity extends BaseBActivity {
  private static final int REQUEST_CODE_DISCOVER_SEND = 1;
  private String session_id;
  private String userUid;
  private BaseApi baseApi;
  private final int FRIEND = 0;
  private final int FISH = 1;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    baseApi = new BaseApi();
    session_id = baseApi.getSeesionId();
    userUid = baseApi.getUid();
    initView();
  }

  private void initView() {
    setContentView(R.layout.activity_discover_select);
    ImageView back = (ImageView) findViewById(R.id.back_menu);
    back.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        DiscoverSelectActivity.this.finish();
      }
    });
    //鱼友
    TextView mark = (TextView) findViewById(R.id.button_friend);
    mark.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        Intent intent = new Intent(DiscoverSelectActivity.this, DiscoverSendActivity.class);
        intent.putExtra("fish", FRIEND);
        startActivityForResult(intent, REQUEST_CODE_DISCOVER_SEND);

      }
    });
    // 渔场
    TextView mark1 = (TextView) findViewById(R.id.button_fishing);
    mark1.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        Intent intent = new Intent(DiscoverSelectActivity.this, DiscoverSendActivity.class);
        intent.putExtra("fish", FISH);
        startActivityForResult(intent, REQUEST_CODE_DISCOVER_SEND);

      }
    });
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    switch (requestCode) {
      case REQUEST_CODE_DISCOVER_SEND:
        if (resultCode == RESULT_OK) {
          finish();
        }
        break;
    }
  }
}
