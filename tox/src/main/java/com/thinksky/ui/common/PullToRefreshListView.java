/*
 * 文件名: PullToRefreshListView.java
 * 版    权：  Copyright Paitao Tech. Co. Ltd. All Rights Reserved.
 * 描    述: 下拉上拉刷新的列表控件
 * 创建人: zhaozeyang
 * 创建时间:2014-4-1
 * 
 * 修改人：
 * 修改时间:
 * 修改内容：[修改内容]
 */
package com.thinksky.ui.common;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import com.thinksky.tox.R;


/**
 * 下拉上拉刷新的列表控件<BR>
 *
 * @author zhaozeyang
 */
public class PullToRefreshListView extends LinearLayout {

  /**
   * 真实展示数据的listView
   */
  private ListView mListView;

  private View mFooterRootView;

  /**
   * 展示刷新状态的控件
   */
  private SwipeRefreshLayout mRefreshLayout;

  /**
   * 需要加载下一页的位置
   */
  private ScrollToLoadListener mScrollToLoadListener;

  /**
   * 需要加载刷新的位置
   */
  private int mNeedLoadPosDesc = 0;

  private boolean mPullUpToRefreshable = true;

  /**
   * 是否在上拉刷新状态
   */
  private boolean mIsPullUpRefreshing = false;

  /**
   * 每页加载数据
   */
  private int mPageCount = 20;

  /**
   * 构造方法
   *
   * @param context 上下文
   * @param attrs 属性
   */
  public PullToRefreshListView(Context context, AttributeSet attrs) {
    super(context, attrs);
    initView();
  }

  /**
   * 初始化view<BR>
   */
  public void initView() {
    View contentView = LayoutInflater.from(getContext())
        .inflate(R.layout.refresh_list, null);
    View footerView = LayoutInflater.from(getContext())
        .inflate(R.layout.refresh_list_footer, null);
    mFooterRootView = footerView.findViewById(R.id.root_layout);
    mListView = (ListView) contentView.findViewById(R.id.listview);
    mListView.addFooterView(footerView);
    mRefreshLayout = (SwipeRefreshLayout) contentView.findViewById(R.id.swipe_container);
    addView(contentView, new LayoutParams(
        LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
    setListParam();

    setRefreshLayoutParam();
  }

  private void setRefreshLayoutParam() {
    mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {

      @Override
      public void onRefresh() {
        // 如果当前处于上拉刷新状态，则不进行下拉刷新
        if (mIsPullUpRefreshing) {
          mRefreshLayout.setRefreshing(false);
          return;
        }
        if (null != mScrollToLoadListener) {
          mScrollToLoadListener.onPullDownLoadData();
        }
      }
    });
  }

  /**
   * 获取展示列表数据的视图<BR>
   *
   * @return ListView
   */
  public ListView getRefreshListView() {
    return mListView;
  }

  /**
   * 获得下拉刷新的布局控件<BR>
   *
   * @return SwipeRefreshLayout
   */
  public SwipeRefreshLayout getRefreshLayout() {
    return mRefreshLayout;
  }

  /**
   * 设置滑动加载数据监听<BR>
   *
   * @param listener 监听
   * @param needLoadPosDesc 倒数第几个进行刷新
   */
  public void setScrollToLoadListener(ScrollToLoadListener listener,
                                      int needLoadPosDesc) {
    mScrollToLoadListener = listener;
    mNeedLoadPosDesc = needLoadPosDesc > 0 ? needLoadPosDesc : 0;
  }

  /**
   * 设置是否可以上拉刷新<BR>
   *
   * @param pullUpTorefreshable 上拉刷新
   */
  public void setPullUpToRefresh(boolean pullUpTorefreshable) {
    mPullUpToRefreshable = pullUpTorefreshable;
  }

  /**
   * 重置刷新的状态<BR>
   */
  public void resetPullStatus() {
    mIsPullUpRefreshing = false;
    mRefreshLayout.setRefreshing(false);
    mFooterRootView.setVisibility(View.GONE);
  }

  /**
   * 每页加载的数据<BR>
   *
   * @param count 每页加载的数据个数
   */
  public void setPageCount(int count) {
    mPageCount = count;
  }

  public void setAdapter(ListAdapter adapter) {
    mListView.setAdapter(adapter);
  }

  public ListAdapter getAdapter() {
    return mListView.getAdapter();
  }

  private void setListParam() {
    mFooterRootView.setVisibility(View.GONE);
    mListView.setOnScrollListener(new OnScrollListener() {

      @Override
      public void onScrollStateChanged(AbsListView view, int scrollState) {

      }

      @Override
      public void onScroll(AbsListView view, int firstVisibleItem,
                           int visibleItemCount, int totalItemCount) {
        int dataTotalItemCount = totalItemCount
            - mListView.getFooterViewsCount()
            - mListView.getHeaderViewsCount();
        if (dataTotalItemCount == 0 || dataTotalItemCount < mPageCount) {
          return;
        }
        // 如果第一个可见的位置+可见的item总数 = 总的ITEM数目 - 倒数第几个刷新数
        if ((totalItemCount - mNeedLoadPosDesc == (firstVisibleItem + visibleItemCount) ||
            (totalItemCount == firstVisibleItem
                + visibleItemCount))
            && mPullUpToRefreshable) {
          // 如果当前未处于刷新状态
          if (null != mScrollToLoadListener
              && !mRefreshLayout.isRefreshing()
              && !mIsPullUpRefreshing) {
            mIsPullUpRefreshing = true;
            mFooterRootView.setVisibility(View.VISIBLE);
            mScrollToLoadListener.onPullUpLoadData();
          }
        }
      }
    });
  }

  /**
   * 滑动加载数据的监听<BR>
   *
   * @author zhaozeyang
   * @version [Paitao Client V20130911, 2014-4-2]
   */
  public interface ScrollToLoadListener {
    /**
     * 上拉加载数据<BR>
     */
    void onPullUpLoadData();

    /**
     * 下拉加载数据<BR>
     */
    void onPullDownLoadData();
  }
}
