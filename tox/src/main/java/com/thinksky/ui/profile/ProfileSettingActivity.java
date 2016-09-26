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

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.squareup.otto.Subscribe;
import com.thinksky.holder.BaseApplication;
import com.thinksky.holder.BaseBActivity;
import com.thinksky.injection.GlobalModule;
import com.thinksky.net.UiRpcSubscriberSimple;
import com.thinksky.net.rpc.model.BaseModel;
import com.thinksky.net.rpc.model.UserInfoModel;
import com.thinksky.net.rpc.service.AppService;
import com.thinksky.serviceinjection.DaggerServiceComponent;
import com.thinksky.serviceinjection.ServiceModule;
import com.thinksky.tox.R;
import com.thinksky.ui.LoginSession;
import com.thinksky.ui.common.TitleBar;
import com.thinksky.ui.profile.view.AddressChooseView;
import com.thinksky.ui.profile.view.DateChooseView;
import com.thinksky.utils.imageloader.ImageLoader;
import com.tox.Url;
import javax.inject.Inject;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * [一句话功能简述]<BR>
 * [功能详细描述]
 *
 * @author zhaozeyang
 * @version [Taobei Client V20160411, 16/8/1]
 */
public class ProfileSettingActivity extends BaseBActivity {
  private static final int MAX_NICK_NAME_LENGTH = 8;
  private static final int MAX_SIGNATURE_LENGTH = 30;

  private static final int INPUT_TYPE_NICK_NAME = 1;
  private static final int INPUT_TYPE_SIGNATURE = 2;
  private static final int INPUT_TYPE_QQ = 3;
  private static final int INPUT_TYPE_EMAIL = 4;
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

  private AlertDialog mAreaChooseDialog;
  private AlertDialog mGenderChooseDialog;
  private AlertDialog mBirthdayChooseDialog;

