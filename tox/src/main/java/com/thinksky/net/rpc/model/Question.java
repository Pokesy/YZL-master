/*
 * 文件名: AdvQuestionList
 * 版    权：  Copyright Hengrtech Tech. Co. Ltd. All Rights Reserved.
 * 描    述: [该类的简要描述]
 * 创建人: zhaozeyang
 * 创建时间:16/5/5
 * 
 * 修改人：
 * 修改时间:
 * 修改内容：[修改内容]
 */
package com.thinksky.net.rpc.model;

import java.util.List;

/**
 * 问题列表<BR>
 *
 * @author zhaozeyang
 * @version [Taobei Client V20160411, 16/5/5]
 */
public class Question {

  /**
   * category : 6
   * name : 请问视频中宣传的电视品牌是哪一个：
   * answer : 1
   * type : 2
   * creator : admin
   * createDate : 2016-01-15 14:39:49
   * flag : 0
   * myAnswer : null
   * qItemList : [{"questionId":"1","item":"松下viera3D1","orderNo":"1","qItemId":"1"},
   * {"questionId":"1","item":"索尼2","orderNo":"2","qItemId":"2"},{"questionId":"1","item":"创维3",
   * "orderNo":"3","qItemId":"3"},{"questionId":"1","item":"3D平板","orderNo":"4","qItemId":"4"}]
   * qId : 1
   */

  private String category;
  private String name;
  private String answer;
  private String type;
  private String creator;
  private String createDate;
  private String flag;
  private Object myAnswer;
  private String qId;
  /**
   * questionId : 1
   * item : 松下viera3D1
   * orderNo : 1
   * qItemId : 1
   */

  private List<QuestionOptions> qItemList;

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAnswer() {
    return answer;
  }

  public void setAnswer(String answer) {
    this.answer = answer;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getCreator() {
    return creator;
  }

  public void setCreator(String creator) {
    this.creator = creator;
  }

  public String getCreateDate() {
    return createDate;
  }

  public void setCreateDate(String createDate) {
    this.createDate = createDate;
  }

  public String getFlag() {
    return flag;
  }

  public void setFlag(String flag) {
    this.flag = flag;
  }

  public Object getMyAnswer() {
    return myAnswer;
  }

  public void setMyAnswer(Object myAnswer) {
    this.myAnswer = myAnswer;
  }

  public String getQId() {
    return qId;
  }

  public void setQId(String qId) {
    this.qId = qId;
  }

  public List<QuestionOptions> getQItemList() {
    return qItemList;
  }

  public void setQItemList(List<QuestionOptions> qItemList) {
    this.qItemList = qItemList;
  }

  public static class QuestionOptions {
    private String questionId;
    private String item;
    private String orderNo;
    private String qItemId;

    public String getQuestionId() {
      return questionId;
    }

    public void setQuestionId(String questionId) {
      this.questionId = questionId;
    }

    public String getItem() {
      return item;
    }

    public void setItem(String item) {
      this.item = item;
    }

    public String getOrderNo() {
      return orderNo;
    }

    public void setOrderNo(String orderNo) {
      this.orderNo = orderNo;
    }

    public String getQItemId() {
      return qItemId;
    }

    public void setQItemId(String qItemId) {
      this.qItemId = qItemId;
    }
  }
}
