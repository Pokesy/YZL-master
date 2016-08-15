package com.thinksky.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.squareup.otto.Subscribe;
import com.thinksky.tox.R;
import com.thinksky.ui.basic.BasicFragment;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiao on 2016/1/27.
 */
public class YlqFragment extends BasicFragment implements View.OnClickListener {
  private TabLayout mTabLayout;
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
    mTabLayout = (TabLayout) view.findViewById(R.id.segment_control);
    mPager = (ViewPager) view.findViewById(R.id.pager);
    List<Fragment> fragments = new ArrayList<>();
    fragments.add(RemenhuatiFragment.newInstance("label1"));
    fragments.add(XiaozujingxuanFragment.newInstance("label2"));
    fragments.add(new MyGroupFragment());
    //fragments.add(LuntanFragment.newInstance("label4"));
    mPager.setAdapter(new PagerAdapter(getChildFragmentManager(), fragments));
    mTabLayout.setupWithViewPager(mPager);
    String []titles = getResources().getStringArray(R.array.ylq_tab_title);
    for(int i=0; i<mTabLayout.getTabCount(); i++) {
      mTabLayout.getTabAt(i).setText(titles[i]);
    }
    mPager.setOffscreenPageLimit(4);
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
