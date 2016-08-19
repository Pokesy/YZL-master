/*
 * 文件名: FollowListActivity
 * 版    权：  Copyright Hengrtech Tech. Co. Ltd. All Rights Reserved.
 * 描    述: [该类的简要描述]
 * 创建人: zhaozeyang
 * 创建时间:16/8/16
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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.thinksky.holder.BaseApplication;
import com.thinksky.holder.BaseBActivity;
import com.thinksky.injection.GlobalModule;
import com.thinksky.net.UiRpcSubscriberSimple;
import com.thinksky.net.rpc.model.UserInfoModel;
import com.thinksky.net.rpc.model.UserListModel;
import com.thinksky.net.rpc.service.AppService;
import com.thinksky.serviceinjection.DaggerServiceComponent;
import com.thinksky.serviceinjection.ServiceModule;
import com.thinksky.tox.R;
import com.thinksky.ui.common.TitleBar;
import com.thinksky.utils.imageloader.ImageLoader;
import com.tox.Url;
import java.util.List;
import javax.inject.Inject;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * [一句话功能简述]<BR>
 * [功能详细描述]
 *
 * @author zhaozeyang
 * @version [Taobei Client V20160411, 16/8/16]
 */
public class FollowListActivity extends BaseBActivity {
  private static final String BUNDLE_KEY_USER_ID = "user_id";
  @Bind(R.id.title_bar)
  TitleBar titleBar;
  @Bind(R.id.recycler)
  RecyclerView recycler;
  private String mUserId;
  @Inject
  AppService mAppService;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_other_group_list);
    ButterKnife.bind(this);
    inject();
    mUserId = getIntent().getStringExtra(BUNDLE_KEY_USER_ID);
    initView();
    initData();
  }

  public static Intent makeIntent(Context context, String userId) {
    Intent intent = new Intent(context, FollowListActivity.class);
    intent.putExtra(BUNDLE_KEY_USER_ID, userId);
    return intent;
  }

  private void initView() {
    titleBar.setLeftImgMenu(R.drawable.arrow_left, new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });
    titleBar.setMiddleTitle(TextUtils.equals(Url.USERID, mUserId) ? R.string
        .activity_follows_title_mine : R.string.activity_follows_title_other);

    recycler.setLayoutManager(new LinearLayoutManager(this));
  }

  private void inject() {
    DaggerServiceComponent.builder().serviceModule(new ServiceModule()).globalModule(new
        GlobalModule(BaseApplication.getApplication()))
        .build().inject(this);
  }


  private void initData() {
    manageRpcCall(mAppService.getMyFollows(mUserId, 1, Integer.MAX_VALUE), new
        UiRpcSubscriberSimple<UserListModel>
            (this) {
          @Override
          protected void onSuccess(UserListModel userListModel) {
            recycler.setAdapter(new UserListAdapter(userListModel.getResult()));
          }

          @Override
          protected void onEnd() {

          }
        });
  }

  class UserListAdapter extends RecyclerView.Adapter<UserViewHolder> {

    private List<UserInfoModel> mData;

    public UserListAdapter(List<UserInfoModel> mData) {
      this.mData = mData;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(FollowListActivity.this).inflate(R.layout
          .profile_user_list_item, parent, false);
      return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
      final UserInfoModel bean = mData.get(position);
      ImageLoader.loadOptimizedHttpImage(FollowListActivity.this, bean
          .getAvatar64()).bitmapTransform(new CropCircleTransformation
          (FollowListActivity.this))
          .placeholder(R.drawable.side_user_avatar).into(holder.avatar);
      holder.userName.setText(bean.getNickname());
      holder.signature.setText(bean.getSignature());
      holder.itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          startActivity(ProfileIntentFactory.makeIntent(FollowListActivity.this, bean.getUid()));
        }
      });
    }

    @Override
    public int getItemCount() {
      return null == mData ? 0 : mData.size();
    }
  }

  class UserViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.avatar)
    ImageView avatar;
    @Bind(R.id.user_name)
    TextView userName;
    @Bind(R.id.signature)
    TextView signature;

    public UserViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }
}
