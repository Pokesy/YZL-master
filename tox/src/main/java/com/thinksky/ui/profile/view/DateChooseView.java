/*
 * 文件名: DateChooseView
 * 版    权：  Copyright Hengrtech Tech. Co. Ltd. All Rights Reserved.
 * 描    述: [该类的简要描述]
 * 创建人: zhaozeyang
 * 创建时间:16/8/16
 * 
 * 修改人：
 * 修改时间:
 * 修改内容：[修改内容]
 */
package com.thinksky.ui.profile.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.aigestudio.wheelpicker.core.AbstractWheelPicker;
import com.aigestudio.wheelpicker.widget.curved.WheelDatePicker;
import com.thinksky.tox.R;
import java.util.Calendar;

/**
 * [一句话功能简述]<BR>
 * [功能详细描述]
 *
 * @author zhaozeyang
 * @version [Taobei Client V20160411, 16/8/16]
 */
public class DateChooseView extends FrameLayout {
  @Bind(R.id.date_picker)
  WheelDatePicker datePicker;

  private String mDateChoose;

  public DateChooseView(Context context) {
    super(context);
    initView();
  }

  public DateChooseView(Context context, AttributeSet attrs) {
    super(context, attrs);
    initView();
  }

  private void initView() {
    View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_date_choose, this, false);
    ButterKnife.bind(this, view);

    Calendar calendar = Calendar.getInstance();
    calendar.setTimeInMillis(System.currentTimeMillis());
    datePicker.setCurrentDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH));
    datePicker.setOnWheelChangeListener(new AbstractWheelPicker.OnWheelChangeListener() {
      @Override
      public void onWheelScrolling(float deltaX, float deltaY) {

      }

      @Override
      public void onWheelSelected(int index, String data) {
        mDateChoose = data;
      }

      @Override
      public void onWheelScrollStateChanged(int state) {

      }
    });
    addView(view);
  }

  public String getDate() {
    return mDateChoose;
  }
}
