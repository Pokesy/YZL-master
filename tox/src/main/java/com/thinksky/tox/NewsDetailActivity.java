package com.thinksky.tox;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import com.thinksky.holder.BaseBActivity;
import com.thinksky.info.NewsDetailInfo;
import com.thinksky.info.NewsListInfo;
import com.thinksky.info.NewsReplyInfo;
import com.thinksky.utils.MyJson;
import com.tox.ImageLoader;
import com.tox.NewsApi;
import com.tox.ToastHelper;
import com.tox.Url;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import org.kymjs.aframe.bitmap.KJBitmap;

/**
 * 资讯详情页面
 * Created by Administrator on 2015/7/23 0023.
 */
public class NewsDetailActivity extends BaseBActivity implements View.OnClickListener {

  protected int activityCloseEnterAnimation;
  protected int activityCloseExitAnimation;
  private static boolean REPLY = false;
  private static boolean REPLYS = false;
  private String newsId;
  private Context mContext;
  ImageView backMenu, newsLogo, newsShare, newsReply;
  private ScrollView newsScroll;
  private RelativeLayout proBarLine;
  private TextView newsType, newsTitle, newsDescription, newsAuthor, newsTime, replyCount,
      edit_disable_text;
  LinearLayout replyBox, replyModule, news_fast_reply, support_button;
  private EditText replyEditText;
  private WebView newWebView;
  private TextView newsContent, sendButn, supportCount;
  private NewsListInfo newsListInfo;
  private String support;
  private NewsApi newsApi;
  private MyHandler mHandler = new MyHandler(this);
  private NewsDetailInfo newsDetailInfo;
  Set<AsyncTask> taskCollection;
  private ImageLoader imageLoader;
  private KJBitmap kjBitmap = KJBitmap.create();
  private boolean isReply = false;
  private boolean isDelReply = false;
  private int page = 1;
  private LinearLayout loadingBar;
  private TextView loadingBarText;
  private ProgressBar loadingProBar;
  private Message message;

  private Handler handler = new Handler() {
    private ArrayList<NewsReplyInfo> replyInfoList;

    @Override
    public void handleMessage(Message msg) {
      super.handleMessage(msg);
      switch (msg.what) {
        case 0:
          MyJson myJson = new MyJson();
          replyInfoList = myJson.getNewsReplyInfo((String) msg.obj);
          if (replyModule.getChildCount() > 0 && replyModule.getChildAt(0).getTag() != null &&
              replyModule.getChildAt(0)
                  .getTag()
                  .equals("isNull")) {
            replyModule.removeViewAt(0);
          }
          replyModule.addView(addReplyView(replyInfoList.get(0)), 0);

          Toast.makeText(NewsDetailActivity.this, "评论成功", Toast.LENGTH_SHORT).show();
          InputMethodManager imm =
              (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
          if (imm.isActive()) {
            imm.hideSoftInputFromWindow(replyEditText.getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
          }
          replyBox.setVisibility(View.GONE);
          //                    isReply = true;
          //                    newsApi.setHandler(mHandler);
          //                    newsApi.getNewsReply(newsId);
          break;
      }
    }
  };

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.activity_news_detail);
    mContext = NewsDetailActivity.this;
    newsListInfo = (NewsListInfo) getIntent().getExtras().get("newsInfo");
    newsId = newsListInfo.getId();
    support = TextUtils.isEmpty(getIntent().getExtras().getString("support")) ? "0" : getIntent()
        .getExtras().getString("support");
    newsApi = new NewsApi(mHandler);
    imageLoader = ImageLoader.getInstance();
    taskCollection = new HashSet<AsyncTask>();
    intView();
    loadHTML();
    exitAnim();
  }

  @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
  private void loadHTML() {
    String url = "file:///android_asset/zixun/article.html";
    WebSettings ws = newWebView.getSettings();
    ws.setJavaScriptEnabled(true);//开启JavaScript支持
    ws.setDomStorageEnabled(true);
    ws.setAllowFileAccessFromFileURLs(true);
    ws.setAppCacheEnabled(true);
    ws.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
    newWebView.setWebChromeClient(new WebChromeClient());
    newWebView.setWebViewClient(new WebViewClient() {
      @Override
      public boolean shouldOverrideUrlLoading(WebView view, String url) {
        return true;
      }
    });
    newWebView.loadUrl(url);
  }

