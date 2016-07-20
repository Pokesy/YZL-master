/*
 * 文件名: BibiModel
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
 * 哔哔信息<BR>
 *
 * @author zhaozeyang
 * @version [Taobei Client V20160411, 16/5/11]
 */
public class BibiModel {

  /**
   * advQuantity : 0
   * benefit : 0
   * benefits : {"key":["21,22,23,昨日,今日,明日,27"],"value":["12,13,14,15,0,0,0"]}
   * expectedBenefit : 12
   * loginDays : 1
   * ranking : 5
   * rankingPercentage : 70%
   * totalBenefit : 0
   * whichDay : 1
   */

  /**
   * data : {"advQuantity":0,"benefit":0,"benefits":{"key":["21,22,23,昨日,今日,明日,27"],"value":["12,
   * 13,14,15,0,0,0"]},"expectedBenefit":12,"loginDays":1,"ranking":"5",
   * "rankingPercentage":"70%","totalBenefit":0,"whichDay":1}
   * message : 成功
   * result : 1
   * token : null
   */

  private int advQuantity;
  private int benefit;
  private BenefitsBean benefits;
  private int expectedBenefit;
  private int loginDays;
  private String ranking;
  private String rankingPercentage;
  private int totalBenefit;
  private int whichDay;
  private int viewTimes;
  private int playDuration;

  public int getAdvQuantity() {
    return advQuantity;
  }

  public void setAdvQuantity(int advQuantity) {
    this.advQuantity = advQuantity;
  }

  public int getBenefit() {
    return benefit;
  }

  public void setBenefit(int benefit) {
    this.benefit = benefit;
  }

  public BenefitsBean getBenefits() {
    return benefits;
  }

  public void setBenefits(BenefitsBean benefits) {
    this.benefits = benefits;
  }

  public int getExpectedBenefit() {
    return expectedBenefit;
  }

  public void setExpectedBenefit(int expectedBenefit) {
    this.expectedBenefit = expectedBenefit;
  }

  public int getLoginDays() {
    return loginDays;
  }

  public void setLoginDays(int loginDays) {
    this.loginDays = loginDays;
  }

  public String getRanking() {
    return ranking;
  }

  public void setRanking(String ranking) {
    this.ranking = ranking;
  }

  public String getRankingPercentage() {
    return rankingPercentage;
  }

  public void setRankingPercentage(String rankingPercentage) {
    this.rankingPercentage = rankingPercentage;
  }

  public int getTotalBenefit() {
    return totalBenefit;
  }

  public void setTotalBenefit(int totalBenefit) {
    this.totalBenefit = totalBenefit;
  }

  public int getWhichDay() {
    return whichDay;
  }

  public void setWhichDay(int whichDay) {
    this.whichDay = whichDay;
  }

  public int getViewTimes() {
    return viewTimes;
  }

  public void setViewTimes(int viewTimes) {
    this.viewTimes = viewTimes;
  }

  public int getPlayDuration() {
    return playDuration;
  }

  public void setPlayDuration(int playDuration) {
    this.playDuration = playDuration;
  }

  public static class BenefitsBean {
    private List<String> key;
    private List<String> value;

    public List<String> getKey() {
      return key;
    }

    public void setKey(List<String> key) {
      this.key = key;
    }

    public List<String> getValue() {
      return value;
    }

    public void setValue(List<String> value) {
      this.value = value;
    }
  }
}
