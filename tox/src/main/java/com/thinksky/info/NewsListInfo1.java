package com.thinksky.info;

import java.io.Serializable;
import java.util.List;

/**
 * news列表对象
 * Created by Administrator on 2015/7/20 0020.
 */
public class NewsListInfo1 implements Serializable{


    /**
     * success : true
     * error_code : 0
     * message : 返回成功
     * list : [{"id":"2","uid":"1","title":"观赏鱼患病的人为因素与自然因素","description":"测试龙鱼资讯发布测试测试龙鱼资讯发布测试测试龙鱼资讯发布测试测试龙鱼资讯发布测试测试龙鱼资讯发布测试测试龙鱼资讯发布测试...","category":"4","status":"1","reason":"","sort":"0","position":"0","cover":"/opensns/Uploads/Picture/2016-03-25/56f4fc62de75c.jpg","view":"23","comment":"4","collection":"0","dead_line":"2038-01-19 11:14","source":"","create_time":"01月26日 19:09","update_time":"03月26日 14:11","user":{"avatar32":"/yzl/Uploads/Avatar/2016-04-13/570df9003d120.jpg","avatar64":"/yzl/Uploads/Avatar/2016-04-13/570df9003d120.jpg","avatar128":"/yzl/Uploads/Avatar/2016-04-13/570df9003d120.jpg","avatar256":"/yzl/Uploads/Avatar/2016-04-13/570df9003d120.jpg","avatar512":"/yzl/Uploads/Avatar/2016-04-13/570df9003d120.jpg","uid":"1","username":"admin","nickname":"admin","real_nickname":"admin"},"description1":"测试龙鱼资讯发布测试测试龙鱼资讯发布测试测试龙鱼资讯发布测试测试龙鱼资讯发布测试测试龙鱼资讯发布测试测试龙鱼资讯发布测试..."},{"id":"1","uid":"1","title":"冬养观赏鱼 门道您得懂","description":"测试魟鱼资讯发布测试测试魟鱼资讯发布测试测试魟鱼资讯发布测试测试魟鱼资讯发布测试测试魟鱼资讯发布测试测试魟鱼资讯发布测试测试魟鱼资讯发布测试测试魟鱼资讯发布测试...","category":"2","status":"1","reason":"","sort":"0","position":"0","cover":"/opensns/Uploads/Picture/2016-03-25/56f4fc4664bea.jpg","view":"8","comment":"8","collection":"0","dead_line":"2038-01-19 11:14","source":"null","create_time":"01月26日 19:08","update_time":"03月26日 14:11","user":{"avatar32":"/yzl/Uploads/Avatar/2016-04-13/570df9003d120.jpg","avatar64":"/yzl/Uploads/Avatar/2016-04-13/570df9003d120.jpg","avatar128":"/yzl/Uploads/Avatar/2016-04-13/570df9003d120.jpg","avatar256":"/yzl/Uploads/Avatar/2016-04-13/570df9003d120.jpg","avatar512":"/yzl/Uploads/Avatar/2016-04-13/570df9003d120.jpg","uid":"1","username":"admin","nickname":"admin","real_nickname":"admin"},"description1":"测试魟鱼资讯发布测试测试魟鱼资讯发布测试测试魟鱼资讯发布测试测试魟鱼资讯发布测试测试魟鱼资讯发布测试测试魟鱼资讯发布测试测试魟鱼资讯发布测试测试魟鱼资讯发布测试..."}]
     */

    private boolean success;
    private int error_code;
    private String message;
    /**
     * id : 2
     * uid : 1
     * title : 观赏鱼患病的人为因素与自然因素
     * description : 测试龙鱼资讯发布测试测试龙鱼资讯发布测试测试龙鱼资讯发布测试测试龙鱼资讯发布测试测试龙鱼资讯发布测试测试龙鱼资讯发布测试...
     * category : 4
     * status : 1
     * reason :
     * sort : 0
     * position : 0
     * cover : /opensns/Uploads/Picture/2016-03-25/56f4fc62de75c.jpg
     * view : 23
     * comment : 4
     * collection : 0
     * dead_line : 2038-01-19 11:14
     * source :
     * create_time : 01月26日 19:09
     * update_time : 03月26日 14:11
     * user : {"avatar32":"/yzl/Uploads/Avatar/2016-04-13/570df9003d120.jpg","avatar64":"/yzl/Uploads/Avatar/2016-04-13/570df9003d120.jpg","avatar128":"/yzl/Uploads/Avatar/2016-04-13/570df9003d120.jpg","avatar256":"/yzl/Uploads/Avatar/2016-04-13/570df9003d120.jpg","avatar512":"/yzl/Uploads/Avatar/2016-04-13/570df9003d120.jpg","uid":"1","username":"admin","nickname":"admin","real_nickname":"admin"}
     * description1 : 测试龙鱼资讯发布测试测试龙鱼资讯发布测试测试龙鱼资讯发布测试测试龙鱼资讯发布测试测试龙鱼资讯发布测试测试龙鱼资讯发布测试...
     */

    private List<ListEntity> list;

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

    public List<ListEntity> getList() {
        return list;
    }

    public void setList(List<ListEntity> list) {
        this.list = list;
    }

    public static class ListEntity implements Serializable{
        private String id;
        private String uid;
        private String title;
        private String description;
        private String category;
        private String status;
        private String reason;
        private String sort;
        private String position;
        private String cover;
        private String view;
        private String comment;
        private String collection;
        private String dead_line;
        private String source;
        private String create_time;
        private String update_time;
        /**
         * avatar32 : /yzl/Uploads/Avatar/2016-04-13/570df9003d120.jpg
         * avatar64 : /yzl/Uploads/Avatar/2016-04-13/570df9003d120.jpg
         * avatar128 : /yzl/Uploads/Avatar/2016-04-13/570df9003d120.jpg
         * avatar256 : /yzl/Uploads/Avatar/2016-04-13/570df9003d120.jpg
         * avatar512 : /yzl/Uploads/Avatar/2016-04-13/570df9003d120.jpg
         * uid : 1
         * username : admin
         * nickname : admin
         * real_nickname : admin
         */

        private UserEntity user;
        private String description1;

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

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public String getSort() {
            return sort;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public String getView() {
            return view;
        }

        public void setView(String view) {
            this.view = view;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getCollection() {
            return collection;
        }

        public void setCollection(String collection) {
            this.collection = collection;
        }

        public String getDead_line() {
            return dead_line;
        }

        public void setDead_line(String dead_line) {
            this.dead_line = dead_line;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
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

        public UserEntity getUser() {
            return user;
        }

        public void setUser(UserEntity user) {
            this.user = user;
        }

        public String getDescription1() {
            return description1;
        }

        public void setDescription1(String description1) {
            this.description1 = description1;
        }

        public static class UserEntity {
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
    }
}
