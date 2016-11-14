package com.thinksky.tox;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.thinksky.net.UiRpcSubscriberSimple;
import com.thinksky.net.rpc.model.UserInfoModel;
import com.thinksky.net.rpc.service.AppService;
import com.thinksky.net.rpc.service.NetConstant;
import com.thinksky.rsen.RsenUrlUtil;
import com.thinksky.serviceinjection.DaggerServiceComponent;
import com.thinksky.serviceinjection.ServiceModule;
import com.thinksky.ui.common.IImageDisplayView;
import com.thinksky.ui.common.ImageDisplayPresenter;
import com.thinksky.utils.FileUtiles;
import com.thinksky.utils.MyJson;
import com.tox.BaseApi;
import com.tox.BaseFunction;
import com.tox.ToastHelper;
import com.tox.Url;
import com.tox.WeiboApi;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import org.json.JSONObject;

public class DiscoverSendActivity extends BaseBActivity implements View.OnClickListener {
  private static final int REQUEST_CODE_LOCATION = 0;

  private static final String TAG = "DiscoverSendActivity";
  private static final String STATE_HIDDEN = "0";
  private static final String STATE_DISPLAY = "1";
  private String mTempPhotoName;
  private EditText mTitleEdit, mContentEdit;

  private View postSendLayout;
  /**
   * 已选择准备上传的图片数量
   */
  private ImageView iv_round;
  /**
   * 用来保存准备选择上传的图片路径
   */
  private MyJson myJson = new MyJson();
  private View backBtn;
  private View title;
  private int fishid = 1;
  private String session_id;
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

    mDisplayPresenter = new ImageDisplayPresenter(this, 3, (IImageDisplayView) findViewById(R.id
        .pic_display_view));

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
    location = (RelativeLayout) findViewById(R.id.location);
    dizhi = (TextView) findViewById(R.id.dizhi);
    iv_round = (ImageView) findViewById(R.id.iv_round);
    baseApi = new BaseApi();
    session_id = baseApi.getSeesionId();
    mContentEdit = (EditText) findViewById(R.id.Post_send_contentEdit);
    mTitleEdit = (EditText) findViewById(R.id.Post_send_titleEdit);
    postSendLayout = findViewById(R.id.post_send);
    title = findViewById(R.id.title);
    mIconView = (ImageView) findViewById(R.id.icon_contact);
    backBtn = findViewById(R.id.Post_send_Back);
    backBtn.setOnClickListener(this);
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
            //展示已上传的图片
            if (!TextUtils.isEmpty(info.getData()) && null != info.getImages()) {
              List<String> ids = Arrays.asList(info.getData().split(","));
              List<String> paths = info.getImages();
              HashMap<String, String> uploadedCache = new HashMap<>();
              int size = Math.min(ids.size(), paths.size());
              for (int i = 0; i < size; i++) {
                uploadedCache.put(ids.get(i), NetConstant.BASE_URL + paths.get(i));
              }
              mDisplayPresenter.setUploadedImages(uploadedCache);
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
      case R.id.post_send:
        if (!BaseFunction.isLogin()) {
          ToastHelper.showToast(R.string.activity_discover_send_info_login, this);
          return;
        }
        if (mDisplayPresenter.getUpLoadImgCount() == 0 && mDisplayPresenter.getUploadedImages()
            .size() == 0) {
          ToastHelper.showToast(R.string.activity_discover_send_info_choose_img, this);
          return;
        } else if (mDisplayPresenter.getUpLoadImgCount() == 0 && mDisplayPresenter
            .getUploadedImages().size() > 0) {
          // 如果没有新上传的图片且已上传的数目大于零，那么直接判断是否能提交
          if (!checkInput()) {
            return;
          }
          commit(mDisplayPresenter.getUploadedImages());
          return;
        }
        if (!checkInput()) {
          return;
        }
        uploadImages();
        break;
      case R.id.Post_send_attachBtn:
        // 弹出对话框选择图片
        mDisplayPresenter.showChooseDialog();
        break;
      case R.id.Post_send_Back:
        DiscoverSendActivity.this.finish();
        break;
      case R.id.location:
        Intent intent4 = new Intent();
        intent4.setClass(DiscoverSendActivity.this, DisLocationActivity.class);
        startActivityForResult(intent4, REQUEST_CODE_LOCATION);
        break;
      default:
        break;
    }
  }

  private boolean checkInput() {
    if (TextUtils.isEmpty(mTitleEdit.getText()) && TextUtils.equals(isfactory, "2")) {
      Toast.makeText(DiscoverSendActivity.this, R.string.activity_discover_send_info_title,
          Toast.LENGTH_LONG).show();
      return false;
    }
    if (TextUtils.equals(isfactory, "2") && !mContentEdit.getText().toString().matches("^" +
        "(13|15|17|18)\\d{9}$")) {
      Toast.makeText(DiscoverSendActivity.this, R.string
          .activity_discover_send_info_number_format, Toast.LENGTH_SHORT).show();
      return false;
    } else if (TextUtils.equals(isfactory, "1") && !mContentEdit.getText().toString()
        .matches("^[0-9a-zA-Z_]+$")) {
      Toast.makeText(DiscoverSendActivity.this, R.string
          .activity_discover_send_info_number_format, Toast.LENGTH_SHORT).show();
      return false;
    }
    return true;
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
    // TODO 判断RequestCode
    if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_LOCATION) {
      Bundle b = data.getExtras(); //data为B中回传的Intent
      address = b.getString("address");//str即为回传的值
      longitude = b.getString("longitude");
      latitude = b.getString("latitude");
      dizhi.setText(address);
    } else {
      mDisplayPresenter.onActivityResult(requestCode, resultCode, data);
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
    showProgressDialog("", true);
    mDisplayPresenter.uploadImg(new ImageDisplayPresenter.UploadCallback() {
      @Override
      public void onUploadCompleted(List<String> ids) {
        commit(ids);
      }

      @Override
      public void onNetInvokeCompleted() {
        closeProgressDialog();
      }
    });
  }

  private void commit(List<String> imgPathList) {
    if (TextUtils.isEmpty(latitude) || TextUtils.isEmpty(longitude)) {
      Toast.makeText(DiscoverSendActivity.this, "请选择地址", Toast.LENGTH_SHORT).show();
      return;
    }
    Map map = new HashMap();
    map.put("session_id", session_id);
    map.put("factory_name", mTitleEdit.getText().toString().trim());
    map.put(TextUtils.equals(isfactory, "2") ? "mobile" : "wechat",
        mContentEdit.getText().toString().trim());
    map.put("data", WeiboApi.getAttachIds(imgPathList));
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

  @Override
  protected void onDestroy() {
    super.onDestroy();
    mDisplayPresenter.onDestroy();
  }

  public class MarkEvent {
    public String isFactory;

    public MarkEvent(String isFactory) {
      this.isFactory = isFactory;
    }
  }
}
