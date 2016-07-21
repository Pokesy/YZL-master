package com.thinksky.tox;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.alibaba.fastjson.JSON;
import com.thinksky.fragment.DiscoverFragment;
import com.thinksky.fragment.QuestionSelectActivity;
import com.thinksky.holder.BaseBActivity;
import com.thinksky.info.PostInfo;
import com.thinksky.rsen.RBaseAdapter;
import com.thinksky.rsen.RViewHolder;
import com.thinksky.rsen.RsenUrlUtil;
import com.thinksky.rsen.view.RGridView;
import com.thinksky.utils.BitmapUtiles;
import com.thinksky.utils.FileUtiles;
import com.thinksky.utils.LoadImg;
import com.thinksky.utils.MyJson;
import com.tox.BaseApi;
import com.tox.BaseFunction;
import com.tox.ForumApi;
import com.tox.ToastHelper;
import com.tox.TouchHelper;
import com.tox.Url;
import com.tox.WeiboApi;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONObject;
import org.kymjs.aframe.bitmap.KJBitmap;
import org.kymjs.aframe.http.KJHttp;
import org.kymjs.aframe.ui.widget.HorizontalListView;
import org.kymjs.aframe.utils.FileUtils;
import org.kymjs.kjframe.http.HttpParams;

public class SendQuestionActivity extends BaseBActivity implements View.OnClickListener {
  private LinearLayout photoLayout;
  private String mTempPhotoName;
  private EditText mTitleEdit, mContentEdit;

  private ImageView postSendLayout;
  private RGridView gridView;
  private View mWealthContainer;
  /**
   * 已选择准备上传的图片数量
   */
  private int img_num = 0;
  private ImageView img1, img2, img3, img4, img5, img6, img7, img8, img9, img10;

