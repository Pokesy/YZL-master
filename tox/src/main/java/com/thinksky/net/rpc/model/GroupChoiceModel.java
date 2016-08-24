/*
 * 文件名: GroupChoiceModel
 * 版    权：  Copyright Hengrtech Tech. Co. Ltd. All Rights Reserved.
 * 描    述: [该类的简要描述]
 * 创建人: zhaozeyang
 * 创建时间:16/8/25
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
 * @version [Taobei Client V20160411, 16/8/25]
 */
public class GroupChoiceModel extends BaseModel {

  /**
   * id : 14
   * uid : 1
   * title : 魟鱼
   * create_time : 05月26日 16:16
   * post_count : 6
   * status : 1
   * allow_user_group :
   * sort : 0
   * logo : /Uploads/Picture/2016-05-27/5747a16aeb02e.png
   * background : false
   * type_id : 3
   * detail : 魟鱼爱好者的天堂
   * type : 0
   * activity : 11
   * member_count : 26
   * member_alias : 小魟
   * notice :
   * is_join : 0
   * user : {"avatar32":"Uploads/Avatar/2016-04-25/571d78270d2f2_32_32.png",
   * "avatar64":"Uploads/Avatar/2016-04-25/571d78270d2f2_64_64.png",
   * "avatar128":"Uploads/Avatar/2016-04-25/571d78270d2f2_128_128.png",
   * "avatar256":"Uploads/Avatar/2016-04-25/571d78270d2f2_256_256.png",
   * "avatar512":"Uploads/Avatar/2016-04-25/571d78270d2f2_512_512.png","uid":"1",
   * "username":"admin","nickname":"鱼小乐","real_nickname":"鱼小乐"}
   * PostNew : [{"id":"60","uid":"1","group_id":"14","title":"共赏","parse":"0","content":"",
   * "create_time":"1464251456","update_time":"1464251456","status":"1",
   * "last_reply_time":"1464251456","view_count":"63","reply_count":"2","is_top":"0",
   * "cate_id":"0","summary":"","cover":"/Uploads/Picture/2016-05-26/5746b0d1b4227.jpg",
   * "supportCount":"2","user":{"avatar32":"Uploads/Avatar/2016-04-25/571d78270d2f2_32_32.png",
   * "avatar64":"Uploads/Avatar/2016-04-25/571d78270d2f2_64_64.png",
   * "avatar128":"Uploads/Avatar/2016-04-25/571d78270d2f2_128_128.png",
   * "avatar256":"Uploads/Avatar/2016-04-25/571d78270d2f2_256_256.png",
   * "avatar512":"Uploads/Avatar/2016-04-25/571d78270d2f2_512_512.png","space_url":"/api
   * .php?s=/Ucenter/Index/index/uid/1.html",
   * "space_link
   * ":"<a ucard=\"1\" target=\"_blank\" href=\"/api.php?s=/Ucenter/Index/index/uid/1.html\">鱼小乐<\/a>","space_mob_url":"/api.php?s=/Mob/User/index/uid/1.html","uid":"1","nickname":"鱼小乐","fans":"12","following":"1","weibocount":"22","title":"Lv3 鱼坛新秀","score2":"5258","real_nickname":"鱼小乐"}}]
   * menmberCount : 26
   * GroupMenmber : [{"id":"119","group_id":"14","uid":"1","status":"1",
   * "create_time":"1464250592","update_time":"1464250592","activity":"3",
   * "last_view":"1471252684","position":"3",
   * "user":{"avatar32":"Uploads/Avatar/2016-04-25/571d78270d2f2_32_32.png",
   * "avatar64":"Uploads/Avatar/2016-04-25/571d78270d2f2_64_64.png",
   * "avatar128":"Uploads/Avatar/2016-04-25/571d78270d2f2_128_128.png",
   * "avatar256":"Uploads/Avatar/2016-04-25/571d78270d2f2_256_256.png",
   * "avatar512":"Uploads/Avatar/2016-04-25/571d78270d2f2_512_512.png","space_url":"/api
   * .php?s=/Ucenter/Index/index/uid/1.html",
   * "space_link
   * ":"<a ucard=\"1\" target=\"_blank\" href=\"/api.php?s=/Ucenter/Index/index/uid/1.html\">鱼小乐<\/a>","space_mob_url":"/api.php?s=/Mob/User/index/uid/1.html","uid":"1","nickname":"鱼小乐","fans":"12","following":"1","weibocount":"22","title":"Lv3 鱼坛新秀","score2":"5258","real_nickname":"鱼小乐"},"isCreator":"1"},{"id":"124","group_id":"14","uid":"214","status":"1","create_time":"1464320340","update_time":"1464320340","activity":"1","last_view":"0","position":"1","user":{"avatar32":"Public/images/default_avatar_32_32.jpg","avatar64":"Public/images/default_avatar_64_64.jpg","avatar128":"Public/images/default_avatar_128_128.jpg","avatar256":"Public/images/default_avatar_256_256.jpg","avatar512":"Public/images/default_avatar_512_512.jpg","space_url":"/api.php?s=/Ucenter/Index/index/uid/214.html","space_link":"<a ucard=\"214\" target=\"_blank\" href=\"/api.php?s=/Ucenter/Index/index/uid/214.html\">13893000015<\/a>","space_mob_url":"/api.php?s=/Mob/User/index/uid/214.html","uid":"214","nickname":"13893000015","fans":"3","following":"1","weibocount":"1","title":"Lv1 无名小螺","score2":"10","real_nickname":"13893000015"},"isCreator":"0"},{"id":"126","group_id":"14","uid":"223","status":"1","create_time":"1464648215","update_time":"1464648215","activity":"0","last_view":"0","position":"1","user":{"avatar32":"Uploads/Avatar/2016-05-31/574cc0f00a68f_32_32.png","avatar64":"Uploads/Avatar/2016-05-31/574cc0f00a68f_64_64.png","avatar128":"Uploads/Avatar/2016-05-31/574cc0f00a68f_128_128.png","avatar256":"Uploads/Avatar/2016-05-31/574cc0f00a68f_256_256.png","avatar512":"Uploads/Avatar/2016-05-31/574cc0f00a68f_512_512.png","space_url":"/api.php?s=/Ucenter/Index/index/uid/223.html","space_link":"<a ucard=\"223\" target=\"_blank\" href=\"/api.php?s=/Ucenter/Index/index/uid/223.html\">小帅<\/a>","space_mob_url":"/api.php?s=/Mob/User/index/uid/223.html","uid":"223","nickname":"小帅","fans":"2","following":"0","weibocount":"0","title":"Lv1 无名小螺","score2":"10","real_nickname":"小帅"},"isCreator":"0"},{"id":"135","group_id":"14","uid":"148","status":"1","create_time":"1466988228","update_time":"1466988228","activity":"0","last_view":"0","position":"1","user":{"avatar32":"Public/images/default_avatar_32_32.jpg","avatar64":"Public/images/default_avatar_64_64.jpg","avatar128":"Public/images/default_avatar_128_128.jpg","avatar256":"Public/images/default_avatar_256_256.jpg","avatar512":"Public/images/default_avatar_512_512.jpg","space_url":"/api.php?s=/Ucenter/Index/index/uid/148.html","space_link":"<a ucard=\"148\" target=\"_blank\" href=\"/api.php?s=/Ucenter/Index/index/uid/148.html\"><\/a>","space_mob_url":"/api.php?s=/Mob/User/index/uid/148.html","fans":"1","following":"0","weibocount":"0","title":"Lv1 无名小螺","real_nickname":null},"isCreator":"0"},{"id":"137","group_id":"14","uid":"315","status":"1","create_time":"1467814352","update_time":"1467814352","activity":"0","last_view":"0","position":"1","user":{"avatar32":"Uploads/Avatar/2016-07-06/577d10dd83c90_32_32.png","avatar64":"Uploads/Avatar/2016-07-06/577d10dd83c90_64_64.png","avatar128":"Uploads/Avatar/2016-07-06/577d10dd83c90_128_128.png","avatar256":"Uploads/Avatar/2016-07-06/577d10dd83c90_256_256.png","avatar512":"Uploads/Avatar/2016-07-06/577d10dd83c90_512_512.png","space_url":"/api.php?s=/Ucenter/Index/index/uid/315.html","space_link":"<a ucard=\"315\" target=\"_blank\" href=\"/api.php?s=/Ucenter/Index/index/uid/315.html\">13931287588<\/a>","space_mob_url":"/api.php?s=/Mob/User/index/uid/315.html","uid":"315","nickname":"13931287588","fans":"2","following":"0","weibocount":"0","title":"Lv1 无名小螺","score2":"10","real_nickname":"13931287588"},"isCreator":"0"},{"id":"139","group_id":"14","uid":"342","status":"1","create_time":"1468383870","update_time":"1468383870","activity":"0","last_view":"0","position":"1","user":{"avatar32":"Uploads/Avatar/2016-07-15/5788b350d2df0_32_32.png","avatar64":"Uploads/Avatar/2016-07-15/5788b350d2df0_64_64.png","avatar128":"Uploads/Avatar/2016-07-15/5788b350d2df0_128_128.png","avatar256":"Uploads/Avatar/2016-07-15/5788b350d2df0_256_256.png","avatar512":"Uploads/Avatar/2016-07-15/5788b350d2df0_512_512.png","space_url":"/api.php?s=/Ucenter/Index/index/uid/342.html","space_link":"<a ucard=\"342\" target=\"_blank\" href=\"/api.php?s=/Ucenter/Index/index/uid/342.html\">18633111111<\/a>","space_mob_url":"/api.php?s=/Mob/User/index/uid/342.html","uid":"342","nickname":"18633111111","fans":"2","following":"1","weibocount":"0","title":"Lv1 无名小螺","score2":"10","real_nickname":"18633111111"},"isCreator":"0"},{"id":"140","group_id":"14","uid":"344","status":"1","create_time":"1468453933","update_time":"1468453933","activity":"0","last_view":"0","position":"1","user":{"avatar32":"Public/images/default_avatar_32_32.jpg","avatar64":"Public/images/default_avatar_64_64.jpg","avatar128":"Public/images/default_avatar_128_128.jpg","avatar256":"Public/images/default_avatar_256_256.jpg","avatar512":"Public/images/default_avatar_512_512.jpg","space_url":"/api.php?s=/Ucenter/Index/index/uid/344.html","space_link":"<a ucard=\"344\" target=\"_blank\" href=\"/api.php?s=/Ucenter/Index/index/uid/344.html\">13942508649<\/a>","space_mob_url":"/api.php?s=/Mob/User/index/uid/344.html","uid":"344","nickname":"13942508649","fans":"1","following":"0","weibocount":"0","title":"Lv1 无名小螺","score2":"10","real_nickname":"13942508649"},"isCreator":"0"},{"id":"141","group_id":"14","uid":"357","status":"1","create_time":"1468584255","update_time":"1468584255","activity":"0","last_view":"0","position":"1","user":{"avatar32":"Public/images/default_avatar_32_32.jpg","avatar64":"Public/images/default_avatar_64_64.jpg","avatar128":"Public/images/default_avatar_128_128.jpg","avatar256":"Public/images/default_avatar_256_256.jpg","avatar512":"Public/images/default_avatar_512_512.jpg","space_url":"/api.php?s=/Ucenter/Index/index/uid/357.html","space_link":"<a ucard=\"357\" target=\"_blank\" href=\"/api.php?s=/Ucenter/Index/index/uid/357.html\">大熊猫<\/a>","space_mob_url":"/api.php?s=/Mob/User/index/uid/357.html","uid":"357","nickname":"大熊猫","fans":"2","following":"1","weibocount":"0","title":"Lv1 无名小螺","score2":"10","real_nickname":"大熊猫"},"isCreator":"0"},{"id":"142","group_id":"14","uid":"359","status":"1","create_time":"1468593976","update_time":"1468593976","activity":"0","last_view":"0","position":"1","user":{"avatar32":"Public/images/default_avatar_32_32.jpg","avatar64":"Public/images/default_avatar_64_64.jpg","avatar128":"Public/images/default_avatar_128_128.jpg","avatar256":"Public/images/default_avatar_256_256.jpg","avatar512":"Public/images/default_avatar_512_512.jpg","space_url":"/api.php?s=/Ucenter/Index/index/uid/359.html","space_link":"<a ucard=\"359\" target=\"_blank\" href=\"/api.php?s=/Ucenter/Index/index/uid/359.html\">威海小R<\/a>","space_mob_url":"/api.php?s=/Mob/User/index/uid/359.html","uid":"359","nickname":"威海小R","fans":"0","following":"0","weibocount":"0","title":"Lv1 无名小螺","score2":"10","real_nickname":"威海小R"},"isCreator":"0"},{"id":"143","group_id":"14","uid":"353","status":"1","create_time":"1468601722","update_time":"1468601722","activity":"0","last_view":"0","position":"1","user":{"avatar32":"Uploads/Avatar/2016-07-16/5789151903e3a_32_32.png","avatar64":"Uploads/Avatar/2016-07-16/5789151903e3a_64_64.png","avatar128":"Uploads/Avatar/2016-07-16/5789151903e3a_128_128.png","avatar256":"Uploads/Avatar/2016-07-16/5789151903e3a_256_256.png","avatar512":"Uploads/Avatar/2016-07-16/5789151903e3a_512_512.png","space_url":"/api.php?s=/Ucenter/Index/index/uid/353.html","space_link":"<a ucard=\"353\" target=\"_blank\" href=\"/api.php?s=/Ucenter/Index/index/uid/353.html\">13812651768<\/a>","space_mob_url":"/api.php?s=/Mob/User/index/uid/353.html","uid":"353","nickname":"13812651768","fans":"0","following":"0","weibocount":"0","title":"Lv1 无名小螺","score2":"10","real_nickname":"13812651768"},"isCreator":"0"}]
   * GroupCreator : {"uid":"1"}
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
    /**
     * uid : 1
     */

