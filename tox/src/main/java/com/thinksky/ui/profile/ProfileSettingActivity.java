/*
 * 文件名: profile
 * 版    权：  Copyright Hengrtech Tech. Co. Ltd. All Rights Reserved.
 * 描    述: [该类的简要描述]
 * 创建人: zhaozeyang
 * 创建时间:16/8/1
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
import com.squareup.otto.Subscribe;
import com.thinksky.holder.BaseBActivity;
import com.thinksky.net.rpc.model.UserInfoModel;
import com.thinksky.tox.R;
import com.thinksky.ui.LoginSession;
import com.thinksky.ui.common.TitleBar;
import com.thinksky.utils.imageloader.ImageLoader;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * [一句话功能简述]<BR>
 * [功能详细描述]
 *
 * @author zhaozeyang
 * @version [Taobei Client V20160411, 16/8/1]
 */
public class ProfileSettingActivity extends BaseBActivity {
  @Bind(R.id.avatar)
  ImageView mAvatarView;
  @Bind(R.id.gender_value)
  TextView mGenderValue;
  @Bind(R.id.area_value)
  TextView mAreaValue;
  @Bind(R.id.birthday_value)
  TextView mBirthdayValue;
  @Bind(R.id.signature_value)
  TextView mSignatureValue;
  @Bind(R.id.qq_value)
  TextView mQQValue;
  @Bind(R.id.email_value)
  TextView mEmailValue;
  @Bind(R.id.register_time_value)
  TextView mRegisterTimeValue;
  @Bind(R.id.score_value)
  TextView mScoreValue;
  @Bind(R.id.nick_name_value)
  TextView mNickNameValue;
  @Bind(R.id.title_bar)
  TitleBar mTitleBar;

  private AvatarChoosePresenter mAvatarChoosePresenter;
  private UserInfoModel mUserInfo;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_profile_setting);
    ButterKnife.bind(this);
    mAvatarChoosePresenter = new AvatarChoosePresenter(this);
    initView();
  }

  private void initView() {
    mUserInfo = getComponent().loginSession().getUserInfo();
    ImageLoader.loadOptimizedHttpImage(this, mUserInfo.getAvatar64()).dontAnimate().placeholder(R
        .drawable.side_user_avatar)
        .bitmapTransform(new CropCircleTransformation(this)).into(mAvatarView);
    mNickNameValue.setText(TextUtils.isEmpty(mUserInfo.getNickname()) ? getString(R.string
        .activity_profile_setting_default_value) : mUserInfo.getNickname());
    switch (mUserInfo.getSex()) {
      case UserInfoModel.GENDER_MALE:
        mGenderValue.setText(R.string.gender_male);
        break;
      case UserInfoModel.GENDER_FEMALE:
        mGenderValue.setText(R.string.gender_female);
        break;
      default:
        mGenderValue.setText(R.string.gender_secret);
        break;
    }
    mAreaValue.setText(!TextUtils.isEmpty(mUserInfo.getP_province()) || !TextUtils.isEmpty(mUserInfo
        .getP_city())
        ? mUserInfo.getP_province() + mUserInfo.getP_province() : getString(R.string
        .activity_profile_setting_default_value));
    mBirthdayValue.setText(TextUtils.isEmpty(mUserInfo.getBirthday()) ? getString(R.string
        .activity_profile_setting_default_value) : mUserInfo.getBirthday());
    mSignatureValue.setText(TextUtils.isEmpty(mUserInfo.getSignature()) ? getString(R.string
        .activity_profile_setting_default_value) : mUserInfo.getSignature());
    mQQValue.setText(TextUtils.isEmpty(mUserInfo.getQq()) ? getString(R.string
        .activity_profile_setting_default_value) : mUserInfo.getQq());
    mEmailValue.setText(TextUtils.isEmpty(mUserInfo.getEmail()) ? getString(R.string
        .activity_profile_setting_default_value) : mUserInfo.getEmail());
    // TODO 创建时间
    //mRegisterTimeValue.setText(mUserInfo.get());
    mScoreValue.setText(mUserInfo.getScore());

    mTitleBar.setLeftImgMenu(R.drawable.arrow_left, new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });
    mTitleBar.setMiddleTitle(R.string.activity_profile_setting_title);
  }

  @OnClick({R.id.avatar_settings, R.id.nick_name_settings, R.id.gender_settings, R.id
      .area_settings, R.id.birthday_settings, R.id.signature_settings, R.id.qq_settings, R.id
      .email_settings, R.id.register_time_settings, R.id.score_settings})
  public void onClick(View view) {
    switch (view.getId()) {
      case R.id.avatar_settings:
        mAvatarChoosePresenter.showChooseAvatarDialog();
        break;
      case R.id.nick_name_settings:
        break;
      case R.id.gender_settings:
        break;
      case R.id.area_settings:
        break;
      case R.id.birthday_settings:
        break;
      case R.id.signature_settings:
        break;
      case R.id.qq_settings:
        break;
      case R.id.email_settings:
        break;
      case R.id.register_time_settings:
        break;
      case R.id.score_settings:
        break;
    }
  }

  @Subscribe
  public void handleUserInfoChangeEvent(LoginSession.UserInfoChangeEvent event) {
    initView();
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    mAvatarChoosePresenter.onActivityResult(requestCode, resultCode, data);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    mAvatarChoosePresenter.onDestroy();
  }
}
