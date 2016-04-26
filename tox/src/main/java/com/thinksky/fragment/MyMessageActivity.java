package com.thinksky.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.thinksky.holder.BaseBActivity;
import com.thinksky.tox.R;
import com.thinksky.tox.SegmentControl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiao on 2016/4/25.
 */
public class MyMessageActivity extends BaseBActivity {
    private SegmentControl mSegmentControl2;
    private ViewPager mPager;
    private static final String ARG_PARAM1 = "param1";
    private ImageView back_menu;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mymessage);
        initView();
    }
    private void initView() {
        mSegmentControl2 = (SegmentControl)findViewById(R.id.segment_control2);
        mPager = (ViewPager) findViewById(R.id.pager);
        back_menu= (ImageView) findViewById(R.id.back_menu);
        back_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        List<Fragment> fragments = new ArrayList<>();
//        WendaFragment wendaFragment = new WendaFragment();
//        ZhuanjiFragment zhuanjiFragment = new ZhuanjiFragment();
//        BaikeFragment baikeFragment = new BaikeFragment();
//        IssueFragment issueFragment = new IssueFragment();
        fragments.add(DongtaiMessageFragment.newInstance("label1"));
        fragments.add(DongtaiMessageFragment.newInstance("label2"));
//        fragments.add(IssueFragment.newInstance("问答"));
        fragments.add(DongtaiMessageFragment.newInstance("label3"));
        mPager.setAdapter(new PagerAdapter(getSupportFragmentManager(), fragments));
        mSegmentControl2.setSelectedTextColor(getResources().getColor(android.R.color.white));
        mSegmentControl2.setOnSegmentControlClickListener(new SegmentControl.OnSegmentControlClickListener() {
            @Override
            public void onSegmentControlClick(int index) {
                mPager.setCurrentItem(index);
            }
        });
        mPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                mSegmentControl2.setCurrentIndex(position);
//                mSegmentControl2.setSelectedBackgroundColors(getResources().getColor(android.R.color.holo_blue_light));
//                if (position % 2 != 0) {
//                    mSegmentControl2.setSelectedBackgroundColors(getResources().getColor(android.R.color.holo_blue_light));
//                } else {
//                    mSegmentControl2.setSelectedBackgroundColors(getResources().getColor(android.R.color.white));
//                }
            }
        });
//        FragmentTransaction mFragmentTransaction = mFragmentManager
//                .beginTransaction();
//        mFragmentTransaction.replace(R.id.wenda, zhuanjiFragment);
//        mFragmentTransaction.commit();
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
}
