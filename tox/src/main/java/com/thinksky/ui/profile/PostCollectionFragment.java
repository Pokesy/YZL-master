/*
 * 文件名: PostCollectionFragment
 * 版    权：  Copyright Hengrtech Tech. Co. Ltd. All Rights Reserved.
 * 描    述: [该类的简要描述]
 * 创建人: zhaozeyang
 * 创建时间:16/8/10
 * 
 * 修改人：
 * 修改时间:
 * 修改内容：[修改内容]
 */
package com.thinksky.ui.profile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.thinksky.holder.BaseApplication;
import com.thinksky.injection.GlobalModule;
import com.thinksky.net.UiRpcSubscriberSimple;
import com.thinksky.net.rpc.model.CollectPostModel;
import com.thinksky.net.rpc.service.AppService;
import com.thinksky.serviceinjection.DaggerServiceComponent;
import com.thinksky.serviceinjection.ServiceModule;
import com.thinksky.tox.R;
import com.thinksky.ui.basic.BasicListAdapter;
import com.thinksky.ui.basic.BasicPullToRefreshFragment;
import com.thinksky.ui.common.PullToRefreshListView;
import com.tox.Url;
import javax.inject.Inject;

/**
 * [一句话功能简述]<BR>
 * [功能详细描述]
 *
 * @author zhaozeyang
 * @version [Taobei Client V20160411, 16/8/10]
 */
public class PostCollectionFragment extends BasicPullToRefreshFragment {
  @Bind(R.id.list)
  PullToRefreshListView mListView;

  @Inject
  AppService mAppService;
  private PostCollectionAdapter mAdapter;

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
    mAdapter = new PostCollectionAdapter();
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
    manageRpcCall(mAppService.getMyCollectPostList(Url.SESSIONID, getCurrentPage(), PAGE_COUNT),
        new UiRpcSubscriberSimple<CollectPostModel>(getActivity()) {


          @Override
          protected void onSuccess(CollectPostModel collectPostModel) {
            if (getCurrentPage() == 0) {
              mAdapter.clear();
            }
            mAdapter.addAll(collectPostModel.getList());
            mAdapter.notifyDataSetChanged();
            onRefreshLoaded(collectPostModel.getList().size() >= PAGE_COUNT);
          }

          @Override
          protected void onEnd() {

          }
        });
  }

  class PostCollectionAdapter extends BasicListAdapter<CollectPostModel.ListBean> {

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      ViewHolder holder;
      if (null == convertView) {
        convertView = LayoutInflater.from(getActivity()).inflate(R.layout
            .fragment_post_colletion_item, parent, false);
        holder = new ViewHolder(convertView);
        convertView.setTag(holder);
      } else {
        holder = (ViewHolder) convertView.getTag();
      }
      CollectPostModel.ListBean listBean = getItem(position);
      holder.title.setText(listBean.getTitle());
      if (!TextUtils.isEmpty(listBean.getContent())) {
        holder.content.setText(listBean.getContent().replaceAll("\\n", "\n"));
      } else {
        holder.content.setVisibility(View.GONE);
      }
      holder.content.setText(listBean.getContent());
      holder.commentCount.setText(listBean.getReply_count());
      // TODO
      //holder.likeCount.setText(listBean.getView_count());
      return convertView;
    }

    class ViewHolder {
      @Bind(R.id.title)
      TextView title;
      @Bind(R.id.content)
      TextView content;
      @Bind(R.id.comment_count)
      TextView commentCount;
      @Bind(R.id.like_count)
      TextView likeCount;

      ViewHolder(View view) {
        ButterKnife.bind(this, view);
      }
    }
  }
}
