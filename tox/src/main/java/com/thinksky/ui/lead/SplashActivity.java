package com.thinksky.ui.lead;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.thinksky.holder.BaseBActivity;
import com.thinksky.model.ActivityModel;
import com.thinksky.net.IsNet;
import com.thinksky.tox.BuildConfig;
import com.thinksky.tox.LoginActivity;
import com.thinksky.tox.MainActivity;
import com.thinksky.tox.R;
import com.thinksky.tox.UserInfoActivity;
import com.thinksky.utils.MyJson;
import com.tox.ToastHelper;
import com.tox.Url;
import com.tox.WeiboApi;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import org.json.JSONException;
import org.json.JSONObject;

public class SplashActivity extends BaseBActivity {
  private TextView tv_splash;
  protected final int SPLASH_TIME = BuildConfig.SPLASH_TIME;
  private WeiboApi weiboApi = new WeiboApi();
  private SharedPreferences sp = null;
  private MyJson myJson = new MyJson();
  private int month, day;
  Timer mTimer;
  private Context mContext;
  AlertDialog.Builder alertDialog;
  AlertDialog dlg;
  private ViewPager mViewPager;
  private ArrayList<String> ways = new ArrayList<String>();
  private int currIndex = 0;
  private Intent intent;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_splash);

    initview();
//        init();

//        if (BuildConfig.DEBUG) {
//            startbutton(null);
//        }
  }

  private void init() {

//        tv_splash = (TextView) findViewById(R.id.tv_splash);
    mViewPager = (ViewPager) findViewById(R.id.whatsnew_viewpager);


    LayoutInflater mLi = LayoutInflater.from(this);
    View view1 = mLi.inflate(R.layout.whats1, null);
    View view2 = mLi.inflate(R.layout.whats2, null);
    View view3 = mLi.inflate(R.layout.whats3, null);


    final ArrayList<View> views = new ArrayList<View>();
    views.add(view1);
    views.add(view2);
    views.add(view3);


    PagerAdapter mPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

      @Override
      public Fragment getItem(int position) {
        LeadFragment fragment = LeadFragment.newInstance(position);
        fragment.setOnBtnClickListener(new LeadFragment.OnBtnClickListener() {
          @Override
          public void onClick() {
            startbutton();
          }
        });
        return fragment;
      }

      @Override
      public int getCount() {
        return LeadFragment.LEAD_IMG_RES.length;
      }
    };

    mViewPager.setAdapter(mPagerAdapter);
  }

  public void startbutton() {
//        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
//        startActivity(intent);
//        SplashActivity.this.finish();
    if (!Url.SESSIONID.equals("")) {
      Intent intent = new Intent(SplashActivity.this,
          UserInfoActivity.class);
      intent.putExtra("userUid", Url.USERID);
      startActivityForResult(intent, 0);
    } else {
      String[] s = new String[ways.size()];
      s = ways.toArray(s);
      Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
      intent.putExtra("ways", s);
      intent.putExtra("entryActivity", ActivityModel.ACTIVITY);
      startActivity(intent);
    }
    SplashActivity.this.finish();

  }

  private void initview() {
    weiboApi.setHandler(handler);
    mContext = SplashActivity.this;
    SharedPreferences sp = getSharedPreferences("userInfo", 0);
    sp.edit().putString("version", "0.1.1").commit();

    Url.sp = sp;
    Url.USERID = sp.getString("uid", "0");
    Url.context = getApplicationContext();
    if (IsNet.IsConnect()) {
//            is2getParameters();

      new Handler().postDelayed(new Runnable() {
        @Override
        public void run() {
          //判断是否是首次登入
          SharedPreferences startTimes = getSharedPreferences("Parameters", 1);
          int times = startTimes.getInt("times", 1);

          Log.e("TIME>>>>>>>>>>>>>>", times + "");
          SharedPreferences.Editor editor = startTimes.edit();
          if (times == 1) {
            ++times;
            editor.putInt("times", times);
            editor.commit();

            Log.e("AfterSave>>>>>>>>>>", startTimes.getInt("times", 0) + "");
            init();
//                        Intent intent = new Intent(SplashActivity.this, CallActivity.class);
//                        startActivity(intent);
//                        SplashActivity.this.finish();
          } else {
            ++times;
            editor.putInt("times", times);
            editor.commit();
            Log.e("AfterSave>>>>>>>>>>", startTimes.getInt("times", 0) + "");
//                        init();
//                        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
//                        startActivity(intent);
//                        SplashActivity.this.finish();

            String[] s = new String[ways.size()];
            s = ways.toArray(s);
            if ("".equals(Url.SESSIONID)) {
              intent = new Intent(SplashActivity.this, LoginActivity.class);
            } else {
              intent = new Intent(SplashActivity.this, MainActivity.class);
            }
            intent.putExtra("ways", s);
            intent.putExtra("entryActivity", ActivityModel.ACTIVITY);
            startActivity(intent);
            SplashActivity.this.finish();
          }
        }
      }, SPLASH_TIME);
    } else {
      alertDialog = new AlertDialog.Builder(this);
      alertDialog.setTitle("提示").setMessage("请开启数据网络！").setPositiveButton("确定", new
          DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
              SplashActivity.this.finish();
            }
          });
      dlg = alertDialog.create();
      dlg.show();
      //周期性的检测网络状况，如果开启则自动载入
      mTimer = new Timer(true);
      mTimer.schedule(new TimerTask() {
        public void run() {
          if (IsNet.IsConnect()) {
            new Handler(mContext.getMainLooper()).post(new Runnable() {
              @Override
              public void run() {
                //判断是否是首次登入
                SharedPreferences startTimes = getSharedPreferences("Parameters", 1);
                int times = startTimes.getInt("times", 1);

                Log.e("TIME>>>>>>>>>>>>>>", times + "");
                SharedPreferences.Editor editor = startTimes.edit();
                if (times == 1) {
                  ++times;
                  editor.putInt("times", times);
                  editor.commit();
                  Log.e("AfterSave>>>>>>>>>>", startTimes.getInt("times", 0) + "");
//                                    Intent intent = new Intent(SplashActivity.this,
// CallActivity.class);
//                                    startActivity(intent);
                  if (dlg != null && dlg.isShowing()) {
                    dlg.dismiss();
                  }
                  init();
                  SplashActivity.this.finish();
                  mTimer.cancel();
                } else {
                  ++times;
                  editor.putInt("times", times);
                  editor.commit();
                  Log.e("AfterSave>>>>>>>>>>", startTimes.getInt("times", 0) + "");
//                                    Intent intent = new Intent(SplashActivity.this,
// MainActivity.class);
//                                    startActivity(intent);
                  String[] s = new String[ways.size()];
                  s = ways.toArray(s);
                  Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                  intent.putExtra("ways", s);
                  intent.putExtra("entryActivity", ActivityModel.ACTIVITY);
                  startActivity(intent);
//                                    SplashActivity.this.finish();
                  if (dlg != null && dlg.isShowing()) {
                    dlg.dismiss();
                  }
                  SplashActivity.this.finish();
                  mTimer.cancel();
                }
              }
            });

          }
        }
      }, 2000, 1500);
    }
  }


