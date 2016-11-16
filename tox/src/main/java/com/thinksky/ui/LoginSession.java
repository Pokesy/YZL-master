/*
 * 文件名: LoginSession
 * 版    权：  Copyright Hengrtech Tech. Co. Ltd. All Rights Reserved.
 * 描    述: [该类的简要描述]
 * 创建人: zhaozeyang
 * 创建时间:16/7/22
 * 
 * 修改人：
 * 修改时间:
 * 修改内容：[修改内容]
 */
package com.thinksky.ui;

import android.content.Context;
import android.content.SharedPreferences;
import com.thinksky.holder.BaseApplication;
import com.thinksky.net.rpc.model.UserInfoModel;
import com.thinksky.utils.JsonConverter;
import com.tox.Url;

/**
 * [一句话功能简述]<BR>
 * [功能详细描述]
 *
 * @author zhaozeyang
 * @version [Taobei Client V20160411, 16/7/22]
 */
public class LoginSession {
  private static final String SHARED_PREFERENCE_NAME = "userInfo";
  private static final String KEY_USER_NAME = "username";
  private static final String KEY_PASSWORD = "password";
  private static final String KEY_UID = "uid";
  private static final String KEY_SESSION_ID = "session_id";
  private static final String KEY_USER_INFO = "user_info";

  private Context mContext;
  private SharedPreferences mSp;

  public LoginSession(Context context) {
    mContext = context.getApplicationContext();
    mSp = context.getSharedPreferences(SHARED_PREFERENCE_NAME, 0);
  }

  public void saveUserInfo(String name, String password, String userId, String sessionId,
                           UserInfoModel userInfo) {
    SharedPreferences.Editor editor = mSp.edit();
    editor.putString(KEY_USER_NAME, name);
    editor.putString(KEY_PASSWORD, password);
    editor.putString(KEY_UID, userId);
    editor.putString(KEY_SESSION_ID, sessionId);
    editor.putString(KEY_USER_INFO, JsonConverter.objectToJson(userInfo));
    editor.apply();
    ((BaseApplication) mContext.getApplicationContext()).getGlobalComponent().getGlobalBus().post
        (new UserInfoChangeEvent());
  }

  public void saveUserInfoModel(UserInfoModel userInfo) {
    SharedPreferences.Editor editor = mSp.edit();
    editor.putString(KEY_USER_INFO, JsonConverter.objectToJson(userInfo));
    editor.commit();
    ((BaseApplication) mContext.getApplicationContext()).getGlobalComponent().getGlobalBus().post
        (new UserInfoChangeEvent());
  }

  public void clearUserInfo() {
    SharedPreferences.Editor editor = mSp.edit();
    editor.remove(KEY_USER_NAME);
    editor.remove(KEY_PASSWORD);
    editor.remove(KEY_UID);
    editor.remove(KEY_SESSION_ID);
    editor.remove(KEY_USER_INFO);
    Url.MYUSERINFO = null;
    Url.LASTPOSTTIME = 0;
    Url.SESSIONID = "";
    Url.USERID = "";
    editor.apply();
  }

  public UserInfoModel getUserInfo() {
    return mSp.contains(KEY_USER_INFO) ? JsonConverter.jsonToObject(UserInfoModel.class, mSp.getString
        (KEY_USER_INFO, "")) : new UserInfoModel();
  }

  public boolean isLogin() {
    return mSp.contains(KEY_UID);
  }

  public String getUserId() {
    return mSp.getString(KEY_UID, "");
  }

  public String getSessionId() {
    return mSp.getString(KEY_SESSION_ID, "");
  }

  public void saveSessionId(String sessionId) {
    mSp.edit().putString(KEY_SESSION_ID, sessionId).apply();
  }

  public String getPassword() {
    return mSp.getString(KEY_PASSWORD, "");
  }

  public String getUserName() {
    return mSp.getString(KEY_USER_NAME, "");
  }

  public static class UserInfoChangeEvent {

  }
}
