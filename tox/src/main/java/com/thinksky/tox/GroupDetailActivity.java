package com.thinksky.tox;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.thinksky.holder.BaseApplication;
import com.thinksky.holder.BaseBActivity;
import com.thinksky.injection.GlobalModule;
import com.thinksky.myview.IssueListView;
import com.thinksky.myview.MoreTextView;
import com.thinksky.net.UiRpcSubscriberSimple;
import com.thinksky.net.rpc.model.HotPostModel;
import com.thinksky.net.rpc.service.AppService;
import com.thinksky.redefine.CircleImageView;
import com.thinksky.rsen.ResUtil;
import com.thinksky.serviceinjection.DaggerServiceComponent;
import com.thinksky.serviceinjection.ServiceModule;
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
import org.json.JSONException;
import org.json.JSONObject;
import org.kymjs.aframe.bitmap.KJBitmap;

/**
 * Created by Administrator on 2015/6/10 0010.
 */
public class GroupDetailActivity extends BaseBActivity implements View.OnClickListener {
  private static boolean PUBLICGROUP = false;
  private static boolean PRIVATEGROUP = false;
  private static boolean DISMISSGROUP = false;
  private HashMap<String, String> groupInfoMap;
  private LinearLayout join_group;
  private Context mContext;
  KJBitmap kjBitmap;
  GroupApi groupApi;
  String session_id;
  LinearLayout topPager;
  private ImageView backMenu;
  private ScrollView mScrollView;
  private ImageView groupLogo;
  private TextView groupName;
  private TextView groupBelong;
  private TextView groupType;
  private TextView postCount;
  private TextView manCount;
  private LinearLayout userInfo;
  private CircleImageView creatorHead;
  private TextView creatorNickName;
  private MoreTextView groupDetail;
  private TextView groupNotice;
  private TextView join_status;
  private IssueListView hotPostView;
  private TextView backBefore;
  private LinearLayout hotLine;
  private boolean joinFlag = true;
  private int isJoin;
  private Context ctx;
  private int group_id;
  private String userUid;
  String groupId;

  @Inject
  AppService mAppService;

  //对加入群组的状态进行实时判断
  private Handler tempHandler = new Handler() {
    @Override public void handleMessage(Message msg) {
      if (msg.what == 0) {
        String result = (String) msg.obj;
        groupInfoMap = groupApi.getGroupInfoMap(result, groupInfoMap);
        Log.e("groupInfoMap>>>>>>>>", groupInfoMap.toString());
        if (BaseFunction.isLogin()) {
          join_group.setVisibility(View.VISIBLE);
          //                    isJoin = Integer.parseInt(groupInfoMap.get("is_join"));
          if (isJoin == 1) {
            joinFlag = false;
            join_status.setText("退出");
          } else if (isJoin == -1) {
            join_status.setText("已申请，审核中");
            join_group.setClickable(false);
          } else if (isJoin != 1) {
            if (groupInfoMap.get("uid").equals(userUid)) {
              join_status.setText("管理小组");
            } else {
              join_status.setText("+加入");
              joinFlag = true;
            }
          }
        } else {
          join_group.setVisibility(View.GONE);
        }
      }
    }
  };
  private Handler myHandler = new Handler() {

    private ArrayList<HashMap<String, String>> categoryList;
    private ArrayList<HashMap<String, String>> topPostInfoList;

    @Override @SuppressWarnings(value = { "unchecked" }) public void handleMessage(Message msg) {
      switch (msg.what) {
        case 0:
          if (joinFlag) {
            joinFlag = true;
            ToastHelper.showToast("退出成功", ctx);
            join_status.setText("+加入");
          } else {
            if (PUBLICGROUP) {
              joinFlag = false;
              ToastHelper.showToast("加入成功", ctx);
              join_status.setText("退出");
            }
            if (PRIVATEGROUP) {
              join_group.setClickable(false);
              ToastHelper.showToast("申请加入成功", ctx);
              join_status.setText("正在审核");
            }
          }
          break;
        case 800:
          if (PUBLICGROUP) {
            if (joinFlag) {
              join_status.setText("+加入");
              ToastHelper.showToast("退群失败，还未加入该群", ctx);
            } else {
              join_status.setText("退出");
              ToastHelper.showToast("你已加入该群", ctx);
            }
          }
          if (PRIVATEGROUP) {
            join_status.setText("已申请，审核中");
            join_group.setClickable(false);
          }
          break;

        default:
          break;
      }
    }
  };

