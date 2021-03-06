package com.tox;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import com.dd.CircularProgressButton;
import com.thinksky.holder.BaseApplication;
import com.thinksky.model.ActivityModel;
import com.thinksky.net.ThreadPoolUtils;
import com.thinksky.net.rpc.model.UserInfoModel;
import com.thinksky.thread.HttpPostThread;
import com.thinksky.tox.LoginActivity;
import com.thinksky.tox.MainActivity;
import com.thinksky.tox.R;
import com.thinksky.tox.UploadActivity;
import com.thinksky.ui.login.LoginEvent;
import com.thinksky.utils.MyJson;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2014/7/18.
 */
public class login {

  private Context context;
  private Handler loginHandler;
  private ProgressDialog progressDialog;
  private EditText mName, mPassword;
  private MyJson myJson = new MyJson();
  private int entryActivity = 0;
  private String mNamestr = "";
  private String mPasswordstr = "";
  private CircularProgressButton circularProgressButton;
  View convertView;

  public login(Context context, int entryActivity) {
    this.context = context;
    LayoutInflater inflater = LayoutInflater.from(context);
    this.convertView = inflater.inflate(R.layout.activity_login, null);
    this.mName = (EditText) convertView.findViewById(R.id.Ledit_name);
    this.mPassword = (EditText) convertView.findViewById(R.id.Ledit_password);
    this.entryActivity = entryActivity;
  }

  public void setProgressDialog(ProgressDialog progressDialog) {
    this.progressDialog = progressDialog;
  }

  public void setCircularProgressButton(CircularProgressButton circularProgressButton) {
    this.circularProgressButton = circularProgressButton;
  }

  public void setLoginHandler() {
    this.loginHandler = new Handler() {
      public void handleMessage(android.os.Message msg) {
        super.handleMessage(msg);
        Log.e("ErrorCode<><><><><><><>", msg.what + "");
        //progressDialog.dismiss();
        if (msg.what == 404) {
          if (circularProgressButton != null) {
            // Toast.makeText(context, "请求失败，服务器故障", Toast.LENGTH_LONG).show();
            circularProgressButton.setErrorText(context.getString(R.string.network_not_normal));
            circularProgressButton.setProgress(-1);
            loginHandler.postDelayed(new Runnable() {
              @Override
              public void run() {
                circularProgressButton.setProgress(0);
              }
            }, 3000);
          }
        } else if (msg.what == 601 || msg.what == 602) {
          if (circularProgressButton != null) {
            circularProgressButton.setErrorText("用户名或密码错误");
            circularProgressButton.setProgress(-1);
            loginHandler.postDelayed(new Runnable() {
              @Override
              public void run() {
                circularProgressButton.setProgress(0);
              }
            }, 3000);
          }
        } else if (msg.what == 801) {
          if (circularProgressButton != null) {
            circularProgressButton.setErrorText("账号未激活");
            circularProgressButton.setProgress(-1);
            loginHandler.postDelayed(new Runnable() {
              @Override
              public void run() {
                circularProgressButton.setProgress(0);
              }
            }, 3000);
          }
        } else if (msg.what == 100) {
          if (circularProgressButton != null) {
            circularProgressButton.setErrorText("登录失败");
            circularProgressButton.setProgress(-1);
            loginHandler.postDelayed(new Runnable() {
              @Override
              public void run() {
                circularProgressButton.setProgress(0);
              }
            }, 3000);
          }
          //Toast.makeText(context, "2服务器无响应", Toast.LENGTH_LONG).show();
        } else if (msg.what == 0) {
          String result = (String) msg.obj;
          if (circularProgressButton != null) {
            circularProgressButton.setProgress(100);
          }

          try {

            JSONObject jsonObject = new JSONObject(result);
            Url.VERSION = jsonObject.getString("version");
//                        Url.WEIBOWORDS=Integer.parseInt(jsonObject.getString
// ("weibo_words_limit"));
            Url.WEIBOWORDS = 200;
          } catch (JSONException E) {

          }
          Log.e("qiangpengyu", result);

          Url.USERID = myJson.getUserID(result);
          Url.LASTPOSTTIME = System.currentTimeMillis();

          Url.SESSIONID = myJson.getUserSessionID(result); //Toast.makeText(context, Url
          // .SESSIONID, Toast.LENGTH_LONG).show();Log.e("SessionID<><><><><><><>", Url
          // .SESSIONID);

          //将用户信息保存到本地
          saveUserInfo(myJson.getUserAllInfo(result));
          ((BaseApplication) LoginActivity.instance.getApplication()).getGlobalComponent()
              .getGlobalBus().post(new LoginEvent());
          LoginActivity.instance.setResult(Activity.RESULT_OK);
          if (entryActivity == ActivityModel.SENDWEIBO) {
            Intent intent = new Intent(context,
                UploadActivity.class);
            context.startActivity(intent);
            LoginActivity.instance.finish();
          } else if (entryActivity == ActivityModel.USERINFO) {
            Intent intent = new Intent(context, MainActivity.class);
            intent.putExtra("userUid", Url.USERID);
            context.startActivity(intent);
            LoginActivity.instance.finish();
          } else if (entryActivity == ActivityModel.ACTIVITY) {
            Intent intent = new Intent(context, MainActivity.class);
            context.startActivity(intent);
            LoginActivity.instance.finish();
          } else {


            LoginActivity.instance.finish();
          }

        }
      }
    };
  }

  public void saveUserInfo(UserInfoModel info) {
    ((BaseApplication) LoginActivity.instance.getApplication()).getGlobalComponent().loginSession
        ().saveUserInfo(mNamestr, mPasswordstr, Url.USERID, Url.SESSIONID
        , info);
  }

  public void userLogin(String username, String password) {
    //String url = Url.LOGIN;

    if (circularProgressButton != null) {
      circularProgressButton.setIndeterminateProgressMode(true);
      circularProgressButton.setProgress(50);
    }
    Map<String, String> mapvalue = new HashMap<String, String>();
    mNamestr = username;
    mPasswordstr = password;
    mapvalue.put("username", username);
    mapvalue.put("password", password);
    mapvalue.put("session_id", "0");
    String url = Url.HTTPURL + "?s=" + Url.LOGIN;
    ThreadPoolUtils.execute(new HttpPostThread(loginHandler, url, mapvalue));
//        progressDialog.dismiss();
  }

}
