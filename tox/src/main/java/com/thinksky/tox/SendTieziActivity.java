package com.thinksky.tox;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
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
import com.thinksky.holder.BaseApplication;
import com.thinksky.holder.BaseBActivity;
import com.thinksky.injection.GlobalModule;
import com.thinksky.net.UiRpcSubscriber1;
import com.thinksky.net.rpc.model.BaseModel;
import com.thinksky.net.rpc.service.AppService;
import com.thinksky.rsen.view.RGridView;
import com.thinksky.serviceinjection.DaggerServiceComponent;
import com.thinksky.serviceinjection.ServiceModule;
import com.thinksky.utils.BitmapUtiles;
import com.thinksky.utils.FileUtiles;
import com.thinksky.utils.MyJson;
import com.tox.BaseFunction;
import com.tox.ToastHelper;
import com.tox.Url;
import com.tox.WeiboApi;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.kymjs.aframe.ui.widget.HorizontalListView;
import org.kymjs.aframe.utils.FileUtils;
import org.kymjs.kjframe.http.HttpParams;


public class SendTieziActivity extends BaseBActivity implements View.OnClickListener {
  private String mTempPhotoName;
  private EditText mTitleEdit, mContentEdit;

  private ImageView postSendLayout;
  private RGridView gridView;
//    private View mWealthContainer;
  /**
   * 已选择准备上传的图片数量
   */
  private int img_num = 0;

  private List<RelativeLayout> imgLayList = new ArrayList<RelativeLayout>();
  /**
   * 用来保存准备选择上传的图片路径
   */
  private List<String> strs;
  private List<String> scrollImg = new ArrayList<String>();
  private List<ImageView> imgList = new ArrayList<ImageView>();
  private MyJson myJson = new MyJson();
  private List<String> attachIds = new ArrayList<String>();
  //    private TextView score;
  private RelativeLayout backBtn;
  private HorizontalListView horizontalListView;
  private LinearLayout mAttachBtn;
  private FrameLayout mPhotoShowLayout;
  private PhotoAdapter photoAdapter;
  private TextView photoCount;
  private List<String> imgPathList;
  private int photo_num = 0;
  private String l;
  private String group_id;

