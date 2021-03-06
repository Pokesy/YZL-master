package com.thinksky.tox;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.thinksky.adapter.DetailListAdapter;
import com.thinksky.holder.BaseBActivity;
import com.thinksky.info.Com2Com;
import com.thinksky.info.WeiboCommentInfo;
import com.thinksky.info.WeiboInfo;
import com.thinksky.myview.MyDetailsListView;
import com.thinksky.redefine.FaceTextView;
import com.thinksky.utils.BitmapUtiles;
import com.thinksky.utils.LoadImg;
import com.thinksky.utils.MyJson;
import com.thinksky.utils.UserUtils;
import com.thinksky.utils.imageloader.ImageLoader;
import com.tox.BaseFunction;
import com.tox.ToastHelper;
import com.tox.TouchHelper;
import com.tox.Url;
import com.tox.WeiboApi;
import java.util.ArrayList;
import java.util.List;

public class WeiboDetailActivity extends BaseBActivity {

  private WeiboInfo Weiboinfo = null;
  private LoadImg loadImg;
  private MyJson myJson = new MyJson();
  private ImageView Detail_Back;
  private ImageView mDetail_UserHead, delete_button;
  private LinearLayout mDetail_Up, mDetail_Share, mDetail__progressBar, mDetail_SendComment,
      mDetail_repostWeibo, mDeleteButton;
  private ProgressBar mComment_ProgressBar;
  private ImageView mDetail_Up_Img;
  private TextView mDetail_Up_text, mDetail_ShareNum, mDetail_CommentsNum, mDetail_repostTime,
      mDetail_repostName, mDetail_repostContent, mDetail_AshameID, mDetail_UserName, mDetail_Ctime,
      mWeiboTop, mWeiboFrom;
  private FaceTextView mDetail_MainText;
  private MyDetailsListView Detail_List;
  private List<Com2Com> mlist = new ArrayList<Com2Com>();
  private List<WeiboCommentInfo> commentInfoList = new ArrayList<WeiboCommentInfo>();
  private DetailListAdapter mAdapter = null;
  private Button ListBottem = null;
  private int mStart = 1;
  private boolean flag = true;
  private boolean upFlag = false;
  private boolean listBottemFlag = true;
  private WeiboApi weiboApi = new WeiboApi();
  private List<ImageView> mImgList = new ArrayList<ImageView>();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.activity_weibo_detail);
    Intent intent = getIntent();
    Bundle bund = intent.getBundleExtra("value");
    Weiboinfo = (WeiboInfo) bund.getSerializable("WeiboInfo");
    Log.e("动态内容", Weiboinfo.getWcontent());
    loadImg = new LoadImg(WeiboDetailActivity.this);
    initView();
    addInformation();
    addImg();
    mAdapter = new DetailListAdapter(WeiboDetailActivity.this, commentInfoList, Weiboinfo);
    ListBottem = new Button(WeiboDetailActivity.this);
    ListBottem.setText("点击加载更多");
    ListBottem.setBackgroundColor(Color.parseColor("#ffffff"));
    ListBottem.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (flag && listBottemFlag) {
          weiboApi.setHandler(hand);
          weiboApi.getWeiboComment(Weiboinfo.getWid(), mStart + "");
          listBottemFlag = false;
        } else if (!listBottemFlag) {
          Toast.makeText(WeiboDetailActivity.this, "正在加载中...", Toast.LENGTH_LONG).show();
        }
      }
    });
    Detail_List.addFooterView(ListBottem, null, false);
    ListBottem.setVisibility(View.GONE);
    Detail_List.setAdapter(mAdapter);
        /*String endParames = Url.COMMENTS + "?weibo_id=" + info.getWid() + "&page="
                + mStart;
		ThreadPoolUtils.execute(new HttpGetThread(hand, endParames));*/
    weiboApi.setHandler(hand);
    weiboApi.getWeiboDetail(Weiboinfo.getWid());
    if (Integer.parseInt(Weiboinfo.getComment_count()) != 0) {

    } else {
      //mDetail__progressBar.setVisibility(View.GONE);
      mComment_ProgressBar = (ProgressBar) findViewById(R.id.Comment_ProgressBar);
      mComment_ProgressBar.setVisibility(View.GONE);
      TextView show = (TextView) findViewById(R.id.TextShowNoComment);
      show.setText("该动态暂无评论");
      //            show.setHeight(40);
    }
  }

  private void initView() {
    MyOnClickListner myOnclick = new MyOnClickListner();
    mDetail_repostContent = (TextView) findViewById(R.id.Detail_Repost_content);
    mDetail_repostName = (TextView) findViewById(R.id.Detail_Repost_name);
    mDetail_repostTime = (TextView) findViewById(R.id.Detail_Repost_time);
    Detail_Back = (ImageView) findViewById(R.id.Detail_Back);
    delete_button = (ImageView) findViewById(R.id.delete_button);
    mDetail_SendComment = (LinearLayout) findViewById(R.id.Detail_SendComment);
    mDetail_AshameID = (TextView) findViewById(R.id.Detail_AshameID);
    mDetail_UserHead = (ImageView) findViewById(R.id.Detail_UserHead);
    mDetail_UserName = (TextView) findViewById(R.id.Detail_UserName);
    mDetail_MainText = (FaceTextView) findViewById(R.id.Detail_MainText);
    mDetail_Up = (LinearLayout) findViewById(R.id.Detail_Up);
    mDetail_Up_Img = (ImageView) findViewById(R.id.Detail_Up_Img);
    mDetail_Up_text = (TextView) findViewById(R.id.Detail_Up_text);
    mDetail_ShareNum = (TextView) findViewById(R.id.Detail_ShareNum);
    mWeiboFrom = (TextView) findViewById(R.id.WeiboDetail_from);

    mDetail_Share = (LinearLayout) findViewById(R.id.Detail_Share);
    mDetail_repostWeibo = (LinearLayout) findViewById(R.id.Detail_RepostWeibo);
    //        mDeleteButton = (LinearLayout) findViewById(R.id.delete_button);
    mWeiboTop = (TextView) findViewById(R.id.Weibo_top);
    Detail_List = (MyDetailsListView) findViewById(R.id.Detail_List);
    mDetail__progressBar = (LinearLayout) findViewById(R.id.Detail__progressBar);
    mDetail_CommentsNum = (TextView) findViewById(R.id.Detail_ComNum);
    mDetail_Ctime = (TextView) findViewById(R.id.Detail_ctime);
    Detail_Back.setOnClickListener(myOnclick);
    if (TextUtils.equals(Url.USERID, Weiboinfo.getUser().getUid())) {
      delete_button.setVisibility(View.VISIBLE);
    }
    delete_button.setOnClickListener(myOnclick);
    mDetail_UserHead.setOnClickListener(myOnclick);
    mDetail_Up.setOnClickListener(myOnclick);
    mDetail_SendComment.setOnClickListener(myOnclick);
    mDetail_Share.setOnClickListener(myOnclick);
    //        mDeleteButton.setOnClickListener(myOnclick);

    //初始化图片
    mImgList.add((ImageView) findViewById(R.id.Detail_MainImg));
    mImgList.add((ImageView) findViewById(R.id.Detail_MainImg1));
    mImgList.add((ImageView) findViewById(R.id.Detail_MainImg2));
    mImgList.add((ImageView) findViewById(R.id.Detail_MainImg3));
    mImgList.add((ImageView) findViewById(R.id.Detail_MainImg4));
    mImgList.add((ImageView) findViewById(R.id.Detail_MainImg5));
    mImgList.add((ImageView) findViewById(R.id.Detail_MainImg6));
    mImgList.add((ImageView) findViewById(R.id.Detail_MainImg7));
    mImgList.add((ImageView) findViewById(R.id.Detail_MainImg8));
    for (ImageView view : mImgList) {
      view.setOnClickListener(new MyOnClickListner());
    }
    if (Weiboinfo.getIs_top() != 1) {
      mWeiboTop.setVisibility(View.GONE);
    }
    mDetail_Up.setOnTouchListener(
        new TouchHelper(WeiboDetailActivity.this, "#ededed", "#ffffff", "color"));
    mDetail_Share.setOnTouchListener(
        new TouchHelper(WeiboDetailActivity.this, "#ededed", "#ffffff", "color"));
    mDetail_SendComment.setOnTouchListener(
        new TouchHelper(WeiboDetailActivity.this, "#ededed", "#ffffff", "color"));
  }

  private class MyOnClickListner implements View.OnClickListener {
    public void onClick(View arg0) {
      int mID = arg0.getId();
      switch (mID) {
        case R.id.Detail_Back:
          WeiboDetailActivity.this.finish();
          break;
        case R.id.Detail_SendComment:
          if (!TextUtils.isEmpty(Url.SESSIONID)) {
            Intent intent = new Intent(WeiboDetailActivity.this, SendCommentActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("weiboInfo", Weiboinfo);
            intent.putExtra("weiboInfo", bundle);
            Url.activityFrom = "weiboDetail";
            intent.putExtra("commentType", 1);
            startActivity(intent);
          } else {
            Intent intent = new Intent(WeiboDetailActivity.this, LoginActivity.class);
            startActivity(intent);
          }
          break;
        case R.id.Detail_Up:
          if (BaseFunction.isLogin()) {
            if (!upFlag) {
              upFlag = true;
              int LikeNum = TextUtils.isEmpty(Weiboinfo.getLikenum()) ? 0 : Integer.parseInt
                  (Weiboinfo.getLikenum());
              mDetail_Up_text.setText((LikeNum + 1) + "");
              mDetail_Up_Img.setImageBitmap(
                  BitmapUtiles.drawableTobitmap(R.drawable.icon_like_blue_stroke,
                      WeiboDetailActivity.this));
              Weiboinfo.setIs_supported(true);
              Weiboinfo.setLikenum(LikeNum + 1 + "");
              support();
            } else {
              ToastHelper.showToast("点赞失败，重复点赞", WeiboDetailActivity.this);
            }
          } else {
            ToastHelper.showToast("请先登录", WeiboDetailActivity.this);
          }

          break;
        case R.id.Detail_Share:
          if (BaseFunction.isLogin()) {
            Intent intent = new Intent(WeiboDetailActivity.this, UploadActivity.class);
            intent.putExtra("weiboType", "repost");
            if (Weiboinfo.getType().equals("repost")) {
              intent.putExtra("weibo_master", Weiboinfo.getUser().getNickname());
              intent.putExtra("weibo_content", Weiboinfo.getWcontent());
              intent.putExtra("weibo_id", Weiboinfo.getWid());
              intent.putExtra("source_id", Weiboinfo.getRepostWeiboInfo().getWid());
            } else {
              intent.putExtra("weibo_id", Weiboinfo.getWid());
              intent.putExtra("source_id", Weiboinfo.getWid());
            }
            startActivity(intent);
          } else {
            ToastHelper.showToast("请先登录", WeiboDetailActivity.this);
          }
          break;
        //删除动态
        case R.id.delete_button:
          if (BaseFunction.isLogin()) {
            if (Weiboinfo.getUser().getUid().equals(Url.USERID)) {
              weiboApi.setHandler(new Handler() {
                @Override
                public void handleMessage(Message msg) {
                  switch (msg.what) {
                    case 0:
                      Url.activityFrom = "DeleteWeiBoActivity";
                      ToastHelper.showToast("删除成功", WeiboDetailActivity.this);
                      getComponent().getGlobalBus().post(new DeleteWeiboEvent());
                      finish();
                      break;
                    case 401:
                      ToastHelper.showToast("操作失败，需要登录", WeiboDetailActivity.this);
                      break;
                    default:
                      break;
                  }
                }
              });
              new AlertDialog.Builder(WeiboDetailActivity.this).setMessage("确定删除动态？")
                  .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                      weiboApi.deleteWeiBo(Weiboinfo.getWid());
                    }
                  })
                  .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                      dialog.dismiss();
                    }
                  })
                  .show();
            } else {
              delete_button.setClickable(false);
              ToastHelper.showToast("没有权限操作", WeiboDetailActivity.this);
            }
          } else {
            ToastHelper.showToast("操作失败，需要登录", WeiboDetailActivity.this);
          }
          break;
        case R.id.Detail_MainImg:
          startPhotoBrowser(0);
          break;
        case R.id.Detail_MainImg1:
          startPhotoBrowser(1);
          break;
        case R.id.Detail_MainImg2:
          startPhotoBrowser(2);
          break;
        case R.id.Detail_MainImg3:
          startPhotoBrowser(3);
          break;
        case R.id.Detail_MainImg4:
          startPhotoBrowser(4);
          break;
        case R.id.Detail_MainImg5:
          startPhotoBrowser(5);
          break;
        case R.id.Detail_MainImg6:
          startPhotoBrowser(6);
          break;
        case R.id.Detail_MainImg7:
          startPhotoBrowser(7);
          break;
        case R.id.Detail_MainImg8:
          startPhotoBrowser(8);
          break;
        case R.id.Detail_UserHead:
          weiboApi.goUserInfo(WeiboDetailActivity.this, Weiboinfo.getUser().getUid());
          break;
        default:
          break;
      }
    }

    private void support() {
      WeiboApi weiboApi1 = new WeiboApi();
      weiboApi1.setHandler(supportHand);
      weiboApi1.supportWeibo(Weiboinfo.getWid());
    }
  }

  Handler supportHand = new Handler() {
    public void handleMessage(Message msg) {
      super.handleMessage(msg);
      if (msg.what == 0) {
        ToastHelper.showToast("点赞成功", WeiboDetailActivity.this);
      } else if (msg.what == 501) {
        ToastHelper.showToast("点赞失败，重复点赞", WeiboDetailActivity.this);
      }
    }
  };

  @SuppressLint("HandlerLeak")
  Handler hand = new Handler() {
    public void handleMessage(Message msg) {
      super.handleMessage(msg);
      if (msg.what == 404) {
        Toast.makeText(WeiboDetailActivity.this, R.string.network_not_normal, Toast.LENGTH_LONG)
            .show();
        listBottemFlag = true;
      } else if (msg.what == 100) {
        Toast.makeText(WeiboDetailActivity.this, R.string.network_not_normal, Toast.LENGTH_LONG)
            .show();
        listBottemFlag = true;
      } else if (msg.what == 0) {
        String result = (String) msg.obj;
        if (result != null) {
          List<WeiboCommentInfo> newList = myJson.getWeiboCommentsList(result);
          Log.e(">>>>>>>>>>", newList.size() + "");
          if (newList != null) {
            if (newList.size() == 10) {
              Detail_List.setVisibility(View.VISIBLE);
              ListBottem.setVisibility(View.VISIBLE);
              mDetail__progressBar.setVisibility(View.GONE);
              mStart += 1;
            } else if (newList.size() == 0) {

              if (mlist.size() == 0) {
                mDetail__progressBar.setVisibility(View.VISIBLE);
              } else {
                mDetail__progressBar.setVisibility(View.GONE);
              }
              ListBottem.setVisibility(View.GONE);
              if (commentInfoList.size() > 0) {
                Toast.makeText(WeiboDetailActivity.this, "没有更多评论...", Toast.LENGTH_LONG).show();
              }
            } else {
              mDetail__progressBar.setVisibility(View.GONE);
              Detail_List.setVisibility(View.VISIBLE);
              ListBottem.setVisibility(View.GONE);
            }
            commentInfoList.addAll(newList);
            listBottemFlag = true;
          } else {
            //mDetail_CommentsNum.setVisibility(View.VISIBLE);
            mDetail__progressBar.setVisibility(View.VISIBLE);
          }
        }
        mAdapter.notifyDataSetChanged();
      }
    }

  };

  @Override
  protected void onResume() {
    super.onResume();
    //评论完后判断是否插入评论列表
    insertComAfterCom();
  }

  private void insertComAfterCom() {
    if (Url.is2InsertWeiboCom) {
      commentInfoList.add(null);
      if (commentInfoList.size() == 1) {
        Detail_List.setVisibility(View.VISIBLE);
        ListBottem.setVisibility(View.GONE);
        mDetail__progressBar.setVisibility(View.GONE);
        commentInfoList.set(0, Url.weiboCommentInfo);
      } else {

        for (int i = commentInfoList.size() - 1; i > 0; i--) {
          commentInfoList.set(i, commentInfoList.get(i - 1));
        }
        commentInfoList.set(0, Url.weiboCommentInfo);
      }
      mDetail_CommentsNum.setText(String.valueOf(commentInfoList.size()));
      Url.is2InsertWeiboCom = false;

      mAdapter.notifyDataSetChanged();
    }
  }

  private void addImg() {
    ImageLoader.loadOptimizedHttpImage(WeiboDetailActivity.this, TextUtils.isEmpty(Weiboinfo
        .getUser().getAvatar()) ? Weiboinfo.getUser().getAvatar64()
        : Weiboinfo.getUser().getAvatar()).placeholder(R.drawable.side_user_avatar).dontAnimate()
        .into(mDetail_UserHead);

    if (null != Weiboinfo.getImgList() && Weiboinfo.getImgList().size() > 0) {
      mDetail_repostWeibo.setVisibility(View.VISIBLE);
      int imageCount = Weiboinfo.getImgList().size();
      if (imageCount > 9) {
        imageCount = 9;
      }
      for (int i = 0; i < imageCount; i++) {
        ImageLoader.loadOptimizedHttpImage(WeiboDetailActivity.this, Weiboinfo.getImgList().get
            (i)).placeholder(R.drawable.picture_no).error(R.drawable.picture_no).into(mImgList
            .get(i));
        mImgList.get(i).setVisibility(View.VISIBLE);
        loadWeiboImg(mImgList.get(i), Url.IMAGE + Weiboinfo.getImgList().get(i));
      }
    } else {
      mDetail_repostWeibo.setVisibility(View.GONE);
    }
  }

  private void loadWeiboImg(final ImageView weiboImgView, String url) {
    ImageLoader.loadOptimizedHttpImage(WeiboDetailActivity.this, url)
        .placeholder(R.drawable.picture_no).into(weiboImgView);
  }

  //初始化动态的详情页的基本信息
  private void addInformation() {
    //mDetail_AshameID.setText("动态ID" + Weiboinfo.getWid());
    mDetail_UserName.setText(UserUtils.getUserName(this, Weiboinfo.getUser().getUid(), Weiboinfo
        .getUser().getNickname()));
    mDetail_MainText.setFaceText(Weiboinfo.getWcontent());
    mDetail_CommentsNum.setText(Weiboinfo.getComment_count());
    mDetail_Up_text.setText(Weiboinfo.getLikenum());
    if (Weiboinfo.getIs_supported()) {
      mDetail_Up_Img.setImageBitmap(
          BitmapUtiles.drawableTobitmap(R.drawable.icon_like_blue_stroke, WeiboDetailActivity
              .this));
      upFlag = true;
    }
    if (TextUtils.isEmpty(Weiboinfo.getFrom())) {
      mWeiboFrom.setText("来自:网站端");
    } else {
      mWeiboFrom.setText("来自:" + Weiboinfo.getFrom());
    }

    mDetail_ShareNum.setText(Weiboinfo.getRepost_count());
    mDetail_Ctime.setText(Weiboinfo.getCtime());
    if (Weiboinfo.getType().equals("repost")) {
      if (null == Weiboinfo.getRepostWeiboInfo() || Weiboinfo.getRepostWeiboInfo().getUser() == null) {
        mDetail_repostWeibo.setVisibility(View.VISIBLE);
        mDetail_repostWeibo.setBackgroundColor(Color.parseColor("#F7F7F7"));
        mDetail_repostName.setText(" ");
        mDetail_repostName.setVisibility(View.VISIBLE);
        mDetail_repostName.setTextColor(getResources().getColor(R.color.repostName));
        mDetail_repostContent.setText("" + "   原动态已删除");
        mDetail_repostContent.setVisibility(View.VISIBLE);
        mDetail_repostTime.setText("");
        mDetail_repostTime.setVisibility(View.VISIBLE);
      } else {
        mDetail_repostWeibo.setVisibility(View.VISIBLE);
        mDetail_repostWeibo.setBackgroundColor(Color.parseColor("#F7F7F7"));
        mDetail_repostWeibo.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            Intent intent = new Intent(WeiboDetailActivity.this, WeiboDetailActivity.class);
            Bundle bund = new Bundle();
            //                        Log.e("被点击啦啦啦啦啦", arg0 + "");
            bund.putSerializable("WeiboInfo", Weiboinfo.getRepostWeiboInfo());
            intent.putExtra("value", bund);
            startActivity(intent);
          }
        });
        mDetail_repostName.setText(
            "@" + Weiboinfo.getRepostWeiboInfo().getUser().getNickname() + ":");
        mDetail_repostName.setVisibility(View.VISIBLE);
        mDetail_repostName.setTextColor(getResources().getColor(R.color.repostName));
        mDetail_repostContent.setText(Weiboinfo.getRepostWeiboInfo().getWcontent());
        mDetail_repostContent.setVisibility(View.VISIBLE);
        mDetail_repostTime.setText(Weiboinfo.getRepostWeiboInfo().getCtime());
        mDetail_repostTime.setVisibility(View.VISIBLE);
        if (TextUtils.equals(Weiboinfo.getRepostWeiboInfo().getType(), "image")) {
          for (int i = 0; i < Weiboinfo.getRepostWeiboInfo().getImgList().size(); i++) {
            ImageLoader.loadOptimizedHttpImage(WeiboDetailActivity.this, Weiboinfo
                .getRepostWeiboInfo().getImgList().get(i))
                .placeholder(R.drawable.picture_no).into(mImgList.get(i));
          }
        }
      }
    }
  }

  private void startPhotoBrowser(int index) {
    if (TextUtils.equals(Weiboinfo.getType(), "image")) {
      List list = new ArrayList();
      Intent intent = new Intent(WeiboDetailActivity.this, ImagePagerActivity.class);
      Bundle bundle = new Bundle();
      for (int i = 0; i < Weiboinfo.getImgList().size(); i++) {
        String str = Weiboinfo.getImgList().get(i);
        if (str.startsWith("http")) {
          list.add(str.substring(0, str.lastIndexOf("/") - 8));
        } else {
          list.add(Url.IMAGE + str);
        }
      }
      bundle.putStringArrayList("image_urls", (ArrayList<String>) list);
      //            bundle.putStringArrayList("image_urls", (ArrayList<String>) Weiboinfo
      // .getImgList());

      bundle.putInt("image_index", index);
      intent.putExtras(bundle);
      startActivity(intent);
    }
    if (Weiboinfo.getType().equalsIgnoreCase("repost")) {
      Intent intent = new Intent(WeiboDetailActivity.this, ImagePagerActivity.class);
      Bundle bundle = new Bundle();
      bundle.putStringArrayList("image_urls",
          (ArrayList<String>) Weiboinfo.getRepostWeiboInfo().getImgList());
      bundle.putInt("image_index", index);
      intent.putExtras(bundle);
      startActivity(intent);
    }
  }

  public class DeleteWeiboEvent {

  }
}
