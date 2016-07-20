/*
 * 文件名: CollectAdvModel
 * 版    权：  Copyright Hengrtech Tech. Co. Ltd. All Rights Reserved.
 * 描    述: [该类的简要描述]
 * 创建人: zhaozeyang
 * 创建时间:16/5/13
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
 * @version [Taobei Client V20160411, 16/5/13]
 */
public class SettingsAdvModel {

  /**
   * data : null
   * message : 成功
   * result : 1
   * token : null
   */

  private Object data;
  private String message;
  private String result;
  private Object token;

  public Object getData() {
    return data;
  }

  public void setData(Object data) {
    this.data = data;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getResult() {
    return result;
  }

  public void setResult(String result) {
    this.result = result;
  }

  public Object getToken() {
    return token;
  }

  public void setToken(Object token) {
    this.token = token;
  }
}
