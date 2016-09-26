package com.thinksky.tox;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import com.thinksky.fragment.VideoPlayActivity;
import com.thinksky.holder.BaseBActivity;
import com.thinksky.myview.IssueListView;
import com.thinksky.redefine.CircleImageView;
import com.thinksky.utils.UserUtils;
import com.tox.BaseFunction;
import com.tox.IssueApi;
import com.tox.IssueData;
import com.tox.ToastHelper;
import com.tox.Url;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by 王杰 on 2015/3/20.
 */
public class IssueDetail extends BaseBActivity {
  //detail中的控件
  TextView issue_title, pinglunshu;
  ImageView issue_image;
  View listLoad;
  int lastVisibleReplyIndex;
  int MaxReplyNum;
  IssueListView repListView;
  ProgressBar image_progress;
  TextView issue_signature;
  ScrollView scrollView;
  //ListView中的控件
  TextView issue_userName, issue_time;
  CircleImageView issue_userImage;
  TextView detail_supportCounts;
  TextView detail_reply_count;
  TextView issue_reply_load;
  ProgressBar issue_reply_proBar;
  IssueApi issueApi;
  private String u;
  int issueID;
  private FrameLayout bofangshipin;
  //    TextView issDetails;
  TextView issDetails;
  ImageView issue_back;
  //获取详情主页面的数据
  ArrayList<HashMap<String, String>> issue_info;
  //回复数据
  ArrayList<HashMap<String, String>> reply_infos;
  ArrayList<HashMap<String, String>> reply_info;

  ArrayList<HashMap<String, String>> replyList;
  IssueListAdapter myAdapter;
  Handler mHandler;
  //定义点赞的按钮
  private LinearLayout zan, bottom;
  //session_id
  private String session_id;
  //session_id和status容器 arr[0]是点赞有没有成功   arr[1]是点赞返回的信息！！
  private String arr[] = null;
  //声明评论按钮
  LinearLayout Post_detail_comBtn;
  private Handler handler;
  //声明评论文本框
  private EditText issue_index_edittext;
  //声明评论的布局
  private LinearLayout issue_editBox;
  //声明评论"发表"
  TextView issue_index_send_com;
  //声明访问网站的按钮
  private LinearLayout issue_internet;

  //JSON解析点赞
  private void MyJson(String result) {
    String s = "";
    try {
      JSONObject jsonObject = new JSONObject(result);
      arr[0] = jsonObject.getString("success");
      arr[1] = jsonObject.getString("message");
      Log.d("status>>>>>>>>>>>", arr[0]);
      Log.d("status>>>>>>>>>>>", arr[1]);

    } catch (JSONException e) {
      e.printStackTrace();
    }
  }

