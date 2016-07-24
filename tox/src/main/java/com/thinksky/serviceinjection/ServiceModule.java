/*
 * 文件名: AppServiceModule
 * 版    权：  Copyright Hengrtech Tech. Co. Ltd. All Rights Reserved.
 * 描    述: [该类的简要描述]
 * 创建人: zhaozeyang
 * 创建时间:16/4/19
 * 
 * 修改人：
 * 修改时间:
 * 修改内容：[修改内容]
 */
package com.thinksky.serviceinjection;

import com.thinksky.injection.GlobalModule;
import com.thinksky.net.RetrofitFactory;
import com.thinksky.net.rpc.service.AppService;
import dagger.Module;
import dagger.Provides;

/**
 * 服务器接口 Module<BR>
 *
 * @author zhaozeyang
 * @version [Taobei Client V20160411, 16/4/19]
 */
@Module(includes = GlobalModule.class)
public class ServiceModule {

  @Provides
  public AppService providesAppService() {
    return RetrofitFactory.createAppService();
  }

}

