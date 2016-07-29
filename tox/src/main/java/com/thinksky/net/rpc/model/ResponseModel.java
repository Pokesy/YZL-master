/*
 * 文件名: AdvertisementList
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
 * 广告列表数据<BR>
 *
 * @author zhaozeyang
 * @version [Taobei Client V20160411, 16/4/20]
 */
public class ResponseModel<T> extends BaseModelTemp {

  private T data;

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }

}