  //开一个关于点赞的线程
  class MyThread extends Thread {
    @Override
    public void run() {
      Log.d("sessionj_id", session_id);
      System.out.println("issueID>>>>>>" + issueID);
      HttpClient httpClient = new DefaultHttpClient();
      HttpGet httpGet = new HttpGet(Url.HTTPURL + "?s=" + Url.SUPPORTISSUE + "&session_id=" +
          session_id + "&id=" + issueID);

      try {
        HttpResponse resp = httpClient.execute(httpGet);
        //检查响应的状态是否正常,检查状态码的值是否等于200
        int code = resp.getStatusLine().getStatusCode();
        if (code == 200) {
          //从响应对象当中取出数据
          HttpEntity entity = resp.getEntity();
          InputStream in = entity.getContent();
          BufferedReader reader = new BufferedReader(new InputStreamReader(in));
          String line = reader.readLine();
//                    textView.setText(line);
          MyJson(line);
          Message msg = handler.obtainMessage();
          msg.what = 3;
          msg.obj = arr[0];
          handler.sendMessage(msg);
          Log.d("HTTP", "从服务器取得的数据为:" + arr[0]);
        }

      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }


  //获取评论的线程
  class ContentThread extends Thread {
    @Override
    public void run() {
      IssueData issueData = new IssueData();
      ArrayList<JSONObject> jsonList = issueData.getIssueReplyJson("?s=" + Url.GETISSUECOMMENTS,
          issueID);
      replyList = new ArrayList<HashMap<String, String>>();
      try {
        for (int i = 0; i < jsonList.size(); i++) {
          JSONObject jsonObj = jsonList.get(i);
          HashMap<String, String> map1 = new HashMap<String, String>();
          map1.put("Comments_content", jsonObj.getString("content"));
          map1.put("Comments_create_time", jsonObj.getString("create_time"));
          //评论回复用户信息
          try {
            JSONObject userJSONObj = jsonObj.getJSONObject("user");
            if (userJSONObj != null) {
              map1.put("user_id", userJSONObj.getString("uid"));
              map1.put("user_name", userJSONObj.getString("nickname"));
              map1.put("user_image", issueData.getResourcesURL(userJSONObj.getString("avatar128")));
              replyList.add(map1);
            }
          } catch (JSONException e) {
            map1.put("user_name", "游客");
            replyList.add(map1);
          }
        }
        new IssueInfoTask().execute(issueID);
      } catch (JSONException e) {
        e.printStackTrace();
      }
      Message message = new Message();
      message.obj = replyList;
      mHandler.sendMessage(message);
    }
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    setContentView(R.layout.activity_issue_detail);
    handler = new Handler() {
      @Override
      public void handleMessage(Message msg) {
        switch (msg.what) {
          //评论专辑的handler
          case 1:
            arr[0] = (String) msg.obj;
            Log.d("Handler status", arr[0]);
            if (TextUtils.equals(arr[0], "true")) {
              Thread thread1 = new ContentThread();
              thread1.start();
              Toast.makeText(IssueDetail.this, "评论成功", Toast.LENGTH_SHORT).show();
              issue_editBox.setVisibility(View.GONE);
              bottom.setVisibility(View.VISIBLE);
            }
            break;
          //回复专辑评论的handler
          case 2:
            arr[0] = (String) msg.obj;
            Log.d("Handler status", arr[0]);
            if (TextUtils.equals(arr[0], "true")) {
              Thread thread2 = new ContentThread();
              thread2.start();
              Toast.makeText(IssueDetail.this, "回复评论成功", Toast.LENGTH_SHORT).show();
            }
            break;
          //专辑点赞的handler
          case 3:
            Log.d("Handler status", arr[0]);
            Log.d("Handler message", arr[1]);
            if (TextUtils.equals(arr[1], "需要登录")) {
              Toast.makeText(IssueDetail.this, "未登入", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.equals(arr[0], "false") && TextUtils.equals(arr[1],
                "您已经赞过，不能再赞了。")) {
              //Toast.makeText(IssueDetail.this, "您已经赞过，不能再赞了。", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.equals(arr[0], "true")) {
              Toast.makeText(IssueDetail.this, "点赞成功", Toast.LENGTH_SHORT).show();
              findViewById(R.id.dianzan).setSelected(true);
              new IssueInfoTask().execute(issueID);
            }
            break;
          default:
            break;
        }
      }
    };
    //获取Detail里的控件
    issue_back = (ImageView) findViewById(R.id.Issue_Back_list);
    issue_title = (TextView) findViewById(R.id.issue_title);
    pinglunshu = (TextView) findViewById(R.id.pinglunshu);
    issue_image = (ImageView) findViewById(R.id.issue_image);
    bofangshipin = (FrameLayout) findViewById(R.id.bofangshipin);
    issue_userName = (TextView) findViewById(R.id.issue_userName);
    issue_time = (TextView) findViewById(R.id.issue_time);
    issue_signature = (TextView) findViewById(R.id.issue_signature);
    issue_userImage = (CircleImageView) findViewById(R.id.issue_userImage);
    detail_supportCounts = (TextView) findViewById(R.id.detail_supportCounts);
    detail_reply_count = (TextView) findViewById(R.id.detail_reply_count);
    image_progress = (ProgressBar) findViewById(R.id.image_progress);
    //评论和回复列表
    scrollView = (ScrollView) findViewById(R.id.scrollView);
    repListView = (IssueListView) findViewById(R.id.issue_list);
    //listView最后的加载按钮
    listLoad = getLayoutInflater().inflate(R.layout.issue_detail_listview_probar, null);
    issue_reply_load = (TextView) listLoad.findViewById(R.id.issue_reply_load);
    issue_reply_proBar = (ProgressBar) listLoad.findViewById(R.id.issue_reply_proBar);
    //获取Session_id
    issueApi = new IssueApi();
    session_id = issueApi.getSeesionId();
    Log.e("session_id>?>?>", session_id);
    //获取访问网络按钮
    //issue_internet = (LinearLayout) findViewById(R.id.issue_internet);
    //点赞
    zan = (LinearLayout) findViewById(R.id.Post_detail_likeBtn);
    bottom = (LinearLayout) findViewById(R.id.bottom);
    zan.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        new MyThread().start();
      }
    });
    //new出点赞状态容器
    arr = new String[2];
    //获取评论布局
    issue_editBox = (LinearLayout) findViewById(R.id.issue_editBox);
    //获取评论的EditBox
    issue_index_edittext = (EditText) findViewById(R.id.issue_index_edittext);

