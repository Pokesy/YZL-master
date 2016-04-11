package com.tox;

import java.util.List;

/**
 * Created by jiao on 2016/4/6.
 *
 */
public class DiscoverInfo {

    /**
     * success : true
     * error_code : 0
     * message : 获取成功
     * avatar32 : /opensns/Uploads/Avatar/2016-04-06/57046983dc930_32_32.png
     * avatar64 : /opensns/Uploads/Avatar/2016-04-06/57046983dc930_64_64.png
     * avatar128 : /opensns/Uploads/Avatar/2016-04-06/57046983dc930_128_128.png
     * avatar256 : /opensns/Uploads/Avatar/2016-04-06/57046983dc930_256_256.png
     * avatar512 : /opensns/Uploads/Avatar/2016-04-06/57046983dc930_512_512.png
     * sex : 1
     * nickname : tangdejun
     * username : tangdejun
     * rank_link : []
     * expand_info : {"qq":"","生日":"2016-04-06"}
     * fans : 2
     * following : 1
     * title : Lv4 助理
     * signature : 我养鱼我快乐
     * birthday : 0000-00-00
     * qq : 284675291
     * pos_city : {"id":"370600","name":"烟台市","level":"2","upid":"370000"}
     * pos_district : null
     * pos_province : {"id":"370000","name":"山东省","level":"1","upid":"0"}
     * isfactory : 1
     * factory_name : 天虹渔业
     * longitude : 121.4879881055313
     * latitude : 37.46105904322834
     * isdisplay : 0
     * mobile1 : 123456789000
     * address : 山东省烟台市莱山区港城东大街
     * score1 : 325
     * email :
     * real_nickname : tangdejun
     * score : 325
     * uid : 102
     * mobile : 123456789000
     * data : 229
     * cover_url : ["229"]
     * images : ["/opensns/Uploads/Picture/2016-04-06/57046a691a32b_100_100.png"]
     * is_follow : 0
     */

    private boolean success;
    private int error_code;
    private String message;
    private String avatar32;
    private String avatar64;
    private String avatar128;
    private String avatar256;
    private String avatar512;
    private String sex;
    private String nickname;
    private String username;
    /**
     * qq :
     * 生日 : 2016-04-06
     */

    private ExpandInfoEntity expand_info;
    private String fans;
    private String following;
    private String title;
    private String signature;
    private String birthday;
    private String qq;
    /**
     * id : 370600
     * name : 烟台市
     * level : 2
     * upid : 370000
     */

    private PosCityEntity pos_city;
    private Object pos_district;
    /**
     * id : 370000
     * name : 山东省
     * level : 1
     * upid : 0
     */

    private PosProvinceEntity pos_province;
    private String isfactory;
    private String factory_name;
    private String longitude;
    private String latitude;
    private String isdisplay;
    private String mobile1;
    private String address;
    private String score1;
    private String email;
    private String real_nickname;
    private String score;
    private String uid;
    private String mobile;
    private String data;
    private int is_follow;
    private List<?> rank_link;
    private List<String> cover_url;
    private List<String> images;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ExpandInfoEntity getExpand_info() {
        return expand_info;
    }

    public void setExpand_info(ExpandInfoEntity expand_info) {
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

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public PosCityEntity getPos_city() {
        return pos_city;
    }

    public void setPos_city(PosCityEntity pos_city) {
        this.pos_city = pos_city;
    }

    public Object getPos_district() {
        return pos_district;
    }

    public void setPos_district(Object pos_district) {
        this.pos_district = pos_district;
    }

    public PosProvinceEntity getPos_province() {
        return pos_province;
    }

    public void setPos_province(PosProvinceEntity pos_province) {
        this.pos_province = pos_province;
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

    public String getMobile1() {
        return mobile1;
    }

    public void setMobile1(String mobile1) {
        this.mobile1 = mobile1;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getScore1() {
        return score1;
    }

    public void setScore1(String score1) {
        this.score1 = score1;
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

    public String getData() {
        return data;
    }

    public void setData(String data) {
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

    public static class ExpandInfoEntity {
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

    public static class PosCityEntity {
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

    public static class PosProvinceEntity {
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
