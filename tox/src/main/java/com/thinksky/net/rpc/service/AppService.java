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

import android.support.annotation.Nullable;
import com.thinksky.net.rpc.GroupModel;
import com.thinksky.net.rpc.model.BaseModel;
import com.thinksky.net.rpc.model.CollectQuestionModel;
import com.thinksky.net.rpc.model.CountModel;
import com.thinksky.net.rpc.model.GroupChoiceModel;
import com.thinksky.net.rpc.model.GroupDetailModel;
import com.thinksky.net.rpc.model.GroupInfoModel;
import com.thinksky.net.rpc.model.GroupMemberListModel;
import com.thinksky.net.rpc.model.GroupTypeModelList;
import com.thinksky.net.rpc.model.HotPostModel;
import com.thinksky.net.rpc.model.MessageModel;
import com.thinksky.net.rpc.model.PostModel;
import com.thinksky.net.rpc.model.QuestionCategoryModel;
import com.thinksky.net.rpc.model.QuestionDetailModel;
import com.thinksky.net.rpc.model.UnReadCountModel;
import com.thinksky.net.rpc.model.UpgradeModel;
import com.thinksky.net.rpc.model.UserInfoModel;
import com.thinksky.net.rpc.model.UserListModel;
import com.thinksky.net.rpc.model.WeiboDetailModel;
import com.thinksky.net.rpc.model.WeiboModel;
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
      count, @Query("category") String category);

  @GET("api.php?s=Question/getMonQuestionList")
  Observable<Response<WendaModel>> getMonQuestionList(@Query("page") int page, @Query("count") int
      count, @Query("category") String category);

  @GET("api.php?s=Question/getSoluteQuestionList")
  Observable<Response<WendaModel>> getSoluteQuestionList(@Query("page") int page, @Query("count")
  int count, @Query("category") String category);

  @GET("api.php?s=Question/getMyQuestionList")
  Observable<Response<WendaModel>> getMyQuestionList(@Query("session_id") String sessionId,
                                                     @Query("page") int page, @Query("count") int
                                                         count, @Query("category") String category);

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
  Observable<Response<HotPostModel>> getHotPostAll(@Query("page") int page, @Query("count") int
      count);

  @GET("api.php?s=group/getMyPostAll")
  Observable<Response<HotPostModel>> getMyPostAll(@Query("session_id") String sessionId);

  @GET("api.php?s=group/getHotPostAll")
  Observable<Response<HotPostModel>> getGroupMyPost(@Query("session_id") String sessionId,
                                                    @Query("group_id") String groupId);

  @GET("api.php?s=group/getPostAll")
  Observable<Response<HotPostModel>> getGroupAllPost(@Query("group_id") String groupId);

  @GET("api.php?s=question/questionBookmark")
  Observable<Response<BaseModel>> questionBookmark(@Query("session_id") String sessionId, @Query
      ("question_id") String questionId);

  @GET("api.php?s=question/rejectBookmark")
  Observable<Response<BaseModel>> cancelQuestionBookmark(@Query("session_id") String sessionId,
                                                         @Query
                                                             ("question_id") String questionId);

  @GET("api.php?s=question/deleteQuestion")
  Observable<Response<BaseModel>> deleteQuestion(@Query("session_id") String sessionId,
                                                 @Query("question_id") String questionId);

  @GET("api.php?s=group/postBookmark")
  Observable<Response<BaseModel>> postBookmark(@Query("session_id") String sessionId, @Query
      ("post_id") String postId);

  @GET("api.php?s=group/rejectBookmark")
  Observable<Response<BaseModel>> cancelPostBookmark(@Query("session_id") String sessionId, @Query
      ("post_id") String postId);

  @GET("api.php?s=group/deletePost")
  Observable<Response<BaseModel>> deletePost(@Query("session_id") String sessionId,
                                             @Query("post_id") String postId);

  @GET("api.php?s=Question/getMyQColection")
  Observable<Response<CollectQuestionModel>> getMyCollectQuestionList(@Query("session_id") String
                                                                          sessionId, @Query
                                                                          ("page") int page,
                                                                      @Query("count") int count);

  @GET("api.php?s=group/getMyPColection")
  Observable<Response<HotPostModel>> getMyCollectPostList(@Query("session_id") String
                                                              sessionId, @Query
                                                              ("page") int page,
                                                          @Query("count") int count);

  @GET("api.php?s=group/getPostDetail")
  Observable<Response<PostModel>> getPostDetail(@Query("session_id") String sessionId, @Query
      ("post_id") String postId);

  @GET("api.php?s=Question/getHerQuestionList")
  Observable<Response<WendaModel>> getHerQuestionList(@Query("uid") String uid, @Query
      ("page") int page, @Query("count") int count);

  @GET("api.php?s=group/getHerPost")
  Observable<Response<HotPostModel>> getHerPostList(@Query("uid") String uid, @Query
      ("page") int page, @Query("count") int count);

  @GET("api.php?s=group/getHerGroup")
  Observable<Response<GroupInfoModel>> getHerGroup(@Query("uid") String uid, @Query
      ("page") int page, @Query("count") int count);

  @GET("api.php?s=weibo/getHerWeibo")
  Observable<Response<WeiboModel>> getHerWeibo(@Query("uid") String uid, @Query("page") int page,
                                               @Query("count") int count);

  @GET("api.php?s=Message/getAllMessage")
  Observable<Response<MessageModel>> getAllMessage(@Query("session_id") String sessionId, @Query
      ("module") String module);

  @GET("api.php?s=group/getMyPostCount")
  Observable<Response<CountModel>> getMyPostCount(@Query("session_id") String sessionId);

  @GET("api.php?s=group/getWeCreatedGroupAll")
  Observable<Response<GroupModel>> getCreatedGroup(@Query("session_id") String sessionId);

  @GET("api.php?s=group/getJoinedGroupAll")
  Observable<Response<GroupModel>> getJoinedGroup(@Query("session_id") String sessionId);

  @GET("api.php?s=question/getQuestionCategory")
  Observable<Response<QuestionCategoryModel>> getQuestionCategory();

  @GET("api.php?s=group/getGroupType")
  Observable<Response<GroupTypeModelList>> getGroupType();

  @GET("api.php?s=user/getMyFans")
  Observable<Response<UserListModel>> getMyFans(@Query("uid") String userId, @Query("page") int
      page, @Query("count") int count);

  @GET("api.php?s=user/getMyFollows")
  Observable<Response<UserListModel>> getMyFollows(@Query("uid") String userId, @Query("page")
  int page, @Query("count") int count);

  @GET("api.php?s=group/addGroup")
  Observable<BaseModel> createGroup(@Query("session_id") String sessionId, @Query("title") String
      groupName, @Query("type_id")
                                    String category, @Query("type") String type, @Query("logo")
                                    String logo, @Query("detail")
                                    String detail, @Nullable @Query("id") String groupId, @Query
                                        ("notice") String notice);

  @GET("api.php?s=group/addGroup")
  Observable<BaseModel> createGroupNoLogo(@Query("session_id") String sessionId, @Query("title")
  String groupName, @Query("type_id")
                                          String category, @Query("type") String type, @Query
                                              ("detail") String detail, @Nullable @Query
      ("id") String groupId, @Query("notice") String notice);

  @GET("api.php?s=group/getGroupDetail")
  Observable<GroupDetailModel> getGroupDetail(@Query("group_id") String groupId, @Query
      ("session_id") String sessionId);

  @GET("api.php?s=group/getGroupMenmber")
  Observable<GroupMemberListModel> getGroupMember(@Query("id") String groupId, @Query("page") int
      page, @Query("count") int count);

  @GET("api.php?s=group/rejectGroupPeople")
  Observable<BaseModel> removeGroupMember(@Query("session_id") String sessionId, @Query
      ("group_id") String groupId, @Query("uid") String ids);

  @GET("api.php?s=group/tranferGroupManager")
  Observable<BaseModel> tranferGroupManager(@Query("session_id") String sessionId, @Query("uid")
  String userId, @Query("group_id") String groupId);

  @GET("api.php?s=group/getGroupChoice")
  Observable<GroupChoiceModel> getGroupChoice(@Query("page") int page, @Query("count") int count);

  @GET("api.php?s=group/endGroup")
  Observable<BaseModel> endGroup(@Query("session_id") String sessionId, @Query("id") String
      groupId);

  @GET("api.php?s=group/getGroupCheckMenmber")
  Observable<GroupMemberListModel> getGroupCheckMember(@Query("id") String sessionId, @Query
      ("page") int page, @Query("count") int count);

  @GET("api.php?s=group/rejectGroupPeople")
  Observable<BaseModel> rejectGroupPeople(@Query("session_id") String sessionId, @Query("uid")
  String uid, @Query("group_id") String groupId);

  @GET("api.php?s=group/addGroupPeople")
  Observable<BaseModel> addGroupPeople(@Query("session_id") String sessionId, @Query("uid")
  String uid, @Query("group_id") String groupId);

  @GET("api.php?s=Message/getNoReadCount")
  Observable<UnReadCountModel> getUnreadCount(@Query("module") String module, @Query
      ("session_id") String sessionId);

  @GET("api.php?s=weibo/getWeiboD")
  Observable<WeiboDetailModel> getWeiboDetail(@Query("id") String id);

  @GET("api.php?s=Message/getMessageContent")
  Observable<BaseModel> getMessageContent(@Query("id") String msgId);

  @GET("api.php?s=group/sendPost")
  Observable<BaseModel> sendPost(@Query("session_id") String sessionId, @Query("group_id") String groupId, @Query("title") String title,
                                 @Query("content") String content, @Query("attach_id") String attachId);

  @GET("api.php?s=Question/getQuestionDetail&page=1&count=" + Integer.MAX_VALUE)
  Observable<QuestionDetailModel> getQuestionDetail(@Query("session_id") String sessionId, @Query("questionid") String questionId);

  interface Constant {
    String SET_PROFILE_URL = "api.php?s=user/setProfile";
  }
}
