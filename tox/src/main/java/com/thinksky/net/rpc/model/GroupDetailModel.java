/*
 * 文件名: GroupDetailModel
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
public class GroupDetailModel  extends BaseModel{

  /**
   * id : 24
   * uid : 325
   * title : 辅导费
   * create_time : 08月22日 17:17
   * post_count : 0
   * status : 1
   * allow_user_group :
   * sort : 0
   * logo : /Uploads/Picture/2016-08-22/57bac3124e155.jpg
   * background : false
   * type_id : 3
   * detail : 与
   * type : 0
   * activity : 0
   * member_count : 1
   * member_alias : 成员
   * notice : 0
   * is_join : 1
   * user : {"avatar32":"Uploads/Avatar/2016-08-02/57a04e6b58c0d_32_32.jpg",
   * "avatar64":"Uploads/Avatar/2016-08-02/57a04e6b58c0d_64_64.jpg",
   * "avatar128":"Uploads/Avatar/2016-08-02/57a04e6b58c0d_128_128.jpg",
   * "avatar256":"Uploads/Avatar/2016-08-02/57a04e6b58c0d_256_256.jpg",
   * "avatar512":"Uploads/Avatar/2016-08-02/57a04e6b58c0d_512_512.jpg","uid":"325",
   * "nickname":"昨日重现","username":"","real_nickname":"昨日重现"}
   * GroupMenmber : [{"id":"254","group_id":"24","uid":"325","status":"1","create_time":"0",
   * "update_time":"0","activity":"0","last_view":"0","position":"3",
   * "user":{"avatar32":"Uploads/Avatar/2016-08-02/57a04e6b58c0d_32_32.jpg",
   * "avatar64":"Uploads/Avatar/2016-08-02/57a04e6b58c0d_64_64.jpg",
   * "avatar128":"Uploads/Avatar/2016-08-02/57a04e6b58c0d_128_128.jpg",
   * "avatar256":"Uploads/Avatar/2016-08-02/57a04e6b58c0d_256_256.jpg",
   * "avatar512":"Uploads/Avatar/2016-08-02/57a04e6b58c0d_512_512.jpg","uid":"325",
   * "nickname":"昨日重现","username":"","real_nickname":"昨日重现"},"isCreator":"1"}]
   * GroupCreator : {"uid":"325"}
   */

  private ListBean list;

  public ListBean getList() {
    return list;
  }

  public void setList(ListBean list) {
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
    private boolean background;
    private String type_id;
    private String detail;
    private String type;
    private String activity;
    private String member_count;
    private String member_alias;
    private String notice;
    private String is_join;
    /**
     * avatar32 : Uploads/Avatar/2016-08-02/57a04e6b58c0d_32_32.jpg
     * avatar64 : Uploads/Avatar/2016-08-02/57a04e6b58c0d_64_64.jpg
     * avatar128 : Uploads/Avatar/2016-08-02/57a04e6b58c0d_128_128.jpg
     * avatar256 : Uploads/Avatar/2016-08-02/57a04e6b58c0d_256_256.jpg
     * avatar512 : Uploads/Avatar/2016-08-02/57a04e6b58c0d_512_512.jpg
     * uid : 325
     * nickname : 昨日重现
     * username :
     * real_nickname : 昨日重现
     */

    private UserBean user;
    /**
     * uid : 325
     */

    private GroupCreatorBean GroupCreator;
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
     * "nickname":"昨日重现","username":"","real_nickname":"昨日重现"}
     * isCreator : 1
     */

    private List<GroupMenmberBean> GroupMenmber;

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

    public boolean isBackground() {
      return background;
    }

    public void setBackground(boolean background) {
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

    public String getNotice() {
      return notice;
    }

    public void setNotice(String notice) {
      this.notice = notice;
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

    public GroupCreatorBean getGroupCreator() {
      return GroupCreator;
    }

    public void setGroupCreator(GroupCreatorBean GroupCreator) {
      this.GroupCreator = GroupCreator;
    }

    public List<GroupMenmberBean> getGroupMenmber() {
      return GroupMenmber;
    }

    public void setGroupMenmber(List<GroupMenmberBean> GroupMenmber) {
      this.GroupMenmber = GroupMenmber;
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

    public static class GroupCreatorBean {
      private String uid;

      public String getUid() {
        return uid;
      }

      public void setUid(String uid) {
        this.uid = uid;
      }
    }

    public static class GroupMenmberBean {
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
       * username :
       * real_nickname : 昨日重现
       */

      private UserBean user;
      private String isCreator;

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
    }
  }
}
