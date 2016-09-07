/*
 * 文件名: WeiboDetailModel
 * 版    权：  Copyright Hengrtech Tech. Co. Ltd. All Rights Reserved.
 * 描    述: [该类的简要描述]
 * 创建人: zhaozeyang
 * 创建时间:16/9/1
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
 * @version [Taobei Client V20160411, 16/9/1]
 */
public class WeiboDetailModel extends BaseModel {


  /**
   * id : 562
   * uid : 391
   * content : 转发自己
   * create_time : 1473215852
   * comment_count : 0
   * status : 1
   * is_top : 0
   * type : repost
   * data : {"source":null,"sourceId":556,"sourse":{"id":"556","uid":"391","content":"哈哈啊哈哈哈",
   * "create_time":"1473155082","comment_count":"1","status":"1","is_top":"0","type":"image",
   * "data":{"attach_ids":"953,955,956"},"repost_count":"2","from":"iPhone 6",
   * "user":{"avatar32":"Uploads/Avatar/2016-09-07/57cf8925abc50_32_32.jpg",
   * "avatar64":"Uploads/Avatar/2016-09-07/57cf8925abc50_64_64.jpg",
   * "avatar128":"Uploads/Avatar/2016-09-07/57cf8925abc50_128_128.jpg",
   * "avatar256":"Uploads/Avatar/2016-09-07/57cf8925abc50_256_256.jpg",
   * "avatar512":"Uploads/Avatar/2016-09-07/57cf8925abc50_512_512.jpg","space_url":"/index
   * .php?s=/ucenter/index/index/uid/391.html",
   * "space_link
   * ":"<a ucard=\"391\" target=\"_blank\" href=\"/index.php?s=/ucenter/index/index/uid/391.html\"><\/a>","space_mob_url":"/index.php?s=/mob/user/index/uid/391.html","uid":"391","nickname":"15589510233","rank_link":[],"signature":"他来听我的演唱会","title":"Lv1
   * 无名小螺","weibocount":"7","fans":"0","following":"0","score2":"69",
   * "real_nickname":"15589510233"},"cover_url":["953","955","956"],
   * "images":["Uploads/Picture/2016-09-06/57ce8faf16722_100_100.png",
   * "Uploads/Picture/2016-09-06/57ce8ff4c0573_100_100.png",
   * "Uploads/Picture/2016-09-06/57ce8ffa4ef40_100_100.png"],"support_count":"1",
   * "weibo_data":{"attach_ids":"953,955,956"}}}
   * repost_count : 0
   * from : Xiaomi
   * support_count : 0
   * cover_url : [""]
   * images :
   * is_supported : 0
   * weibo_data : {"source":null,"sourceId":556,"sourse":{"id":"556","uid":"391",
   * "content":"哈哈啊哈哈哈","create_time":"1473155082","comment_count":"1","status":"1","is_top":"0",
   * "type":"image","data":{"attach_ids":"953,955,956"},"repost_count":"2","from":"iPhone 6",
   * "user":{"avatar32":"Uploads/Avatar/2016-09-07/57cf8925abc50_32_32.jpg",
   * "avatar64":"Uploads/Avatar/2016-09-07/57cf8925abc50_64_64.jpg",
   * "avatar128":"Uploads/Avatar/2016-09-07/57cf8925abc50_128_128.jpg",
   * "avatar256":"Uploads/Avatar/2016-09-07/57cf8925abc50_256_256.jpg",
   * "avatar512":"Uploads/Avatar/2016-09-07/57cf8925abc50_512_512.jpg","space_url":"/index
   * .php?s=/ucenter/index/index/uid/391.html",
   * "space_link
   * ":"<a ucard=\"391\" target=\"_blank\" href=\"/index.php?s=/ucenter/index/index/uid/391.html\"><\/a>","space_mob_url":"/index.php?s=/mob/user/index/uid/391.html","uid":"391","nickname":"15589510233","rank_link":[],"signature":"他来听我的演唱会","title":"Lv1
   * 无名小螺","weibocount":"7","fans":"0","following":"0","score2":"69",
   * "real_nickname":"15589510233"},"cover_url":["953","955","956"],
   * "images":["Uploads/Picture/2016-09-06/57ce8faf16722_100_100.png",
   * "Uploads/Picture/2016-09-06/57ce8ff4c0573_100_100.png",
   * "Uploads/Picture/2016-09-06/57ce8ffa4ef40_100_100.png"],"support_count":"1",
   * "weibo_data":{"attach_ids":"953,955,956"}}}
   * can_delete : false
   * user : {"avatar32":"Uploads/Avatar/2016-09-07/57cf8925abc50_32_32.jpg",
   * "avatar64":"Uploads/Avatar/2016-09-07/57cf8925abc50_64_64.jpg",
   * "avatar128":"Uploads/Avatar/2016-09-07/57cf8925abc50_128_128.jpg",
   * "avatar256":"Uploads/Avatar/2016-09-07/57cf8925abc50_256_256.jpg",
   * "avatar512":"Uploads/Avatar/2016-09-07/57cf8925abc50_512_512.jpg","space_url":"/index
   * .php?s=/ucenter/index/index/uid/391.html",
   * "space_link
   * ":"<a ucard=\"391\" target=\"_blank\" href=\"/index.php?s=/ucenter/index/index/uid/391.html\"><\/a>","space_mob_url":"/index.php?s=/mob/user/index/uid/391.html","uid":"391","nickname":"15589510233","rank_link":[],"signature":"他来听我的演唱会","title":"Lv1
   * 无名小螺","weibocount":"7","fans":"0","following":"0","score2":"69","real_nickname":"15589510233"}
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
     * source : null
     * sourceId : 556
     * sourse : {"id":"556","uid":"391","content":"哈哈啊哈哈哈","create_time":"1473155082",
     * "comment_count":"1","status":"1","is_top":"0","type":"image","data":{"attach_ids":"953,
     * 955,956"},"repost_count":"2","from":"iPhone 6",
     * "user":{"avatar32":"Uploads/Avatar/2016-09-07/57cf8925abc50_32_32.jpg",
     * "avatar64":"Uploads/Avatar/2016-09-07/57cf8925abc50_64_64.jpg",
     * "avatar128":"Uploads/Avatar/2016-09-07/57cf8925abc50_128_128.jpg",
     * "avatar256":"Uploads/Avatar/2016-09-07/57cf8925abc50_256_256.jpg",
     * "avatar512":"Uploads/Avatar/2016-09-07/57cf8925abc50_512_512.jpg","space_url":"/index
     * .php?s=/ucenter/index/index/uid/391.html",
     * "space_link
     * ":"<a ucard=\"391\" target=\"_blank\" href=\"/index.php?s=/ucenter/index/index/uid/391.html\"><\/a>","space_mob_url":"/index.php?s=/mob/user/index/uid/391.html","uid":"391","nickname":"15589510233","rank_link":[],"signature":"他来听我的演唱会","title":"Lv1
     * 无名小螺","weibocount":"7","fans":"0","following":"0","score2":"69",
     * "real_nickname":"15589510233"},"cover_url":["953","955","956"],
     * "images":["Uploads/Picture/2016-09-06/57ce8faf16722_100_100.png",
     * "Uploads/Picture/2016-09-06/57ce8ff4c0573_100_100.png",
     * "Uploads/Picture/2016-09-06/57ce8ffa4ef40_100_100.png"],"support_count":"1",
     * "weibo_data":{"attach_ids":"953,955,956"}}
     */

