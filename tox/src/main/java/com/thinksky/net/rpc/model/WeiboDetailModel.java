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
   * id : 530
   * content :
   * create_time : 1472796981
   * type : feed
   * data : {"attach_ids":""}
   * is_supported : 0
   * weibo_data : {"attach_ids":""}
   * comment_count : 4
   * repost_count : 0
   * can_delete : 0
   * is_top : 0
   * uid : 325
   * from : SMARTISAN
   * user : {"avatar32":"Uploads/Avatar/2016-08-02/57a04e6b58c0d_32_32.jpg",
   * "avatar64":"Uploads/Avatar/2016-08-02/57a04e6b58c0d_64_64.jpg",
   * "avatar128":"Uploads/Avatar/2016-08-02/57a04e6b58c0d_128_128.jpg",
   * "avatar256":"Uploads/Avatar/2016-08-02/57a04e6b58c0d_256_256.jpg",
   * "avatar512":"Uploads/Avatar/2016-08-02/57a04e6b58c0d_512_512.jpg","space_url":"/api
   * .php?s=/Ucenter/Index/index/uid/325.html",
   * "space_link
   * ":"<a ucard=\"325\" target=\"_blank\" href=\"/api.php?s=/Ucenter/Index/index/uid/325.html\">昨日重现<\/a>","space_mob_url":"/api.php?s=/Mob/User/index/uid/325.html","uid":"325","nickname":"昨日重现","rank_link":[],"title":"Lv1 无名小螺","score2":"200","real_nickname":"昨日重现"}
   */

  private ListBean list;

  public ListBean getList() {
    return list;
  }

  public void setList(ListBean list) {
    this.list = list;
  }

  public static class ListBean {
    private int id;
    private String content;
    private int create_time;
    private String type;
    /**
     * attach_ids :
     */

    private DataBean data;
    private String is_supported;
    /**
     * attach_ids :
     */

    private WeiboDataBean weibo_data;
    private int comment_count;
    private int repost_count;
    private int can_delete;
    private String is_top;
    private String uid;
    private String from;
    /**
     * avatar32 : Uploads/Avatar/2016-08-02/57a04e6b58c0d_32_32.jpg
     * avatar64 : Uploads/Avatar/2016-08-02/57a04e6b58c0d_64_64.jpg
     * avatar128 : Uploads/Avatar/2016-08-02/57a04e6b58c0d_128_128.jpg
     * avatar256 : Uploads/Avatar/2016-08-02/57a04e6b58c0d_256_256.jpg
     * avatar512 : Uploads/Avatar/2016-08-02/57a04e6b58c0d_512_512.jpg
     * space_url : /api.php?s=/Ucenter/Index/index/uid/325.html
     * space_link :
     * <a ucard="325" target="_blank" href="/api.php?s=/Ucenter/Index/index/uid/325.html">昨日重现</a>
     * space_mob_url : /api.php?s=/Mob/User/index/uid/325.html
     * uid : 325
     * nickname : 昨日重现
     * rank_link : []
     * title : Lv1 无名小螺
     * score2 : 200
     * real_nickname : 昨日重现
     */

    private UserBean user;

    public int getId() {
      return id;
    }

    public void setId(int id) {
      this.id = id;
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

    public int getComment_count() {
      return comment_count;
    }

    public void setComment_count(int comment_count) {
      this.comment_count = comment_count;
    }

    public int getRepost_count() {
      return repost_count;
    }

    public void setRepost_count(int repost_count) {
      this.repost_count = repost_count;
    }

    public int getCan_delete() {
      return can_delete;
    }

    public void setCan_delete(int can_delete) {
      this.can_delete = can_delete;
    }

    public String getIs_top() {
      return is_top;
    }

    public void setIs_top(String is_top) {
      this.is_top = is_top;
    }

    public String getUid() {
      return uid;
    }

    public void setUid(String uid) {
      this.uid = uid;
    }

    public String getFrom() {
      return from;
    }

    public void setFrom(String from) {
      this.from = from;
    }

    public UserBean getUser() {
      return user;
    }

    public void setUser(UserBean user) {
      this.user = user;
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

    public static class WeiboDataBean {
      private String attach_ids;

      public String getAttach_ids() {
        return attach_ids;
      }

      public void setAttach_ids(String attach_ids) {
        this.attach_ids = attach_ids;
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
      private String title;
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

      public List<?> getRank_link() {
        return rank_link;
      }

      public void setRank_link(List<?> rank_link) {
        this.rank_link = rank_link;
      }
    }
  }
}