  @Override @SuppressWarnings(value = { "unchecked" })
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_group_detail);
    inject();
    ctx = this;
    mContext = GroupDetailActivity.this;
    kjBitmap = KJBitmap.create();
    groupApi = new GroupApi();
    session_id = new BaseApi().getSeesionId();
    userUid = new BaseApi().getUid();
    groupInfoMap = (HashMap<String, String>) getIntent().getExtras().get("groupInfoMap");
    isJoin = getIntent().getExtras().getInt("is_join");
    groupId = groupInfoMap.get("id");
    //        Log.e("groupInfoMap>>>>>>>>>>>",groupInfoMap.toString());
    group_id = Integer.parseInt(groupInfoMap.get("id"));
    new Thread(new NoticeThread(groupId)).start();
    loadPost(groupId, session_id);
    groupApi.setHandler(tempHandler);
    groupApi.getGroupInfo(group_id + "");
    backMenu = (ImageView) findViewById(R.id.back_menu);
    mScrollView = (ScrollView) findViewById(R.id.group_scrollView);
    topPager = (LinearLayout) findViewById(R.id.top_pager);
    groupLogo = (ImageView) findViewById(R.id.group_logo);
    groupName = (TextView) findViewById(R.id.group_name);
    groupType = (TextView) findViewById(R.id.group_type);
    groupBelong = (TextView) findViewById(R.id.group_type_belong);
    //        postCount = (TextView) findViewById(R.id.post_count);
    manCount = (TextView) findViewById(R.id.man_count);
    userInfo = (LinearLayout) findViewById(R.id.user_info);
    join_group = (LinearLayout) findViewById(R.id.join_group);
    creatorHead = (CircleImageView) findViewById(R.id.creator_user_head);
    creatorNickName = (TextView) findViewById(R.id.creator_user_name);
    groupDetail = (MoreTextView) findViewById(R.id.group_detail);
    groupNotice = (TextView) findViewById(R.id.group_notice);
    join_status = (TextView) findViewById(R.id.join_status);
    hotLine = (LinearLayout) findViewById(R.id.hot_line);
    hotPostView = (IssueListView) findViewById(R.id.hot_post_listView);
    backBefore = (TextView) findViewById(R.id.back_before);
    InitThisView(groupInfoMap);

    backMenu.setOnClickListener(this);
    userInfo.setOnClickListener(this);
    join_group.setOnClickListener(this);
    backBefore.setOnClickListener(this);

    hotPostView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent postIntent = new Intent(mContext, GroupPostInfoActivity.class);
        postIntent.putExtra(GroupPostInfoActivity.BUNDLE_KEY_POST,
            ((HotPostListAdapter)parent.getAdapter()).getItem(position));
        startActivity(postIntent);
      }
    });
    mScrollView.smoothScrollTo(0, 0);
  }

  protected void InitThisView(HashMap<String, String> groupInfoMap) {

    groupName.setText(groupInfoMap.get("title"));
    if (groupInfoMap.get("group_type").equals("1")) {
      groupType.setText("私有群组");
    }
    groupDetail.setText(groupInfoMap.get("detail"));
    groupBelong.setText(groupInfoMap.get("type_name"));
    //        postCount.setText(groupInfoMap.get("post_count"));
    manCount.setText(groupInfoMap.get("memberCount"));
    if (groupInfoMap.get("group_logo").equals(Url.USERHEADURL + "Public/Core/images/nopic.png")) {
      groupLogo.setImageResource(R.drawable.side_user_avatar);
    } else {
      ResUtil.setRoundImage(groupInfoMap.get("group_logo"), groupLogo);
    }
    ImageLoader.loadOptimizedHttpImage(GroupDetailActivity.this, groupInfoMap.get("user_logo"))
        .into(creatorHead);
    creatorNickName.setText(groupInfoMap.get("user_nickname"));
    if (Integer.parseInt(groupInfoMap.get("is_join")) == 1) {
      joinFlag = false;
      join_status.setText("退出");
    } else {
      joinFlag = true;
    }
  }

  @Override public void onClick(View v) {
    int viewId = v.getId();
    switch (viewId) {
      case R.id.back_menu:
        finish();
        break;
      case R.id.back_before:
        finish();
        break;
      case R.id.user_info:
        groupApi.goUserInfo(mContext, groupInfoMap.get("user_uid"));
        break;
      case R.id.join_group:
        if (!groupApi.getSeesionId().equals("")) {
          if (groupInfoMap.get("uid").equals(userUid)) {
            //                        new AlertDialog.Builder(mContext)
            //                                .setTitle("解散群组")
            //                                .setMessage("确定吗？")
            //                                .setPositiveButton("是", new DialogInterface.OnClickListener() {
            //                                    @Override
            //                                    public void onClick(DialogInterface dialog, int which) {
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
            new AlertDialog.Builder(mContext).setTitle("退出小组")
                .setMessage("确定吗？")
                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                  @Override public void onClick(DialogInterface dialog, int which) {
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
      //            case R.id.join_status:
      //                if (joinFlag){
      //                    //做加入群组的操作
      //                    joinGroup.setText("加入");
      //                }else{
      //                    AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
      //                    dialog.setMessage("确定退出群组？");
      //                    dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
      //                        @Override
      //                        public void onClick(DialogInterface dialog, int which) {
      //                            //做退群操作
      //                            joinFlag = true;
      //                            joinGroup.setText("加入");
      //                        }
      //                    });
      //                    dialog.setNegativeButton("取消", null);
      //                    dialog.create().show();
      //                }
      //                break;
      default:
        break;
    }
  }

  //标记加入的是私有还是共有群组
  public void initFlag(boolean publicgroup, boolean privategroup, boolean dismissgroup) {
    PUBLICGROUP = publicgroup;
    PRIVATEGROUP = privategroup;
    DISMISSGROUP = dismissgroup;
  }

  private Handler mHandler = new Handler() {

    private HashMap<String, String> noticeInfoMap;
    private ArrayList<HashMap<String, String>> arrayList;

    @Override public void handleMessage(Message msg) {
      switch (msg.what) {
        case 0x155:
          noticeInfoMap = (HashMap<String, String>) msg.obj;
          //                    Log.e("noticeInfoMap>>>>>>",noticeInfoMap.toString());
          if (noticeInfoMap.get("notice") != null) {
            groupNotice.setText(noticeInfoMap.get("notice"));
          } else {
            groupNotice.setText("本群还没有发表公告！");
          }
          break;
        default:
          break;
      }
    }
  };

  private void inject() {
    DaggerServiceComponent.builder().globalModule(new GlobalModule(BaseApplication.getApplication
        ())).serviceModule(new ServiceModule())
        .build().inject(this);
  }

  private void loadPost(final String groupId, String session_id) {
    manageRpcCall(mAppService.getGroupMyPost(session_id, groupId), new
        UiRpcSubscriberSimple<HotPostModel>(this) {


          @Override
          protected void onSuccess(HotPostModel hotPostModel) {
            List<HotPostModel.HotPostBean> arrayList = hotPostModel.getList();
            if (arrayList.size() == 0) {
              TextView tempView = new TextView(mContext);
              tempView.setPadding(0, 0, 0, 5);
              tempView.setGravity(Gravity.CENTER);
              tempView.setBackgroundResource(R.drawable.item_bg);
              tempView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  if (Integer.parseInt(groupInfoMap.get("is_join")) == 1) {
                    Intent post = new Intent(mContext, PostGroupActivity.class);
                    post.putExtra("group_id", groupId);
                    startActivity(post);
                  } else {
                    ToastHelper.showToast("加群才能发帖", mContext);
                  }
                }
              });
              tempView.setText("还没有帖子，我来发表一个");
              hotLine.addView(tempView);
            }
            hotPostView.setAdapter(new HotPostListAdapter(mContext, arrayList));
          }

          @Override
          protected void onEnd() {

          }
        });
  }

  //热帖ListView适配器
  class HotPostListAdapter extends BaseAdapter {

    private List<HotPostModel.HotPostBean> topPostList;
    private Context context;
    ViewHolder viewHolder;

    public HotPostListAdapter(Context context, List<HotPostModel.HotPostBean> data) {
      this.context = context;
      this.topPostList = data;
    }

    @Override public int getCount() {
      return topPostList.size();
    }

    @Override public HotPostModel.HotPostBean getItem(int position) {
      return topPostList.get(position);
    }

    @Override public long getItemId(int position) {
      return 0;
    }

    @Override public View getView(int position, View convertView, ViewGroup parent) {
      if (convertView == null) {
        viewHolder = new ViewHolder();
        convertView =
            LayoutInflater.from(context).inflate(R.layout.group_hot_post_item, parent, false);
        viewHolder.postTitle = (TextView) convertView.findViewById(R.id.hot_post_title);
        viewHolder.postContent = (TextView) convertView.findViewById(R.id.hot_post_content);
        viewHolder.replyCount = (TextView) convertView.findViewById(R.id.reply_count);
        convertView.setTag(viewHolder);
      } else {
        viewHolder = (ViewHolder) convertView.getTag();
      }
      viewHolder.postTitle.setText(topPostList.get(position).getTitle());
      viewHolder.postContent.setText(topPostList.get(position).getContent());
      viewHolder.replyCount.setText(topPostList.get(position).getReply_count());
      return convertView;
    }
  }

  //缓存器
  class ViewHolder {
    TextView postTitle;
    TextView postContent;
    TextView replyCount;
  }

  //获取公告信息的线程
  class NoticeThread implements Runnable {

    private JSONObject jsonObject;
    private HashMap<String, String> noticeInfo;
    private String groupId;

    public NoticeThread(String groupId) {
      this.groupId = groupId;
    }

    @Override public void run() {
      jsonObject = groupApi.getGroupNotice(Url.getApiUrl(Url.GROUPNOTICE), groupId);
      noticeInfo = new HashMap<String, String>();
      //            Log.e("jsonObject>>>>>>",jsonObject.toString());
      try {
        JSONObject tempObj = jsonObject.getJSONObject("list");
        noticeInfo.put("noticeId", tempObj.getString("id"));
        noticeInfo.put("groupId", tempObj.getString("group_id"));
        noticeInfo.put("notice", tempObj.getString("content"));
        noticeInfo.put("createTime", tempObj.getString("create_time"));
      } catch (JSONException e) {
        e.printStackTrace();
      }
      Message message = new Message();
      message.what = 0x155;
      message.obj = noticeInfo;
      mHandler.sendMessage(message);
    }
  }

}