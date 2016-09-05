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
   * id : 70
   * uid : 325
   * group_id : 15
   * title : 龙鱼品种
   * parse : 0
   * content : 龙鱼有几类，哪种最好\n
   * create_time : 07月19日 13:40
   * update_time : 07月19日 13:40
   * status : 1
   * last_reply_time :
   * view_count : 62
   * reply_count : 10
   * is_top : 0
   * cate_id : 0
   * summary : 龙鱼有几类，哪种最好
   * cover :
   * imgList : []
   * user : {"avatar32":"Uploads/Avatar/2016-08-02/57a04e6b58c0d_32_32.jpg",
   * "avatar64":"Uploads/Avatar/2016-08-02/57a04e6b58c0d_64_64.jpg",
   * "avatar128":"Uploads/Avatar/2016-08-02/57a04e6b58c0d_128_128.jpg",
   * "avatar256":"Uploads/Avatar/2016-08-02/57a04e6b58c0d_256_256.jpg",
   * "avatar512":"Uploads/Avatar/2016-08-02/57a04e6b58c0d_512_512.jpg","uid":"325",
   * "signature":"界面都这么丑，受不鸟了","nickname":"昨日重现","username":"","real_nickname":"昨日重现"}
   * supportCount : 5
   * groupMemberCount : 4
   * is_support : 0
   * is_collection : 0
   * posts_rply : [{"id":"200","uid":"456","post_id":"70","parse":"0","content":"8",
   * "create_time":"1471410687","update_time":"1471410687","status":"1",
   * "rp_user":{"avatar32":"Uploads/Avatar/2016-08-20/57b7bf13e7207_32_32.png",
   * "avatar64":"Uploads/Avatar/2016-08-20/57b7bf13e7207_64_64.png",
   * "avatar128":"Uploads/Avatar/2016-08-20/57b7bf13e7207_128_128.png",
   * "avatar256":"Uploads/Avatar/2016-08-20/57b7bf13e7207_256_256.png",
   * "avatar512":"Uploads/Avatar/2016-08-20/57b7bf13e7207_512_512.png","uid":"456",
   * "nickname":"mengls","username":"","real_nickname":"mengls"}},{"id":"185","uid":"427",
   * "post_id":"70","parse":"0","content":"7","create_time":"1469674834",
   * "update_time":"1469674834","status":"1",
   * "rp_user":{"avatar32":"Uploads/Avatar/2016-08-22/57baad21df8b2_32_32.png",
   * "avatar64":"Uploads/Avatar/2016-08-22/57baad21df8b2_64_64.png",
   * "avatar128":"Uploads/Avatar/2016-08-22/57baad21df8b2_128_128.png",
   * "avatar256":"Uploads/Avatar/2016-08-22/57baad21df8b2_256_256.png",
   * "avatar512":"Uploads/Avatar/2016-08-22/57baad21df8b2_512_512.png","uid":"427",
   * "nickname":"得到","username":"","real_nickname":"得到"}},{"id":"187","uid":"397","post_id":"70",
   * "parse":"0","content":"评论呢","create_time":"1469773378","update_time":"1469773378",
   * "status":"1","rp_user":{"avatar32":"Uploads/Avatar/2016-07-29/579af5d7526fa_32_32.png",
   * "avatar64":"Uploads/Avatar/2016-07-29/579af5d7526fa_64_64.png",
   * "avatar128":"Uploads/Avatar/2016-07-29/579af5d7526fa_128_128.png",
   * "avatar256":"Uploads/Avatar/2016-07-29/579af5d7526fa_256_256.png",
   * "avatar512":"Uploads/Avatar/2016-07-29/579af5d7526fa_512_512.png","uid":"397",
   * "nickname":"萨玛","username":"","real_nickname":"萨玛"}},{"id":"206","uid":"325","post_id":"70",
   * "parse":"0","content":"111","create_time":"1472796950","update_time":"1472796950",
   * "status":"1","rp_user":{"avatar32":"Uploads/Avatar/2016-08-02/57a04e6b58c0d_32_32.jpg",
   * "avatar64":"Uploads/Avatar/2016-08-02/57a04e6b58c0d_64_64.jpg",
   * "avatar128":"Uploads/Avatar/2016-08-02/57a04e6b58c0d_128_128.jpg",
   * "avatar256":"Uploads/Avatar/2016-08-02/57a04e6b58c0d_256_256.jpg",
   * "avatar512":"Uploads/Avatar/2016-08-02/57a04e6b58c0d_512_512.jpg","uid":"325",
   * "nickname":"昨日重现","username":"","real_nickname":"昨日重现"}},{"id":"164","uid":"373",
   * "post_id":"70","parse":"0","content":"text","create_time":"1469174578",
   * "update_time":"1469174578","status":"1",
   * "rp_user":{"avatar32":"Uploads/Avatar/2016-07-19/578d91a883242_32_32.png",
   * "avatar64":"Uploads/Avatar/2016-07-19/578d91a883242_64_64.png",
   * "avatar128":"Uploads/Avatar/2016-07-19/578d91a883242_128_128.png",
   * "avatar256":"Uploads/Avatar/2016-07-19/578d91a883242_256_256.png",
   * "avatar512":"Uploads/Avatar/2016-07-19/578d91a883242_512_512.png","real_nickname":null}},
   * {"id":"161","uid":"373","post_id":"70","parse":"0","content":"5","create_time":"1469150026",
   * "update_time":"1469150026","status":"1",
   * "rp_user":{"avatar32":"Uploads/Avatar/2016-07-19/578d91a883242_32_32.png",
   * "avatar64":"Uploads/Avatar/2016-07-19/578d91a883242_64_64.png",
   * "avatar128":"Uploads/Avatar/2016-07-19/578d91a883242_128_128.png",
   * "avatar256":"Uploads/Avatar/2016-07-19/578d91a883242_256_256.png",
   * "avatar512":"Uploads/Avatar/2016-07-19/578d91a883242_512_512.png","real_nickname":null}},
   * {"id":"154","uid":"325","post_id":"70","parse":"0","content":"4","create_time":"1469070351",
   * "update_time":"1469070351","status":"1",
   * "rp_user":{"avatar32":"Uploads/Avatar/2016-08-02/57a04e6b58c0d_32_32.jpg",
   * "avatar64":"Uploads/Avatar/2016-08-02/57a04e6b58c0d_64_64.jpg",
   * "avatar128":"Uploads/Avatar/2016-08-02/57a04e6b58c0d_128_128.jpg",
   * "avatar256":"Uploads/Avatar/2016-08-02/57a04e6b58c0d_256_256.jpg",
   * "avatar512":"Uploads/Avatar/2016-08-02/57a04e6b58c0d_512_512.jpg","uid":"325",
   * "nickname":"昨日重现","username":"","real_nickname":"昨日重现"}},{"id":"153","uid":"325",
   * "post_id":"70","parse":"0","content":"3","create_time":"1469070101",
   * "update_time":"1469070101","status":"1",
   * "rp_user":{"avatar32":"Uploads/Avatar/2016-08-02/57a04e6b58c0d_32_32.jpg",
   * "avatar64":"Uploads/Avatar/2016-08-02/57a04e6b58c0d_64_64.jpg",
   * "avatar128":"Uploads/Avatar/2016-08-02/57a04e6b58c0d_128_128.jpg",
   * "avatar256":"Uploads/Avatar/2016-08-02/57a04e6b58c0d_256_256.jpg",
   * "avatar512":"Uploads/Avatar/2016-08-02/57a04e6b58c0d_512_512.jpg","uid":"325",
   * "nickname":"昨日重现","username":"","real_nickname":"昨日重现"}},{"id":"152","uid":"325",
   * "post_id":"70","parse":"0","content":"没人回啊","create_time":"1469070072",
   * "update_time":"1469070072","status":"1",
   * "rp_user":{"avatar32":"Uploads/Avatar/2016-08-02/57a04e6b58c0d_32_32.jpg",
   * "avatar64":"Uploads/Avatar/2016-08-02/57a04e6b58c0d_64_64.jpg",
   * "avatar128":"Uploads/Avatar/2016-08-02/57a04e6b58c0d_128_128.jpg",
   * "avatar256":"Uploads/Avatar/2016-08-02/57a04e6b58c0d_256_256.jpg",
   * "avatar512":"Uploads/Avatar/2016-08-02/57a04e6b58c0d_512_512.jpg","uid":"325",
   * "nickname":"昨日重现","username":"","real_nickname":"昨日重现"}},{"id":"144","uid":"373",
   * "post_id":"70","parse":"0","content":"同问","create_time":"1468916082",
   * "update_time":"1468916082","status":"1",
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
     * avatar32 : Uploads/Avatar/2016-08-02/57a04e6b58c0d_32_32.jpg
     * avatar64 : Uploads/Avatar/2016-08-02/57a04e6b58c0d_64_64.jpg
     * avatar128 : Uploads/Avatar/2016-08-02/57a04e6b58c0d_128_128.jpg
     * avatar256 : Uploads/Avatar/2016-08-02/57a04e6b58c0d_256_256.jpg
     * avatar512 : Uploads/Avatar/2016-08-02/57a04e6b58c0d_512_512.jpg
     * uid : 325
     * signature : 界面都这么丑，受不鸟了
     * nickname : 昨日重现
     * username :
     * real_nickname : 昨日重现
     */

    private UserBean user;
    private String supportCount;
    private String groupMemberCount;
    private String is_support;
    private String is_collection;
    private List<String> imgList;
    /**
     * id : 200
     * uid : 456
     * post_id : 70
     * parse : 0
     * content : 8
     * create_time : 1471410687
     * update_time : 1471410687
     * status : 1
     * rp_user : {"avatar32":"Uploads/Avatar/2016-08-20/57b7bf13e7207_32_32.png",
     * "avatar64":"Uploads/Avatar/2016-08-20/57b7bf13e7207_64_64.png",
     * "avatar128":"Uploads/Avatar/2016-08-20/57b7bf13e7207_128_128.png",
     * "avatar256":"Uploads/Avatar/2016-08-20/57b7bf13e7207_256_256.png",
     * "avatar512":"Uploads/Avatar/2016-08-20/57b7bf13e7207_512_512.png","uid":"456",
     * "nickname":"mengls","username":"","real_nickname":"mengls"}
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
      private String uid;
      private String signature;
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

      public String getSignature() {
        return signature;
      }

      public void setSignature(String signature) {
        this.signature = signature;
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
       * avatar32 : Uploads/Avatar/2016-08-20/57b7bf13e7207_32_32.png
       * avatar64 : Uploads/Avatar/2016-08-20/57b7bf13e7207_64_64.png
       * avatar128 : Uploads/Avatar/2016-08-20/57b7bf13e7207_128_128.png
       * avatar256 : Uploads/Avatar/2016-08-20/57b7bf13e7207_256_256.png
       * avatar512 : Uploads/Avatar/2016-08-20/57b7bf13e7207_512_512.png
       * uid : 456
       * nickname : mengls
       * username :
       * real_nickname : mengls
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
