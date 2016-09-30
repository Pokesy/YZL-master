/*
 * 文件名: UploadImageModel
 * 版    权：  Copyright Hengrtech Tech. Co. Ltd. All Rights Reserved.
 * 描    述: [该类的简要描述]
 * 创建人: zhaozeyang
 * 创建时间:16/9/29
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
 * @version [Taobei Client V20160411, 16/9/29]
 */
public class UploadImageModel {

  private MessageModel message;

  public MessageModel getMessage() {
    return message;
  }

  public void setMessage(MessageModel message) {
    this.message = message;
  }

  /**
   * state : SUCCESS
   * url : /Uploads/Picture/2016-07-25/57957d9c2c1d2.png
   * id : 853
   */

  public class MessageModel {

    private String state;
    private String url;
    private String id;

    public String getState() {
      return state;
    }

    public void setState(String state) {
      this.state = state;
    }

    public String getUrl() {
      return url;
    }

    public void setUrl(String url) {
      this.url = url;
    }

    public String getId() {
      return id;
    }

    public void setId(String id) {
      this.id = id;
    }
  }
}