  @Inject
  AppService mAppService;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    inject();
    setContentView(R.layout.activity_send_tiezi);
    // 创建名/值组列表
    Intent intent = getIntent();
    group_id = intent.getExtras().getString("group_id");
    initView();
//        loadGroupData();
  }

  private void inject() {
    DaggerServiceComponent.builder().globalModule(new GlobalModule((BaseApplication)
        getApplication())).serviceModule(new ServiceModule())
        .build().inject(this);
  }

  private void initView() {
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
//        mWealthContainer = findViewById(R.id.wealth_container);
//        mWealthView = (TextView) findViewById(R.id.available_wealth);
    mTitleEdit = (EditText) findViewById(R.id.Post_send_titleEdit);
    postSendLayout = (ImageView) findViewById(R.id.post_send);
    mPhotoShowLayout = (FrameLayout) findViewById(R.id.Post_photo_layout);
    horizontalListView = (HorizontalListView) findViewById(R.id.HorizontalListView);
    photoAdapter = new PhotoAdapter(this, scrollImg);
    horizontalListView.setAdapter(photoAdapter);
    mAttachBtn = (LinearLayout) findViewById(R.id.Post_send_attachBtn);
    backBtn = (RelativeLayout) findViewById(R.id.Post_send_Back);
    backBtn.setOnClickListener(this);
    mAttachBtn.setOnClickListener(this);
    postSendLayout.setOnClickListener(this);

    photoCount = (TextView) findViewById(R.id.photo_count);
    initList();
  }

  private void initList() {

  }

  @Override
  public void onClick(View v) {
    int id = v.getId();
    hideInputWindow(mContentEdit);
    switch (id) {
      case R.id.Post_send_attachBtn:
        String[] items = {"相册", "拍照"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("操作");
        builder.setItems(items, new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            switch (which) {
              case 0:
                Intent intent3 = new Intent(SendTieziActivity.this, AlbumListActivity.class);
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

        break;
      case R.id.post_send:
        if (scrollImg.size() != 0) {
          if (mTitleEdit.getText().toString().equals("")) {
            ToastHelper.showToast("请填写标题", this);
            return;
          }
          if (BaseFunction.isLogin()) {
            uploadImages();
          } else {
            ToastHelper.showToast("未登录", this);
          }
        } else {
          if (mTitleEdit.getText().toString().equals("")) {
            Toast.makeText(SendTieziActivity.this, "请填写标题", Toast.LENGTH_LONG).show();
            return;
          }

          if (BaseFunction.isLogin()) {
            sendWeibo();
          } else {
            Toast.makeText(SendTieziActivity.this, "未登入", Toast.LENGTH_LONG).show();
          }
        }

        break;
      //case R.id.Post_send_attachBtn:
      //  if (mAttachLayout.isShown()) {
      //    mAttachLayout.setVisibility(View.GONE);
      //    mPhotoShowLayout.setVisibility(View.GONE);
      //  } else {
      //    mAttachLayout.setVisibility(View.VISIBLE);
      //    mPhotoShowLayout.setVisibility(View.GONE);
      //  }
      //  break;

      case R.id.Post_send_Back:
        SendTieziActivity.this.finish();
        break;

      default:
        break;

    }
  }


  @Override
  public void onBackPressed() {
    super.onBackPressed();
    FileUtiles.DeleteTempFiles(Url.getDeleteFilesPath());
    SendTieziActivity.this.finish();
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
              imgList.get(img_num).setVisibility(View.VISIBLE);
              imgList.get(img_num).setTag(imgPathList.get(i));
              imgList.get(img_num).setBackgroundDrawable(new BitmapDrawable(BitmapUtiles
                  .loadBitmap(imgPathList.get(i), 4)));
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
        mPhotoShowLayout.setVisibility(View.VISIBLE);
        if (scrollImg.size() != 0) {
          scrollImg.remove((scrollImg.size() - 1));
        }
        photo_num += imgPathList.size();
        Log.d("photo_num", photo_num + "");
        if (photo_num > 3) {
          Toast.makeText(SendTieziActivity.this, "不能超过3张哟", Toast.LENGTH_SHORT).show();
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
      File temFile = new File(Environment.getExternalStorageDirectory() + "/tox/photos/" +
          mTempPhotoName);
      if (temFile.exists()) {
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


  private void uploadImages() {
    attachIds.clear();
//        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//        progressDialog.setTitle("发布中请等待");
//        progressDialog.setCanceledOnTouchOutside(false);
//        progressDialog.show();
    for (int i = 0; i < scrollImg.size() - 1; i++) {
      kjUpload(scrollImg.get(i));
      AjaxParams params = new AjaxParams();
      try {
        String path1 = scrollImg.get(i);
        //Log.e("原路径", path1);
        String path = BitmapUtiles.getOnlyUploadImgPath(scrollImg.get(i));
        Log.e("压缩后路径", path);

        // Toast.makeText(SendPostActivity.this, "压缩后路径" + path, Toast.LENGTH_LONG).show();
                /*String name=path.substring(path.lastIndexOf("/")+1,path.length());
                File file= FileUtils.getSaveFile(Url.UPLOADTEMPORARYPATH,name);*/
        File file = new File(path1);
        params.put("image", file);
        params.put("image", path1);
        params.put("session_id", Url.SESSIONID);
        FinalHttp fh = new FinalHttp();
        fh.post(Url.UPLOADIMGURL, params, new AjaxCallBack<Object>() {
          @Override
          public void onLoading(long count, long current) {
          }

          @Override
          public void onSuccess(Object o) {
            String s = myJson.getAttachId(o);
            attachIds.add(s);
            Log.e("上传照片成功", o.toString());
            if (attachIds.size() == scrollImg.size() - 1) {
              l = WeiboApi.getAttachIds(attachIds);
              sendWeibo();
            }
          }

          @Override
          public void onFailure(Throwable t, int errorNo, String strMsg) {
            Log.e("上传照片失败", "");
            Toast.makeText(SendTieziActivity.this, "上传照片失败！", Toast.LENGTH_LONG).show();
          }
        });
      } catch (Exception e) {
        Log.e("SendTieziActivity", e.getMessage());
      }
    }
  }

  private void kjUpload(String path) {
    HttpParams params = new HttpParams();

    try {
      params.put("image", FileUtils.getSaveFile(Url.UPLOADTEMPORARYPATH, path.substring(path
          .lastIndexOf("/") + 1, path.length())));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

  }

  private void sendWeibo() {
    showProgressDialog("发布中,请等待", false);
    manageRpcCall(mAppService.sendPost(Url.SESSIONID, String.valueOf(group_id), mTitleEdit
        .getText().toString()
        .trim(), mContentEdit.getText().toString().trim(), l), new UiRpcSubscriber1<BaseModel>
        (this) {


      @Override
      protected void onSuccess(BaseModel baseModel) {
        Toast.makeText(SendTieziActivity.this, R.string.send_post_success, Toast.LENGTH_SHORT)
            .show();
        getComponent().getGlobalBus().post(new GroupPostInfoChangeEvent());
        finish();
      }

      @Override
      protected void onEnd() {
        closeProgressDialog();
      }
    });

  }

  public class PhotoAdapter extends BaseAdapter {
    private List<String> imgUrl = new ArrayList<String>();
    private Context ctx;

    public PhotoAdapter(Context ctx, List<String> list) {
      this.imgUrl = list;
      this.ctx = ctx;
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
          BitmapDrawable bitmapDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable
              .add_post_photo);
          holder.imageView.setImageBitmap(bitmapDrawable.getBitmap());
          holder.delImg.setVisibility(View.GONE);
        } else {
          holder.imageView.setImageBitmap(BitmapUtiles.loadBitmap(imgUrl.get(position), 3));
          holder.delImg.setVisibility(View.VISIBLE);
        }

      } else {
        if (imgUrl.get(position).equals("add")) {
          holder.imageView.setTag(imgUrl.get(position));
          BitmapDrawable bitmapDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable
              .add_post_photo);
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
                      Intent intent3 = new Intent(SendTieziActivity.this, AlbumListActivity.class);
                      startActivityForResult(intent3, 9);
                      break;
                    case 1:
                      Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                      File file = new File(Environment.getExternalStorageDirectory() +
                          "/tox/photos");
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
        if (img_num == 0) {
          mPhotoShowLayout.setVisibility(View.GONE);
        }
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
    TranslateAnimation translateAnimation = new TranslateAnimation(
        Animation.RELATIVE_TO_SELF, 0f,
        Animation.RELATIVE_TO_SELF, 0f,
        Animation.RELATIVE_TO_SELF, 0f,
        Animation.RELATIVE_TO_SELF, 1f);

    if (al != null) {
      translateAnimation.setAnimationListener(al);
    }
    translateAnimation.setDuration(300);
    v.startAnimation(translateAnimation);
  }

  public class GroupPostInfoChangeEvent {

  }
}
