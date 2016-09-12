/*
 * 文件名: MyMessageActivity
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

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.squareup.otto.Subscribe;
import com.thinksky.holder.BaseApplication;
import com.thinksky.holder.BaseBActivity;
import com.thinksky.injection.GlobalModule;
import com.thinksky.net.UiRpcSubscriber1;
import com.thinksky.net.rpc.model.UnReadCountModel;
import com.thinksky.net.rpc.service.AppService;
import com.thinksky.serviceinjection.DaggerServiceComponent;
import com.thinksky.serviceinjection.ServiceModule;
import com.thinksky.tox.R;
import com.thinksky.ui.common.TitleBar;
import com.tox.Url;
import javax.inject.Inject;

/**
 * [一句话功能简述]<BR>
 * [功能详细描述]
 *
 * @author zhaozeyang
 * @version [Taobei Client V20160411, 16/8/12]
 */
public class MyMessageActivity extends BaseBActivity {

  @Bind(R.id.tab_layout)
  TabLayout mTabLayout;
  @Bind(R.id.pagers)
  ViewPager mPagers;
  @Bind(R.id.title_bar)
  TitleBar titleBar;

  @Inject
  AppService mAppService;

  private View[] mIconViews;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    inject();
    setContentView(R.layout.activity_my_collection);
    ButterKnife.bind(this);
    initView();
    loadUnreadCount();
  }

  private void inject() {
    DaggerServiceComponent.builder().serviceModule(new ServiceModule()).globalModule(new
        GlobalModule(BaseApplication.getApplication()))
        .build().inject(this);
  }

  private void initView() {
    titleBar.setMiddleTitle(R.string.activity_my_message_title);
    titleBar.setLeftImgMenu(R.drawable.icon_title_bar_back, new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });

    String[] titles = getResources().getStringArray(R.array.my_message_tab_titles);
    MessagePagerAdapter adapter = new MessagePagerAdapter(getSupportFragmentManager());
    mPagers.setAdapter(adapter);
    mPagers.setOffscreenPageLimit(titles.length);
    mTabLayout.setupWithViewPager(mPagers);
    mIconViews = new View[titles.length];
    for (int i = 0; i < titles.length; i++) {
      View view = LayoutInflater.from(this).inflate(R.layout.activity_my_message_tab, mTabLayout,
          false);
      TextView titleView = (TextView) view.findViewById(R.id.title);
      titleView.setText(titles[i]);
      mIconViews[i] = view.findViewById(R.id.icon_status);
      mTabLayout.getTabAt(i).setCustomView(view);
    }
  }

  class MessagePagerAdapter extends FragmentPagerAdapter {

    public MessagePagerAdapter(FragmentManager fm) {
      super(fm);
    }

    @Override
    public Fragment getItem(int position) {
      if (position == MessagePagerType.ACTIVITY.ordinal()) {
        return new ActivityMessageFragment();
      } else if (position == MessagePagerType.DOCTOR.ordinal()) {
        return new DoctorMessageFragment();
      } else if (position == MessagePagerType.YLQ.ordinal()) {
        return new YLQMessageFragment();
      }
      return null;
    }

    @Override
    public int getCount() {
      return MessagePagerType.values().length;
    }
  }

  private enum MessagePagerType {
    ACTIVITY, DOCTOR, YLQ
  }

  private void loadUnreadCount() {
    manageRpcCall(mAppService.getUnreadCount("4", Url.SESSIONID), new
        UiRpcSubscriber1<UnReadCountModel>(this) {


          @Override
          protected void onSuccess(UnReadCountModel unReadCountModel) {
            mIconViews[0].setVisibility(unReadCountModel.getCount() > 0 ? View.VISIBLE : View.GONE);
          }

          @Override
          protected void onEnd() {

          }
        });
    manageRpcCall(mAppService.getUnreadCount("16", Url.SESSIONID), new
        UiRpcSubscriber1<UnReadCountModel>(this) {


          @Override
          protected void onSuccess(UnReadCountModel unReadCountModel) {
            mIconViews[2].setVisibility(unReadCountModel.getCount() > 0 ? View.VISIBLE : View.GONE);
          }

          @Override
          protected void onEnd() {

          }
        });
    manageRpcCall(mAppService.getUnreadCount("23", Url.SESSIONID), new
        UiRpcSubscriber1<UnReadCountModel>(this) {


          @Override
          protected void onSuccess(UnReadCountModel unReadCountModel) {
            mIconViews[1].setVisibility(unReadCountModel.getCount() > 0 ? View.VISIBLE : View.GONE);
          }

          @Override
          protected void onEnd() {

          }
        });
  }

  @Subscribe
  public void handleMessageReadEvent(MessageReadEvent event) {
    loadUnreadCount();
  }

  public static class MessageReadEvent {

  }
}
