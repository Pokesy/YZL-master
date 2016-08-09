/*
 * 文件名: UserInfoModel
 * 版    权：  Copyright Hengrtech Tech. Co. Ltd. All Rights Reserved.
 * 描    述: [该类的简要描述]
 * 创建人: zhaozeyang
 * 创建时间:16/8/4
 * 
 * 修改人：
 * 修改时间:
 * 修改内容：[修改内容]
 */
package com.thinksky.net.rpc.model;

import android.text.TextUtils;
import com.thinksky.net.rpc.service.NetConstant;
import java.io.Serializable;
import java.util.List;

/**
 * [一句话功能简述]<BR>
 * [功能详细描述]
 *
 * @author zhaozeyang
 * @version [Taobei Client V20160411, 16/8/4]
 */
public class UserInfoModel extends BaseModel implements Serializable {

  public static final String GENDER_MALE = "1";
  public static final String GENDER_FEMALE = "2";
  /**
   * avatar32 : Uploads/Avatar/2016-08-02/57a04e6b58c0d_32_32.jpg
   * avatar64 : Uploads/Avatar/2016-08-02/57a04e6b58c0d_64_64.jpg
   * avatar128 : Uploads/Avatar/2016-08-02/57a04e6b58c0d_128_128.jpg
   * avatar256 : Uploads/Avatar/2016-08-02/57a04e6b58c0d_256_256.jpg
   * avatar512 : Uploads/Avatar/2016-08-02/57a04e6b58c0d_512_512.jpg
   * sex : 0
   * nickname : 昨日重现
   * rank_link : []
   * expand_info : {"qq":"","生日":"2016-08-04"}
   * fans : 2
   * following : 1
   * title : Lv1 无名小螺
   * signature : 界面都这么丑，受不鸟
   * birthday : 0000-00-00
   * pos_city : {"id":"110100","name":"北京市","level":"2","upid":"110000"}
   * pos_district : null
   * pos_province : {"id":"110000","name":"北京市","level":"1","upid":"0"}
   * isfactory : 0
   * isdisplay : 0
   * iswechat : 0
   * score1 : 87
   * score2 : 87
   * qq :
   * factory_name :
   * longitude :
   * latitude :
   * address :
   * mobile1 :
   * wechat :
   * username :
   * email :
   * real_nickname : 昨日重现
   * score : 87
   * p_city : 北京市
   * p_province : 北京市
   * uid : 325
   * mobile :
   * data : false
   * cover_url : [""]
   * images : [""]
   * is_follow : 0
   */

  private String avatar32;
  private String avatar64;
  private String avatar128;
  private String avatar256;
  private String avatar512;
  private String avatar;
  private String sex;
  private String nickname;
  /**
   * qq :
   * 生日 : 2016-08-04
   */

  private ExpandInfoBean expand_info;
  private String fans;
  private String following;
  private String title;
  private String signature;
  private String birthday;
  private String isfactory;
  private String isdisplay;
  private String iswechat;
  private String score1;
  private String score2;
  private String qq;
  private String factory_name;
  private String longitude;
  private String latitude;
  private String address;
  private String mobile1;
  private String wechat;
  private String username;
  private String email;
  private String real_nickname;
  private String score;
  private String p_city;
  private String p_province;
  private String uid;
  private String mobile;
  private boolean data;
  private int is_follow;
  private List<?> rank_link;
  private List<String> cover_url;
  private List<String> images;

  public String getAvatar() {
    return avatar;
  }

  public void setAvatar(String avatar) {
    this.avatar = avatar;
  }

  public String getAvatar32() {
    if (!TextUtils.isEmpty(avatar32) && avatar32.startsWith(NetConstant.BASE_URL)) {
      return avatar32;
    }
    return NetConstant.BASE_URL + avatar32;
  }

  public void setAvatar32(String avatar32) {
    this.avatar32 = avatar32;
  }

  public String getAvatar64() {
    if (!TextUtils.isEmpty(avatar64) && avatar64.startsWith(NetConstant.BASE_URL)) {
      return avatar64;
    }
    return NetConstant.BASE_URL + avatar64;
  }

  public void setAvatar64(String avatar64) {
    this.avatar64 = avatar64;
  }

  public String getAvatar128() {
    if (!TextUtils.isEmpty(avatar128) && avatar128.startsWith(NetConstant.BASE_URL)) {
      return avatar128;
    }
    return NetConstant.BASE_URL + avatar128;
  }

  public void setAvatar128(String avatar128) {
    this.avatar128 = avatar128;
  }

  public String getAvatar256() {
    if (!TextUtils.isEmpty(avatar256) && avatar256.startsWith(NetConstant.BASE_URL)) {
      return avatar256;
    }
    return NetConstant.BASE_URL + avatar256;
  }

