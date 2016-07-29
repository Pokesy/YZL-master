package com.thinksky.tox;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.thinksky.anim3d.RoundBitmap;
import com.thinksky.holder.BaseBActivity;
import com.thinksky.info.UserInfo;
import com.thinksky.ui.login.LogoutEvent;
import com.thinksky.utils.JsonConverter;
import com.thinksky.utils.LoadImg;
import com.thinksky.utils.MyJson;
import com.thinksky.utils.imageloader.ImageLoader;
import com.tox.ToastHelper;
import com.tox.Url;
import com.tox.UserApi;
import net.tsz.afinal.FinalBitmap;


public class UserInfoActivity extends BaseBActivity {

  private UserInfo info = null;
  private ImageView mUserRevise, mUserMore, mUserCamera;
  private LinearLayout mBrief;
  private LinearLayout mUserBrief;
  private RelativeLayout mBack, mChangeMsg;
  private Boolean myflag = true;
  private RoundBitmap roundBitmap = new RoundBitmap();
  private LoadImg loadImgHeadImg;
  private MyJson myJson = new MyJson();
  private Button mLogout = null;
  private Button followBtn = null;
  private int mStart = 1;
  private int mEnd = 5;
  private String url = null;
  private boolean flag = true;
  private boolean loadflag = false;
  private boolean listBottemFlag = true;

  private UserApi userApi = new UserApi();
  private TextView UserName, UserTitle, UserTime, signature, UserWriter, UserFollowing, UserFans,
      UserEmail, UserScore;
  //    private WeiboApi mWeiboApi = new WeiboApi();
  private FinalBitmap finalBitmap;
  //    private String mhotUrl = Url.WEIBO;
//    private WeiboApi weiboApi = new WeiboApi();
  private SharedPreferences sp;
  private String userUid;
  private boolean followFlag;
  private RelativeLayout progress;
  UserApi userApi2;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    // TODO Auto-generated method stub
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.activity_user);
    //记录当前activity以备下一个activity调用
    finalBitmap = FinalBitmap.create(this);
    finalBitmap.configMemoryCacheSize(20);
    finalBitmap.configBitmapLoadThreadSize(15);
    loadImgHeadImg = new LoadImg(this);
    userUid = getIntent().getExtras().getString("userUid");
    Url.activityFrom = userUid + "UserInfoActivity";

    sp = getSharedPreferences("userInfo", 0);
    initView();
    if (!TextUtils.equals(userUid, Url.USERID)) {
      mChangeMsg.setVisibility(View.GONE);
    }
    userApi2 = new UserApi(followHandler);
    //加载用户数据
