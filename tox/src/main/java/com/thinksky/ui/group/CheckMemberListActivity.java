/*
 * 文件名: CheckMemberListActivity
 * 版    权：  Copyright Hengrtech Tech. Co. Ltd. All Rights Reserved.
 * 描    述: [该类的简要描述]
 * 创建人: zhaozeyang
 * 创建时间:16/8/26
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.thinksky.holder.BaseApplication;
import com.thinksky.holder.BaseBActivity;
import com.thinksky.injection.GlobalModule;
import com.thinksky.net.RpcApiError;
import com.thinksky.net.UiRpcSubscriber1;
import com.thinksky.net.rpc.model.BaseModel;
import com.thinksky.net.rpc.model.GroupMemberListModel;
import com.thinksky.net.rpc.service.AppService;
import com.thinksky.net.rpc.service.NetConstant;
import com.thinksky.serviceinjection.DaggerServiceComponent;
import com.thinksky.serviceinjection.ServiceModule;
import com.thinksky.tox.R;
import com.thinksky.ui.basic.BasicListAdapter;
import com.thinksky.ui.common.PullToRefreshListView;
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
 * @version [Taobei Client V20160411, 16/8/26]
 */
public class CheckMemberListActivity extends BaseBActivity {
  private static final String BUNDLE_KEY_GROUP_ID = "group_id";
  private static final int INIT_PAGE = 1;
  private static final int PAGE_COUNT = 20;

  @Bind(R.id.title_bar)
  TitleBar titleBar;
  @Bind(R.id.memberRecycler)
  PullToRefreshListView mListView;
  @Bind(R.id.check_all)
  CheckBox checkAll;
  @Bind(R.id.btn_accept_all)
  TextView btnAcceptAll;
  @Bind(R.id.operation_container)
  LinearLayout operationContainer;

  private String mGroupId;
  private int mCurrentIndex;
  private CheckListAdapter mAdapter = new CheckListAdapter();

  @Inject
  AppService mAppService;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mGroupId = getIntent().getStringExtra(BUNDLE_KEY_GROUP_ID);
    setContentView(R.layout.activity_check_member_list);
    ButterKnife.bind(this);
    inject();
    initView();
    loadData();
  }

  private void inject() {
    DaggerServiceComponent.builder().globalModule(new GlobalModule((BaseApplication)
        getApplication())).serviceModule(new ServiceModule())
        .build().inject(this);
  }

  private void initView() {
    mGroupId = getIntent().getStringExtra(BUNDLE_KEY_GROUP_ID);
    titleBar.setLeftImgMenu(R.drawable.icon_title_bar_back, new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        onBackPressed();
      }
    });

    titleBar.setMiddleTitle(R.string.activity_check_member_list_title);

    mListView.setPullUpToRefresh(false);
    mListView.setAdapter(mAdapter);
    mListView.setScrollToLoadListener(new PullToRefreshListView.ScrollToLoadListener() {
      @Override
      public void onPullUpLoadData() {
        loadData();
      }

      @Override
      public void onPullDownLoadData() {
        mCurrentIndex = INIT_PAGE;
        loadData();
      }
    }, 3);

    checkAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        //selectOrRemoveAll(isChecked);
      }
    });
  }

  private void loadData() {
    manageRpcCall(mAppService.getGroupCheckMember(mGroupId, mCurrentIndex, Integer.MAX_VALUE), new
        UiRpcSubscriber1<GroupMemberListModel>(this) {


          @Override
          protected void onSuccess(GroupMemberListModel groupMemberListModel) {
            mAdapter.clear();
            mAdapter.addAll(groupMemberListModel.getList());
            mAdapter.notifyDataSetChanged();
          }

          @Override
          protected void onEnd() {
            mListView.resetPullStatus();
          }
        });
  }

  private void reject(String userId) {
    manageRpcCall(mAppService.rejectGroupPeople(Url.SESSIONID, userId, mGroupId), new
        UiRpcSubscriber1<BaseModel>(this) {


          @Override
          protected void onSuccess(BaseModel baseModel) {
            loadData();
            getComponent().getGlobalBus().post(new CheckMemberDataChangeEvent());
          }

          @Override
          protected void onEnd() {

          }

          @Override
          public void onApiError(RpcApiError apiError) {
            super.onApiError(apiError);
            Toast.makeText(CheckMemberListActivity.this, apiError.getMessage(), Toast.LENGTH_SHORT).show();
          }
        });
  }

  private void accept(String userId) {
    manageRpcCall(mAppService.addGroupPeople(Url.SESSIONID, userId, mGroupId), new
        UiRpcSubscriber1<BaseModel>(this) {


          @Override
          protected void onSuccess(BaseModel baseModel) {
            loadData();
            getComponent().getGlobalBus().post(new CheckMemberDataChangeEvent());
          }

          @Override
          protected void onEnd() {

          }

          @Override
          public void onApiError(RpcApiError apiError) {
            super.onApiError(apiError);
            Toast.makeText(CheckMemberListActivity.this, apiError.getMessage(), Toast.LENGTH_SHORT).show();
          }
        });
  }

  public class CheckMemberDataChangeEvent {
  }

  class CheckListAdapter extends BasicListAdapter<GroupMemberListModel.ListBean> {

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      ViewHolder viewHolder;
      if (null == convertView) {
        convertView = LayoutInflater.from(CheckMemberListActivity.this).inflate(R.layout
            .activity_check_member_list_item, parent, false);
        viewHolder = new ViewHolder(convertView);
        convertView.setTag(viewHolder);
      } else {
        viewHolder = (ViewHolder) convertView.getTag();
      }
      final GroupMemberListModel.ListBean bean = getItem(position);
      ImageLoader.loadOptimizedHttpImage(CheckMemberListActivity.this, NetConstant.BASE_URL +
          bean.getUser().getAvatar64()).bitmapTransform(new CropCircleTransformation
          (CheckMemberListActivity.this))
          .dontAnimate()
          .into(viewHolder.userLogo);
      viewHolder.userName.setText(bean.getUser().getNickname());
      //viewHolder.signature.setText(bean.getUser().get);
      viewHolder.accept.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          // 审核通过
          accept(bean.getUid());
        }
      });
      viewHolder.reject.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          // 拒绝
          reject(bean.getUid());
        }
      });
      return convertView;
    }

    class ViewHolder {
      @Bind(R.id.item_check)
      CheckBox itemCheck;
      @Bind(R.id.user_logo)
      ImageView userLogo;
      @Bind(R.id.user_name)
      TextView userName;
      @Bind(R.id.signature)
      TextView signature;
      @Bind(R.id.accept)
      TextView accept;
      @Bind(R.id.reject)
      TextView reject;
      @Bind(R.id.item_layout)
      RelativeLayout itemLayout;

      ViewHolder(View view) {
        ButterKnife.bind(this, view);
      }
    }
  }

  public static Intent makeIntent(Context context, String groupId) {
    Intent intent = new Intent(context, CheckMemberListActivity.class);
    intent.putExtra(BUNDLE_KEY_GROUP_ID, groupId);
    return intent;
  }
}