    private DataBean data;
    private int repost_count;
    private String from;
    private String support_count;
    private String images;
    private String is_supported;
    /**
     * source : null
     * sourceId : 556
     * sourse : {"id":"556","uid":"391","content":"哈哈啊哈哈哈","create_time":"1473155082",
     * "comment_count":"1","status":"1","is_top":"0","type":"image","data":{"attach_ids":"953,
     * 955,956"},"repost_count":"2","from":"iPhone 6",
     * "user":{"avatar32":"Uploads/Avatar/2016-09-07/57cf8925abc50_32_32.jpg",
     * "avatar64":"Uploads/Avatar/2016-09-07/57cf8925abc50_64_64.jpg",
     * "avatar128":"Uploads/Avatar/2016-09-07/57cf8925abc50_128_128.jpg",
     * "avatar256":"Uploads/Avatar/2016-09-07/57cf8925abc50_256_256.jpg",
     * "avatar512":"Uploads/Avatar/2016-09-07/57cf8925abc50_512_512.jpg","space_url":"/index
     * .php?s=/ucenter/index/index/uid/391.html",
     * "space_link
     * ":"<a ucard=\"391\" target=\"_blank\" href=\"/index.php?s=/ucenter/index/index/uid/391.html\"><\/a>","space_mob_url":"/index.php?s=/mob/user/index/uid/391.html","uid":"391","nickname":"15589510233","rank_link":[],"signature":"他来听我的演唱会","title":"Lv1
     * 无名小螺","weibocount":"7","fans":"0","following":"0","score2":"69",
     * "real_nickname":"15589510233"},"cover_url":["953","955","956"],
     * "images":["Uploads/Picture/2016-09-06/57ce8faf16722_100_100.png",
     * "Uploads/Picture/2016-09-06/57ce8ff4c0573_100_100.png",
     * "Uploads/Picture/2016-09-06/57ce8ffa4ef40_100_100.png"],"support_count":"1",
     * "weibo_data":{"attach_ids":"953,955,956"}}
     */

