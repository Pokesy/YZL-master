/*
 * 文件名: AdvertisementDetail
 * 版    权：  Copyright Hengrtech Tech. Co. Ltd. All Rights Reserved.
 * 描    述: [该类的简要描述]
 * 创建人: zhaozeyang
 * 创建时间:16/4/25
 * 
 * 修改人：
 * 修改时间:
 * 修改内容：[修改内容]
 */
package com.thinksky.net.rpc.model;

import java.util.List;

/**
 * 广告详情<BR>
 *
 * @author zhaozeyang
 * @version [Taobei Client V20160411, 16/4/25]
 */
public class AdvertisementDetail extends BaseModel {

  /**
   * activities : 春节购买可享受八折优惠春节购买可享受八折优惠春节购买可享受八折优惠春节购买可享受八折优惠春节购买可享受八折优惠春节购买可享受八折优惠
   * advId : 1
   * advType : 1
   * area : 全国
   * bbjPrice :
   * benefitType : 0
   * categoryId : 1
   * collected : false
   * collectionNum : 0
   * collectors :
   * commentsHotList : [{"advId":1,"advName":"","avart":"","clickHearts":false,
   * "commentId":"8e83ac0a1767417a84c77e206cf767b3","commentTime":1461398383000,
   * "contents":"Klqejkljmwkl","hearts":1,"heartsUser":"74,","thumbnail":"","userId":74,
   * "userName":"游客"},{"advId":1,"advName":"","avart":"","clickHearts":false,
   * "commentId":"c2144d8e24be4f2bac600f8901799d1b","commentTime":1461564737000,
   * "contents":"Qjkljwkelas","hearts":1,"heartsUser":"4,","thumbnail":"","userId":4,
   * "userName":"Tianqi"},{"advId":1,"advName":"","avart":"","clickHearts":false,
   * "commentId":"c6ec6fb239964732a453f08a46853663","commentTime":1461564734000,
   * "contents":"Qjekwljklq","hearts":1,"heartsUser":"4,","thumbnail":"","userId":4,
   * "userName":"Tianqi"},{"advId":1,"advName":"","avart":"http://wx.qlogo
   * .cn/mmopen
   * /kWNw759VkMXgQf9AwrcPWmfkIicTggsFUDqWOPTVGGZU2lLxjwyhCjOb2AqPGwMSeYw3nTrDE9sVAr3tDnglPrBaJgNsQzCdc/0","clickHearts":false,"commentId":"2bd79a744c144a36be617deb64509070","commentTime":1460947831000,"contents":"h\u2006，\u2006l","hearts":0,"heartsUser":"","thumbnail":"","userId":58,"userName":"零点"},{"advId":1,"advName":"","avart":"","clickHearts":false,"commentId":"4f6650b7a6b44a84b2bd0aafe1d78a69","commentTime":1461566992000,"contents":"Dsaq","hearts":0,"heartsUser":"","thumbnail":"","userId":4,"userName":"Tianqi"},{"advId":1,"advName":"","avart":"","clickHearts":false,"commentId":"bd4f0758748a49d8b330a3b5f96e265a","commentTime":1461564726000,"contents":"Jeqwjlq","hearts":0,"heartsUser":"","thumbnail":"","userId":4,"userName":"Tianqi"},{"advId":1,"advName":"","avart":"","clickHearts":false,"commentId":"d3e097dea0594d59bb7995ca23c9aeb9","commentTime":1461548635000,"contents":"Jjjq","hearts":0,"heartsUser":"","thumbnail":"","userId":74,"userName":"游客"},{"advId":1,"advName":"","avart":"","clickHearts":false,"commentId":"e37ffb7622904f5b9b8b9b830a39554f","commentTime":1461566980000,"contents":"D","hearts":0,"heartsUser":"","thumbnail":"","userId":4,"userName":"Tianqi"},{"advId":1,"advName":"","avart":"","clickHearts":false,"commentId":"e6eff33ec17442f5891b3fe9472e9eac","commentTime":1461564730000,"contents":"Qjewkljklwq","hearts":0,"heartsUser":"","thumbnail":"","userId":4,"userName":"Tianqi"},{"advId":1,"advName":"","avart":"","clickHearts":false,"commentId":"f2bed6dec926408c98910e986edb6fe0","commentTime":1461548675000,"contents":"Qewqw","hearts":0,"heartsUser":"","thumbnail":"","userId":74,"userName":"游客"}]
   * commentsTimeList : [{"advId":1,"advName":"","avart":"","clickHearts":false,
   * "commentId":"4f6650b7a6b44a84b2bd0aafe1d78a69","commentTime":1461566992000,
   * "contents":"Dsaq","hearts":0,"heartsUser":"","thumbnail":"","userId":4,"userName":"Tianqi"},
   * {"advId":1,"advName":"","avart":"","clickHearts":false,
   * "commentId":"e37ffb7622904f5b9b8b9b830a39554f","commentTime":1461566980000,"contents":"D",
   * "hearts":0,"heartsUser":"","thumbnail":"","userId":4,"userName":"Tianqi"},{"advId":1,
   * "advName":"","avart":"","clickHearts":false,"commentId":"c2144d8e24be4f2bac600f8901799d1b",
   * "commentTime":1461564737000,"contents":"Qjkljwkelas","hearts":1,"heartsUser":"4,",
   * "thumbnail":"","userId":4,"userName":"Tianqi"},{"advId":1,"advName":"","avart":"",
   * "clickHearts":false,"commentId":"c6ec6fb239964732a453f08a46853663",
   * "commentTime":1461564734000,"contents":"Qjekwljklq","hearts":1,"heartsUser":"4,",
   * "thumbnail":"","userId":4,"userName":"Tianqi"},{"advId":1,"advName":"","avart":"",
   * "clickHearts":false,"commentId":"e6eff33ec17442f5891b3fe9472e9eac",
   * "commentTime":1461564730000,"contents":"Qjewkljklwq","hearts":0,"heartsUser":"",
   * "thumbnail":"","userId":4,"userName":"Tianqi"},{"advId":1,"advName":"","avart":"",
   * "clickHearts":false,"commentId":"bd4f0758748a49d8b330a3b5f96e265a",
   * "commentTime":1461564726000,"contents":"Jeqwjlq","hearts":0,"heartsUser":"","thumbnail":"",
   * "userId":4,"userName":"Tianqi"},{"advId":1,"advName":"","avart":"","clickHearts":false,
   * "commentId":"f2bed6dec926408c98910e986edb6fe0","commentTime":1461548675000,
   * "contents":"Qewqw","hearts":0,"heartsUser":"","thumbnail":"","userId":74,"userName":"游客"},
   * {"advId":1,"advName":"","avart":"","clickHearts":false,
   * "commentId":"d3e097dea0594d59bb7995ca23c9aeb9","commentTime":1461548635000,
   * "contents":"Jjjq","hearts":0,"heartsUser":"","thumbnail":"","userId":74,"userName":"游客"},
   * {"advId":1,"advName":"","avart":"","clickHearts":false,
   * "commentId":"8e83ac0a1767417a84c77e206cf767b3","commentTime":1461398383000,
   * "contents":"Klqejkljmwkl","hearts":1,"heartsUser":"74,","thumbnail":"","userId":74,
   * "userName":"游客"},{"advId":1,"advName":"","avart":"http://wx.qlogo
   * .cn/mmopen
   * /kWNw759VkMXgQf9AwrcPWmfkIicTggsFUDqWOPTVGGZU2lLxjwyhCjOb2AqPGwMSeYw3nTrDE9sVAr3tDnglPrBaJgNsQzCdc/0","clickHearts":false,"commentId":"2bd79a744c144a36be617deb64509070","commentTime":1460947831000,"contents":"h\u2006，\u2006l","hearts":0,"heartsUser":"","thumbnail":"","userId":58,"userName":"零点"}]
   * commentsTotal : 10
   * companyName :
   * cost : 101
   * createDate : 2016-04-11
   * creator : admin
   * endTime : 2016-04-12 10:21:00
   * fee : 1000
   * filePath : http://139.129.133.223:8080/uploads/adv/durex.mp4
   * hearts : 0
   * heartsClicked : false
   * heartsUser :
   * leftTime :
   * moneyPrice : 5-10
   * name : 养生堂天然维生素C
   * planCounts : 10000
   * playCounts : 13
   * playType : 1
   * price : 15
   * questionMode : 1
   * startTime : 2016-04-23 16:10:45
   * startTimeE :
   * startTimeS :
   * state : 1
   * subTitle : titile1
   * summary : 养生堂天然维生素C
   * tags : 健康,生活,健康,生活,健康,生活,健康,生活,健康,生活,健康,生活
   * tagsArr : ["健康","生活","健康","生活","健康","生活","健康","生活","健康","生活","健康","生活"]
   * thumbnail : http://139.129.133.223:8080/uploads/adv/adv1.png
   * totalTime :
   * userAdvState : 2
   */

