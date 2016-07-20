/*
 * 文件名: AdvPlayInfo
 * 版    权：  Copyright Hengrtech Tech. Co. Ltd. All Rights Reserved.
 * 描    述: [该类的简要描述]
 * 创建人: zhaozeyang
 * 创建时间:16/5/6
 * 
 * 修改人：
 * 修改时间:
 * 修改内容：[修改内容]
 */
package com.thinksky.net.rpc.model;

/**
 * 开始播放<BR>
 *
 * @author zhaozeyang
 * @version [Taobei Client V20160411, 16/5/6]
 */
public class AdvPlayInfo {

  /**
   * benefitFinal : 5
   * benefitType : 1
   * watchId : 66c60f86e7c04387a9fde69356fe4b8d
   */

  private String benefitFinal;
  private String benefitType;
  private String watchId;

  public String getBenefitFinal() {
    return benefitFinal;
  }

  public void setBenefitFinal(String benefitFinal) {
    this.benefitFinal = benefitFinal;
  }

  public String getBenefitType() {
    return benefitType;
  }

  public void setBenefitType(String benefitType) {
    this.benefitType = benefitType;
  }

  public String getWatchId() {
    return watchId;
  }

  public void setWatchId(String watchId) {
    this.watchId = watchId;
  }
}
