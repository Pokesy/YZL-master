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

    private List<NewsListInfo> list;

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

    public List<NewsListInfo> getList() {
        return list;
    }

    public void setList(List<NewsListInfo> list) {
        this.list = list;
    }


}
