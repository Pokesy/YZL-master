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
public class HotPostModel extends BaseModel implements Serializable {

  /**
   * id : 62
   * uid : 214
   * group_id : 14
   * title : 我的小盘子
   * parse : 0
   * content :
   * create_time : 05月27日 11:40
   * update_time : 05月27日 11:40
   * status : 1
   * last_reply_time :
   * view_count : 78
   * reply_count : 31
   * is_top : 0
   * cate_id : 0
   * summary :
   * cover : /Uploads/Picture/2016-05-27/5747c18186249.png
   * imgList : ["/Uploads/Picture/2016-05-27/5747c18186249.png"]
   * user : {"avatar32":"Public/images/default_avatar_32_32.jpg",
   * "avatar64":"Public/images/default_avatar_64_64.jpg",
   * "avatar128":"Public/images/default_avatar_128_128.jpg",
   * "avatar256":"Public/images/default_avatar_256_256.jpg",
   * "avatar512":"Public/images/default_avatar_512_512.jpg","uid":"214","nickname":"13893000015",
   * "signature":"","username":"","real_nickname":"13893000015"}
   * supportCount : 11
   * is_support : 0
   * posts_rply : [{"id":"181","uid":"325","post_id":"62","parse":"0","content":"24",
   * "create_time":"1469496264","update_time":"1469496264","status":"1",
   * "rp_user":{"avatar32":"Uploads/Avatar/2016-08-02/57a04e6b58c0d_32_32.jpg",
   * "avatar64":"Uploads/Avatar/2016-08-02/57a04e6b58c0d_64_64.jpg",
   * "avatar128":"Uploads/Avatar/2016-08-02/57a04e6b58c0d_128_128.jpg",
   * "avatar256":"Uploads/Avatar/2016-08-02/57a04e6b58c0d_256_256.jpg",
   * "avatar512":"Uploads/Avatar/2016-08-02/57a04e6b58c0d_512_512.jpg","uid":"325",
   * "nickname":"昨日重现","username":"","real_nickname":"昨日重现"}},{"id":"182","uid":"325",
   * "post_id":"62","parse":"0","content":"25","create_time":"1469496275",
   * "update_time":"1469496275","status":"1",
   * "rp_user":{"avatar32":"Uploads/Avatar/2016-08-02/57a04e6b58c0d_32_32.jpg",
   * "avatar64":"Uploads/Avatar/2016-08-02/57a04e6b58c0d_64_64.jpg",
   * "avatar128":"Uploads/Avatar/2016-08-02/57a04e6b58c0d_128_128.jpg",
   * "avatar256":"Uploads/Avatar/2016-08-02/57a04e6b58c0d_256_256.jpg",
   * "avatar512":"Uploads/Avatar/2016-08-02/57a04e6b58c0d_512_512.jpg","uid":"325",
   * "nickname":"昨日重现","username":"","real_nickname":"昨日重现"}},{"id":"183","uid":"427",
   * "post_id":"62","parse":"0","content":"26","create_time":"1469674692",
   * "update_time":"1469674692","status":"1",
   * "rp_user":{"avatar32":"Uploads/Avatar/2016-08-02/57a04b6e1c1a4_32_32.jpg",
   * "avatar64":"Uploads/Avatar/2016-08-02/57a04b6e1c1a4_64_64.jpg",
   * "avatar128":"Uploads/Avatar/2016-08-02/57a04b6e1c1a4_128_128.jpg",
   * "avatar256":"Uploads/Avatar/2016-08-02/57a04b6e1c1a4_256_256.jpg",
   * "avatar512":"Uploads/Avatar/2016-08-02/57a04b6e1c1a4_512_512.jpg","uid":"427",
   * "nickname":"唐德as","username":"","real_nickname":"唐德as"}},{"id":"184","uid":"427",
   * "post_id":"62","parse":"0","content":"27","create_time":"1469674731",
   * "update_time":"1469674731","status":"1",
   * "rp_user":{"avatar32":"Uploads/Avatar/2016-08-02/57a04b6e1c1a4_32_32.jpg",
   * "avatar64":"Uploads/Avatar/2016-08-02/57a04b6e1c1a4_64_64.jpg",
   * "avatar128":"Uploads/Avatar/2016-08-02/57a04b6e1c1a4_128_128.jpg",
   * "avatar256":"Uploads/Avatar/2016-08-02/57a04b6e1c1a4_256_256.jpg",
   * "avatar512":"Uploads/Avatar/2016-08-02/57a04b6e1c1a4_512_512.jpg","uid":"427",
   * "nickname":"唐德as","username":"","real_nickname":"唐德as"}},{"id":"186","uid":"373",
   * "post_id":"62","parse":"0","content":"28","create_time":"1469692753",
   * "update_time":"1469692753","status":"1",
   * "rp_user":{"avatar32":"Uploads/Avatar/2016-07-19/578d91a883242_32_32.png",
   * "avatar64":"Uploads/Avatar/2016-07-19/578d91a883242_64_64.png",
   * "avatar128":"Uploads/Avatar/2016-07-19/578d91a883242_128_128.png",
   * "avatar256":"Uploads/Avatar/2016-07-19/578d91a883242_256_256.png",
   * "avatar512":"Uploads/Avatar/2016-07-19/578d91a883242_512_512.png","real_nickname":null}},
   * {"id":"190","uid":"373","post_id":"62","parse":"0","content":"在于",
   * "create_time":"1469779083","update_time":"1469779083","status":"1",
   * "rp_user":{"avatar32":"Uploads/Avatar/2016-07-19/578d91a883242_32_32.png",
   * "avatar64":"Uploads/Avatar/2016-07-19/578d91a883242_64_64.png",
   * "avatar128":"Uploads/Avatar/2016-07-19/578d91a883242_128_128.png",
   * "avatar256":"Uploads/Avatar/2016-07-19/578d91a883242_256_256.png",
   * "avatar512":"Uploads/Avatar/2016-07-19/578d91a883242_512_512.png","real_nickname":null}},
   * {"id":"193","uid":"434","post_id":"62","parse":"0","content":"Dasqwe",
   * "create_time":"1470040926","update_time":"1470040926","status":"1",
   * "rp_user":{"avatar32":"Public/images/default_avatar_32_32.jpg",
   * "avatar64":"Public/images/default_avatar_64_64.jpg",
   * "avatar128":"Public/images/default_avatar_128_128.jpg",
   * "avatar256":"Public/images/default_avatar_256_256.jpg",
   * "avatar512":"Public/images/default_avatar_512_512.jpg","uid":"434","nickname":"dakskj",
   * "username":"","real_nickname":"dakskj"}},{"id":"194","uid":"434","post_id":"62","parse":"0",
   * "content":"Sdqwe","create_time":"1470041006","update_time":"1470041006","status":"1",
   * "rp_user":{"avatar32":"Public/images/default_avatar_32_32.jpg",
   * "avatar64":"Public/images/default_avatar_64_64.jpg",
   * "avatar128":"Public/images/default_avatar_128_128.jpg",
   * "avatar256":"Public/images/default_avatar_256_256.jpg",
   * "avatar512":"Public/images/default_avatar_512_512.jpg","uid":"434","nickname":"dakskj",
   * "username":"","real_nickname":"dakskj"}},{"id":"178","uid":"325","post_id":"62","parse":"0",
   * "content":"1234567","create_time":"1469435239","update_time":"1469435239","status":"1",
   * "rp_user":{"avatar32":"Uploads/Avatar/2016-08-02/57a04e6b58c0d_32_32.jpg",
   * "avatar64":"Uploads/Avatar/2016-08-02/57a04e6b58c0d_64_64.jpg",
   * "avatar128":"Uploads/Avatar/2016-08-02/57a04e6b58c0d_128_128.jpg",
   * "avatar256":"Uploads/Avatar/2016-08-02/57a04e6b58c0d_256_256.jpg",
   * "avatar512":"Uploads/Avatar/2016-08-02/57a04e6b58c0d_512_512.jpg","uid":"325",
   * "nickname":"昨日重现","username":"","real_nickname":"昨日重现"}},{"id":"179","uid":"350",
   * "post_id":"62","parse":"0","content":"信息","create_time":"1469438796",
   * "update_time":"1469438796","status":"1",
   * "rp_user":{"avatar32":"Uploads/Avatar/2016-07-15/578899dd93a64_32_32.png",
   * "avatar64":"Uploads/Avatar/2016-07-15/578899dd93a64_64_64.png",
   * "avatar128":"Uploads/Avatar/2016-07-15/578899dd93a64_128_128.png",
   * "avatar256":"Uploads/Avatar/2016-07-15/578899dd93a64_256_256.png",
   * "avatar512":"Uploads/Avatar/2016-07-15/578899dd93a64_512_512.png","uid":"350",
   * "nickname":"婕儿","username":"","real_nickname":"婕儿"}}]
   */

