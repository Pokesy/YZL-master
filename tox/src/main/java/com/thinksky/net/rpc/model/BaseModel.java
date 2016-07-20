/*
 * 文件名: BaseModel
 * 版    权：  Copyright Hengrtech Tech. Co. Ltd. All Rights Reserved.
 * 描    述: [该类的简要描述]
 * 创建人: zhaozeyang
 * 创建时间:16/4/20
 * 
 * 修改人：
 * 修改时间:
 * 修改内容：[修改内容]
 */
package com.thinksky.net.rpc.model;

/**
 * 基类<BR>
 *
 * @author zhaozeyang
 * @version [Taobei Client V20160411, 16/4/20]
 */
public class BaseModel {
  int result;
  String message;
  String token;

  public int getResult() {
    return result;
  }

  public void setResult(int result) {
    this.result = result;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }
}
