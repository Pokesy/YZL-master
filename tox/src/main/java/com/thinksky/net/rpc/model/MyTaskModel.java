package com.thinksky.net.rpc.model;

import java.util.List;

/**
 * Created by jiao on 2016/6/6.
 */
public class MyTaskModel {
  /**
   * list : [{"description":"完成任意三项资料","id":"aa","orderNo":1,"state":"0","title":"完善资料","type":"0"},{"description":"完成任意广告并领取奖励","id":"ab","orderNo":2,"state":"0","title":"看广告","type":"0"},{"description":"完成任意答题","id":"ac","orderNo":3,"state":"0","title":"答题","type":"0"},{"description":"申请提现","id":"ad","orderNo":4,"state":"0","title":"提现","type":"0"},{"description":"哔哔分享","id":"ae","orderNo":5,"state":"0","title":"分享朋友圈","type":"0"},{"description":"添加任意朋友","id":"af","orderNo":6,"state":"0","title":"添加朋友","type":"0"},{"description":"分享任意广告","id":"ag","orderNo":7,"state":"0","title":"分享广告","type":"0"}]
   * type : 0
   */

  private String type;
  /**
   * description : 完成任意三项资料
   * id : aa
   * orderNo : 1
   * state : 0
   * title : 完善资料
   * type : 0
   */

  private List<ListEntity> list;

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public List<ListEntity> getList() {
    return list;
  }

  public void setList(List<ListEntity> list) {
    this.list = list;
  }

  public static class ListEntity {
    private String desc;
    private String id;
    private int orderNo;
    private String state;
    private String title;
    private String type;

    public String getTarget() {
      return target;
    }

    public void setTarget(String target) {
      this.target = target;
    }

    private String target;

    public String getDesc() {
      return desc;
    }

    public void setDesc(String desc) {
      this.desc = desc;
    }

    public String getId() {
      return id;
    }

    public void setId(String id) {
      this.id = id;
    }

    public int getOrderNo() {
      return orderNo;
    }

    public void setOrderNo(int orderNo) {
      this.orderNo = orderNo;
    }

    public String getState() {
      return state;
    }

    public void setState(String state) {
      this.state = state;
    }

    public String getTitle() {
      return title;
    }

    public void setTitle(String title) {
      this.title = title;
    }

    public String getType() {
      return type;
    }

    public void setType(String type) {
      this.type = type;
    }
  }
}
