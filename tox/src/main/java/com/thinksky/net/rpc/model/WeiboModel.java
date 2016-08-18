/*
 * 文件名: WeiboModel
 * 版    权：  Copyright Hengrtech Tech. Co. Ltd. All Rights Reserved.
 * 描    述: [该类的简要描述]
 * 创建人: zhaozeyang
 * 创建时间:16/8/17
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
 * @version [Taobei Client V20160411, 16/8/17]
 */
public class WeiboModel extends BaseModel{

  /**
   * id : 470
   * uid : 427
   * content : 我在群组【我们一起动起来】里发表了一个新的帖子【Bihar】：http://122.5.45.150/index
   * .php?s=/Group/Index/detail/id/90.html
   * create_time : 1471308876
   * comment_count : 0
   * status : 1
   * is_top : 0
   * type : feed
   * data : {"sourse":{"user":{"avatar32":"Public/images/default_avatar_32_32.jpg",
   * "avatar64":"Public/images/default_avatar_64_64.jpg",
   * "avatar128":"Public/images/default_avatar_128_128.jpg",
   * "avatar256":"Public/images/default_avatar_256_256.jpg",
   * "avatar512":"Public/images/default_avatar_512_512.jpg","space_url":"/index
   * .php?s=/ucenter/index/index/uid/0.html",
   * "space_link
   * ":"<a ucard=\"0\" target=\"_blank\" href=\"/index.php?s=/ucenter/index/index/uid/0.html\"><\/a>","space_mob_url":"/index.php?s=/mob/user/index/uid/0.html","rank_link":[],"title":"Lv1 无名小螺","weibocount":"0","fans":"0","following":"0","real_nickname":null},"data":false,"cover_url":[""],"images":[""],"support_count":"0","weibo_data":false}}
   * repost_count : 0
   * from :
   * support_count : 0
   * cover_url : [""]
   * images :
   * is_supported : 0
   * weibo_data : {"sourse":{"user":{"avatar32":"Public/images/default_avatar_32_32.jpg",
   * "avatar64":"Public/images/default_avatar_64_64.jpg",
   * "avatar128":"Public/images/default_avatar_128_128.jpg",
   * "avatar256":"Public/images/default_avatar_256_256.jpg",
   * "avatar512":"Public/images/default_avatar_512_512.jpg","space_url":"/index
   * .php?s=/ucenter/index/index/uid/0.html",
   * "space_link
   * ":"<a ucard=\"0\" target=\"_blank\" href=\"/index.php?s=/ucenter/index/index/uid/0.html\"><\/a>","space_mob_url":"/index.php?s=/mob/user/index/uid/0.html","rank_link":[],"title":"Lv1 无名小螺","weibocount":"0","fans":"0","following":"0","real_nickname":null},"data":false,"cover_url":[""],"images":[""],"support_count":"0","weibo_data":false}}
   * can_delete : false
   * user : {"avatar32":"Uploads/Avatar/2016-08-02/57a04b6e1c1a4_32_32.jpg",
   * "avatar64":"Uploads/Avatar/2016-08-02/57a04b6e1c1a4_64_64.jpg",
   * "avatar128":"Uploads/Avatar/2016-08-02/57a04b6e1c1a4_128_128.jpg",
   * "avatar256":"Uploads/Avatar/2016-08-02/57a04b6e1c1a4_256_256.jpg",
   * "avatar512":"Uploads/Avatar/2016-08-02/57a04b6e1c1a4_512_512.jpg","space_url":"/api
   * .php?s=/Ucenter/Index/index/uid/427.html",
   * "space_link
   * ":"<a ucard=\"427\" target=\"_blank\" href=\"/api.php?s=/Ucenter/Index/index/uid/427.html\">唐德as<\/a>","space_mob_url":"/api.php?s=/Mob/User/index/uid/427.html","uid":"427","nickname":"唐德as","rank_link":[],"signature":"L;ask;k","title":"Lv1 无名小螺","weibocount":"19","fans":"0","following":"2","score2":"99","real_nickname":"唐德as"}
   */