  private String activities;
  private int advId;
  private String advType;
  private String area;
  private String bbjPrice;
  private String benefitType;
  private int categoryId;
  private boolean collected;
  private int collectionNum;
  private String collectors;
  private int commentsTotal;
  private String companyName;
  private int cost;
  private String createDate;
  private String creator;
  private String endTime;
  private int fee;
  private String filePath;
  private int hearts;
  private boolean heartsClicked;
  private String heartsUser;
  private String leftTime;
  private String moneyPrice;
  private String name;
  private int planCounts;
  private int playCounts;
  private String playType;
  private String price;
  private String questionMode;
  private String startTime;
  private String startTimeE;
  private String startTimeS;
  private String state;
  private String subTitle;
  private String summary;
  private String tags;
  private String thumbnail;
  private String totalTime;
  private String userAdvState;
  private String needSysQuestion;

  /**
   * advId : 1
   * advName :
   * avart :
   * clickHearts : false
   * commentId : 8e83ac0a1767417a84c77e206cf767b3
   * commentTime : 1461398383000
   * contents : Klqejkljmwkl
   * hearts : 1
   * heartsUser : 74,
   * thumbnail :
   * userId : 74
   * userName : 游客
   */

  private List<Comment> commentsHotList;
  /**
   * advId : 1
   * advName :
   * avart :
   * clickHearts : false
   * commentId : 4f6650b7a6b44a84b2bd0aafe1d78a69
   * commentTime : 1461566992000
   * contents : Dsaq
   * hearts : 0
   * heartsUser :
   * thumbnail :
   * userId : 4
   * userName : Tianqi
   */

