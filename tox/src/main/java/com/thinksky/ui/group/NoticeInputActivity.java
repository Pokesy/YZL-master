/*
 * 文件名: NoticeInputActivity
 * 版    权：  Copyright Hengrtech Tech. Co. Ltd. All Rights Reserved.
 * 描    述: [该类的简要描述]
 * 创建人: zhaozeyang
 * 创建时间:16/8/22
 * 
 * 修改人：
 * 修改时间:
 * 修改内容：[修改内容]
 */
package com.thinksky.ui.group;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.thinksky.holder.BaseBActivity;
import com.thinksky.tox.R;
import com.thinksky.ui.common.TitleBar;

/**
 * [一句话功能简述]<BR>
 * [功能详细描述]
 *
 * @author zhaozeyang
 * @version [Taobei Client V20160411, 16/8/22]
 */
public class NoticeInputActivity extends BaseBActivity {
  public static final String BUNDLE_KEY_NOTICE = "bundle_key_notice";
  private static final String BUNDLE_KEY_GROUP_NOTICE = "group_notice";
  @Bind(R.id.title_bar)
  TitleBar titleBar;
  @Bind(R.id.input)
  EditText input;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_notice_input);
    ButterKnife.bind(this);
    initView();
  }

  private void initView() {
    String notice = getIntent().getStringExtra(BUNDLE_KEY_GROUP_NOTICE);
    input.setText(TextUtils.isEmpty(notice) ? "" : notice);

    titleBar.setMiddleTitle(R.string.activity_notice_input_title);
    titleBar.setLeftImgMenu(R.drawable.arrow_left, new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        onBackPressed();
      }
    });

    titleBar.setRightTextBtn(R.string.activity_notice_input_title_btn_complete, new View
        .OnClickListener() {


      @Override
      public void onClick(View v) {
        Intent data = new Intent();
        data.putExtra(BUNDLE_KEY_NOTICE, input.getText().toString());
        setResult(RESULT_OK, data);
        finish();
      }
    });
  }

  @Override
  public void onBackPressed() {
    super.onBackPressed();
  }

  public static Intent makeIntent(Context context, String notice) {
    Intent intent = new Intent(context, NoticeInputActivity.class);
    intent.putExtra(BUNDLE_KEY_GROUP_NOTICE, notice);
    return intent;
  }
}
