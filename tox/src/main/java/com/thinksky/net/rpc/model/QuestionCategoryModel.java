/*
 * 文件名: QuestionCategoryModel
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
public class QuestionCategoryModel extends BaseModel {

  /**
   * id : 8
   * title : 其他鱼类
   * pid : 0
   * sort : 5
   * status : 1
   */

  private List<ListBean> list;

  public List<ListBean> getList() {
    return list;
  }

  public void setList(List<ListBean> list) {
    this.list = list;
  }

  public static class ListBean {
    private String id;
    private String title;
    private String pid;
    private String sort;
    private String status;

    public String getId() {
      return id;
    }

    public void setId(String id) {
      this.id = id;
    }

    public String getTitle() {
      return title;
    }

    public void setTitle(String title) {
      this.title = title;
    }

    public String getPid() {
      return pid;
    }

    public void setPid(String pid) {
      this.pid = pid;
    }

    public String getSort() {
      return sort;
    }

    public void setSort(String sort) {
      this.sort = sort;
    }

    public String getStatus() {
      return status;
    }

    public void setStatus(String status) {
      this.status = status;
    }
  }
}
