/*
 * 文件名: AppService
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

import com.thinksky.net.rpc.model.UpgradeModel;
import com.thinksky.net.rpc.model.WendaModel;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;

/**
 * [一句话功能简述]<BR>
 * [功能详细描述]
 *
 * @author zhaozeyang
 * @version [Taobei Client V20160411, 16/4/19]
 */
public interface AppService {


  @GET("api.php")
  Observable<Response<UpgradeModel>> getLatestVersion(@Query("s") String
                                                          version);

  @GET
  @Streaming
  Observable<Response<ResponseBody>> downloadAPK(@Url String fileUrl);

  @GET("api.php?s=Question/getQuestionList")
  Observable<Response<WendaModel>> getQuestionList(@Query("page") int page, @Query("count") int
      count);

  @GET("api.php?s=Question/getHotQuestionList")
  Observable<Response<WendaModel>> getHotQuestionList(@Query("page") int page, @Query("count") int
      count);

  @GET("api.php?s=Question/getMonQuestionList")
  Observable<Response<WendaModel>> getMonQuestionList(@Query("page") int page, @Query("count") int
      count);

  @GET("api.php?s=Question/getSoluteQuestionList")
  Observable<Response<WendaModel>> getSoluteQuestionList(@Query("page") int page, @Query("count")
  int
      count);

  @GET("api.php?s=Question/getMyQuestionList")
  Observable<Response<WendaModel>> getMyQuestionList(@Query("session_id") String sessionId,
                                                     @Query("page") int page, @Query("count") int
      count);
}
