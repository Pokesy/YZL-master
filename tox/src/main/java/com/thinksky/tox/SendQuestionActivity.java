package com.thinksky.tox;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.thinksky.fragment.QuestionSelectActivity;
import com.thinksky.holder.BaseApplication;
import com.thinksky.holder.BaseBActivity;
import com.thinksky.info.PostInfo;
import com.thinksky.injection.GlobalModule;
import com.thinksky.net.UiRpcSubscriber1;
import com.thinksky.net.rpc.model.BaseModel;
import com.thinksky.net.rpc.model.UploadImageModel;
import com.thinksky.net.rpc.service.AppService;
import com.thinksky.rsen.RBaseAdapter;
import com.thinksky.rsen.RViewHolder;
import com.thinksky.rsen.view.RGridView;
import com.thinksky.serviceinjection.DaggerServiceComponent;
import com.thinksky.serviceinjection.ServiceModule;
import com.thinksky.utils.BitmapUtiles;
import com.thinksky.utils.FileUtiles;
import com.thinksky.utils.ImageUtils;
import com.thinksky.utils.MyJson;
import com.thinksky.utils.ScalingUtil;
import com.thinksky.utils.imageloader.ImageLoader;
import com.tox.BaseApi;
import com.tox.BaseFunction;
import com.tox.ForumApi;
import com.tox.ToastHelper;
import com.tox.TouchHelper;
import com.tox.Url;
import com.tox.WeiboApi;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class SendQuestionActivity extends BaseBActivity implements View.OnClickListener {
  private static final int MAX_IMG_NUM = 9;
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
  private View backBtn;
  private RecyclerView horizontalListView;
  private LinearLayout mAttachLayout, mAttachBtn, mFaceBtn;
  private FrameLayout mPhotoShowLayout;
  private PhotoAdapter photoAdapter;
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

  private HashMap<String, Bitmap> mUploadBitmapCache = new HashMap<>();

  @Inject
  AppService mAppService;

  private CompositeSubscription mSubscriptions = new CompositeSubscription();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    inject();
    setContentView(R.layout.activity_send_question);
    initView();
    initData();
  }

  private void inject() {
    DaggerServiceComponent.builder().serviceModule(new ServiceModule()).globalModule(new
        GlobalModule((BaseApplication) getApplication()))
        .build().inject(this);
  }

  private void initData() {
    if (!isLogin()) {
      ToastHelper.showToast("请登录", SendQuestionActivity.this);
    } else {
      mWealthView.setText(
          getResources().getString(R.string.available_wealth_text, getComponent().loginSession()
              .getUserInfo().getScore()));
    }
  }

  private void initView() {
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
    strs = new ArrayList<>();
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
    horizontalListView = (RecyclerView) findViewById(R.id.HorizontalListView);
    horizontalListView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager
        .HORIZONTAL, false));
    photoAdapter = new PhotoAdapter(this, scrollImg);
    horizontalListView.setAdapter(photoAdapter);
    mAttachBtn = (LinearLayout) findViewById(R.id.Post_send_attachBtn);
    title = (LinearLayout) findViewById(R.id.title);
    attachBtns = (LinearLayout) findViewById(R.id.attachBtns);
    mFaceBtn = (LinearLayout) findViewById(R.id.Post_send_faceBtn);
    backBtn = findViewById(R.id.Post_send_Back);
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
    hideInputWindow(mContentEdit);
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
                  Intent intent3 = new Intent(SendQuestionActivity.this, AlbumListActivity.class);
                  startActivityForResult(intent3, 9);
                  break;
                case 1:
                  Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                  File file = new File(getExternalFilesDir(null)
                      + File.separator
                      + getPackageName()
                      + File.separator
                      + "photos"
                      + File.separator);
                  mTempPhotoName = System.currentTimeMillis() + ".jpg";
                  if (!file.exists()) {
                    file.mkdirs();
                  }
                  File photo = new File(file, mTempPhotoName);
                  Uri u = Uri.fromFile(photo);
                  intent1.putExtra(MediaStore.EXTRA_OUTPUT, u);
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
        if (TextUtils.isEmpty(mTitleEdit.getText())) {
          ToastHelper.showToast("请填写标题", this);
          return;
        }
        if (mTitleEdit.getText().length() < getResources().getInteger(R.integer
            .question_title_min_length)) {
          ToastHelper.showToast("标题数字不能小于" + getResources().getInteger(R.integer
              .question_title_min_length), this);
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
      return wealth <= Integer.parseInt(TextUtils.isEmpty(getComponent().loginSession()
          .getUserInfo().getScore()) ? "0"
          : getComponent().loginSession().getUserInfo().getScore());
    }
  }

  @Override
  public void onBackPressed() {
    super.onBackPressed();
    FileUtiles.DeleteTempFiles(Url.getDeleteFilesPath());
    SendQuestionActivity.this.finish();
  }

  public Bitmap scaleImg(String path, int width, int height) {
    if (TextUtils.isEmpty(path)) {
      return null;
    }
    Bitmap bitmap = ScalingUtil.decodeFile(path, width, height, ScalingUtil
        .ScalingLogic.FIT);
    bitmap = ScalingUtil.createScaledBitmap(bitmap, width, height, ScalingUtil.ScalingLogic.FIT);
    return bitmap;
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    /**
     * 对ShowImageActivity直接返回的
     */

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
              mUploadBitmapCache.put(imgPathList.get(i), scaleImg(imgPathList.get(i), 800, 800));
              imgList.get(img_num).setVisibility(View.VISIBLE);
              imgList.get(img_num).setTag(imgPathList.get(i));
              imgList.get(img_num)
                  .setBackgroundDrawable(
                      new BitmapDrawable(BitmapUtiles.loadBitmap(imgPathList.get(i), 4)));
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
        if (photo_num > MAX_IMG_NUM) {
          Toast.makeText(SendQuestionActivity.this, "不能超过" + MAX_IMG_NUM + "张哟", Toast
              .LENGTH_SHORT).show();
          photo_num = photo_num - imgPathList.size();
          scrollImg.add(img_num, "add");
          return;
        }
        for (int i = 0; i < imgPathList.size(); i++) {
          if (!BaseFunction.isExistsInList(imgPathList.get(i), scrollImg)) {
            if (img_num <= MAX_IMG_NUM) {
              scrollImg.add(imgPathList.get(i));
              mUploadBitmapCache.put(imgPathList.get(i), scaleImg(imgPathList.get(i), 800, 800));
              img_num++;
            }
          }
        }
        photoCount.setText("已选" + img_num + "张，还剩" + (MAX_IMG_NUM - img_num) + "张");
        scrollImg.add(img_num, "add");
        photoAdapter.notifyDataSetChanged();
      }
      /**
       * 表示从拍照的Activity返回
       */
    } else if (requestCode == 1 && resultCode == RESULT_OK) {
      File temFile =
          new File(getExternalFilesDir(null)
              + File.separator
              + getPackageName()
              + File.separator
              + "photos"
              + File.separator + mTempPhotoName);
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
            mUploadBitmapCache.put(temFile.getPath(), scaleImg(temFile.getPath(), 800, 800));
            img_num++;
          }
        }
        photoCount.setText("已选" + img_num + "张，还剩" + (MAX_IMG_NUM - img_num) + "张");
        scrollImg.add(img_num, "add");
        photoAdapter.notifyDataSetChanged();
      }
    } else if (resultCode == RESULT_OK) {
      Bundle b = data.getExtras(); //data为B中回传的Intent
      category_id = b.getString("category_id");//str即为回传的值

      dizhi.setText(b.getString("title"));
    }
    if (scrollImg.size() != 0) {
      scrollImg.remove(scrollImg.size() - 1);
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

  };

  private void uploadImages() {
    attachIds.clear();
    showProgressDialog("发布中,请等待", false);
    for (int i = 0; i < scrollImg.size() - 1; i++) {
      File file;
      if (mUploadBitmapCache.containsKey(scrollImg.get(i))) {
        byte[] bytes = ImageUtils.bitmap2Bytes(mUploadBitmapCache.get(scrollImg.get(i)), 100);
        file = ImageUtils.byte2File(bytes, getExternalFilesDir(null) +
            "uploads", "temp_" + System.currentTimeMillis() + ".jpg");
      } else {
        file = new File(scrollImg.get(i));
      }

      RequestBody requestFile =
          RequestBody.create(MediaType.parse("multipart/form-data"), file);

      MultipartBody.Part body =
          MultipartBody.Part.createFormData("image", file.getName(), requestFile);
      String url = "api.php?s=public/uploadimage&session_id=" + Url.SESSIONID;
      Subscription subscription = mAppService.upload(url, body).subscribeOn(Schedulers.io())
          .observeOn
              (AndroidSchedulers.mainThread())
          .subscribe(new Action1<Response<UploadImageModel>>() {
            @Override
            public void call(Response<UploadImageModel> uploadImageModelResponse) {
              attachIds.add(uploadImageModelResponse.body().getMessage().getId());
              if (attachIds.size() == scrollImg.size() - 1) {
                l = WeiboApi.getAttachIds(attachIds);
                sendWeibo();
              }
            }
          }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
              Toast.makeText(SendQuestionActivity.this, "上传图片失败", Toast.LENGTH_SHORT).show();
              closeProgressDialog();
            }
          }, new Action0() {
            @Override
            public void call() {
              closeProgressDialog();
            }
          });
      mSubscriptions.add(subscription);

    }
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    mSubscriptions.unsubscribe();
    Iterator<Map.Entry<String, Bitmap>> it = mUploadBitmapCache.entrySet().iterator();
    while (it.hasNext()) {
      Map.Entry<String, Bitmap> entry = it.next();
      Bitmap bitmap = entry.getValue();
      if (null != bitmap && !bitmap.isRecycled()) {
        bitmap.recycle();
      }
    }
  }

  private void sendWeibo() {
    showProgressDialog("", false);
    manageRpcCall(mAppService.sendQuestion(session_id, mTitleEdit.getText().toString().trim(),
        mContentEdit.getText().toString().trim(), score.getText().toString().trim(), category_id,
        l), new UiRpcSubscriber1<BaseModel>(this) {


      @Override
      protected void onSuccess(BaseModel baseModel) {
        getComponent().getGlobalBus().post(new QuestionSendEvent());
        finish();
      }

      @Override
      protected void onEnd() {
        closeProgressDialog();
      }
    });

  }

  public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoHolder> {
    private List<String> imgUrl = new ArrayList<String>();
    private Context ctx;

    public PhotoAdapter(Context ctx, List<String> list) {
      this.imgUrl = list;
      this.ctx = ctx;
    }

    @Override
    public PhotoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      View convertView = View.inflate(ctx, R.layout.photo_item, null);
      PhotoHolder holder = new PhotoHolder(convertView);
      holder.imageView = (ImageView) convertView.findViewById(R.id.Photo_item);
      holder.delImg = (ImageView) convertView.findViewById(R.id.delImg);
      return holder;
    }

    @Override
    public void onBindViewHolder(PhotoHolder holder, final int position) {
      //holder.imageView.setTag(imgUrl.get(position));
      holder.delImg.setAlpha(180);

      if (TextUtils.equals((String) holder.imageView.getTag(), imgUrl.get(position))) {
        if (TextUtils.equals(imgUrl.get(position), "add")) {
          holder.imageView.setImageResource(R.drawable.add_post_photo);
          holder.delImg.setVisibility(View.GONE);
        } else {
          ImageLoader.loadOptimizedHttpImage(ctx, imgUrl.get(position)).into(holder.imageView);
          holder.delImg.setVisibility(View.VISIBLE);
        }
      } else {
        if (TextUtils.equals(imgUrl.get(position), "add")) {
          holder.imageView.setImageResource(R.drawable.add_post_photo);
          holder.delImg.setVisibility(View.GONE);
        } else {
          holder.imageView.setTag(imgUrl.get(position));
          ImageLoader.loadOptimizedHttpImage(ctx, imgUrl.get(position)).into(holder.imageView);
          holder.delImg.setVisibility(View.VISIBLE);
        }
      }
      holder.imageView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          if (imgUrl.get(position).equals("add")) {
            if (img_num < MAX_IMG_NUM) {
              String[] items = {"相册", "拍照"};
              AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
              builder.setTitle("操作");
              builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                  switch (which) {
                    case 0:
                      Intent intent3 =
                          new Intent(SendQuestionActivity.this, AlbumListActivity.class);
                      startActivityForResult(intent3, 9);
                      break;
                    case 1:
                      Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                      File file = new File(getExternalFilesDir(null)
                          + File.separator
                          + getPackageName()
                          + File.separator
                          + "photos"
                          + File.separator);
                      mTempPhotoName = System.currentTimeMillis() + ".jpg";
                      if (!file.exists()) {
                        file.mkdirs();
                      }
                      File photo = new File(file, mTempPhotoName);
                      Uri u = Uri.fromFile(photo);
                      intent.putExtra(MediaStore.EXTRA_OUTPUT, u);
                      startActivityForResult(intent, 1);
                      break;
                  }
                }
              });
              builder.setCancelable(true);
              builder.show();
            } else {
              ToastHelper.showToast("最多上传" + MAX_IMG_NUM + "张图片", ctx);
            }
          }
        }
      });
      holder.delImg.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          deleteCell(position);
        }
      });
    }


    @Override
    public int getItemCount() {
      return imgUrl.size();
    }

    class PhotoHolder extends RecyclerView.ViewHolder {
      ImageView imageView, delImg;

      public PhotoHolder(View itemView) {
        super(itemView);
      }
    }
  }

  private void deleteCell(final int index) {
    scrollImg.remove(index);
    img_num--;
    photo_num--;
    photoCount.setText("已选" + img_num + "张，还剩" + (MAX_IMG_NUM - img_num) + "张");
    photoAdapter.notifyDataSetChanged();
  }

  public class QuestionSendEvent {

  }
}