  public void setAvatar256(String avatar256) {
    this.avatar256 = avatar256;
  }

  public String getAvatar512() {
    if (!TextUtils.isEmpty(avatar512) && avatar512.startsWith(NetConstant.BASE_URL)) {
      return avatar512;
    }
    return NetConstant.BASE_URL + avatar512;
  }

  public void setAvatar512(String avatar512) {
    this.avatar512 = avatar512;
  }

  public String getSex() {
    return sex;
  }

  public void setSex(String sex) {
    this.sex = sex;
  }

  public String getNickname() {
    return nickname;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }

  public ExpandInfoBean getExpand_info() {
    return expand_info;
  }

  public void setExpand_info(ExpandInfoBean expand_info) {
    this.expand_info = expand_info;
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

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getSignature() {
    return signature;
  }

  public void setSignature(String signature) {
    this.signature = signature;
  }

  public String getBirthday() {
    return birthday;
  }

  public void setBirthday(String birthday) {
    this.birthday = birthday;
  }

  public String getIsfactory() {
    return isfactory;
  }

  public void setIsfactory(String isfactory) {
    this.isfactory = isfactory;
  }

  public String getIsdisplay() {
    return isdisplay;
  }

  public void setIsdisplay(String isdisplay) {
    this.isdisplay = isdisplay;
  }

  public String getIswechat() {
    return iswechat;
  }

  public void setIswechat(String iswechat) {
    this.iswechat = iswechat;
  }

  public String getScore1() {
    return score1;
  }

  public void setScore1(String score1) {
    this.score1 = score1;
  }

  public String getScore2() {
    return score2;
  }

  public void setScore2(String score2) {
    this.score2 = score2;
  }

  public String getQq() {
    return qq;
  }

  public void setQq(String qq) {
    this.qq = qq;
  }

  public String getFactory_name() {
    return factory_name;
  }

  public void setFactory_name(String factory_name) {
    this.factory_name = factory_name;
  }

  public String getLongitude() {
    return longitude;
  }

  public void setLongitude(String longitude) {
    this.longitude = longitude;
  }

  public String getLatitude() {
    return latitude;
  }

  public void setLatitude(String latitude) {
    this.latitude = latitude;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getMobile1() {
    return mobile1;
  }

  public void setMobile1(String mobile1) {
    this.mobile1 = mobile1;
  }

  public String getWechat() {
    return wechat;
  }

  public void setWechat(String wechat) {
    this.wechat = wechat;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getReal_nickname() {
    return real_nickname;
  }

  public void setReal_nickname(String real_nickname) {
    this.real_nickname = real_nickname;
  }

  public String getScore() {
    return score;
  }

  public void setScore(String score) {
    this.score = score;
  }

  public String getP_city() {
    return p_city;
  }

  public void setP_city(String p_city) {
    this.p_city = p_city;
  }

  public String getP_province() {
    return p_province;
  }

  public void setP_province(String p_province) {
    this.p_province = p_province;
  }

  public String getUid() {
    return uid;
  }

  public void setUid(String uid) {
    this.uid = uid;
  }

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public boolean isData() {
    return data;
  }

  public void setData(boolean data) {
    this.data = data;
  }

  public int getIs_follow() {
    return is_follow;
  }

  public void setIs_follow(int is_follow) {
    this.is_follow = is_follow;
  }

  public List<?> getRank_link() {
    return rank_link;
  }

  public void setRank_link(List<?> rank_link) {
    this.rank_link = rank_link;
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

  public static class ExpandInfoBean {
    private String qq;
    private String 生日;

    public String getQq() {
      return qq;
    }

    public void setQq(String qq) {
      this.qq = qq;
    }

    public String get生日() {
      return 生日;
    }

    public void set生日(String 生日) {
      this.生日 = 生日;
    }
  }

  public static class PosCityBean {
    private String id;
    private String name;
    private String level;
    private String upid;

    public String getId() {
      return id;
    }

    public void setId(String id) {
      this.id = id;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getLevel() {
      return level;
    }

    public void setLevel(String level) {
      this.level = level;
    }

    public String getUpid() {
      return upid;
    }

    public void setUpid(String upid) {
      this.upid = upid;
    }
  }

  public static class PosProvinceBean {
    private String id;
    private String name;
    private String level;
    private String upid;

    public String getId() {
      return id;
    }

    public void setId(String id) {
      this.id = id;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getLevel() {
      return level;
    }

    public void setLevel(String level) {
      this.level = level;
    }

    public String getUpid() {
      return upid;
    }

    public void setUpid(String upid) {
      this.upid = upid;
    }
  }
}
