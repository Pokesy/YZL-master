/*
 * 文件名: OtherProfileActivity
 * 版    权：  Copyright Hengrtech Tech. Co. Ltd. All Rights Reserved.
 * 描    述: [该类的简要描述]
 * 创建人: zhaozeyang
 * 创建时间:16/8/5
 * 
 * 修改人：
 * 修改时间:
 * 修改内容：[修改内容]
 */
package com.thinksky.ui.profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.thinksky.fragment.MyScrollview;
import com.thinksky.holder.BaseApplication;
import com.thinksky.holder.BaseBActivity;
import com.thinksky.injection.GlobalModule;
import com.thinksky.net.UiRpcSubscriberSimple;
import com.thinksky.net.rpc.model.BaseModel;
import com.thinksky.net.rpc.model.UserInfoModel;
import com.thinksky.net.rpc.service.AppService;
import com.thinksky.serviceinjection.DaggerServiceComponent;
import com.thinksky.serviceinjection.ServiceModule;
import com.thinksky.tox.MainActivity;
import com.thinksky.tox.R;
import com.thinksky.ui.common.TitleBar;
import com.thinksky.utils.imageloader.ImageLoader;
import com.tox.Url;
import javax.inject.Inject;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * [一句话功能简述]<BR>
 * [功能详细描述]
 *
 * @author zhaozeyang
 * @version [Taobei Client V20160411, 16/8/5]
 */
public class OtherProfileActivity extends BaseBActivity {
  private static final String BUNDLE_KEY_USER_ID = "user_id";
  @Bind(R.id.avatar)
  ImageView avatar;
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