  private List<Comment> commentsTimeList;
  private List<String> tagsArr;

  public String getActivities() {
    return activities;
  }

  public void setActivities(String activities) {
    this.activities = activities;
  }

  public int getAdvId() {
    return advId;
  }

  public void setAdvId(int advId) {
    this.advId = advId;
  }

  public String getAdvType() {
    return advType;
  }

  public void setAdvType(String advType) {
    this.advType = advType;
  }

  public String getArea() {
    return area;
  }

  public void setArea(String area) {
    this.area = area;
  }

  public String getBbjPrice() {
    return bbjPrice;
  }

  public void setBbjPrice(String bbjPrice) {
    this.bbjPrice = bbjPrice;
  }

  public String getBenefitType() {
    return benefitType;
  }

  public void setBenefitType(String benefitType) {
    this.benefitType = benefitType;
  }

  public int getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(int categoryId) {
    this.categoryId = categoryId;
  }

  public boolean isCollected() {
    return collected;
  }

  public void setCollected(boolean collected) {
    this.collected = collected;
  }

  public int getCollectionNum() {
    return collectionNum;
  }

  public void setCollectionNum(int collectionNum) {
    this.collectionNum = collectionNum;
  }

  public String getCollectors() {
    return collectors;
  }

  public void setCollectors(String collectors) {
    this.collectors = collectors;
  }

  public int getCommentsTotal() {
    return commentsTotal;
  }

  public void setCommentsTotal(int commentsTotal) {
    this.commentsTotal = commentsTotal;
  }

  public String getCompanyName() {
    return companyName;
  }

  public void setCompanyName(String companyName) {
    this.companyName = companyName;
  }

  public int getCost() {
    return cost;
  }

