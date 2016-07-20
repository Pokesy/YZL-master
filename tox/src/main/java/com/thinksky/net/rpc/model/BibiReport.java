/*
 * 文件名: BibiReport
 * 版    权：  Copyright Hengrtech Tech. Co. Ltd. All Rights Reserved.
 * 描    述: [该类的简要描述]
 * 创建人: zhaozeyang
 * 创建时间:16/5/11
 * 
 * 修改人：
 * 修改时间:
 * 修改内容：[修改内容]
 */
package com.thinksky.net.rpc.model;

import java.util.List;

/**
 * 贝贝报告<BR>
 *
 * @author zhaozeyang
 * @version [Taobei Client V20160411, 16/5/11]
 */
public class BibiReport {

  /**
   * advQuantity : 5
   * expectedBenefit : 15
   * friendsRanking : [{"avart":"","birthPlace":"","birthday":"","certified":"","devId":"",
   * "education":"","gender":"","idCardImg":"","introduce":"","inviteCode":"zopz4t","loginNum":5,
   * "loginTime":1461896393000,"mobileNo":"18266450911","money":33,"msgCounts":0,"myCity":"",
   * "occupation":"","password":"508624861b0a4bc154387a9ec6231125","payPwd":"",
   * "registerDate":1456761600000,"result":"","securityLevel":"m","state":"1","thirdAccount":"",
   * "todayBenefit":0,"type":"0","userId":1,"userLabel":"","userName":"18266450911",
   * "virtualMoney":0},{"avart":"","birthPlace":"","birthday":"","certified":"","devId":"",
   * "education":"","gender":"","idCardImg":"","introduce":"","inviteCode":"zopz41","loginNum":4,
   * "loginTime":1462927228000,"mobileNo":"18253553110","money":0,"msgCounts":0,"myCity":"",
   * "occupation":"","password":"0c8f9f819b45f36a4be6f7c175cd8cf8","payPwd":"",
   * "registerDate":1456761600000,"result":"","securityLevel":"m","state":"1","thirdAccount":"",
   * "todayBenefit":0,"type":"0","userId":2,"userLabel":"","userName":"18253553110",
   * "virtualMoney":0},{"avart":"","birthPlace":"","birthday":"","certified":"","devId":"",
   * "education":"","gender":"","idCardImg":"","introduce":"","inviteCode":"zopz42","loginNum":0,
   * "loginTime":null,"mobileNo":"18253333333","money":0,"msgCounts":0,"myCity":"",
   * "occupation":"","password":"0c8f9f819b45f36a4be6f7c175cd8cf8","payPwd":"",
   * "registerDate":1456761600000,"result":"","securityLevel":"m","state":"1","thirdAccount":"",
   * "todayBenefit":0,"type":"0","userId":3,"userLabel":"","userName":"18253333333",
   * "virtualMoney":0},{"avart":"","birthPlace":"","birthday":"2000-04-29","certified":"",
   * "devId":"","education":"","gender":"男","idCardImg":"","introduce":"","inviteCode":"zopz4a",
   * "loginNum":18,"loginTime":1462927831000,"mobileNo":"18660562761","money":139,"msgCounts":0,
   * "myCity":"安顺","occupation":"","password":"508624861b0a4bc154387a9ec6231125",
   * "payPwd":"cd0db277acfe3c5fbb9ed11a119ffff6","registerDate":1456761600000,"result":"",
   * "securityLevel":"m","state":"1","thirdAccount":"","todayBenefit":0,"type":"0","userId":4,
   * "userLabel":"","userName":"Tianqi","virtualMoney":490},{"avart":"","birthPlace":"",
   * "birthday":"","certified":"","devId":"","education":"","gender":"","idCardImg":"",
   * "introduce":"","inviteCode":"zopz4b","loginNum":0,"loginTime":null,"mobileNo":"18660562762",
   * "money":0,"msgCounts":0,"myCity":"","occupation":"",
   * "password":"0c8f9f819b45f36a4be6f7c175cd8cf8","payPwd":"","registerDate":1456761600000,
   * "result":"","securityLevel":"m","state":"1","thirdAccount":"","todayBenefit":0,"type":"0",
   * "userId":5,"userLabel":"","userName":"18660562762","virtualMoney":0},{"avart":"",
   * "birthPlace":"","birthday":"","certified":"","devId":"","education":"","gender":"",
   * "idCardImg":"","introduce":"","inviteCode":"zopz4c","loginNum":0,"loginTime":null,
   * "mobileNo":"18253553111","money":0,"msgCounts":0,"myCity":"","occupation":"",
   * "password":"0d119f05fd59682b7e42000f3ee0053e","payPwd":"","registerDate":1456761600000,
   * "result":"","securityLevel":"m","state":"1","thirdAccount":"","todayBenefit":0,"type":"0",
   * "userId":6,"userLabel":"","userName":"18253553111","virtualMoney":0},{"avart":"",
   * "birthPlace":"","birthday":"","certified":"","devId":"","education":"","gender":"",
   * "idCardImg":"","introduce":"","inviteCode":"zopz4d","loginNum":0,"loginTime":null,
   * "mobileNo":"15612345678","money":0,"msgCounts":0,"myCity":"","occupation":"",
   * "password":"bb0367d99394178b77eafaf67965cffb","payPwd":"","registerDate":1456761600000,
   * "result":"","securityLevel":"m","state":"1","thirdAccount":"","todayBenefit":0,"type":"0",
   * "userId":7,"userLabel":"","userName":"15612345678","virtualMoney":0},{"avart":"",
   * "birthPlace":"","birthday":"","certified":"","devId":"","education":"","gender":"",
   * "idCardImg":"","introduce":"","inviteCode":"zopz4e","loginNum":0,"loginTime":null,
   * "mobileNo":"18253553320","money":0,"msgCounts":0,"myCity":"","occupation":"",
   * "password":"0c8f9f819b45f36a4be6f7c175cd8cf8","payPwd":"","registerDate":1456761600000,
   * "result":"","securityLevel":"m","state":"1","thirdAccount":"","todayBenefit":0,"type":"0",
   * "userId":33,"userLabel":"","userName":"18253553320","virtualMoney":0},{"avart":"",
   * "birthPlace":"","birthday":"","certified":"","devId":"","education":"","gender":"",
   * "idCardImg":"","introduce":"","inviteCode":"zopz4f","loginNum":0,"loginTime":null,
   * "mobileNo":"18660562758","money":0,"msgCounts":0,"myCity":"","occupation":"",
   * "password":"0c8f9f819b45f36a4be6f7c175cd8cf8","payPwd":"","registerDate":1456761600000,
   * "result":"","securityLevel":"m","state":"1","thirdAccount":"","todayBenefit":0,"type":"0",
   * "userId":36,"userLabel":"","userName":"18660562758","virtualMoney":0},{"avart":"",
   * "birthPlace":"","birthday":"","certified":"","devId":"C660CF44-74BD-4B08-A996-E212995B38C2",
   * "education":"","gender":"","idCardImg":"","introduce":"","inviteCode":"zopz4g","loginNum":0,
   * "loginTime":null,"mobileNo":"18660562764","money":0,"msgCounts":0,"myCity":"",
   * "occupation":"","password":"0c8f9f819b45f36a4be6f7c175cd8cf8","payPwd":"",
   * "registerDate":1456761600000,"result":"","securityLevel":"m","state":"1","thirdAccount":"",
   * "todayBenefit":0,"type":"0","userId":40,"userLabel":"","userName":"18660562764",
   * "virtualMoney":0}]
   * honorAchievement : 专家,工程师
   * playDuration : 1326
   * qList : [{"answer":"","category":"","createDate":"2016-04-15 14:39:48","creator":"platform",
   * "flag":"1","myAnswer":"","name":"你平常一般几点进入梦乡？","qId":"sys1","type":"4"},{"answer":"",
   * "category":"","createDate":"2016-04-15 14:39:48","creator":"platform","flag":"1",
   * "myAnswer":"","name":"你一般在哪个平台网购？","qId":"sys10","type":"4"},{"answer":"","category":"",
   * "createDate":"2016-04-15 14:39:49","creator":"platform","flag":"1","myAnswer":"",
   * "name":"你认为网络广告未来的趋势在哪？","qId":"sys100","type":"4"},{"answer":"","category":"",
   * "createDate":"2016-04-15 14:39:49","creator":"platform","flag":"1","myAnswer":"",
   * "name":"您认为当今公益广告的不足有哪些？","qId":"sys101","type":"4"}]
   * totalBenefit : 0
   */

