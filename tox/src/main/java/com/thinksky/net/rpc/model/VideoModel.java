/*
 * 文件名: VideoModel
 * 版    权：  Copyright Hengrtech Tech. Co. Ltd. All Rights Reserved.
 * 描    述: [该类的简要描述]
 * 创建人: zhaozeyang
 * 创建时间:2016/11/10
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
 * @version [Taobei Client V20160411, 2016/11/10]
 */
public class VideoModel extends BaseModel {

  private List<ListBean> list;

  public List<ListBean> getList() {
    return list;
  }

  public void setList(List<ListBean> list) {
    this.list = list;
  }

  public static class ListBean {
    /**
     * id : 62
     * title : 观赏鱼鱼
     * content : 1212
     * view_count : 2
     * cover_id : 1604
     * issue_id : 21
     * issue : 20
     * uid : 1
     * reply_count : 3
     * create_time : 今天09:55
     * update_time : 今天09:55
     * status : 1
     * url : http://player.youku.com/embed/XMTY1MjE1OTQw
     * imgList : []
     * user : {"avatar32":"Uploads/Avatar/2016-04-25/571d78270d2f2_32_32.png",
     * "avatar64":"Uploads/Avatar/2016-04-25/571d78270d2f2_64_64.png",
     * "avatar128":"Uploads/Avatar/2016-04-25/571d78270d2f2_128_128.png",
     * "avatar256":"Uploads/Avatar/2016-04-25/571d78270d2f2_256_256.png",
     * "avatar512":"Uploads/Avatar/2016-04-25/571d78270d2f2_512_512.png","uid":"1",
     * "username":"admin","nickname":"鱼小乐","signature":"鱼小乐竭诚为大家服务哦。联系QQ211130380",
     * "real_nickname":"鱼小乐"}
     * cover_url : Uploads/Picture/2016-11-07/581fdde7272f8_160_210.jpg
     * issue_title : [{"id":"21","title":"种类介绍","create_time":"1468849369",
     * "update_time":"1478486564","status":"1","allow_post":"0","pid":"20","sort":"0"}]
     * support_count : 1
     * is_supported : 1
     * Modules_id : [{"id":"20"}]
     * Comments : [{"id":"236","uid":"608","app":"Issue","mod":"issueContent","row_id":"62",
     * "parse":"0","content":"Qwqw","create_time":"48分钟前","pid":"0","status":"1","ip":"0",
     * "area":""},{"id":"237","uid":"608","app":"Issue","mod":"issueContent","row_id":"62",
     * "parse":"0","content":"Qeqw","create_time":"41分钟前","pid":"0","status":"1","ip":"0",
     * "area":""},{"id":"238","uid":"608","app":"Issue","mod":"issueContent","row_id":"62",
     * "parse":"0","content":"Qwqwwww","create_time":"39分钟前","pid":"0","status":"1","ip":"0",
     * "area":""}]
     */

    private String id;
    private String title;
    private String content;
    private String view_count;
    private String cover_id;
    private String issue_id;
    private String issue;
    private String uid;
    private String reply_count;
    private String create_time;
    private String update_time;
    private String status;
    private String url;
    private UserBean user;
    private String cover_url;
    private String support_count;
    private String is_supported;
    private List<?> imgList;
    private List<IssueTitleBean> issue_title;
    private List<ModulesIdBean> Modules_id;
    private List<CommentsBean> Comments;

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

    public String getContent() {
      return content;
    }

    public void setContent(String content) {
      this.content = content;
    }

    public String getView_count() {
      return view_count;
    }

    public void setView_count(String view_count) {
      this.view_count = view_count;
    }

    public String getCover_id() {
      return cover_id;
    }

    public void setCover_id(String cover_id) {
      this.cover_id = cover_id;
    }

    public String getIssue_id() {
      return issue_id;
    }

    public void setIssue_id(String issue_id) {
      this.issue_id = issue_id;
    }

    public String getIssue() {
      return issue;
    }

    public void setIssue(String issue) {
      this.issue = issue;
    }

    public String getUid() {
      return uid;
    }

    public void setUid(String uid) {
      this.uid = uid;
    }

    public String getReply_count() {
      return reply_count;
    }

    public void setReply_count(String reply_count) {
      this.reply_count = reply_count;
    }

    public String getCreate_time() {
      return create_time;
    }

    public void setCreate_time(String create_time) {
      this.create_time = create_time;
    }

    public String getUpdate_time() {
      return update_time;
    }

    public void setUpdate_time(String update_time) {
      this.update_time = update_time;
    }

    public String getStatus() {
      return status;
    }

    public void setStatus(String status) {
      this.status = status;
    }

    public String getUrl() {
      return url;
    }

    public void setUrl(String url) {
      this.url = url;
    }

    public UserBean getUser() {
      return user;
    }

    public void setUser(UserBean user) {
      this.user = user;
    }

    public String getCover_url() {
      return cover_url;
    }

    public void setCover_url(String cover_url) {
      this.cover_url = cover_url;
    }

    public String getSupport_count() {
      return support_count;
    }

    public void setSupport_count(String support_count) {
      this.support_count = support_count;
    }

    public String getIs_supported() {
      return is_supported;
    }

    public void setIs_supported(String is_supported) {
      this.is_supported = is_supported;
    }

    public List<?> getImgList() {
      return imgList;
    }

    public void setImgList(List<?> imgList) {
      this.imgList = imgList;
    }

    public List<IssueTitleBean> getIssue_title() {
      return issue_title;
    }

    public void setIssue_title(List<IssueTitleBean> issue_title) {
      this.issue_title = issue_title;
    }

