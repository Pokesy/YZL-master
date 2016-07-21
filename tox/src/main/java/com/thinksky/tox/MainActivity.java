package com.thinksky.tox;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
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
import com.nineoldandroids.view.ViewHelper;
import com.squareup.otto.Subscribe;
import com.thinksky.fragment.CollectListActivity;
import com.thinksky.fragment.DiscoverFragment;
import com.thinksky.fragment.HomeFragment;
import com.thinksky.fragment.IsseuFragment;
import com.thinksky.fragment.MyMessageActivity;
import com.thinksky.fragment.WeiboFragment;
import com.thinksky.fragment.YlqFragment;
import com.thinksky.holder.BaseActivity;
import com.thinksky.model.ActivityModel;
import com.thinksky.qqsliding.widget.DragLayout;
import com.thinksky.ui.common.TitleBar;
import com.thinksky.utils.LoadImg;
import com.tox.BaseFunction;
import com.tox.ToastHelper;
import com.tox.Url;
import java.util.ArrayList;

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
  private WeiboFragment weiboFragment;

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
  private String backName, backAvater;
  private TextView myUserName;
  private TextView signature;
  //头像
  private ImageView mleftHead, sousuo;
  LinearLayout mLoginThisAPP;
  private SharedPreferences sp = null;
  //下载图片
  private LoadImg loadImgMainImg;
  private Intent intent_Share;
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
  private TextView user;
  private TextView setting;
  public TextView collect;
  public TextView invite;
  public TextView message1;
  private TextView mFeedbackMenu;
  private LinearLayout drawer_view;
  private RadioGroup mTabGroup;

  private TitleBar mTitleBar;
  private View.OnClickListener mWriteFeedListener = new View.OnClickListener() {
    @Override
    public void onClick(View v) {
      if (!Url.SESSIONID.equals("")) {
        Intent intent = new Intent(MainActivity.this, UploadActivity.class);
        startActivity(intent);
      } else {
        Log.e(">>>>>>>>>>>>>>>", "login Form WeiboFragment");

        String[] s = new String[ways.size()];
        s = ways.toArray(s);
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.putExtra("ways", s);
        intent.putExtra("entryActivity", ActivityModel.SENDWEIBO);
        startActivity(intent);
      }
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

  protected void initActionbar() {
    mTitleBar = (TitleBar) findViewById(R.id.title_bar);
    setSupportActionBar(mTitleBar.getToolbar());

    drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);

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
        };
    drawerToggle.setDrawerIndicatorEnabled(false);
    //        drawerToggle.setHomeAsUpIndicator(R.drawable.iconfont_gerenshezhi);
    drawer_layout.setDrawerListener(drawerToggle);
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
    mTitleBar.setLeftImgMenu(R.drawable.list, new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (!drawer_layout.isDrawerOpen(drawer_view)) {
          drawer_layout.openDrawer(drawer_view);
        } else {
          drawer_layout.closeDrawer(drawer_view);
        }
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

    loadImgMainImg = new LoadImg(this);
    mTitleBar = (TitleBar) findViewById(R.id.title_bar);
    myUserName = (TextView) findViewById(R.id.myUserName);
    mleftHead = (ImageView) findViewById(R.id.iv_bottom);

    sp = getSharedPreferences("userInfo", 0);
    Log.e(sp.getString("avatar", "空空空"), "");

    myUserName.setText(sp.getString("username", "未登录"));
    if (!TextUtils.isEmpty(sp.getString("avatar", ""))) {
      BaseFunction.showImage(this, mleftHead, sp.getString("avatar", ""), loadImgMainImg,
          Url.IMGTYPE_HEAD);
    } else {
      mleftHead.setImageResource(R.drawable.side_user_avatar);
    }

    mLoginThisAPP = (LinearLayout) findViewById(R.id.LoginThisAPP);
    signature = (TextView) findViewById(R.id.signature);
    user = (TextView) findViewById(R.id.user);
    setting = (TextView) findViewById(R.id.setting);
    collect = (TextView) findViewById(R.id.collect);
    message1 = (TextView) findViewById(R.id.message1);
    invite = (TextView) findViewById(R.id.invite);
    mFeedbackMenu = (TextView) findViewById(R.id.feed_back);
    user.setOnClickListener(MainActivity.this);
    setting.setOnClickListener(MainActivity.this);
    message1.setOnClickListener(MainActivity.this);
    collect.setOnClickListener(MainActivity.this);
    invite.setOnClickListener(MainActivity.this);
    mFeedbackMenu.setOnClickListener(MainActivity.this);
    mLoginThisAPP.setOnClickListener(MainActivity.this);
    findViewById(R.id.ll1).setOnClickListener(this);
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

    //        home.setOnClickListener(MainActivity.this);
    //        weibo.setOnClickListener(MainActivity.this);
    //        yulequan.setOnClickListener(MainActivity.this);
    //        yuyisheng.setOnClickListener(MainActivity.this);
    //        mall.setOnClickListener(MainActivity.this);

    setTabSelection(0);
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
        if (!Url.SESSIONID.equals("")) {
          Intent intent = new Intent(MainActivity.this, UserInfoActivity.class);
          intent.putExtra("userUid", Url.USERID);
          startActivityForResult(intent, 0);
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
        if (!Url.SESSIONID.equals("")) {
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
        //InputStream is=getResources().openRawResource(R.raw.ic_launcher);

        intent_Share = new Intent(Intent.ACTION_SEND);
        //Uri imageUri =  Uri.parse("android.resource://package_name/raw/ic_luncher.png");
        //Uri imageUri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.ic_launcher);
        //intent_Share.putExtra(Intent.EXTRA_STREAM, imageUri);
        //intent_Share.setType("image/*");
        intent_Share.putExtra(Intent.EXTRA_SUBJECT, "分享");
        intent_Share.putExtra(Intent.EXTRA_TEXT, " http://a.app.qq.com/o/simple.jsp?pkgname=com" +
            ".hengrtech.yuzhile"
            + "（来自鱼知乐手机客户端）");//分享内容体
        intent_Share.setType("text/plain");


        //intent_Share.putExtra(Intent.ACTION_PACKAGE_ADDED,
        //    "http://a.app.qq.com/o/simple.jsp?pkgname=com.hengrtech.yuzhile");
        // 分享内容体
        startActivity(Intent.createChooser(intent_Share, "分享"));//分享选择页面标题
        break;
      case R.id.message1:
        if (BaseFunction.isLogin()) {
          Intent intent1 = new Intent(MainActivity.this, MyMessageActivity.class);
          startActivity(intent1);
        } else {
          ToastHelper.showToast("请登录", this);
        }
        break;
      case R.id.LoginThisAPP:
        if (!Url.SESSIONID.equals("")) {
          intent = new Intent(MainActivity.this, UserInfoActivity.class);
          intent.putExtra("userUid", Url.USERID);
          startActivityForResult(intent, 0);
        } else {
          String[] s = new String[ways.size()];
          s = ways.toArray(s);
          intent = new Intent(MainActivity.this, LoginActivity.class);
          intent.putExtra("ways", s);
          intent.putExtra("entryActivity", ActivityModel.USERINFO);
          startActivity(intent);
        }
        break;
      // TODO 临时代码
      case R.id.search:

        break;
      case R.id.feed_back:
        startActivity(new Intent(this, Setting_yijianActivity.class));
        break;
      default:
        break;
    }
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
            if (drawer_layout.isDrawerOpen(Gravity.LEFT)) {
              drawer_layout.closeDrawer(Gravity.LEFT);
            } else {
              drawer_layout.openDrawer(Gravity.LEFT);
            }
          }

          @Override
          public void onSearchBtnClicked() {

          }
        });
    // 如果NewsFragment为空，则创建一个并添加到界面上
    weiboFragment = new WeiboFragment();
    mFragmentTransaction.add(R.id.content, weiboFragment);
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
                  if (drawer_layout.isDrawerOpen(Gravity.LEFT)) {
                    drawer_layout.closeDrawer(Gravity.LEFT);
                  } else {
                    drawer_layout.openDrawer(Gravity.LEFT);
                  }
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
        if (weiboFragment == null) {
          // 如果NewsFragment为空，则创建一个并添加到界面上
          weiboFragment = new WeiboFragment();
          mFragmentTransaction.add(R.id.content, weiboFragment);
        } else {
          // 如果NewsFragment不为空，则直接将它显示出来
          mFragmentTransaction.show(weiboFragment);
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
    mFragmentTransaction.commit();
    performTitleBarChange(index);
  }

  private void performTitleBarChange(int tabIndex) {
    switch (tabIndex) {
      case 1:
        mTitleBar.setVisibility(View.VISIBLE);
        mTitleBar.setRightTextBtn(R.string.title_bar_right_btn_write_feed, mWriteFeedListener);
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

  //    /**
  //     * 清除掉所有的选中状态。
  //     */
  //    private void clearSelection() {
  //        messageImage.setImageResource(R.drawable.message_unselected);
  //        messageText.setTextColor(Color.parseColor("#82858b"));
  //        contactsImage.setImageResource(R.drawable.contacts_unselected);
  //        contactsText.setTextColor(Color.parseColor("#82858b"));
  //        newsImage.setImageResource(R.drawable.news_unselected);
  //        newsText.setTextColor(Color.parseColor("#82858b"));
  //        settingImage.setImageResource(R.drawable.setting_unselected);
  //        settingText.setTextColor(Color.parseColor("#82858b"));
  //    }

  /**
   * 将所有的Fragment都 为 藏状态。
   *
   * @param mFragmentTransaction 用于对Fragment执行操作的事务
   */
  private void hideFragments(FragmentTransaction mFragmentTransaction) {
    if (homeFragment != null) {
      mFragmentTransaction.hide(homeFragment);
    }
    if (weiboFragment != null) {
      mFragmentTransaction.hide(weiboFragment);
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

  protected void setLeftMenu() {
    //判断是否登入
    if (BaseFunction.isLogin()) {
      signature.setVisibility(View.VISIBLE);
      if (!sp.getString("avatar", "").equalsIgnoreCase("")) {
        BaseFunction.showImage(this, mleftHead, sp.getString("avatar", ""), loadImgMainImg,
            Url.IMGTYPE_HEAD);
      }
      if (!("").equalsIgnoreCase(sp.getString("nickname", ""))) {
        myUserName.setText(sp.getString("nickname", ""));
      } else {
        myUserName.setText(sp.getString("username", ""));
      }
      if (backName != null) {
        myUserName.setText(backName);
      }
      if (backAvater != null) {
        mleftHead.setImageBitmap(BitmapFactory.decodeFile(backAvater));
      }
      //            if (!("").equals(Url.MYUSERINFO)) {
      //                signature.setText(Url.MYUSERINFO.getSignature());
      //            }else{
      //                signature.setText("这个人很懒，什么都没写");
      //            }
      if (!("").equalsIgnoreCase(sp.getString("getSignature", ""))) {

        signature.setText(sp.getString("nickname", ""));
      } else {

        signature.setText("这个人很懒，什么都没写");
      }
    } else {
      mleftHead.setImageResource(R.drawable.side_user_avatar);
      myUserName.setText("未登录");
    }
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == 0 && resultCode == 1) {
      Log.d("回来了吗？", "123");
      backName = data.getExtras().getString("NickName");
      backAvater = data.getExtras().getString("Avater");
    }
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
}