//    private void is2getParameters() {
//        SharedPreferences sp = getSharedPreferences("Parameters", 0);
//    Calendar calendar = Calendar.getInstance();
//    month = calendar.get(Calendar.MONTH) + 1;
//    day = calendar.get(Calendar.DAY_OF_MONTH);
//    if (month == sp.getInt("month", 0) && day == sp.getInt("day", 0)) {
//        Url.WEIBOWORDS = sp.getString("weiboWordsLimit", "200");
//    } else {
//        weiboApi.getWeiboWordsLimit();
//    }
//}

  Handler handler = new Handler() {
    public void handleMessage(Message msg) {
      super.handleMessage(msg);
      if (msg.what == 0) {
        try {
          JSONObject jsonObject = new JSONObject((String) msg.obj);
          SharedPreferences sharedPreferences = getSharedPreferences("Parameters", 0);
          SharedPreferences.Editor editor = sharedPreferences.edit();
          editor.putString("weiboWordsLimit", jsonObject.getString("limit"));
          editor.putInt("month", Calendar.getInstance().get(Calendar.MONTH) + 1);
          editor.putInt("day", Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
          editor.commit();
        } catch (JSONException E) {

        }
      } else {
        ToastHelper.showToast("错误代码" + msg.what, SplashActivity.this);
      }

    }
  };

}
