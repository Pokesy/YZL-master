package com.thinksky.tox;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.squareup.otto.Subscribe;
import com.thinksky.fragment.MyScrollview;
import com.thinksky.holder.BaseApplication;
import com.thinksky.holder.BaseBActivity;
import com.thinksky.injection.GlobalModule;
import com.thinksky.myview.MoreTextView;
import com.thinksky.net.UiRpcSubscriber1;
import com.thinksky.net.UiRpcSubscriberSimple;
import com.thinksky.net.rpc.model.BaseModel;
import com.thinksky.net.rpc.model.GroupDetailModel;
import com.thinksky.net.rpc.model.HotPostModel;
import com.thinksky.net.rpc.service.AppService;
import com.thinksky.net.rpc.service.NetConstant;
import com.thinksky.rsen.RBaseAdapter;
import com.thinksky.rsen.RViewHolder;
import com.thinksky.rsen.RsenUrlUtil;
import com.thinksky.serviceinjection.DaggerServiceComponent;
import com.thinksky.serviceinjection.ServiceModule;
import com.thinksky.ui.common.TitleBar;
import com.thinksky.ui.group.CheckMemberListActivity;
import com.thinksky.ui.group.CreateGroupActivity;
import com.thinksky.ui.group.GroupMemberListActivity;
import com.thinksky.utils.imageloader.ImageLoader;
import com.tox.BaseApi;
import com.tox.BaseFunction;
import com.tox.GroupApi;
import com.tox.ToastHelper;
import com.tox.Url;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.inject.Inject;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2015/5/18 0018.
 */
public class GroupInfoActivity extends BaseBActivity implements View.OnClickListener {
  private static final String BUNDLE_KEY_GROUP_ID = "group_id";
  private List<String> imgs;
  protected MyScrollview group_scro;
  protected RelativeLayout refreshButn, enter_cy;
  protected ArrayList<HashMap<String, String>> categoryList;
  private RemenhuatiAdapter rm_adapter;
  Context mContext;
  Bundle GroupBundle;
  String session_id;
  private String userUid;
  Boolean isWeGroup;
  Intent postIntent;
  Bundle postBundle;
  RecyclerView group_post_listView;
  PopupWindow cateWindow;
  private GroupApi groupApi;
  private String group_id;
  private Context ctx;
  private LinearLayout linear_list;
  private LinearLayout linear_body;
  private LinearLayout linear_isnull;
  private RelativeLayout body_probar;
  private ImageView group_logo;
  private TextView group_name;
  private TextView join_status;
  private LinearLayout join_group;
  private MoreTextView group_detail;
  private ImageView refreshImage;
  private ArrayList<String> userlist;
  private HashMap<String, String> titleMap;
  private ArrayList<HashMap<String, String>> postInfoList;
  private long lastClick;
  private RecyclerView memberRecycler;
  private GroupApi mUpdateGroupApi;

  private TitleBar mTitleBar;
  private TextView mMemberCountView;
  private TextView mCreatorView;
  private TextView mCreateTimeView;

  private ExpandableTextView mNoticeView;
  private View mMenuNotice;
  private View mNoticeContainer;

  private boolean mIsCreator;
  private GroupDetailModel mGroupModel;
  private View mBtnSendPost;

  @Inject
  AppService mAppService;

  private boolean mIsPublicGroup;
  private JoinStatus mJoinStatus = JoinStatus.UN_JOIN;

  //对加入群组的状态进行实时判断
  private Handler tempHandler = new Handler() {
    @Override
    public void handleMessage(Message msg) {
      if (msg.what == 0) {

      }
    }
  };
  private Handler myHandler = new Handler() {

    private ArrayList<HashMap<String, String>> categoryList;
    private ArrayList<HashMap<String, String>> topPostInfoList;

    @Override
    @SuppressWarnings(value = {"unchecked"})
    public void handleMessage(Message msg) {
      switch (msg.what) {
        case 0x120:
          categoryList = (ArrayList<HashMap<String, String>>) msg.obj;
          createCatePopWindow(categoryList);
          break;
        default:
          break;
      }
    }
  };

  public static Intent makeIntent(Context context, String groupId) {
    Intent intent = new Intent(context, GroupInfoActivity.class);
    intent.putExtra(BUNDLE_KEY_GROUP_ID, groupId);
    return intent;
  }