  private int advQuantity;
  private int expectedBenefit;
  private String[] honorAchievement;
  private int playDuration;
  private int totalBenefit;
  /**
   * avart :
   * birthPlace :
   * birthday :
   * certified :
   * devId :
   * education :
   * gender :
   * idCardImg :
   * introduce :
   * inviteCode : zopz4t
   * loginNum : 5
   * loginTime : 1461896393000
   * mobileNo : 18266450911
   * money : 33
   * msgCounts : 0
   * myCity :
   * occupation :
   * password : 508624861b0a4bc154387a9ec6231125
   * payPwd :
   * registerDate : 1456761600000
   * result :
   * securityLevel : m
   * state : 1
   * thirdAccount :
   * todayBenefit : 0
   * type : 0
   * userId : 1
   * userLabel :
   * userName : 18266450911
   * virtualMoney : 0
   */

  private List<FriendsRankingBean> friendsRanking;
  /**
   * answer :
   * category :
   * createDate : 2016-04-15 14:39:48
   * creator : platform
   * flag : 1
   * myAnswer :
   * name : 你平常一般几点进入梦乡？
   * qId : sys1
   * type : 4
   */

  private List<QListBean> qList;

  public int getAdvQuantity() {
    return advQuantity;
  }

  public void setAdvQuantity(int advQuantity) {
    this.advQuantity = advQuantity;
  }

