/*
 * 文件名: OtherGroupListActivity
 * 版    权：  Copyright Hengrtech Tech. Co. Ltd. All Rights Reserved.
 * 描    述: [该类的简要描述]
 * 创建人: zhaozeyang
 * 创建时间:16/8/11
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
import com.thinksky.net.rpc.model.GroupInfoModel;
import com.thinksky.net.rpc.service.AppService;
import com.thinksky.net.rpc.service.NetConstant;
import com.thinksky.serviceinjection.DaggerServiceComponent;
import com.thinksky.serviceinjection.ServiceModule;
import com.thinksky.tox.R;
import com.thinksky.ui.common.TitleBar;
import com.thinksky.utils.imageloader.ImageLoader;
import java.util.List;
import javax.inject.Inject;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * [一句话功能简述]<BR>
 * [功能详细描述]
 *
 * @author zhaozeyang
 * @version [Taobei Client V20160411, 16/8/11]
 */
public class OtherGroupListActivity extends BaseBActivity {
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
    Intent intent = new Intent(context, OtherGroupListActivity.class);
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
    titleBar.setMiddleTitle(R.string.activity_other_group_list_title);

    recycler.setLayoutManager(new LinearLayoutManager(this));
  }

  private void inject() {
    DaggerServiceComponent.builder().serviceModule(new ServiceModule()).globalModule(new
        GlobalModule(BaseApplication.getApplication()))
        .build().inject(this);
  }


  private void initData() {
    manageRpcCall(mAppService.getHerGroup(mUserId, 1, Integer.MAX_VALUE), new
        UiRpcSubscriberSimple<GroupInfoModel>(this) {


          @Override
          protected void onSuccess(GroupInfoModel groupInfoModel) {
            recycler.setAdapter(new GroupListAdapter(groupInfoModel.getList()));
          }

          @Override
          protected void onEnd() {

          }
        });
  }

  class GroupListAdapter extends RecyclerView.Adapter<GroupViewHolder> {

    private List<GroupInfoModel.ListBean> mData;

    public GroupListAdapter(List<GroupInfoModel.ListBean> mData) {
      this.mData = mData;
    }

    @Override
    public GroupViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(OtherGroupListActivity.this).inflate(R.layout
          .activity_other_group_list_item, parent, false);
      return new GroupViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GroupViewHolder holder, int position) {
      GroupInfoModel.ListBean bean = mData.get(position);
      ImageLoader.loadOptimizedHttpImage(OtherGroupListActivity.this, NetConstant.BASE_URL + bean
          .getLogo()).bitmapTransform(new CropCircleTransformation(OtherGroupListActivity.this))
          .placeholder(R.drawable.picture_1_no).into(holder.avatar);
      holder.memberCount.setText(getString(R.string.activity_other_group_member_count, bean
          .getMember_count()));
      holder.groupName.setText(bean.getTitle());
      holder.groupNotice.setText(bean.getDetail());
    }

    @Override
    public int getItemCount() {
      return null == mData ? 0 : mData.size();
    }
  }

  class GroupViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.avatar)
    ImageView avatar;
    @Bind(R.id.member_count)
    TextView memberCount;
    @Bind(R.id.group_name)
    TextView groupName;
    @Bind(R.id.group_notice)
    TextView groupNotice;

    public GroupViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }

}
