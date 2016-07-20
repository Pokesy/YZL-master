/*
 * 文件名: CouponModel
 * 版    权：  Copyright Hengrtech Tech. Co. Ltd. All Rights Reserved.
 * 描    述: [该类的简要描述]
 * 创建人: zhaozeyang
 * 创建时间:16/5/31
 * 
 * 修改人：
 * 修改时间:
 * 修改内容：[修改内容]
 */
package com.thinksky.net.rpc.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * [一句话功能简述]<BR>
 * [功能详细描述]
 *
 * @author zhaozeyang
 * @version [Taobei Client V20160411, 16/5/31]
 */
public class CouponModel implements Parcelable{
  public static final String EXPIRED_NORMAL = "0";
  public static final String EXPIRED_OVERDUE = "1";
  public static final String TYPE_COUPON = "0";
  public static final String TYPE_DISCOUNT = "1";

  /**
   * couponCode : AFDE12
   * couponDesc : 10元
   * couponId : 1
   * couponName : 现金抵用券
   * endTime : 2016-06-30 00:00:00.0
   * img :
   * sellerAddress : 烟台高新区
   * sellerContact : 18266450911
   * sellerName : 商家名1
   * startTime : 2016-05-20 00:00:00.0
   * type : 1
   * usageDesc : 满50立减10
   * used : 0
   */

  private String couponCode;
  private String couponDesc;
  private int couponId;
  private String couponName;
  private long endTime;
  private String img;
  private String sellerAddress;
  private String sellerContact;
  private String sellerName;
  private long startTime;
  private String type;
  private String usageDesc;
  private String used;
  private String expired;

  public String getCouponCode() {
    return couponCode;
  }

  public void setCouponCode(String couponCode) {
    this.couponCode = couponCode;
  }

  public String getCouponDesc() {
    return couponDesc;
  }

  public void setCouponDesc(String couponDesc) {
    this.couponDesc = couponDesc;
  }

  public int getCouponId() {
    return couponId;
  }

  public void setCouponId(int couponId) {
    this.couponId = couponId;
  }

  public String getCouponName() {
    return couponName;
  }

  public void setCouponName(String couponName) {
    this.couponName = couponName;
  }

  public long getEndTime() {
    return this.endTime;
  }

  public void setEndTime(long endTime) {
    this.endTime = endTime;
  }

  public String getImg() {
    return img;
  }

  public void setImg(String img) {
    this.img = img;
  }

  public String getSellerAddress() {
    return sellerAddress;
  }

  public void setSellerAddress(String sellerAddress) {
    this.sellerAddress = sellerAddress;
  }

  public String getSellerContact() {
    return sellerContact;
  }

  public void setSellerContact(String sellerContact) {
    this.sellerContact = sellerContact;
  }

  public String getSellerName() {
    return sellerName;
  }

  public void setSellerName(String sellerName) {
    this.sellerName = sellerName;
  }

  public long getStartTime() {
    return startTime;
  }

  public void setStartTime(long startTime) {
    this.startTime = startTime;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getUsageDesc() {
    return usageDesc;
  }

  public void setUsageDesc(String usageDesc) {
    this.usageDesc = usageDesc;
  }

  public String getUsed() {
    return used;
  }

  public void setUsed(String used) {
    this.used = used;
  }

  public String getExpired() {
    return expired;
  }

  public void setExpired(String expired) {
    this.expired = expired;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(this.couponCode);
    dest.writeString(this.couponDesc);
    dest.writeInt(this.couponId);
    dest.writeString(this.couponName);
    dest.writeLong(this.endTime);
    dest.writeString(this.img);
    dest.writeString(this.sellerAddress);
    dest.writeString(this.sellerContact);
    dest.writeString(this.sellerName);
    dest.writeLong(this.startTime);
    dest.writeString(this.type);
    dest.writeString(this.usageDesc);
    dest.writeString(this.used);
    dest.writeString(this.expired);
  }

  public CouponModel() {
  }

  protected CouponModel(Parcel in) {
    this.couponCode = in.readString();
    this.couponDesc = in.readString();
    this.couponId = in.readInt();
    this.couponName = in.readString();
    this.endTime = in.readLong();
    this.img = in.readString();
    this.sellerAddress = in.readString();
    this.sellerContact = in.readString();
    this.sellerName = in.readString();
    this.startTime = in.readLong();
    this.type = in.readString();
    this.usageDesc = in.readString();
    this.used = in.readString();
    this.expired = in.readString();
  }

  public static final Creator<CouponModel> CREATOR = new Creator<CouponModel>() {
    @Override
    public CouponModel createFromParcel(Parcel source) {
      return new CouponModel(source);
    }

    @Override
    public CouponModel[] newArray(int size) {
      return new CouponModel[size];
    }
  };
}