  private List<HotPostBean> list;

  public List<HotPostBean> getList() {
    return list;
  }

  public void setList(List<HotPostBean> list) {
    this.list = list;
  }

  public static class HotPostBean implements Serializable {
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
     * uid : 214
     * nickname : 13893000015
     * signature :
     * username :
     * real_nickname : 13893000015
     */

    private UserInfoModel user;
    private String supportCount;
    private String is_support;
    private List<String> imgList;
    /**
     * id : 181
     * uid : 325
     * post_id : 62
     * parse : 0
     * content : 24
     * create_time : 1469496264
     * update_time : 1469496264
     * status : 1
     * rp_user : {"avatar32":"Uploads/Avatar/2016-08-02/57a04e6b58c0d_32_32.jpg",
     * "avatar64":"Uploads/Avatar/2016-08-02/57a04e6b58c0d_64_64.jpg",
     * "avatar128":"Uploads/Avatar/2016-08-02/57a04e6b58c0d_128_128.jpg",
     * "avatar256":"Uploads/Avatar/2016-08-02/57a04e6b58c0d_256_256.jpg",
     * "avatar512":"Uploads/Avatar/2016-08-02/57a04e6b58c0d_512_512.jpg","uid":"325",
     * "nickname":"昨日重现","username":"","real_nickname":"昨日重现"}
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

    public UserInfoModel getUser() {
      return user;
    }

    public void setUser(UserInfoModel user) {
      this.user = user;
    }

    public String getSupportCount() {
      return supportCount;
    }

    public void setSupportCount(String supportCount) {
      this.supportCount = supportCount;
    }

    public String getIs_support() {
      return is_support;
    }

    public void setIs_support(String is_support) {
      this.is_support = is_support;
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

    public static class PostsRplyBean implements Serializable{
      private String id;
      private String uid;
      private String post_id;
      private String parse;
      private String content;
      private String create_time;
      private String update_time;
      private String status;
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

      public static class RpUserBean implements Serializable{
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
