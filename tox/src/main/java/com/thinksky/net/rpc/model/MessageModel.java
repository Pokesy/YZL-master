/*
 * 文件名: MessageModel
 * 版    权：  Copyright Hengrtech Tech. Co. Ltd. All Rights Reserved.
 * 描    述: [该类的简要描述]
 * 创建人: zhaozeyang
 * 创建时间:16/8/12
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
 * @version [Taobei Client V20160411, 16/8/12]
 */
public class MessageModel extends BaseModel {

  /**
   * id : 458
   * content_id : 531
   * from_uid : 427
   * to_uid : 325
   * create_time : 08月09日 15:15
   * is_read : 0
   * last_toast : 0
   * status : 1
   * module : 23
   * content : {"id":"531","from_id":"427","title":"给您的问题提交了答案","content":"回答您的问题。",
   * "url":"Question/Index/detail","args":"","type":"0","create_time":"1470726919","status":"1"}
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
    private String content_id;
    private String from_uid;
    private String to_uid;
    private String create_time;
    private String is_read;
    private String last_toast;
    private String status;
    private String module;
    /**
     * id : 531
     * from_id : 427
     * title : 给您的问题提交了答案
     * content : 回答您的问题。
     * url : Question/Index/detail
     * args :
     * type : 0
     * create_time : 1470726919
     * status : 1
     */

    private ContentBean content;

    public String getId() {
      return id;
    }

    public void setId(String id) {
      this.id = id;
    }

    public String getContent_id() {
      return content_id;
    }

    public void setContent_id(String content_id) {
      this.content_id = content_id;
    }

    public String getFrom_uid() {
      return from_uid;
    }

    public void setFrom_uid(String from_uid) {
      this.from_uid = from_uid;
    }

    public String getTo_uid() {
      return to_uid;
    }

    public void setTo_uid(String to_uid) {
      this.to_uid = to_uid;
    }

    public String getCreate_time() {
      return create_time;
    }

    public void setCreate_time(String create_time) {
      this.create_time = create_time;
    }

    public String getIs_read() {
      return is_read;
    }

    public void setIs_read(String is_read) {
      this.is_read = is_read;
    }

    public String getLast_toast() {
      return last_toast;
    }

    public void setLast_toast(String last_toast) {
      this.last_toast = last_toast;
    }

    public String getStatus() {
      return status;
    }

    public void setStatus(String status) {
      this.status = status;
    }

    public String getModule() {
      return module;
    }

    public void setModule(String module) {
      this.module = module;
    }

    public ContentBean getContent() {
      return content;
    }

    public void setContent(ContentBean content) {
      this.content = content;
    }

    public static class ContentBean {
      private String id;
      private String from_id;
      private String title;
      private String content;
      private String url;
      private String args;
      private String type;
      private String create_time;
      private String status;

      public String getId() {
        return id;
      }

      public void setId(String id) {
        this.id = id;
      }

      public String getFrom_id() {
        return from_id;
      }

      public void setFrom_id(String from_id) {
        this.from_id = from_id;
      }

      public String getTitle() {
        return title;
      }

      public void setTitle(String title) {
        this.title = title;
      }

      public String getContent() {
        return content;
      }

      public void setContent(String content) {
        this.content = content;
      }

      public String getUrl() {
        return url;
      }

      public void setUrl(String url) {
        this.url = url;
      }

      public String getArgs() {
        return args;
      }

      public void setArgs(String args) {
        this.args = args;
      }

      public String getType() {
        return type;
      }

      public void setType(String type) {
        this.type = type;
      }

      public String getCreate_time() {
        return create_time;
      }

      public void setCreate_time(String create_time) {
        this.create_time = create_time;
      }

      public String getStatus() {
        return status;
      }

      public void setStatus(String status) {
        this.status = status;
      }
    }
  }
}
