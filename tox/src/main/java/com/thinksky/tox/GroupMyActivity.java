package com.thinksky.tox;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import com.thinksky.serviceinjection.DaggerServiceComponent;
import com.thinksky.serviceinjection.ServiceModule;
import com.thinksky.utils.imageloader.ImageLoader;
import com.tox.BaseApi;
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
 * Created by jiao on 2015/6/10 0010.
 */
public class GroupMyActivity extends BaseBActivity implements View.OnClickListener {

  private HashMap<String, String> groupInfoMap;
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
  private TextView joinGroup;
  private IssueListView hotPostView;
  private TextView backBefore;
  private LinearLayout hotLine;
  private boolean joinFlag = true;
  Bundle GroupBundle;
  Boolean isWeGroup;
  String groupId;

  @Inject
  AppService mAppService;

  @Override
  @SuppressWarnings(value = {"unchecked"})
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_group_detail);

    inject();

    mContext = GroupMyActivity.this;
    kjBitmap = KJBitmap.create();
    groupApi = new GroupApi();
    session_id = new BaseApi().getSeesionId();
    //获得上个activity传递的群组信息
    Intent groupIntent = this.getIntent();
    GroupBundle = groupIntent.getExtras();
    isWeGroup = GroupBundle.getBoolean("isWeGroup");
    groupInfoMap = (HashMap<String, String>) GroupBundle.getSerializable("group_info");
    groupId = groupInfoMap.get("id");

    new Thread(new NoticeThread(groupId)).start();
    loadPost(groupId, session_id);

    backMenu = (ImageView) findViewById(R.id.back_menu);
    mScrollView = (ScrollView) findViewById(R.id.group_scrollView);
    topPager = (LinearLayout) findViewById(R.id.top_pager);
    groupLogo = (ImageView) findViewById(R.id.group_logo);
    groupName = (TextView) findViewById(R.id.group_name);
    groupType = (TextView) findViewById(R.id.group_type);
    groupBelong = (TextView) findViewById(R.id.group_type_belong);
    postCount = (TextView) findViewById(R.id.post_count);
    manCount = (TextView) findViewById(R.id.man_count);
    userInfo = (LinearLayout) findViewById(R.id.user_info);
    creatorHead = (CircleImageView) findViewById(R.id.creator_user_head);
    creatorNickName = (TextView) findViewById(R.id.creator_user_name);
    groupDetail = (MoreTextView) findViewById(R.id.group_detail);
    groupNotice = (TextView) findViewById(R.id.group_notice);
    joinGroup = (TextView) findViewById(R.id.join_status);
    hotLine = (LinearLayout) findViewById(R.id.hot_line);
    hotPostView = (IssueListView) findViewById(R.id.hot_post_listView);
    backBefore = (TextView) findViewById(R.id.back_before);
    initThisView(groupInfoMap);

    backMenu.setOnClickListener(this);
    userInfo.setOnClickListener(this);
    joinGroup.setOnClickListener(this);
    backBefore.setOnClickListener(this);

    hotPostView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent postIntent = new Intent(mContext, GroupPostInfoActivity.class);
        postIntent.putExtra(GroupPostInfoActivity.BUNDLE_KEY_POST, (HotPostModel.HotPostBean) parent.getItemAtPosition
            (position));
        startActivity(postIntent);
      }
    });
    mScrollView.smoothScrollTo(0, 0);
  }

  private void inject() {
    DaggerServiceComponent.builder().globalModule(new GlobalModule(BaseApplication.getApplication
        ())).serviceModule(new ServiceModule())
        .build().inject(this);
  }

  protected void initThisView(HashMap<String, String> groupInfoMap) {

    groupName.setText(groupInfoMap.get("title"));
    if (groupInfoMap.get("group_type").equals("1")) {
      groupType.setText("私有群组");
    }
    groupDetail.setText(groupInfoMap.get("detail"));
    groupBelong.setText(groupInfoMap.get("type_name"));
    postCount.setText(groupInfoMap.get("post_count"));
    manCount.setText(groupInfoMap.get("memberCount"));
    if (groupInfoMap.get("group_logo").equals(Url.USERHEADURL + "Public/Core/images/nopic.png")) {
      groupLogo.setImageResource(R.drawable.side_user_avatar);
    } else {
      ImageLoader.loadOptimizedHttpImage(GroupMyActivity.this, groupInfoMap.get("group_logo"))
          .into(groupLogo);
    }
    creatorNickName.setText(groupInfoMap.get("user_nickname"));
    if (Integer.parseInt(groupInfoMap.get("is_join")) == 1) {
      joinFlag = false;
      joinGroup.setText("退出群组");
    } else {
      joinFlag = true;
    }
  }

  @Override
  public void onClick(View v) {
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
        if (joinFlag) {
          //做加入群组的操作

        } else {
          AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
          dialog.setMessage("确定退出群组？");
          dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
              //做退群操作
              joinFlag = true;
              joinGroup.setText("加入群组");
            }
          });
          dialog.setNegativeButton("取消", null);
          dialog.create().show();
        }
        break;
      default:
        break;
    }
  }

  private Handler mHandler = new Handler() {

    private HashMap<String, String> noticeInfoMap;
    private ArrayList<HashMap<String, String>> arrayList;

    @Override
    public void handleMessage(Message msg) {
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

  //热帖ListView适配器
  class HotPostListAdapter extends BaseAdapter {

    private List<HotPostModel.HotPostBean> topPostList;
    private Context context;
    ViewHolder viewHolder;

    public HotPostListAdapter(Context context, List<HotPostModel.HotPostBean> beanList) {
      this.context = context;
      this.topPostList = beanList;
    }

    @Override
    public int getCount() {
      return topPostList.size();
    }

    @Override
    public Object getItem(int position) {
      return topPostList.get(position);
    }

    @Override
    public long getItemId(int position) {
      return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      if (convertView == null) {
        viewHolder = new ViewHolder();
        convertView = LayoutInflater.from(context).inflate(R.layout.group_hot_post_item, parent,
            false);
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

    @Override
    public void run() {
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


}