  @Override
  @SuppressWarnings(value = {"unchecked"})
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_group_info);
    inject();
    ctx = this;
    mContext = GroupInfoActivity.this;
    session_id = new BaseApi().getSeesionId();
    groupApi = new GroupApi();
    mUpdateGroupApi = new GroupApi();
    userUid = new BaseApi().getUid();
    //获得上个activity传递的群组信息
    GroupBundle = getIntent().getExtras();
    isWeGroup = GroupBundle.getBoolean("isWeGroup");
    memberRecycler = (RecyclerView) findViewById(R.id.memberRecycler);
    group_id = getIntent().getStringExtra(BUNDLE_KEY_GROUP_ID);

    //即时获取群组信息线程
    groupApi.setHandler(tempHandler);
    mUpdateGroupApi.setHandler(tempHandler);
    getGroupInfo();

    initTitleBar();

    mBtnSendPost = findViewById(R.id.btn_send_post);
    group_scro = (MyScrollview) findViewById(R.id.group_scro);
    linear_list = (LinearLayout) findViewById(R.id.linear_list);
    linear_body = (LinearLayout) findViewById(R.id.linear_body);
    linear_isnull = (LinearLayout) findViewById(R.id.linear_isnull);
    body_probar = (RelativeLayout) findViewById(R.id.body_probar);
    group_logo = (ImageView) findViewById(R.id.group_logo);
    group_name = (TextView) findViewById(R.id.group_name);
    //        group_type_name = (TextView) findViewById(R.id.group_type_name);
    join_status = (TextView) findViewById(R.id.join_status);
    mMenuNotice = findViewById(R.id.menu_notice);
    mNoticeContainer = findViewById(R.id.notice_container);

    join_group = (LinearLayout) findViewById(R.id.join_group);
    group_post_listView = (RecyclerView) findViewById(R.id.group_post_listView);
    group_detail = (MoreTextView) findViewById(R.id.group_detail);
    refreshButn = (RelativeLayout) findViewById(R.id.refresh_butn);
    refreshImage = (ImageView) findViewById(R.id.refresh_image);
    mMemberCountView = (TextView) findViewById(R.id.member_count);
    mCreatorView = (TextView) findViewById(R.id.creator);
    mCreateTimeView = (TextView) findViewById(R.id.create_time);

    mNoticeView = (ExpandableTextView) findViewById(R.id.notice_content);

    enter_cy = (RelativeLayout) findViewById(R.id.enter_cy);
    group_logo.setOnClickListener(this);
    linear_isnull.setOnClickListener(this);
    join_group.setOnClickListener(this);
    refreshButn.setOnClickListener(this);
    enter_cy.setOnClickListener(this);
    mMenuNotice.setOnClickListener(this);
    postInfoList = new ArrayList<HashMap<String, String>>();
    final View containerView = findViewById(R.id.group_info_container);

    group_scro.setOnScrollListener(new MyScrollview.OnScrollListener() {
      @Override
      public void onScroll(int scrollY) {
        float alpha = (float) scrollY / (float) containerView.getHeight();
        mTitleBar.getTitleBgView().setAlpha(alpha >= 1.0f ? 1.0f : (alpha));
      }
    });

    mBtnSendPost.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (BaseFunction.isLogin()) {
          sendPost();
        } else {
          ToastHelper.showToast("请登陆后操作", mContext);
        }
      }
    });
  }

  private void inject() {
    DaggerServiceComponent.builder().globalModule(new GlobalModule(BaseApplication.getApplication
        ())).serviceModule(new ServiceModule())
        .build().inject(this);
  }

  private void getGroupInfo() {
    manageRpcCall(mAppService.getGroupDetail(String.valueOf(group_id), Url.SESSIONID), new
        UiRpcSubscriber1<GroupDetailModel>(this) {


          @Override
          protected void onSuccess(GroupDetailModel groupDetailModel) {
            mGroupModel = groupDetailModel;
            mIsCreator = TextUtils.equals(Url.USERID, mGroupModel.getList().getUid());
            initGroupView(groupDetailModel);
            memberRecycler.setAdapter(
                new MySubAdapter(GroupInfoActivity.this, groupDetailModel.getList()
                    .getGroupMenmber()));
            join_group.setVisibility(View.VISIBLE);
            mIsPublicGroup = !TextUtils.equals(groupDetailModel.getList().getType(),
                GroupDetailModel.GROUP_TYPE_PRIVATE);
            setJoinStatus();
          }


          @Override
          protected void onEnd() {

          }
        });
  }

  private void setJoinStatus() {
    switch (mGroupModel.getList().getIs_join()) {
      case GroupDetailModel.GROUP_JOIN_STATUS_JOINED:
        mJoinStatus = JoinStatus.JOIN;
        break;
      case GroupDetailModel.GROUP_JOIN_STATUS_REVIEW:
        mJoinStatus = JoinStatus.REVIEW;
        break;
      default:
        mJoinStatus = JoinStatus.UN_JOIN;
        break;
    }
    if (TextUtils.equals(mGroupModel.getList().getUid(), Url.USERID)) {
      join_status.setText(R.string.activity_group_info_btn_manage_group);
      return;
    }
    switch (mJoinStatus) {
      case JOIN:
        join_status.setText(R.string.activity_group_info_btn_quit_group);
        join_group.setClickable(true);
        break;
      case UN_JOIN:
        join_status.setText(R.string.activity_group_info_btn_join_group);
        join_group.setClickable(true);
        break;
      case REVIEW:
        join_status.setText(R.string.activity_group_info_btn_review);
        join_group.setClickable(false);
        break;
    }

    if (!BaseFunction.isLogin()) {
      join_group.setVisibility(View.GONE);
    }
  }

  @Subscribe
  public void handleGroupMemberDataChangeEvent(GroupMemberListActivity.GroupMemberDataChangeEvent
                                                   event) {
    getGroupInfo();
  }

  private void initTitleBar() {
    mTitleBar = (TitleBar) findViewById(R.id.title_bar);
    mTitleBar.setMiddleTitle(R.string.activity_group_info_title);
    mTitleBar.setLeftImgMenu(R.drawable.icon_title_bar_back, new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });
    mTitleBar.setSearchBtn(R.drawable.icon_title_bar_share, new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        showShare();
      }
    });
    mTitleBar.getTitleBgView().setAlpha(0);
  }

  private void showShare() {
    if (null == mGroupModel) {
      return;
    }
    ShareSDK.initSDK(GroupInfoActivity.this);
    OnekeyShare oks = new OnekeyShare();
    //关闭sso授权
    oks.disableSSOWhenAuthorize();

    // 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
    //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
    // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
    oks.setTitle(getString(R.string.app_name));
    // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
    oks.setTitleUrl("http://www.yuzhile.com/index.php?s=/mob/group/group_share/id/ " + group_id +
        ".html");
    // text是分享文本，所有平台都需要这个字段
    oks.setText("群组:" + mGroupModel.getList().getTitle());
    //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
    String imageUrl = TextUtils.isEmpty(mGroupModel.getList().getLogo()) ? "http://www.yuzhile" +
        ".com/apk/ic_launcher.png" : NetConstant.BASE_URL + mGroupModel.getList().getLogo();
    oks.setImageUrl(imageUrl);
    // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
    //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
    // url仅在微信（包括好友和朋友圈）中使用
    oks.setUrl("http://www.yuzhile.com/index.php?s=/mob/group/group_share/id/ " + group_id + "" +
        ".html");
    // comment是我对这条分享的评论，仅在人人网和QQ空间使用
    oks.setComment("群组:" + mGroupModel.getList().getTitle());
    // site是分享此内容的网站名称，仅在QQ空间使用
    oks.setSite(getString(R.string.app_name));
    // siteUrl是分享此内容的网站地址，仅在QQ空间使用
    oks.setSiteUrl("http://www.yuzhile.com/index.php?s=/mob/group/group_share/id/ " + group_id +
        ".html");

    // 启动分享GUI
    oks.show(this);

  }

  /*成员头像*/
  public class MySubAdapter extends RBaseAdapter<GroupDetailModel.ListBean.GroupMenmberBean> {
    private static final int MAX_COUNT = 8;

    public MySubAdapter(Context context, List<GroupDetailModel.ListBean.GroupMenmberBean> datas) {
      super(context, datas);
    }

    @Override
    protected int getItemLayoutId(int viewType) {
      return R.layout.fragment_xiaozujingxuan_adapter_sub_item;
    }

    @Override
    protected void onBindView(RViewHolder holder, int position, GroupDetailModel.ListBean
        .GroupMenmberBean bean) {
      ImageLoader.loadOptimizedHttpImage(GroupInfoActivity.this, NetConstant.BASE_URL + bean
          .getUser().getAvatar64())
          .bitmapTransform(new
              CropCircleTransformation(GroupInfoActivity.this))
          .error(R.drawable.side_user_avatar).placeholder(R.drawable.side_user_avatar).into
          (holder.imgV(R.id.logo));
      holder.tV(R.id.creator_flag).setVisibility(TextUtils.equals(bean.getIsCreator(), "1") ?
          View.VISIBLE : View.GONE);
    }

    @Override
    public int getItemCount() {
      return super.getItemCount() >= MAX_COUNT ? MAX_COUNT : super.getItemCount();
    }
  }

  private void init() {
    rm_adapter = new RemenhuatiAdapter(mContext);

    group_post_listView.setLayoutManager(new LinearLayoutManager(this));
    group_post_listView.setAdapter(rm_adapter);
    initTieziList();
  }

  @Subscribe
  public void handleGroupPostDataChangeEvent(SendTieziActivity.GroupPostInfoChangeEvent event) {
    initTieziList();
  }

  @Subscribe
  public void handleGroupPostDataDeleteEvent(GroupPostInfoActivity.PostDataChangeEvent event) {
    initTieziList();
  }

  private void initTieziList() {
    manageRpcCall(mAppService.getGroupAllPost(String.valueOf(group_id)), new
        UiRpcSubscriberSimple<HotPostModel>(this) {
          @Override
          protected void onSuccess(HotPostModel hotPostModel) {
            if (null != hotPostModel.getList() && hotPostModel.getList().size() != 0) {
              linear_list.setVisibility(View.VISIBLE);
              linear_isnull.setVisibility(View.GONE);
              rm_adapter.resetData(hotPostModel.getList());
            } else {
              linear_isnull.setVisibility(View.VISIBLE);
              linear_list.setVisibility(View.GONE);
              rm_adapter.resetData(new ArrayList<HotPostModel.HotPostBean>());
            }

            linear_body.setVisibility(View.VISIBLE);
            body_probar.setVisibility(View.GONE);
          }

          @Override
          protected void onEnd() {

          }
        });

  }

  @Subscribe
  public void handlePostDataChangeEvent(GroupPostInfoActivity.PostDataChangeEvent event) {
    initTieziList();
  }

  //初始化群组固有信息
  private void initGroupView(GroupDetailModel groupDetailModel) {
    if (null == groupDetailModel) {
      return;
    }
    group_name.setText(groupDetailModel.getList().getTitle());
    group_detail.setText("群组简介：" + groupDetailModel.getList().getDetail());
    group_detail.setVisibility(View.GONE);
    mCreatorView.setText(getString(R.string.activity_group_info_text_creator, groupDetailModel
        .getList
            ().getUser().getNickname()));
    //        group_type_name.setText(groupInfoMap.get("type_name"));
    mCreateTimeView.setText(getString(R.string.activity_group_info_text_create_time, TextUtils
        .isEmpty(groupDetailModel.getList().getCreate_time()) ? "" : groupDetailModel.getList()
        .getCreate_time()));
    mMemberCountView.setText(getString(R.string.activity_group_info_member_count,
        groupDetailModel.getList().getMember_count()));

    try {
      ImageLoader.loadOptimizedHttpImage(this, NetConstant.BASE_URL + groupDetailModel.getList()
          .getLogo()).bitmapTransform(new
          CropCircleTransformation(this))
          .error(R.drawable.picture_1_no).placeholder(R.drawable.picture_1_no).dontAnimate().into
          (group_logo);
    } catch (Exception e) {
      e.printStackTrace();
    }

    String notice = groupDetailModel.getList().getNotice();
    mNoticeView.setText(notice);
    mNoticeContainer.setVisibility(TextUtils.isEmpty(notice) ? View.GONE : View.VISIBLE);
    init();
  }

  @Subscribe
  public void handleGroupInfoChangeEvent(CreateGroupActivity.CreateGroupSuccessEvent event) {
    getGroupInfo();
  }

  @Subscribe
  public void handleCheckMemberDataChangeEvent(CheckMemberListActivity.CheckMemberDataChangeEvent
                                                   event) {
    getGroupInfo();
  }

  @Subscribe
  public void handleDissolutionGroupEvent(CreateGroupActivity.DissolutionGroupEvent event) {
    finish();
  }

  @Override
  public void onClick(View v) {
    int viewID = v.getId();
    switch (viewID) {
      case R.id.back_menu:
        finish();
        break;
      case R.id.cate_menu:
        cateWindow.showAsDropDown(mTitleBar.getTextBtnRight());
        break;
      case R.id.linear_isnull:
        sendPost();
        break;
      case R.id.enter_cy:
        startActivity(GroupMemberListActivity.makeIntent(mContext, String.valueOf(group_id),
            mIsCreator));
        break;
      case R.id.join_group:
        if (!TextUtils.isEmpty(groupApi.getSeesionId())) {
          if (TextUtils.equals(mGroupModel.getList().getUid(), userUid)) {
            startActivity(CreateGroupActivity.makeIntent(GroupInfoActivity.this, String.valueOf
                (group_id)));
            break;
          }
          switch (mGroupModel.getList().getIs_join()) {
            case GroupDetailModel.GROUP_JOIN_STATUS_JOINED:
              new AlertDialog.Builder(mContext).setTitle("退出群组")
                  .setMessage("确定吗？")
                  .setPositiveButton(R.string.btn_confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                      quitGroup();
                      dialog.cancel();
                    }
                  })
                  .setNegativeButton(R.string.btn_cancel, null)
                  .show();
              break;
            case GroupDetailModel.GROUP_JOIN_STATUS_REVIEW:
              break;
            default:
              joinGroup();
          }
        } else {
          ToastHelper.showToast("请登陆后操作", mContext);
          join_group.setClickable(false);
        }
        break;
      case R.id.refresh_butn:
        //大于2秒可以通过
        if (System.currentTimeMillis() - lastClick <= 2000) {
          Toast.makeText(GroupInfoActivity.this, "请勿重复刷新", Toast.LENGTH_SHORT).show();
          return;
        }
        lastClick = System.currentTimeMillis();
        postInfoList.clear();
        AnimationSet animationSet = new AnimationSet(true);
        RotateAnimation rotateAnimation =
            new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5F,
                Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(2000);
        animationSet.addAnimation(rotateAnimation);
        refreshImage.startAnimation(animationSet);
        break;
      case R.id.menu_notice:
        break;
      default:
        break;
    }
  }

  private void joinGroup() {
    showProgressDialog("", true);
    manageRpcCall(mAppService.joinGroup(Url.SESSIONID, group_id, mGroupModel.getList().getType())
        , new UiRpcSubscriber1<BaseModel>(this) {


          @Override
          protected void onSuccess(BaseModel baseModel) {
            if (mIsPublicGroup) {
              Toast.makeText(GroupInfoActivity.this, R.string.activity_group_info_join_success,
                  Toast.LENGTH_SHORT).show();
              mGroupModel.getList().setIs_join(GroupDetailModel.GROUP_JOIN_STATUS_JOINED);
            } else {
              Toast.makeText(GroupInfoActivity.this, R.string.activity_group_info_join_review,
                  Toast.LENGTH_SHORT).show();
              mGroupModel.getList().setIs_join(GroupDetailModel.GROUP_JOIN_STATUS_REVIEW);
            }
            getComponent().getGlobalBus().post(new GroupMemberListActivity
                .GroupMemberDataChangeEvent());
            //setJoinStatus();
            getGroupInfo();
          }

          @Override
          protected void onEnd() {
            closeProgressDialog();
          }
        });
  }

  private void quitGroup() {
    showProgressDialog("", true);
    Toast.makeText(mContext, R.string.activity_group_info_quit_success, Toast.LENGTH_SHORT).show();
    manageRpcCall(mAppService.quitGroup(Url.SESSIONID, group_id), new UiRpcSubscriber1<BaseModel>
        (this) {


      @Override
      protected void onSuccess(BaseModel baseModel) {
        mGroupModel.getList().setIs_join(GroupDetailModel.GROUP_JOIN_STATUS_UN_JOIN);
        //setJoinStatus();
        getGroupInfo();
        getComponent().getGlobalBus().post(new GroupMemberListActivity
            .GroupMemberDataChangeEvent());
      }

      @Override
      protected void onEnd() {
        closeProgressDialog();
      }
    });
  }

  //判断发帖权限
  public void sendPost() {
    if (mJoinStatus.ordinal() == JoinStatus.JOIN.ordinal()) {
      //            post.putExtra("categoryList", categoryList);
      Intent intent = new Intent(this, SendTieziActivity.class);
      intent.putExtra("group_id", group_id);
      startActivity(intent);
    } else {
      ToastHelper.showToast("加群才能发帖", mContext);
    }
  }

  //封装的帖子信息发送方法
  public void postInfoTo(HashMap<String, String> mapTwo) {

    postIntent = new Intent(mContext, GroupPostInfoActivity.class);
    postBundle = new Bundle();
    postBundle.putSerializable("post_info", mapTwo);
    //        postBundle.putStringArrayList("imgList", (ArrayList<String>) imgs);
    postIntent.putExtras(postBundle);
    startActivity(postIntent);
  }

  //封装的帖子信息
  public HashMap<String, String> getPostMap(JSONObject jsonObj) {

    HashMap<String, String> map1 = new HashMap<String, String>();
    try {
      map1.put("id", jsonObj.getString("id"));
      map1.put("uid", jsonObj.getString("uid"));
      map1.put("group_id", jsonObj.getString("group_id"));
      map1.put("group_name", mGroupModel.getList().getTitle());
      map1.put("title", jsonObj.getString("title"));
      map1.put("content", jsonObj.getString("content"));
      map1.put("create_time", jsonObj.getString("create_time"));
      map1.put("update_time", jsonObj.getString("update_time"));
      map1.put("last_reply_time", jsonObj.getString("last_reply_time"));
      map1.put("status", jsonObj.getString("status"));
      map1.put("view_count", jsonObj.getString("view_count"));
      map1.put("reply_count", jsonObj.getString("reply_count"));
      map1.put("is_top", jsonObj.getString("is_top"));

      map1.put("cate_id", jsonObj.getString("cate_id"));
      map1.put("supportCount", jsonObj.getString("supportCount"));
      map1.put("is_support", jsonObj.getString("is_support"));
      JSONObject tempJSONObj = jsonObj.getJSONObject("user");
      map1.put("user_uid", tempJSONObj.getString("uid"));
      map1.put("user_nickname", tempJSONObj.getString("nickname"));
      map1.put("signature", tempJSONObj.getString("signature"));
      map1.put("user_logo", Url.IMAGE + tempJSONObj.getString("avatar128"));
      JSONArray imgList = jsonObj.getJSONArray("imgList");
      imgs = new ArrayList<String>();
      for (int i = 0; imgList != null && i < imgList.length(); i++) {
        imgs.add(imgList.getString(i));
      }
    } catch (JSONException e) {
      e.printStackTrace();
    }
    return map1;
  }

  //创建帖子分类PopWindow
  private void createCatePopWindow(ArrayList<HashMap<String, String>> cateList) {
    View view = LayoutInflater.from(mContext).inflate(R.layout.group_postcate_popview, null);
    final LinearLayout post_cateLinear = (LinearLayout) view.findViewById(R.id.post_cate);
    // 下面是两种方法得到宽度和高度 getWindow().getDecorView().getWidth()
    cateWindow = new PopupWindow(view, WindowManager.LayoutParams.WRAP_CONTENT,
        WindowManager.LayoutParams.WRAP_CONTENT);
    // 设置popWindow弹出窗体可以通过点击屏幕其他地方自动消失
    cateWindow.setFocusable(true);
    // 实例化一个ColorDrawable颜色为半透明
    ColorDrawable dw = new ColorDrawable(0xb0000000);
    cateWindow.setBackgroundDrawable(dw);
    // 设置popWindow的显示和消失动画
    cateWindow.setAnimationStyle(R.style.CatePopWindow);
    //导航标题块
    LinearLayout.LayoutParams tempTextPar =
        new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT);
    tempTextPar.setMargins(10, 10, 0, 10);
    for (int i = 0; i < cateList.size(); i++) {
      if (Integer.parseInt(cateList.get(i).get("status")) == 1) {
        TextView cateLabel = new TextView(mContext);
        cateLabel.setPadding(10, 5, 15, 5);
        cateLabel.setLayoutParams(tempTextPar);
        cateLabel.setText(cateList.get(i).get("title"));
        cateLabel.setId(Integer.parseInt(cateList.get(i).get("id")));
        if (i == 0) {
          cateLabel.setBackgroundColor(Color.parseColor("#EDEDED"));
        }
        cateLabel.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(final View v) {

            v.setClickable(false);
            postInfoList.clear();
            setBackColor(post_cateLinear);
            v.setBackgroundColor(Color.parseColor("#EDEDED"));
            //防暴力点击
            new Handler().postDelayed(new Runnable() {
              @Override
              public void run() {
                v.setClickable(true);
              }
            }, 1500);
          }
        });
        post_cateLinear.addView(cateLabel);
      }
    }
  }

  //清空导航标签背景色
  @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
  private void setBackColor(LinearLayout layout) {
    for (int i = 0; i < layout.getChildCount(); i++) {
      layout.getChildAt(i).setBackground(null);
    }
  }

  /*数据适配器*/
  public class RemenhuatiAdapter extends RBaseAdapter<HotPostModel.HotPostBean> {
    Context context;

    public RemenhuatiAdapter(Context context) {
      super(context);
    }

    @Override
    protected int getItemLayoutId(int viewType) {
      return R.layout.fragment_remen_ylq_adapter_item;
    }

    @Override
    protected void onBindView(RViewHolder holder, int position, final HotPostModel.HotPostBean
        bean) {
      holder.tV(R.id.title).setText(bean.getTitle());
      holder.tV(R.id.content).setText(bean.getContent());
      holder.tV(R.id.supportCount).setText(bean.getSupportCount());
      holder.tV(R.id.reply_count).setText(bean.getReply_count());
      holder.tV(R.id.nickname).setText(bean.getUser().getNickname());
      ImageLoader.loadOptimizedHttpImage(GroupInfoActivity.this, bean.getUser().getAvatar64())
          .
              bitmapTransform(new CropCircleTransformation(GroupInfoActivity.this))
          .into(holder.imgV(R.id.user_logo));
      if (bean.getImgList() != null && bean.getImgList().size() > 0) {
        holder.v(R.id.img_layout).setVisibility(View.VISIBLE);

        int size = bean.getImgList().size();
        holder.v(R.id.iv_1).setVisibility(size > 0 ? View.VISIBLE : View.GONE);
        holder.v(R.id.iv_2).setVisibility(size > 1 ? View.VISIBLE : View.GONE);
        holder.v(R.id.iv_3).setVisibility(size > 2 ? View.VISIBLE : View.GONE);

        for (int i = 0; i < bean.getImgList().size(); i++) {
          String url = RsenUrlUtil.URL_BASE + bean.getImgList().get(i);
          ImageView imageView = null;
          if (i == 0) {
            imageView = holder.imgV(R.id.iv_1);
          } else if (i == 1) {
            imageView = holder.imgV(R.id.iv_2);
          } else if (i == 2) {
            imageView = holder.imgV(R.id.iv_3);
          }

          if (imageView != null) {

            //ImageLoader.getInstance().displayImage(url, imageView);
            ImageLoader.loadOptimizedHttpImage(GroupInfoActivity.this, url).into(imageView);
            final int in = i;
            imageView.setOnClickListener(new View.OnClickListener() {

              @Override
              public void onClick(View v) {
                Intent intent = new Intent(GroupInfoActivity.this, ImagePagerActivity.class);
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("image_urls", (ArrayList<String>) bean.getImgList());
                bundle.putInt("image_index", in);
                intent.putExtras(bundle);
                startActivity(intent);
              }
            });
          }
        }
      } else {
        holder.v(R.id.img_layout).setVisibility(View.GONE);
      }

      holder.v(R.id.root_layout).setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          //                    Bundle bundle = new Bundle();
          if (null != bean) {
            launch(mContext, isWeGroup, bean);
          }
        }
      });
    }

    @Override
    public int getItemCount() {
      return mAllDatas.size() > 50 ? 50 : mAllDatas.size();
    }
  }

  public static void launch(Context context, boolean isWeGroup, HotPostModel.HotPostBean bean) {
    Bundle bundle = new Bundle();

    bundle.putString(GroupPostInfoActivity.BUNDLE_KEY_POST_ID, bean.getId());
    bundle.putBoolean(GroupPostInfoActivity.BUNDLE_KEY_IS_WE_GROUP, isWeGroup);
    Intent intent = new Intent(context, GroupPostInfoActivity.class);
    intent.putExtras(bundle);
    context.startActivity(intent);
  }

  private enum JoinStatus {
    JOIN, UN_JOIN, REVIEW;
  }
}
