package com.thinksky.injection;

import android.content.Context;
import com.squareup.otto.Bus;
import com.thinksky.holder.BaseApplication;
import com.thinksky.ui.LoginSession;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

/**
 * Created by zhaozeyang on 16/4/13.
 */
@Module
public class GlobalModule {
  private BaseApplication mApplication;

  @Singleton
  public GlobalModule(BaseApplication app) {
    mApplication = app;
  }

  @Singleton
  @Provides
  public Context providesApplicationContext() {
    return mApplication;
  }

  @Singleton
  @Provides
  @GlobalBus
  public Bus providesGlobalBus() {
    return new Bus();
  }

  //@Singleton
  //@Provides
  //public CustomAppPreferences providesGlobalPreferences() {
  //  return new CustomAppPreferences(mApplication);
  //}

  //@Provides
  //public boolean providesLoginStatus(CustomAppPreferences preferences) {
  //  String type = preferences.getString(CustomAppPreferences.KEY_USER_TYPE, "");
  //  int uid = preferences.getInt(CustomAppPreferences.KEY_USER_ID, 0);
  //  return (TextUtils.equals(type, UserInfo.USER_TYPE_PHONE) || TextUtils.equals(type, UserInfo
  //      .USER_TYPE_THIRD))
  //      && uid > 0;
  //}

  @Provides
  @Singleton
  public LoginSession providesLoginSession() {
    return new LoginSession(mApplication);
  }
  //
  //@Provides
  //@DeviceId
  //public String providesDeviceId() {
  //  return DeviceUuidFactory.getInstance(mApplication).getDeviceUuidString();
  //}
}
