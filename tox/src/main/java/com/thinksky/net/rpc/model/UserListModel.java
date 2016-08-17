/*
 * 文件名: UserListModel
 * 版    权：  Copyright Hengrtech Tech. Co. Ltd. All Rights Reserved.
 * 描    述: [该类的简要描述]
 * 创建人: zhaozeyang
 * 创建时间:16/8/16
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
 * @version [Taobei Client V20160411, 16/8/16]
 */
public class UserListModel extends BaseModel {

  /**
   * uid : 379
   * nickname : 天行健
   * sex : 0
   * birthday : 0000-00-00
   * qq :
   * login : 1
   * reg_ip : 2886732541
   * reg_time : 1468675799
   * last_login_ip : 2886732541
   * last_login_time : 1469756873
   * status : 1
   * last_login_role : 1
   * show_role : 1
   * signature :
   * pos_province : null
   * pos_city : null
   * pos_district : null
   * pos_community : 0
   * score1 : 26
   * score2 : 41
   * score3 : 0
   * score4 : 0
   * con_check : 0
   * total_check : 0
   * isexpert : null
   * isfactory : 1
   * factory_name :
   * longitude : 121.60028415245374
   * latitude : 37.388576821226586
   * isdisplay : 1
   * data : 793,794,792
   * mobile1 :
   * wechat :
   * iswechat : 0
   * address : 山东省烟台市牟平区西关路562号
   * expand_info : {"qq":"","生日":"2016-08-16"}
   * avatar : {"avatar32":"Public/images/default_avatar_32_32.jpg",
   * "avatar64":"Public/images/default_avatar_64_64.jpg",
   * "avatar128":"Public/images/default_avatar_128_128.jpg",
   * "avatar256":"Public/images/default_avatar_256_256.jpg",
   * "avatar512":"Public/images/default_avatar_512_512.jpg"}
   * mobile :
   * cover_url : ["793","794","792"]
   * images : ["Uploads/Picture/2016-07-16/578a40939d97c_100_100.jpg",
   * "Uploads/Picture/2016-07-16/578a4093b5309_100_100.jpg","Uploads/Picture/2016-07-16
   * /578a3d026d02e_100_100.jpg"]
   * is_follow : 0
   */

  private List<ResultBean> result;

  public List<ResultBean> getResult() {
    return result;
  }

  public void setResult(List<ResultBean> result) {
    this.result = result;
  }

  public static class ResultBean {
    private String uid;
    private String nickname;
    private String sex;
    private String birthday;
    private String qq;
    private String login;
    private String reg_ip;
    private String reg_time;
    private String last_login_ip;
    private String last_login_time;
    private String status;
    private String last_login_role;
    private String show_role;
    private String signature;
    private Object pos_province;
    private Object pos_city;
    private Object pos_district;
    private String pos_community;
    private String score1;
    private String score2;
    private String score3;
    private String score4;
    private String con_check;
    private String total_check;
    private Object isexpert;
    private String isfactory;
    private String factory_name;
    private String longitude;
    private String latitude;
    private String isdisplay;
    private String data;
    private String mobile1;
    private String wechat;
    private String iswechat;
    private String address;
    /**
     * qq :
     * 生日 : 2016-08-16
     */

    private ExpandInfoBean expand_info;
    /**
     * avatar32 : Public/images/default_avatar_32_32.jpg
     * avatar64 : Public/images/default_avatar_64_64.jpg
     * avatar128 : Public/images/default_avatar_128_128.jpg
     * avatar256 : Public/images/default_avatar_256_256.jpg
     * avatar512 : Public/images/default_avatar_512_512.jpg
     */

    private AvatarBean avatar;
    private String mobile;
    private int is_follow;
    private List<String> cover_url;
    private List<String> images;

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

    public String getSex() {
      return sex;
    }

    public void setSex(String sex) {
      this.sex = sex;
    }

    public String getBirthday() {
      return birthday;
    }

    public void setBirthday(String birthday) {
      this.birthday = birthday;
    }