  private List<RelativeLayout> imgLayList = new ArrayList<RelativeLayout>();
  private RelativeLayout imgLay1, imgLay2, imgLay3, imgLay4, imgLay5, imgLay6, imgLay7, imgLay8,
      imgLay9;
  private ProgressDialog progressDialog, progressDialog1;
  /**
   * 用来保存准备选择上传的图片路径
   */
  private List<String> strs;
  private List<String> scrollImg = new ArrayList<String>();
  private List<ImageView> imgList = new ArrayList<ImageView>();
  private MyJson myJson = new MyJson();
  private ForumApi forumApi = new ForumApi();
  private List<String> attachIds = new ArrayList<String>();
  private TextView score;
  private RelativeLayout backBtn;
  private HorizontalListView horizontalListView;
  private LinearLayout mAttachLayout, mAttachBtn, mFaceBtn;
  private FrameLayout mPhotoShowLayout;
  private PhotoAdapter photoAdapter;
  private FinalBitmap finalBitmap;
  private KJBitmap kjBitmap;
  private TextView photoCount;
  private List<String> imgPathList;
  private int photo_num = 0;
  private LinearLayout title;
  private LinearLayout attachBtns;
  private LinearLayout score_select;
  private int fishid = 1;
  private BaseApi baseApi;
  private String session_id;
  private String l;
  //    private String longitude;
  //    private String latitude;
  private String category_id;
  private TextView dizhi;
  private TextView mWealthView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_send_question);
    initView();
    initData();
  }

  private void initData() {
    if (null == Url.MYUSERINFO) {
      ToastHelper.showToast("请登录", SendQuestionActivity.this);
    } else {
      mWealthView.setText(
          getResources().getString(R.string.available_wealth_text, Url.MYUSERINFO.getScore()));
    }
  }

  private void initView() {
    kjBitmap = KJBitmap.create();
    finalBitmap = FinalBitmap.create(this);
    //        forumApi.setHandler(hand);
    Intent intent = getIntent();
    //        forumId=intent.getStringExtra("forumId");
    //        address = intent.getStringExtra("address");
    //        longitude = intent.getStringExtra("longitude");
    //        latitude = intent.getStringExtra("latitude");
    photoLayout = (LinearLayout) findViewById(R.id.Post_send_photo);
    //        score_select = (LinearLayout) findViewById(R.id.score_select);
    dizhi = (TextView) findViewById(R.id.dizhi);
    score = (TextView) findViewById(R.id.score);
    photoLayout.setOnClickListener(this);
    photoLayout.setOnTouchListener(new TouchHelper(this, R.drawable.borderradius_postsend + "",
        R.drawable.borderradius_postsend_touched + "", "drawable"));
    baseApi = new BaseApi();
    session_id = baseApi.getSeesionId();
    strs = new ArrayList<String>();
    strs.add("0");
    strs.add("5");
    strs.add("10");
    strs.add("20");
    strs.add("30");
    strs.add("50");
    strs.add("70");
    strs.add("100");

    mContentEdit = (EditText) findViewById(R.id.Post_send_contentEdit);
    gridView = (RGridView) findViewById(R.id.gridView);
    mWealthContainer = findViewById(R.id.wealth_container);
    mWealthView = (TextView) findViewById(R.id.available_wealth);
    mTitleEdit = (EditText) findViewById(R.id.Post_send_titleEdit);
    postSendLayout = (ImageView) findViewById(R.id.post_send);
    mAttachLayout = (LinearLayout) findViewById(R.id.Post_attach_layout);
    mPhotoShowLayout = (FrameLayout) findViewById(R.id.Post_photo_layout);
    horizontalListView = (HorizontalListView) findViewById(R.id.HorizontalListView);
    photoAdapter = new PhotoAdapter(this, finalBitmap, kjBitmap, scrollImg);
    horizontalListView.setAdapter(photoAdapter);
    mAttachBtn = (LinearLayout) findViewById(R.id.Post_send_attachBtn);
    title = (LinearLayout) findViewById(R.id.title);
    attachBtns = (LinearLayout) findViewById(R.id.attachBtns);
    mFaceBtn = (LinearLayout) findViewById(R.id.Post_send_faceBtn);
    backBtn = (RelativeLayout) findViewById(R.id.Post_send_Back);
    score.setOnClickListener(this);
    backBtn.setOnClickListener(this);
    mFaceBtn.setOnClickListener(this);
    mAttachBtn.setOnClickListener(this);
    postSendLayout.setOnClickListener(this);
    dizhi.setOnClickListener(this);

    progressDialog = new ProgressDialog(this);
    photoCount = (TextView) findViewById(R.id.photo_count);
    initList();
  }

  private void initList() {

  }

  @Override
  public void onClick(View v) {
    int id = v.getId();
    switch (id) {
      case R.id.Post_send_photo:
        if (scrollImg.size() != 0) {
          mAttachLayout.setVisibility(View.GONE);
          mPhotoShowLayout.setVisibility(View.VISIBLE);
        } else {
          String[] items = {"相册", "拍照"};
          AlertDialog.Builder builder = new AlertDialog.Builder(this);
          builder.setTitle("操作");
          builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
              switch (which) {
                case 0:
                  Intent intent3 = new Intent(SendQuestionActivity.this, ScanPhotoActivity.class);
                  startActivityForResult(intent3, 9);
                  break;
                case 1:
                  Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                  File file = new File(Environment.getExternalStorageDirectory() + "/tox/photos");
                  mTempPhotoName = System.currentTimeMillis() + ".png";
                  if (!file.exists()) {
                    file.mkdirs();

                    File photo = new File(file, mTempPhotoName);
                    Uri u = Uri.fromFile(photo);
                    intent1.putExtra(MediaStore.EXTRA_OUTPUT, u);
                  } else {

                    File photo = new File(file, mTempPhotoName);
                    Uri u = Uri.fromFile(photo);
                    intent1.putExtra(MediaStore.EXTRA_OUTPUT, u);
                  }
                  startActivityForResult(intent1, 1);
                  break;
              }
            }
          });
          builder.setCancelable(true);
          builder.show();
        }

        break;
      case R.id.post_send:
        if (!BaseFunction.isLogin()) {
          ToastHelper.showToast("未登录", this);
          return;
        }
        if (mTitleEdit.getText().toString().equals("")) {
          ToastHelper.showToast("请填写标题", this);
          return;
        }
        if (TextUtils.isEmpty(score.getText())) {
          ToastHelper.showToast("请设置悬赏金额", this);
          return;
        }
        if (TextUtils.isEmpty(category_id)) {
          ToastHelper.showToast("请选择类型", this);
          return;
        }
        if (scrollImg.size() > 1) {
          uploadImages();
        } else {
          progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
          progressDialog.setTitle("发布中请等待");
          progressDialog.setCanceledOnTouchOutside(false);
          progressDialog.show();
          sendWeibo();
        }

        break;
      case R.id.Post_send_attachBtn:
        if (mAttachLayout.isShown()) {
          mAttachLayout.setVisibility(View.GONE);
          mPhotoShowLayout.setVisibility(View.GONE);
        } else {
          mAttachLayout.setVisibility(View.VISIBLE);
          mPhotoShowLayout.setVisibility(View.GONE);
        }
        break;
      case R.id.score:
        mWealthContainer.setVisibility(View.VISIBLE);
        gridView.setVisibility(View.VISIBLE);
        gridView.setAdapter(new MySubAdapter(SendQuestionActivity.this, strs));
        break;
      case R.id.Post_send_Back:
        SendQuestionActivity.this.finish();
        break;
      case R.id.dizhi:
        Intent intent4 = new Intent();
        intent4.setClass(SendQuestionActivity.this, QuestionSelectActivity.class);
        startActivityForResult(intent4, 0);
        break;
      default:
        break;
    }
  }

  /*成员头像*/
  public class MySubAdapter extends RBaseAdapter<String> {

    public MySubAdapter(Context context, List<String> datas) {
      super(context, datas);
    }

    @Override
    protected int getItemLayoutId(int viewType) {
      return R.layout.activity_send_question_adapter;
    }

    @Override
    protected void onBindView(final RViewHolder holder, final int position, String bean) {

      holder.tV(R.id.title).setText(strs.get(position));
      if (isHighLight(Integer.parseInt(strs.get(position)))) {
        holder.tV(R.id.title).setSelected(true);
        holder.tV(R.id.title).setEnabled(true);
      } else {
        holder.tV(R.id.title).setSelected(false);
        holder.tV(R.id.title).setEnabled(false);
      }
      //holder.tV(R.id.title).setSelected(isHighLight(Integer.parseInt(strs.get(position))));
            /*点击用户头像*/
      holder.v(R.id.item_layout).setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          if (!holder.tV(R.id.title).isEnabled()) {
            return;
          }
          score.setText(strs.get(position));
          mWealthContainer.setVisibility(View.GONE);
          gridView.setVisibility(View.GONE);
        }
      });
    }

    private boolean isHighLight(int wealth) {
      return wealth <= Integer.parseInt(Url.MYUSERINFO.getScore());
    }
  }

  @Override
  public void onBackPressed() {
    super.onBackPressed();
    FileUtiles.DeleteTempFiles(Url.getDeleteFilesPath());
    SendQuestionActivity.this.finish();
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    /**
     * 对ShowImageActivity直接返回的
     */

    if (resultCode == RESULT_OK) {
      Bundle b = data.getExtras(); //data为B中回传的Intent
      category_id = b.getString("category_id");//str即为回传的值

      dizhi.setText(b.getString("title"));
    }
    if (requestCode == 8 && resultCode == 999) {
      Log.e("scroll返回", "");
      imgPathList = data.getStringArrayListExtra("data");
      if (imgPathList.size() <= 0) {
        scrollImg.clear();
        setImageGone(imgList);
        img_num = 0;
      } else {
        scrollImg.clear();
        setImageGone(imgList);
        img_num = 0;
        for (int i = 0; i < imgPathList.size(); i++) {
          if (!BaseFunction.isExistsInList(imgPathList.get(i), scrollImg)) {
            if (img_num <= 3) {
              scrollImg.add(imgPathList.get(i));
              imgList.get(img_num).setVisibility(View.VISIBLE);
              imgList.get(img_num).setTag(imgPathList.get(i));
              imgList.get(img_num)
                  .setBackgroundDrawable(
                      new BitmapDrawable(BitmapUtiles.loadBitmap(imgPathList.get(i), 4)));
                            /*imgList.get(img_num).setImageBitmap(BitmapUtiles.loadBitmap
                            (imgPathList.get(i), 4));
                            imgList.get(img_num).setLayoutParams(new ViewGroup.LayoutParams
                            (ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams
                            .FILL_PARENT));
                            imgList.get(img_num).setMinimumHeight(100);
                            imgList.get(img_num).setMinimumWidth(80);*/
              img_num++;
            }
          }
        }
      }
    }
    /**
     * 表示从ScanPhotoActivity返回
     */
    if (resultCode == 99 && requestCode == 9) {
      List<String> imgPathList = data.getStringArrayListExtra("data");
      Log.e("选图返回", "");
      if (imgPathList.size() > 0) {
        mAttachLayout.setVisibility(View.GONE);
        mPhotoShowLayout.setVisibility(View.VISIBLE);
        if (scrollImg.size() != 0) {
          scrollImg.remove((scrollImg.size() - 1));
        }
        photo_num += imgPathList.size();
        Log.d("photo_num", photo_num + "");
        if (photo_num > 3) {
          Toast.makeText(SendQuestionActivity.this, "不能超过3张哟", Toast.LENGTH_SHORT).show();
          photo_num = photo_num - imgPathList.size();
          scrollImg.add(img_num, "add");
          return;
        }
        for (int i = 0; i < imgPathList.size(); i++) {
          if (!BaseFunction.isExistsInList(imgPathList.get(i), scrollImg)) {
            if (img_num <= 3) {
              Log.d("Andy", img_num + "");
              scrollImg.add(imgPathList.get(i));
              Log.e(">>", scrollImg.get(i));
                           /* imgList.get(img_num).setVisibility(View.VISIBLE);
                            imgLayList.get(getAbleLocation()).setVisibility(View.VISIBLE);
                            imgList.get(getAbleLocation()).setImageBitmap(BitmapUtiles.loadBitmap
                            (imgPathList.get(i), 2));*/
              img_num++;
            }
          }
        }
        photoCount.setText("已选" + img_num + "张，还剩" + (3 - img_num) + "张");
        scrollImg.add(img_num, "add");
        photoAdapter.notifyDataSetChanged();
      }
      /**
       * 表示从拍照的Activity返回
       */
    } else if (requestCode == 1 && resultCode == RESULT_OK) {
      File temFile =
          new File(Environment.getExternalStorageDirectory() + "/tox/photos/" + mTempPhotoName);
      if (temFile.exists()) {
        mAttachLayout.setVisibility(View.GONE);
        mPhotoShowLayout.setVisibility(View.VISIBLE);
        if (scrollImg.size() != 0) {
          scrollImg.remove((scrollImg.size() - 1));
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 3;

        if (!BaseFunction.isExistsInList(temFile.getPath(), scrollImg)) {
          if (img_num <= 3) {

            scrollImg.add(temFile.getPath());
            img_num++;
          }
        }
        photoCount.setText("已选" + img_num + "张，还剩" + (3 - img_num) + "张");
        scrollImg.add(img_num, "add");
        photoAdapter.notifyDataSetChanged();
      }
    }
    if (scrollImg.size() != 0) {
      scrollImg.remove(scrollImg.size() - 1);
      long count = BitmapUtiles.getFileSize(scrollImg);
      scrollImg.add(scrollImg.size(), "add");
    } else {
      scrollImg.add(scrollImg.size(), "add");
    }
  }

  private void setImageGone(List<ImageView> imageLists) {
    for (int i = 0; i < img_num; i++) {
      imageLists.get(i).setVisibility(View.GONE);
      imgLayList.get(i).setVisibility(View.GONE);
    }
  }

  private void ableAddPhoto() {
    if (img_num >= 3) {

    }
  }

  Handler hand = new Handler() {
    public void handleMessage(android.os.Message msg) {
      super.handleMessage(msg);
      if (msg.what == 0) {
        progressDialog.dismiss();
        Log.e("after dismiss", "");
        String result = (String) msg.obj;
        if (result != null) {
          PostInfo postInfo = myJson.getPostInfo(result);
          FileUtiles.DeleteTempFiles(Url.getDeleteFilesPath());
          Url.postInfo = postInfo;
          Url.is2InsertPost = true;
          SendQuestionActivity.this.finish();
        }
      }
    }

    ;
  };

  private void uploadImages() {
    attachIds.clear();
    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    progressDialog.setTitle("发布中请等待");
    progressDialog.setCanceledOnTouchOutside(false);
    progressDialog.show();
    for (int i = 0; i < scrollImg.size() - 1; i++) {
      //            kjUpload(scrollImg.get(i));
      AjaxParams params = new AjaxParams();
      try {
        String path1 = scrollImg.get(i);
        //Log.e("原路径", path1);
        String path = BitmapUtiles.getOnlyUploadImgPath(scrollImg.get(i));
        Log.e("压缩后路径", path);

        // Toast.makeText(SendPostActivity.this, "压缩后路径" + path, Toast.LENGTH_LONG).show();
                /*String name=path.substring(path.lastIndexOf("/")+1,path.length());
                File file= FileUtils.getSaveFile(Url.UPLOADTEMPORARYPATH,name);*/
        File file = new File(BitmapUtiles.getOnlyUploadImgPath(scrollImg.get(i)));
        params.put("image", file);
        params.put("image", path1);
        params.put("session_id", Url.SESSIONID);
        FinalHttp fh = new FinalHttp();
        fh.post(Url.UPLOADIMGURL, params, new AjaxCallBack<Object>() {
          @Override
          public void onLoading(long count, long current) {
            progressDialog.setProgressNumberFormat("%1dKB/%2dKB");
            ;
            progressDialog.setMax((int) count / 1024);
            progressDialog.setProgress((int) (current / 1024));
          }

          @Override
          public void onSuccess(Object o) {
            progressDialog.dismiss();
            String s = myJson.getAttachId(o);
            attachIds.add(s);
            Log.e("上传照片成功", o.toString());
            if (attachIds.size() == scrollImg.size() - 1) {
              //                            progressDialog1.show(SendQuestionActivity.this, "提示",
              // "发布中...", true, false);
              l = WeiboApi.getAttachIds(attachIds);
              sendWeibo();
            }
          }

          @Override
          public void onFailure(Throwable t, int errorNo, String strMsg) {
            Log.e("上传照片失败", "");
            progressDialog.dismiss();
            Toast.makeText(SendQuestionActivity.this, "上传照片失败！", Toast.LENGTH_LONG).show();
          }
        });
      } catch (Exception e) {

      }
    }
  }

  private void kjUpload(String path) {
    HttpParams params = new HttpParams();
    KJHttp kjHttp = new KJHttp();

    try {
      params.put("image", FileUtils.getSaveFile(Url.UPLOADTEMPORARYPATH,
          path.substring(path.lastIndexOf("/") + 1, path.length())));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  private void sendWeibo() {

    //        Map map = new HashMap();
    //        map.put("session_id", session_id);
    //        map.put("title", mTitleEdit.getText().toString().trim());
    //        map.put("content", mContentEdit.getText().toString().trim());
    //        map.put("score", score.getText().toString().trim());
    //        map.put("category_id", category_id);

    RsenUrlUtil.executeNew(this, RsenUrlUtil.URL_SEND_QUESTION,
        new RsenUrlUtil.OnJsonResultListener<DiscoverFragment.FXBean>() {
          @Override
          public void onNoNetwork(String msg) {
            ToastHelper.showToast(msg, Url.context);
          }

          @Override
          public Map getMap() {
            Map map = new HashMap();
            map.put("session_id", session_id);
            map.put("title", mTitleEdit.getText().toString().trim());
            map.put("content", mContentEdit.getText().toString().trim());
            map.put("score", score.getText().toString().trim());
            map.put("category_id", category_id);
            map.put("attach_ids", l);
            return map;
          }

          @Override
          public void onParseJsonBean(List<DiscoverFragment.FXBean> beans, JSONObject jsonObject) {
            String result = jsonObject.toString();
            DiscoverFragment.FXBean discoverInfo =
                JSON.parseObject(result, DiscoverFragment.FXBean.class);
            beans.add(discoverInfo);
          }

          @Override
          public void onResult(boolean state, List beans) {
            progressDialog.dismiss();
            if (state) {
              SendQuestionActivity.this.finish();
              getComponent().getGlobalBus().post(new QuestionSendEvent());
            }
          }

          @Override
          public void onError(int errorCode, String message) {
            super.onError(errorCode, message);
            Toast.makeText(SendQuestionActivity.this, message, Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
          }
        });
  }

  public class PhotoAdapter extends BaseAdapter {
    private KJBitmap kjBitmap;
    private List<String> imgUrl = new ArrayList<String>();
    private Context ctx;
    private FinalBitmap finalBitmap;
    private LoadImg loadImg;

    public PhotoAdapter(Context ctx, FinalBitmap finalBitmap, KJBitmap kjBitmap,
                        List<String> list) {
      this.kjBitmap = kjBitmap;
      this.finalBitmap = finalBitmap;
      this.imgUrl = list;
      this.ctx = ctx;
      loadImg = new LoadImg(ctx);
    }

    @Override
    public int getCount() {
      return imgUrl.size();
    }

    @Override
    public Object getItem(int position) {
      return position;
    }

    @Override
    public long getItemId(int position) {
      return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
      final Holder holder;
      final View view;

      if (convertView == null) {
        holder = new Holder();
        convertView = View.inflate(ctx, R.layout.photo_item, null);
        holder.imageView = (ImageView) convertView.findViewById(R.id.Photo_item);
        holder.imageView.setTag(imgUrl.get(position));
        holder.delImg = (ImageView) convertView.findViewById(R.id.delImg);
        holder.delImg.setAlpha(180);
        convertView.setTag(holder);
      } else {
        holder = (Holder) convertView.getTag();
      }
      view = convertView;

      if (holder.imageView.getTag().equals(imgUrl.get(position))) {
        if (imgUrl.get(position).equals("add")) {
          BitmapDrawable bitmapDrawable =
              (BitmapDrawable) getResources().getDrawable(R.drawable.add_post_photo);
          holder.imageView.setImageBitmap(bitmapDrawable.getBitmap());
          holder.delImg.setVisibility(View.GONE);
        } else {
          holder.imageView.setImageBitmap(BitmapUtiles.loadBitmap(imgUrl.get(position), 3));
          holder.delImg.setVisibility(View.VISIBLE);
        }
      } else {
        if (imgUrl.get(position).equals("add")) {
          holder.imageView.setTag(imgUrl.get(position));
          BitmapDrawable bitmapDrawable =
              (BitmapDrawable) getResources().getDrawable(R.drawable.add_post_photo);
          holder.imageView.setImageBitmap(bitmapDrawable.getBitmap());
          holder.delImg.setVisibility(View.GONE);
        } else {
          holder.imageView.setTag(imgUrl.get(position));
          holder.imageView.setImageBitmap(BitmapUtiles.loadBitmap(imgUrl.get(position), 3));
          holder.delImg.setVisibility(View.VISIBLE);
        }
      }
      holder.imageView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          if (imgUrl.get(position).equals("add")) {
            Log.d("Andy12345", img_num + "");
            Log.d("Andy123456", imgUrl.get(position));
            if (img_num < 3) {
              //ToastHelper.showToast("点击了罗",ctx);
              String[] items = {"相册", "拍照"};
              AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
              builder.setTitle("操作");
              builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                  switch (which) {
                    case 0:
                      Intent intent3 =
                          new Intent(SendQuestionActivity.this, ScanPhotoActivity.class);
                      startActivityForResult(intent3, 9);
                      break;
                    case 1:
                      Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                      File file =
                          new File(Environment.getExternalStorageDirectory() + "/tox/photos");
                      mTempPhotoName = System.currentTimeMillis() + ".png";
                      if (!file.exists()) {
                        file.mkdirs();

                        File photo = new File(file, mTempPhotoName);
                        Uri u = Uri.fromFile(photo);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, u);
                      } else {

                        File photo = new File(file, mTempPhotoName);
                        Uri u = Uri.fromFile(photo);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, u);
                      }
                      startActivityForResult(intent, 1);
                      break;
                  }
                }
              });
              builder.setCancelable(true);
              builder.show();
            } else {
              ToastHelper.showToast("最多上传3张图片", ctx);
            }
          }
        }
      });
      holder.delImg.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          deleteCell(view, position);
        }
      });
      return convertView;
    }

    class Holder {
      ImageView imageView, delImg;
    }
  }

  private void deleteCell(final View v, final int index) {
    Animation.AnimationListener al = new Animation.AnimationListener() {
      @Override
      public void onAnimationEnd(Animation arg0) {
        horizontalListView.scrollTo(0);
        scrollImg.remove(index);
        img_num--;
        photo_num--;
        photoCount.setText("已选" + img_num + "张，还剩" + (3 - img_num) + "张");
        PhotoAdapter.Holder vh = (PhotoAdapter.Holder) v.getTag();
        photoAdapter.notifyDataSetChanged();
      }

      @Override
      public void onAnimationRepeat(Animation animation) {
      }

      @Override
      public void onAnimationStart(Animation animation) {
      }
    };
    collapse(v, al);
  }

  private void collapse(final View v, Animation.AnimationListener al) {
    final int initialHeight = v.getMeasuredHeight();

    Animation anim = new Animation() {
      @Override
      protected void applyTransformation(float interpolatedTime, Transformation t) {
        if (interpolatedTime == 1) {
          v.setVisibility(View.GONE);
        } else {
          v.getLayoutParams().height = initialHeight - (int) (initialHeight * interpolatedTime);
          v.requestLayout();
        }
      }

      @Override
      public boolean willChangeBounds() {
        return true;
      }
    };
    TranslateAnimation translateAnimation =
        new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
            Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 1f);

    if (al != null) {
      translateAnimation.setAnimationListener(al);
    }
    translateAnimation.setDuration(300);
    v.startAnimation(translateAnimation);
  }

  public class QuestionSendEvent {

  }
}
