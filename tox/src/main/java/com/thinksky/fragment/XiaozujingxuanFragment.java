package com.thinksky.fragment;


import android.content.Context;
import android.os.Bundle;
import android.view.View;
import com.squareup.otto.Subscribe;
import com.thinksky.holder.BaseApplication;
import com.thinksky.injection.GlobalModule;
import com.thinksky.net.UiRpcSubscriber1;
import com.thinksky.net.rpc.model.GroupChoiceModel;
import com.thinksky.net.rpc.service.AppService;
import com.thinksky.net.rpc.service.NetConstant;
import com.thinksky.rsen.RBaseAdapter;
import com.thinksky.rsen.RViewHolder;
import com.thinksky.rsen.fragment.RBaseFragment;
import com.thinksky.rsen.view.RGridView;
import com.thinksky.serviceinjection.DaggerServiceComponent;
import com.thinksky.serviceinjection.ServiceModule;
import com.thinksky.tox.GroupInfoActivity;
import com.thinksky.tox.R;
import com.thinksky.ui.group.CreateGroupActivity;
import com.thinksky.ui.group.GroupMemberListActivity;
import com.thinksky.utils.imageloader.ImageLoader;
import java.util.List;
import javax.inject.Inject;
import jp.wasabeef.glide.transformations.CropCircleTransformation;


public class XiaozujingxuanFragment extends RBaseFragment {
  private static final String ARG_PARAM1 = "param1";
  private RGridView recyclerView;
  private boolean isWeGroup = true;

  @Inject
  AppService mAppService;

  public static XiaozujingxuanFragment newInstance(String param1) {
    XiaozujingxuanFragment fragment = new XiaozujingxuanFragment();
    Bundle args = new Bundle();
    args.putString(ARG_PARAM1, param1);
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    inject();
  }

  private void inject() {
    DaggerServiceComponent.builder().serviceModule(new ServiceModule()).globalModule(new
        GlobalModule(BaseApplication.getApplication()))
        .build().inject(this);
  }

  @Override
  protected int getBaseLayoutId() {
    return R.layout.fragment_xiaozujingxuan_layout;
  }

  @Override
  protected void initViewData() {
    manageRpcCall(mAppService.getGroupChoice(1, Integer.MAX_VALUE), new
        UiRpcSubscriber1<GroupChoiceModel>(getActivity()) {


          @Override
          protected void onSuccess(GroupChoiceModel groupChoiceModel) {
            recyclerView.setAdapter(new MyAdapter(getActivity(), groupChoiceModel.getList()));
            recyclerView.getAdapter().notifyDataSetChanged();
          }

          @Override
          protected void onEnd() {

          }
        });

  }


  @Override
  protected void initView(View rootView) {
    recyclerView = (RGridView) mViewHolder.v(R.id.gridView);
  }

  public class MyAdapter extends RBaseAdapter<GroupChoiceModel.ListBean> {

    public MyAdapter(Context context, List<GroupChoiceModel.ListBean> datas) {
      super(context, datas);
    }

    @Override
    protected int getItemLayoutId(int viewType) {
      return R.layout.fragment_xiaozujingxuan_adapter;
    }

    @Override
    protected void onBindView(RViewHolder holder, int position, final GroupChoiceModel.ListBean
        bean) {
      try {
        ImageLoader.loadOptimizedHttpImage(getActivity(), NetConstant.BASE_URL + bean.getLogo())
            .bitmapTransform(new
                CropCircleTransformation(getActivity())).placeholder(R.drawable.picture_1_no)
            .error(R
                .drawable.picture_1_no).into
            (holder.imgV(R.id.logo));
      } catch (Exception e) {
        e.printStackTrace();
      }
      holder.tV(R.id.title).setText(bean.getTitle());
      holder.tV(R.id.detail).setText(bean.getDetail());
      holder.tV(R.id.post_count).setText(bean.getPost_count());
      holder.tV(R.id.member_count).setText(bean.getMenmberCount());
            /*item的点击事件*/
      holder.v(R.id.fragment_layout).setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          launch(mContext, isWeGroup, bean);
        }
      });
    }
  }

  @Subscribe
  public void handleCreateGroupEvent(CreateGroupActivity.CreateGroupSuccessEvent event) {
    initViewData();
  }

  @Subscribe
  public void handleDissolutionGroupEvent(CreateGroupActivity.DissolutionGroupEvent event) {
    initViewData();
  }

  @Subscribe
  public void handleMemberDataChangeEvent(GroupMemberListActivity.GroupMemberDataChangeEvent
                                                event) {
    initViewData();
  }

  public static void launch(Context context, boolean isWeGroup, GroupChoiceModel.ListBean bean) {
    context.startActivity(GroupInfoActivity.makeIntent(context, bean.getId()));
  }
}
