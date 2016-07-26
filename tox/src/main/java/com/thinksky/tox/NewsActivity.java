package com.thinksky.tox;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.thinksky.PagerSlidingTabStrip.PagerSlidingTabStrip;
import com.thinksky.fragment.NewsFragment;
import com.thinksky.holder.BaseBActivity;
import com.thinksky.info.NewsCategory;
import com.thinksky.utils.MyJson;
import com.tox.NewsApi;
import com.tox.ToastHelper;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by HuJiaYu on 2015/7/7
 */
public class NewsActivity extends BaseBActivity implements View.OnClickListener {

  protected ImageView backMenu;
  protected ImageView newsPost, myNews;
  protected ViewPager newsPager;
  protected PagerSlidingTabStrip newsTabs;
  private TabLayout mTablayout;
  private LinearLayout newsBody;
  protected RelativeLayout proBarLine;
  protected NewsApi newsApi;
  private Context context;
  private MyHandler mHandler = new MyHandler(NewsActivity.this);
  protected String id = "0";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.activity_news);
    context = NewsActivity.this;
    newsApi = new NewsApi(mHandler);
    newsApi.getNavigation(id);
    intView();
  }

  public void intView() {
    backMenu = (ImageView) findViewById(R.id.back_menu);
    newsPost = (ImageView) findViewById(R.id.news_post);
    myNews = (ImageView) findViewById(R.id.my_news);
    newsPager = (ViewPager) findViewById(R.id.news_pager);
    newsTabs = (PagerSlidingTabStrip) findViewById(R.id.news_tabs);
    newsBody = (LinearLayout) findViewById(R.id.newsBody);
    mTablayout = (TabLayout) findViewById(R.id.first_navigation_line);
    proBarLine = (RelativeLayout) findViewById(R.id.proBarLine);
    backMenu.setOnClickListener(this);
    newsPost.setOnClickListener(this);
    myNews.setOnClickListener(this);

  }

  @Override
  public void onClick(View v) {
    int viewID = v.getId();
    switch (viewID) {
      case R.id.back_menu:
        finish();
        break;
      case R.id.my_news://我的资讯
        if (!newsApi.getSeesionId().equals("")) {
          Intent myNewsIntent = new Intent(context, NewsMeActivity.class);
          startActivity(myNewsIntent);
        } else {
          ToastHelper.showToast("请登陆后操作", context);
        }
        break;
      case R.id.news_post://资讯投稿
        Intent postNewsIntent = new Intent(context, PostNewsActivity.class);
        startActivity(postNewsIntent);
        break;
      default:
        break;
    }
  }

  private static class MyHandler extends Handler {

    WeakReference<NewsActivity> mActivityReference;
    private MyJson myJson;
    private ArrayList<NewsCategory> navigationLineList;
    private NewsFragmentAdapter mFragmentAdapter;

    MyHandler(NewsActivity activity) {
      mActivityReference = new WeakReference<NewsActivity>(activity);
    }

    @Override
    public void handleMessage(Message msg) {

      final NewsActivity activity = mActivityReference.get();
      myJson = new MyJson();
      if (activity != null) {
        switch (msg.what) {
          case 0:
            String result = (String) msg.obj;
            navigationLineList = myJson.getNewsNavigation(result);
            if (null != result && navigationLineList.size() > 0) {
              LinearLayout.LayoutParams textLpa = new LinearLayout.LayoutParams(
                  ViewGroup.LayoutParams.WRAP_CONTENT,
                  ViewGroup.LayoutParams.WRAP_CONTENT);
              textLpa.setMargins(5, 0, 20, 0);
              String[] navigationTexts = new String[navigationLineList.size()];
              List<NewsFragment> mFragmentList = new ArrayList<>();
              for (int i = 0; i < navigationLineList.size(); i++) {
                mFragmentList.add(NewsFragment.newInstance(navigationLineList.get(i)));
                navigationTexts[i] = navigationLineList.get(i).getTitle();
              }
              if (null == mFragmentAdapter) {
                mFragmentAdapter = new NewsFragmentAdapter(activity.getSupportFragmentManager(),
                    mFragmentList, navigationTexts);
              }
              activity.newsPager.setAdapter(mFragmentAdapter);
              activity.newsPager.setCurrentItem(0);
              activity.mTablayout.setupWithViewPager(activity.newsPager);
              for (int i = 0; i < activity.mTablayout.getTabCount(); i++) {
                activity.mTablayout.getTabAt(i).setText(navigationTexts[i]);
              }
            }
            activity.newsBody.setVisibility(View.VISIBLE);
            activity.proBarLine.setVisibility(View.GONE);
            break;
          default:
            break;
        }
      }
    }

    private class NewsFragmentAdapter extends FragmentStatePagerAdapter {

      private List<NewsFragment> mFragments;
      private String[] mTitles;

      public NewsFragmentAdapter(FragmentManager fm, List<NewsFragment> fragments, String[]
          titles) {
        super(fm);
        mFragments = fragments;
        mTitles = titles;
      }

      @Override
      public Fragment getItem(int position) {
        return mFragments.get(position);
      }

      @Override
      public int getCount() {
        return null == mTitles || mTitles.length == 0 ? 0 : mTitles.length;
      }

      @Override
      public CharSequence getPageTitle(int position) {
        return null == mTitles || mTitles.length == 0 ? "" : mTitles[position];
      }
    }

  }
}