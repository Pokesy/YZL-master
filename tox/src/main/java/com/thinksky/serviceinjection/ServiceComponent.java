/*
 * 文件名: AppServiceComponent
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

import com.thinksky.fragment.HomeFragment;
import com.thinksky.fragment.MyGroupFragment;
import com.thinksky.fragment.MyhuatiActivity;
import com.thinksky.fragment.QuestionDetailActivity;
import com.thinksky.fragment.RemenhuatiActivity;
import com.thinksky.fragment.RemenhuatiFragment;
import com.thinksky.fragment.WendaFragment;
import com.thinksky.fragment.WendaListActivity;
import com.thinksky.fragment.WendaMyQuestionActivity;
import com.thinksky.net.rpc.service.AppService;
import com.thinksky.net.rpc.service.UserService;
import com.thinksky.tox.GroupDetailActivity;
import com.thinksky.tox.GroupInfoActivity;
import com.thinksky.tox.GroupMyActivity;
import com.thinksky.tox.GroupPostInfoActivity;
import com.thinksky.tox.MainActivity;
import com.thinksky.tox.SettingActivity;
import com.thinksky.ui.profile.ActivityMessageFragment;
import com.thinksky.ui.profile.AvatarChoosePresenter;
import com.thinksky.ui.profile.DoctorMessageFragment;
import com.thinksky.ui.profile.OtherGroupListActivity;
import com.thinksky.ui.profile.OtherPostListActivity;
import com.thinksky.ui.profile.OtherProfileActivity;
import com.thinksky.ui.profile.OtherQuestionListActivity;
import com.thinksky.ui.profile.PostCollectionFragment;
import com.thinksky.ui.profile.ProfileSettingActivity;
import com.thinksky.ui.profile.QuestionCollectionFragment;
import com.thinksky.ui.profile.YLQMessageFragment;
import com.thinksky.utils.UpgradeHelper;
import dagger.Component;
import javax.inject.Singleton;

/**
 * 服务器接口 Component<BR>
 *
 * @author zhaozeyang
 * @version [Taobei Client V20160411, 16/4/19]
 */
@Singleton
@Component(modules = ServiceModule.class)
public interface ServiceComponent {

  AppService appService();

  UserService userService();

  void inject(UpgradeHelper upgradeHelper);

  void inject(WendaFragment wendaFragment);

  void inject(WendaListActivity wendaListActivity);

  void inject(WendaMyQuestionActivity wendaMyQuestionActivity);

  void inject(AvatarChoosePresenter avatarChoosePresenter);

  void inject(MainActivity activity);

  void inject(SettingActivity settingActivity);

  void inject(ProfileSettingActivity profileSettingActivity);

  void inject(OtherProfileActivity otherProfileActivity);

  void inject(HomeFragment homeFragment);

  void inject(MyhuatiActivity myhuatiActivity);

  void inject(RemenhuatiActivity remenhuatiActivity);

  void inject(RemenhuatiFragment remenhuatiFragment);

  void inject(GroupMyActivity groupMyActivity);

  void inject(GroupDetailActivity groupDetailActivity);

  void inject(GroupInfoActivity groupInfoActivity);

  void inject(QuestionDetailActivity questionDetailActivity);

  void inject(QuestionCollectionFragment questionCollectionFragmet);

  void inject(GroupPostInfoActivity groupPostInfoActivity);

  void inject(PostCollectionFragment postCollectionFragment);

  void inject(OtherQuestionListActivity otherQuestionListActivity);

  void inject(OtherPostListActivity otherPostListFragment);

  void inject(OtherGroupListActivity otherGroupListActivity);

  void inject(ActivityMessageFragment activityMessageFragment);

  void inject(DoctorMessageFragment doctorMessageFragment);

  void inject(YLQMessageFragment ylqMessageFragment);

  void inject(MyGroupFragment myGroupFragment);
}