  private List<ListBean> list;

  public List<ListBean> getList() {
    return list;
  }

  public void setList(List<ListBean> list) {
    this.list = list;
  }

  public static class ListBean {
    private int id;
    private String uid;
    private String content;
    private int create_time;
    private int comment_count;
    private String status;
    private String is_top;
    private String type;
    /**
     * sourse : {"user":{"avatar32":"Public/images/default_avatar_32_32.jpg",
     * "avatar64":"Public/images/default_avatar_64_64.jpg",
     * "avatar128":"Public/images/default_avatar_128_128.jpg",
     * "avatar256":"Public/images/default_avatar_256_256.jpg",
     * "avatar512":"Public/images/default_avatar_512_512.jpg","space_url":"/index
     * .php?s=/ucenter/index/index/uid/0.html",
     * "space_link
     * ":"<a ucard=\"0\" target=\"_blank\" href=\"/index.php?s=/ucenter/index/index/uid/0.html\"><\/a>","space_mob_url":"/index.php?s=/mob/user/index/uid/0.html","rank_link":[],"title":"Lv1 无名小螺","weibocount":"0","fans":"0","following":"0","real_nickname":null},"data":false,"cover_url":[""],"images":[""],"support_count":"0","weibo_data":false}
     */

    private DataBean data;
    private int repost_count;
    private String from;
    private String support_count;
    private String images;
    private String is_supported;
    /**
     * sourse : {"user":{"avatar32":"Public/images/default_avatar_32_32.jpg",
     * "avatar64":"Public/images/default_avatar_64_64.jpg",
     * "avatar128":"Public/images/default_avatar_128_128.jpg",
     * "avatar256":"Public/images/default_avatar_256_256.jpg",
     * "avatar512":"Public/images/default_avatar_512_512.jpg","space_url":"/index
     * .php?s=/ucenter/index/index/uid/0.html",
     * "space_link
     * ":"<a ucard=\"0\" target=\"_blank\" href=\"/index.php?s=/ucenter/index/index/uid/0.html\"><\/a>","space_mob_url":"/index.php?s=/mob/user/index/uid/0.html","rank_link":[],"title":"Lv1 无名小螺","weibocount":"0","fans":"0","following":"0","real_nickname":null},"data":false,"cover_url":[""],"images":[""],"support_count":"0","weibo_data":false}
     */

    private WeiboDataBean weibo_data;
    private boolean can_delete;
    /**
     * avatar32 : Uploads/Avatar/2016-08-02/57a04b6e1c1a4_32_32.jpg
     * avatar64 : Uploads/Avatar/2016-08-02/57a04b6e1c1a4_64_64.jpg
     * avatar128 : Uploads/Avatar/2016-08-02/57a04b6e1c1a4_128_128.jpg
     * avatar256 : Uploads/Avatar/2016-08-02/57a04b6e1c1a4_256_256.jpg
     * avatar512 : Uploads/Avatar/2016-08-02/57a04b6e1c1a4_512_512.jpg
     * space_url : /api.php?s=/Ucenter/Index/index/uid/427.html
     * space_link :
     * <a ucard="427" target="_blank" href="/api.php?s=/Ucenter/Index/index/uid/427.html">唐德as</a>
     * space_mob_url : /api.php?s=/Mob/User/index/uid/427.html
     * uid : 427
     * nickname : 唐德as
     * rank_link : []
     * signature : L;ask;k
     * title : Lv1 无名小螺
     * weibocount : 19
     * fans : 0
     * following : 2
     * score2 : 99
     * real_nickname : 唐德as
     */

    private UserBean user;
    private List<String> cover_url;

    public int getId() {
      return id;
    }

    public void setId(int id) {
      this.id = id;
    }

    public String getUid() {
      return uid;
    }

    public void setUid(String uid) {
      this.uid = uid;
    }

    public String getContent() {
      return content;
    }

    public void setContent(String content) {
      this.content = content;
    }

