/*
 * 文件名: HotPostModel
 * 版    权：  Copyright Hengrtech Tech. Co. Ltd. All Rights Reserved.
 * 描    述: [该类的简要描述]
 * 创建人: zhaozeyang
 * 创建时间:16/8/9
 * 
 * 修改人：
 * 修改时间:
 * 修改内容：[修改内容]
 */
package com.thinksky.net.rpc.model;

import java.io.Serializable;
import java.util.List;

/**
 * [一句话功能简述]<BR>
 * [功能详细描述]
 *
 * @author zhaozeyang
 * @version [Taobei Client V20160411, 16/8/9]
 */
public class PostModel extends BaseModel implements Serializable {


  /**
   * id : 65
   * uid : 317
   * group_id : 18
   * title : 咯无开空调
   * parse : 0
   * content : 酒肉朋友婆娘\n
   * create_time : 07月08日 16:19
   * update_time : 07月08日 16:19
   * status : 1
   * last_reply_time :
   * view_count : 34
   * reply_count : 6
   * is_top : 0
   * cate_id : 0
   * summary : 酒肉朋友婆娘
   * cover : /Uploads/Picture/2016-07-08/577f61ffef1ad.jpg
   * imgList : ["/Uploads/Picture/2016-07-08/577f61ffef1ad.jpg"]
   * user : {"avatar32":"Public/images/default_avatar_32_32.jpg",
   * "avatar64":"Public/images/default_avatar_64_64.jpg",
   * "avatar128":"Public/images/default_avatar_128_128.jpg",
   * "avatar256":"Public/images/default_avatar_256_256.jpg",
   * "avatar512":"Public/images/default_avatar_512_512.jpg","real_nickname":null}
   * supportCount : 3
   * groupMemberCount : 9
   * is_support : 0
   * is_collection : 0
   * posts_rply : [{"id":"180","uid":"379","post_id":"65","parse":"0","content":"打打闹闹都能",
   * "create_time":"1469494650","update_time":"1469494650","status":"1",
   * "rp_user":{"avatar32":"Public/images/default_avatar_32_32.jpg",
   * "avatar64":"Public/images/default_avatar_64_64.jpg",
   * "avatar128":"Public/images/default_avatar_128_128.jpg",
   * "avatar256":"Public/images/default_avatar_256_256.jpg",
   * "avatar512":"Public/images/default_avatar_512_512.jpg","uid":"379","nickname":"天行健",
   * "username":"","real_nickname":"天行健"}},{"id":"173","uid":"325","post_id":"65","parse":"0",
   * "content":"1234","create_time":"1469435151","update_time":"1469435151","status":"1",
   * "rp_user":{"avatar32":"Uploads/Avatar/2016-08-02/57a04e6b58c0d_32_32.jpg",
   * "avatar64":"Uploads/Avatar/2016-08-02/57a04e6b58c0d_64_64.jpg",
   * "avatar128":"Uploads/Avatar/2016-08-02/57a04e6b58c0d_128_128.jpg",
   * "avatar256":"Uploads/Avatar/2016-08-02/57a04e6b58c0d_256_256.jpg",
   * "avatar512":"Uploads/Avatar/2016-08-02/57a04e6b58c0d_512_512.jpg","uid":"325",
   * "nickname":"昨日重现","username":"","real_nickname":"昨日重现"}},{"id":"163","uid":"373",
   * "post_id":"65","parse":"0","content":"text","create_time":"1469174286",
   * "update_time":"1469174286","status":"1",
   * "rp_user":{"avatar32":"Uploads/Avatar/2016-07-19/578d91a883242_32_32.png",
   * "avatar64":"Uploads/Avatar/2016-07-19/578d91a883242_64_64.png",
   * "avatar128":"Uploads/Avatar/2016-07-19/578d91a883242_128_128.png",
   * "avatar256":"Uploads/Avatar/2016-07-19/578d91a883242_256_256.png",
   * "avatar512":"Uploads/Avatar/2016-07-19/578d91a883242_512_512.png","real_nickname":null}},
   * {"id":"159","uid":"325","post_id":"65","parse":"0","content":"3","create_time":"1469082169",
   * "update_time":"1469082169","status":"1",
   * "rp_user":{"avatar32":"Uploads/Avatar/2016-08-02/57a04e6b58c0d_32_32.jpg",
   * "avatar64":"Uploads/Avatar/2016-08-02/57a04e6b58c0d_64_64.jpg",
   * "avatar128":"Uploads/Avatar/2016-08-02/57a04e6b58c0d_128_128.jpg",
   * "avatar256":"Uploads/Avatar/2016-08-02/57a04e6b58c0d_256_256.jpg",
   * "avatar512":"Uploads/Avatar/2016-08-02/57a04e6b58c0d_512_512.jpg","uid":"325",
   * "nickname":"昨日重现","username":"","real_nickname":"昨日重现"}},{"id":"148","uid":"373",
   * "post_id":"65","parse":"0","content":"high","create_time":"1469065758",
   * "update_time":"1469065758","status":"1",
   * "rp_user":{"avatar32":"Uploads/Avatar/2016-07-19/578d91a883242_32_32.png",
   * "avatar64":"Uploads/Avatar/2016-07-19/578d91a883242_64_64.png",
   * "avatar128":"Uploads/Avatar/2016-07-19/578d91a883242_128_128.png",
   * "avatar256":"Uploads/Avatar/2016-07-19/578d91a883242_256_256.png",
   * "avatar512":"Uploads/Avatar/2016-07-19/578d91a883242_512_512.png","real_nickname":null}},
   * {"id":"138","uid":"373","post_id":"65","parse":"0","content":"？？？",
   * "create_time":"1468828768","update_time":"1468828768","status":"1",
   * "rp_user":{"avatar32":"Uploads/Avatar/2016-07-19/578d91a883242_32_32.png",
   * "avatar64":"Uploads/Avatar/2016-07-19/578d91a883242_64_64.png",
   * "avatar128":"Uploads/Avatar/2016-07-19/578d91a883242_128_128.png",
   * "avatar256":"Uploads/Avatar/2016-07-19/578d91a883242_256_256.png",
   * "avatar512":"Uploads/Avatar/2016-07-19/578d91a883242_512_512.png","real_nickname":null}}]
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
    /**
     * avatar32 : Public/images/default_avatar_32_32.jpg
     * avatar64 : Public/images/default_avatar_64_64.jpg
     * avatar128 : Public/images/default_avatar_128_128.jpg
     * avatar256 : Public/images/default_avatar_256_256.jpg
     * avatar512 : Public/images/default_avatar_512_512.jpg
     * real_nickname : null
     */

