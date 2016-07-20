/*
 * 文件名: CollectAdvModel
 * 版    权：  Copyright Hengrtech Tech. Co. Ltd. All Rights Reserved.
 * 描    述: [该类的简要描述]
 * 创建人: zhaozeyang
 * 创建时间:16/5/13
 * 
 * 修改人：
 * 修改时间:
 * 修改内容：[修改内容]
 */
package com.thinksky.net.rpc.model;

import java.util.List;

/**
 * [一句话功能简述]<BR>
 * [功能详细描述]
 *
 * @author zhaozeyang
 * @version [Taobei Client V20160411, 16/5/13]
 */
public class CollectAdvModel {

  /**
   * adv : {"activities":"春节购买可享受八折优惠春节购买可享受八折优惠春节购买可享受八折优惠春节购买可享受八折优惠春节购买可享受八折优惠春节购买可享受八折优惠",
   * "advId":1,"advType":"1","area":"bj","bbjPrice":"","benefitType":"","categoryId":"2",
   * "collected":false,"collectionNum":0,"collectors":"4,94,","commentsHotList":[],
   * "commentsTimeList":[],"commentsTotal":0,"companyName":"","cost":142,
   * "createDate":"2016-04-11","creator":"admin","endTime":"2016-04-12 10:21:00","fee":1000,
   * "filePath":"http://139.129.133.223:8080/uploads/adv/durex.mp4","hearts":0,
   * "heartsClicked":false,"heartsUser":"4,94,","leftTime":"","moneyPrice":"","name":"养生堂天然维生素C",
   * "needSysQuestion":"1","planCounts":10000,"playCounts":19,"playType":"1","price":"5,10|20,15,
   * 10,5","questionMode":"1","questions":[],"startTime":"2016-05-13 10:57:03","startTimeE":"",
   * "startTimeS":"","state":"1","subTitle":"titile1","summary":"养生堂天然维生素C","tags":"健康,生活,健康,生活,
   * 健康,生活,健康,生活,健康,生活,健康,生活","tagsArr":[],"thumbnail":"http://139.129.133
   * .223:8080/uploads/adv/adv1.png","totalTime":"","userAdvState":""}
   * advId : 1
   * collectId : 9624924a7b3a4494847b973ed3237d15
   * collectTime : 1463132648000
   * userId : 94
   */

  /**
   * activities : 春节购买可享受八折优惠春节购买可享受八折优惠春节购买可享受八折优惠春节购买可享受八折优惠春节购买可享受八折优惠春节购买可享受八折优惠
   * advId : 1
   * advType : 1
   * area : bj
   * bbjPrice :
   * benefitType :
   * categoryId : 2
   * collected : false
   * collectionNum : 0
   * collectors : 4,94,
   * commentsHotList : []
   * commentsTimeList : []
   * commentsTotal : 0
   * companyName :
   * cost : 142
   * createDate : 2016-04-11
   * creator : admin
   * endTime : 2016-04-12 10:21:00
   * fee : 1000
   * filePath : http://139.129.133.223:8080/uploads/adv/durex.mp4
   * hearts : 0
   * heartsClicked : false
   * heartsUser : 4,94,
   * leftTime :
   * moneyPrice :
   * name : 养生堂天然维生素C
   * needSysQuestion : 1
   * planCounts : 10000
   * playCounts : 19
   * playType : 1
   * price : 5,10|20,15,10,5
   * questionMode : 1
   * questions : []
   * startTime : 2016-05-13 10:57:03
   * startTimeE :
   * startTimeS :
   * state : 1
   * subTitle : titile1
   * summary : 养生堂天然维生素C
   * tags : 健康,生活,健康,生活,健康,生活,健康,生活,健康,生活,健康,生活
   * tagsArr : []
   * thumbnail : http://139.129.133.223:8080/uploads/adv/adv1.png
   * totalTime :
   * userAdvState :
   */

  private AdvBean adv;
  private int advId;
  private String collectId;
  private long collectTime;
  private int userId;

  public AdvBean getAdv() {
    return adv;
  }

  public void setAdv(AdvBean adv) {
    this.adv = adv;
  }

  public int getAdvId() {
    return advId;
  }

  public void setAdvId(int advId) {
    this.advId = advId;
  }

  public String getCollectId() {
    return collectId;
  }

  public void setCollectId(String collectId) {
    this.collectId = collectId;
  }

  public long getCollectTime() {
    return collectTime;
  }

  public void setCollectTime(long collectTime) {
    this.collectTime = collectTime;
  }

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public static class AdvBean {
    private String activities;
    private int advId;
    private String advType;
    private String area;
    private String bbjPrice;
    private String benefitType;
    private String categoryId;
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
    private String needSysQuestion;
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
    private List<?> commentsHotList;
    private List<?> commentsTimeList;
    private List<?> questions;
    private List<?> tagsArr;

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

    public String getCategoryId() {
      return categoryId;
    }

    public void setCategoryId(String categoryId) {
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

    public String getNeedSysQuestion() {
      return needSysQuestion;
    }

    public void setNeedSysQuestion(String needSysQuestion) {
      this.needSysQuestion = needSysQuestion;
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

    public List<?> getCommentsHotList() {
      return commentsHotList;
    }

    public void setCommentsHotList(List<?> commentsHotList) {
      this.commentsHotList = commentsHotList;
    }

    public List<?> getCommentsTimeList() {
      return commentsTimeList;
    }

    public void setCommentsTimeList(List<?> commentsTimeList) {
      this.commentsTimeList = commentsTimeList;
    }

    public List<?> getQuestions() {
      return questions;
    }

    public void setQuestions(List<?> questions) {
      this.questions = questions;
    }

    public List<?> getTagsArr() {
      return tagsArr;
    }

    public void setTagsArr(List<?> tagsArr) {
      this.tagsArr = tagsArr;
    }
  }
}
