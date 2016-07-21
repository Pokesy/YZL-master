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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import com.thinksky.fragment.UserListActivity;
import com.thinksky.holder.BaseBActivity;
import com.thinksky.myview.MoreTextView;
import com.thinksky.redefine.CircleImageView;
import com.thinksky.rsen.RBaseAdapter;
import com.thinksky.rsen.RViewHolder;
import com.thinksky.rsen.RsenUrlUtil;
import com.thinksky.utils.imageloader.ImageLoader;
import com.tox.BaseApi;
import com.tox.BaseFunction;
import com.tox.GroupApi;
import com.tox.ToastHelper;
import com.tox.Url;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.kymjs.aframe.bitmap.KJBitmap;

/**
 * Created by Administrator on 2015/5/18 0018.
 */
public class GroupInfoActivity extends BaseBActivity implements View.OnClickListener {
  private List<String> imgs;
  private static boolean PUBLICGROUP = false;
  private static boolean PRIVATEGROUP = false;
  private static boolean DISMISSGROUP = false;
  protected ScrollView group_scro;
  protected ImageView back_menu;
  protected ImageView cate_menu;
  private CircleImageView user_logo;
  protected RelativeLayout refreshButn, enter_cy;
  protected ArrayList<HashMap<String, String>> categoryList;
  private RemenhuatiAdapter rm_adapter;
  Context mContext;
  HashMap<String, String> groupInfoMap;
  Bundle GroupBundle;
  String session_id;
  private String userUid;
  Boolean isWeGroup;
  Intent postIntent;
  Bundle postBundle;
  RecyclerView group_post_listView;
  PopupWindow cateWindow;
  private GroupApi groupApi;
  private int group_id;
  private int cateID = 0;
  private Context ctx;
  private LinearLayout linear_list;
  private LinearLayout linear_body;
  private LinearLayout linear_isnull;
  private RelativeLayout body_probar;
  private ImageView group_post;
  private ImageView group_logo;
  private TextView group_name;
  private TextView group_type;
  private TextView group_type_name;
  private TextView post_count;
  private TextView man_count;
  private TextView join_status;
  private LinearLayout join_group;
  private LinearLayout post_at_top;
  private MoreTextView group_detail;
  private ImageView refreshImage;
  private ArrayList<String> userlist;
  private HashMap<String, String> titleMap;
  private ArrayList<HashMap<String, String>> postInfoList;
  private boolean count = true;
  private int maxNumber = 0;
  private int page = 1;
  private int index = 0;
  private Intent post;
  private boolean joinFlag = false;
  private int isJoin;
  private long lastClick;
  private RecyclerView memberRecycler;
  private GroupApi mUpdateGroupApi;
  //对加入群组的状态进行实时判断
  private Handler tempHandler = new Handler() {
    @Override
    public void handleMessage(Message msg) {
      if (msg.what == 0) {
        String result = (String) msg.obj;
        groupInfoMap = groupApi.getGroupInfoMap(result, groupInfoMap);
        if (BaseFunction.isLogin()) {
          join_group.setVisibility(View.VISIBLE);
          isJoin = Integer.parseInt(groupInfoMap.get("is_join"));
          if (isJoin == 1) {
            joinFlag = false;
            join_status.setText("退出群组");
          } else if (isJoin == -1) {
            join_status.setText("已申请，审核中");
            join_group.setClickable(false);
          } else if (isJoin != 1) {
            if (groupInfoMap.get("uid").equals(userUid)) {
              join_status.setText("管理小组");
            } else {
              join_status.setText("加入群组");
              joinFlag = true;
            }
          }
          initGroupView(groupInfoMap);
        } else {
          join_group.setVisibility(View.GONE);
        }
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
        case 0:
          if (DISMISSGROUP) {
            Intent intent = new Intent();
            intent.putExtra("isWeGroup", isWeGroup);
            setResult(1, intent);
            GroupInfoActivity.this.finish();
            break;
          }
          if (joinFlag) {
            joinFlag = false;
            ToastHelper.showToast("退出群组成功", ctx);
            join_status.setText("加入群组");
            mUpdateGroupApi.getGroupInfo(String.valueOf(group_id));
          } else {
            if (PUBLICGROUP) {
              joinFlag = false;
              ToastHelper.showToast("加入群组成功", ctx);
              join_status.setText("退出群组");
              mUpdateGroupApi.getGroupInfo(String.valueOf(group_id));
            }
            if (PRIVATEGROUP) {
              join_group.setClickable(false);
              ToastHelper.showToast("申请加入群组成功", ctx);
              join_status.setText("正在审核");
            }
          }
          break;
        case 800:
          if (PUBLICGROUP) {
            if (joinFlag) {
              join_status.setText("加入群组");
              ToastHelper.showToast("退群失败，还未加入该群", ctx);
            } else {
              join_status.setText("退出群组");
              ToastHelper.showToast("你已加入该群", ctx);
            }
          }
          if (PRIVATEGROUP) {
            join_status.setText("已申请，审核中");
            join_group.setClickable(false);
          }
          break;
        case 0x120:
          categoryList = (ArrayList<HashMap<String, String>>) msg.obj;
          //                    Log.e("categoryList>>>>>>>>",categoryList.toString());
          createCatePopWindow(categoryList);
          break;
        case 0x122:

          break;
        //置顶区帖子
        case 0x124:
          break;
        default:
          break;
      }
    }
  };

