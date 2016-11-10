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
import android.os.Handler;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.alibaba.fastjson.JSON;
import com.thinksky.fragment.DisLocationActivity;
import com.thinksky.fragment.DiscoverFragment;
import com.thinksky.holder.BaseApplication;
import com.thinksky.holder.BaseBActivity;
import com.thinksky.info.PostInfo;
import com.thinksky.injection.GlobalModule;
import com.thinksky.log.Logger;
import com.thinksky.net.UiRpcSubscriberSimple;
import com.thinksky.net.rpc.model.UserInfoModel;
import com.thinksky.net.rpc.service.AppService;
import com.thinksky.rsen.RsenUrlUtil;
import com.thinksky.serviceinjection.DaggerServiceComponent;
import com.thinksky.serviceinjection.ServiceModule;
import com.thinksky.ui.common.ImageDisplayPresenter;
import com.thinksky.utils.BitmapUtiles;
import com.thinksky.utils.FileUtiles;
import com.thinksky.utils.MyJson;
import com.tox.BaseApi;
import com.tox.BaseFunction;
import com.tox.ToastHelper;
import com.tox.Url;
import com.tox.WeiboApi;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONObject;
import org.kymjs.aframe.utils.FileUtils;
import org.kymjs.kjframe.http.HttpParams;

public class DiscoverSendActivity extends BaseBActivity implements View.OnClickListener {
  private static final String TAG = "DiscoverSendActivity";
  private static final String STATE_HIDDEN = "0";
  private static final String STATE_DISPLAY = "1";
  private LinearLayout photoLayout;
  private String mTempPhotoName;
  private EditText mTitleEdit, mContentEdit;

  private RelativeLayout postSendLayout;
  /**
   * 已选择准备上传的图片数量
   */
  private int img_num = 0;
  private ImageView iv_round;
  private List<RelativeLayout> imgLayList = new ArrayList<RelativeLayout>();
  /**
   * 用来保存准备选择上传的图片路径
   */
  private List<String> scrollImg = new ArrayList<String>();
  private List<ImageView> imgList = new ArrayList<ImageView>();
  private MyJson myJson = new MyJson();
  private List<String> attachIds = new ArrayList<String>();
  private RelativeLayout backBtn;
  private LinearLayout mAttachLayout, mAttachBtn, mFaceBtn;
  private FrameLayout mPhotoShowLayout;
  private PhotoAdapter photoAdapter;
  private TextView photoCount;
  private List<String> imgPathList;
  private int photo_num = 0;
  private LinearLayout title;
  private int fishid = 1;
  private String session_id;
  private String userUid;
  private BaseApi baseApi;
  private String l;
  private RelativeLayout location;
  private String longitude;
  private String latitude;
  private String address;
  private TextView dizhi;
  private String isfactory = "2";
  private ImageView mTogBtn;
  private String isdisplay = "1";
  private ImageView mIconView;

