package com.thinksky.net.rpc.model;

/**
 * Created by jiao on 2016/6/2.
 */
public class ThirdLogin {
  /**
   * result : 1
   * uid : 1
   * devId : null
   * userName : null
   * password : 123456
   * mobileNo : 18266450911
   * registerDate : null
   * avart : null
   * state : 1
   * money : 0
   * virtualMoney : 0
   */

  private String result;
  private int uid;
  private Object devId;
  private Object userName;
  private String password;
  private String mobileNo;
  private Object registerDate;
  private Object avart;
  private String state;
  private int money;
  private int virtualMoney;

  public String getResult() {
    return result;
  }

  public void setResult(String result) {
    this.result = result;
  }

  public int getUid() {
    return uid;
  }

  public void setUid(int uid) {
    this.uid = uid;
  }

  public Object getDevId() {
    return devId;
  }

  public void setDevId(Object devId) {
    this.devId = devId;
  }

  public Object getUserName() {
    return userName;
  }

  public void setUserName(Object userName) {
    this.userName = userName;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getMobileNo() {
    return mobileNo;
  }

  public void setMobileNo(String mobileNo) {
    this.mobileNo = mobileNo;
  }

  public Object getRegisterDate() {
    return registerDate;
  }

  public void setRegisterDate(Object registerDate) {
    this.registerDate = registerDate;
  }

  public Object getAvart() {
    return avart;
  }

  public void setAvart(Object avart) {
    this.avart = avart;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public int getMoney() {
    return money;
  }

  public void setMoney(int money) {
    this.money = money;
  }

  public int getVirtualMoney() {
    return virtualMoney;
  }

  public void setVirtualMoney(int virtualMoney) {
    this.virtualMoney = virtualMoney;
  }
/*
* {
    "result":"1",
    "uid":1,
    "devId":null,
    "userName":null,
    "password":"123456",
    "mobileNo":"18266450911",
    "registerDate":null,
    "avart":null,
    "state":"1",
    "money":0,
    "virtualMoney":0
}

* */

}
