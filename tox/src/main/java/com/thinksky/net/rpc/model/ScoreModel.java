/*
 * 文件名: ScoreModel
 * 版    权：  Copyright Hengrtech Tech. Co. Ltd. All Rights Reserved.
 * 描    述: [该类的简要描述]
 * 创建人: zhaozeyang
 * 创建时间:16/9/26
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
 * @version [Taobei Client V20160411, 16/9/26]
 */
public class ScoreModel extends BaseModel {

  /**
   * score1 : 237
   * ratio : 99.1
   * date : 2016-09-26
   */

  private ListBean list;

  public ListBean getList() {
    return list;
  }

  public void setList(ListBean list) {
    this.list = list;
  }

  public static class ListBean {
    private String score1;
    private String ratio;
    private String date;

    public String getScore1() {
      return score1;
    }

    public void setScore1(String score1) {
      this.score1 = score1;
    }

    public String getRatio() {
      return ratio;
    }

    public void setRatio(String ratio) {
      this.ratio = ratio;
    }

    public String getDate() {
      return date;
    }

    public void setDate(String date) {
      this.date = date;
    }
  }
}
