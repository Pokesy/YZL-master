package com.thinksky.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.squareup.otto.Subscribe;
import com.thinksky.holder.BaseApplication;
import com.thinksky.injection.GlobalModule;
import com.thinksky.net.RpcApiError;
import com.thinksky.net.UiRpcSubscriberSimple;
import com.thinksky.net.rpc.GroupModel;
import com.thinksky.net.rpc.model.CountModel;
import com.thinksky.net.rpc.service.AppService;
import com.thinksky.net.rpc.service.NetConstant;
import com.thinksky.serviceinjection.DaggerServiceComponent;
import com.thinksky.serviceinjection.ServiceModule;
import com.thinksky.tox.GroupInfoActivity;
import com.thinksky.tox.R;
import com.thinksky.tox.SendTieziActivity;
import com.thinksky.ui.basic.BasicFragment;
import com.thinksky.ui.group.CreateGroupActivity;
import com.thinksky.ui.group.GroupMemberListActivity;
import com.thinksky.utils.imageloader.ImageLoader;
import com.tox.Url;
import de.hdodenhof.circleimageview.CircleImageView;
import java.util.List;
import javax.inject.Inject;
import retrofit2.Response;
import rx.Observable;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


public class MyGroupFragment extends BasicFragment {
  @Bind(R.id.topic_count)
  TextView topicCount;
  @Bind(R.id.create_group)
  TextView createGroup;
  @Bind(R.id.list_view)
  ExpandableListView listView;
  @Bind(R.id.menu_topic)
  LinearLayout menuTopic;

  @Inject
  AppService mAppService;

