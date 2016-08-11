/*
 * 文件名: GroupInfoModel
 * 版    权：  Copyright Hengrtech Tech. Co. Ltd. All Rights Reserved.
 * 描    述: [该类的简要描述]
 * 创建人: zhaozeyang
 * 创建时间:16/8/11
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
 * @version [Taobei Client V20160411, 16/8/11]
 */
public class GroupInfoModel extends BaseModel {


  /**
   * id : 14
   * uid : 1
   * title : 魟鱼
   * create_time : 05月26日 16:16
   * post_count : 7
   * status : 1
   * allow_user_group :
   * sort : 0
   * logo : Uploads/Picture/2016-05-27/5747a16aeb02e_160_180.png
   * background :
   * type_id : 3
   * detail : 魟鱼爱好者的天堂
   * type : 0
   * activity : 11
   * member_count : 25
   * member_alias : 小魟
   * is_join : 1
   * user : {"avatar32":"Uploads/Avatar/2016-04-25/571d78270d2f2_32_32.png",
   * "avatar64":"Uploads/Avatar/2016-04-25/571d78270d2f2_64_64.png",
   * "avatar128":"Uploads/Avatar/2016-04-25/571d78270d2f2_128_128.png",
   * "avatar256":"Uploads/Avatar/2016-04-25/571d78270d2f2_256_256.png",
   * "avatar512":"Uploads/Avatar/2016-04-25/571d78270d2f2_512_512.png","uid":"1",
   * "username":"admin","nickname":"鱼小乐","real_nickname":"鱼小乐"}
   * menmberCount : 25
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
    private String title;
    private String create_time;
    private String post_count;
    private String status;
    private String allow_user_group;
    private String sort;
    private String logo;
    private String background;
    private String type_id;
    private String detail;
    private String type;
    private String activity;
    private String member_count;
    private String member_alias;
    private String is_join;
    /**
     * avatar32 : Uploads/Avatar/2016-04-25/571d78270d2f2_32_32.png
     * avatar64 : Uploads/Avatar/2016-04-25/571d78270d2f2_64_64.png
     * avatar128 : Uploads/Avatar/2016-04-25/571d78270d2f2_128_128.png
     * avatar256 : Uploads/Avatar/2016-04-25/571d78270d2f2_256_256.png
     * avatar512 : Uploads/Avatar/2016-04-25/571d78270d2f2_512_512.png
     * uid : 1
     * username : admin
     * nickname : 鱼小乐
     * real_nickname : 鱼小乐
     */

    private UserBean user;
    private String menmberCount;

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

    public String getPost_count() {
      return post_count;
    }

    public void setPost_count(String post_count) {
      this.post_count = post_count;
    }

    public String getStatus() {
      return status;
    }

    public void setStatus(String status) {
      this.status = status;
    }

    public String getAllow_user_group() {
      return allow_user_group;
    }

    public void setAllow_user_group(String allow_user_group) {
      this.allow_user_group = allow_user_group;
    }

    public String getSort() {
      return sort;
    }

    public void setSort(String sort) {
      this.sort = sort;
    }

    public String getLogo() {
      return logo;
    }

    public void setLogo(String logo) {
      this.logo = logo;
    }

    public String getBackground() {
      return background;
    }

    public void setBackground(String background) {
      this.background = background;
    }

    public String getType_id() {
      return type_id;
    }

    public void setType_id(String type_id) {
      this.type_id = type_id;
    }

    public String getDetail() {
      return detail;
    }

    public void setDetail(String detail) {
      this.detail = detail;
    }

    public String getType() {
      return type;
    }

    public void setType(String type) {
      this.type = type;
    }

    public String getActivity() {
      return activity;
    }

    public void setActivity(String activity) {
      this.activity = activity;
    }

    public String getMember_count() {
      return member_count;
    }

    public void setMember_count(String member_count) {
      this.member_count = member_count;
    }

    public String getMember_alias() {
      return member_alias;
    }

    public void setMember_alias(String member_alias) {
      this.member_alias = member_alias;
    }

    public String getIs_join() {
      return is_join;
    }

    public void setIs_join(String is_join) {
      this.is_join = is_join;
    }

    public UserBean getUser() {
      return user;
    }

    public void setUser(UserBean user) {
      this.user = user;
    }

    public String getMenmberCount() {
      return menmberCount;
    }

    public void setMenmberCount(String menmberCount) {
      this.menmberCount = menmberCount;
    }

    public static class UserBean {
      private String avatar32;
      private String avatar64;
      private String avatar128;
      private String avatar256;
      private String avatar512;
      private String uid;
      private String username;
      private String nickname;
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

      public String getReal_nickname() {
        return real_nickname;
      }

      public void setReal_nickname(String real_nickname) {
        this.real_nickname = real_nickname;
      }
    }
  }
}