  public int getExpectedBenefit() {
    return expectedBenefit;
  }

  public void setExpectedBenefit(int expectedBenefit) {
    this.expectedBenefit = expectedBenefit;
  }

  public String[] getHonorAchievement() {
    return honorAchievement;
  }

  public void setHonorAchievement(String[] honorAchievement) {
    this.honorAchievement = honorAchievement;
  }

  public int getPlayDuration() {
    return playDuration;
  }

  public void setPlayDuration(int playDuration) {
    this.playDuration = playDuration;
  }

  public int getTotalBenefit() {
    return totalBenefit;
  }

  public void setTotalBenefit(int totalBenefit) {
    this.totalBenefit = totalBenefit;
  }

  public List<FriendsRankingBean> getFriendsRanking() {
    return friendsRanking;
  }

  public void setFriendsRanking(List<FriendsRankingBean> friendsRanking) {
    this.friendsRanking = friendsRanking;
  }

  public List<QListBean> getQList() {
    return qList;
  }

  public void setQList(List<QListBean> qList) {
    this.qList = qList;
  }

  public static class FriendsRankingBean {
    private String avart;
    private String birthPlace;
    private String birthday;
    private String certified;
    private String devId;
    private String education;
    private String gender;
    private String idCardImg;
    private String introduce;
    private String inviteCode;
    private int loginNum;
    private long loginTime;
    private String mobileNo;
    private int money;
    private int msgCounts;
    private String myCity;
    private String occupation;
    private String password;
    private String payPwd;
    private long registerDate;
    private String result;
    private String securityLevel;
    private String state;
    private String thirdAccount;
    private int todayBenefit;
    private String type;
    private int userId;
    private String userLabel;
    private String userName;
    private int virtualMoney;

    public String getAvart() {
      return avart;
    }

    public void setAvart(String avart) {
      this.avart = avart;
    }

    public String getBirthPlace() {
      return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
      this.birthPlace = birthPlace;
    }

    public String getBirthday() {
      return birthday;
    }

    public void setBirthday(String birthday) {
      this.birthday = birthday;
    }

    public String getCertified() {
      return certified;
    }

    public void setCertified(String certified) {
      this.certified = certified;
    }

    public String getDevId() {
      return devId;
    }

    public void setDevId(String devId) {
      this.devId = devId;
    }

    public String getEducation() {
      return education;
    }

    public void setEducation(String education) {
      this.education = education;
    }

    public String getGender() {
      return gender;
    }

    public void setGender(String gender) {
      this.gender = gender;
    }

