/*
 * 文件名: NetConstant
 * 版    权：  Copyright Hengrtech Tech. Co. Ltd. All Rights Reserved.
 * 描    述: [该类的简要描述]
 * 创建人: zhaozeyang
 * 创建时间:16/4/19
 * 
 * 修改人：
 * 修改时间:
 * 修改内容：[修改内容]
 */
package com.thinksky.net.rpc.service;

import com.thinksky.tox.BuildConfig;

/**
 * 服务器常量<BR>
 *
 * @author zhaozeyang
 * @version [Taobei Client V20160411, 16/4/19]
 */
public class NetConstant {
  public static final String URL_DOWNLOAD_APP = "http://192.168.0.1";

  public static final String BASE_URL_LOCATION = BuildConfig.URL_BASE;
  //public static final String BASE_URL_LOCATION = "http://192.168.15.201:8080/taobei/";
  public static final String BASE_URL_SERVICE_SUFFIX = "/api.php";
  public static final String BASE_URL = BASE_URL_LOCATION;
  public static final String UPLOAD_AVATAR_URL = "Uploads/Avatar/";

  private NetConstant() {

  }

  public class HttpCodeConstant {
    public static final int UNKNOWN_ERROR = -1;
    public static final int SUCCESS = 200;
    public static final int HTTP_ERROR_NOT_FOUND = 404;
    public static final int SESSION_EXPIRED = 100;
  }
}
