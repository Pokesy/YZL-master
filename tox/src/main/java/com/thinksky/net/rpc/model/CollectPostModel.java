/*
 * 文件名: CollectQuestionModel
 * 版    权：  Copyright Hengrtech Tech. Co. Ltd. All Rights Reserved.
 * 描    述: [该类的简要描述]
 * 创建人: zhaozeyang
 * 创建时间:16/8/10
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
 * @version [Taobei Client V20160411, 16/8/10]
 */
public class CollectPostModel extends BaseModel {

  /**
   * id : 62
   * uid : 214
   * group_id : 14
   * title : 我的小盘子
   * parse : 0
   * content : <p><-IMG#0-></p>
   * create_time : 1464320407
   * update_time : 1464320407
   * status : 1
   * last_reply_time : 0
   * view_count : 79
   * reply_count : 32
   * is_top : 0
   * cate_id : 0
   * summary :
   * cover : /Uploads/Picture/2016-05-27/5747c18186249.png
   * imgList : [{"pos":"<-IMG#0->","src":"/Uploads/Picture/2016-05-27/5747c18186249.png"}]
   * contentText :
   * user : {"avatar32":"Public/images/default_avatar_32_32.jpg",
   * "avatar64":"Public/images/default_avatar_64_64.jpg",
   * "avatar128":"Public/images/default_avatar_128_128.jpg",
   * "avatar256":"Public/images/default_avatar_256_256.jpg",
   * "avatar512":"Public/images/default_avatar_512_512.jpg","uid":"214","nickname":"13893000015",
   * "username":"","real_nickname":"13893000015"}
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
    private String uid;
    private String group_id;
    private String title;
    private String parse;
    private String content;
    private String create_time;
    private String update_time;
    private String status;
    private String last_reply_time;
    private String view_count;
    private String reply_count;
    private String is_top;
    private String cate_id;
    private String summary;
    private String cover;
    private String contentText;
    /**
     * avatar32 : Public/images/default_avatar_32_32.jpg
     * avatar64 : Public/images/default_avatar_64_64.jpg
     * avatar128 : Public/images/default_avatar_128_128.jpg
     * avatar256 : Public/images/default_avatar_256_256.jpg
     * avatar512 : Public/images/default_avatar_512_512.jpg
     * uid : 214
     * nickname : 13893000015
     * username :
     * real_nickname : 13893000015
     */

    private UserBean user;
    /**
     * pos : <-IMG#0->
     * src : /Uploads/Picture/2016-05-27/5747c18186249.png
     */

    private List<ImgListBean> imgList;

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

    public String getGroup_id() {
      return group_id;
    }

    public void setGroup_id(String group_id) {
      this.group_id = group_id;
    }

    public String getTitle() {
      return title;
    }

    public void setTitle(String title) {
      this.title = title;
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

    public String getLast_reply_time() {
      return last_reply_time;
    }

    public void setLast_reply_time(String last_reply_time) {
      this.last_reply_time = last_reply_time;
    }

    public String getView_count() {
      return view_count;
    }

    public void setView_count(String view_count) {
      this.view_count = view_count;
    }

    public String getReply_count() {
      return reply_count;
    }

    public void setReply_count(String reply_count) {
      this.reply_count = reply_count;
    }

    public String getIs_top() {
      return is_top;
    }

    public void setIs_top(String is_top) {
      this.is_top = is_top;
    }

    public String getCate_id() {
      return cate_id;
    }

    public void setCate_id(String cate_id) {
      this.cate_id = cate_id;
    }

    public String getSummary() {
      return summary;
    }

    public void setSummary(String summary) {
      this.summary = summary;
    }

    public String getCover() {
      return cover;
    }

    public void setCover(String cover) {
      this.cover = cover;
    }

    public String getContentText() {
      return contentText;
    }

    public void setContentText(String contentText) {
      this.contentText = contentText;
    }

    public UserBean getUser() {
      return user;
    }

    public void setUser(UserBean user) {
      this.user = user;
    }

    public List<ImgListBean> getImgList() {
      return imgList;
    }

    public void setImgList(List<ImgListBean> imgList) {
      this.imgList = imgList;
    }

    public static class UserBean {
      private String avatar32;
      private String avatar64;
      private String avatar128;
      private String avatar256;
      private String avatar512;
      private String uid;
      private String nickname;
      private String username;
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

      public String getNickname() {
        return nickname;
      }

      public void setNickname(String nickname) {
        this.nickname = nickname;
      }

      public String getUsername() {
        return username;
      }

      public void setUsername(String username) {
        this.username = username;
      }

      public String getReal_nickname() {
        return real_nickname;
      }

      public void setReal_nickname(String real_nickname) {
        this.real_nickname = real_nickname;
      }
    }

    public static class ImgListBean {
      private String pos;
      private String src;

      public String getPos() {
        return pos;
      }

      public void setPos(String pos) {
        this.pos = pos;
      }

      public String getSrc() {
        return src;
      }

      public void setSrc(String src) {
        this.src = src;
      }
    }
  }
}