//        if (!Url.SESSIONID.equalsIgnoreCase("")) {
    progress = (RelativeLayout) findViewById(R.id.progress);
    //7秒后如果还没获取到用户信息，自动关闭并提示网络环境
    new Handler().postDelayed(new Runnable() {
      @Override
      public void run() {
        if (progress.getVisibility() == View.VISIBLE) {
          progress.setVisibility(View.GONE);
          ToastHelper.showToast("网络连接状态不稳定", UserInfoActivity.this);
        }
      }
    }, 7000);
    userApi.setHandler(hand);
    userApi.getUserInfo(userUid);
    //判断是否是登陆者信息，如果是就保存当前页面以重复使用
    if (TextUtils.equals(userUid, Url.USERID)) {
      ImageLoader.loadOptimizedHttpImage(this, Url.MYUSERINFO.getAvatar()).placeholder(R.drawable
          .side_user_avatar)
          .error(R.drawable.side_user_avatar).dontAnimate().into(mUserCamera);
    }
  }

  @Override
  protected void onStart() {
    super.onStart();
    //删除微博后刷新listview

  }

  private void initView() {
    // TODO Auto-generated method stub
    mChangeMsg = (RelativeLayout) findViewById(R.id.changeMsg);
    mBrief = (LinearLayout) findViewById(R.id.Brief);
//        mQiushi = (LinearLayout) findViewById(R.id.Weibo);
    mUserBrief = (LinearLayout) findViewById(R.id.UserBrief);
    mBack = (RelativeLayout) findViewById(R.id.UserBack);
    mUserRevise = (ImageView) findViewById(R.id.UserRevise);
    mUserCamera = (ImageView) findViewById(R.id.UserCamera);
    UserName = (TextView) findViewById(R.id.UserName);
    UserTitle = (TextView) findViewById(R.id.UserTitle);
    signature = (TextView) findViewById(R.id.userinfo);
    UserTime = (TextView) findViewById(R.id.Ctime);
    UserWriter = (TextView) findViewById(R.id.userinfo);
    UserFollowing = (TextView) findViewById(R.id.UserFollowing_num);
    UserFans = (TextView) findViewById(R.id.fans_numer);
    UserEmail = (TextView) findViewById(R.id.email);
    UserScore = (TextView) findViewById(R.id.Score);
    mLogout = (Button) findViewById(R.id.Logout);
    followBtn = (Button) findViewById(R.id.follow_btn);
    MyOnClick my = new MyOnClick();
//        mBrief.setOnClickListener(my);
//        mQiushi.setOnClickListener(my);
    mUserRevise.setOnClickListener(my);
    mBack.setOnClickListener(my);
    mUserCamera.setOnClickListener(my);
    mLogout.setOnClickListener(my);
    followBtn.setOnClickListener(my);
    mChangeMsg.setOnClickListener(my);
    mUserCamera.setClickable(false);
    mChangeMsg.setClickable(false);
  }

  class MyOnClick implements OnClickListener {

    @Override
    public void onClick(View v) {
      // TODO Auto-generated method stub
      int mId = v.getId();
      switch (mId) {

        case R.id.UserBack:
          Intent ii = new Intent();
          if (info != null) {
            ii.putExtra("Avater", info.getAvatar());
            ii.putExtra("NickName", info.getNickname());
            setResult(1, ii);
          }
          finish();
          break;
        case R.id.UserRevise:
          // Intent intent = new Intent(UserInfoActivity.this,.class);
          // startActivity(intent);
          break;
        case R.id.UserCamera:
          if (TextUtils.equals(userUid, Url.USERID)) {
            Intent intent = new Intent(UserInfoActivity.this, SetUserInfoActivity.class);
            intent.putExtra("info", Url.MYUSERINFO.getAvatar());
            Bundle bundle = new Bundle();
            bundle.putSerializable("inf", info);
            intent.putExtras(bundle);
            startActivityForResult(intent, 0);
          }
          break;
        case R.id.changeMsg:
          if (TextUtils.equals(userUid, Url.USERID)) {
            Intent intent = new Intent(UserInfoActivity.this, SetUserInfoActivity.class);
            intent.putExtra("info", Url.MYUSERINFO.getAvatar());
            Bundle bundle = new Bundle();
            bundle.putSerializable("inf", info);
            intent.putExtras(bundle);
            startActivityForResult(intent, 0);
          }
          break;
        case R.id.Logout:

          new AlertDialog.Builder(UserInfoActivity.this)
              .setTitle("确认")
              .setMessage("确定退出?")
              .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                  dialog.cancel();
                  showProgressDialog("", false);
                  UserApi userApi1 = new UserApi();
                  userApi1.setHandler(logoutHandler);
                  userApi1.logout();

                }
              })
              .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                  dialog.dismiss();
                }
              }).show();
          break;
        //关注按钮事件
        case R.id.follow_btn:
          if (!TextUtils.isEmpty(userApi.getSeesionId())) {
            if (followFlag) {
              //取消关注的操作
              new AlertDialog.Builder(UserInfoActivity.this)
                  .setMessage("确定取消关注？")
                  .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                      userApi2.endFollow(userUid);
                    }
                  })
                  .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                      dialog.dismiss();
                    }
                  }).show();
            } else {
              //加关注的操作
              userApi2.doFollow(userUid);
            }
          } else {
            ToastHelper.showToast("请登陆后操作", UserInfoActivity.this);
          }
          break;
        default:
          break;
      }
    }
  }

  @Override
  public void onBackPressed() {
    Intent ii = new Intent();
    ii.putExtra("Avater", info.getAvatar());
    ii.putExtra("NickName", info.getNickname());
    setResult(1, ii);
    finish();
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == 0 && resultCode == 1) {
      info = (UserInfo) data.getSerializableExtra("inff");
      UserName.setText(info.getNickname());
      UserEmail.setText(info.getEmail());
      if (!"".equals(info.getSignature())) {
        signature.setText(info.getSignature());
      }
      if (info.getSex().equals("1")) {
        UserTitle.setText("男");
      } else if (info.getSex().equals("2")) {
        UserTitle.setText("女");
      } else {
        UserTitle.setText("保密");
      }
      if (info.getProvince() == null) {
        UserTime.setText("");
      } else if (info.getCity() == null) {
        UserTime.setText(info.getProvince());
      } else {
        UserTime.setText(info.getProvince() + " " + info.getCity());
      }
      ImageLoader.loadOptimizedHttpImage(this, info.getAvatar()).placeholder(R.drawable
          .side_user_avatar).dontAnimate().into(mUserCamera);
      //mUserCamera.setImageBitmap(BitmapFactory.decodeFile(String.valueOf(info.getAvatar())));

      if (TextUtils.equals(userUid, Url.USERID)) {
        Url.MYUSERINFO.setSex(info.getSex());
        Url.MYUSERINFO.setNickname(info.getNickname());
        Url.MYUSERINFO.setEmail(info.getEmail());
        Url.MYUSERINFO.setSignature(info.getSignature());
        Url.MYUSERINFO.setProvince(info.getProvince());
        Url.MYUSERINFO.setCity(info.getCity());
        Url.MYUSERINFO.setAvatar(info.getAvatar());
        getSharedPreferences("userInfo", 0).edit().putString("user_info", JsonConverter
            .objectToJson(Url.MYUSERINFO));
      }
    }
  }

  //加关注操作
  Handler followHandler = new Handler() {
    @Override
    public void handleMessage(Message msg) {
      if (hasDestroyed()) {
        return;
      }
      switch (msg.what) {
        case 0:
          if (followFlag) {
            ToastHelper.showToast("取消关注成功", UserInfoActivity.this);
            followBtn.setBackgroundColor(Color.parseColor("#3BAFDA"));
            followFlag = false;
            followBtn.setText("添加关注");
          } else {
            ToastHelper.showToast("关注成功", UserInfoActivity.this);
            followBtn.setBackgroundColor(Color.parseColor("#3BAFC7"));
            followFlag = true;
            followBtn.setText("取消关注");
          }
          break;
        default:
          break;
      }
    }
  };

  Handler logoutHandler = new Handler() {
    public void handleMessage(Message msg) {
      super.handleMessage(msg);
      closeProgressDialog();
      switch (msg.what) {
        case 0:
          clearUserInfo();
          getComponent().getGlobalBus().post(new LogoutEvent());
          Intent intent = new Intent(UserInfoActivity.this, MainActivity.class);
          intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
          startActivity(intent);
          finish();
          break;
        default:
          Toast.makeText(UserInfoActivity.this, "退出登录失败", Toast.LENGTH_SHORT).show();
          break;
      }
    }
  };

  private void clearUserInfo() {
    SharedPreferences.Editor editor = sp.edit();
    editor.putString("username", "");
    editor.putString("password", "");
    editor.putString("session_id", "");
    editor.putString("signature", "");
    editor.putString("user_info", "");
    Url.MYUSERINFO = null;
    Url.LASTPOSTTIME = 0;
    Url.SESSIONID = "";
    Url.USERID = "";
    editor.commit();
  }

  @SuppressLint("HandlerLeak")
  Handler hand = new Handler() {

    public void handleMessage(Message msg) {
      if (hasDestroyed()) {
        return;
      }
      super.handleMessage(msg);
      if (msg.what == 404) {
        Toast.makeText(UserInfoActivity.this, R.string.network_not_normal, Toast.LENGTH_LONG).show();
        listBottemFlag = true;
      } else if (msg.what == 100) {
        Toast.makeText(UserInfoActivity.this, R.string.network_not_normal, Toast.LENGTH_LONG).show();
        listBottemFlag = true;
      } else if (msg.what == 0) {
        String result = (String) msg.obj;
        Log.e("result>>>>>>>>", result);

        if (result != null) {
          //获取数据成功
          info = myJson.getUserAllInfo(result);
          ImageLoader.loadOptimizedHttpImage(UserInfoActivity.this, info.getAvatar())
              .placeholder(R.drawable.side_user_avatar)
              .error(R.drawable.side_user_avatar).dontAnimate().into(mUserCamera);
          if (TextUtils.equals(userUid, Url.USERID)) {
            Url.MYUSERINFO = info;
            saveUserInfoToNative();
          }
          createUserInfo(info);
        }
      }
      mChangeMsg.setClickable(true);
      mUserCamera.setClickable(true);
      progress.setVisibility(View.GONE);
    }
  };


  //保存用户信息至本地
  private void saveUserInfoToNative() {

    SharedPreferences sp = this.getSharedPreferences("userInfo", 0);
    SharedPreferences.Editor editor = sp.edit();
    editor.putString("nickname", Url.MYUSERINFO.getNickname());
    editor.putString("avatar", Url.MYUSERINFO.getAvatar());
    editor.putString("signature", Url.MYUSERINFO.getSignature());
    editor.putString("session_id", Url.SESSIONID);
    editor.putString("user_info", JsonConverter.objectToJson(Url.MYUSERINFO));

    editor.commit();
  }


  private void createUserInfo(UserInfo userInfo) {
    UserScore.setText(userInfo.getScore());
    UserEmail.setText(userInfo.getEmail());
    UserFans.setText(userInfo.getFans());
    UserFollowing.setText(userInfo.getFollowing());
//        UserTime.setText(userInfo.getCity());

//        UserTime.setText(userInfo.getProvince() + "" + userInfo.getCity() + "" + userInfo
// .getDistrict());
    UserName.setText(userInfo.getNickname());
    if (!"".equals(userInfo.getSignature())) {
      signature.setText(userInfo.getSignature());
    }
    if (TextUtils.equals(userInfo.getSex(), "1")) {
      UserTitle.setText("男");
    } else if (TextUtils.equals(userInfo.getSex(), "2")) {
      UserTitle.setText("女");
    } else {
      UserTitle.setText("保密");
    }
    if (userInfo.getProvince() == null) {
      UserTime.setText("");
    } else if (userInfo.getCity() == null) {
      UserTime.setText(userInfo.getProvince());
    } else {
      UserTime.setText(userInfo.getProvince() + " " + userInfo.getCity());
    }
//        mUserCamera.setImageBitmap(userInfo.getAvatar());
    if (!TextUtils.equals(userUid, Url.USERID)) {
      if (TextUtils.equals(userInfo.getIsFollow(), "0")) {
        followFlag = false;
        followBtn.setText("添加关注");
        followBtn.setVisibility(View.VISIBLE);
      } else {
        followFlag = true;
        followBtn.setText("取消关注");
        followBtn.setVisibility(View.VISIBLE);
      }
    } else {
      mLogout.setVisibility(View.VISIBLE);
    }
    progress.setVisibility(View.GONE);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    hand.removeCallbacksAndMessages(null);
  }
}