    public int getCreate_time() {
      return create_time;
    }

    public void setCreate_time(int create_time) {
      this.create_time = create_time;
    }

    public int getComment_count() {
      return comment_count;
    }

    public void setComment_count(int comment_count) {
      this.comment_count = comment_count;
    }

    public String getStatus() {
      return status;
    }

    public void setStatus(String status) {
      this.status = status;
    }

    public String getIs_top() {
      return is_top;
    }

    public void setIs_top(String is_top) {
      this.is_top = is_top;
    }

    public String getType() {
      return type;
    }

    public void setType(String type) {
      this.type = type;
    }

    public DataBean getData() {
      return data;
    }

    public void setData(DataBean data) {
      this.data = data;
    }

    public int getRepost_count() {
      return repost_count;
    }

    public void setRepost_count(int repost_count) {
      this.repost_count = repost_count;
    }

    public String getFrom() {
      return from;
    }

    public void setFrom(String from) {
      this.from = from;
    }

    public String getSupport_count() {
      return support_count;
    }

    public void setSupport_count(String support_count) {
      this.support_count = support_count;
    }

    public String getImages() {
      return images;
    }

    public void setImages(String images) {
      this.images = images;
    }

    public String getIs_supported() {
      return is_supported;
    }

    public void setIs_supported(String is_supported) {
      this.is_supported = is_supported;
    }

    public WeiboDataBean getWeibo_data() {
      return weibo_data;
    }

    public void setWeibo_data(WeiboDataBean weibo_data) {
      this.weibo_data = weibo_data;
    }

    public boolean isCan_delete() {
      return can_delete;
    }

    public void setCan_delete(boolean can_delete) {
      this.can_delete = can_delete;
    }

    public UserBean getUser() {
      return user;
    }

    public void setUser(UserBean user) {
      this.user = user;
    }

    public List<String> getCover_url() {
      return cover_url;
    }

    public void setCover_url(List<String> cover_url) {
      this.cover_url = cover_url;
    }

    public static class DataBean {
      /**
       * user : {"avatar32":"Public/images/default_avatar_32_32.jpg",
       * "avatar64":"Public/images/default_avatar_64_64.jpg",
       * "avatar128":"Public/images/default_avatar_128_128.jpg",
       * "avatar256":"Public/images/default_avatar_256_256.jpg",
       * "avatar512":"Public/images/default_avatar_512_512.jpg","space_url":"/index
       * .php?s=/ucenter/index/index/uid/0.html",
       * "space_link":"<a ucard=\"0\" target=\"_blank\" href=\"/index.php?s=/ucenter/index/index/uid/0.html\"><\/a>","space_mob_url":"/index.php?s=/mob/user/index/uid/0.html","rank_link":[],"title":"Lv1 无名小螺","weibocount":"0","fans":"0","following":"0","real_nickname":null}
       * data : false
       * cover_url : [""]
       * images : [""]
       * support_count : 0
       * weibo_data : false
       */

      private SourseBean sourse;

      public SourseBean getSourse() {
        return sourse;
      }

      public void setSourse(SourseBean sourse) {
        this.sourse = sourse;
      }

      public static class SourseBean {
        /**
         * avatar32 : Public/images/default_avatar_32_32.jpg
         * avatar64 : Public/images/default_avatar_64_64.jpg
         * avatar128 : Public/images/default_avatar_128_128.jpg
         * avatar256 : Public/images/default_avatar_256_256.jpg
         * avatar512 : Public/images/default_avatar_512_512.jpg
         * space_url : /index.php?s=/ucenter/index/index/uid/0.html
         * space_link :
         * <a ucard="0" target="_blank" href="/index.php?s=/ucenter/index/index/uid/0.html"></a>
         * space_mob_url : /index.php?s=/mob/user/index/uid/0.html
         * rank_link : []
         * title : Lv1 无名小螺
         * weibocount : 0
         * fans : 0
         * following : 0
         * real_nickname : null
         */

