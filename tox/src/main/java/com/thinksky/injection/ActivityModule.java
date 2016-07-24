/*
 * 文件名: ActivityModule
 * 版    权：  Copyright Paitao Tech. Co. Ltd. All Rights Reserved.
 * 描    述: [该类的简要描述]
 * 创建人: zhaozeyang
 * 创建时间:16/4/15
 * 
 * 修改人：
 * 修改时间:
 * 修改内容：[修改内容]
 */
package com.thinksky.injection;

import android.app.Activity;
import com.squareup.otto.Bus;
import com.thinksky.net.RpcCallManager;
import com.thinksky.utils.UpgradeHelper;
import dagger.Module;
import dagger.Provides;

/**
 * [一句话功能简述]<BR>
 *
 * @author zhaozeyang
 * @version [Taobei Client V20160411, 16/4/15]
 */
@Module
public class ActivityModule {
  private Activity mActivity;

  public ActivityModule(Activity activity) {
    mActivity = activity;
  }

  @Provides
  public Activity providesActivity() {
    return mActivity;
  }

  @Provides
  @ActivityBus
  public Bus providesActivityBus() {
    return new Bus();
  }

  @Provides
  public RpcCallManager providesRpcCallManager() {
    return new RpcCallManager.RpcCallManagerImpl();
  }

  @Provides
  public UpgradeHelper providesUpgradeHelper() {
    return new UpgradeHelper(mActivity);
  }
}
