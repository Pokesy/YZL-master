/*
 * 文件名: QuestionListActivity
 * 版    权：  Copyright Hengrtech Tech. Co. Ltd. All Rights Reserved.
 * 描    述: [该类的简要描述]
 * 创建人: zhaozeyang
 * 创建时间:16/8/16
 * 
 * 修改人：
 * 修改时间:
 * 修改内容：[修改内容]
 */
package com.thinksky.ui.question;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.thinksky.holder.BaseApplication;
import com.thinksky.holder.BaseBActivity;
import com.thinksky.injection.GlobalModule;
import com.thinksky.net.UiRpcSubscriberSimple;
import com.thinksky.net.rpc.model.QuestionCategoryModel;
import com.thinksky.net.rpc.service.AppService;
import com.thinksky.serviceinjection.DaggerServiceComponent;
import com.thinksky.serviceinjection.ServiceModule;
import com.thinksky.tox.R;
import com.thinksky.tox.SendQuestionActivity;
import com.thinksky.ui.common.TitleBar;
import com.tox.ToastHelper;
import com.tox.Url;
import java.util.List;
import javax.inject.Inject;

/**
 * [一句话功能简述]<BR>
 * [功能详细描述]
 *
 * @author zhaozeyang
 * @version [Taobei Client V20160411, 16/8/16]
 */
public class QuestionListActivity extends BaseBActivity {
  public static final String TYPE_HOT = "hot";
  public static final String TYPE_MAX_AWARD = "max_award";
  public static final String TYPE_SOLUTION = "solution";
  public static final String TYPE_MINE = "mine";
  private static final String BUNDLE_KEY_WHICH_ACTIVITY = "which_activity";
  @Bind(R.id.tabs)
  TabLayout tabs;
  @Bind(R.id.pager)
  ViewPager pager;
  @Inject
  AppService mAppService;
  @Bind(R.id.title_bar)
  TitleBar titleBar;

  private String mWhichActivity;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    inject();
    setContentView(R.layout.activity_question_list);
    ButterKnife.bind(this);
    mWhichActivity = getIntent().getStringExtra(BUNDLE_KEY_WHICH_ACTIVITY);
    initTitleBar();
    loadData();
  }

  private void initTitleBar() {
    titleBar.setLeftImgMenu(R.drawable.icon_title_bar_back, new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });

    switch (mWhichActivity) {
      case TYPE_HOT:
        titleBar.setMiddleTitle(R.string.question_list_title_hot);
        break;
      case TYPE_MAX_AWARD:
        titleBar.setMiddleTitle(R.string.question_list_title_max_award);
        break;
      case TYPE_SOLUTION:
        titleBar.setMiddleTitle(R.string.question_list_title_solution);
        break;
      case TYPE_MINE:
        titleBar.setMiddleTitle(R.string.question_list_title_mine);
        break;
    }

    titleBar.setRightTextBtn(R.string.question_list_title_bar_btn_ask, new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (getComponent().loginSession().isLogin()) {
          Intent intent = new Intent(QuestionListActivity.this, SendQuestionActivity.class);
          startActivity(intent);
        } else {
          ToastHelper.showToast("请登录", Url.context);
        }
      }
    });
  }

  private void inject() {
    DaggerServiceComponent.builder().globalModule(new GlobalModule(BaseApplication.getApplication
        ())).serviceModule(new ServiceModule())
        .build().inject(this);
  }

  private void loadData() {
    manageRpcCall(mAppService.getQuestionCategory(), new
        UiRpcSubscriberSimple<QuestionCategoryModel>(this) {


          @Override
          protected void onSuccess(QuestionCategoryModel questionCategoryModel) {
            bindViewData(questionCategoryModel.getList());
          }

          @Override
          protected void onEnd() {

          }
        });
  }

  private void bindViewData(List<QuestionCategoryModel.ListBean> beans) {
    if (null == beans) {
      return;
    }
    CategoryAdapter adapter = new CategoryAdapter(getSupportFragmentManager(), beans);
    pager.setAdapter(adapter);
    pager.setOffscreenPageLimit(beans.size());
    tabs.setupWithViewPager(pager);
    for (int i = 0; i < beans.size() + 1; i++) {
      if (i == 0) {
        tabs.getTabAt(i).setText(R.string.question_category_all);
        continue;
      }
      tabs.getTabAt(i).setText(beans.get(i - 1).getTitle());
    }
  }

  private class CategoryAdapter extends FragmentPagerAdapter {

    private List<QuestionCategoryModel.ListBean> mTitles;

    public CategoryAdapter(FragmentManager fm, List<QuestionCategoryModel.ListBean> titles) {
      super(fm);
      this.mTitles = titles;
    }

    @Override
    public Fragment getItem(int position) {
      if (0 == position) {
        return QuestionListFragment.newInstance(mWhichActivity, "0");
      }
      return QuestionListFragment.newInstance(mWhichActivity, mTitles.get(position - 1).getId());
    }

    @Override
    public int getCount() {
      return (null == mTitles ? 0 : mTitles.size()) + 1;
    }
  }

  public static Intent makeIntent(Context context, String whichActivity) {
    Intent intent = new Intent(context, QuestionListActivity.class);
    intent.putExtra(BUNDLE_KEY_WHICH_ACTIVITY, whichActivity);
    return intent;
  }
}
