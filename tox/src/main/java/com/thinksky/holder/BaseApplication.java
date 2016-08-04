package com.thinksky.holder;

import android.app.Application;
import android.app.Service;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.os.Vibrator;
import android.support.multidex.MultiDex;
import com.baidu.mapapi.SDKInitializer;
import com.bugtags.library.Bugtags;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.thinksky.Face.LocationService;
import com.thinksky.injection.DaggerGlobalComponent;
import com.thinksky.injection.GlobalComponent;
import com.thinksky.injection.GlobalModule;
import com.thinksky.log.Logger;
import com.thinksky.tox.BuildConfig;
import com.tox.Url;

public class BaseApplication extends Application {

  // 获取到主线程的上下文
  private static BaseApplication mContext = null;
  // 获取到主线程的handler
  private static Handler mMainThreadHandler = null;

  // 获取到主线程
  private static Thread mMainThread = null;
  // 获取到主线程的id
  private static int mMainThreadId;
  // 获取到主线程的looper
  private static Looper mMainThreadLooper = null;
  public LocationService locationService;
  public Vibrator mVibrator;
  private SharedPreferences sp = null;

  private GlobalComponent mGlobalComponent;

  @Override public void onCreate() {
    super.onCreate();
    Logger.init();
    mGlobalComponent = DaggerGlobalComponent.builder().globalModule(new GlobalModule(this)).build();
    this.mContext = this;
    this.mMainThreadHandler = new Handler();
    this.mMainThread = Thread.currentThread();
    this.mMainThreadId = android.os.Process.myTid();
    this.mMainThreadLooper = getMainLooper();
    SDKInitializer.initialize(this);

    ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(this));
    if (BuildConfig.DEBUG) {
      Bugtags.start("309e5c0b10a89d083fcb08b0c17543e2", this, Bugtags.BTGInvocationEventBubble);
    }

    locationService = new LocationService(getApplicationContext());
    mVibrator = (Vibrator) getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
    sp = getSharedPreferences("userInfo", 0);
    if (null != sp) {
      Url.SESSIONID = sp.getString("session_id", "");
      Url.USERID = sp.getString("uid", "0");
    }
    MultiDex.install(this);
  }

  public GlobalComponent getGlobalComponent() {
    return mGlobalComponent;
  }

  // 对外暴露上下文
  public static BaseApplication getApplication() {
    return mContext;
  }

  // 对外暴露主线程的handler
  public static Handler getMainThreadHandler() {
    return mMainThreadHandler;
  }

  // 对外暴露主线程
  public static Thread getMainThread() {
    return mMainThread;
  }

  // 对外暴露主线程id
  public static int getMainThreadId() {
    return mMainThreadId;
  }

  // 对外暴露主线程的looper
  public static Looper getMainThreadLooper() {
    return mMainThreadLooper;
  }
}