    private WeiboDataBean weibo_data;
    private boolean can_delete;
    /**
     * avatar32 : Uploads/Avatar/2016-09-07/57cf8925abc50_32_32.jpg
     * avatar64 : Uploads/Avatar/2016-09-07/57cf8925abc50_64_64.jpg
     * avatar128 : Uploads/Avatar/2016-09-07/57cf8925abc50_128_128.jpg
     * avatar256 : Uploads/Avatar/2016-09-07/57cf8925abc50_256_256.jpg
     * avatar512 : Uploads/Avatar/2016-09-07/57cf8925abc50_512_512.jpg
     * space_url : /index.php?s=/ucenter/index/index/uid/391.html
     * space_link :
     * <a ucard="391" target="_blank" href="/index.php?s=/ucenter/index/index/uid/391.html"></a>
     * space_mob_url : /index.php?s=/mob/user/index/uid/391.html
     * uid : 391
     * nickname : 15589510233
     * rank_link : []
     * signature : 他来听我的演唱会
     * title : Lv1 无名小螺
     * weibocount : 7
     * fans : 0
     * following : 0
     * score2 : 69
     * real_nickname : 15589510233
     */

    private UserInfoModel user;
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

    public UserInfoModel getUser() {
      return user;
    }

    public void setUser(UserInfoModel user) {
      this.user = user;
    }

    public List<String> getCover_url() {
      return cover_url;
    }

    public void setCover_url(List<String> cover_url) {
      this.cover_url = cover_url;
    }

    public static class DataBean {
      private Object source;
      private int sourceId;
      /**
       * id : 556
       * uid : 391
       * content : 哈哈啊哈哈哈
       * create_time : 1473155082
       * comment_count : 1
       * status : 1
       * is_top : 0
       * type : image
       * data : {"attach_ids":"953,955,956"}
       * repost_count : 2
       * from : iPhone 6
       * user : {"avatar32":"Uploads/Avatar/2016-09-07/57cf8925abc50_32_32.jpg",
       * "avatar64":"Uploads/Avatar/2016-09-07/57cf8925abc50_64_64.jpg",
       * "avatar128":"Uploads/Avatar/2016-09-07/57cf8925abc50_128_128.jpg",
       * "avatar256":"Uploads/Avatar/2016-09-07/57cf8925abc50_256_256.jpg",
       * "avatar512":"Uploads/Avatar/2016-09-07/57cf8925abc50_512_512.jpg","space_url":"/index
       * .php?s=/ucenter/index/index/uid/391.html",
       * "space_link":"<a ucard=\"391\" target=\"_blank\" href=\"/index.php?s=/ucenter/index/index/uid/391.html\"><\/a>","space_mob_url":"/index.php?s=/mob/user/index/uid/391.html","uid":"391","nickname":"15589510233","rank_link":[],"signature":"他来听我的演唱会","title":"Lv1
       * 无名小螺","weibocount":"7","fans":"0","following":"0","score2":"69",
       * "real_nickname":"15589510233"}
       * cover_url : ["953","955","956"]
       * images : ["Uploads/Picture/2016-09-06/57ce8faf16722_100_100.png",
       * "Uploads/Picture/2016-09-06/57ce8ff4c0573_100_100.png",
       * "Uploads/Picture/2016-09-06/57ce8ffa4ef40_100_100.png"]
       * support_count : 1
       * weibo_data : {"attach_ids":"953,955,956"}
       */