    //获取评论的TextView
    issue_index_send_com = (TextView) findViewById(R.id.issue_index_send_com);
    issue_index_send_com.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        //得到EditView中的评论
        final String com = issue_index_edittext.getText().toString();
        if (com.equals("")) {
          Toast.makeText(IssueDetail.this, "评论不能为空", Toast.LENGTH_SHORT).show();
          return;
        }
        Log.d("issue_index_edittext", com);
        new Thread(new Runnable() {
          @Override
          public void run() {
            HttpClient httpClient = new DefaultHttpClient();
            String url = Url.HTTPURL + "?s=" + Url.SENDISSUECOMMENT + "&session_id=" + session_id
                + "&row_id=" + issueID + "&content=";
            HttpGet httpGet = new HttpGet(url + URLEncoder.encode(com));
            try {
              HttpResponse resp = httpClient.execute(httpGet);
              //检查响应的状态是否正常,检查状态码的值是否等于200
              int code = resp.getStatusLine().getStatusCode();
              if (code == 200) {
                //从响应对象当中取出数据
                HttpEntity entity = resp.getEntity();
                InputStream in = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                String line = reader.readLine();
//                    textView.setText(line);
                MyJson(line);
                Message msg = handler.obtainMessage();
                msg.what = 1;
                msg.obj = arr[0];
                handler.sendMessage(msg);
              }
            } catch (IOException e) {
              e.printStackTrace();
            }
          }
        }).start();
      }
    });
    //获取评论按钮
    Post_detail_comBtn = (LinearLayout) findViewById(R.id.Post_detail_comBtn);
    Post_detail_comBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (BaseFunction.isLogin()) {
          if (issue_editBox.getVisibility() == View.GONE) {
            issue_editBox.setVisibility(View.VISIBLE);
            bottom.setVisibility(View.GONE);
          } else {
            issue_editBox.setVisibility(View.GONE);
            bottom.setVisibility(View.VISIBLE);
          }
        } else {
          Toast.makeText(IssueDetail.this, "未登录", Toast.LENGTH_SHORT).show();
        }
      }
    });
    //专辑详情
    issDetails = (TextView) findViewById(R.id.iss_details);
    Bundle id = this.getIntent().getExtras();
    issueID = id.getInt("id");

    //listView的异步加载器
    new IssueInfoTask().execute(issueID);
    mHandler = new Handler() {
      @Override
      @SuppressWarnings(value = {"unchecked"})
      public void handleMessage(Message msg) {
        reply_infos = (ArrayList<HashMap<String, String>>) msg.obj;
        // 加上底部View，注意要放在setAdapter方法前
        repListView.addFooterView(listLoad);
        MaxReplyNum = reply_infos.size();
        reply_info = new ArrayList<HashMap<String, String>>();
        for (int i = 0; i < reply_infos.size(); i++) {
          reply_info.add(reply_infos.get(i));
        }
        //
        pinglunshu.setText("评论" + "(" + reply_info.size() + ")");
        myAdapter = new IssueListAdapter(IssueDetail.this, reply_info, R.layout
            .issue_detail_listview_item,
            null, new int[]{R.id.replyTime, R.id.replyTextView});
        repListView.setAdapter(myAdapter);
        //设置scrollView初始化时始终在顶部
        scrollView.smoothScrollTo(0, 20);
      }
    };

    Thread thread = new ContentThread();
    thread.start();

    repListView.setOnScrollListener(new AbsListView.OnScrollListener() {
      int totalItemCount;

      @Override
      public void onScrollStateChanged(AbsListView view, int scrollState) {

      }

      @Override
      public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int
          totalItemCount) {
        // 计算最后可见条目的索引
        lastVisibleReplyIndex = firstVisibleItem + visibleItemCount - 1;
        this.totalItemCount = totalItemCount;
      }
    });

    issue_back.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        IssueDetail.this.finish();
      }
    });
    issue_reply_load.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        issue_reply_proBar.setVisibility(View.VISIBLE);
        issue_reply_load.setVisibility(View.GONE);
        new Handler().postDelayed(new Runnable() {

          @Override
          public void run() {
            LoadingMoreReply();
            issue_reply_load.setVisibility(View.VISIBLE);
            issue_reply_proBar.setVisibility(View.GONE);
            myAdapter.notifyDataSetChanged();
          }
        }, 2000);
        // 所有的条目已经和最大条数相等，则移除底部的View
        if (MaxReplyNum == myAdapter.getCount() && MaxReplyNum > 0) {
          repListView.removeFooterView(listLoad);
          Toast.makeText(IssueDetail.this, "已没有更多评论", Toast.LENGTH_SHORT).show();
        } else if (MaxReplyNum == 0) {
//                    repListView.removeFooterView(listLoad);
          issue_reply_load.setText("还没有评论");
          Toast.makeText(IssueDetail.this, "还没有人评论，留个脚印吧", Toast.LENGTH_SHORT).show();
        }
      }
    });
  }

  //主页面的异步加载器
  private class IssueInfoTask extends AsyncTask<Integer, Void, ArrayList<HashMap<String, String>>> {

    private ArrayList<HashMap<String, String>> comments;
    int issueID;
    String str;

    @Override
    protected void onPostExecute(final ArrayList<HashMap<String, String>> comments) {
      if (hasDestroyed()) {
        return;
      }
      //为每个文本控件赋值
      issue_title.setText(comments.get(0).get("title"));
      //专辑介绍正则处理
//            str = comments.get(0).get("content").replaceAll("[<][/pbr]{2,3}[>]", "\n")
// .replaceAll("[<][^>]+[>]", "").replace("\\n","\n");
//            Log.e("测试：",comments.get(0).get("content"));
      str = comments.get(0).get("content").replace("\\n", "\n");

      issDetails.setText(str);
      //给textView加滚动条
//            issDetails.setMovementMethod(new ScrollingMovementMethod());
//            issDetails.setBackgroundColor(0);
//            issDetails.loadDataWithBaseURL(null, comments.get(0).get("content"), "text/html",
// "utf-8", null);
//            issDetails.getSettings().setSupportZoom(true);
      issue_userName.setText(UserUtils.getUserName(IssueDetail.this, comments.get(0).get
          ("user_id"), comments.get(0).get("user_name")));
      detail_supportCounts.setText(comments.get(0).get("support_count"));
      detail_reply_count.setText(comments.get(0).get("reply_count"));
      issue_time.setText(comments.get(0).get("create_time"));
      if (comments.get(0).get("signature").length() != 0) {
        issue_signature.setText(comments.get(0).get("signature"));
      } else {
        issue_signature.setText("主人太懒，还没有个性签名");
      }
      //为图片控件加载数据
      String u = comments.get(0).get("cover_url").replace("opensns//opensns", "opensns");
//            kjBitmap.display(issue_image, u);
//            ImageLoader.getInstance().displayImage(u, issue_image);
      com.thinksky.utils.imageloader.ImageLoader.loadOptimizedHttpImage(IssueDetail.this, u).into
          (issue_image);
      com.thinksky.utils.imageloader.ImageLoader.loadOptimizedHttpImage(IssueDetail.this,
          comments.get(0).get("issue_userImage")).into(issue_userImage);
      issue_userImage.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          issueApi.goUserInfo(IssueDetail.this, comments.get(0).get("user_id"));
        }
      });

      bofangshipin.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          String url = comments.get(0).get("url");
          url.replace("<p>", "").replace("</p>", "");
          Log.d("url------------------>", "url");
          Bundle bundle = new Bundle();
          bundle.putString("url", url);
          Intent intent = new Intent(IssueDetail.this, VideoPlayActivity.class);
          intent.putExtras(bundle);


          IssueDetail.this.startActivity(intent);

        }
      });
    }

    @Override
    protected ArrayList<HashMap<String, String>> doInBackground(Integer... params) {
      try {
        issueID = params[0];
        IssueData issueData = new IssueData();
        JSONArray jsonList = issueData.getIssueDetailJson("?s=" + Url.GETISSUEDETAIL, issueID);
        comments = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> map1 = new HashMap<String, String>();
        //专辑详情
        if (jsonList.length() > 0) {
          JSONObject jsonObj = (JSONObject) jsonList.get(0);
          map1.put("id", jsonObj.getString("id"));
          map1.put("title", jsonObj.getString("title"));
          map1.put("cover_url", issueData.getResourcesURL(jsonObj.getString("cover_url")));
          map1.put("content", jsonObj.getString("content"));
          map1.put("url", jsonObj.getString("url"));
          map1.put("reply_count", jsonObj.getString("reply_count"));
          map1.put("support_count", jsonObj.getString("support_count"));
          map1.put("create_time", jsonObj.getString("create_time"));
          //发布专辑用户信息
          JSONObject userJSONObj = jsonObj.getJSONObject("user");
          map1.put("user_id", userJSONObj.getString("uid"));
          map1.put("user_name", userJSONObj.getString("nickname"));
          map1.put("signature", userJSONObj.getString("signature"));
          map1.put("user_image", issueData.getResourcesURL(Url.IMAGE + userJSONObj.getString
              ("avatar128")));
          comments.add(map1);
          return comments;
        }
      } catch (NullPointerException e) {
        ToastHelper.showToast("获取数据失败，请重试", IssueDetail.this);
      } catch (JSONException e) {
        e.printStackTrace();
      }
      return null;
    }
  }

  //ViewHolder缓存类
  private class ViewHolder {
    //listView中的控件
    ImageView replyUserImage;
    TextView replyUserName;
    TextView replyTime;
    TextView replyTextView;
  }

  private class IssueListAdapter extends SimpleAdapter {

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
      final ViewHolder viewHolder;
      if (convertView == null) {
        viewHolder = new ViewHolder();
        convertView = LayoutInflater.from(mContext).inflate(R.layout.comment_item,
            null);
        //获取listView中的控件
        viewHolder.replyUserImage = (ImageView) convertView.findViewById(R.id.user_avatar);
        viewHolder.replyUserName = (TextView) convertView.findViewById(R.id.user_name);
        viewHolder.replyTime = (TextView) convertView.findViewById(R.id.time);
        viewHolder.replyTextView = (TextView) convertView.findViewById(R.id.content);
        convertView.setTag(viewHolder);
      } else {
        viewHolder = (ViewHolder) convertView.getTag();
      }
      //给控件赋值
      com.thinksky.utils.imageloader.ImageLoader.loadOptimizedHttpImage(IssueDetail.this,
          reply_info.get(position).get("user_image")).bitmapTransform(new
          CropCircleTransformation(IssueDetail.this))
          .placeholder(R.drawable.side_user_avatar).error(R.drawable.side_user_avatar)
          .dontAnimate().into(viewHolder
          .replyUserImage);
      viewHolder.replyUserImage.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          if (null != reply_info.get(position).get("user_id")) {
            issueApi.goUserInfo(mContext, reply_info.get(position).get("user_id"));
          }
        }
      });
      viewHolder.replyUserName.setText(UserUtils.getUserName(IssueDetail.this, reply_info.get
          (position).get("user_id"), reply_info.get(position).get("user_name")));
      viewHolder.replyTextView.setText(reply_info.get(position).get("Comments_content"));
      viewHolder.replyTime.setText(reply_info.get(position).get("Comments_create_time"));

      return convertView;
    }

    private Context mContext;

    public IssueListAdapter(Context context, ArrayList<HashMap<String, String>> data, int
        resource, String[] from, int[] to) {
      super(context, data, resource, from, to);
      this.mContext = context;
    }

    @Override
    public int getCount() {
      return reply_info.size();
    }
  }

  //评论分页加载
  private void LoadingMoreReply() {
    int count = myAdapter.getCount();
    if (count + 5 < MaxReplyNum) {
      for (int i = count; i < count + 5; i++) {
        reply_info.add(reply_infos.get(i));
      }
    } else {
      for (int i = count; i < MaxReplyNum; i++) {
        reply_info.add(reply_infos.get(i));
      }
    }
  }
}