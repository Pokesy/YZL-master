/*
 * 文件名: MyProfileActivity
 * 版    权：  Copyright Hengrtech Tech. Co. Ltd. All Rights Reserved.
 * 描    述: [该类的简要描述]
 * 创建人: zhaozeyang
 * 创建时间:16/8/24
 * 
 * 修改人：
 * 修改时间:
 * 修改内容：[修改内容]
 */
package com.thinksky.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import com.squareup.otto.Subscribe;
import com.thinksky.holder.BaseApplication;
import com.thinksky.holder.BaseBActivity;
import com.thinksky.net.rpc.model.UserInfoModel;
import com.thinksky.tox.MainActivity;
import com.thinksky.tox.R;
import com.thinksky.tox.SettingActivity;
import com.thinksky.ui.LoginSession;
import com.thinksky.ui.common.TitleBar;
import com.thinksky.utils.imageloader.ImageLoader;
import com.tox.Url;
import de.hdodenhof.circleimageview.CircleImageView;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * [一句话功能简述]<BR>
 * [功能详细描述]
 *
 * @author zhaozeyang
 * @version [Taobei Client V20160411, 16/8/24]
 */
public class MyProfileActivity extends BaseBActivity {
  @Bind(R.id.avatar)
  CircleImageView avatar;
  @Bind(R.id.nick_name)
  TextView nickName;
  @Bind(R.id.gender)
  ImageView gender;
  @Bind(R.id.fans_count)
  TextView fansCount;
  @Bind(R.id.follow_count)
  TextView followCount;
  @Bind(R.id.area_value)
  TextView areaValue;
  @Bind(R.id.signature_value)
  TextView signatureValue;
  @Bind(R.id.enter_map)
  TextView enterMap;
  @Bind(R.id.title_bar)
  TitleBar titleBar;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.profile_drawer_layout);
    ButterKnife.bind(this);
    initView();
    setData();
  }

  @OnClick({R.id.btn_fans, R.id.btn_follow, R.id.menu_message, R.id.menu_collect, R.id
      .menu_invite, R.id.menu_setting, R.id.profile_menu})
  public void onClick(View view) {
    switch (view.getId()) {
      case R.id.btn_fans:
        startActivity(FansListActivity.makeIntent(this, Url.USERID));
        break;
      case R.id.btn_follow:
        startActivity(FollowListActivity.makeIntent(this, Url.USERID));
        break;
      case R.id.menu_message:
        startActivity(new Intent(this, com.thinksky.ui.profile.MyMessageActivity
            .class));
        break;
      case R.id.menu_collect:
        startActivity(new Intent(this, MyCollectionActivity.class));
        break;
      case R.id.menu_invite:
        showShare();
        break;
      case R.id.menu_setting:
        Intent intent = new Intent(this, SettingActivity.class);
        startActivity(intent);
        break;
      case R.id.profile_menu:
        break;
    }
  }

  private void initView() {
    enterMap.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        UserInfoModel info = BaseApplication.getApplication().getGlobalComponent().loginSession()
            .getUserInfo();
        if (TextUtils.isEmpty(info.getLatitude()) || TextUtils.isEmpty(info.getLongitude())) {
          return;
        }
        getComponent().getGlobalBus().post(new MainActivity.EnterMapEvent(TextUtils.equals(info
            .getIsfactory
                (), "2"), info.getLongitude(), info
            .getLatitude()));
      }
    });

  }

  public void setData() {
    UserInfoModel info = BaseApplication.getApplication().getGlobalComponent().loginSession()
        .getUserInfo();
    ImageLoader.loadOptimizedHttpImage(this, info.getAvatar128()).dontAnimate()
        .bitmapTransform(new CropCircleTransformation(this)).placeholder(R
        .drawable.side_user_avatar).into(avatar);
    gender.setVisibility(View.VISIBLE);
    if (!TextUtils.isEmpty(info.getSex())) {
      switch (info.getSex()) {
        case UserInfoModel.GENDER_MALE:
          gender.setImageResource(R.drawable.icon_profile_gender_male);
          break;
        case UserInfoModel.GENDER_FEMALE:
          gender.setImageResource(R.drawable.icon_profile_gender_female);
          break;
        default:
          gender.setVisibility(View.GONE);
          break;
      }
    }
    nickName.setText(info.getNickname());
    areaValue.setText(TextUtils.isEmpty(info.getP_province()) ? "" : info.getP_province() +
        (TextUtils.isEmpty(info.getP_city()) ? "" : info.getP_city()));
    signatureValue.setText(info.getSignature());
    fansCount.setText(info.getFans());
    followCount.setText(info.getFollowing());
    if (TextUtils.isEmpty(info.getLatitude()) || TextUtils.isEmpty(info.getLongitude())) {
      enterMap.setText(R.string.activity_profile_setting_default_value);
      enterMap.setTextColor(getResources().getColor(R.color.font_color_secondary));
    } else {
      enterMap.setText(R.string.activity_profile_enter_map);
      enterMap.setTextColor(getResources().getColor(R.color.font_color_blue));
    }
    setTitleBar();
  }

  private void setTitleBar() {
    titleBar.getTitleBgView().setAlpha(0);
    titleBar.setLeftImgMenu(R.drawable.icon_title_bar_back, new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });
    titleBar.setRightTextBtn(R.string.activity_profile_title_right_btn, new View
        .OnClickListener() {


      @Override
      public void onClick(View v) {
        startActivity(new Intent(MyProfileActivity.this, ProfileSettingActivity.class));
      }
    });
  }

  private void showShare() {
    ShareSDK.initSDK(this);
    OnekeyShare oks = new OnekeyShare();
    //关闭sso授权
    oks.disableSSOWhenAuthorize();

    // 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
    //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
    // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
    oks.setTitle(getString(R.string.setting));
    // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
    oks.setTitleUrl("http://sharesdk.cn");
    // text是分享文本，所有平台都需要这个字段
    oks.setText("我是分享文本");
    // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
    //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
    // url仅在微信（包括好友和朋友圈）中使用
    oks.setUrl("http://sharesdk.cn");
    // comment是我对这条分享的评论，仅在人人网和QQ空间使用
    oks.setComment("我是测试评论文本");
    // site是分享此内容的网站名称，仅在QQ空间使用
    oks.setSite(getString(R.string.app_name));
    // siteUrl是分享此内容的网站地址，仅在QQ空间使用
    oks.setSiteUrl("http://sharesdk.cn");

    // 启动分享GUI
    oks.show(this);
  }

  @Subscribe
  public void handleuserInfoChangeEvent(LoginSession.UserInfoChangeEvent event) {
    setData();
  }
}