    private GroupCreatorBean GroupCreator;
    /**
     * id : 60
     * uid : 1
     * group_id : 14
     * title : 共赏
     * parse : 0
     * content :
     * create_time : 1464251456
     * update_time : 1464251456
     * status : 1
     * last_reply_time : 1464251456
     * view_count : 63
     * reply_count : 2
     * is_top : 0
     * cate_id : 0
     * summary :
     * cover : /Uploads/Picture/2016-05-26/5746b0d1b4227.jpg
     * supportCount : 2
     * user : {"avatar32":"Uploads/Avatar/2016-04-25/571d78270d2f2_32_32.png",
     * "avatar64":"Uploads/Avatar/2016-04-25/571d78270d2f2_64_64.png",
     * "avatar128":"Uploads/Avatar/2016-04-25/571d78270d2f2_128_128.png",
     * "avatar256":"Uploads/Avatar/2016-04-25/571d78270d2f2_256_256.png",
     * "avatar512":"Uploads/Avatar/2016-04-25/571d78270d2f2_512_512.png","space_url":"/api
     * .php?s=/Ucenter/Index/index/uid/1.html",
     * "space_link
     * ":"<a ucard=\"1\" target=\"_blank\" href=\"/api.php?s=/Ucenter/Index/index/uid/1.html\">鱼小乐<\/a>","space_mob_url":"/api.php?s=/Mob/User/index/uid/1.html","uid":"1","nickname":"鱼小乐","fans":"12","following":"1","weibocount":"22","title":"Lv3 鱼坛新秀","score2":"5258","real_nickname":"鱼小乐"}
     */