      private SourseBean sourse;

      public Object getSource() {
        return source;
      }

      public void setSource(Object source) {
        this.source = source;
      }

      public int getSourceId() {
        return sourceId;
      }

      public void setSourceId(int sourceId) {
        this.sourceId = sourceId;
      }

      public SourseBean getSourse() {
        return sourse;
      }

      public void setSourse(SourseBean sourse) {
        this.sourse = sourse;
      }

      public static class SourseBean {
        private String id;
        private String uid;
        private String content;
        private String create_time;
        private String comment_count;
        private String status;
        private String is_top;
        private String type;
        /**
         * attach_ids : 953,955,956
         */

        private DataBean data;
        private String repost_count;
        private String from;
        /**
         * avatar32 : Uploads/Avatar/2016-09-07/57cf8925abc50_32_32.jpg
         * avatar64 : Uploads/Avatar/2016-09-07/57cf8925abc50_64_64.jpg
         * avatar128 : Uploads/Avatar/2016-09-07/57cf8925abc50_128_128.jpg
         * avatar256 : Uploads/Avatar/2016-09-07/57cf8925abc50_256_256.jpg
         * avatar512 : Uploads/Avatar/2016-09-07/57cf8925abc50_512_512.jpg
         * space_url : /index.php?s=/ucenter/index/index/uid/391.html
         * space_link :
         * <a ucard="391" target="_blank" href="/index.php?s=/ucenter/index/index/uid/391.html"></a>
         * space_mob_url : /index.php?s=/mob/user/index/uid/391.html
         * uid : 391
         * nickname : 15589510233
         * rank_link : []
         * signature : 他来听我的演唱会
         * title : Lv1 无名小螺
         * weibocount : 7
         * fans : 0
         * following : 0
         * score2 : 69
         * real_nickname : 15589510233
         */

        private UserInfoModel user;
        private String support_count;
        /**
         * attach_ids : 953,955,956
         */

        private WeiboDataBean weibo_data;
        private List<String> cover_url;
        private List<String> images;

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

        public String getComment_count() {
          return comment_count;
        }

        public void setComment_count(String comment_count) {
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

        public String getRepost_count() {
          return repost_count;
        }

        public void setRepost_count(String repost_count) {
          this.repost_count = repost_count;
        }

        public String getFrom() {
          return from;
        }

        public void setFrom(String from) {
          this.from = from;
        }

        public UserInfoModel getUser() {
          return user;
        }

        public void setUser(UserInfoModel user) {
          this.user = user;
        }

        public String getSupport_count() {
          return support_count;
        }

        public void setSupport_count(String support_count) {
          this.support_count = support_count;
        }

        public WeiboDataBean getWeibo_data() {
          return weibo_data;
        }

        public void setWeibo_data(WeiboDataBean weibo_data) {
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

        public static class WeiboDataBean {
          private String attach_ids;

          public String getAttach_ids() {
            return attach_ids;
          }

          public void setAttach_ids(String attach_ids) {
            this.attach_ids = attach_ids;
          }
        }
      }
    }

