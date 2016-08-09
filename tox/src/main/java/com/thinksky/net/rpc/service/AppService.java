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

import com.thinksky.net.rpc.model.BaseModel;
import com.thinksky.net.rpc.model.HotPostModel;
import com.thinksky.net.rpc.model.UpgradeModel;
import com.thinksky.net.rpc.model.UserInfoModel;
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

  @GET("api.php?s=user/getProfile")
  Observable<Response<UserInfoModel>> getProfile(@Query("uid") String uid);

  @GET("api.php?s=user/logout")
  Observable<Response<BaseModel>> logout(@Query("session_id") String sessionId);


  @GET(Constant.SET_PROFILE_URL)
  Observable<Response<BaseModel>> setNickName(@Query("session_id") String sessionId, @Query
      ("nickname") String nickName);

  @GET(Constant.SET_PROFILE_URL)
  Observable<Response<BaseModel>> setGender(@Query("session_id") String sessionId, @Query("sex")
  String sex);

  @GET(Constant.SET_PROFILE_URL)
  Observable<Response<BaseModel>> setArea(@Query("session_id") String sessionId, @Query
      ("province") String province, @Query("city") String city);

  @GET(Constant.SET_PROFILE_URL)
  Observable<Response<BaseModel>> setBirthday(@Query("session_id") String sessionId, @Query
      ("birthday") String birthday);

  @GET(Constant.SET_PROFILE_URL)
  Observable<Response<BaseModel>> setSignature(@Query("session_id") String sessionId, @Query
      ("signature") String signature);

  @GET(Constant.SET_PROFILE_URL)
  Observable<Response<BaseModel>> setQQ(@Query("session_id") String sessionId, @Query("qq")
  String qq);

  @GET(Constant.SET_PROFILE_URL)
  Observable<Response<BaseModel>> setEmail(@Query("session_id") String sessionId, @Query("email")
  String email);

  @GET("api.php?s=user/doFollow")
  Observable<Response<BaseModel>> doFollow(@Query("session_id") String sessionId, @Query
      ("follow_who") String followUserId);

  @GET("api.php?s=user/endFollow")
  Observable<Response<BaseModel>> endFollow(@Query("session_id") String sessionId, @Query
      ("follow_who") String followUserId);

  @GET("api.php?s=group/getHotPostAll")
  Observable<Response<HotPostModel>> getHotPostAll();

  @GET("api.php?s=group/getMyPostAll")
  Observable<Response<HotPostModel>> getMyPostAll(@Query("session_id") String sessionId);

  @GET("api.php?s=group/getHotPostAll")
  Observable<Response<HotPostModel>> getGroupMyPost(@Query("session_id") String sessionId,
                                                    @Query("group_id") String groupId);

  @GET("api.php?s=group/getPostAll")
  Observable<Response<HotPostModel>> getGroupAllPost(@Query("group_id") String groupId);

  interface Constant {
    String SET_PROFILE_URL = "api.php?s=user/setProfile";
  }
}