  /*数据bean*/
  public static class RemenhuatiBean {
    public String title;
    public String content;
    public String supportCount;
    public String is_support;
    public String nickname;
    public List<String> imgList;
    public String id;
    public String uid;
    public String group_id;
    public String create_time;
    public String update_time;
    public String last_reply_time;
    public String status;
    public String view_count;
    public String reply_count;
    public String is_top;
    public String cate_id;
    public String user_logo;
  }


  @Override
  @SuppressWarnings(value = {"unchecked"})
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_group_info);
    ctx = this;
    mContext = GroupInfoActivity.this;
    session_id = new BaseApi().getSeesionId();
    groupApi = new GroupApi();
    mUpdateGroupApi = new GroupApi();
    userUid = new BaseApi().getUid();
    //获得上个activity传递的群组信息
    GroupBundle = getIntent().getExtras();
    isWeGroup = GroupBundle.getBoolean("isWeGroup");
    groupInfoMap = (HashMap<String, String>) GroupBundle.getSerializable("group_info");
    memberRecycler = (RecyclerView) findViewById(R.id.memberRecycler);
    group_id = Integer.parseInt(groupInfoMap.get("id"));

    RsenUrlUtil.execute(RsenUrlUtil.URL_XIAOZU_XIANGQING,
        new RsenUrlUtil.OnJsonResultListener<MyBean>() {
          @Override
          public void onNoNetwork(String msg) {
            ToastHelper.showToast(msg, Url.context);
          }

          @Override
          public Map getMap() {
            Map map = new HashMap();
            map.put("group_id", group_id);
            if (BaseFunction.isLogin()) {
              map.put("session_id", session_id);
            }
            return map;
          }

          @Override
          public void onParseJsonBean(List<MyBean> beans, JSONObject jsonObject) {
            try {
              MyBean bean = new MyBean();

              bean.is_join = jsonObject.getInt("is_join");
              bean.uid = jsonObject.getString("uid");
              JSONArray userArray = jsonObject.getJSONArray("GroupMenmber");
              bean.userList = parseUserList(userArray);
              //                    bean.uidList = parseUidList(userArray);
              beans.add(bean);
            } catch (Exception e) {

            }
          }

          @Override
          public void onResult(boolean state, final List<MyBean> beans) {
            if (state) {
              //isJoin = beans.get(0).is_join;
              userlist = beans.get(0).uidList;
              memberRecycler.setAdapter(
                  new MySubAdapter(GroupInfoActivity.this, beans.get(0).userList));
            } else {
              //                    ToastHelper.showToast("请求失败", Url.context);
            }
          }
        });
    //即时获取群组信息线程
    groupApi.setHandler(tempHandler);
    mUpdateGroupApi.setHandler(tempHandler);
    groupApi.getGroupInfo(String.valueOf(group_id));

    post = new Intent(mContext, SendTieziActivity.class);
    post.putExtra("group_id", group_id);
    user_logo = (CircleImageView) findViewById(R.id.user_logo);
    back_menu = (ImageView) findViewById(R.id.back_menu);
    group_post = (ImageView) findViewById(R.id.group_post);
    group_scro = (ScrollView) findViewById(R.id.group_scro);
    linear_list = (LinearLayout) findViewById(R.id.linear_list);
    linear_body = (LinearLayout) findViewById(R.id.linear_body);
    linear_isnull = (LinearLayout) findViewById(R.id.linear_isnull);
    body_probar = (RelativeLayout) findViewById(R.id.body_probar);
    group_logo = (ImageView) findViewById(R.id.group_logo);
    group_name = (TextView) findViewById(R.id.group_name);
    group_type = (TextView) findViewById(R.id.group_type);
    //        group_type_name = (TextView) findViewById(R.id.group_type_name);
    post_count = (TextView) findViewById(R.id.post_count);
    man_count = (TextView) findViewById(R.id.man_count);
    join_status = (TextView) findViewById(R.id.join_status);
    cate_menu = (ImageView) findViewById(R.id.cate_menu);

    join_group = (LinearLayout) findViewById(R.id.join_group);
    post_at_top = (LinearLayout) findViewById(R.id.post_at_top);
    group_post_listView = (RecyclerView) findViewById(R.id.group_post_listView);
    group_detail = (MoreTextView) findViewById(R.id.group_detail);
    refreshButn = (RelativeLayout) findViewById(R.id.refresh_butn);
    refreshImage = (ImageView) findViewById(R.id.refresh_image);

    enter_cy = (RelativeLayout) findViewById(R.id.enter_cy);
    group_logo.setOnClickListener(this);
    back_menu.setOnClickListener(this);
    group_post.setOnClickListener(this);
    linear_isnull.setOnClickListener(this);
    cate_menu.setOnClickListener(this);
    join_group.setOnClickListener(this);
    refreshButn.setOnClickListener(this);
    enter_cy.setOnClickListener(this);
    initGroupView(groupInfoMap);
    postInfoList = new ArrayList<HashMap<String, String>>();
  }

  public static class MyBean {
    public int is_join;
    public String uid;
    public List<String> userList;
    public ArrayList<String> uidList;
  }

  private List<String> parseUserList(JSONArray userArray) {
    List<String> userList = new ArrayList<>();
    for (int i = 0; i < userArray.length(); i++) {
      try {
        JSONObject jsonObject = userArray.getJSONObject(i);
        JSONObject user = jsonObject.getJSONObject("user");
        userList.add(RsenUrlUtil.URL_BASE + user.getString("avatar32"));
      } catch (JSONException e) {
        e.printStackTrace();
      }
    }
    return userList;
  }

  /*成员头像*/
  public class MySubAdapter extends RBaseAdapter<String> {
    private static final int MAX_COUT = 6;

    public MySubAdapter(Context context, List<String> datas) {
      super(context, datas);
    }

    @Override
    protected int getItemLayoutId(int viewType) {
      return R.layout.fragment_xiaozujingxuan_adapter_sub_item;
    }

    @Override
    protected void onBindView(RViewHolder holder, int position, String bean) {
      ImageLoader.loadOptimizedHttpImage(GroupInfoActivity.this, bean).bitmapTransform(new
          CropCircleTransformation(GroupInfoActivity.this))
          .error(R.drawable.side_user_avatar).placeholder(R.drawable.side_user_avatar).into
          (holder.imgV(R.id.logo));
    }

    @Override
    public int getItemCount() {
      return super.getItemCount() >= MAX_COUT ? MAX_COUT : super.getItemCount();
    }
  }

  private void init() {
    rm_adapter = new RemenhuatiAdapter(mContext);

    group_post_listView.setLayoutManager(new LinearLayoutManager(this));
    group_post_listView.setAdapter(rm_adapter);
    RsenUrlUtil.execute(RsenUrlUtil.URL_XIAOZU_XIANGQINGTZ,
        new RsenUrlUtil.OnJsonResultListener<RemenhuatiBean>() {
          @Override
          public void onNoNetwork(String msg) {
            ToastHelper.showToast(msg, Url.context);
          }

          @Override
          public Map getMap() {
            Map map = new HashMap();
            map.put("group_id", group_id);
            return map;
          }

          @Override
          public void onParseJsonBean(List<RemenhuatiBean> beans, JSONObject jsonObject) {
            RemenhuatiBean bean = new RemenhuatiBean();
            try {
              bean.title = jsonObject.getString("title");
              bean.content = jsonObject.getString("content");
              bean.supportCount = jsonObject.getString("supportCount");
              bean.is_support = jsonObject.getString("is_support");
              bean.nickname = jsonObject.getJSONObject("user").getString("nickname");
              bean.user_logo =
                  RsenUrlUtil.URL_BASE + jsonObject.getJSONObject("user").getString("avatar32");
              bean.id = jsonObject.getString("id");
              bean.uid = jsonObject.getString("uid");
              bean.group_id = jsonObject.getString("group_id");
              bean.create_time = jsonObject.getString("create_time");
              bean.update_time = jsonObject.getString("update_time");
              bean.last_reply_time = jsonObject.getString("last_reply_time");
              bean.status = jsonObject.getString("status");
              //                    bean.view_count = jsonObject.getString("view_count");
              bean.reply_count = jsonObject.getString("reply_count");
              bean.is_top = jsonObject.getString("is_top");

              bean.cate_id = jsonObject.getString("cate_id");
              JSONArray imgList = jsonObject.optJSONArray("imgList");
              List<String> imgs = new ArrayList<String>();
              for (int i = 0; imgList != null && i < imgList.length(); i++) {
                imgs.add(imgList.getString(i));
              }
              bean.imgList = imgs;
              beans.add(bean);
            } catch (JSONException e) {
            }
          }

          @Override
          public void onResult(boolean state, List<RemenhuatiBean> beans) {
            if (state) {
              maxNumber = beans.size();
              if (beans.size() != 0) {
                linear_list.setVisibility(View.VISIBLE);
                linear_isnull.setVisibility(View.GONE);

                rm_adapter.resetData(beans);

                //                        group_post_listView.setAdapter(new GroupListAdapter
                // (mContext, postInfoList, R.layout.group_post_item, null, null));
                //                        Utility.setListViewHeightBasedOnChildren
                // (group_post_listView);
              } else {
                linear_isnull.setVisibility(View.VISIBLE);
                linear_list.setVisibility(View.GONE);
              }
              linear_body.setVisibility(View.VISIBLE);
              body_probar.setVisibility(View.GONE);
            } else {
              //                    ToastHelper.showToast("请求失败", Url.context);
            }
          }
        });
  }

  //初始化群组固有信息
  private void initGroupView(HashMap<String, String> groupInfoMap) {

    group_name.setText(groupInfoMap.get("title"));
    //        if (groupInfoMap.get("group_type").equals("1")) {
    //            group_type.setText("私有群组");
    //        }
    group_detail.setText("群组简介：" + groupInfoMap.get("detail"));
    //        group_type_name.setText(groupInfoMap.get("type_name"));
    post_count.setText(groupInfoMap.get("user_nickname"));
    man_count.setText(groupInfoMap.get("memberCount"));

    ImageLoader.loadOptimizedHttpImage(this, groupInfoMap.get("group_logo")).bitmapTransform(new
        CropCircleTransformation(this))
        .error(R.drawable.picture_1_no).placeholder(R.drawable.picture_1_no).dontAnimate().into
        (group_logo);

    init();
  }

  @Override
  public void onClick(View v) {
    int viewID = v.getId();
    switch (viewID) {
      case R.id.back_menu:
        finish();
        break;
      case R.id.group_post:
        if (BaseFunction.isLogin()) {
          sendPost();
        } else {
          ToastHelper.showToast("请登陆后操作", mContext);
        }
        break;
      case R.id.cate_menu:
        cateWindow.showAsDropDown(group_post);
        break;
      case R.id.linear_isnull:
        sendPost();
        break;
      case R.id.enter_cy:
        Intent intent1 = new Intent(mContext, UserListActivity.class);
        intent1.putExtra("group_id", group_id);
        //                    intent.putStringArrayListExtra("uidlist", userlist);
        startActivity(intent1);
        break;
      case R.id.group_logo:
        Intent intent = new Intent(mContext, GroupDetailActivity.class);
        intent.putExtra("groupInfoMap", groupInfoMap);
        intent.putExtra("is_join", isJoin);
        startActivity(intent);
        break;
      case R.id.join_group:
        if (!groupApi.getSeesionId().equals("")) {
          if (groupInfoMap.get("uid").equals(userUid)) {
            //                        new AlertDialog.Builder(mContext)
            //                                .setTitle("解散群组")
            //                                .setMessage("确定吗？")
            //                                .setPositiveButton("是", new DialogInterface
            // .OnClickListener() {
            //                                    @Override
            //                                    public void onClick(DialogInterface dialog, int
            // which) {
            //                                        initFlag(false, false, true);
            //                                        groupApi.setHandler(myHandler);
            //                                        groupApi.dismissGroup(group_id + "");
            //                                    }
            //                                })
            //                                .setNegativeButton("否", null)
            //                                .show();
            ToastHelper.showToast("请在网站端操作", mContext);
            break;
          }
          if (isJoin == -1) {
            join_group.setClickable(false);
          }
          if (joinFlag) {
            //加入群组
            if (groupInfoMap.get("group_type").equals("1")) {
              //加入的是私有群组
              initFlag(false, true, false);
            } else {
              initFlag(true, false, false);
            }
            groupApi.setHandler(myHandler);
            groupApi.joinGroupPost(group_id + "");
            joinFlag = false;
          } else {
            //退出群组
            new AlertDialog.Builder(mContext).setTitle("退出群组")
                .setMessage("确定吗？")
                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(DialogInterface dialog, int which) {
                    groupApi.setHandler(myHandler);
                    groupApi.quitGroupPost(group_id + "");
                    joinFlag = true;
                  }
                })
                .setNegativeButton("否", null)
                .show();
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
        page = 1;
        count = true;
        //                new GroupPostThread(page, cateID).start();
        AnimationSet animationSet = new AnimationSet(true);
        RotateAnimation rotateAnimation =
            new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5F,
                Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(2000);
        animationSet.addAnimation(rotateAnimation);
        refreshImage.startAnimation(animationSet);
        break;
      default:
        break;
    }
  }

  //判断发帖权限
  public void sendPost() {
    if (!joinFlag) {
      //            post.putExtra("categoryList", categoryList);
      startActivity(post);
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
      map1.put("group_name", groupInfoMap.get("title"));
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
            count = true;
            page = 1;
            setBackColor(post_cateLinear);
            v.setBackgroundColor(Color.parseColor("#EDEDED"));
            cateID = v.getId();
            //                        new GroupPostThread(page, cateID).start();
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

  //标记加入的是私有还是共有群组
  public void initFlag(boolean publicgroup, boolean privategroup, boolean dismissgroup) {
    PUBLICGROUP = publicgroup;
    PRIVATEGROUP = privategroup;
    DISMISSGROUP = dismissgroup;
  }

  //解决ListView在scrollView中显示不全
  public static class Utility {
    public static void setListViewHeightBasedOnChildren(ListView listView) {
      //获取ListView对应的Adapter
      ListAdapter listAdapter = listView.getAdapter();
      if (listAdapter == null) {
        // pre-condition
        return;
      }

      int totalHeight = 0;
      for (int i = 0, len = listAdapter.getCount(); i < len;
           i++) {   //listAdapter.getCount()返回数据项的数目
        View listItem = listAdapter.getView(i, null, listView);
        listItem.measure(0, 0);  //计算子项View 的宽高
        //                int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth()
        // , View.MeasureSpec.AT_MOST);
        //                listItem.measure(desiredWidth, 0);
        totalHeight += listItem.getMeasuredHeight();  //统计所有子项的总高度
      }

      ViewGroup.LayoutParams params = listView.getLayoutParams();
      params.height = totalHeight + (listView.getDividerHeight() * listAdapter.getCount());
      //listView.getDividerHeight()获取子项间分隔符占用的高度
      //params.height最后得到整个ListView完整显示需要的高度
      listView.setLayoutParams(params);
    }
  }

  //ListView适配器
  private class GroupListAdapter extends SimpleAdapter {

    private ArrayList<HashMap<String, String>> postInfoList;
    private int resource;
    private ViewHolder viewHolder;

    public GroupListAdapter(Context context, ArrayList<HashMap<String, String>> data, int resource,
                            String[] from, int[] to) {
      super(context, data, resource, from, to);
      this.resource = resource;
      this.postInfoList = data;
    }

    @Override
    public long getItemId(int position) {
      return Integer.parseInt(postInfoList.get(position).get("id"));
    }

    @Override
    public int getCount() {
      return postInfoList.size();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

      KJBitmap kjbImage = KJBitmap.create();
      if (convertView == null) {
        viewHolder = new ViewHolder();
        convertView = LayoutInflater.from(mContext).inflate(resource, null);
        viewHolder.user_image = (ImageView) convertView.findViewById(R.id.user_image);
        viewHolder.post_title = (TextView) convertView.findViewById(R.id.post_title);
        viewHolder.user_name = (TextView) convertView.findViewById(R.id.user_name);
        viewHolder.post_category = (TextView) convertView.findViewById(R.id.post_category);
        viewHolder.view_count = (TextView) convertView.findViewById(R.id.view_count);
        viewHolder.reply_count = (TextView) convertView.findViewById(R.id.reply_count);
        convertView.setTag(viewHolder);
      } else {
        viewHolder = (ViewHolder) convertView.getTag();
      }
      kjbImage.display(viewHolder.user_image, postInfoList.get(position).get("user_logo"));
      //点击头像获取用户信息
      viewHolder.user_image.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          groupApi.goUserInfo(mContext, postInfoList.get(position).get("user_uid"));
        }
      });

      viewHolder.post_title.setText(postInfoList.get(position).get("title"));
      viewHolder.user_name.setText(postInfoList.get(position).get("user_nickname"));
      viewHolder.post_category.setText(titleMap.get(postInfoList.get(position).get("cate_id")));
      viewHolder.view_count.setText(postInfoList.get(position).get("view_count"));
      viewHolder.reply_count.setText(postInfoList.get(position).get("reply_count"));
      return convertView;
    }
  }

  //控件缓存器
  private class ViewHolder {
    ImageView user_image;
    TextView post_title;
    TextView user_name;
    TextView post_category;
    TextView view_count;
    TextView reply_count;
  }

  //帖子分类导航线程
  class CategoryThread extends Thread implements Runnable {

    private ArrayList<JSONObject> jsonObjArrayList;

    public CategoryThread() {
      super();
    }

    @Override
    public void run() {
      categoryList = new ArrayList<HashMap<String, String>>();
      titleMap = new HashMap<String, String>();
      jsonObjArrayList = groupApi.getGroupCategory("?s=" + Url.POSTCATEGORY, group_id);
      HashMap<String, String> map = new HashMap<String, String>();
      map.put("id", "0");
      map.put("group_id", String.valueOf(group_id));
      map.put("title", "全部分类");
      map.put("status", "1");
      categoryList.add(map);
      for (int i = 0; i < jsonObjArrayList.size(); i++) {
        JSONObject jsonObj = jsonObjArrayList.get(i);
        HashMap<String, String> map1 = new HashMap<String, String>();
        try {
          titleMap.put(jsonObj.getString("id"), jsonObj.getString("title"));
          map1.put("id", jsonObj.getString("id"));
          map1.put("group_id", jsonObj.getString("group_id"));
          map1.put("title", jsonObj.getString("title"));
          map1.put("status", jsonObj.getString("status"));

          categoryList.add(map1);
        } catch (JSONException e) {
          e.printStackTrace();
        }
      }
      Message message = new Message();
      message.what = 0x120;
      message.obj = categoryList;
      myHandler.sendMessage(message);
    }
  }


  /*数据适配器*/
  public class RemenhuatiAdapter extends RBaseAdapter<RemenhuatiBean> {
    Context context;

    public RemenhuatiAdapter(Context context) {
      super(context);
    }

    public RemenhuatiAdapter(Context context, List<RemenhuatiBean> datas) {
      super(context, datas);
    }

    @Override
    protected int getItemLayoutId(int viewType) {
      return R.layout.fragment_remen_ylq_adapter_item;
    }

    @Override
    protected void onBindView(RViewHolder holder, int position, final RemenhuatiBean bean) {
      holder.tV(R.id.title).setText(bean.title);
      holder.tV(R.id.content).setText(bean.content);
      holder.tV(R.id.supportCount).setText(bean.supportCount);
      holder.tV(R.id.reply_count).setText(bean.reply_count);
      holder.tV(R.id.nickname).setText(bean.nickname);
      //            ResUtil.setRoundImage(bean.user_logo, holder.imgV(R.id.user_logo));
      //            ImageLoader.getInstance().displayImage(bean.user_logo, holder.imgV(R.id
      // .user_logo),
      //                    new DisplayImageOptions.Builder()
      //                            .showImageOnLoading(R.drawable.ic_launcher)
      //                            .showImageForEmptyUri(R.drawable.ic_launcher)
      //                            .showImageOnFail(R.drawable.ic_launcher)
      //                            .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
      //                            .displayer(new RoundedBitmapDisplayer(100)).build());
      ImageLoader.loadOptimizedHttpImage(GroupInfoActivity.this, bean.user_logo)
          .
              bitmapTransform(new CropCircleTransformation(GroupInfoActivity.this))
          .into(holder.imgV(R.id.user_logo));
      if (bean.imgList != null && bean.imgList.size() > 0) {
        holder.v(R.id.img_layout).setVisibility(View.VISIBLE);

        int size = bean.imgList.size();
        holder.v(R.id.iv_1).setVisibility(size > 0 ? View.VISIBLE : View.GONE);
        holder.v(R.id.iv_2).setVisibility(size > 1 ? View.VISIBLE : View.GONE);
        holder.v(R.id.iv_3).setVisibility(size > 2 ? View.VISIBLE : View.GONE);

        for (int i = 0; i < bean.imgList.size(); i++) {
          String url = RsenUrlUtil.URL_BASE + bean.imgList.get(i);
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
                bundle.putStringArrayList("image_urls", (ArrayList<String>) bean.imgList);
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
          if (!"".equals(bean)) {
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

  public static void launch(Context context, boolean isWeGroup, RemenhuatiBean bean) {
    HashMap<String, String> map = new HashMap<>();
    Bundle bundle = new Bundle();
    //        map.put("id", bean.id);
    //        map.put("title", bean.title);
    //        map.put("group_type", bean.group_type);
    //        map.put("detail", bean.detail);
    //        map.put("type_name", bean.type_name);
    //        map.put("post_count", bean.post_count);
    //        map.put("group_logo", bean.logo);
    //        map.put("memberCount", bean.memberCount);
    //        map.put("uid", bean.uid);

    map.put("id", bean.id);
    map.put("uid", bean.uid);
    map.put("group_id", bean.group_id);
    //        map.put("group_name", groupInfoMap.get("title"));
    map.put("title", bean.title);
    map.put("content", bean.content);
    map.put("create_time", bean.create_time);
    map.put("update_time", bean.update_time);
    map.put("last_reply_time", bean.last_reply_time);
    map.put("status", bean.status);
    map.put("view_count", bean.view_count);
    map.put("reply_count", bean.reply_count);
    map.put("is_top", bean.is_top);
    map.put("cate_id", bean.cate_id);
    map.put("supportCount", bean.supportCount);
    map.put("is_support", bean.is_support);

    //        JSONObject tempJSONObj = jsonObj.getJSONObject("user");
    map.put("user_uid", bean.uid);
    map.put("user_nickname", bean.nickname);
    map.put("user_logo", bean.user_logo);

    bundle.putSerializable("post_info", map);
    bundle.putBoolean("isWeGroup", isWeGroup);
    bundle.putStringArrayList("imgList", (ArrayList<String>) bean.imgList);
    Intent intent = new Intent(context, GroupPostInfoActivity.class);
    intent.putExtras(bundle);
    context.startActivity(intent);
  }
}
