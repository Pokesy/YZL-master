package com.thinksky.tox;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.dd.CircularProgressButton;
import com.thinksky.holder.BaseBActivity;
import com.thinksky.utils.MD5;
import com.thinksky.utils.MyJson;
import com.tox.LoginApi;
import com.tox.Url;
import com.tox.login;
import java.util.ArrayList;
import java.util.HashMap;

public class LoginActivity extends BaseBActivity {

  //    private RelativeLayout mClose;
  private RelativeLayout  mWeibo, mQQ;
  private EditText mName, mPassword;
  private TextView mRegister;
  private String nameValue = null;
  private String passwordValue = null;
  private String url = null;
  private String value = null;
  private MyJson myJson = new MyJson();
  private ProgressDialog progressDialog;
  private login mlogin;
  private int entryActivity;
  private CircularProgressButton btnLogin;
  private LoginApi loginApi;
  private Handler handler;
  private String status[] = null;
  private ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
  public static Activity instance;
  String[] shuju = {"mobile"};
  private TextView enter, reset;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    // TODO Auto-generated method stub
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.activity_login);
//        shuju=getIntent().getStringArrayExtra("ways");
    loginApi = new LoginApi();
    Intent intent = this.getIntent();
    instance = this;
    Url.activityFrom = "LoginActivity";
    entryActivity = intent.getIntExtra("entryActivity", 0);
    mlogin = new login(this, entryActivity);
    mlogin.setLoginHandler();
    handler = new Handler() {
      @Override
      public void handleMessage(Message msg) {
        switch (msg.what) {
          case 0:
            String result = (String) msg.obj;
            Log.e("result>>>>>>>>", result);
            list = myJson.beforeRegisterStatus(result);
            break;
        }
      }
    };
    loginApi.setHandler(handler);
    loginApi.beforeRegister("");
    initView();
  }

  private void initView() {
    mWeibo = (RelativeLayout) findViewById(R.id.button_weibo);
    mQQ = (RelativeLayout) findViewById(R.id.buton_qq);
    mName = (EditText) findViewById(R.id.Ledit_name);
    mPassword = (EditText) findViewById(R.id.Ledit_password);
    mRegister = (TextView) findViewById(R.id.register);
    btnLogin = (CircularProgressButton) findViewById(R.id.btn_login);
    enter = (TextView) findViewById(R.id.enter);
    reset = (TextView) findViewById(R.id.reset);
    MyOnClickListener my = new MyOnClickListener();
    mWeibo.setOnClickListener(my);
    mQQ.setOnClickListener(my);
    mRegister.setOnClickListener(my);
    btnLogin.setOnClickListener(my);
    enter.setOnClickListener(my);
    reset.setOnClickListener(my);
  }

  class MyOnClickListener implements View.OnClickListener {

    @Override
    public void onClick(View v) {
      int mId = v.getId();
      switch (mId) {
//                case R.id.loginClose:
//                    finish();
//                    break;
        case R.id.login:
          nameValue = mName.getText().toString();

          passwordValue = MD5.md5(mPassword.getText().toString());
//                    passwordValue =mPassword.getText().toString();
          if (TextUtils.isEmpty(nameValue) || TextUtils.isEmpty(passwordValue)) {
            Toast.makeText(LoginActivity.this, "用户名和密码不能为空", Toast.LENGTH_LONG).show();
          } else {
            progressDialog = ProgressDialog.show(LoginActivity.this, "登录中....", "请等待。", true,
                false);
            mlogin.setProgressDialog(progressDialog);
            //login();

            mlogin.userLogin(nameValue, passwordValue);
          }
          break;
        case R.id.button_weibo:
          break;
        case R.id.enter:
          Intent intent = new Intent(LoginActivity.this, MainActivity.class);
          startActivity(intent);
          LoginActivity.instance.finish();
          break;
        case R.id.btn_login:
          nameValue = mName.getText().toString();

          passwordValue = MD5.md5(mPassword.getText().toString());
          //passwordValue =mPassword.getText().toString();
          if (TextUtils.isEmpty(nameValue) || TextUtils.isEmpty(passwordValue)) {
            Toast.makeText(LoginActivity.this, "用户名和密码不能为空", Toast.LENGTH_LONG).show();
          } else {
            //progressDialog = ProgressDialog.show(LoginActivity.this, "登录中....", "请等待。", true,
            // false);
            mlogin.setCircularProgressButton(btnLogin);
            //login();

            mlogin.userLogin(nameValue, passwordValue);
          }
          break;
        case R.id.buton_qq:
          break;
        case R.id.reset:
          Intent intent2 = new Intent(LoginActivity.this, ReSetPasswordActivity.class);
          startActivity(intent2);

          break;
        case R.id.register:
          Intent intent1 = new Intent(LoginActivity.this, RegisterActivity.class);
          //这是判断有没有开邀请注册
          intent1.putExtra("title", list);
          intent1.putExtra("opinion", "2");
          startActivity(intent1);
          break;
      }

    }

  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    // TODO Auto-generated method stub
    super.onActivityResult(requestCode, resultCode, data);
    //如果注册成功将用户名复制给输入框
    if (requestCode == 1 && resultCode == 2 && data != null) {
      nameValue = data.getStringExtra("nameValue");
      mName.setText(nameValue);
    }

  }


}