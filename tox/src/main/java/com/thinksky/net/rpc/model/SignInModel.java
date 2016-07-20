/*
 * 文件名: SignInModel
 * 版    权：  Copyright Hengrtech Tech. Co. Ltd. All Rights Reserved.
 * 描    述: [该类的简要描述]
 * 创建人: zhaozeyang
 * 创建时间:16/5/16
 * 
 * 修改人：
 * 修改时间:
 * 修改内容：[修改内容]
 */
package com.thinksky.net.rpc.model;

import java.util.List;

/**
 * 签到对象<BR>
 *
 * @author zhaozeyang
 * @version [Taobei Client V20160411, 16/5/16]
 */
public class SignInModel {

  /**
   * benefitRules : [{"benefit":2,"benefitType":"0"},{"benefit":3,"benefitType":"1"},
   * {"benefit":5,"benefitType":"1"},{"benefit":8,"benefitType":"0"},{"benefit":10,
   * "benefitType":"1"},{"benefit":15,"benefitType":"1"},{"benefit":20,"benefitType":"0"}]
   * checkinCounts : 1
   * isCheckin : 0
   */

  private int checkinCounts;
  private String isCheckin;
  /**
   * benefit : 2
   * benefitType : 0
   */

  private List<BenefitRulesBean> benefitRules;

  public int getCheckinCounts() {
    return checkinCounts;
  }

  public void setCheckinCounts(int checkinCounts) {
    this.checkinCounts = checkinCounts;
  }

  public String getIsCheckin() {
    return isCheckin;
  }

  public void setIsCheckin(String isCheckin) {
    this.isCheckin = isCheckin;
  }

  public List<BenefitRulesBean> getBenefitRules() {
    return benefitRules;
  }

  public void setBenefitRules(List<BenefitRulesBean> benefitRules) {
    this.benefitRules = benefitRules;
  }

  public static class BenefitRulesBean {
    private int benefit;
    private String benefitType;

    public int getBenefit() {
      return benefit;
    }

    public void setBenefit(int benefit) {
      this.benefit = benefit;
    }

    public String getBenefitType() {
      return benefitType;
    }

    public void setBenefitType(String benefitType) {
      this.benefitType = benefitType;
    }
  }
}