    public String getIdCardImg() {
      return idCardImg;
    }

    public void setIdCardImg(String idCardImg) {
      this.idCardImg = idCardImg;
    }

    public String getIntroduce() {
      return introduce;
    }

    public void setIntroduce(String introduce) {
      this.introduce = introduce;
    }

    public String getInviteCode() {
      return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
      this.inviteCode = inviteCode;
    }

    public int getLoginNum() {
      return loginNum;
    }

    public void setLoginNum(int loginNum) {
      this.loginNum = loginNum;
    }

    public long getLoginTime() {
      return loginTime;
    }

    public void setLoginTime(long loginTime) {
      this.loginTime = loginTime;
    }

    public String getMobileNo() {
      return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
      this.mobileNo = mobileNo;
    }

    public int getMoney() {
      return money;
    }

    public void setMoney(int money) {
      this.money = money;
    }

    public int getMsgCounts() {
      return msgCounts;
    }

    public void setMsgCounts(int msgCounts) {
      this.msgCounts = msgCounts;
    }

    public String getMyCity() {
      return myCity;
    }

    public void setMyCity(String myCity) {
      this.myCity = myCity;
    }

    public String getOccupation() {
      return occupation;
    }

    public void setOccupation(String occupation) {
      this.occupation = occupation;
    }

    public String getPassword() {
      return password;
    }

    public void setPassword(String password) {
      this.password = password;
    }

    public String getPayPwd() {
      return payPwd;
    }

    public void setPayPwd(String payPwd) {
      this.payPwd = payPwd;
    }

    public long getRegisterDate() {
      return registerDate;
    }

    public void setRegisterDate(long registerDate) {
      this.registerDate = registerDate;
    }

    public String getResult() {
      return result;
    }

    public void setResult(String result) {
      this.result = result;
    }

    public String getSecurityLevel() {
      return securityLevel;
    }

    public void setSecurityLevel(String securityLevel) {
      this.securityLevel = securityLevel;
    }

    public String getState() {
      return state;
    }

    public void setState(String state) {
      this.state = state;
    }

    public String getThirdAccount() {
      return thirdAccount;
    }

    public void setThirdAccount(String thirdAccount) {
      this.thirdAccount = thirdAccount;
    }

    public int getTodayBenefit() {
      return todayBenefit;
    }

    public void setTodayBenefit(int todayBenefit) {
      this.todayBenefit = todayBenefit;
    }

    public String getType() {
      return type;
    }

    public void setType(String type) {
      this.type = type;
    }

    public int getUserId() {
      return userId;
    }

    public void setUserId(int userId) {
      this.userId = userId;
    }

    public String getUserLabel() {
      return userLabel;
    }

    public void setUserLabel(String userLabel) {
      this.userLabel = userLabel;
    }

    public String getUserName() {
      return userName;
    }

    public void setUserName(String userName) {
      this.userName = userName;
    }

    public int getVirtualMoney() {
      return virtualMoney;
    }

    public void setVirtualMoney(int virtualMoney) {
      this.virtualMoney = virtualMoney;
    }
  }

  public static class QListBean {
    private String answer;
    private String category;
    private String createDate;
    private String creator;
    private String flag;
    private String myAnswer;
    private String name;
    private String qId;
    private String type;

    public String getAnswer() {
      return answer;
    }

    public void setAnswer(String answer) {
      this.answer = answer;
    }

    public String getCategory() {
      return category;
    }

    public void setCategory(String category) {
      this.category = category;
    }

    public String getCreateDate() {
      return createDate;
    }

    public void setCreateDate(String createDate) {
      this.createDate = createDate;
    }

    public String getCreator() {
      return creator;
    }

    public void setCreator(String creator) {
      this.creator = creator;
    }

    public String getFlag() {
      return flag;
    }

    public void setFlag(String flag) {
      this.flag = flag;
    }

    public String getMyAnswer() {
      return myAnswer;
    }

    public void setMyAnswer(String myAnswer) {
      this.myAnswer = myAnswer;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getQId() {
      return qId;
    }

    public void setQId(String qId) {
      this.qId = qId;
    }

    public String getType() {
      return type;
    }

    public void setType(String type) {
      this.type = type;
    }
  }
}
