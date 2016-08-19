/*
 * 文件名: UserListModel
 * 版    权：  Copyright Hengrtech Tech. Co. Ltd. All Rights Reserved.
 * 描    述: [该类的简要描述]
 * 创建人: zhaozeyang
 * 创建时间:16/8/16
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
 * @version [Taobei Client V20160411, 16/8/16]
 */
public class UserListModel extends BaseModel {

  private List<UserInfoModel> result;

  public List<UserInfoModel> getResult() {
    return result;
  }

  public void setResult(List<UserInfoModel> result) {
    this.result = result;
  }

}
