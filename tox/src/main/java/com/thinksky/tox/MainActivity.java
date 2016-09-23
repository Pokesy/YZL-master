package com.thinksky.tox;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import com.nineoldandroids.view.ViewHelper;
import com.squareup.otto.Subscribe;
import com.thinksky.fragment.CollectListActivity;
import com.thinksky.fragment.DiscoverFragment;
import com.thinksky.fragment.HomeFragment;
import com.thinksky.fragment.IsseuFragment;
import com.thinksky.fragment.WeiboFragment;
import com.thinksky.fragment.YlqFragment;
import com.thinksky.holder.BaseActivity;
import com.thinksky.holder.BaseApplication;
import com.thinksky.injection.GlobalModule;
import com.thinksky.model.ActivityModel;
import com.thinksky.net.UiRpcSubscriber1;
import com.thinksky.net.UiRpcSubscriberSimple;
import com.thinksky.net.rpc.model.UnReadCountModel;
import com.thinksky.net.rpc.model.UserInfoModel;
import com.thinksky.net.rpc.service.AppService;
import com.thinksky.qqsliding.widget.DragLayout;
import com.thinksky.serviceinjection.DaggerServiceComponent;
import com.thinksky.serviceinjection.ServiceModule;
import com.thinksky.ui.LoginSession;
import com.thinksky.ui.common.TitleBar;
import com.thinksky.ui.profile.FansListActivity;
import com.thinksky.ui.profile.FollowListActivity;
import com.thinksky.ui.profile.MyCollectionActivity;
import com.thinksky.ui.profile.MyMessageActivity;
import com.thinksky.ui.profile.ProfileIntentFactory;
import com.thinksky.ui.profile.ProfileSettingActivity;
import com.thinksky.utils.imageloader.ImageLoader;
import com.tox.Url;
import java.util.ArrayList;
import javax.inject.Inject;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by jiao on 2016/1/26.
 */
public class MainActivity extends BaseActivity implements View.OnClickListener {

  /**
   * 用于展示  的Fragment
   */
  private HomeFragment homeFragment;

  /**
   * 用于展示动态的Fragment
   */
  private WeiboFragment WeiBoFragment;

  /**
   * 用于展示鱼医生的Fragment
   */
  private IsseuFragment issueFragment;

  /**
   * 用于展示鱼乐圈的Fragment
   */
  private YlqFragment ylqFragment;

  /**
   * 用于展示发现的Fragment
   */
  private DiscoverFragment mallFragment;

  //从UserInfoActivity返回的信息
  private Intent intent_Share;
  private static boolean hasCheckd = false;
  /**
   * 用于对Fragment进行 理
   */
  private long exitTime = 0;
  private ArrayList<String> ways = new ArrayList<String>();
  private FragmentTransaction mFragmentTransaction;
  private FragmentManager mFragmentManager;
  private DrawerLayout drawer_layout;
  private ActionBarDrawerToggle drawerToggle;
  private View mContent;
  private DragLayout dl;
  private ListView lv;
  private LinearLayout drawer_view;
  private RadioGroup mTabGroup;
  private SlideController mController;

  private TitleBar mTitleBar;

  @Inject
  AppService mAppService;

  private View.OnClickListener mWriteFeedListener = new View.OnClickListener() {
    @Override
    public void onClick(View v) {
      if (!Url.SESSIONID.equals("")) {
        Intent intent = new Intent(MainActivity.this, UploadActivity.class);
        startActivity(intent);
      } else {
        Log.e(">>>>>>>>>>>>>>>", "login Form WeiBoFragment");

        String[] s = new String[ways.size()];
        s = ways.toArray(s);
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.putExtra("ways", s);
        intent.putExtra("entryActivity", ActivityModel.SENDWEIBO);
        startActivity(intent);
      }
    }
  };

  private View.OnClickListener mRefreshListener = new View.OnClickListener() {

    @Override
    public void onClick(View v) {
      getComponent().getGlobalBus().post(new DiscoverFragment.ReLocationEvent());
    }
  };

