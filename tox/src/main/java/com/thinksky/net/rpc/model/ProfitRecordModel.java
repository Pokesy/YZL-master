/*
 * 文件名: ProfitRecordModel
 * 版    权：  Copyright Hengrtech Tech. Co. Ltd. All Rights Reserved.
 * 描    述: [该类的简要描述]
 * 创建人: zhaozeyang
 * 创建时间:16/5/18
 * 
 * 修改人：
 * 修改时间:
 * 修改内容：[修改内容]
 */
package com.thinksky.net.rpc.model;

/**
 * [一句话功能简述]<BR>
 * [功能详细描述]
 *
 * @author zhaozeyang
 * @version [Taobei Client V20160411, 16/5/18]
 */
public class ProfitRecordModel {

  /**
   * benefit : 0
   * benefitType : 1
   * recordDesc : 已支付观看广告收入，支付成功。
   * recordId : 35cb54012a41465393bff71d9f79289b
   * recordTime : 1462774522000
   * recordTitle : 观看广告收入
   * userId : 94
   */

  private int benefit;
  private String benefitType;
  private String recordDesc;
  private String recordId;
  private long recordTime;
  private String recordTitle;
  private String userId;

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

  public String getRecordDesc() {
    return recordDesc;
  }

  public void setRecordDesc(String recordDesc) {
    this.recordDesc = recordDesc;
  }

  public String getRecordId() {
    return recordId;
  }

  public void setRecordId(String recordId) {
    this.recordId = recordId;
  }

  public long getRecordTime() {
    return recordTime;
  }

  public void setRecordTime(long recordTime) {
    this.recordTime = recordTime;
  }

  public String getRecordTitle() {
    return recordTitle;
  }

  public void setRecordTitle(String recordTitle) {
    this.recordTitle = recordTitle;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }
}
