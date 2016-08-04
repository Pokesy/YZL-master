package com.thinksky.tox;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.thinksky.fragment.DiscoverFragment;
import com.thinksky.holder.BaseApplication;
import com.thinksky.holder.BaseBActivity;
import com.thinksky.rsen.RsenUrlUtil;
import com.thinksky.ui.common.TitleBar;
import com.thinksky.utils.MD5;
import com.thinksky.utils.MyJson;
import com.tox.BaseFunction;
import com.tox.ToastHelper;
import com.tox.Url;
import com.tox.UserApi;
import com.tox.login;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class RegistetActivity extends BaseBActivity {
  private RelativeLayout mNext;
  private EditText mName, mPassword, role, nickname, verifyId, reset_password;
  private TextView openRegist;
  private LinearLayout checkId;
  private TextView mStartlogin;
  private String url = null;
  private String value = null;
  private String code = null;
  private String juese = null;
  private String username = null;
  private String password = null;
  private UserApi mUserapi = new UserApi();
  private ProgressDialog progressDialog;
  private login login;
  private TextView sendVerify;
  private RadioButton tempButton;
  private RadioGroup radioGroup;
  private ArrayList<HashMap<String, String>> arr = new ArrayList<HashMap<String, String>>();
  private MyJson myJson;
  private String type;
  private TimeCount time;
  //存储从那种注册方式进来
  private String opinion;
  private static final String LOG_TAG = "0";
  private String reg_type;
  private TitleBar mTitleBar;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    // TODO Auto-generated method stub
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.activity_register);
    myJson = new MyJson();
    login = new login(RegistetActivity.this, 2);
    login.setLoginHandler();
    initView();
    if (Url.activityFrom.equals("LoginActivity")) {
      role.setText("");
      code = "";
      juese = "";
    } else {
      role.setVisibility(View.VISIBLE);
      role.setText(getIntent().getExtras().getString("role"));
      if (getIntent().getExtras().getString("role") == null) {
        role.setVisibility(View.GONE);
      }
      code = getIntent().getExtras().getString("code");
      juese = getIntent().getExtras().getString("role");
    }
    arr = (ArrayList<HashMap<String, String>>) getIntent().getSerializableExtra("title");
    juese = arr.get(1).get("id");
    opinion = getIntent().getExtras().getString("opinion");
    mVerifyStep.entry();
    if (arr.get(0).get("open_invite").equals("0")) {
      openRegist.setVisibility(View.GONE);
    }
    checkId.setVisibility(View.VISIBLE);
    type = "mobile";
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (resultCode == 1) {
      role.setVisibility(View.VISIBLE);
      radioGroup.setVisibility(View.GONE);
      role.setText(data.getStringExtra("role"));
      code = data.getExtras().getString("code");
      juese = data.getExtras().getString("id");
    }
  }

  @Override
  public void onBackPressed() {
    mCurrentStep.back();
  }

  private void initView() {
    mTitleBar = (TitleBar) findViewById(R.id.title_bar);
    mName = (EditText) findViewById(R.id.redit_name);
    mPassword = (EditText) findViewById(R.id.redit_password);
    verifyId = (EditText) findViewById(R.id.verifyId);
    checkId = (LinearLayout) findViewById(R.id.checkId);
    mNext = (RelativeLayout) findViewById(R.id.next);
    mStartlogin = (TextView) findViewById(R.id.startlogin);
    openRegist = (TextView) findViewById(R.id.openRegist);
    reset_password = (EditText) findViewById(R.id.reset_password);
    role = (EditText) findViewById(R.id.role);
    nickname = (EditText) findViewById(R.id.nickname);
    radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
    sendVerify = (TextView) findViewById(R.id.sendVerify);
    progressDialog = new ProgressDialog(this);
    time = new TimeCount(60000, 1000);//构造CountDownTimer对象
    MyOnClickListener my = new MyOnClickListener();
    mNext.setOnClickListener(my);
    mStartlogin.setOnClickListener(my);
    openRegist.setOnClickListener(my);
    sendVerify.setOnClickListener(my);
    radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(RadioGroup group, int checkedId) {
        tempButton = (RadioButton) findViewById(
            checkedId); // 通过RadioGroup的findViewById方法，找到ID为checkedID的RadioButton
        // 以下就可以对这个RadioButton进行处理了
        if (checkedId > 1) {
          checkedId = (checkedId - 1) % radioGroup.getChildCount() + 1;
          Log.d("RadioButton>>>>>>>", checkedId + "");
        }

        juese = arr.get(checkedId).get("id");
        Log.d("juese", juese);
      }
    });
    mTitleBar.setLeftImgMenu(R.drawable.arrow_left, new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        mCurrentStep.back();
      }
    });
    mTitleBar.getTitleBgView().setAlpha(0);
    mTitleBar.getTitleView().setVisibility(View.GONE);
  }

  public void nick() {
    RsenUrlUtil.execute(this, RsenUrlUtil.URL_USER,
        new RsenUrlUtil.OnJsonResultListener<DiscoverFragment.FXBean>() {
          @Override
          public void onNoNetwork(String msg) {
            ToastHelper.showToast(msg, Url.context);
          }

          @Override
          public Map getMap() {
            Map map = new HashMap();
            map.put("username", mName.getText().toString());
            return map;
          }

          @Override
          public void onParseJsonBean(List<DiscoverFragment.FXBean> beans, JSONObject jsonObject) {

          }

          @Override
          public void onResult(boolean state, List beans) {
            if (state) {
              mNext.setEnabled(true);
              if (mName.getText().toString().equals("")) {
                if (type.equals("email")) {
                  Toast.makeText(RegistetActivity.this, "请填写邮箱", Toast.LENGTH_SHORT).show();
                  return;
                } else {
                  Toast.makeText(RegistetActivity.this, "请填写手机号", Toast.LENGTH_SHORT).show();
                  return;
                }
              }
              time.start();//开始计时
              mUserapi.setHandler(handler);
              mUserapi.sendVerify(mName.getText().toString(), type);
            } else {
              ToastHelper.showToast("用户已存在或被禁用", RegistetActivity.this);
              mNext.setEnabled(false);
            }
          }
        });
  }

  public void verify() {
    RsenUrlUtil.execute(this, RsenUrlUtil.URL_VERIFY,
        new RsenUrlUtil.OnJsonResultListener<VBean>() {
          @Override
          public void onNoNetwork(String msg) {
            ToastHelper.showToast(msg, Url.context);
          }

          @Override
          public Map getMap() {
            Map map = new HashMap();
            map.put("account", mName.getText().toString());
            map.put("type", "mobile");
            map.put("Verify", verifyId.getText().toString());

            return map;
          }

          @Override
          public void onParseJsonBean(List<VBean> beans, JSONObject jsonObject) {
            VBean bean = new VBean();
            try {
              bean.message = jsonObject.getString("message");
              beans.add(bean);
            } catch (JSONException e) {
              e.printStackTrace();
            }
          }

          @Override
          public void onResult(boolean state, List<VBean> beans) {

            if (state) {
              //ToastHelper.showToast(beans.get(0).message, RegistetActivity.this);
              mRegisterStep.entry();
            } else {
              ToastHelper.showToast("验证码错误", RegistetActivity.this);
            }
          }
        });
  }

  class MyOnClickListener implements View.OnClickListener {

    @Override
    public void onClick(View v) {
      int mId = v.getId();
      switch (mId) {
        case R.id.next:
          mCurrentStep.next();

          break;
        case R.id.startlogin:
          finish();
          break;
        case R.id.openRegist:
          Intent intent1 = new Intent(RegistetActivity.this, BeforeRegistetActivity.class);
          startActivityForResult(intent1, 0);
          break;
        case R.id.sendVerify:
          if (TextUtils.isEmpty(mName.getText().toString())) {
            if (type.equals("email")) {
              Toast.makeText(RegistetActivity.this, "请填写邮箱", Toast.LENGTH_SHORT).show();
              return;
            } else {
              Toast.makeText(RegistetActivity.this, "请填写手机号", Toast.LENGTH_SHORT).show();
              return;
            }
          }
          time.start();//开始计时
          mUserapi.setHandler(handler);
          mUserapi.sendVerify(mName.getText().toString(), type);
          //nick();
          break;
        default:
      }
    }
  }

  Handler handler = new Handler() {
    @Override
    public void handleMessage(Message msg) {
      super.handleMessage(msg);
      String result = (String) msg.obj;
      try {
        JSONObject jsonObject = new JSONObject(result);
        if (jsonObject.getString("success").equals("true")) {
          mNext.setEnabled(true);
          Toast.makeText(RegistetActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT)
              .show();
        } else {
          mNext.setEnabled(false);
          Toast.makeText(RegistetActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT)
              .show();
        }
      } catch (JSONException e) {
        e.printStackTrace();
      }
    }
  };
  // INSERT INTO `quser`( `uname`, `upassword`) VALUES ('111111','111111');
  Handler hand = new Handler() {
    public void handleMessage(android.os.Message msg) {
      super.handleMessage(msg);
      String result = (String) msg.obj;
      progressDialog.cancel();
      try {
        JSONObject jsonObject = new JSONObject(result);

        if (jsonObject.getBoolean("success")) {
          if (jsonObject.getString("message").equals("注册成功，请登录邮箱进行激活")) {
            new AlertDialog.Builder(RegistetActivity.this).setTitle("注意")
                .setMessage("请先进您的邮箱进行验证,再登入账号")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(RegistetActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                  }
                })
                .show();
            return;
          }
          BaseFunction.putSharepreference("username", username, RegistetActivity.this,
              Url.SharedPreferenceName);
          BaseFunction.putSharepreference("password", password, RegistetActivity.this,
              Url.SharedPreferenceName);
          //TODO 注册后的nickname问题，头像路径问题
          ToastHelper.showToast("注册成功", RegistetActivity.this);
          Url.activityFrom = "registe";
          Url.MYUSERINFO = myJson.getUserAllInfo(result);
          Url.SESSIONID = myJson.getUserSessionID(result);
          Url.USERID = myJson.getUserID(result);
          ((BaseApplication) getApplication()).getGlobalComponent()
              .loginSession
                  ().saveUserInfo(username, password, Url.USERID, Url.SESSIONID
              , myJson.getUserAllInfo(result));
          //                        login.userLogin(username, password);
          Intent intent = new Intent(RegistetActivity.this, MainActivity.class);
          intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
          startActivity(intent);
          finish();
        } else {
          progressDialog.cancel();
          ToastHelper.showToast(jsonObject.getString("message"), RegistetActivity.this);
        }
      } catch (JSONException e) {
        e.printStackTrace();
      }
    }

    ;
  };

  /* 定义一个倒计时的内部类 */
  class TimeCount extends CountDownTimer {
    public TimeCount(long millisInFuture, long countDownInterval) {
      super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔
    }

    @Override
    public void onFinish() {//计时完毕时触发
      sendVerify.setText("重新验证");
      sendVerify.setClickable(true);
    }

    @Override
    public void onTick(long millisUntilFinished) {//计时过程显示
      sendVerify.setClickable(false);
      sendVerify.setText(millisUntilFinished / 1000 + "秒");
    }
  }

  private Step mVerifyStep = new VerifyStep();
  private Step mRegisterStep = new RegisterStep();
  private Step mCurrentStep;

  interface Step {
    void entry();

    void next();

    void back();
  }

  public class VerifyStep implements Step {

    @Override
    public void entry() {
      mCurrentStep = this;
      mPassword.setVisibility(View.GONE);
      reset_password.setVisibility(View.GONE);
      nickname.setVisibility(View.GONE);
      mName.setVisibility(View.VISIBLE);
      checkId.setVisibility(View.VISIBLE);
      // TODO 隐藏第一步不需要的控件， 显示第一步需要的控件
      mName.setHint("输入手机号");
      mName.setFilters(new InputFilter[]{new InputFilter.LengthFilter(13)});
      //mTitleBar.setMiddleTitle("用户注册");
      Drawable drawable = getResources().getDrawable(R.drawable.mobile);
      drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
      //            mName.setCompoundDrawables(drawable, null, null, null);
      reg_type = "mobile";
    }

    @Override
    public void next() {
      if (!TextUtils.isEmpty(verifyId.getText().toString()) && !TextUtils.isEmpty(mName.getText()
          .toString())) {
        verify();
      } else {
        Toast.makeText(RegistetActivity.this, "手机号或验证码不能为空", Toast.LENGTH_SHORT).show();
      }
    }

    @Override
    public void back() {
      // TODO 第一步 后退 执行的操作 可能是 finish？
      finish();
    }
  }

  public class RegisterStep implements Step {

    @Override
    public void entry() {
      mCurrentStep = this;
      mPassword.setVisibility(View.VISIBLE);
      reset_password.setVisibility(View.VISIBLE);
      nickname.setVisibility(View.VISIBLE);
      mName.setVisibility(View.GONE);
      checkId.setVisibility(View.GONE);
      // TODO 隐藏第二步不需要的控件， 显示第二步需要的控件

      mName.setHint("输入手机号");
      mName.setFilters(new InputFilter[]{new InputFilter.LengthFilter(13)});
      //mTitleBar.setMiddleTitle("用户注册");
      Drawable drawable = getResources().getDrawable(R.drawable.mobile);
      drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
      //            mName.setCompoundDrawables(drawable, null, null, null);
      reg_type = "mobile";
    }

    @Override
    public void next() {
      // TODO 第二步 调用接口进行注册？？？？
      username = mName.getText().toString();

      password = MD5.md5(mPassword.getText().toString());

      if (opinion.equals("0")) {
        if (!username.matches("[0-9A-Za-z_]*")) {
          Toast.makeText(RegistetActivity.this, "用户名只能是英文数字和下划线", Toast.LENGTH_SHORT).show();
        } else if (username.length() > 8 || username.length() < 0) {
          Toast.makeText(RegistetActivity.this, "用户名不能小于0或者大于8字符", Toast.LENGTH_SHORT).show();
          return;
        }
      }
      if (opinion.equals("1")) {
        if (!username.matches(
            "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(" +
                "([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$")) {
          Toast.makeText(RegistetActivity.this, "邮箱不正确", Toast.LENGTH_SHORT).show();
          return;
        }
      }
      if (opinion.equals("2")) {
        if (!username.matches("^(13|15|17|18)\\d{9}$")) {
          Toast.makeText(RegistetActivity.this, "号码不正确", Toast.LENGTH_SHORT).show();
          return;
        }
      }

      if (!username.equalsIgnoreCase(null) && !password.equalsIgnoreCase(null) && !"".equals(
          username) && !"".equals(password) && !"".equals(nickname.getText().toString())) {
        if (!mPassword.getText().toString().equals(reset_password.getText().toString())) {
          Toast.makeText(RegistetActivity.this, "两次输入密码不一致", Toast.LENGTH_SHORT).show();
        } else {
          if ("".equals(verifyId.getText().toString()) && (checkId.getVisibility()
              == View.VISIBLE)) {
            Toast.makeText(RegistetActivity.this, "请填写验证码", Toast.LENGTH_SHORT).show();
            return;
          }
          // 请求注册功能
          mUserapi.setHandler(hand);
          mUserapi.register(username, password, code, juese, nickname.getText().toString(),
              reg_type, verifyId.getText().toString());
          Log.d("username", username);
          Log.d("password", password);
          Log.d("code", code);
          Log.d("juese", juese);
          Log.d("nickname", nickname.getText().toString());
          Log.d("reg_type", reg_type);
          progressDialog.setTitle("提示");
          progressDialog.setMessage("注册中...");
          progressDialog.show();
        }
      } else {
        Toast.makeText(RegistetActivity.this, "请先填写昵称或密码", Toast.LENGTH_LONG).show();
      }
    }

    @Override
    public void back() {
      mVerifyStep.entry();
    }
  }

  public static class VBean {
    public String message;
  }
}