        private UserBean user;
        private boolean data;
        private String support_count;
        private boolean weibo_data;
        private List<String> cover_url;
        private List<String> images;

        public UserBean getUser() {
          return user;
        }

        public void setUser(UserBean user) {
          this.user = user;
        }

        public boolean isData() {
          return data;
        }

        public void setData(boolean data) {
          this.data = data;
        }

        public String getSupport_count() {
          return support_count;
        }

        public void setSupport_count(String support_count) {
          this.support_count = support_count;
        }

        public boolean isWeibo_data() {
          return weibo_data;
        }

        public void setWeibo_data(boolean weibo_data) {
          this.weibo_data = weibo_data;
        }

        public List<String> getCover_url() {
          return cover_url;
        }

        public void setCover_url(List<String> cover_url) {
          this.cover_url = cover_url;
        }

        public List<String> getImages() {
          return images;
        }

        public void setImages(List<String> images) {
          this.images = images;
        }

        public static class UserBean {
          private String avatar32;
          private String avatar64;
          private String avatar128;
          private String avatar256;
          private String avatar512;
          private String space_url;
          private String space_link;
          private String space_mob_url;
          private String title;
          private String weibocount;
          private String fans;
          private String following;
          private Object real_nickname;
          private List<?> rank_link;

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

          public String getSpace_url() {
            return space_url;
          }

          public void setSpace_url(String space_url) {
            this.space_url = space_url;
          }

          public String getSpace_link() {
            return space_link;
          }

          public void setSpace_link(String space_link) {
            this.space_link = space_link;
          }

          public String getSpace_mob_url() {
            return space_mob_url;
          }

          public void setSpace_mob_url(String space_mob_url) {
            this.space_mob_url = space_mob_url;
          }

          public String getTitle() {
            return title;
          }

          public void setTitle(String title) {
            this.title = title;
          }

          public String getWeibocount() {
            return weibocount;
          }

          public void setWeibocount(String weibocount) {
            this.weibocount = weibocount;
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

          public Object getReal_nickname() {
            return real_nickname;
          }

          public void setReal_nickname(Object real_nickname) {
            this.real_nickname = real_nickname;
          }

          public List<?> getRank_link() {
            return rank_link;
          }

          public void setRank_link(List<?> rank_link) {
            this.rank_link = rank_link;
          }
        }
      }
    }

    public static class WeiboDataBean {
      /**
       * user : {"avatar32":"Public/images/default_avatar_32_32.jpg",
       * "avatar64":"Public/images/default_avatar_64_64.jpg",
       * "avatar128":"Public/images/default_avatar_128_128.jpg",
       * "avatar256":"Public/images/default_avatar_256_256.jpg",
       * "avatar512":"Public/images/default_avatar_512_512.jpg","space_url":"/index
       * .php?s=/ucenter/index/index/uid/0.html",
       * "space_link":"<a ucard=\"0\" target=\"_blank\" href=\"/index.php?s=/ucenter/index/index/uid/0.html\"><\/a>","space_mob_url":"/index.php?s=/mob/user/index/uid/0.html","rank_link":[],"title":"Lv1 无名小螺","weibocount":"0","fans":"0","following":"0","real_nickname":null}
       * data : false
       * cover_url : [""]
       * images : [""]
       * support_count : 0
       * weibo_data : false
       */

      private SourseBean sourse;

      public SourseBean getSourse() {
        return sourse;
      }

      public void setSourse(SourseBean sourse) {
        this.sourse = sourse;
      }

      public static class SourseBean {
        /**
         * avatar32 : Public/images/default_avatar_32_32.jpg
         * avatar64 : Public/images/default_avatar_64_64.jpg
         * avatar128 : Public/images/default_avatar_128_128.jpg
         * avatar256 : Public/images/default_avatar_256_256.jpg
         * avatar512 : Public/images/default_avatar_512_512.jpg
         * space_url : /index.php?s=/ucenter/index/index/uid/0.html
         * space_link :
         * <a ucard="0" target="_blank" href="/index.php?s=/ucenter/index/index/uid/0.html"></a>
         * space_mob_url : /index.php?s=/mob/user/index/uid/0.html
         * rank_link : []
         * title : Lv1 无名小螺
         * weibocount : 0
         * fans : 0
         * following : 0
         * real_nickname : null
         */

