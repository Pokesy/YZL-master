package com.thinksky.ui.group;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
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
import butterknife.OnClick;
import com.thinksky.holder.BaseApplication;
import com.thinksky.holder.BaseBActivity;
import com.thinksky.injection.GlobalModule;
import com.thinksky.net.UiRpcSubscriber1;
import com.thinksky.net.rpc.model.BaseModel;
import com.thinksky.net.rpc.model.GroupMemberListModel;
import com.thinksky.net.rpc.service.AppService;
import com.thinksky.rsen.RsenUrlUtil;
import com.thinksky.serviceinjection.DaggerServiceComponent;
import com.thinksky.serviceinjection.ServiceModule;
import com.thinksky.tox.R;
import com.thinksky.ui.basic.BasicListAdapter;
import com.thinksky.ui.common.PullToRefreshListView;
import com.thinksky.ui.common.TitleBar;
import com.thinksky.ui.profile.ProfileIntentFactory;
import com.thinksky.utils.imageloader.ImageLoader;
import com.tox.BaseFunction;
import com.tox.ToastHelper;
import com.tox.Url;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by jiao on 2016/3/13.
 */
public class GroupMemberListActivity extends BaseBActivity {
  private static final int PAGE_COUNT = 20;
  private static final int INIT_PAGE = 1;
  private static final String BUNDLE_KEY_GROUP_ID = "group_id";
  private static final String BUNDLE_KEY_IS_CREATOR = "is_creator";
  @Bind(R.id.title_bar)
  TitleBar titleBar;
  @Bind(R.id.memberRecycler)
  PullToRefreshListView mListView;
  @Bind(R.id.check_all)
  CheckBox checkAll;
  @Bind(R.id.btn_set_manager)
  TextView btnSetManager;
  @Bind(R.id.operation_container)
  LinearLayout operationContainer;
  private String mGroupId;
  private boolean mIsCreator;

  private int mCurrentIndex = INIT_PAGE;
  private MySubAdapter mAdapter = new MySubAdapter();

  @Inject
  AppService mAppService;

