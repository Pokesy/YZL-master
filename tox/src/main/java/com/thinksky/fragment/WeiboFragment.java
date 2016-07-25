package com.thinksky.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.thinksky.tox.SegmentControl;
import com.thinksky.ui.weibo.WeiboListFragment;
import com.tox.BaseFunction;
import com.tox.ToastHelper;
import java.util.ArrayList;

/**
 * 热门的fragment
 */
public class WeiboFragment extends Fragment {

  @Bind(R.id.segment_control)
  SegmentControl mSegmentControl;
  @Bind(R.id.pager)
  CustomViewPager mPager;

  //获取可用注册方式
  private ArrayList<String> ways = new ArrayList<String>();

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
    mPager.setOffscreenPageLimit(3);
    mSegmentControl.setTouchInterceptor(new SegmentControl.TouchInterceptor() {
      @Override
      public boolean onIntercept(int index) {
        boolean intercept = !BaseFunction.isLogin() && (index > 0);
        if (intercept) {
          ToastHelper.showToast(R.string.prompt_offline, getActivity());
        }
        return intercept;
      }
    });
    mSegmentControl.setOnSegmentControlClickListener(new SegmentControl
        .OnSegmentControlClickListener() {
      @Override
      public void onSegmentControlClick(int index) {
        mPager.setCurrentItem(index);
      }
    });

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
    HOT, FOLLOW, MY
  }

}