  //再按一次退出
  @Override
  public boolean onKeyDown(int keyCode, KeyEvent event) {
    if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
      if ((System.currentTimeMillis() - exitTime) > 2000) {
        Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
        exitTime = System.currentTimeMillis();
      } else {
        finish();
        //                System.exit(0);
      }
      return true;
    }
    return super.onKeyDown(keyCode, event);
  }

  @Override
  protected void onLogin() {
    super.onLogin();
    drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
  }

  @Override
  protected void onLogout() {
    super.onLogout();
    drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    if (drawer_layout.isDrawerOpen(Gravity.LEFT)) {
      drawer_layout.closeDrawer(Gravity.LEFT);
    }
  }

  protected void initActionbar() {
    mTitleBar = (TitleBar) findViewById(R.id.title_bar);
    setSupportActionBar(mTitleBar.getToolbar());

    drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
    if (!isLogin()) {
      drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    } else {
      drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    }

    drawer_view = (LinearLayout) findViewById(R.id.drawer_view);
    mContent = drawer_layout.getChildAt(0);
    drawerToggle =
        new ActionBarDrawerToggle(this, drawer_layout, mTitleBar.getToolbar(), R.string.drawer_open,
            R.string.drawer_close) {
          @Override
          public void onDrawerSlide(View drawerView, float slideOffset) {
            super.onDrawerSlide(drawerView, slideOffset);
            float scale = 1 - slideOffset;
            ViewHelper.setTranslationX(mContent, drawerView.getMeasuredWidth() * (1 - scale));
          }

          @Override
          public void onDrawerOpened(View drawerView) {
            super.onDrawerOpened(drawerView);
            manageRpcCall(mAppService.getProfile(Url.USERID), new
                UiRpcSubscriberSimple<UserInfoModel>
                    (MainActivity.this) {


                  @Override
                  protected void onSuccess(UserInfoModel info) {
                    getComponent().loginSession().saveUserInfoModel(info);
                    mController.setData();
                  }

                  @Override
                  protected void onEnd() {

                  }
                });
          }

          @Override
          public void onDrawerClosed(View drawerView) {
            super.onDrawerClosed(drawerView);
          }
        };
    drawerToggle.setDrawerIndicatorEnabled(false);
    //        drawerToggle.setHomeAsUpIndicator(R.drawable.iconfont_gerenshezhi);
    drawer_layout.addDrawerListener(drawerToggle);
    drawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (!drawer_layout.isDrawerOpen(drawer_view)) {
          drawer_layout.openDrawer(drawer_view);
        } else {
          drawer_layout.closeDrawer(drawer_view);
        }
      }
    });
    drawerToggle.syncState();

    //        lv = (ListView) findViewById(R.id.lv);
    //        lv.setAdapter(new LeftItemAdapter(this));

    mTitleBar.setLogoVisible(true);
    mTitleBar.setLeftImgMenu(R.drawable.icon_title_bar_profile, new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        showOrHideDrawer();
      }
    });
  }

  /**
   * 如果想是实现滑动菜单可以滑动，必 实现如下方法
   */
  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    return drawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
  }

  protected void initView() {
    setContentView(R.layout.activity_home);

    mTitleBar = (TitleBar) findViewById(R.id.title_bar);

    mFragmentManager = MainActivity.this.getSupportFragmentManager();
    initAllFragment();
    mTabGroup = ((RadioGroup) findViewById(R.id.main_radio));
    mTabGroup.setOnCheckedChangeListener(
        new RadioGroup.OnCheckedChangeListener() {
          @Override
          public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
              case R.id.rb_weibo:
                setTabSelection(1);
                break;
              case R.id.rb_yulequan:
                setTabSelection(2);
                break;
              case R.id.rb_yuyisheng:
                setTabSelection(3);
                break;
              case R.id.rb_mall:
                setTabSelection(4);
                break;
              default:
                setTabSelection(0);
                break;
            }
          }
        });


    setTabSelection(0);
  }

  @Override
  protected void setLeftMenu() {
    mController = new SlideController(drawer_view);
    mController.setup();
  }


  @Override
  protected void init() {
    //        if (BuildConfig.DEBUG) {
    //            AnswerDialogFragment.show(getSupportFragmentManager(), new AnswerDialogFragment
    // .ArgBean());
    //        }
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.ll1:
      case R.id.user:
        if (!TextUtils.isEmpty(Url.SESSIONID)) {
          startActivity(ProfileIntentFactory.makeIntent(MainActivity.this, Url.USERID));
        } else {
          String[] s = new String[ways.size()];
          s = ways.toArray(s);
          Intent intent = new Intent(MainActivity.this, LoginActivity.class);
          intent.putExtra("ways", s);
          intent.putExtra("entryActivity", ActivityModel.USERINFO);
          startActivity(intent);
        }
        break;
      case R.id.setting:
        Intent intent = new Intent(MainActivity.this, SettingActivity.class);
        startActivity(intent);
        break;
      case R.id.collect:
        if (!TextUtils.isEmpty(Url.SESSIONID)) {
          intent = new Intent(MainActivity.this, CollectListActivity.class);
          startActivity(intent);
        } else {
          String[] s = new String[ways.size()];
          s = ways.toArray(s);
          Intent intent1 = new Intent(MainActivity.this, LoginActivity.class);
          intent1.putExtra("ways", s);
          intent1.putExtra("entryActivity", ActivityModel.USERINFO);
          startActivity(intent1);
        }
        break;
      case R.id.invite:
        ////InputStream is=getResources().openRawResource(R.raw.ic_launcher);
        //
        //intent_Share = new Intent(Intent.ACTION_SEND);
        ////Uri imageUri =  Uri.parse("android.resource://package_name/raw/ic_luncher.png");
        ////Uri imageUri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.ic_launcher);
        ////intent_Share.putExtra(Intent.EXTRA_STREAM, imageUri);
        ////intent_Share.setType("image/*");
        //intent_Share.putExtra(Intent.EXTRA_SUBJECT, "分享");
        //intent_Share.putExtra(Intent.EXTRA_TEXT, " http://a.app.qq.com/o/simple.jsp?pkgname=com" +
        //    ".hengrtech.yuzhile"
        //    + "（来自鱼知乐手机客户端）");//分享内容体
        //intent_Share.setType("text/plain");
        //
        //
        ////intent_Share.putExtra(Intent.ACTION_PACKAGE_ADDED,
        ////    "http://a.app.qq.com/o/simple.jsp?pkgname=com.hengrtech.yuzhile");
        //// 分享内容体
        //startActivity(Intent.createChooser(intent_Share, "分享"));//分享选择页面标题
        showShare();
        break;
      case R.id.message1:
        break;
      case R.id.search:

        break;
      case R.id.feed_back:
        startActivity(new Intent(this, Setting_yijianActivity.class));
        break;
      default:
        break;
    }
  }

  private void showShare() {
    ShareSDK.initSDK(MainActivity.this);
    OnekeyShare oks = new OnekeyShare();
    //关闭sso授权
    oks.disableSSOWhenAuthorize();

    // 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
    //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
    // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
    oks.setTitle(getString(R.string.app_name));
    // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
    oks.setTitleUrl("http://a.app.qq.com/o/simple.jsp?pkgname=com.hengrtech.yuzhile");
    // text是分享文本，所有平台都需要这个字段
    oks.setText("鱼知乐");
    //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
    oks.setImageUrl("http://www.yuzhile.com/apk/ic_launcher.png");
    // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
    //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
    // url仅在微信（包括好友和朋友圈）中使用
    oks.setUrl("http://a.app.qq.com/o/simple.jsp?pkgname=com.hengrtech.yuzhile");
    // comment是我对这条分享的评论，仅在人人网和QQ空间使用
    oks.setComment("鱼知乐");
    // site是分享此内容的网站名称，仅在QQ空间使用
    oks.setSite(getString(R.string.app_name));
    // siteUrl是分享此内容的网站地址，仅在QQ空间使用
    oks.setSiteUrl("http://a.app.qq.com/o/simple.jsp?pkgname=com.hengrtech.yuzhile");

    // 启动分享GUI
    oks.show(this);

  }


  private void initAllFragment() {
    // 如果MessageFragment为空，则创建一个并添加到界面上
    homeFragment = new HomeFragment();
    mFragmentTransaction = mFragmentManager.beginTransaction();
    mFragmentTransaction.add(R.id.content, homeFragment);
    homeFragment.setOnHomeTitleBarClickListener(
        new HomeFragment.OnHomeTitleBarClickListener() {

          @Override
          public void onMenuBtnClicked() {
            showOrHideDrawer();
          }

          @Override
          public void onSearchBtnClicked() {

          }
        });
    // 如果NewsFragment为空，则创建一个并添加到界面上
    WeiBoFragment = new WeiboFragment();
    mFragmentTransaction.add(R.id.content, WeiBoFragment);
    // 如果SettingFragment为空，则创建一个并添加到界面上
    ylqFragment = new YlqFragment();
    mFragmentTransaction.add(R.id.content, ylqFragment);
    // 如果ContactsFragment为空，则创建一个并添加到界面上
    issueFragment = new IsseuFragment();
    mFragmentTransaction.add(R.id.content, issueFragment);
    // 如果ContactsFragment为空，则创建一个并添加到界面上
    mallFragment = new DiscoverFragment();
    mFragmentTransaction.add(R.id.content, mallFragment);
    mFragmentTransaction.commitAllowingStateLoss();
  }

  private void showOrHideDrawer() {
    if (!isLogin()) {
      startActivity(new Intent(MainActivity.this, LoginActivity.class));
      return;
    }
    if (drawer_layout.isDrawerOpen(Gravity.LEFT)) {
      drawer_layout.closeDrawer(Gravity.LEFT);
    } else {
      drawer_layout.openDrawer(Gravity.LEFT);
    }
  }

  /**
   * 根据传入的index参数来设 选中的tab 。
   *
   * @param index 每个tab 对应的下标。0表示  ，1表示动态，2表示鱼乐圈，3表示鱼医生，4表示商城。
   */
  private void setTabSelection(int index) {
    //        // 每次选中之前先清楚掉上次的选中状态
    //        clearSelection();
    // 开启一个Fragment事务
    mFragmentTransaction = mFragmentManager.beginTransaction();
    // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
    hideFragments(mFragmentTransaction);
    mTitleBar.setVisibility(View.GONE);
    switch (index) {
      case 0:
        // 当点击了消息tab时，改变控件的图片和文字颜色
        //                home_image.setImageResource(R.drawable.message_selected);
        //                messageText.setTextColor(Color.WH ITE);
        if (homeFragment == null) {
          // 如果MessageFragment为空，则创建一个并添加到界面上
          homeFragment = new HomeFragment();
          mFragmentTransaction.add(R.id.content, homeFragment);
          homeFragment.setOnHomeTitleBarClickListener(
              new HomeFragment.OnHomeTitleBarClickListener() {

                @Override
                public void onMenuBtnClicked() {
                  showOrHideDrawer();
                }

                @Override
                public void onSearchBtnClicked() {

                }
              });
        } else {
          // 如果MessageFragment不为空，则直接将它显示出来
          mFragmentTransaction.show(homeFragment);
        }
        break;
      case 1:
        // 当点击了动态tab时，改变控件的图片和文字颜色
        //                newsImage.setImageResource(R.drawable.news_selected);
        //                newsText.setTextColor(Color.WHITE);
        if (WeiBoFragment == null) {
          // 如果NewsFragment为空，则创建一个并添加到界面上
          WeiBoFragment = new WeiboFragment();
          mFragmentTransaction.add(R.id.content, WeiBoFragment);
        } else {
          // 如果NewsFragment不为空，则直接将它显示出来
          mFragmentTransaction.show(WeiBoFragment);
        }

        break;
      case 2:

        // 当点击了设置tab时，改变控件的图片和文字颜色
        //                settingImage.setImageResource(R.drawable.setting_selected);
        //                settingText.setTextColor(Color.WHITE);
        if (ylqFragment == null) {
          // 如果SettingFragment为空，则创建一个并添加到界面上
          ylqFragment = new YlqFragment();
          mFragmentTransaction.add(R.id.content, ylqFragment);
        } else {
          // 如果SettingFragment不为空，则直接将它显示出来
          mFragmentTransaction.show(ylqFragment);
        }
        break;
      case 3:
        // 当点击了联系人tab时，改变控件的图片和文字颜色
        //                contactsImage.setImageResource(R.drawable.contacts_selected);
        //                contactsText.setTextColor(Color.WHITE);
        if (issueFragment == null) {
          // 如果ContactsFragment为空，则创建一个并添加到界面上
          issueFragment = new IsseuFragment();
          mFragmentTransaction.add(R.id.content, issueFragment);
        } else {
          // 如果ContactsFragment不为空，则直接将它显示出来
          mFragmentTransaction.show(issueFragment);
        }
        break;

      case 4:
        // 当点击了联系人tab时，改变控件的图片和文字颜色
        //                contactsImage.setImageResource(R.drawable.contacts_selected);
        //                contactsText.setTextColor(Color.WHITE);
        if (mallFragment == null) {
          // 如果ContactsFragment为空，则创建一个并添加到界面上
          mallFragment = new DiscoverFragment();
          mFragmentTransaction.add(R.id.content, mallFragment);
        } else {
          // 如果ContactsFragment不为空，则直接将它显示出来
          mFragmentTransaction.show(mallFragment);
        }
        break;
    }
    mFragmentTransaction.commitAllowingStateLoss();
    performTitleBarChange(index);
  }

  private void performTitleBarChange(int tabIndex) {
    switch (tabIndex) {
      case 1:
        mTitleBar.setVisibility(View.VISIBLE);
        mTitleBar.setRightTextBtn(R.string.title_bar_right_btn_write_feed, mWriteFeedListener);
        break;
      case 4:
        mTitleBar.setVisibility(View.VISIBLE);
        mTitleBar.setRightTextBtn(R.string.title_bar_right_btn_refresh, mRefreshListener);
        break;
      case 0:
        mTitleBar.setVisibility(View.GONE);
        break;
      default:
        mTitleBar.setRightTextBtnVisible(false);
        mTitleBar.setVisibility(View.VISIBLE);
        break;
    }
  }

  /**
   * 将所有的Fragment都 为 藏状态。
   *
   * @param mFragmentTransaction 用于对Fragment执行操作的事务
   */
  private void hideFragments(FragmentTransaction mFragmentTransaction) {
    if (homeFragment != null) {
      mFragmentTransaction.hide(homeFragment);
    }
    if (WeiBoFragment != null) {
      mFragmentTransaction.hide(WeiBoFragment);
    }
    if (issueFragment != null) {
      mFragmentTransaction.hide(issueFragment);
    }
    if (ylqFragment != null) {
      mFragmentTransaction.hide(ylqFragment);
    }
    if (mallFragment != null) {
      mFragmentTransaction.hide(mallFragment);
    }
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    setLeftMenu();
  }

  @Subscribe
  public void handleGroupMoreClickEvent(HomeFragment.GroupMoreClickEvent event) {
    // TODO 跳转到鱼乐圈小组精选
    mTabGroup.check(R.id.rb_yulequan);
  }

  @Subscribe
  public void handleZhuanjiMoreClickEvent(HomeFragment.ZhuanjiMoreClickEvent event) {
    // TODO 跳转到鱼医生精彩视频
    mTabGroup.check(R.id.rb_yuyisheng);
  }

  @Subscribe
  public void handleHuatiMoreClickEvent(HomeFragment.HuatiMoreClickEvent event) {
    // TODO 跳转到鱼乐圈话题
    mTabGroup.check(R.id.rb_yulequan);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    inject();
    super.onCreate(savedInstanceState);
    checkNewVersion();
  }

  private void inject() {
    DaggerServiceComponent.builder().globalModule(new GlobalModule(BaseApplication.getApplication
        ())).serviceModule(new ServiceModule())
        .build().inject(this);
  }

  private void checkNewVersion() {
    if (hasCheckd) {
      return;
    }
    hasCheckd = true;
    getComponent().upgradeHelper().checkUpgradeInfo();
  }

  @Subscribe
  public void handleEnterMapEvent(EnterMapEvent enterMapEvent) {
    drawer_layout.closeDrawer(Gravity.LEFT);
    mTabGroup.check(R.id.rb_mall);
  }

  @Subscribe
  public void handleuserInfoChangeEvent(LoginSession.UserInfoChangeEvent event) {
    mController.setData();
  }

  @Subscribe
  public void handleMessageReadEvent(MyMessageActivity.MessageReadEvent event) {
    mController.getUnreadStatus();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
  }

  private class SlideController {
    private View rootView;

    private TitleBar mTitleBar;
    private ImageView mAvatarView;
    private TextView mNameView;
    private ImageView mGenderView;
    private TextView mFollowView;
    private TextView mFansView;
    private TextView mAreaView;
    private TextView mSignatureView;
    private TextView mLocationView;
    private View mMessageMenuView;
    private View mCollectionView;
    private View mInviteFriendsView;
    private View mSettingView;
    private View mBtnFans;
    private View mBtnFollow;
    private ImageView mNewStatusView;
    private TextView mLevelView;

    public SlideController(View rootView) {
      this.rootView = rootView;
      setup();
      setData();
    }

    private void setup() {
      mTitleBar = (TitleBar) rootView.findViewById(R.id.title_bar);
      mAvatarView = (ImageView) rootView.findViewById(R.id.avatar);
      mNameView = (TextView) rootView.findViewById(R.id.nick_name);
      mGenderView = (ImageView) rootView.findViewById(R.id.gender);
      mFansView = (TextView) rootView.findViewById(R.id.fans_count);
      mFollowView = (TextView) rootView.findViewById(R.id.follow_count);
      mAreaView = (TextView) rootView.findViewById(R.id.area_value);
      mSignatureView = (TextView) rootView.findViewById(R.id.signature_value);
      mLocationView = (TextView) rootView.findViewById(R.id.enter_map);
      mMessageMenuView = rootView.findViewById(R.id.menu_message);
      mCollectionView = rootView.findViewById(R.id.menu_collect);
      mInviteFriendsView = rootView.findViewById(R.id.menu_invite);
      mSettingView = rootView.findViewById(R.id.menu_setting);
      mBtnFans = rootView.findViewById(R.id.btn_fans);
      mBtnFollow = rootView.findViewById(R.id.btn_follow);
      mNewStatusView = (ImageView) rootView.findViewById(R.id.icon_new_msg);
      mLevelView = (TextView) rootView.findViewById(R.id.level);

      mLocationView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          UserInfoModel info = BaseApplication.getApplication().getGlobalComponent().loginSession()
              .getUserInfo();
          if (TextUtils.isEmpty(info.getLatitude()) || TextUtils.isEmpty(info.getLongitude())) {
            return;
          }
          getComponent().getGlobalBus().post(new EnterMapEvent(TextUtils.equals(info.getIsfactory
              (), "2"), info.getLongitude(), info
              .getLatitude()));
        }
      });

      mMessageMenuView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          // TODO 跳转到消息中心界面
          startActivity(new Intent(MainActivity.this, com.thinksky.ui.profile.MyMessageActivity
              .class));
        }
      });

      mCollectionView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          startActivity(new Intent(MainActivity.this, MyCollectionActivity.class));
        }
      });

      mInviteFriendsView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          showShare();
        }
      });

      mSettingView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          // 跳转到设置界面
          Intent intent = new Intent(MainActivity.this, SettingActivity.class);
          startActivity(intent);
        }
      });

      mBtnFans.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          startActivity(FansListActivity.makeIntent(MainActivity.this, Url.USERID));
        }
      });

      mBtnFollow.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          startActivity(FollowListActivity.makeIntent(MainActivity.this, Url.USERID));
        }
      });
    }

    public void setData() {
      UserInfoModel info = BaseApplication.getApplication().getGlobalComponent().loginSession()
          .getUserInfo();
      ImageLoader.loadOptimizedHttpImage(MainActivity.this, info.getAvatar128()).dontAnimate()
          .bitmapTransform(new CropCircleTransformation(MainActivity.this)).placeholder(R
          .drawable.side_user_avatar).into(mAvatarView);
      mGenderView.setVisibility(View.VISIBLE);
      if (!TextUtils.isEmpty(info.getSex())) {
        switch (info.getSex()) {
          case UserInfoModel.GENDER_MALE:
            mGenderView.setImageResource(R.drawable.icon_profile_gender_male);
            break;
          case UserInfoModel.GENDER_FEMALE:
            mGenderView.setImageResource(R.drawable.icon_profile_gender_female);
            break;
          default:
            mGenderView.setVisibility(View.GONE);
            break;
        }
      }
      mNameView.setText(info.getNickname());
      mAreaView.setText(TextUtils.isEmpty(info.getP_province()) ? "" : info.getP_province() +
          (TextUtils.isEmpty(info.getP_city()) ? "" : info.getP_city()));
      mSignatureView.setText(info.getSignature());
      mFansView.setText(info.getFans());
      mFollowView.setText(info.getFollowing());
      if (TextUtils.isEmpty(info.getLatitude()) || TextUtils.isEmpty(info.getLongitude())) {
        mLocationView.setText(R.string.activity_profile_setting_default_value);
        mLocationView.setTextColor(getResources().getColor(R.color.font_color_secondary));
      } else {
        mLocationView.setText(R.string.activity_profile_enter_map);
        mLocationView.setTextColor(getResources().getColor(R.color.font_color_blue));
      }
      mLevelView.setText(info.getTitle());
      setTitleBar();
      getUnreadStatus();
    }

    private void setTitleBar() {
      mTitleBar.getTitleBgView().setAlpha(0);
      mTitleBar.setLeftImgMenu(R.drawable.icon_title_bar_back, new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          drawer_layout.closeDrawer(drawer_view);
        }
      });
      mTitleBar.setRightTextBtn(R.string.activity_profile_title_right_btn, new View
          .OnClickListener() {


        @Override
        public void onClick(View v) {
          startActivity(new Intent(MainActivity.this, ProfileSettingActivity.class));
        }
      });
    }

    private void getUnreadStatus() {
      Observable<UnReadCountModel> observable = mAppService.getUnreadCount("4", Url.SESSIONID)
          .observeOn(AndroidSchedulers.mainThread())
          .filter(new Func1<UnReadCountModel, Boolean>() {
            @Override
            public Boolean call(UnReadCountModel unReadCountModel) {
              if (unReadCountModel.getCount() > 0) {
                mNewStatusView.setVisibility(View.VISIBLE);
              } else {
                mNewStatusView.setVisibility(View.GONE);
              }
              return unReadCountModel.getCount() <= 0;
            }
          }).observeOn(Schedulers.newThread()).flatMap(new Func1<UnReadCountModel,
              Observable<UnReadCountModel>>() {
            @Override
            public Observable<UnReadCountModel> call(UnReadCountModel unReadCountModel) {
              return mAppService.getUnreadCount("16", Url.SESSIONID);
            }
          }).observeOn(AndroidSchedulers.mainThread()).filter(new Func1<UnReadCountModel,
              Boolean>() {
            @Override
            public Boolean call(UnReadCountModel unReadCountModel) {
              if (unReadCountModel.getCount() > 0) {
                mNewStatusView.setVisibility(View.VISIBLE);
              } else {
                mNewStatusView.setVisibility(View.GONE);
              }
              return unReadCountModel.getCount() <= 0;
            }
          }).observeOn(Schedulers.newThread()).flatMap(new Func1<UnReadCountModel,
              Observable<UnReadCountModel>>() {
            @Override
            public Observable<UnReadCountModel> call(UnReadCountModel unReadCountModel) {
              return mAppService.getUnreadCount("16", Url.SESSIONID);
            }
          });

      manageRpcCall(observable, new UiRpcSubscriber1<UnReadCountModel>(MainActivity.this) {
        @Override
        protected void onSuccess(UnReadCountModel unReadCountModel) {
          if (unReadCountModel.getCount() > 0) {
            mNewStatusView.setVisibility(View.VISIBLE);
          } else {
            mNewStatusView.setVisibility(View.GONE);
          }
        }

        @Override
        protected void onEnd() {

        }
      });

    }


  }

  public static class EnterMapEvent {
    public boolean isFactory;
    public String lng;
    public String lat;

    public EnterMapEvent(boolean isFactory, String lng, String lat) {
      this.isFactory = isFactory;
      this.lng = lng;
      this.lat = lat;
    }
  }
}