        private UserBean user;
        private boolean data;
        private String support_count;
        private boolean weibo_data;
        private List<String> cover_url;
        private List<String> images;

        public UserBean getUser() {
          return user;
        }

        public void setUser(UserBean user) {
          this.user = user;
        }

        public boolean isData() {
          return data;
        }

        public void setData(boolean data) {
          this.data = data;
        }

        public String getSupport_count() {
          return support_count;
        }

        public void setSupport_count(String support_count) {
          this.support_count = support_count;
        }

        public boolean isWeibo_data() {
          return weibo_data;
        }

        public void setWeibo_data(boolean weibo_data) {
          this.weibo_data = weibo_data;
        }

        public List<String> getCover_url() {
          return cover_url;
        }

        public void setCover_url(List<String> cover_url) {
          this.cover_url = cover_url;
        }

        public List<String> getImages() {
          return images;
        }

        public void setImages(List<String> images) {
          this.images = images;
        }

        public static class UserBean {
          private String avatar32;
          private String avatar64;
          private String avatar128;
          private String avatar256;
          private String avatar512;
          private String space_url;
          private String space_link;
          private String space_mob_url;
          private String title;
          private String weibocount;
          private String fans;
          private String following;
          private Object real_nickname;
          private List<?> rank_link;

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

          public String getSpace_url() {
            return space_url;
          }

          public void setSpace_url(String space_url) {
            this.space_url = space_url;
          }

          public String getSpace_link() {
            return space_link;
          }

          public void setSpace_link(String space_link) {
            this.space_link = space_link;
          }

          public String getSpace_mob_url() {
            return space_mob_url;
          }

          public void setSpace_mob_url(String space_mob_url) {
            this.space_mob_url = space_mob_url;
          }

          public String getTitle() {
            return title;
          }

          public void setTitle(String title) {
            this.title = title;
          }

          public String getWeibocount() {
            return weibocount;
          }

          public void setWeibocount(String weibocount) {
            this.weibocount = weibocount;
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

          public Object getReal_nickname() {
            return real_nickname;
          }

          public void setReal_nickname(Object real_nickname) {
            this.real_nickname = real_nickname;
          }

          public List<?> getRank_link() {
            return rank_link;
          }

          public void setRank_link(List<?> rank_link) {
            this.rank_link = rank_link;
          }
        }
      }
    }

    public static class UserBean {
      private String avatar32;
      private String avatar64;
      private String avatar128;
      private String avatar256;
      private String avatar512;
      private String space_url;
      private String space_link;
      private String space_mob_url;
      private String uid;
      private String nickname;
      private String signature;
      private String title;
      private String weibocount;
      private String fans;
      private String following;
      private String score2;
      private String real_nickname;
      private List<?> rank_link;

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

      public String getSpace_url() {
        return space_url;
      }

      public void setSpace_url(String space_url) {
        this.space_url = space_url;
      }

      public String getSpace_link() {
        return space_link;
      }

      public void setSpace_link(String space_link) {
        this.space_link = space_link;
      }

      public String getSpace_mob_url() {
        return space_mob_url;
      }

      public void setSpace_mob_url(String space_mob_url) {
        this.space_mob_url = space_mob_url;
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

      public String getSignature() {
        return signature;
      }

      public void setSignature(String signature) {
        this.signature = signature;
      }

      public String getTitle() {
        return title;
      }

      public void setTitle(String title) {
        this.title = title;
      }

      public String getWeibocount() {
        return weibocount;
      }

      public void setWeibocount(String weibocount) {
        this.weibocount = weibocount;
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

      public List<?> getRank_link() {
        return rank_link;
      }

      public void setRank_link(List<?> rank_link) {
        this.rank_link = rank_link;
      }
    }
  }
}
