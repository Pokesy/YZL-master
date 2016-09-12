package com.thinksky.fragment;

/**
 * Created by jiao on 2016/3/21.
 */

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.ScrollView;
import com.thinksky.utils.LogUtils;

/*屏蔽 滑动事件
        *Created by fc on 2015/7/16.
        */
public class MyScrollview extends ScrollView {
  private int downX;
  private int downY;
  private int mTouchSlop;
  private OnScrollListener onScrollListener;

  public MyScrollview(Context context) {
    super(context);
    mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
  }

  public MyScrollview(Context context, AttributeSet attrs) {
    super(context, attrs);
    mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
  }

  public MyScrollview(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
  }

  /**
   * 设置滚动接口
   */
  public void setOnScrollListener(OnScrollListener onScrollListener) {
    this.onScrollListener = onScrollListener;
  }

  @Override protected void onScrollChanged(int l, int t, int oldl, int oldt) {
    super.onScrollChanged(l, t, oldl, oldt);
    if (onScrollListener != null) {
      onScrollListener.onScroll(t);
    }
  }

  /**
   * 滚动的回调接口
   *
   * @author xiaanming
   */
  public interface OnScrollListener {
    /**
     * 回调方法， 返回MyScrollView滑动的Y方向距离
     *
     * @param scrollY 、
     */
    void onScroll(int scrollY);
  }

  @Override public boolean onInterceptTouchEvent(MotionEvent e) {
    int action = e.getAction();
    switch (action) {
      case MotionEvent.ACTION_DOWN:
        downX = (int) e.getRawX();
        downY = (int) e.getRawY();
        break;
      case MotionEvent.ACTION_MOVE:
        int moveY = (int) e.getRawY();
        if (Math.abs(moveY - downY) > mTouchSlop) {
          return true;
        }
    }
    return super.onInterceptTouchEvent(e);
  }
}