  private List<String> mSelectUserIds = new ArrayList<>();

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    inject();
    setContentView(R.layout.activity_group_member_list);
    ButterKnife.bind(this);
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
    mIsCreator = getIntent().getBooleanExtra(BUNDLE_KEY_IS_CREATOR, false);
    titleBar.setLeftImgMenu(R.drawable.icon_title_bar_back, new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        onBackPressed();
      }
    });

    titleBar.setMiddleTitle(R.string.activity_group_member_list_title);
    if (mIsCreator) {
      titleBar.setRightTextBtn(R.string.activity_group_member_list_title_btn_edit, new View
          .OnClickListener() {


        @Override
        public void onClick(View v) {
          performEdit();
        }
      });
    }

    mListView.setPullUpToRefresh(false);
    mListView.getRefreshListView().setAdapter(mAdapter);
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
        selectOrRemoveAll(isChecked);
      }
    });
  }

  private void selectOrRemoveAll(boolean isChecked) {
    mSelectUserIds.clear();
    if (isChecked) {
      List<GroupMemberListModel.ListBean> beans = mAdapter.getAllList();
      for (GroupMemberListModel.ListBean bean : beans) {
        if (!TextUtils.equals(bean.getUid(), Url.USERID)) {
          mSelectUserIds.add(bean.getUid());
        }
      }
    }
    mAdapter.notifyDataSetChanged();
    updateBottomBtn();
  }

  @Override
  public void onBackPressed() {
    if (mAdapter.isEditable) {
      quitEdit();
      return;
    }
    super.onBackPressed();
  }

  private void performEdit() {
    if (mAdapter.isEditable) {
      return;
    }
    mAdapter.setEditable(true);
    mAdapter.notifyDataSetChanged();
    operationContainer.setVisibility(View.VISIBLE);
  }

  private void quitEdit() {
    mAdapter.setEditable(false);
    mAdapter.notifyDataSetChanged();
    operationContainer.setVisibility(View.GONE);
    checkAll.setChecked(false);
  }

  private void loadData() {
    manageRpcCall(mAppService.getGroupMember(mGroupId, mCurrentIndex, PAGE_COUNT), new
        UiRpcSubscriber1<GroupMemberListModel>(this) {


          @Override
          protected void onSuccess(GroupMemberListModel groupMemberListModel) {
            if (null == groupMemberListModel || null == groupMemberListModel.getList()) {
              return;
            }
            List<GroupMemberListModel.ListBean> beans = groupMemberListModel.getList();
            if (mCurrentIndex == INIT_PAGE) {
              mAdapter.clear();
            }
            mAdapter.addAll(beans);
            mAdapter.notifyDataSetChanged();
          }

          @Override
          protected void onEnd() {
            mListView.resetPullStatus();
          }
        });
  }

  @OnClick({R.id.btn_set_manager, R.id.btn_remove})
  public void onClick(View view) {
    switch (view.getId()) {
      case R.id.btn_set_manager:
        tranferManager();
        break;
      case R.id.btn_remove:
        removeGroupMembers();
        break;
    }
  }

  private void tranferManager() {
    if (mSelectUserIds.size() != 1) {
      return;
    }
    manageRpcCall(mAppService.tranferGroupManager(Url.SESSIONID, mSelectUserIds.get(0), mGroupId)
        , new UiRpcSubscriber1<BaseModel>(this) {


          @Override
          protected void onSuccess(BaseModel baseModel) {
            quitEdit();
            mIsCreator = false;
            titleBar.getTextBtnRight().setVisibility(View.GONE);
            loadData();
            getComponent().getGlobalBus().post(new GroupMemberDataChangeEvent());
            Toast.makeText(GroupMemberListActivity.this, R.string
                .activity_group_member_list_set_manager_success, Toast.LENGTH_SHORT).show();
          }

          @Override
          protected void onEnd() {

          }
        });
  }

  private void removeGroupMembers() {
    if (mSelectUserIds.size() == 0) {
      return;
    }
    AlertDialog dialog = new AlertDialog.Builder(this).setMessage(R.string
        .activity_group_member_list_remove_alert)
        .setPositiveButton(R.string.btn_confirm, new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            dialog.cancel();
            StringBuffer stringBuffer = new StringBuffer();
            for (String userId : mSelectUserIds) {
              stringBuffer.append(userId).append(",");
            }
            manageRpcCall(mAppService.removeGroupMember(Url.SESSIONID, mGroupId, stringBuffer
                .substring
                    (0, stringBuffer.length())), new UiRpcSubscriber1<BaseModel>
                (GroupMemberListActivity.this) {


              @Override
              protected void onSuccess(BaseModel baseModel) {
                quitEdit();
                loadData();
                getComponent().getGlobalBus().post(new GroupMemberDataChangeEvent());
              }

              @Override
              protected void onEnd() {

              }
            });
          }
        }).setNegativeButton(R.string.btn_cancel, new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            dialog.cancel();
          }
        }).create();
    dialog.show();
  }

  /*成员头像*/
  public class MySubAdapter extends BasicListAdapter<GroupMemberListModel.ListBean> {
    private boolean isEditable;

    public boolean isEditable() {
      return isEditable;
    }

    public void setEditable(boolean editable) {
      isEditable = editable;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      final ViewHolder holder;
      if (null == convertView) {
        convertView = LayoutInflater.from(GroupMemberListActivity.this).inflate(R.layout
            .activity_group_member_list_item, parent, false);
        holder = new ViewHolder(convertView);
        convertView.setTag(holder);
      } else {
        holder = (ViewHolder) convertView.getTag();
      }
      final GroupMemberListModel.ListBean bean = getItem(position);
      String url = RsenUrlUtil.URL_BASE + bean.getUser().getAvatar64();
      try {
        ImageLoader.loadOptimizedHttpImage(GroupMemberListActivity.this, url).error(R.drawable
            .side_user_avatar).bitmapTransform(new CropCircleTransformation
            (GroupMemberListActivity.this))
            .placeholder(R.drawable.side_user_avatar).into(holder.userLogo);
      } catch (Exception e) {
        e.printStackTrace();
      }
      holder.userName.setText(bean.getUser().getNickname());
      holder.fans.setText("粉丝数" + bean.getUser().getFans());
            /*点击用户头像*/
      if (TextUtils.equals(bean.getIsfollow(), "0")) {
        holder.guanzhu.setSelected(true);
        holder.guanzhu.setText("加关注");
      } else {
        holder.guanzhu.setSelected(false);
        holder.guanzhu.setText("取消关注");
      }
      holder.guanzhu.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          if (BaseFunction.isLogin()) {
            if (TextUtils.equals(bean.getIsfollow(), "0")) {
              holder.guanzhu.setText("取消关注");
              holder.guanzhu.setSelected(true);
            } else {
              holder.guanzhu.setText("加关注");
              holder.guanzhu.setSelected(false);
            }
          } else {
            ToastHelper.showToast("请登录", Url.context);
          }
        }
      });
      holder.guanzhu.setVisibility((TextUtils.equals(Url.USERID, bean.getUid()) || isEditable) ?
          View.GONE : View.VISIBLE);

      holder.itemLayout.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          if (TextUtils.isEmpty(bean.getUid())) {
            return;
          }
          startActivity(ProfileIntentFactory.makeIntent(GroupMemberListActivity.this, bean.getUid
              ()));
        }
      });
      holder.itemCheck.setVisibility(isEditable && !TextUtils.equals(bean.getUid(), Url.USERID) ?
          View.VISIBLE : View.GONE);
      holder.itemCheck.setChecked(mSelectUserIds.contains(bean.getUid()));
      holder.itemCheck.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          holder.itemCheck.setChecked(!mSelectUserIds.contains(bean.getUid()));
          if (holder.itemCheck.isChecked()) {
            mSelectUserIds.add(bean.getUid());
          } else {
            mSelectUserIds.remove(bean.getUid());
          }
          notifyDataSetChanged();
          updateBottomBtn();
        }
      });

      return convertView;
    }

    class ViewHolder {
      @Bind(R.id.user_logo)
      ImageView userLogo;
      @Bind(R.id.user_name)
      TextView userName;
      @Bind(R.id.fans)
      TextView fans;
      @Bind(R.id.guanzhu)
      TextView guanzhu;
      @Bind(R.id.item_layout)
      RelativeLayout itemLayout;
      @Bind(R.id.item_check)
      CheckBox itemCheck;


      ViewHolder(View view) {
        ButterKnife.bind(this, view);
      }
    }
  }

  private void updateBottomBtn() {
    btnSetManager.setVisibility(mSelectUserIds.size() == 1 ? View.VISIBLE : View.GONE);
  }

  public static Intent makeIntent(Context context, String groupId, boolean isCreator) {
    Intent intent = new Intent(context, GroupMemberListActivity.class);
    intent.putExtra(BUNDLE_KEY_GROUP_ID, groupId);
    intent.putExtra(BUNDLE_KEY_IS_CREATOR, isCreator);
    return intent;
  }

  public static class GroupMemberDataChangeEvent {

  }
}