    public String getQq() {
      return qq;
    }

    public void setQq(String qq) {
      this.qq = qq;
    }

    public String getLogin() {
      return login;
    }

    public void setLogin(String login) {
      this.login = login;
    }

    public String getReg_ip() {
      return reg_ip;
    }

    public void setReg_ip(String reg_ip) {
      this.reg_ip = reg_ip;
    }

    public String getReg_time() {
      return reg_time;
    }

    public void setReg_time(String reg_time) {
      this.reg_time = reg_time;
    }

    public String getLast_login_ip() {
      return last_login_ip;
    }

    public void setLast_login_ip(String last_login_ip) {
      this.last_login_ip = last_login_ip;
    }

    public String getLast_login_time() {
      return last_login_time;
    }

    public void setLast_login_time(String last_login_time) {
      this.last_login_time = last_login_time;
    }

    public String getStatus() {
      return status;
    }

    public void setStatus(String status) {
      this.status = status;
    }

    public String getLast_login_role() {
      return last_login_role;
    }

    public void setLast_login_role(String last_login_role) {
      this.last_login_role = last_login_role;
    }

    public String getShow_role() {
      return show_role;
    }

    public void setShow_role(String show_role) {
      this.show_role = show_role;
    }

    public String getSignature() {
      return signature;
    }

    public void setSignature(String signature) {
      this.signature = signature;
    }

    public Object getPos_province() {
      return pos_province;
    }

    public void setPos_province(Object pos_province) {
      this.pos_province = pos_province;
    }

    public Object getPos_city() {
      return pos_city;
    }

    public void setPos_city(Object pos_city) {
      this.pos_city = pos_city;
    }

    public Object getPos_district() {
      return pos_district;
    }

    public void setPos_district(Object pos_district) {
      this.pos_district = pos_district;
    }

    public String getPos_community() {
      return pos_community;
    }

    public void setPos_community(String pos_community) {
      this.pos_community = pos_community;
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

    public String getScore3() {
      return score3;
    }

    public void setScore3(String score3) {
      this.score3 = score3;
    }

    public String getScore4() {
      return score4;
    }

    public void setScore4(String score4) {
      this.score4 = score4;
    }

    public String getCon_check() {
      return con_check;
    }

    public void setCon_check(String con_check) {
      this.con_check = con_check;
    }

    public String getTotal_check() {
      return total_check;
    }

    public void setTotal_check(String total_check) {
      this.total_check = total_check;
    }

    public Object getIsexpert() {
      return isexpert;
    }

    public void setIsexpert(Object isexpert) {
      this.isexpert = isexpert;
    }

    public String getIsfactory() {
      return isfactory;
    }

    public void setIsfactory(String isfactory) {
      this.isfactory = isfactory;
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

    public String getIsdisplay() {
      return isdisplay;
    }

    public void setIsdisplay(String isdisplay) {
      this.isdisplay = isdisplay;
    }

    public String getData() {
      return data;
    }

    public void setData(String data) {
      this.data = data;
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

    public String getIswechat() {
      return iswechat;
    }

    public void setIswechat(String iswechat) {
      this.iswechat = iswechat;
    }

    public String getAddress() {
      return address;
    }

    public void setAddress(String address) {
      this.address = address;
    }

    public ExpandInfoBean getExpand_info() {
      return expand_info;
    }

    public void setExpand_info(ExpandInfoBean expand_info) {
      this.expand_info = expand_info;
    }

    public AvatarBean getAvatar() {
      return avatar;
    }

    public void setAvatar(AvatarBean avatar) {
      this.avatar = avatar;
    }

    public String getMobile() {
      return mobile;
    }

    public void setMobile(String mobile) {
      this.mobile = mobile;
    }

    public int getIs_follow() {
      return is_follow;
    }

    public void setIs_follow(int is_follow) {
      this.is_follow = is_follow;
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

    public static class AvatarBean {
      private String avatar32;
      private String avatar64;
      private String avatar128;
      private String avatar256;
      private String avatar512;

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
    }
  }
}
