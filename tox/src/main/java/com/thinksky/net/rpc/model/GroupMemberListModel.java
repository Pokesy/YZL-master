/*
 * 文件名: GroupMemberListModel
 * 版    权：  Copyright Hengrtech Tech. Co. Ltd. All Rights Reserved.
 * 描    述: [该类的简要描述]
 * 创建人: zhaozeyang
 * 创建时间:16/8/23
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
 * @version [Taobei Client V20160411, 16/8/23]
 */
public class GroupMemberListModel extends BaseModel {

  /**
   * id : 254
   * group_id : 24
   * uid : 325
   * status : 1
   * create_time : 0
   * update_time : 0
   * activity : 0
   * last_view : 0
   * position : 3
   * user : {"avatar32":"Uploads/Avatar/2016-08-02/57a04e6b58c0d_32_32.jpg",
   * "avatar64":"Uploads/Avatar/2016-08-02/57a04e6b58c0d_64_64.jpg",
   * "avatar128":"Uploads/Avatar/2016-08-02/57a04e6b58c0d_128_128.jpg",
   * "avatar256":"Uploads/Avatar/2016-08-02/57a04e6b58c0d_256_256.jpg",
   * "avatar512":"Uploads/Avatar/2016-08-02/57a04e6b58c0d_512_512.jpg","uid":"325",
   * "nickname":"昨日重现","fans":"2","following":"1","weibocount":"1","title":"Lv1 无名小螺",
   * "score2":"147","real_nickname":"昨日重现"}
   * isCreator : 1
   * isfollow : 0
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
    private String group_id;
    private String uid;
    private String status;
    private String create_time;
    private String update_time;
    private String activity;
    private String last_view;
    private String position;
    /**
     * avatar32 : Uploads/Avatar/2016-08-02/57a04e6b58c0d_32_32.jpg
     * avatar64 : Uploads/Avatar/2016-08-02/57a04e6b58c0d_64_64.jpg
     * avatar128 : Uploads/Avatar/2016-08-02/57a04e6b58c0d_128_128.jpg
     * avatar256 : Uploads/Avatar/2016-08-02/57a04e6b58c0d_256_256.jpg
     * avatar512 : Uploads/Avatar/2016-08-02/57a04e6b58c0d_512_512.jpg
     * uid : 325
     * nickname : 昨日重现
     * fans : 2
     * following : 1
     * weibocount : 1
     * title : Lv1 无名小螺
     * score2 : 147
     * real_nickname : 昨日重现
     */

    private UserBean user;
    private String isCreator;
    private String isfollow;

    public String getId() {
      return id;
    }

    public void setId(String id) {
      this.id = id;
    }

    public String getGroup_id() {
      return group_id;
    }

    public void setGroup_id(String group_id) {
      this.group_id = group_id;
    }

    public String getUid() {
      return uid;
    }

    public void setUid(String uid) {
      this.uid = uid;
    }

    public String getStatus() {
      return status;
    }

    public void setStatus(String status) {
      this.status = status;
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

    public String getActivity() {
      return activity;
    }

    public void setActivity(String activity) {
      this.activity = activity;
    }

    public String getLast_view() {
      return last_view;
    }

    public void setLast_view(String last_view) {
      this.last_view = last_view;
    }

    public String getPosition() {
      return position;
    }

    public void setPosition(String position) {
      this.position = position;
    }

    public UserBean getUser() {
      return user;
    }

    public void setUser(UserBean user) {
      this.user = user;
    }

    public String getIsCreator() {
      return isCreator;
    }

    public void setIsCreator(String isCreator) {
      this.isCreator = isCreator;
    }

    public String getIsfollow() {
      return isfollow;
    }

    public void setIsfollow(String isfollow) {
      this.isfollow = isfollow;
    }

    public static class UserBean {
      private String avatar32;
      private String avatar64;
      private String avatar128;
      private String avatar256;
      private String avatar512;
      private String uid;
      private String nickname;
      private String fans;
      private String following;
      private String weibocount;
      private String title;
      private String score2;
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

      public String getFans() {
        return fans;
      }

      public void setFans(String fans) {
        this.fans = fans;
      }

      public String getFollowing() {
        return following;
      }

      public void setFollowing(String following) {
        this.following = following;
      }

      public String getWeibocount() {
        return weibocount;
      }

      public void setWeibocount(String weibocount) {
        this.weibocount = weibocount;
      }

      public String getTitle() {
        return title;
      }

      public void setTitle(String title) {
        this.title = title;
      }

      public String getScore2() {
        return score2;
      }

      public void setScore2(String score2) {
        this.score2 = score2;
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
