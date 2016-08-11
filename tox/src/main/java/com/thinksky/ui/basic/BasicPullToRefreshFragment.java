/*
 * 文件名: BasicPullToRefreshFragment
 * 版    权：  Copyright Hengrtech Tech. Co. Ltd. All Rights Reserved.
 * 描    述: [该类的简要描述]
 * 创建人: zhaozeyang
 * 创建时间:16/8/10
 * 
 * 修改人：
 * 修改时间:
 * 修改内容：[修改内容]
 */
package com.thinksky.ui.basic;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import com.thinksky.ui.common.PullToRefreshListView;

/**
 * [一句话功能简述]<BR>
 * [功能详细描述]
 *
 * @author zhaozeyang
 * @version [Taobei Client V20160411, 16/8/10]
 */
public abstract class BasicPullToRefreshFragment extends BasicFragment {
  protected static final int PAGE_COUNT = 20;
  private static final int START_PAGE = 1;
  private static final int LOAD_MORE_VALUE = 3;
  private int mCurrentPage = START_PAGE;

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    getListView().setScrollToLoadListener(new PullToRefreshListView.ScrollToLoadListener() {
      @Override
      public void onPullUpLoadData() {
        loadData();
      }

      @Override
      public void onPullDownLoadData() {
        mCurrentPage = 0;
        loadData();
      }
    }, LOAD_MORE_VALUE);
    loadData();
  }

  protected abstract PullToRefreshListView getListView();

  protected abstract void loadData();

  protected void resetRefreshStatus() {
    getListView().resetPullStatus();
  }

  protected void onRefreshLoaded(boolean hasNew) {
    if(hasNew) {
      mCurrentPage ++;
    }
    getListView().setPullUpToRefresh(hasNew);
  }

  protected void resetCurrentPage() {
    mCurrentPage = START_PAGE;
  }

  protected int getCurrentPage() {
    return mCurrentPage;
  }

}