    private List<PostNewBean> PostNew;
    /**
     * id : 119
     * group_id : 14
     * uid : 1
     * status : 1
     * create_time : 1464250592
     * update_time : 1464250592
     * activity : 3
     * last_view : 1471252684
     * position : 3
     * user : {"avatar32":"Uploads/Avatar/2016-04-25/571d78270d2f2_32_32.png",
     * "avatar64":"Uploads/Avatar/2016-04-25/571d78270d2f2_64_64.png",
     * "avatar128":"Uploads/Avatar/2016-04-25/571d78270d2f2_128_128.png",
     * "avatar256":"Uploads/Avatar/2016-04-25/571d78270d2f2_256_256.png",
     * "avatar512":"Uploads/Avatar/2016-04-25/571d78270d2f2_512_512.png","space_url":"/api
     * .php?s=/Ucenter/Index/index/uid/1.html",
     * "space_link
     * ":"<a ucard=\"1\" target=\"_blank\" href=\"/api.php?s=/Ucenter/Index/index/uid/1.html\">鱼小乐<\/a>","space_mob_url":"/api.php?s=/Mob/User/index/uid/1.html","uid":"1","nickname":"鱼小乐","fans":"12","following":"1","weibocount":"22","title":"Lv3 鱼坛新秀","score2":"5258","real_nickname":"鱼小乐"}
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

    public String getMenmberCount() {
      return menmberCount;
    }

    public void setMenmberCount(String menmberCount) {
      this.menmberCount = menmberCount;
    }

    public GroupCreatorBean getGroupCreator() {
      return GroupCreator;
    }

    public void setGroupCreator(GroupCreatorBean GroupCreator) {
      this.GroupCreator = GroupCreator;
    }

    public List<PostNewBean> getPostNew() {
      return PostNew;
    }

    public void setPostNew(List<PostNewBean> PostNew) {
      this.PostNew = PostNew;
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

    public static class GroupCreatorBean {
      private String uid;

      public String getUid() {
        return uid;
      }

      public void setUid(String uid) {
        this.uid = uid;
      }
    }

    public static class PostNewBean {
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
      private String supportCount;
      /**
       * avatar32 : Uploads/Avatar/2016-04-25/571d78270d2f2_32_32.png
       * avatar64 : Uploads/Avatar/2016-04-25/571d78270d2f2_64_64.png
       * avatar128 : Uploads/Avatar/2016-04-25/571d78270d2f2_128_128.png
       * avatar256 : Uploads/Avatar/2016-04-25/571d78270d2f2_256_256.png
       * avatar512 : Uploads/Avatar/2016-04-25/571d78270d2f2_512_512.png
       * space_url : /api.php?s=/Ucenter/Index/index/uid/1.html
       * space_link :
       * <a ucard="1" target="_blank" href="/api.php?s=/Ucenter/Index/index/uid/1.html">鱼小乐</a>
       * space_mob_url : /api.php?s=/Mob/User/index/uid/1.html
       * uid : 1
       * nickname : 鱼小乐
       * fans : 12
       * following : 1
       * weibocount : 22
       * title : Lv3 鱼坛新秀
       * score2 : 5258
       * real_nickname : 鱼小乐
       */

