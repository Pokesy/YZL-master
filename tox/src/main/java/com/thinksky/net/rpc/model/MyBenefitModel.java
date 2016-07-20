/*
 * 文件名: MyBenefitModel
 * 版    权：  Copyright Hengrtech Tech. Co. Ltd. All Rights Reserved.
 * 描    述: [该类的简要描述]
 * 创建人: zhaozeyang
 * 创建时间:16/5/17
 * 
 * 修改人：
 * 修改时间:
 * 修改内容：[修改内容]
 */
package com.thinksky.net.rpc.model;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.List;

/**
 * 我的收益<BR>
 *
 * @author zhaozeyang
 * @version [Taobei Client V20160411, 16/5/17]
 */
public class MyBenefitModel implements Parcelable {

  /**
   * flag : null
   * percentage : 0
   * percentage2 : 0
   * taskAwards : 0.00
   * taskAwards2 : 0
   * today : 2016年05月17日
   * todayBenefit : 0.00
   * todayBenefit2 : 0
   * todayTotalAwards : 0
   * todayTotalAwards2 : 0
   * totalAwards : 0
   * totalAwards2 : 5
   * watchedAdvQuantity : 0
   * xData : ["11","12","13","14","15","16","17"]
   * yData : [0,0,0,0,0,0,0]
   * yData2 : [0,0,0,0,0,0,0]
   */

  private Object flag;
  private int percentage;
  private int percentage2;
  private String taskAwards;
  private String taskAwards2;
  private String today;
  private String todayBenefit;
  private String todayBenefit2;
  private int todayTotalAwards;
  private int todayTotalAwards2;
  private int totalAwards;
  private int totalAwards2;
  private int watchedAdvQuantity;
  private List<String> xData;
  private List<Integer> yData;
  private List<Integer> yData2;

  public Object getFlag() {
    return flag;
  }

  public void setFlag(Object flag) {
    this.flag = flag;
  }

  public int getPercentage() {
    return percentage;
  }

  public void setPercentage(int percentage) {
    this.percentage = percentage;
  }

  public int getPercentage2() {
    return percentage2;
  }

  public void setPercentage2(int percentage2) {
    this.percentage2 = percentage2;
  }

  public String getTaskAwards() {
    return taskAwards;
  }

  public void setTaskAwards(String taskAwards) {
    this.taskAwards = taskAwards;
  }

  public String getTaskAwards2() {
    return taskAwards2;
  }

  public void setTaskAwards2(String taskAwards2) {
    this.taskAwards2 = taskAwards2;
  }

  public String getToday() {
    return today;
  }

  public void setToday(String today) {
    this.today = today;
  }

  public String getTodayBenefit() {
    return todayBenefit;
  }

  public void setTodayBenefit(String todayBenefit) {
    this.todayBenefit = todayBenefit;
  }

  public String getTodayBenefit2() {
    return todayBenefit2;
  }

  public void setTodayBenefit2(String todayBenefit2) {
    this.todayBenefit2 = todayBenefit2;
  }

  public int getTodayTotalAwards() {
    return todayTotalAwards;
  }

  public void setTodayTotalAwards(int todayTotalAwards) {
    this.todayTotalAwards = todayTotalAwards;
  }

  public int getTodayTotalAwards2() {
    return todayTotalAwards2;
  }

  public void setTodayTotalAwards2(int todayTotalAwards2) {
    this.todayTotalAwards2 = todayTotalAwards2;
  }

  public int getTotalAwards() {
    return totalAwards;
  }

  public void setTotalAwards(int totalAwards) {
    this.totalAwards = totalAwards;
  }

  public int getTotalAwards2() {
    return totalAwards2;
  }

  public void setTotalAwards2(int totalAwards2) {
    this.totalAwards2 = totalAwards2;
  }

  public int getWatchedAdvQuantity() {
    return watchedAdvQuantity;
  }

  public void setWatchedAdvQuantity(int watchedAdvQuantity) {
    this.watchedAdvQuantity = watchedAdvQuantity;
  }

  public List<String> getXData() {
    return xData;
  }

  public void setXData(List<String> xData) {
    this.xData = xData;
  }

  public List<Integer> getYData() {
    return yData;
  }

  public void setYData(List<Integer> yData) {
    this.yData = yData;
  }

  public List<Integer> getYData2() {
    return yData2;
  }

  public void setYData2(List<Integer> yData2) {
    this.yData2 = yData2;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeInt(this.percentage);
    dest.writeInt(this.percentage2);
    dest.writeString(this.taskAwards);
    dest.writeString(this.taskAwards2);
    dest.writeString(this.today);
    dest.writeString(this.todayBenefit);
    dest.writeString(this.todayBenefit2);
    dest.writeInt(this.todayTotalAwards);
    dest.writeInt(this.todayTotalAwards2);
    dest.writeInt(this.totalAwards);
    dest.writeInt(this.totalAwards2);
    dest.writeInt(this.watchedAdvQuantity);
    dest.writeStringList(this.xData);
    dest.writeList(this.yData);
    dest.writeList(this.yData2);
  }

  public MyBenefitModel() {
  }

  protected MyBenefitModel(Parcel in) {
    this.flag = in.readParcelable(Object.class.getClassLoader());
    this.percentage = in.readInt();
    this.percentage2 = in.readInt();
    this.taskAwards = in.readString();
    this.taskAwards2 = in.readString();
    this.today = in.readString();
    this.todayBenefit = in.readString();
    this.todayBenefit2 = in.readString();
    this.todayTotalAwards = in.readInt();
    this.todayTotalAwards2 = in.readInt();
    this.totalAwards = in.readInt();
    this.totalAwards2 = in.readInt();
    this.watchedAdvQuantity = in.readInt();
    this.xData = in.createStringArrayList();
    this.yData = new ArrayList<Integer>();
    in.readList(this.yData, Integer.class.getClassLoader());
    this.yData2 = new ArrayList<Integer>();
    in.readList(this.yData2, Integer.class.getClassLoader());
  }

  public static final Creator<MyBenefitModel> CREATOR = new Creator<MyBenefitModel>() {
    @Override
    public MyBenefitModel createFromParcel(Parcel source) {
      return new MyBenefitModel(source);
    }

    @Override
    public MyBenefitModel[] newArray(int size) {
      return new MyBenefitModel[size];
    }
  };
}
