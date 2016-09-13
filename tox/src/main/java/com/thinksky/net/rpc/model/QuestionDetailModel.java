/*
 * 文件名: QuestionDetailModel
 * 版    权：  Copyright Hengrtech Tech. Co. Ltd. All Rights Reserved.
 * 描    述: [该类的简要描述]
 * 创建人: zhaozeyang
 * 创建时间:16/9/13
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
 * @version [Taobei Client V20160411, 16/9/13]
 */
public class QuestionDetailModel extends BaseModel {

  /**
   * id : 106
   * uid : 162
   * category : 5
   * title : 有奖征集养鱼爱好者
   * description1 : 平台内部封闭测试，您都能找的到，小乐实在佩服！接下来您在平台任意板块分享自己的爱鱼并在本帖回答，就有机会中奖哦，奖品保密，大家可以试试，万一中了呢^_^
   * answer_num : 5
   * best_answer : 0
   * good_question : 0
   * status : 1
   * is_recommend : 0
   * create_time : 05月27日 10:50
   * update_time : 05月27日 10:50
   * data : a:1:{s:10:"attach_ids";s:3:"420";}
   * score : 20
   * Questionimages : ["Uploads/Picture/2016-05-27/5747b5bdba105_100_100.png"]
   * imgList : []
   * category_title : 魟鱼
   * QuestionAnswer : [{"id":"329","uid":"391","question_id":"106","content":"在哪里？",
   * "support":"0","oppose":"0","status":"1","update_time":"1473217302",
   * "create_time":"1473217302","user":{"avatar32":"Uploads/Avatar/2016-09-07/57cf8925abc50_32_32
   * .jpg","avatar64":"Uploads/Avatar/2016-09-07/57cf8925abc50_64_64.jpg",
   * "avatar128":"Uploads/Avatar/2016-09-07/57cf8925abc50_128_128.jpg",
   * "avatar256":"Uploads/Avatar/2016-09-07/57cf8925abc50_256_256.jpg",
   * "avatar512":"Uploads/Avatar/2016-09-07/57cf8925abc50_512_512.jpg","uid":"391",
   * "nickname":"15589510233","signature":"他来听我的演唱会","username":"",
   * "real_nickname":"15589510233"},"create_time1":"09月07日 11:01","update_time1":"09月07日 11:01",
   * "imgList":[],"isbest":0,"support_count":"0","is_supported":"0","issetbest":0},{"id":"328",
   * "uid":"391","question_id":"106","content":"回答到哪里了?","support":"0","oppose":"0","status":"1",
   * "update_time":"1473217286","create_time":"1473217286",
   * "user":{"avatar32":"Uploads/Avatar/2016-09-07/57cf8925abc50_32_32.jpg",
   * "avatar64":"Uploads/Avatar/2016-09-07/57cf8925abc50_64_64.jpg",
   * "avatar128":"Uploads/Avatar/2016-09-07/57cf8925abc50_128_128.jpg",
   * "avatar256":"Uploads/Avatar/2016-09-07/57cf8925abc50_256_256.jpg",
   * "avatar512":"Uploads/Avatar/2016-09-07/57cf8925abc50_512_512.jpg","uid":"391",
   * "nickname":"15589510233","signature":"他来听我的演唱会","username":"",
   * "real_nickname":"15589510233"},"create_time1":"09月07日 11:01","update_time1":"09月07日 11:01",
   * "imgList":[],"isbest":0,"support_count":"0","is_supported":"0","issetbest":0},{"id":"327",
   * "uid":"391","question_id":"106","content":"我不知道，我不知道，什么都不知道","support":"0","oppose":"0",
   * "status":"1","update_time":"1473217267","create_time":"1473217267",
   * "user":{"avatar32":"Uploads/Avatar/2016-09-07/57cf8925abc50_32_32.jpg",
   * "avatar64":"Uploads/Avatar/2016-09-07/57cf8925abc50_64_64.jpg",
   * "avatar128":"Uploads/Avatar/2016-09-07/57cf8925abc50_128_128.jpg",
   * "avatar256":"Uploads/Avatar/2016-09-07/57cf8925abc50_256_256.jpg",
   * "avatar512":"Uploads/Avatar/2016-09-07/57cf8925abc50_512_512.jpg","uid":"391",
   * "nickname":"15589510233","signature":"他来听我的演唱会","username":"",
   * "real_nickname":"15589510233"},"create_time1":"09月07日 11:01","update_time1":"09月07日 11:01",
   * "imgList":[],"isbest":0,"support_count":"0","is_supported":"0","issetbest":0},{"id":"212",
   * "uid":"148","question_id":"106","content":"Zha di le","support":"1","oppose":"0",
   * "status":"1","update_time":"1464769574","create_time":"1464769574",
   * "user":{"avatar32":"Public/images/default_avatar_32_32.jpg",
   * "avatar64":"Public/images/default_avatar_64_64.jpg",
   * "avatar128":"Public/images/default_avatar_128_128.jpg",
   * "avatar256":"Public/images/default_avatar_256_256.jpg",
   * "avatar512":"Public/images/default_avatar_512_512.jpg","real_nickname":null},
   * "create_time1":"06月01日 16:26","update_time1":"06月01日 16:26","imgList":[],"isbest":0,
   * "support_count":"1","is_supported":"0","issetbest":0},{"id":"211","uid":"214",
   * "question_id":"106","content":"咋地了？","support":"1","oppose":"0","status":"1",
   * "update_time":"1464320501","create_time":"1464320501",
   * "user":{"avatar32":"Public/images/default_avatar_32_32.jpg",
   * "avatar64":"Public/images/default_avatar_64_64.jpg",
   * "avatar128":"Public/images/default_avatar_128_128.jpg",
   * "avatar256":"Public/images/default_avatar_256_256.jpg",
   * "avatar512":"Public/images/default_avatar_512_512.jpg","uid":"214","nickname":"13893000015",
   * "signature":"","username":"","real_nickname":"13893000015"},"create_time1":"05月27日 11:41",
   * "update_time1":"05月27日 11:41","imgList":[],"isbest":0,"support_count":"1",
   * "is_supported":"0","issetbest":0}]
   * cover_url :
   * is_collection : 0
   * is_author : 0
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
    private String category_title;
    private String cover_url;
    private String is_collection;
    private String is_author;
    private List<String> Questionimages;
    private List<String> imgList;
    /**
     * id : 329
     * uid : 391
     * question_id : 106
     * content : 在哪里？
     * support : 0
     * oppose : 0
     * status : 1
     * update_time : 1473217302
     * create_time : 1473217302
     * user : {"avatar32":"Uploads/Avatar/2016-09-07/57cf8925abc50_32_32.jpg",
     * "avatar64":"Uploads/Avatar/2016-09-07/57cf8925abc50_64_64.jpg",
     * "avatar128":"Uploads/Avatar/2016-09-07/57cf8925abc50_128_128.jpg",
     * "avatar256":"Uploads/Avatar/2016-09-07/57cf8925abc50_256_256.jpg",
     * "avatar512":"Uploads/Avatar/2016-09-07/57cf8925abc50_512_512.jpg","uid":"391",
     * "nickname":"15589510233","signature":"他来听我的演唱会","username":"","real_nickname":"15589510233"}
     * create_time1 : 09月07日 11:01
     * update_time1 : 09月07日 11:01
     * imgList : []
     * isbest : 0
     * support_count : 0
     * is_supported : 0
     * issetbest : 0
     */