    public static class WeiboDataBean {
      private DataBean.SourseBean source;
      private int sourceId;
      /**
       * id : 556
       * uid : 391
       * content : 哈哈啊哈哈哈
       * create_time : 1473155082
       * comment_count : 1
       * status : 1
       * is_top : 0
       * type : image
       * data : {"attach_ids":"953,955,956"}
       * repost_count : 2
       * from : iPhone 6
       * user : {"avatar32":"Uploads/Avatar/2016-09-07/57cf8925abc50_32_32.jpg",
       * "avatar64":"Uploads/Avatar/2016-09-07/57cf8925abc50_64_64.jpg",
       * "avatar128":"Uploads/Avatar/2016-09-07/57cf8925abc50_128_128.jpg",
       * "avatar256":"Uploads/Avatar/2016-09-07/57cf8925abc50_256_256.jpg",
       * "avatar512":"Uploads/Avatar/2016-09-07/57cf8925abc50_512_512.jpg","space_url":"/index
       * .php?s=/ucenter/index/index/uid/391.html",
       * "space_link":"<a ucard=\"391\" target=\"_blank\" href=\"/index.php?s=/ucenter/index/index/uid/391.html\"><\/a>","space_mob_url":"/index.php?s=/mob/user/index/uid/391.html","uid":"391","nickname":"15589510233","rank_link":[],"signature":"他来听我的演唱会","title":"Lv1
       * 无名小螺","weibocount":"7","fans":"0","following":"0","score2":"69",
       * "real_nickname":"15589510233"}
       * cover_url : ["953","955","956"]
       * images : ["Uploads/Picture/2016-09-06/57ce8faf16722_100_100.png",
       * "Uploads/Picture/2016-09-06/57ce8ff4c0573_100_100.png",
       * "Uploads/Picture/2016-09-06/57ce8ffa4ef40_100_100.png"]
       * support_count : 1
       * weibo_data : {"attach_ids":"953,955,956"}
       */

      private SourseBean sourse;

      public DataBean.SourseBean getSource() {
        return source;
      }

      public void setSource(DataBean.SourseBean source) {
        this.source = source;
      }

      public int getSourceId() {
        return sourceId;
      }

      public void setSourceId(int sourceId) {
        this.sourceId = sourceId;
      }

      public SourseBean getSourse() {
        return sourse;
      }

      public void setSourse(SourseBean sourse) {
        this.sourse = sourse;
      }

      public static class SourseBean {
        private String id;
        private String uid;
        private String content;
        private String create_time;
        private String comment_count;
        private String status;
        private String is_top;
        private String type;
        /**
         * attach_ids : 953,955,956
         */

        private DataBean data;
        private String repost_count;
        private String from;
        /**
         * avatar32 : Uploads/Avatar/2016-09-07/57cf8925abc50_32_32.jpg
         * avatar64 : Uploads/Avatar/2016-09-07/57cf8925abc50_64_64.jpg
         * avatar128 : Uploads/Avatar/2016-09-07/57cf8925abc50_128_128.jpg
         * avatar256 : Uploads/Avatar/2016-09-07/57cf8925abc50_256_256.jpg
         * avatar512 : Uploads/Avatar/2016-09-07/57cf8925abc50_512_512.jpg
         * space_url : /index.php?s=/ucenter/index/index/uid/391.html
         * space_link :
         * <a ucard="391" target="_blank" href="/index.php?s=/ucenter/index/index/uid/391.html"></a>
         * space_mob_url : /index.php?s=/mob/user/index/uid/391.html
         * uid : 391
         * nickname : 15589510233
         * rank_link : []
         * signature : 他来听我的演唱会
         * title : Lv1 无名小螺
         * weibocount : 7
         * fans : 0
         * following : 0
         * score2 : 69
         * real_nickname : 15589510233
         */

        private UserInfoModel user;
        private String support_count;
        /**
         * attach_ids : 953,955,956
         */

        private WeiboDataBean weibo_data;
        private List<String> cover_url;
        private List<String> images;

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

        public String getComment_count() {
          return comment_count;
        }

        public void setComment_count(String comment_count) {
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

        public String getRepost_count() {
          return repost_count;
        }

        public void setRepost_count(String repost_count) {
          this.repost_count = repost_count;
        }

        public String getFrom() {
          return from;
        }

        public void setFrom(String from) {
          this.from = from;
        }

        public UserInfoModel getUser() {
          return user;
        }

        public void setUser(UserInfoModel user) {
          this.user = user;
        }

        public String getSupport_count() {
          return support_count;
        }

        public void setSupport_count(String support_count) {
          this.support_count = support_count;
        }

        public WeiboDataBean getWeibo_data() {
          return weibo_data;
        }

        public void setWeibo_data(WeiboDataBean weibo_data) {
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

        public static class DataBean {
          private String attach_ids;

          public String getAttach_ids() {
            return attach_ids;
          }

          public void setAttach_ids(String attach_ids) {
            this.attach_ids = attach_ids;
          }
        }

      }
    }
  }

}
