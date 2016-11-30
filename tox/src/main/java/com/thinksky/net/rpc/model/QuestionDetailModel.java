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

  private List<ListBean> list;

  public List<ListBean> getList() {
    return list;
  }

  public void setList(List<ListBean> list) {
    this.list = list;
  }

  public static class ListBean {
    /**
     * id : 418
     * uid : 325
     * category : 11
     * title : 123456
     * description1 : 789654
     * answer_num : 1
     * best_answer : 0
     * good_question : 0
     * status : 1
     * is_recommend : 0
     * create_time : 今天09:14
     * update_time : 今天09:14
     * data : a:1:{s:10:"attach_ids";s:0:"";}
     * score : 0
     * Questionimages : []
     * imgList : []
     * category_title : 龙鱼
     * QuestionAnswer : [{"id":"614","uid":"325","question_id":"418","content":"111",
     * "support":"0","oppose":"0","status":"1","update_time":"1480295694",
     * "create_time":"1480295694",
     * "user":{"avatar32":"Uploads/Avatar/2016-11-16/582bccb99abb2_32_32.jpg",
     * "avatar64":"Uploads/Avatar/2016-11-16/582bccb99abb2_64_64.jpg",
     * "avatar128":"Uploads/Avatar/2016-11-16/582bccb99abb2_128_128.jpg",
     * "avatar256":"Uploads/Avatar/2016-11-16/582bccb99abb2_256_256.jpg",
     * "avatar512":"Uploads/Avatar/2016-11-16/582bccb99abb2_512_512.jpg","uid":"325",
     * "nickname":"此情可待","signature":"花姑娘在哪里","username":"","real_nickname":"此情可待"},
     * "create_time1":"今天09:14","update_time1":"今天09:14","imgList":[],"isbest":0,
     * "support_count":"0","is_supported":"0","issetbest":0}]
     * user : {"avatar32":"Uploads/Avatar/2016-11-16/582bccb99abb2_32_32.jpg",
     * "avatar64":"Uploads/Avatar/2016-11-16/582bccb99abb2_64_64.jpg",
     * "avatar128":"Uploads/Avatar/2016-11-16/582bccb99abb2_128_128.jpg",
     * "avatar256":"Uploads/Avatar/2016-11-16/582bccb99abb2_256_256.jpg",
     * "avatar512":"Uploads/Avatar/2016-11-16/582bccb99abb2_512_512.jpg","uid":"325",
     * "nickname":"此情可待","signature":"花姑娘在哪里","username":"","real_nickname":"此情可待"}
     * cover_url :
     * is_author : 1
     * is_collection : 0
     */

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
    private UserBean user;
    private String cover_url;
    private String is_author;
    private int is_collection;
    private List<String> Questionimages;
    private List<?> imgList;
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

    public UserBean getUser() {
      return user;
    }

    public void setUser(UserBean user) {
      this.user = user;
    }

    public String getCover_url() {
      return cover_url;
    }

    public void setCover_url(String cover_url) {
      this.cover_url = cover_url;
    }

    public String getIs_author() {
      return is_author;
    }

    public void setIs_author(String is_author) {
      this.is_author = is_author;
    }

    public int getIs_collection() {
      return is_collection;
    }

    public void setIs_collection(int is_collection) {
      this.is_collection = is_collection;
    }

    public List<String> getQuestionimages() {
      return Questionimages;
    }

    public void setQuestionimages(List<String> Questionimages) {
      this.Questionimages = Questionimages;
    }

    public List<?> getImgList() {
      return imgList;
    }

    public void setImgList(List<?> imgList) {
      this.imgList = imgList;
    }

    public List<QuestionAnswerBean> getQuestionAnswer() {
      return QuestionAnswer;
    }

    public void setQuestionAnswer(List<QuestionAnswerBean> QuestionAnswer) {
      this.QuestionAnswer = QuestionAnswer;
    }

    public static class UserBean {
      /**
       * avatar32 : Uploads/Avatar/2016-11-16/582bccb99abb2_32_32.jpg
       * avatar64 : Uploads/Avatar/2016-11-16/582bccb99abb2_64_64.jpg
       * avatar128 : Uploads/Avatar/2016-11-16/582bccb99abb2_128_128.jpg
       * avatar256 : Uploads/Avatar/2016-11-16/582bccb99abb2_256_256.jpg
       * avatar512 : Uploads/Avatar/2016-11-16/582bccb99abb2_512_512.jpg
       * uid : 325
       * nickname : 此情可待
       * signature : 花姑娘在哪里
       * username :
       * real_nickname : 此情可待
       */

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

    public static class QuestionAnswerBean {
      /**
       * id : 614
       * uid : 325
       * question_id : 418
       * content : 111
       * support : 0
       * oppose : 0
       * status : 1
       * update_time : 1480295694
       * create_time : 1480295694
       * user : {"avatar32":"Uploads/Avatar/2016-11-16/582bccb99abb2_32_32.jpg",
       * "avatar64":"Uploads/Avatar/2016-11-16/582bccb99abb2_64_64.jpg",
       * "avatar128":"Uploads/Avatar/2016-11-16/582bccb99abb2_128_128.jpg",
       * "avatar256":"Uploads/Avatar/2016-11-16/582bccb99abb2_256_256.jpg",
       * "avatar512":"Uploads/Avatar/2016-11-16/582bccb99abb2_512_512.jpg","uid":"325",
       * "nickname":"此情可待","signature":"花姑娘在哪里","username":"","real_nickname":"此情可待"}
       * create_time1 : 今天09:14
       * update_time1 : 今天09:14
       * imgList : []
       * isbest : 0
       * support_count : 0
       * is_supported : 0
       * issetbest : 0
       */

      private String id;
      private String uid;
      private String question_id;
      private String content;
      private String support;
      private String oppose;
      private String status;
      private String update_time;
      private String create_time;
      private UserBeanX user;
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

      public UserBeanX getUser() {
        return user;
      }

      public void setUser(UserBeanX user) {
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

      public static class UserBeanX {
        /**
         * avatar32 : Uploads/Avatar/2016-11-16/582bccb99abb2_32_32.jpg
         * avatar64 : Uploads/Avatar/2016-11-16/582bccb99abb2_64_64.jpg
         * avatar128 : Uploads/Avatar/2016-11-16/582bccb99abb2_128_128.jpg
         * avatar256 : Uploads/Avatar/2016-11-16/582bccb99abb2_256_256.jpg
         * avatar512 : Uploads/Avatar/2016-11-16/582bccb99abb2_512_512.jpg
         * uid : 325
         * nickname : 此情可待
         * signature : 花姑娘在哪里
         * username :
         * real_nickname : 此情可待
         */

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
