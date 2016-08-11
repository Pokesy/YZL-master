/*
 * 文件名: MyCollectionActivity
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
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.thinksky.holder.BaseBActivity;
import com.thinksky.tox.R;
import com.thinksky.ui.common.TitleBar;

/**
 * [一句话功能简述]<BR>
 * [功能详细描述]
 *
 * @author zhaozeyang
 * @version [Taobei Client V20160411, 16/8/10]
 */
public class MyCollectionActivity extends BaseBActivity {
  @Bind(R.id.tab_layout)
  TabLayout mTabLayout;
  @Bind(R.id.pagers)
  ViewPager mPagers;
  @Bind(R.id.title_bar)
  TitleBar titleBar;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_my_collection);
    ButterKnife.bind(this);
    initView();
  }

  private void initView() {
    titleBar.setMiddleTitle(R.string.activity_my_collection_title);
    titleBar.setLeftImgMenu(R.drawable.arrow_left, new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });

    String[] titles = getResources().getStringArray(R.array.my_collection_tab_titles);
    CollectionPagerAdapter adapter = new CollectionPagerAdapter(getSupportFragmentManager());
    mPagers.setAdapter(adapter);
    mTabLayout.setupWithViewPager(mPagers);
    for (int i = 0; i < titles.length; i++) {
      mTabLayout.getTabAt(i).setText(titles[i]);
    }
  }

  class CollectionPagerAdapter extends FragmentPagerAdapter {

    public CollectionPagerAdapter(FragmentManager fm) {
      super(fm);
    }

    @Override
    public Fragment getItem(int position) {
      if (position == CollectionType.POST.ordinal()) {
        return new PostCollectionFragment();
      } else if (position == CollectionType.QUESTION.ordinal()) {
        return new QuestionCollectionFragment();
      }
      return null;
    }

    @Override
    public int getCount() {
      return CollectionType.values().length;
    }
  }

  private enum CollectionType {
    POST, QUESTION;
  }
}
