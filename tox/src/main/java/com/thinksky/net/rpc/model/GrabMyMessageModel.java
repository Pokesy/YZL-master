/*
 * 文件名: GrabMyMessageModel
 * 版    权：  Copyright Hengrtech Tech. Co. Ltd. All Rights Reserved.
 * 描    述: [该类的简要描述]
 * 创建人: zhaozeyang
 * 创建时间:16/5/20
 * 
 * 修改人：
 * 修改时间:
 * 修改内容：[修改内容]
 */
package com.thinksky.net.rpc.model;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;

/**
 * [一句话功能简述]<BR>
 * [功能详细描述]
 *
 * @author zhaozeyang
 * @version [Taobei Client V20160411, 16/5/20]
 */
public class GrabMyMessageModel {


  /**
   * msgContent : 图图
   * msgId : 792f77320648444d978c4949efe224e1
   * msgImg :
   * msgRefer : 8
   * msgTime : 1463142179000
   * msgTitle : Tianqi
   * msgType : 2
   * userId : 94
   */

  private List<MessageBean> ActivityBroadcast;
  /**
   * msgContent : Adagio
   * msgId : 9095fbdd3bdc405c920ced60d87e5aa1
   * msgImg :
   * msgRefer : 1
   * msgTime : 1463147203000
   * msgTitle : Tianqi
   * msgType : 3
   * userId : 94
   */

  private List<MessageBean> CommentsPraise;
  /**
   * msgContent : Qjkljwkelas
   * msgId : e0108049806046e0b625d1a718a5b855
   * msgImg :
   * msgRefer : 1
   * msgTime : 1463100429000
   * msgTitle : Tianqi
   * msgType : 1
   * userId : 94
   */

  private List<MessageBean> SystemNotification;

  public List<MessageBean> getActivityBroadcast() {
    return ActivityBroadcast;
  }

  public void setActivityBroadcast(List<MessageBean> ActivityBroadcast) {
    this.ActivityBroadcast = ActivityBroadcast;
  }

  public List<MessageBean> getCommentsPraise() {
    return CommentsPraise;
  }

  public void setCommentsPraise(List<MessageBean> CommentsPraise) {
    this.CommentsPraise = CommentsPraise;
  }

  public List<MessageBean> getSystemNotification() {
    return SystemNotification;
  }

  public void setSystemNotification(List<MessageBean> SystemNotification) {
    this.SystemNotification = SystemNotification;
  }

  public static class MessageBean implements Parcelable{

    private String msgContent;
    private String msgId;
    private String msgImg;
    private String msgRefer;
    private long msgTime;
    private String msgTitle;
    private String msgType;
    private String userId;

    public String getMsgContent() {
      return msgContent;
    }

    public void setMsgContent(String msgContent) {
      this.msgContent = msgContent;
    }

    public String getMsgId() {
      return msgId;
    }

    public void setMsgId(String msgId) {
      this.msgId = msgId;
    }

    public String getMsgImg() {
      return msgImg;
    }

    public void setMsgImg(String msgImg) {
      this.msgImg = msgImg;
    }

    public String getMsgRefer() {
      return msgRefer;
    }

    public void setMsgRefer(String msgRefer) {
      this.msgRefer = msgRefer;
    }

    public long getMsgTime() {
      return msgTime;
    }

    public void setMsgTime(long msgTime) {
      this.msgTime = msgTime;
    }

    public String getMsgTitle() {
      return msgTitle;
    }

    public void setMsgTitle(String msgTitle) {
      this.msgTitle = msgTitle;
    }

    public String getMsgType() {
      return msgType;
    }

    public void setMsgType(String msgType) {
      this.msgType = msgType;
    }

    public String getUserId() {
      return userId;
    }

    public void setUserId(String userId) {
      this.userId = userId;
    }

    @Override
    public int describeContents() {
      return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
      dest.writeString(this.msgContent);
      dest.writeString(this.msgId);
      dest.writeString(this.msgImg);
      dest.writeString(this.msgRefer);
      dest.writeLong(this.msgTime);
      dest.writeString(this.msgTitle);
      dest.writeString(this.msgType);
      dest.writeString(this.userId);
    }

    public MessageBean() {
    }

    protected MessageBean(Parcel in) {
      this.msgContent = in.readString();
      this.msgId = in.readString();
      this.msgImg = in.readString();
      this.msgRefer = in.readString();
      this.msgTime = in.readLong();
      this.msgTitle = in.readString();
      this.msgType = in.readString();
      this.userId = in.readString();
    }

    public static final Creator<MessageBean> CREATOR = new Creator<MessageBean>() {
      @Override
      public MessageBean createFromParcel(Parcel source) {
        return new MessageBean(source);
      }

      @Override
      public MessageBean[] newArray(int size) {
        return new MessageBean[size];
      }
    };
  }

}
