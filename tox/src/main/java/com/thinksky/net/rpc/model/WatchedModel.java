/*
 * 文件名: WatchedModel
 * 版    权：  Copyright Hengrtech Tech. Co. Ltd. All Rights Reserved.
 * 描    述: [该类的简要描述]
 * 创建人: zhaozeyang
 * 创建时间:16/5/27
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
 * @version [Taobei Client V20160411, 16/5/27]
 */
public class WatchedModel {
  public static final String STATE_ANSWER = "1";
  public static final String STATE_ACCEPT = "2";
  public static final String STATE_WATCH_AGAIN = "3";
  public static final String STATE_OVER = "4";

  /**
   * list : [{"advId":2,"advState":"1","money":0,"name":"北京现代ELANTRA领动精彩","seeDate":"2016-05-09
   * 08:11:42","state":"2","summary":"北京现代ELANTRA-领动-领新而动","thumbnail":"http://139.129.133.223:8080/uploads/adv/adv2.png","userId":0,"virtualMoney":0}]
   * seeDate : 2016年5月9日
   */

  private String seeDate;
  /**
   * advId : 2
   * advState : 1
   * money : 0
   * name : 北京现代ELANTRA领动精彩
   * seeDate : 2016-05-09 08:11:42
   * state : 2
   * summary : 北京现代ELANTRA-领动-领新而动
   * thumbnail : http://139.129.133.223:8080/uploads/adv/adv2.png
   * userId : 0
   * virtualMoney : 0
   */

  private List<ListBean> list;

  public String getSeeDate() {
    return seeDate;
  }

  public void setSeeDate(String seeDate) {
    this.seeDate = seeDate;
  }

  public List<ListBean> getList() {
    return list;
  }

  public void setList(List<ListBean> list) {
    this.list = list;
  }

  public static class ListBean {
    private int advId;
    private String advState;
    private int money;
    private String name;
    private String seeDate;
    private String state;
    private String summary;
    private String thumbnail;
    private int userId;
    private int virtualMoney;

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

    public int getMoney() {
      return money;
    }

    public void setMoney(int money) {
      this.money = money;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getSeeDate() {
      return seeDate;
    }

    public void setSeeDate(String seeDate) {
      this.seeDate = seeDate;
    }

    public String getState() {
      return state;
    }

    public void setState(String state) {
      this.state = state;
    }

    public String getSummary() {
      return summary;
    }

    public void setSummary(String summary) {
      this.summary = summary;
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

    public int getVirtualMoney() {
      return virtualMoney;
    }

    public void setVirtualMoney(int virtualMoney) {
      this.virtualMoney = virtualMoney;
    }
  }
}
