/*
 * 文件名: CustomViewPager
 * 版    权：  Copyright Hengrtech Tech. Co. Ltd. All Rights Reserved.
 * 描    述: [该类的简要描述]
 * 创建人: zhaozeyang
 * 创建时间:16/7/24
 * 
 * 修改人：
 * 修改时间:
 * 修改内容：[修改内容]
 */
package com.thinksky.myview;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * [一句话功能简述]<BR>
 * [功能详细描述]
 *
 * @author zhaozeyang
 * @version [Taobei Client V20160411, 16/7/24]
 */
public class CustomViewPager extends ViewPager {
  private boolean isPagingEnabled = true;

  public CustomViewPager(Context context) {
    super(context);
  }

  public CustomViewPager(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override
  public boolean onTouchEvent(MotionEvent event) {
    return this.isPagingEnabled && super.onTouchEvent(event);
  }

  @Override
  public boolean onInterceptTouchEvent(MotionEvent event) {
    return this.isPagingEnabled && super.onInterceptTouchEvent(event);
  }

  public void setPagingEnabled(boolean b) {
    this.isPagingEnabled = b;
  }
}