  @Inject
  AppService mAppService;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_profile_setting);
    ButterKnife.bind(this);
    mAvatarChoosePresenter = new AvatarChoosePresenter(this);
    initView();
    inject();
  }

  private void inject() {
    DaggerServiceComponent.builder().globalModule(new GlobalModule(BaseApplication.getApplication
        ())).serviceModule(new ServiceModule())
        .build().inject(this);
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
    mAreaValue.setText(!TextUtils.isEmpty(mUserInfo.getP_province()) || !TextUtils.isEmpty
        (mUserInfo
            .getP_city())
        ? mUserInfo.getP_province() + mUserInfo.getP_city() : getString(R.string
        .activity_profile_setting_default_value));
    mBirthdayValue.setText(TextUtils.isEmpty(mUserInfo.getBirthday()) ? getString(R.string
        .activity_profile_setting_default_value) : mUserInfo.getBirthday());
    mSignatureValue.setText(TextUtils.isEmpty(mUserInfo.getSignature()) ? getString(R.string
        .activity_profile_setting_default_value) : mUserInfo.getSignature());
    mQQValue.setText(TextUtils.isEmpty(mUserInfo.getQq()) ? getString(R.string
        .activity_profile_setting_default_value) : mUserInfo.getQq());
    mEmailValue.setText(TextUtils.isEmpty(mUserInfo.getEmail()) ? getString(R.string
        .activity_profile_setting_default_value) : mUserInfo.getEmail());
    mRegisterTimeValue.setText(mUserInfo.getReg_time());
    mScoreValue.setText(mUserInfo.getScore());

    mTitleBar.setLeftImgMenu(R.drawable.icon_title_bar_back, new View.OnClickListener() {
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
        showInputDialog(mUserInfo.getNickname(), INPUT_TYPE_NICK_NAME);
        break;
      case R.id.gender_settings:
        showGenderChooseDialog();
        break;
      case R.id.area_settings:
        showAreaChooseDialog();
        break;
      case R.id.birthday_settings:
        showBirthChooseDialog();
        break;
      case R.id.signature_settings:
        showInputDialog(mUserInfo.getSignature(), INPUT_TYPE_SIGNATURE);
        break;
      case R.id.qq_settings:
        showInputDialog(mUserInfo.getQq(), INPUT_TYPE_QQ);
        break;
      case R.id.email_settings:
        showInputDialog(mUserInfo.getEmail(), INPUT_TYPE_EMAIL);
        break;
      case R.id.register_time_settings:
        break;
      case R.id.score_settings:
        startActivity(new Intent(this, ScoreActivity.class));
        break;
    }
  }

  private void showInputDialog(String initText, final int inputType) {
    final EditText editText = (EditText) LayoutInflater.from(this).inflate(R.layout.dialog_input,
        null);
    if (inputType == INPUT_TYPE_QQ) {
      editText.setInputType(InputType.TYPE_CLASS_PHONE);
    }
    if (inputType == INPUT_TYPE_NICK_NAME) {
      editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(MAX_NICK_NAME_LENGTH)});
    }
    if (inputType == INPUT_TYPE_SIGNATURE) {
      editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(MAX_SIGNATURE_LENGTH)});
      editText.setHint(getString(R.string.activity_profile_setting_signature_hint,
          MAX_SIGNATURE_LENGTH));
    }
    editText.setText(initText);
    AlertDialog dialog = new AlertDialog.Builder(this).setView(editText).setPositiveButton(R
        .string.btn_confirm, new DialogInterface.OnClickListener() {


      @Override
      public void onClick(DialogInterface dialog, int which) {
        if (TextUtils.isEmpty(editText.getText())) {
          return;
        }
        updateInfo(editText.getText().toString(), inputType);
        dialog.cancel();
      }
    }).setNegativeButton(R.string.btn_cancel, new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {
        dialog.cancel();
      }
    }).create();
    dialog.show();
  }

  private void updateInfo(final String text, int inputType) {
    switch (inputType) {
      case INPUT_TYPE_NICK_NAME:
        manageRpcCall(mAppService.setNickName(Url.SESSIONID, text), new
            UiRpcSubscriberSimple<BaseModel>(ProfileSettingActivity.this) {


              @Override
              protected void onSuccess(BaseModel baseModel) {
                UserInfoModel model = getComponent().loginSession().getUserInfo();
                model.setNickname(text);
                getComponent().loginSession().saveUserInfoModel(model);
              }

              @Override
              protected void onEnd() {

              }
            });
        break;
      case INPUT_TYPE_SIGNATURE:
        manageRpcCall(mAppService.setSignature(Url.SESSIONID, text), new
            UiRpcSubscriberSimple<BaseModel>(ProfileSettingActivity.this) {


              @Override
              protected void onSuccess(BaseModel baseModel) {
                UserInfoModel model = getComponent().loginSession().getUserInfo();
                model.setSignature(text);
                getComponent().loginSession().saveUserInfoModel(model);
              }

              @Override
              protected void onEnd() {

              }
            });
        break;
      case INPUT_TYPE_QQ:
        manageRpcCall(mAppService.setQQ(Url.SESSIONID, text), new
            UiRpcSubscriberSimple<BaseModel>(ProfileSettingActivity.this) {


              @Override
              protected void onSuccess(BaseModel baseModel) {
                UserInfoModel model = getComponent().loginSession().getUserInfo();
                model.setQq(text);
                getComponent().loginSession().saveUserInfoModel(model);
              }

              @Override
              protected void onEnd() {

              }
            });
        break;
      case INPUT_TYPE_EMAIL:
        manageRpcCall(mAppService.setEmail(Url.SESSIONID, text), new
            UiRpcSubscriberSimple<BaseModel>(ProfileSettingActivity.this) {


              @Override
              protected void onSuccess(BaseModel baseModel) {
                UserInfoModel model = getComponent().loginSession().getUserInfo();
                model.setEmail(text);
                getComponent().loginSession().saveUserInfoModel(model);
              }

              @Override
              protected void onEnd() {

              }
            });
        break;
    }
  }

  private void showBirthChooseDialog() {
    if (null == mBirthdayChooseDialog) {
      final DateChooseView view = new DateChooseView(this);
      mBirthdayChooseDialog = new AlertDialog.Builder(this).setPositiveButton(getString(R
          .string.btn_confirm), new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
          manageRpcCall(mAppService.setBirthday(Url.SESSIONID, view.getDate()), new
              UiRpcSubscriberSimple<BaseModel>(ProfileSettingActivity
                  .this) {

                @Override
                protected void onSuccess(BaseModel baseModel) {
                  UserInfoModel model = getComponent().loginSession().getUserInfo();
                  model.setBirthday(view.getDate());
                  getComponent().loginSession().saveUserInfoModel(model);
                }

                @Override
                protected void onEnd() {

                }
              });
          mBirthdayChooseDialog.cancel();
        }
      }).setNegativeButton(getString(R.string.btn_cancel), new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
          mBirthdayChooseDialog.cancel();
        }
      }).setView(view).create();
    }
    if (mBirthdayChooseDialog.isShowing()) {
      return;
    }
    mBirthdayChooseDialog.show();
  }

  private void showAreaChooseDialog() {
    if (null == mAreaChooseDialog) {
      final AddressChooseView view = new AddressChooseView(this);
      mAreaChooseDialog = new AlertDialog.Builder(this).setPositiveButton(getString(R
          .string.btn_confirm), new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
          manageRpcCall(mAppService.setArea(Url.SESSIONID, view.getSelectProvinceId(), view
              .getSelectCityId()), new UiRpcSubscriberSimple<BaseModel>(ProfileSettingActivity
              .this) {


            @Override
            protected void onSuccess(BaseModel baseModel) {
              UserInfoModel model = getComponent().loginSession().getUserInfo();
              model.setP_province(view.getSelectProvince());
              model.setP_city(view.getSelectCity());
              getComponent().loginSession().saveUserInfoModel(model);
            }

            @Override
            protected void onEnd() {

            }
          });
          mAreaChooseDialog.cancel();
        }
      }).setNegativeButton(getString(R.string.btn_cancel), new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
          mAreaChooseDialog.cancel();
        }
      }).setView(view).create();
    }
    if (mAreaChooseDialog.isShowing()) {
      return;
    }
    mAreaChooseDialog.show();
  }

  private void showGenderChooseDialog() {
    if (null == mGenderChooseDialog) {
      final String[] genderItems = getResources().getStringArray(R.array.gender_items);
      mGenderChooseDialog = new AlertDialog.Builder(this).setItems(genderItems, new
          DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, final int which) {
              manageRpcCall(mAppService.setGender(Url.SESSIONID, String.valueOf(which)), new
                  UiRpcSubscriberSimple<BaseModel>(ProfileSettingActivity.this) {


                    @Override
                    protected void onSuccess(BaseModel baseModel) {
                      UserInfoModel model = getComponent().loginSession().getUserInfo();
                      model.setSex(String.valueOf(which));
                      getComponent().loginSession().saveUserInfoModel(model);
                    }

                    @Override
                    protected void onEnd() {

                    }
                  });
            }
          }).create();
    }
    if (mGenderChooseDialog.isShowing()) {
      return;
    }
    mGenderChooseDialog.show();
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