    private List<QuestionAnswerBean> QuestionAnswer;

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

    public String getCategory_title() {
      return category_title;
    }

    public void setCategory_title(String category_title) {
      this.category_title = category_title;
    }

    public String getCover_url() {
      return cover_url;
    }

    public void setCover_url(String cover_url) {
      this.cover_url = cover_url;
    }

    public String getIs_collection() {
      return is_collection;
    }

    public void setIs_collection(String is_collection) {
      this.is_collection = is_collection;
    }

    public String getIs_author() {
      return is_author;
    }

    public void setIs_author(String is_author) {
      this.is_author = is_author;
    }

    public List<String> getQuestionimages() {
      return Questionimages;
    }

    public void setQuestionimages(List<String> Questionimages) {
      this.Questionimages = Questionimages;
    }

    public List<String> getImgList() {
      return imgList;
    }

    public void setImgList(List<String> imgList) {
      this.imgList = imgList;
    }

    public List<QuestionAnswerBean> getQuestionAnswer() {
      return QuestionAnswer;
    }

    public void setQuestionAnswer(List<QuestionAnswerBean> QuestionAnswer) {
      this.QuestionAnswer = QuestionAnswer;
    }

    public static class QuestionAnswerBean {
      private String id;
      private String uid;
      private String question_id;
      private String content;
      private String support;
      private String oppose;
      private String status;
      private String update_time;
      private String create_time;
      /**
       * avatar32 : Uploads/Avatar/2016-09-07/57cf8925abc50_32_32.jpg
       * avatar64 : Uploads/Avatar/2016-09-07/57cf8925abc50_64_64.jpg
       * avatar128 : Uploads/Avatar/2016-09-07/57cf8925abc50_128_128.jpg
       * avatar256 : Uploads/Avatar/2016-09-07/57cf8925abc50_256_256.jpg
       * avatar512 : Uploads/Avatar/2016-09-07/57cf8925abc50_512_512.jpg
       * uid : 391
       * nickname : 15589510233
       * signature : 他来听我的演唱会
       * username :
       * real_nickname : 15589510233
       */

