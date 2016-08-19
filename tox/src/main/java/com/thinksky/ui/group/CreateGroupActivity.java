/*
 * 文件名: CreateGroupActivity
 * 版    权：  Copyright Hengrtech Tech. Co. Ltd. All Rights Reserved.
 * 描    述: [该类的简要描述]
 * 创建人: zhaozeyang
 * 创建时间:16/8/19
 * 
 * 修改人：
 * 修改时间:
 * 修改内容：[修改内容]
 */
package com.thinksky.ui.group;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.thinksky.holder.BaseApplication;
import com.thinksky.holder.BaseBActivity;
import com.thinksky.injection.GlobalModule;
import com.thinksky.net.rpc.service.AppService;
import com.thinksky.serviceinjection.DaggerServiceComponent;
import com.thinksky.serviceinjection.ServiceModule;
import com.thinksky.tox.R;
import com.thinksky.ui.common.TitleBar;
import javax.inject.Inject;

/**
 * [一句话功能简述]<BR>
 * [功能详细描述]
 *
 * @author zhaozeyang
 * @version [Taobei Client V20160411, 16/8/19]
 */
public class CreateGroupActivity extends BaseBActivity {
  @Bind(R.id.title_bar)
  TitleBar titleBar;
  @Bind(R.id.logo)
  ImageView logo;
  @Bind(R.id.group_name)
  TextView groupName;
  @Bind(R.id.group_category)
  TextView groupCategory;
  @Bind(R.id.group_type)
  TextView groupType;
  @Bind(R.id.description)
  ImageView description;

  @Inject
  AppService mAppService;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    inject();
    setContentView(R.layout.activity_create_group);
    ButterKnife.bind(this);
    initView();
  }

  private void initView() {
    titleBar.setMiddleTitle(R.string.activity_create_group_title);
    titleBar.setLeftImgMenu(R.drawable.arrow_left, new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });
  }

  private void inject() {
    DaggerServiceComponent.builder().globalModule(new GlobalModule((BaseApplication)
        getApplication())).serviceModule(new ServiceModule())
        .build().inject(this);
  }

  @OnClick({R.id.menu_logo, R.id.menu_group_name, R.id.menu_group_category, R.id.menu_group_type,
      R.id.menu_description})
  public void onClick(View view) {
    switch (view.getId()) {
      case R.id.menu_logo:
        break;
      case R.id.menu_group_name:
        break;
      case R.id.menu_group_category:
        break;
      case R.id.menu_group_type:
        break;
      case R.id.menu_description:
        break;
    }
  }
}