  public void intView() {
    backMenu = (ImageView) findViewById(R.id.back_menu);
    newsReply = (ImageView) findViewById(R.id.news_reply);

    newsShare = (ImageView) findViewById(R.id.news_share);
    newsType = (TextView) findViewById(R.id.news_type);
    supportCount = (TextView) findViewById(R.id.supportCount);
    proBarLine = (RelativeLayout) findViewById(R.id.proBarLine);
    newsScroll = (ScrollView) findViewById(R.id.news_scroll);
    newsTitle = (TextView) findViewById(R.id.news_title);
    newsLogo = (ImageView) findViewById(R.id.news_logo);
    newsDescription = (TextView) findViewById(R.id.news_description);
    newsAuthor = (TextView) findViewById(R.id.news_author);
    newsTime = (TextView) findViewById(R.id.news_time);
    replyCount = (TextView) findViewById(R.id.reply_count);
    newWebView = (WebView) findViewById(R.id.news_web);
    newsContent = (TextView) findViewById(R.id.news_content);
    replyModule = (LinearLayout) findViewById(R.id.reply_module);
    support_button = (LinearLayout) findViewById(R.id.support_button);
    replyBox = (LinearLayout) findViewById(R.id.reply_box);
    news_fast_reply = (LinearLayout) findViewById(R.id.news_fast_reply);
    replyEditText = (EditText) findViewById(R.id.reply_editText);
    edit_disable_text = (TextView) findViewById(R.id.edit_disable_text);
    sendButn = (TextView) findViewById(R.id.sendButn);

    //加载更多按钮
    loadingBar = (LinearLayout) findViewById(R.id.loading_bar);
    loadingBarText = (TextView) findViewById(R.id.load_more_text);
    loadingProBar = (ProgressBar) findViewById(R.id.load_more_pro);
    loadingBar.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        loadingBarText.setVisibility(View.GONE);
        loadingProBar.setVisibility(View.VISIBLE);
        page++;
        setBoolean(true, false);
        newsApi.setHandler(mHandler);
        message = Message.obtain();
        message.what = 0x640;
        mHandler.sendMessage(message);
      }
    });

    backMenu.setOnClickListener(this);
    newsReply.setOnClickListener(this);
    newsShare.setOnClickListener(this);
    sendButn.setOnClickListener(this);
    //supportCount.setOnClickListener(this);
    edit_disable_text.setOnClickListener(this);
    //获取资讯信息
    newsApi.getNewsInfo(newsListInfo.getId());
    //发表回复文本框的事件监听器
    replyEditText.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {

      }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (count != 0) {
          sendButn.setBackgroundResource(R.drawable.forum_enable_btn_send);
          sendButn.setTextColor(Color.WHITE);
        } else {
          sendButn.setBackgroundResource(R.drawable.border);
          sendButn.setTextColor(Color.parseColor("#A9ADB0"));
        }
      }

      @Override
      public void afterTextChanged(Editable s) {
        if (s.length() != 0) {
          sendButn.setBackgroundResource(R.drawable.forum_enable_btn_send);
          sendButn.setTextColor(Color.WHITE);
        } else {
          sendButn.setBackgroundResource(R.drawable.border);
          sendButn.setTextColor(Color.parseColor("#A9ADB0"));
        }
      }
    });
    newsScroll.setOnTouchListener(new View.OnTouchListener() {
      @Override
      public boolean onTouch(View v, MotionEvent event) {
        //触摸屏幕隐藏回复界面
        replyBox.setVisibility(View.GONE);
        InputMethodManager imm =
            (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
          imm.hideSoftInputFromWindow(replyEditText.getWindowToken(),
              InputMethodManager.HIDE_NOT_ALWAYS);
        }
        replyEditText.clearFocus();
        return false;
      }
    });
    newWebView.setFocusable(false);
    newWebView.setFocusableInTouchMode(false);
    WebSettings webSettings = newWebView.getSettings();
    webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);//图片自适应大小
    newWebView.setHorizontalScrollBarEnabled(false);//水平不显示
    newWebView.setVerticalScrollBarEnabled(false); //垂直不显示
    //拦截webView的超链接请求
    newWebView.setWebViewClient(new WebViewClient() {
      public boolean shouldOverrideUrlLoading(WebView view, String url) {
        Log.e("url<><><>", url);
        return true;
      }
    });
  }

  @Override
  public void onClick(View v) {
    int viewId = v.getId();
    switch (viewId) {
      case R.id.back_menu:

        finish();
        break;
      case R.id.supportCount:
        //if (BaseFunction.isLogin()) {
        //  if (bean.getIs_supported().equals("0")) {
        //
        //    supportCount.setText(Integer.parseInt(support) + 1 + "");
        //    supportCount.setBackgroundResource(R.drawable.icon_like_blue_stroke);
        //    //
        //    RsenUrlUtil.execute(NewsDetailActivity.this, RsenUrlUtil
        //        .URL_SUPPORT_QUESTION_ANSWER, new RsenUrlUtil
        // .OnJsonResultListener<DiscoverFragment
        //        .FXBean>() {
        //      @Override
        //      public void onNoNetwork(String msg) {
        //        ToastHelper.showToast(msg, Url.context);
        //      }
        //
        //      @Override
        //      public Map getMap() {
        //        Map map = new HashMap();
        //        map.put("session_id", Url.SESSIONID);
        //        map.put("answerid", newsId);
        //        return map;
        //
        //      }
        //
        //      @Override
        //      public void onParseJsonBean(List<DiscoverFragment.FXBean>
        //                                      beans, JSONObject jsonObject) {
        //        String result = jsonObject.toString();
        //        DiscoverFragment.FXBean discoverInfo = JSON.parseObject
        //            (result, DiscoverFragment.FXBean.class);
        //        beans.add(discoverInfo);
        //      }
        //
        //      @Override
        //      public void onResult(boolean state, List beans) {
        //
        //        if (state) {
        //
        //          ToastHelper.showToast("点赞成功", Url.context);
        //        } else {
        //          ToastHelper.showToast("请求失败", Url.context);
        //        }
        //        //
        //
        //      }
        //    });
        //    //
        //  } else {
        //    ToastHelper.showToast("点赞失败，重复点赞", NewsDetailActivity.this);
        //  }
        //
        //} else {
        //  ToastHelper.showToast("请先登录", NewsDetailActivity.this);
        //}

        break;
      case R.id.news_share:
        Intent intent_Share = new Intent(Intent.ACTION_SEND);
        intent_Share.setType("text/plain");
        intent_Share.putExtra(Intent.EXTRA_SUBJECT, "分享");
        intent_Share.putExtra(Intent.EXTRA_TEXT, "【"
            + newsListInfo.getTitle()
            + "】"
            + newsListInfo.getDescription()
            + Url.USERHEADURL
            + "news/detail_"
            + newsListInfo.getId()
            + ".html"
            + " （来自OpenSNS手机客户端）");//分享内容体
        startActivity(Intent.createChooser(intent_Share, "分享"));//分享选择页面标题
        break;
      case R.id.news_reply://回复按钮
        initFlag(true, false);
        if (!newsApi.getSeesionId().equals("")) {
          replyBox.setVisibility(View.VISIBLE);
          replyEditText.setText("");
          newsApi.openKeyBoard(mContext, replyEditText);
        } else {
          ToastHelper.showToast("请登录后操作", mContext);
        }
        break;
      case R.id.edit_disable_text://回复按钮
        initFlag(true, false);
        if (!newsApi.getSeesionId().equals("")) {
          replyBox.setVisibility(View.VISIBLE);
          replyEditText.setText("");
          newsApi.openKeyBoard(mContext, replyEditText);
        } else {
          ToastHelper.showToast("请登录后操作", mContext);
        }
        break;
      case R.id.sendButn:
        String reply = replyEditText.getText().toString();
        if (reply == null || reply.length() <= 0) {
          Toast.makeText(NewsDetailActivity.this, "回复不能为空哟", Toast.LENGTH_SHORT).show();
          return;
        }
        newsApi.setHandler(handler);
        newsApi.sendReply(newsId, reply);
        break;
      default:
        break;
    }
  }

  private static class MyHandler extends Handler {

    private WeakReference<NewsDetailActivity> mActivityReference;
    private MyJson myJson;
    private ArrayList<NewsReplyInfo> replyInfoList;

    MyHandler(NewsDetailActivity activity) {
      mActivityReference = new WeakReference<NewsDetailActivity>(activity);
    }

    @Override
    public void handleMessage(Message msg) {

      NewsDetailActivity activity = mActivityReference.get();
      myJson = new MyJson();
      if (activity != null) {
        switch (msg.what) {
          case 0x640:
            activity.newsApi.getNewsReply(activity.newsId, activity.page);
            activity.setBoolean(true, false);
            break;
          case 0:
            String result = (String) msg.obj;
            if (!activity.isReply && !activity.isDelReply) {
              activity.newsDetailInfo = myJson.getNewsInfoList(result);
              activity.setNewsDetail(activity.newsDetailInfo);
              activity.message = new Message();
              activity.message.what = 0x640;
              sendMessage(activity.message);
            } else if (activity.isDelReply) {
              activity.replyModule.removeAllViews();
              activity.page = 1;
              activity.setBoolean(true, false);
              activity.message = Message.obtain();
              activity.message.what = 0x640;
              sendMessage(activity.message);
              ToastHelper.showToast("删除成功", activity.mContext);
            } else {
              replyInfoList = myJson.getNewsReplyInfo(result);
              activity.supportCount.setText(activity.newsDetailInfo.getView());
              activity.replyCount.setText(replyInfoList.size() < Integer.parseInt(activity
                  .newsDetailInfo.getComment()) ? String.valueOf(activity.newsDetailInfo
                  .getComment()) : String.valueOf(replyInfoList.size()));
              if (replyInfoList.size() == 0 && activity.replyModule.getChildCount() == 0) {
                activity.loadingBar.setVisibility(View.GONE);
                View nullDateView =
                    View.inflate(activity.mContext, R.layout.issue_detail_listview_probar, null);
                TextView nullTextView = (TextView) nullDateView.findViewById(R.id.issue_reply_load);
                nullDateView.setTag("isNull");
                nullTextView.setText("还没有人评论");
                activity.replyModule.addView(nullDateView);
              } else if (replyInfoList.size() == 0) {
                activity.loadingBarText.setText("暂无更多评论");
                activity.loadingBarText.setVisibility(View.VISIBLE);
                activity.loadingProBar.setVisibility(View.GONE);
              } else {
                for (int i = 0; i < replyInfoList.size(); i++) {
                  activity.replyModule.addView(activity.addReplyView(replyInfoList.get(i)));
                }
                activity.loadingBar.setVisibility(View.VISIBLE);
                activity.loadingBarText.setVisibility(View.VISIBLE);
                activity.loadingProBar.setVisibility(View.GONE);
              }
            }
            break;
          case 800:
            if (activity.isDelReply) {
              String result1 = (String) msg.obj;
              ToastHelper.showToast(myJson.getReturnInfo(result1).getMessage(), activity.mContext);
            }
            break;
          case 404:
            ToastHelper.showToast("网络连接失败", activity.mContext);
            break;
          default:
            break;
        }
      } else {
        Log.d("activity回收了", "回收了");
      }
    }
  }

  /**
   * 添加评论列表
   */
  public View addReplyView(final NewsReplyInfo replyInfo) {
    View replyView = View.inflate(mContext, R.layout.comment_item, null);

    replyView.findViewById(R.id.user_avatar).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        //点击头像跳转信息
        if (replyInfo.getUser() != null) {
          newsApi.goUserInfo(mContext, replyInfo.getUser().getUid());
        }
      }
    });
    replyView.findViewById(R.id.user_name).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        //点击头像跳转信息
        if (replyInfo.getUser() != null) {
          newsApi.goUserInfo(mContext, replyInfo.getUser().getUid());
        }
      }
    });
    //控件赋值
    if (replyInfo.getUser() == null || TextUtils.isEmpty(replyInfo.getUser().getUid())) {
      ((TextView) replyView.findViewById(R.id.user_name)).setText(R.string
          .activity_user_not_exists);
    } else {
      ((TextView) replyView.findViewById(R.id.user_name)).setText(replyInfo.getUser().getNickname
          ());
    }
    String avatar = null == replyInfo.getUser() ? "" : (TextUtils.isEmpty(replyInfo.getUser()
        .getAvatar()) ? "" : replyInfo.getUser().getAvatar().replace("opensns//opensns",
        "opensns"));

    try {
      com.thinksky.utils.imageloader.ImageLoader.loadOptimizedHttpImage(NewsDetailActivity.this,
          avatar).bitmapTransform
          (new CropCircleTransformation(this)).into(
          (ImageView) replyView.findViewById(R.id.user_avatar));
    } catch (Exception e) {

    }

    ((TextView) replyView.findViewById(R.id.time)).setText(replyInfo.getCreate_time());
    ((TextView) replyView.findViewById(R.id.content)).setText(replyInfo.getContent());
    return replyView;
  }

  /**
   * 给资讯页面控件赋值
   *
   * @param newsDetailInfo 资讯信息
   */
  public void setNewsDetail(NewsDetailInfo newsDetailInfo) {
    newsType.setText(TextUtils.isEmpty(newsDetailInfo.getCategory_title()) ? ""
        : newsDetailInfo.getCategory_title());
    newsTitle.setText(newsDetailInfo.getTitle());
    //加载图片
    LoadImageTask task = new LoadImageTask(newsLogo, 540);
    task.execute(newsDetailInfo.getCover());
    taskCollection.add(task);

    newsDescription.setText(Html.fromHtml(newsDetailInfo.getDescription()));
    newsAuthor.setText(newsDetailInfo.getUser().getNickname());
    newsTime.setText(newsDetailInfo.getCreate_time());
    replyCount.setText(newsDetailInfo.getComment());
    newWebView.loadUrl("javascript:$('#content h1').html('" + newsDetailInfo.getTitle() + "')");
    newWebView.loadUrl("javascript:$('#content .edit a').html('" + newsDetailInfo.getUser()
        .getNickname() + "')");
    newWebView.loadUrl("javascript:$('#content .date a').html('" + newsDetailInfo.getCreate_time
        () + "')");
    newWebView.loadUrl("javascript:$('#content article').html('" + newsDetailInfo.getContent_html
        () + "')");
  }


  /**
   * 图片异步下载器
   */
  private class LoadImageTask extends AsyncTask<String, Void, Bitmap> {

    private ImageView imageView;
    private String imageUrl;
    private int imageWidth;

    public LoadImageTask(ImageView imageView, int imageWidth) {
      this.imageView = imageView;
      this.imageWidth = imageWidth;
    }

    @Override
    protected Bitmap doInBackground(String... params) {
      imageUrl = params[0];
      Bitmap bitmap = imageLoader.getBitmapFromMemoryCache(imageUrl);
      if (bitmap == null) {
        bitmap = imageLoader.loadImage(imageUrl, imageWidth);
      }
      return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
      //图片高比宽长1.1倍时采用把图片放于页面中间的方式放置图片，以免图片拉伸过度
      if (bitmap != null) {
        if (bitmap.getHeight() > 1.1 * bitmap.getWidth() || bitmap.getHeight() < 270) {
          imageView.setScaleType(ImageView.ScaleType.CENTER);
        }
      }
      imageView.setImageBitmap(bitmap);
      if (!newsScroll.isShown()) {
        newsScroll.setVisibility(View.VISIBLE);
        proBarLine.setVisibility(View.GONE);
      }
      taskCollection.remove(this);
    }
  }

  /**
   * @param isReply 获取评论数据开关
   * @param isDelReply 删除评论开关
   */
  public void setBoolean(boolean isReply, boolean isDelReply) {
    this.isReply = isReply;
    this.isDelReply = isDelReply;
  }

  @Override
  public void finish() {
    super.finish();
    //解决Activity退出动画无效的问题
    overridePendingTransition(activityCloseEnterAnimation, activityCloseExitAnimation);
  }

  /**
   * 解决Activity退出动画无效的问题
   */
  public void exitAnim() {
    TypedArray activityStyle =
        getTheme().obtainStyledAttributes(new int[]{android.R.attr.windowAnimationStyle});
    int windowAnimationStyleResId = activityStyle.getResourceId(0, 0);
    activityStyle.recycle();
    activityStyle = getTheme().obtainStyledAttributes(windowAnimationStyleResId, new int[]{
        android.R.attr.activityCloseEnterAnimation, android.R.attr.activityCloseExitAnimation
    });
    activityCloseEnterAnimation = activityStyle.getResourceId(0, 0);
    activityCloseExitAnimation = activityStyle.getResourceId(1, 0);
    activityStyle.recycle();
  }

  public void initFlag(boolean reply, boolean replys) {
    REPLY = reply;
    REPLYS = replys;
  }
}
