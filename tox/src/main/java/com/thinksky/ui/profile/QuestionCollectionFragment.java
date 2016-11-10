/*
 * 文件名: QuestionCollectionFragmet
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
import com.squareup.otto.Subscribe;
import com.thinksky.fragment.QuestionDetailActivity;
import com.thinksky.holder.BaseApplication;
import com.thinksky.injection.GlobalModule;
import com.thinksky.net.UiRpcSubscriberSimple;
import com.thinksky.net.rpc.model.CollectQuestionModel;
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
public class QuestionCollectionFragment extends BasicPullToRefreshFragment {

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
  private QuestionCollectionAdapter mAdapter;

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
    mAdapter = new QuestionCollectionAdapter();
    mListView.getRefreshListView().setAdapter(mAdapter);
    return view;
  }

  private void inject() {
    DaggerServiceComponent.builder().serviceModule(new ServiceModule()).globalModule(new
        GlobalModule(BaseApplication.getApplication())).build().inject(this);
  }

  @Override
  protected PullToRefreshListView getListView() {
    return mListView;
  }

  @Override
  protected void loadData() {
    manageRpcCall(mAppService.getMyCollectQuestionList(Url.SESSIONID, getCurrentPage(),
        PAGE_COUNT), new UiRpcSubscriberSimple<CollectQuestionModel>(getActivity()) {


      @Override
      protected void onSuccess(CollectQuestionModel collectQuestionModel) {
        if (getCurrentPage() == 0) {
          mAdapter.clear();
        }
        if (null == collectQuestionModel.getList() || collectQuestionModel.getList().size() == 0) {
          emptyLayout.setVisibility(View.VISIBLE);
        } else {
          emptyLayout.setVisibility(View.GONE);
        }
        mAdapter.addAll(collectQuestionModel.getList());
        mAdapter.notifyDataSetChanged();
        onRefreshLoaded(collectQuestionModel.getList().size() >= PAGE_COUNT);
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

  @Subscribe
  public void handleQuestionDataChangeEvent(QuestionDetailActivity.AnswerChangedEvent event) {
    resetCurrentPage();
    loadData();
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    ButterKnife.unbind(this);
  }

  class QuestionCollectionAdapter extends BasicListAdapter<CollectQuestionModel.ListBean> {

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      ViewHolder holder;
      if (null == convertView) {
        convertView = LayoutInflater.from(getActivity()).inflate(R.layout
            .fragment_question_colletion_item, parent, false);
        holder = new ViewHolder(convertView);
        convertView.setTag(holder);
      } else {
        holder = (ViewHolder) convertView.getTag();
      }
      final CollectQuestionModel.ListBean bean = getItem(position);
      holder.title.setText(bean.getTitle());
      holder.status.setText(TextUtils.equals(bean.getBest_answer(), "1") ? R.string
          .question_status_resolved : R.string.question_status_un_resolved);
      holder.answerCount.setText(bean.getAnswer_num());
      holder.time.setText(bean.getCreate_time());
      convertView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          Bundle bundle = new Bundle();
          bundle.putString("question_id", bean.getId());
          Intent intent = new Intent(getActivity(), QuestionDetailActivity.class);
          intent.putExtras(bundle);
          startActivity(intent);
        }
      });
      return convertView;
    }

    class ViewHolder {
      @Bind(R.id.title)
      TextView title;
      @Bind(R.id.status)
      TextView status;
      @Bind(R.id.answer_count)
      TextView answerCount;
      @Bind(R.id.time)
      TextView time;

      ViewHolder(View view) {
        ButterKnife.bind(this, view);
      }
    }
  }
}
