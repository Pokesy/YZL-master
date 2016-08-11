package com.thinksky.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.thinksky.myview.CustomViewPager;
import com.thinksky.tox.R;
import com.thinksky.ui.basic.BasicFragment;
import com.thinksky.ui.weibo.WeiboListFragment;
import com.tox.BaseFunction;
import com.tox.ToastHelper;

/**
 * 热门的fragment
 */
public class WeiboFragment extends BasicFragment {

  @Bind(R.id.tab_layout)
  TabLayout mTabLayout;
  @Bind(R.id.pager)
  CustomViewPager mPager;

  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.frame_home, null);
    ButterKnife.bind(this, view);
    return view;
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    initView();
  }

  private void initView() {
    mPager.setPagingEnabled(BaseFunction.isLogin());
    mPager.setAdapter(new WeiboPagerAdapter(getChildFragmentManager()));
    mTabLayout.setupWithViewPager(mPager);
    String[] titles = getResources().getStringArray(R.array.weibo_tab_title);
    mPager.setOffscreenPageLimit(titles.length);
    for (int i = 0; i < titles.length; i++) {
      TabLayout.Tab tab = mTabLayout.getTabAt(i);
      tab.setText(titles[i]);
      //tab.setCustomView(new TextView(getActivity()));
      //View tabView = (View) tab.getCustomView().getParent();
      //tabView.setTag(i);
      //tabView.setOnClickListener(mTabClickListener);
    }

    mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
      @Override
      public void onTabSelected(TabLayout.Tab tab) {
        if (tab.getPosition() > 0 && !BaseFunction.isLogin()) {
          mPager.setCurrentItem(0);
          mTabLayout.getTabAt(0).select();
          ToastHelper.showToast(R.string.prompt_offline, getActivity());
        } else {
          mPager.setCurrentItem(tab.getPosition());
        }
      }

      @Override
      public void onTabUnselected(TabLayout.Tab tab) {

      }

      @Override
      public void onTabReselected(TabLayout.Tab tab) {

      }
    });
  }

  @Override
  protected void onLogin() {
    super.onLogin();
    mPager.setPagingEnabled(BaseFunction.isLogin());
    mTabLayout.getTabAt(0).select();
  }

  @Override
  protected void onLogout() {
    super.onLogout();
    mPager.setPagingEnabled(BaseFunction.isLogin());
    mTabLayout.getTabAt(0).select();
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    ButterKnife.unbind(this);
  }

  private class WeiboPagerAdapter extends FragmentPagerAdapter {

    public WeiboPagerAdapter(FragmentManager fm) {
      super(fm);
    }

    @Override
    public Fragment getItem(int position) {
      return WeiboListFragment.newInstance(position);
    }

    @Override
    public int getCount() {
      return WeiboType.values().length;
    }
  }

  private enum WeiboType {
    LATEST,HOT, FOLLOW, MY
  }

}