  @Inject
  AppService mAppService;
  private ImageDisplayPresenter mDisplayPresenter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    inject();
    setContentView(R.layout.activity_send_discover);
    initView();
  }

  private void inject() {
    DaggerServiceComponent.builder().globalModule(new GlobalModule((BaseApplication)
        getApplication())).serviceModule(new ServiceModule()).build()
        .inject(this);
  }

  private void initView() {
    mTogBtn = (ImageView) findViewById(R.id.mTogBtn); // 获取到控件
    // 添加监听事件
    mTogBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        mTogBtn.setSelected(!mTogBtn.isSelected());
        if (mTogBtn.isSelected()) {
          isdisplay = STATE_HIDDEN;
        } else {
          isdisplay = STATE_DISPLAY;
        }
      }
    });
    photoLayout = (LinearLayout) findViewById(R.id.Post_send_photo);
    location = (RelativeLayout) findViewById(R.id.location);
    dizhi = (TextView) findViewById(R.id.dizhi);
    iv_round = (ImageView) findViewById(R.id.iv_round);
    photoLayout.setOnClickListener(this);
    baseApi = new BaseApi();
    session_id = baseApi.getSeesionId();
    userUid = baseApi.getUid();
    mContentEdit = (EditText) findViewById(R.id.Post_send_contentEdit);
    mTitleEdit = (EditText) findViewById(R.id.Post_send_titleEdit);
    postSendLayout = (RelativeLayout) findViewById(R.id.post_send);
    mAttachLayout = (LinearLayout) findViewById(R.id.Post_attach_layout);
    mPhotoShowLayout = (FrameLayout) findViewById(R.id.Post_photo_layout);
    photoAdapter = new PhotoAdapter(this, scrollImg);
    mAttachBtn = (LinearLayout) findViewById(R.id.Post_send_attachBtn);
    title = (LinearLayout) findViewById(R.id.title);
    mIconView = (ImageView) findViewById(R.id.icon_contact);
    mFaceBtn = (LinearLayout) findViewById(R.id.Post_send_faceBtn);
    backBtn = (RelativeLayout) findViewById(R.id.Post_send_Back);
    backBtn.setOnClickListener(this);
    mFaceBtn.setOnClickListener(this);
    mAttachBtn.setOnClickListener(this);
    postSendLayout.setOnClickListener(this);
    location.setOnClickListener(this);
    Intent intent = getIntent();
    fishid = intent.getIntExtra("fish", 0);
    //fishid = intent.getIntExtra("fish", 0);
    if (intent.getIntExtra("fish", 0) == 0) {
      iv_round.setImageResource(R.drawable.yuyou_1);
      title.setVisibility(View.GONE);
      mIconView.setImageResource(R.drawable.weixin);
      mContentEdit.setHint("请输入微信号");
      isfactory = "1";
    } else {
      iv_round.setImageResource(R.drawable.yuchang_1);
      mIconView.setImageResource(R.drawable.phone);
      mContentEdit.setHint("请输入手机号");
    }
    photoCount = (TextView) findViewById(R.id.photo_count);
    initList();
    address = intent.getStringExtra("address");
    longitude = intent.getStringExtra("longitude");
    latitude = intent.getStringExtra("latitude");
  }

  private void initList() {
    manageRpcCall(mAppService.getProfile(Url.USERID, Url.SESSIONID), new
        UiRpcSubscriberSimple<UserInfoModel>
            (DiscoverSendActivity.this) {


          @Override
          protected void onSuccess(UserInfoModel info) {
            if (!TextUtils.isEmpty(info.getAddress())) {
              dizhi.setText(info.getAddress());
              address = info.getAddress();
            }
            if (!TextUtils.isEmpty(info.getLatitude())) {
              latitude = info.getLatitude();
            }
            if (!TextUtils.isEmpty(info.getLongitude())) {
              longitude = info.getLongitude();
            }
            if (!TextUtils.isEmpty(info.getMobile1()) && TextUtils.equals(isfactory, "2")) {
              mContentEdit.setText(info.getMobile1());
            }
            if (!TextUtils.isEmpty(info.getWechat()) && TextUtils.equals(isfactory, "1")) {
              mContentEdit.setText(info.getWechat());
            }
            mTogBtn.setSelected(TextUtils.equals(info.getIsdisplay(), STATE_HIDDEN));
            if (!TextUtils.isEmpty(info.getFactory_name())) {
              mTitleEdit.setText(info.getFactory_name());
            }
          }

          @Override
          protected void onEnd() {

          }
        });
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
                  Intent intent3 = new Intent(DiscoverSendActivity.this, ScanPhotoActivity.class);
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
        if (scrollImg.size() != 0) {
          if (mTitleEdit.getText().toString().equals("") && fishid == 1) {
            ToastHelper.showToast("请填写标题", this);
            return;
          }
          if (photo_num == 0) {
            ToastHelper.showToast("请选择图片", this);
            return;
          }
          if (BaseFunction.isLogin()) {
            uploadImages();
          } else {
            ToastHelper.showToast("未登录", this);
            return;
          }
        } else {
          if (mTitleEdit.getText().toString().equals("") && fishid == 1) {
            Toast.makeText(DiscoverSendActivity.this, "请填写标题", Toast.LENGTH_LONG).show();
            return;
          }
          if (TextUtils.equals(isfactory, "2") && !mContentEdit.getText().toString().matches("^" +
              "(13|15|17|18)\\d{9}$")) {
            Toast.makeText(DiscoverSendActivity.this, "号码填写不正确", Toast.LENGTH_SHORT).show();
            return;
          } else if (TextUtils.equals(isfactory, "1") && !mContentEdit.getText().toString()
              .matches("^[0-9a-zA-Z_]+$")) {
            Toast.makeText(DiscoverSendActivity.this, "号码填写不正确", Toast.LENGTH_SHORT).show();
            return;
          }
          if (BaseFunction.isLogin()) {

            commit();
          } else {
            Toast.makeText(DiscoverSendActivity.this, "未登入", Toast.LENGTH_LONG).show();
            return;
          }
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
      case R.id.Post_send_faceBtn:
        break;
      case R.id.Post_send_Back:
        DiscoverSendActivity.this.finish();
        break;
      case R.id.location:
        Intent intent4 = new Intent();
        intent4.setClass(DiscoverSendActivity.this, DisLocationActivity.class);
        startActivityForResult(intent4, 0);
        break;
      default:
        break;
    }
  }

  protected void onActivityResult(int resultCode, Intent data) {
    switch (resultCode) { //resultCode为回传的标记，我在B中回传的是RESULT_OK
      case RESULT_OK:
        Bundle b = data.getExtras(); //data为B中回传的Intent
        address = b.getString("address");//str即为回传的值
        longitude = b.getString("longitude");
        latitude = b.getString("latitude");
        dizhi.setText(address);
        break;
      default:
        break;
    }
  }

  @Override
  public void onBackPressed() {
    super.onBackPressed();
    FileUtiles.DeleteTempFiles(Url.getDeleteFilesPath());
    DiscoverSendActivity.this.finish();
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    /**
     * 对ShowImageActivity直接返回的
     */

    if (resultCode == RESULT_OK) {
      Bundle b = data.getExtras(); //data为B中回传的Intent
      address = b.getString("address");//str即为回传的值
      longitude = b.getString("longitude");
      latitude = b.getString("latitude");
      dizhi.setText(address);
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
                      new BitmapDrawable(BitmapUtiles.loadBitmap(imgPathList.get(i), 3)));
              img_num++;
            }
          }
        }
      }
    }
    /**
     *  示从ScanPhotoActivity返回
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
        if (photo_num > 4) {
          Toast.makeText(DiscoverSendActivity.this, "不能超过3张哟", Toast.LENGTH_SHORT).show();
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
              img_num++;
            }
          }
        }
        photoCount.setText("已选" + img_num + "张，还剩" + (3 - img_num) + "张");
        scrollImg.add(img_num, "add");
        photoAdapter.notifyDataSetChanged();
      }
      /**
       *  示从拍照的Activity返回
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

  Handler hand = new Handler() {
    public void handleMessage(android.os.Message msg) {
      super.handleMessage(msg);
      if (msg.what == 0) {
        String result = (String) msg.obj;
        if (result != null) {
          PostInfo postInfo = myJson.getPostInfo(result);
          FileUtiles.DeleteTempFiles(Url.getDeleteFilesPath());
          Url.postInfo = postInfo;
          Url.is2InsertPost = true;
          DiscoverSendActivity.this.finish();
        }
      }
    }

  };

  private void uploadImages() {
    attachIds.clear();
    for (int i = 0; i < scrollImg.size() - 1; i++) {
      kjUpload(scrollImg.get(i));
      AjaxParams params = new AjaxParams();
      try {
        String path1 = scrollImg.get(i);
        //Log.e("原路径", path1);
        String path = BitmapUtiles.getOnlyUploadImgPath(scrollImg.get(i));
        Log.e("压缩后路径", path);

        File file = new File(BitmapUtiles.getOnlyUploadImgPath(scrollImg.get(i)));
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
              //progressDialog1.show(UploadActivity.this,"提示","发布中...",true,false);
              l = WeiboApi.getAttachIds(attachIds);
              commit();
            }
          }

          @Override
          public void onFailure(Throwable t, int errorNo, String strMsg) {
            Log.e("上传照片失败", "");
            Toast.makeText(DiscoverSendActivity.this, "上传照片失败！", Toast.LENGTH_LONG).show();
          }
        });
      } catch (Exception e) {
        Logger.e(TAG, e.getMessage());
      }
    }
  }

  private void kjUpload(String path) {
    HttpParams params = new HttpParams();

    try {
      params.put("image", FileUtils.getSaveFile(Url.UPLOADTEMPORARYPATH,
          path.substring(path.lastIndexOf("/") + 1, path.length())));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  private void commit() {
    if (TextUtils.isEmpty(latitude) || TextUtils.isEmpty(longitude)) {
      Toast.makeText(DiscoverSendActivity.this, "请选择地址", Toast.LENGTH_SHORT).show();
      return;
    }
    Map map = new HashMap();
    map.put("session_id", session_id);
    map.put("factory_name", mTitleEdit.getText().toString().trim());
    map.put(TextUtils.equals(isfactory, "2") ? "mobile" : "wechat",
        mContentEdit.getText().toString().trim());
    map.put("data", l);
    map.put("latitude", latitude);
    map.put("longitude", longitude);
    map.put("address", address);

    map.put("isfactory", isfactory);
    map.put("isdisplay", isdisplay);
    showProgressDialog("", true);
    RsenUrlUtil.executeGetWidthMap(this, RsenUrlUtil.SENDDISCOVER, map,
        new RsenUrlUtil.OnJsonResultListener<DiscoverFragment.FXBean>() {
          @Override
          public void onNoNetwork(String msg) {
            ToastHelper.showToast(msg, Url.context);
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
            closeProgressDialog();
            if (state) {
              getComponent().getGlobalBus().post(new MarkEvent(isfactory));
              ToastHelper.showToast("编辑成功", DiscoverSendActivity.this);
              setResult(RESULT_OK);
              DiscoverSendActivity.this.finish();
            } else {
              ToastHelper.showToast("编辑失败", DiscoverSendActivity.this);
            }
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
                          new Intent(DiscoverSendActivity.this, ScanPhotoActivity.class);
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
      return convertView;
    }

    class Holder {
      ImageView imageView, delImg;
    }
  }


  public class MarkEvent {
    public String isFactory;

    public MarkEvent(String isFactory) {
      this.isFactory = isFactory;
    }
  }
}
