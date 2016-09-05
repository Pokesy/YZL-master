/*
 * 文件名: ActivityMessageFragment
 * 版    权：  Copyright Hengrtech Tech. Co. Ltd. All Rights Reserved.
 * 描    述: [该类的简要描述]
 * 创建人: zhaozeyang
 * 创建时间:16/8/12
 * 
 * 修改人：
 * 修改时间:
 * 修改内容：[修改内容]
 */
package com.thinksky.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.thinksky.holder.BaseApplication;
import com.thinksky.injection.GlobalModule;
import com.thinksky.net.UiRpcSubscriber1;
import com.thinksky.net.UiRpcSubscriberSimple;
import com.thinksky.net.rpc.model.BaseModel;
import com.thinksky.net.rpc.model.MessageModel;
import com.thinksky.net.rpc.service.AppService;
import com.thinksky.serviceinjection.DaggerServiceComponent;
import com.thinksky.serviceinjection.ServiceModule;
import com.thinksky.tox.GroupPostInfoActivity;
import com.thinksky.tox.R;
import com.thinksky.ui.basic.BasicListAdapter;
import com.thinksky.ui.basic.BasicPullToRefreshFragment;
import com.thinksky.ui.common.PullToRefreshListView;
import com.thinksky.ui.group.CheckMemberListActivity;
import com.tox.Url;
import javax.inject.Inject;

/**
 * [一句话功能简述]<BR>
 * [功能详细描述]
 *
 * @author zhaozeyang
 * @version [Taobei Client V20160411, 16/8/12]
 */
public class YLQMessageFragment extends BasicPullToRefreshFragment {
  @Bind(R.id.list)
  PullToRefreshListView mListView;
  @Bind(R.id.empty_info)
  TextView emptyInfo;
  @Bind(R.id.empty_layout)
  FrameLayout emptyLayout;

  @Inject
  AppService mAppService;
  private ActivityAdapter mAdapter;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    inject();
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
  Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_question_collection, container, false);
    ButterKnife.bind(this, view);
    mAdapter = new ActivityAdapter();
    mListView.getRefreshListView().setAdapter(mAdapter);
    mListView.getRefreshListView().setDividerHeight(1);
    return view;
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    ButterKnife.unbind(this);
  }


  private void inject() {
    DaggerServiceComponent.builder().serviceModule(new ServiceModule()).globalModule(new
        GlobalModule(BaseApplication.getApplication()))
        .build().inject(this);
  }

  @Override
  protected PullToRefreshListView getListView() {
    return mListView;
  }

  @Override
  protected void loadData() {
    // TODO
    manageRpcCall(mAppService.getAllMessage(Url.SESSIONID, "16"), new
        UiRpcSubscriberSimple<MessageModel>(getActivity()) {


          @Override
          protected void onSuccess(MessageModel messageModel) {
            if (null == messageModel.getList() || messageModel.getList().size() == 0) {
              emptyLayout.setVisibility(View.VISIBLE);
              mListView.setVisibility(View.GONE);
            } else {
              emptyLayout.setVisibility(View.GONE);
              mListView.setVisibility(View.VISIBLE);
            }
            mAdapter.clear();
            mAdapter.addAll(messageModel.getList());
            mAdapter.notifyDataSetChanged();
          }

          @Override
          protected void onEnd() {
            resetRefreshStatus();
          }
        });
  }

  class ActivityAdapter extends BasicListAdapter<MessageModel.ListBean> {

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      ViewHolder holder;
      if (null == convertView) {
        convertView = LayoutInflater.from(getActivity()).inflate(R.layout
            .fragment_message_item, parent, false);
        holder = new ViewHolder(convertView);
        convertView.setTag(holder);
      } else {
        holder = (ViewHolder) convertView.getTag();
      }
      final MessageModel.ListBean listBean = getItem(position);
      holder.content.setText(listBean.getContent().getContent());
      holder.time.setText(listBean.getCreate_time());
      convertView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          if (listBean.getType() == 3) {
            Intent intent = CheckMemberListActivity.makeIntent(getActivity(), listBean.getContent
                ().getArgs());
            startActivity(intent);
          } else {
            Intent intent = new Intent(getActivity(), GroupPostInfoActivity.class);
            intent.putExtra(GroupPostInfoActivity.BUNDLE_KEY_POST_ID, listBean.getContent().getArgs
                ());
            startActivity(intent);
          }

          manageRpcCall(mAppService.getMessageContent(listBean.getId()), new
              UiRpcSubscriber1<BaseModel>(getActivity()) {


                @Override
                protected void onSuccess(BaseModel baseModel) {
                  getComponent().getGlobalBus().post(new MyMessageActivity.MessageReadEvent());
                  loadData();
                }

                @Override
                protected void onEnd() {

                }
              });
        }
      });
      return convertView;
    }


    class ViewHolder {
      @Bind(R.id.content)
      TextView content;
      @Bind(R.id.time)
      TextView time;

      ViewHolder(View view) {
        ButterKnife.bind(this, view);
      }
    }
  }
}
