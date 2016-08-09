/*
 * 文件名: ResidenceChooseActivity
 * 版    权：  Copyright Hengrtech Tech. Co. Ltd. All Rights Reserved.
 * 描    述: [该类的简要描述]
 * 创建人: zhaozeyang
 * 创建时间:16/6/1
 * 
 * 修改人：
 * 修改时间:
 * 修改内容：[修改内容]
 */
package com.thinksky.ui.profile.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.aigestudio.wheelpicker.core.AbstractWheelPicker;
import com.aigestudio.wheelpicker.view.WheelStraightPicker;
import com.thinksky.tox.R;
import com.thinksky.utils.AddressUtils;
import java.util.ArrayList;
import java.util.List;

/**
 * [一句话功能简述]<BR>
 * [功能详细描述]
 *
 * @author zhaozeyang
 * @version [Taobei Client V20160411, 16/6/1]
 */
public class AddressChooseView extends FrameLayout {

  @Bind(R.id.province_picker)
  WheelStraightPicker mProvincePicker;
  @Bind(R.id.city_picker)
  WheelStraightPicker mCityPicker;

  private String mSelectedProvince;
  private String mSelectedCity;
  private List<AddressUtils.AddressModel> mAddressModelList;
  private List<List<String>> mCityList = new ArrayList<>();

  public AddressChooseView(Context context) {
    super(context);
    initView();
  }

  private void initView() {
    View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_address_choose, null);
    ButterKnife.bind(this, view);
    mAddressModelList = AddressUtils.getAddress(getContext());
    List<String> provinceList = new ArrayList<>();
    for (AddressUtils.AddressModel model : mAddressModelList) {
      provinceList.add(model.getName());
      List<AddressUtils.AddressModel.CityBean> cityBeanList = model.getCity();
      List<String> cityNameList = new ArrayList<>();
      for (AddressUtils.AddressModel.CityBean bean : cityBeanList) {
        cityNameList.add(bean.getName());
      }
      mCityList.add(cityNameList);
    }
    mProvincePicker.setData(provinceList);
    mProvincePicker.setOnWheelChangeListener(new AbstractWheelPicker.OnWheelChangeListener() {
      @Override
      public void onWheelScrolling(float deltaX, float deltaY) {

      }

      @Override
      public void onWheelSelected(int index, String data) {
        mSelectedProvince = data;
        swapCityList(index);
        mSelectedCity = mCityList.get(index).get(0);
      }

      @Override
      public void onWheelScrollStateChanged(int state) {

      }
    });
    mCityPicker.setOnWheelChangeListener(new AbstractWheelPicker.OnWheelChangeListener() {
      @Override
      public void onWheelScrolling(float deltaX, float deltaY) {

      }

      @Override
      public void onWheelSelected(int index, String data) {
        mSelectedCity = data;
      }

      @Override
      public void onWheelScrollStateChanged(int state) {

      }
    });
    addView(view, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup
        .LayoutParams.MATCH_PARENT));
  }

  private void swapCityList(int provinceIndex) {
    mCityPicker.setData(mCityList.get(provinceIndex));
  }

  public String getSelectAddress() {
    return mSelectedProvince + mSelectedCity;
  }

  public String getSelectCity() {
    return mSelectedCity;
  }

  public String getSelectProvince() {
    return mSelectedProvince;
  }
}
