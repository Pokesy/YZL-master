/*
 * 文件名: GlobalComponent
 * 版    权：  Copyright Paitao Tech. Co. Ltd. All Rights Reserved.
 * 描    述: [该类的简要描述]
 * 创建人: zhaozeyang
 * 创建时间:16/4/14
 * 
 * 修改人：
 * 修改时间:
 * 修改内容：[修改内容]
 */
package com.thinksky.injection;

import android.content.Context;
import com.squareup.otto.Bus;
import com.thinksky.net.HttpErrorUiNotifier;
import com.thinksky.net.SessionNotifier;
import com.thinksky.tox.MainActivity;
import dagger.Component;
import javax.inject.Singleton;

/**
 * 全局<BR>
 *
 * @author zhaozeyang
 * @version [Taobei Client V20160411, 16/4/14]
 */
@Singleton
@Component(modules = GlobalModule.class)
public interface GlobalComponent {

  Context getApplicationContext();

  @GlobalBus
  Bus getGlobalBus();

  HttpErrorUiNotifier httpErrorUiNotifier();

  SessionNotifier sessionNotifier();

  //CustomAppPreferences appPreferences();
  //
  //LoginSession loginSession();

  //boolean isLogin();

  //@DeviceId
  //String deviceId();

  void inject(MainActivity activity);
}
