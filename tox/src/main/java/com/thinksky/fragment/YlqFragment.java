package com.thinksky.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.squareup.otto.Subscribe;
import com.thinksky.tox.R;
import com.thinksky.tox.SegmentControl;
import com.thinksky.ui.basic.BasicFragment;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiao on 2016/1/27.
 */
public class YlqFragment extends BasicFragment implements View.OnClickListener {
  private SegmentControl mSegmentControl;
  private ViewPager mPager;

  @Override
  public void onClick(View view) {

  }

  private View view;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    view = inflater.inflate(R.layout.activity_yulequan, null);
    initView();
    return view;
  }

  private void initView() {
    mSegmentControl = (SegmentControl) view.findViewById(R.id.segment_control);
    mPager = (ViewPager) view.findViewById(R.id.pager);
    List<Fragment> fragments = new ArrayList<>();
//        RemenhuatiFragment remenhuatiFragment = new RemenhuatiFragment();
//        XiaozujingxuanFragment xiaozujingxuanFragment = new XiaozujingxuanFragment();
//        WodexiaozuFragment wodexiaozuFragment = new WodexiaozuFragment();
//        LuntanFragment luntanFragment = new LuntanFragment();
//
//        fragments.add(remenhuatiFragment);
//        fragments.add(xiaozujingxuanFragment);
//        fragments.add(wodexiaozuFragment);
//        fragments.add(luntanFragment);
    fragments.add(RemenhuatiFragment.newInstance("label1"));
    fragments.add(XiaozujingxuanFragment.newInstance("label2"));
    fragments.add(WodexiaozuFragment.newInstance("label3"));
    //fragments.add(LuntanFragment.newInstance("label4"));
    mPager.setAdapter(new PagerAdapter(getChildFragmentManager(), fragments));
    mSegmentControl.setSelectedTextColor(getResources().getColor(android.R.color.white));
    mSegmentControl.setOnSegmentControlClickListener(new SegmentControl
        .OnSegmentControlClickListener() {
      @Override
      public void onSegmentControlClick(int index) {
        mPager.setCurrentItem(index);
      }
    });
    mPager.setOffscreenPageLimit(4);
    mPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
      @Override
      public void onPageSelected(int position) {
        super.onPageSelected(position);
        mSegmentControl.setCurrentIndex(position);
//                if(position %2 != 0) {
//                    mSegmentControl.setSelectedBackgroundColors(getResources().getColor(android
// .R.color.darker_gray));
//                }else {
//                    mSegmentControl.setSelectedBackgroundColors(0xff009688);
//                }
      }
    });
  }

  private static class PagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments;

    public PagerAdapter(FragmentManager fm, List<Fragment> fragments) {
      super(fm);
      this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
      return fragments.get(position);
    }

    @Override
    public int getCount() {
      return fragments.size();
    }
  }

  @Subscribe
  public void handleGroupMoreClickEvent(HomeFragment.GroupMoreClickEvent event) {
    // TODO 跳转到鱼乐圈小组精选
    mPager.setCurrentItem(1);
  }

  @Subscribe
  public void handleHuatiMoreClickEvent(HomeFragment.HuatiMoreClickEvent event) {
    // TODO 跳转到鱼乐圈话题
    mPager.setCurrentItem(0);
  }
}