      private UserBean user;

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

      public String getSupportCount() {
        return supportCount;
      }

      public void setSupportCount(String supportCount) {
        this.supportCount = supportCount;
      }

      public UserBean getUser() {
        return user;
      }

      public void setUser(UserBean user) {
        this.user = user;
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
       * avatar32 : Uploads/Avatar/2016-04-25/571d78270d2f2_32_32.png
       * avatar64 : Uploads/Avatar/2016-04-25/571d78270d2f2_64_64.png
       * avatar128 : Uploads/Avatar/2016-04-25/571d78270d2f2_128_128.png
       * avatar256 : Uploads/Avatar/2016-04-25/571d78270d2f2_256_256.png
       * avatar512 : Uploads/Avatar/2016-04-25/571d78270d2f2_512_512.png
       * space_url : /api.php?s=/Ucenter/Index/index/uid/1.html
       * space_link :
       * <a ucard="1" target="_blank" href="/api.php?s=/Ucenter/Index/index/uid/1.html">鱼小乐</a>
       * space_mob_url : /api.php?s=/Mob/User/index/uid/1.html
       * uid : 1
       * nickname : 鱼小乐
       * fans : 12
       * following : 1
       * weibocount : 22
       * title : Lv3 鱼坛新秀
       * score2 : 5258
       * real_nickname : 鱼小乐
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
        private String space_url;
        private String space_link;
        private String space_mob_url;
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
}