      private UserBean user;
      private String create_time1;
      private String update_time1;
      private int isbest;
      private String support_count;
      private String is_supported;
      private int issetbest;
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

      public String getQuestion_id() {
        return question_id;
      }

      public void setQuestion_id(String question_id) {
        this.question_id = question_id;
      }

      public String getContent() {
        return content;
      }

      public void setContent(String content) {
        this.content = content;
      }

      public String getSupport() {
        return support;
      }

      public void setSupport(String support) {
        this.support = support;
      }

      public String getOppose() {
        return oppose;
      }

      public void setOppose(String oppose) {
        this.oppose = oppose;
      }

      public String getStatus() {
        return status;
      }

      public void setStatus(String status) {
        this.status = status;
      }

      public String getUpdate_time() {
        return update_time;
      }

      public void setUpdate_time(String update_time) {
        this.update_time = update_time;
      }

      public String getCreate_time() {
        return create_time;
      }

      public void setCreate_time(String create_time) {
        this.create_time = create_time;
      }

      public UserBean getUser() {
        return user;
      }

      public void setUser(UserBean user) {
        this.user = user;
      }

      public String getCreate_time1() {
        return create_time1;
      }

      public void setCreate_time1(String create_time1) {
        this.create_time1 = create_time1;
      }

      public String getUpdate_time1() {
        return update_time1;
      }

      public void setUpdate_time1(String update_time1) {
        this.update_time1 = update_time1;
      }

      public int getIsbest() {
        return isbest;
      }

      public void setIsbest(int isbest) {
        this.isbest = isbest;
      }

      public String getSupport_count() {
        return support_count;
      }

      public void setSupport_count(String support_count) {
        this.support_count = support_count;
      }

      public String getIs_supported() {
        return is_supported;
      }

      public void setIs_supported(String is_supported) {
        this.is_supported = is_supported;
      }

      public int getIssetbest() {
        return issetbest;
      }

      public void setIssetbest(int issetbest) {
        this.issetbest = issetbest;
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
        private String signature;
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

        public String getSignature() {
          return signature;
        }

        public void setSignature(String signature) {
          this.signature = signature;
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