  public void setCost(int cost) {
    this.cost = cost;
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

  public String getEndTime() {
    return endTime;
  }

  public void setEndTime(String endTime) {
    this.endTime = endTime;
  }

  public int getFee() {
    return fee;
  }

  public void setFee(int fee) {
    this.fee = fee;
  }

  public String getFilePath() {
    return filePath;
  }

  public void setFilePath(String filePath) {
    this.filePath = filePath;
  }

  public int getHearts() {
    return hearts;
  }

  public void setHearts(int hearts) {
    this.hearts = hearts;
  }

  public boolean isHeartsClicked() {
    return heartsClicked;
  }

  public void setHeartsClicked(boolean heartsClicked) {
    this.heartsClicked = heartsClicked;
  }

  public String getHeartsUser() {
    return heartsUser;
  }

  public void setHeartsUser(String heartsUser) {
    this.heartsUser = heartsUser;
  }

  public String getLeftTime() {
    return leftTime;
  }

  public void setLeftTime(String leftTime) {
    this.leftTime = leftTime;
  }

  public String getMoneyPrice() {
    return moneyPrice;
  }

  public void setMoneyPrice(String moneyPrice) {
    this.moneyPrice = moneyPrice;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getPlanCounts() {
    return planCounts;
  }

  public void setPlanCounts(int planCounts) {
    this.planCounts = planCounts;
  }

  public int getPlayCounts() {
    return playCounts;
  }

  public void setPlayCounts(int playCounts) {
    this.playCounts = playCounts;
  }

  public String getPlayType() {
    return playType;
  }

  public void setPlayType(String playType) {
    this.playType = playType;
  }

  public String getPrice() {
    return price;
  }

  public void setPrice(String price) {
    this.price = price;
  }

  public String getQuestionMode() {
    return questionMode;
  }

  public void setQuestionMode(String questionMode) {
    this.questionMode = questionMode;
  }

  public String getStartTime() {
    return startTime;
  }

  public void setStartTime(String startTime) {
    this.startTime = startTime;
  }

  public String getStartTimeE() {
    return startTimeE;
  }

  public void setStartTimeE(String startTimeE) {
    this.startTimeE = startTimeE;
  }

  public String getStartTimeS() {
    return startTimeS;
  }

  public void setStartTimeS(String startTimeS) {
    this.startTimeS = startTimeS;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public String getSubTitle() {
    return subTitle;
  }

  public void setSubTitle(String subTitle) {
    this.subTitle = subTitle;
  }

  public String getSummary() {
    return summary;
  }

  public void setSummary(String summary) {
    this.summary = summary;
  }

  public String getTags() {
    return tags;
  }

  public void setTags(String tags) {
    this.tags = tags;
  }

  public String getThumbnail() {
    return thumbnail;
  }

  public void setThumbnail(String thumbnail) {
    this.thumbnail = thumbnail;
  }

  public String getTotalTime() {
    return totalTime;
  }

  public void setTotalTime(String totalTime) {
    this.totalTime = totalTime;
  }

  public String getUserAdvState() {
    return userAdvState;
  }

  public void setUserAdvState(String userAdvState) {
    this.userAdvState = userAdvState;
  }

  public List<Comment> getCommentsHotList() {
    return commentsHotList;
  }

  public void setCommentsHotList(List<Comment> commentsHotList) {
    this.commentsHotList = commentsHotList;
  }

  public List<Comment> getCommentsTimeList() {
    return commentsTimeList;
  }

  public void setCommentsTimeList(List<Comment> commentsTimeList) {
    this.commentsTimeList = commentsTimeList;
  }

  public List<String> getTagsArr() {
    return tagsArr;
  }

  public void setTagsArr(List<String> tagsArr) {
    this.tagsArr = tagsArr;
  }

  public String getNeedSysQuestion() {
    return needSysQuestion;
  }

  public void setNeedSysQuestion(String needQuestion) {
    this.needSysQuestion = needQuestion;
  }

  public static class Comment {
    private int advId;
    private String advName;
    private String avart;
    private boolean clickHearts;
    private String commentId;
    private long commentTime;
    private String contents;
    private int hearts;
    private String heartsUser;
    private String thumbnail;
    private int userId;
    private String userName;

    public int getAdvId() {
      return advId;
    }

    public void setAdvId(int advId) {
      this.advId = advId;
    }

    public String getAdvName() {
      return advName;
    }

    public void setAdvName(String advName) {
      this.advName = advName;
    }

    public String getAvart() {
      return avart;
    }

    public void setAvart(String avart) {
      this.avart = avart;
    }

    public boolean isClickHearts() {
      return clickHearts;
    }

    public void setClickHearts(boolean clickHearts) {
      this.clickHearts = clickHearts;
    }

    public String getCommentId() {
      return commentId;
    }

    public void setCommentId(String commentId) {
      this.commentId = commentId;
    }

    public long getCommentTime() {
      return commentTime;
    }

    public void setCommentTime(long commentTime) {
      this.commentTime = commentTime;
    }

    public String getContents() {
      return contents;
    }

    public void setContents(String contents) {
      this.contents = contents;
    }

    public int getHearts() {
      return hearts;
    }

    public void setHearts(int hearts) {
      this.hearts = hearts;
    }

    public String getHeartsUser() {
      return heartsUser;
    }

    public void setHeartsUser(String heartsUser) {
      this.heartsUser = heartsUser;
    }

    public String getThumbnail() {
      return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
      this.thumbnail = thumbnail;
    }

    public int getUserId() {
      return userId;
    }

    public void setUserId(int userId) {
      this.userId = userId;
    }

    public String getUserName() {
      return userName;
    }

    public void setUserName(String userName) {
      this.userName = userName;
    }
  }

}