  private GroupListAdapter mGroupListAdapter;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    inject();
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
  Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_my_group_layout, container, false);
    ButterKnife.bind(this, view);
    return view;
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    initView();
    initPostCount();
    initGroupList();
  }

  private void initView() {
    topicCount.setText("0");
    mGroupListAdapter = new GroupListAdapter();
    listView.setAdapter(mGroupListAdapter);

    createGroup.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        startActivity(new Intent(getActivity(), CreateGroupActivity.class));
      }
    });
    menuTopic.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        startActivity(new Intent(getActivity(), MyhuatiActivity.class));
      }
    });
  }

  private void inject() {
    DaggerServiceComponent.builder().globalModule(new GlobalModule(BaseApplication.getApplication
        ())).serviceModule(new ServiceModule())
        .build().inject(this);
  }

  private void initPostCount() {
    manageRpcCall(mAppService.getMyPostCount(Url.SESSIONID), new
        UiRpcSubscriberSimple<CountModel>(getActivity()) {


          @Override
          protected void onSuccess(CountModel countModel) {
            topicCount.setText(String.valueOf(countModel.getCount()));
          }

          @Override
          protected void onEnd() {

          }

          @Override
          public void onApiError(RpcApiError apiError) {
          }
        });
  }

  @Override
  protected void onLogin() {
    super.onLogin();
    initPostCount();
    initGroupList();
  }

  @Override
  protected void onLogout() {
    super.onLogout();
  }

  @Subscribe
  public void handleCreateGroupSuccessEvent(CreateGroupActivity.CreateGroupSuccessEvent event) {
    initGroupList();
  }

  @Subscribe
  public void handleDissolutionGroupEvent(CreateGroupActivity.DissolutionGroupEvent event) {
    initGroupList();
  }

  @Subscribe
  public void handleGroupMemberDataChangeEvent(GroupMemberListActivity.GroupMemberDataChangeEvent
                                                   event) {
    initGroupList();
  }

  @Subscribe
  public void handleGroupPostDataChangeEvent(SendTieziActivity.GroupPostInfoChangeEvent event) {
    initGroupList();
    initPostCount();
  }

  private void initGroupList() {
    final GroupListModel model = new GroupListModel();
    Observable<Response<GroupModel>> observable = mAppService.getCreatedGroup(Url.SESSIONID)
        .subscribeOn(Schedulers.newThread()).observeOn(Schedulers.newThread())
        .flatMap(new Func1<Response<GroupModel>, Observable<Response<GroupModel>>>() {
          @Override
          public Observable<Response<GroupModel>> call(Response<GroupModel> groupModelResponse) {
            if (null != groupModelResponse && null != groupModelResponse.body()) {
              model.setCreateList(groupModelResponse.body().getList());
            }
            return mAppService.getJoinedGroup(Url.SESSIONID);
          }
        });
    manageRpcCall(observable, new UiRpcSubscriberSimple<GroupModel>(getActivity()) {
      @Override
      protected void onSuccess(GroupModel groupModel) {
        model.setJoinList(groupModel.getList());
        mGroupListAdapter.setData(model);
        mGroupListAdapter.notifyDataSetChanged();
        expandItems();
      }

      @Override
      protected void onEnd() {

      }

      @Override
      public void onApiError(RpcApiError apiError) {
      }
    });
  }

  private void expandItems() {
    listView.expandGroup(0);
    listView.expandGroup(1);
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    ButterKnife.unbind(this);
  }

  class GroupListAdapter extends BaseExpandableListAdapter {
    private GroupListModel model;

    public void setData(GroupListModel model) {
      this.model = model;
    }

    private static final int GROUP_COUNT = 2;

    @Override
    public int getGroupCount() {
      return null == model ? 0 : GROUP_COUNT;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
      switch (groupPosition) {
        case 0:
          return null == model.getCreateList() ? 0 : model.getCreateList().size();
        case 1:
          return null == model.getJoinList() ? 0 : model.getJoinList().size();
      }
      return 0;
    }

    @Override
    public Object getGroup(int groupPosition) {
      return groupPosition;
    }

    @Override
    public GroupModel.ListBean getChild(int groupPosition, int childPosition) {
      switch (groupPosition) {
        case 0:
          return model.getCreateList().get(childPosition);
        case 1:
          return model.getJoinList().get(childPosition);
      }
      return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
      return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
      return childPosition;
    }

    @Override
    public boolean hasStableIds() {
      return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup
        parent) {
      GroupViewHolder viewHolder;
      if (null == convertView) {
        convertView = LayoutInflater.from(getActivity()).inflate(R.layout
            .fragment_my_group_list_item_group, parent, false);
        viewHolder = new GroupViewHolder(convertView);
        convertView.setTag(viewHolder);
      } else {
        viewHolder = (GroupViewHolder) convertView.getTag();
      }
      switch (groupPosition) {
        case 0:
          viewHolder.title.setText(R.string.fragment_my_group_category_create);
          break;
        case 1:
          viewHolder.title.setText(R.string.fragment_my_group_category_join);
          break;
      }
      return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View
        convertView, ViewGroup parent) {
      ChildViewHolder holder;
      if (null == convertView) {
        convertView = LayoutInflater.from(getActivity()).inflate(R.layout
            .fragment_my_group_list_item_child, parent, false);
        holder = new ChildViewHolder(convertView);
        convertView.setTag(holder);
      } else {
        holder = (ChildViewHolder) convertView.getTag();
      }
      final GroupModel.ListBean bean;
      switch (groupPosition) {
        case 0:
          bean = model.getCreateList().get(childPosition);
          break;
        case 1:
          bean = model.getJoinList().get(childPosition);
          break;
        default:
          return convertView;
      }
      ImageLoader.loadOptimizedHttpImage(getActivity(), NetConstant.BASE_URL + bean.getLogo())
          .placeholder(R.drawable
              .picture_1_no).dontAnimate().into(holder.groupLogo);
      holder.groupName.setText(bean.getTitle());
      holder.groupDetail.setText(bean.getDetail());
      holder.topicCount.setText(bean.getPost_count());
      holder.memberCount.setText(bean.getMenmberCount());
      convertView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          launch(getActivity(), true, bean);
        }
      });
      return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
      return false;
    }

    class GroupViewHolder {
      @Bind(R.id.title)
      TextView title;

      GroupViewHolder(View view) {
        ButterKnife.bind(this, view);
      }
    }

    class ChildViewHolder {
      @Bind(R.id.group_logo)
      CircleImageView groupLogo;
      @Bind(R.id.group_name)
      TextView groupName;
      @Bind(R.id.group_detail)
      TextView groupDetail;
      @Bind(R.id.topic_count)
      TextView topicCount;
      @Bind(R.id.member_count)
      TextView memberCount;

      ChildViewHolder(View view) {
        ButterKnife.bind(this, view);
      }
    }
  }

  public static void launch(Context context, boolean isWeGroup, GroupModel.ListBean bean) {
    context.startActivity(GroupInfoActivity.makeIntent(context, bean.getId()));
  }

  private class GroupListModel {
    List<GroupModel.ListBean> createList;
    List<GroupModel.ListBean> joinList;

    public List<GroupModel.ListBean> getCreateList() {
      return createList;
    }

    public void setCreateList(List<GroupModel.ListBean> createList) {
      this.createList = createList;
    }

    public List<GroupModel.ListBean> getJoinList() {
      return joinList;
    }

    public void setJoinList(List<GroupModel.ListBean> joinList) {
      this.joinList = joinList;
    }
  }
}
