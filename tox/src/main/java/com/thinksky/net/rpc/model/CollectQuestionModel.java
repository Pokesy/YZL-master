/*
 * 文件名: CollectQuestionModel
 * 版    权：  Copyright Hengrtech Tech. Co. Ltd. All Rights Reserved.
 * 描    述: [该类的简要描述]
 * 创建人: zhaozeyang
 * 创建时间:16/8/10
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
 * @version [Taobei Client V20160411, 16/8/10]
 */
public class CollectQuestionModel extends BaseModel {

  /**
   * id : 170
   * uid : 397
   * category : 8
   * title : text
   * description1 : text
   * answer_num : 0
   * best_answer : 0
   * good_question : 0
   * status : 1
   * is_recommend : 0
   * create_time : 07月29日 16:25
   * update_time : 07月29日 16:25
   * data : a:1:{s:10:"attach_ids";s:4:"null";}
   * score : 0
   * imgList : []
   * user : {"avatar32":"Uploads/Avatar/2016-07-29/579af5d7526fa_32_32.png",
   * "avatar64":"Uploads/Avatar/2016-07-29/579af5d7526fa_64_64.png",
   * "avatar128":"Uploads/Avatar/2016-07-29/579af5d7526fa_128_128.png",
   * "avatar256":"Uploads/Avatar/2016-07-29/579af5d7526fa_256_256.png",
   * "avatar512":"Uploads/Avatar/2016-07-29/579af5d7526fa_512_512.png","uid":"397",
   * "nickname":"萨玛","username":"","real_nickname":"萨玛"}
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
    private String category;
    private String title;
    private String description1;
    private String answer_num;
    private String best_answer;
    private String good_question;
    private String status;
    private String is_recommend;
    private String create_time;
    private String update_time;
    private String data;
    private String score;
    /**
     * avatar32 : Uploads/Avatar/2016-07-29/579af5d7526fa_32_32.png
     * avatar64 : Uploads/Avatar/2016-07-29/579af5d7526fa_64_64.png
     * avatar128 : Uploads/Avatar/2016-07-29/579af5d7526fa_128_128.png
     * avatar256 : Uploads/Avatar/2016-07-29/579af5d7526fa_256_256.png
     * avatar512 : Uploads/Avatar/2016-07-29/579af5d7526fa_512_512.png
     * uid : 397
     * nickname : 萨玛
     * username :
     * real_nickname : 萨玛
     */

    private UserBean user;
    private List<?> imgList;

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

    public String getCategory() {
      return category;
    }

    public void setCategory(String category) {
      this.category = category;
    }

    public String getTitle() {
      return title;
    }

    public void setTitle(String title) {
      this.title = title;
    }

    public String getDescription1() {
      return description1;
    }

    public void setDescription1(String description1) {
      this.description1 = description1;
    }

    public String getAnswer_num() {
      return answer_num;
    }

    public void setAnswer_num(String answer_num) {
      this.answer_num = answer_num;
    }

    public String getBest_answer() {
      return best_answer;
    }

    public void setBest_answer(String best_answer) {
      this.best_answer = best_answer;
    }

    public String getGood_question() {
      return good_question;
    }

    public void setGood_question(String good_question) {
      this.good_question = good_question;
    }

    public String getStatus() {
      return status;
    }

    public void setStatus(String status) {
      this.status = status;
    }

    public String getIs_recommend() {
      return is_recommend;
    }

    public void setIs_recommend(String is_recommend) {
      this.is_recommend = is_recommend;
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

    public String getData() {
      return data;
    }

    public void setData(String data) {
      this.data = data;
    }

    public String getScore() {
      return score;
    }

    public void setScore(String score) {
      this.score = score;
    }

    public UserBean getUser() {
      return user;
    }

    public void setUser(UserBean user) {
      this.user = user;
    }

    public List<?> getImgList() {
      return imgList;
    }

    public void setImgList(List<?> imgList) {
      this.imgList = imgList;
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
