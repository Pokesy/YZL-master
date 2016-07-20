/*
 * 文件名: CommentModel
 * 版    权：  Copyright Hengrtech Tech. Co. Ltd. All Rights Reserved.
 * 描    述: [该类的简要描述]
 * 创建人: zhaozeyang
 * 创建时间:16/5/19
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
 * @version [Taobei Client V20160411, 16/5/19]
 */
public class CommentModel {


  /**
   * advId : 1
   * advName : 养生堂天然维生素C
   * avart :
   * clickHearts : false
   * commentId : a42bdb19dc2e45dc992d995ef151ed72
   * commentTime : 1463109335000
   * contents : 2222
   * hearts : 0
   * heartsUser :
   * thumbnail : http://139.129.133.223:8080/uploads/adv/adv1.png
   * userId : 94
   * userName :
   */

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
