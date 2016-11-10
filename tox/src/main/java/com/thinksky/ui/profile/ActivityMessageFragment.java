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
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.FrameLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.thinksky.holder.BaseApplication;
import com.thinksky.info.WeiboInfo;
import com.thinksky.injection.GlobalModule;
import com.thinksky.net.UiRpcSubscriber1;
import com.thinksky.net.UiRpcSubscriberSimple;
import com.thinksky.net.rpc.model.BaseModel;
import com.thinksky.net.rpc.model.MessageModel;
import com.thinksky.net.rpc.model.WeiboDetailModel;
import com.thinksky.net.rpc.service.AppService;
import com.thinksky.serviceinjection.DaggerServiceComponent;
import com.thinksky.serviceinjection.ServiceModule;
import com.thinksky.tox.R;
import com.thinksky.tox.WeiboDetailActivity;
import com.thinksky.ui.basic.BasicListAdapter;
import com.thinksky.ui.basic.BasicPullToRefreshFragment;
import com.thinksky.ui.common.PullToRefreshListView;
import com.thinksky.utils.DateUtils;
import com.tox.Url;
import java.util.Date;
import javax.inject.Inject;

/**
 * [一句话功能简述]<BR>
 * [功能详细描述]
 *
 * @author zhaozeyang
 * @version [Taobei Client V20160411, 16/8/12]
 */
public class ActivityMessageFragment extends BasicPullToRefreshFragment {
  @Bind(R.id.list)
  PullToRefreshListView mListView;

  @Inject
  AppService mAppService;
  @Bind(R.id.empty_info)
  TextView emptyInfo;
  @Bind(R.id.empty_layout)
  FrameLayout emptyLayout;
  @Bind(R.id.viewStub)
  View stubImport;
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
    manageRpcCall(mAppService.getAllMessage(Url.SESSIONID, "4"), new
        UiRpcSubscriberSimple<MessageModel>(getActivity()) {


          @Override
          protected void onSuccess(MessageModel messageModel) {
            if (null == messageModel.getList() || messageModel.getList().size() == 0) {
              emptyLayout.setVisibility(View.VISIBLE);
            } else {
              emptyLayout.setVisibility(View.GONE);
            }
            mAdapter.clear();
            mAdapter.addAll(messageModel.getList());
            mAdapter.notifyDataSetChanged();
          }

          @Override
          protected void onEnd() {
            resetRefreshStatus();
            dismissInitProgress();
          }
        });
  }

  @Override
  protected View getInitProgress() {
    return stubImport;
  }

  private void getWeiboDetail(final String id, final String messageId) {
    manageRpcCall(mAppService.getWeiboDetail(id), new UiRpcSubscriber1<WeiboDetailModel>
        (getActivity()) {
      @Override
      protected void onSuccess(WeiboDetailModel weiboDetailModel) {
        // TODO 设置MAP,跳转到动态详情
        WeiboDetailModel.ListBean bean = weiboDetailModel.getList().get(0);
        WeiboInfo info = new WeiboInfo();
        info.setWid(String.valueOf(bean.getId()));
        info.setWcontent(bean.getContent());
        info.setRepost_count(String.valueOf(bean.getRepost_count()));
        info.setLikenum(bean.getSupport_count());
        info.setCan_delete(bean.isCan_delete());
        info.setCtime(DateUtils.getFormatDateTime(new Date(bean.getCreate_time()), DateUtils
            .FORMAT_MM_DD_HH_MM));
        info.setImgList(bean.getImages());
        info.setComment_count(String.valueOf(bean.getComment_count()));
        info.setFrom(bean.getFrom());
        info.setIs_top(Integer.parseInt(bean.getIs_top()));
        info.setUser(bean.getUser());
        info.setType(bean.getType());

        if (null != bean.getWeibo_data() && !TextUtils.isEmpty(bean.getWeibo_data().getSourse()
            .getId())) {
          WeiboDetailModel.ListBean.WeiboDataBean weiboDataBean = bean.getWeibo_data();
          WeiboInfo subInfo = new WeiboInfo();
          subInfo.setWid(String.valueOf(weiboDataBean.getSourse().getId()));
          subInfo.setWcontent(weiboDataBean.getSourse().getContent());
          subInfo.setRepost_count(String.valueOf(weiboDataBean.getSourse().getRepost_count()));
          subInfo.setLikenum(weiboDataBean.getSourse().getSupport_count());
          subInfo.setCtime(DateUtils.getFormatDateTime(new Date(weiboDataBean.getSourse()
              .getCreate_time()), DateUtils.FORMAT_MM_DD_HH_MM));
          subInfo.setImgList(weiboDataBean.getSourse().getImages());
          subInfo.setComment_count(weiboDataBean.getSourse().getComment_count());
          subInfo.setFrom(weiboDataBean.getSourse().getFrom());
          subInfo.setIs_top(Integer.parseInt(weiboDataBean.getSourse().getIs_top()));
          subInfo.setUser(weiboDataBean.getSourse().getUser());
          subInfo.setType(weiboDataBean.getSourse().getType());
          info.setRepostWeiboInfo(subInfo);
        }

        Intent intent = new Intent(getActivity(), WeiboDetailActivity.class);
        Bundle bund = new Bundle();
        bund.putSerializable("WeiboInfo", info);
        intent.putExtra("value", bund);
        getActivity().startActivity(intent);
        manageRpcCall(mAppService.getMessageContent(messageId), new
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

      @Override
      protected void onEnd() {

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
      holder.content.setTextColor(getResources().getColor(TextUtils.equals(listBean.getIs_read(),
          "1") ? R.color.font_color_secondary : R.color.font_color_primary));
      holder.time.setText(listBean.getCreate_time());
      convertView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          getWeiboDetail(listBean.getContent().getArgs(), listBean.getId());
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
