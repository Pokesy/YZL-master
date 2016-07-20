package com.thinksky.net.rpc.model;

/**
 * Created by jiao on 2016/6/13.
 */
public class GetMoneyModel {

  /**
   * advId : 2
   * advState : 1
   * benefitType : 2
   * coupon : {"couponCode":"AFDE12","couponDesc":"10元","couponId":1,"couponName":"现金抵用券","endTime":1467216000000,"expired":"","img":"/uploads/adv/adv1.png","sellerAddress":"烟台高新区","sellerContact":"18266450911","sellerId":0,"sellerName":"商家名1","startTime":1463673600000,"type":"1","usageDesc":"满50立减10","used":""}
   * state : 3
   */

  private int advId;
  private String advState;
  private String benefitType;
  /**
   * couponCode : AFDE12
   * couponDesc : 10元
   * couponId : 1
   * couponName : 现金抵用券
   * endTime : 1467216000000
   * expired :
   * img : /uploads/adv/adv1.png
   * sellerAddress : 烟台高新区
   * sellerContact : 18266450911
   * sellerId : 0
   * sellerName : 商家名1
   * startTime : 1463673600000
   * type : 1
   * usageDesc : 满50立减10
   * used :
   */

  private CouponEntity coupon;
  private int benefit;
  private int state;

  public int getAdvId() {
    return advId;
  }

  public void setAdvId(int advId) {
    this.advId = advId;
  }

  public String getAdvState() {
    return advState;
  }

  public void setAdvState(String advState) {
    this.advState = advState;
  }

  public String getBenefitType() {
    return benefitType;
  }

  public void setBenefitType(String benefitType) {
    this.benefitType = benefitType;
  }

  public CouponEntity getCoupon() {
    return coupon;
  }

  public void setCoupon(CouponEntity coupon) {
    this.coupon = coupon;
  }

  public int getState() {
    return state;
  }

  public void setState(int state) {
    this.state = state;
  }

  public int getBenefit() {
    return benefit;
  }

  public void setBenefit(int benefit) {
    this.benefit = benefit;
  }

  public static class CouponEntity {
    private String couponCode;
    private String couponDesc;
    private int couponId;
    private String couponName;
    private long endTime;
    private String expired;
    private String img;
    private String sellerAddress;
    private String sellerContact;
    private int sellerId;
    private String sellerName;
    private long startTime;
    private String type;
    private String usageDesc;
    private String used;

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
      return endTime;
    }

    public void setEndTime(long endTime) {
      this.endTime = endTime;
    }

    public String getExpired() {
      return expired;
    }

    public void setExpired(String expired) {
      this.expired = expired;
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

    public int getSellerId() {
      return sellerId;
    }

    public void setSellerId(int sellerId) {
      this.sellerId = sellerId;
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
  }
}