  @Inject
  AppService mAppService;
  @Bind(R.id.menu_question)
  RelativeLayout menuQuestion;
  @Bind(R.id.menu_topic)
  RelativeLayout menuTopic;
  @Bind(R.id.menu_group)
  RelativeLayout menuGroup;
  @Bind(R.id.scrollView)
  MyScrollview scrollView;
  @Bind(R.id.profile_menu)
  RelativeLayout profileMenu;
  @Bind(R.id.level)
  TextView level;
  private String mUserId;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_other_profile);
    ButterKnife.bind(this);
    inject();
    mUserId = getIntent().getStringExtra(BUNDLE_KEY_USER_ID);
    loadData();
  }

  private void inject() {
    DaggerServiceComponent.builder().globalModule(new GlobalModule(BaseApplication.getApplication
        ())).serviceModule(new ServiceModule())
        .build().inject(this);
  }

  private void bindData(final UserInfoModel model) {
    if (null == model) {
      return;
    }
    ImageLoader.loadOptimizedHttpImage(this, model.getAvatar64()).placeholder(R.drawable
        .side_user_avatar)
        .dontAnimate().bitmapTransform(new CropCircleTransformation(this)).into(avatar);
    nickName.setText(model.getNickname());
    if (TextUtils.isEmpty(model.getSex())) {
      gender.setVisibility(View.GONE);
    } else {
      gender.setVisibility(View.VISIBLE);
      switch (model.getSex()) {
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
    fansCount.setText(model.getFans());
    followCount.setText(model.getFollowing());
    if (!TextUtils.isEmpty(model.getP_province()) || !TextUtils.isEmpty(model.getP_city())) {
      areaValue.setText(model.getP_province() + " " + model.getP_city());
    }
    signatureValue.setText(model.getSignature());

    if (model.getIs_follow() == 1) {
      titleBar.setRightTextBtn(R.string.activity_other_profile_btn_cancel_follow, new View
          .OnClickListener() {


        @Override
        public void onClick(View v) {

          manageRpcCall(mAppService.endFollow(Url.SESSIONID, model.getUid()), new
              UiRpcSubscriberSimple<BaseModel>(OtherProfileActivity.this) {


                @Override
                protected void onSuccess(BaseModel baseModel) {
                  model.setIs_follow(0);
                  int fansCount = TextUtils.isEmpty(model.getFans()) ? 0 : Integer
                      .parseInt(model.getFans());
                  model.setFans(String.valueOf(fansCount - 1));
                  bindData(model);
                  getComponent().getGlobalBus().post(new FollowEvent());
                }

                @Override
                protected void onEnd() {

                }
              });
        }
      });
    } else {
      titleBar.setRightTextBtn(R.string.activity_other_profile_btn_follow, new View
          .OnClickListener() {

        @Override
        public void onClick(View v) {
          manageRpcCall(mAppService.doFollow(Url.SESSIONID, model.getUid()), new
              UiRpcSubscriberSimple<BaseModel>(OtherProfileActivity.this) {


                @Override
                protected void onSuccess(BaseModel baseModel) {
                  model.setIs_follow(1);
                  int fansCount = TextUtils.isEmpty(model.getFans()) ? 0 : Integer
                      .parseInt(model.getFans());
                  model.setFans(String.valueOf(fansCount + 1));
                  bindData(model);
                  getComponent().getGlobalBus().post(new FollowEvent());
                }

                @Override
                protected void onEnd() {

                }
              });
        }
      });
    }

    if (TextUtils.isEmpty(model.getLatitude()) || TextUtils.isEmpty(model.getLongitude())) {
      enterMap.setText(R.string.activity_profile_setting_default_value);
      enterMap.setTextColor(getResources().getColor(R.color.font_color_secondary));
    } else {
      enterMap.setText(R.string.activity_profile_enter_map);
      enterMap.setTextColor(getResources().getColor(R.color.font_color_blue));
    }

    enterMap.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (TextUtils.isEmpty(model.getLatitude()) || TextUtils.isEmpty(model.getLongitude())) {
          return;
        }
        getComponent().getGlobalBus().post(new MainActivity.EnterMapEvent(TextUtils.equals(model
            .getIsfactory
                (), "2"), model.getLongitude(), model.getLatitude()));
        finish();
        Intent intent = new Intent(OtherProfileActivity.this, MainActivity.class);
        startActivity(intent);
      }
    });

    level.setText(model.getTitle());

    titleBar.setLeftImgMenu(R.drawable.icon_title_bar_back, new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });

    titleBar.setMiddleTitle(R.string.activity_other_profile_title);
    titleBar.getTitleBgView().setAlpha(0);
  }

  private void loadData() {
    manageRpcCall(mAppService.getProfile(mUserId, Url.SESSIONID), new
        UiRpcSubscriberSimple<UserInfoModel>(this) {
      @Override
      protected void onSuccess(UserInfoModel model) {
        bindData(model);
      }

      @Override
      protected void onEnd() {

      }
    });
  }


  @OnClick({R.id.menu_question, R.id.menu_topic, R.id.menu_group, R.id.btn_fans, R.id
      .btn_follow_list, R.id.menu_trends})
  public void onClick(View view) {
    switch (view.getId()) {
      case R.id.menu_question:
        startActivity(OtherQuestionListActivity.makeIntent(this, mUserId));
        break;
      case R.id.menu_topic:
        startActivity(OtherPostListActivity.makeIntent(this, mUserId));
        break;
      case R.id.menu_group:
        startActivity(OtherGroupListActivity.makeIntent(this, mUserId));
        break;
      case R.id.btn_fans:
        startActivity(FansListActivity.makeIntent(this, mUserId));
        break;
      case R.id.btn_follow_list:
        startActivity(FollowListActivity.makeIntent(this, mUserId));
        break;
      case R.id.menu_trends:
        startActivity(OtherWeiboActivity.makeIntent(this, mUserId));
        break;
    }
  }

  public static Intent makeIntent(Context context, String userId) {
    Intent intent = new Intent(context, OtherProfileActivity.class);
    intent.putExtra(BUNDLE_KEY_USER_ID, userId);
    return intent;
  }

  public class FollowEvent {

  }

}
