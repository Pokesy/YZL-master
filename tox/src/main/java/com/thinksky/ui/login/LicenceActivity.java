/*
 * 文件名: LicenceActivity
 * 版    权：  Copyright Hengrtech Tech. Co. Ltd. All Rights Reserved.
 * 描    述: [该类的简要描述]
 * 创建人: zhaozeyang
 * 创建时间:16/9/12
 * 
 * 修改人：
 * 修改时间:
 * 修改内容：[修改内容]
 */
package com.thinksky.ui.login;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
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
 * @version [Taobei Client V20160411, 16/9/12]
 */
public class LicenceActivity extends BaseBActivity {
  @Bind(R.id.title_bar)
  TitleBar titleBar;
  @Bind(R.id.content)
  TextView content;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_licence);
    ButterKnife.bind(this);

    titleBar.setLeftImgMenu(R.drawable.icon_title_bar_back, new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });

    titleBar.setMiddleTitle(R.string.activity_licence);

    content.setText("");
  }
}