    public List<ModulesIdBean> getModules_id() {
      return Modules_id;
    }

    public void setModules_id(List<ModulesIdBean> Modules_id) {
      this.Modules_id = Modules_id;
    }

    public List<CommentsBean> getComments() {
      return Comments;
    }

    public void setComments(List<CommentsBean> Comments) {
      this.Comments = Comments;
    }

    public static class UserBean {
      /**
       * avatar32 : Uploads/Avatar/2016-04-25/571d78270d2f2_32_32.png
       * avatar64 : Uploads/Avatar/2016-04-25/571d78270d2f2_64_64.png
       * avatar128 : Uploads/Avatar/2016-04-25/571d78270d2f2_128_128.png
       * avatar256 : Uploads/Avatar/2016-04-25/571d78270d2f2_256_256.png
       * avatar512 : Uploads/Avatar/2016-04-25/571d78270d2f2_512_512.png
       * uid : 1
       * username : admin
       * nickname : 鱼小乐
       * signature : 鱼小乐竭诚为大家服务哦。联系QQ211130380
       * real_nickname : 鱼小乐
       */

      private String avatar32;
      private String avatar64;
      private String avatar128;
      private String avatar256;
      private String avatar512;
      private String uid;
      private String username;
      private String nickname;
      private String signature;
      private String real_nickname;

      public String getAvatar32() {
        return avatar32;
      }

      public void setAvatar32(String avatar32) {
        this.avatar32 = avatar32;
      }

      public String getAvatar64() {
        return avatar64;
      }

      public void setAvatar64(String avatar64) {
        this.avatar64 = avatar64;
      }

      public String getAvatar128() {
        return avatar128;
      }

      public void setAvatar128(String avatar128) {
        this.avatar128 = avatar128;
      }

      public String getAvatar256() {
        return avatar256;
      }

      public void setAvatar256(String avatar256) {
        this.avatar256 = avatar256;
      }

      public String getAvatar512() {
        return avatar512;
      }

      public void setAvatar512(String avatar512) {
        this.avatar512 = avatar512;
      }

      public String getUid() {
        return uid;
      }

      public void setUid(String uid) {
        this.uid = uid;
      }

      public String getUsername() {
        return username;
      }

      public void setUsername(String username) {
        this.username = username;
      }

      public String getNickname() {
        return nickname;
      }

      public void setNickname(String nickname) {
        this.nickname = nickname;
      }

      public String getSignature() {
        return signature;
      }

      public void setSignature(String signature) {
        this.signature = signature;
      }

      public String getReal_nickname() {
        return real_nickname;
      }

      public void setReal_nickname(String real_nickname) {
        this.real_nickname = real_nickname;
      }
    }

    public static class IssueTitleBean {
      /**
       * id : 21
       * title : 种类介绍
       * create_time : 1468849369
       * update_time : 1478486564
       * status : 1
       * allow_post : 0
       * pid : 20
       * sort : 0
       */

      private String id;
      private String title;
      private String create_time;
      private String update_time;
      private String status;
      private String allow_post;
      private String pid;
      private String sort;

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

      public String getCreate_time() {
        return create_time;
      }

      public void setCreate_time(String create_time) {
        this.create_time = create_time;
      }

      public String getUpdate_time() {
        return update_time;
      }

      public void setUpdate_time(String update_time) {
        this.update_time = update_time;
      }

      public String getStatus() {
        return status;
      }

      public void setStatus(String status) {
        this.status = status;
      }

      public String getAllow_post() {
        return allow_post;
      }

      public void setAllow_post(String allow_post) {
        this.allow_post = allow_post;
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
    }

    public static class ModulesIdBean {
      /**
       * id : 20
       */

      private String id;

      public String getId() {
        return id;
      }

      public void setId(String id) {
        this.id = id;
      }
    }

    public static class CommentsBean {
      /**
       * id : 236
       * uid : 608
       * app : Issue
       * mod : issueContent
       * row_id : 62
       * parse : 0
       * content : Qwqw
       * create_time : 48分钟前
       * pid : 0
       * status : 1
       * ip : 0
       * area :
       */

      private String id;
      private String uid;
      private String app;
      private String mod;
      private String row_id;
      private String parse;
      private String content;
      private String create_time;
      private String pid;
      private String status;
      private String ip;
      private String area;

      public String getId() {
        return id;
      }

      public void setId(String id) {
        this.id = id;
      }

      public String getUid() {
        return uid;
      }

      public void setUid(String uid) {
        this.uid = uid;
      }

      public String getApp() {
        return app;
      }

      public void setApp(String app) {
        this.app = app;
      }

      public String getMod() {
        return mod;
      }

      public void setMod(String mod) {
        this.mod = mod;
      }

      public String getRow_id() {
        return row_id;
      }

      public void setRow_id(String row_id) {
        this.row_id = row_id;
      }

      public String getParse() {
        return parse;
      }

      public void setParse(String parse) {
        this.parse = parse;
      }

      public String getContent() {
        return content;
      }

      public void setContent(String content) {
        this.content = content;
      }

      public String getCreate_time() {
        return create_time;
      }

      public void setCreate_time(String create_time) {
        this.create_time = create_time;
      }

      public String getPid() {
        return pid;
      }

      public void setPid(String pid) {
        this.pid = pid;
      }

      public String getStatus() {
        return status;
      }

      public void setStatus(String status) {
        this.status = status;
      }

      public String getIp() {
        return ip;
      }

      public void setIp(String ip) {
        this.ip = ip;
      }

      public String getArea() {
        return area;
      }

      public void setArea(String area) {
        this.area = area;
      }
    }
  }
}
