package com.thinksky.info;

import com.thinksky.net.rpc.model.UserInfoModel;
import java.io.Serializable;

/**
 * Created by Administrator on 2014/7/17.
 */
public class WeiboCommentInfo implements Serializable {
    private String id;
    private String content;
    private UserInfoModel user;
    private String ctime;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setUser(UserInfoModel user) {
        this.user = user;
    }

    public UserInfoModel getUser() {
        return user;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public String getCtime() {
        return ctime;
    }
}