    private UserBean user;
    private String supportCount;
    private String groupMemberCount;
    private String is_support;
    private String is_collection;
    private List<String> imgList;
    /**
     * id : 180
     * uid : 379
     * post_id : 65
     * parse : 0
     * content : 打打闹闹都能
     * create_time : 1469494650
     * update_time : 1469494650
     * status : 1
     * rp_user : {"avatar32":"Public/images/default_avatar_32_32.jpg",
     * "avatar64":"Public/images/default_avatar_64_64.jpg",
     * "avatar128":"Public/images/default_avatar_128_128.jpg",
     * "avatar256":"Public/images/default_avatar_256_256.jpg",
     * "avatar512":"Public/images/default_avatar_512_512.jpg","uid":"379","nickname":"天行健",
     * "username":"","real_nickname":"天行健"}
     */

    private List<PostsRplyBean> posts_rply;

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

    public UserBean getUser() {
      return user;
    }

    public void setUser(UserBean user) {
      this.user = user;
    }

    public String getSupportCount() {
      return supportCount;
    }

    public void setSupportCount(String supportCount) {
      this.supportCount = supportCount;
    }

    public String getGroupMemberCount() {
      return groupMemberCount;
    }

    public void setGroupMemberCount(String groupMemberCount) {
      this.groupMemberCount = groupMemberCount;
    }

    public String getIs_support() {
      return is_support;
    }

    public void setIs_support(String is_support) {
      this.is_support = is_support;
    }

    public String getIs_collection() {
      return is_collection;
    }

    public void setIs_collection(String is_collection) {
      this.is_collection = is_collection;
    }

    public List<String> getImgList() {
      return imgList;
    }

    public void setImgList(List<String> imgList) {
      this.imgList = imgList;
    }

    public List<PostsRplyBean> getPosts_rply() {
      return posts_rply;
    }

    public void setPosts_rply(List<PostsRplyBean> posts_rply) {
      this.posts_rply = posts_rply;
    }

    public static class UserBean {
      private String avatar32;
      private String avatar64;
      private String avatar128;
      private String avatar256;
      private String avatar512;
      private Object real_nickname;

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

      public Object getReal_nickname() {
        return real_nickname;
      }

      public void setReal_nickname(Object real_nickname) {
        this.real_nickname = real_nickname;
      }
    }

    public static class PostsRplyBean {
      private String id;
      private String uid;
      private String post_id;
      private String parse;
      private String content;
      private String create_time;
      private String update_time;
      private String status;
      /**
       * avatar32 : Public/images/default_avatar_32_32.jpg
       * avatar64 : Public/images/default_avatar_64_64.jpg
       * avatar128 : Public/images/default_avatar_128_128.jpg
       * avatar256 : Public/images/default_avatar_256_256.jpg
       * avatar512 : Public/images/default_avatar_512_512.jpg
       * uid : 379
       * nickname : 天行健
       * username :
       * real_nickname : 天行健
       */

      private RpUserBean rp_user;

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

      public String getPost_id() {
        return post_id;
      }

      public void setPost_id(String post_id) {
        this.post_id = post_id;
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

      public RpUserBean getRp_user() {
        return rp_user;
      }

      public void setRp_user(RpUserBean rp_user) {
        this.rp_user = rp_user;
      }

      public static class RpUserBean {
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
