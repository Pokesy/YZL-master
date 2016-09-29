/*
 * 文件名: AvatarChoosePresenter
 * 版    权：  Copyright Hengrtech Tech. Co. Ltd. All Rights Reserved.
 * 描    述: [该类的简要描述]
 * 创建人: zhaozeyang
 * 创建时间:16/5/23
 * 
 * 修改人：
 * 修改时间:
 * 修改内容：[修改内容]
 */
package com.thinksky.ui.profile;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.widget.Toast;
import com.thinksky.holder.BaseApplication;
import com.thinksky.injection.GlobalModule;
import com.thinksky.net.UiRpcSubscriberSimple;
import com.thinksky.net.rpc.model.UploadModel;
import com.thinksky.net.rpc.model.UserInfoModel;
import com.thinksky.net.rpc.service.NetConstant;
import com.thinksky.net.rpc.service.UserService;
import com.thinksky.serviceinjection.DaggerServiceComponent;
import com.thinksky.serviceinjection.ServiceModule;
import com.thinksky.tox.R;
import com.thinksky.utils.DeviceUtils;
import com.thinksky.utils.ImageUtils;
import com.thinksky.utils.ScalingUtil;
import com.tox.Url;
import java.io.File;
import javax.inject.Inject;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * 图片选择<BR>
 *
 * @author zhaozeyang
 * @version [Taobei Client V20160411, 16/5/23]
 */
public class AvatarChoosePresenter {
  public static final int REQUEST_CODE_CAPTURE = 0;
  public static final int REQUEST_CODE_ALBUM = 1;
  private static final int INDEX_CAPTURE = 0;
  private static final int INDEX_ALBUM = 1;

  private Activity mContext;
  private Bitmap mBitmap;

  CompositeSubscription mSubscriptions = new CompositeSubscription();

  @Inject
  UserService mUserService;
  /**
   * 图片类型
   */
  private static final String IMAGE_UNSPECIFIED = "image/*";
  /**
   * 拍照保存路径
   */
  private static final String SAVE_FOLDER = "images";

  private static final String AVATAR_FOLDER = "/avatar/";
  private static final String FILE_NAME = "temp.jpg";

  private String mCurrentPicPath;

  private AlertDialog mChooseAvatarDialog;

  public AvatarChoosePresenter(Activity context) {
    this.mContext = context;
    inject();
  }

  private void inject() {
    DaggerServiceComponent.builder().globalModule(new GlobalModule((BaseApplication) mContext
        .getApplication())).serviceModule(new ServiceModule()).build().inject(this);
  }

  public void showChooseAvatarDialog() {
    String[] items = getResources().getStringArray(R.array.avatar_choose_item);
    if (null == mChooseAvatarDialog) {
      mChooseAvatarDialog = new AlertDialog.Builder(mContext).setItems(items, new DialogInterface
          .OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
          Intent intent;
          switch (which) {
            case INDEX_CAPTURE:
              File savedFile = getSaveFile();
              if (savedFile == null) {
                //showShortToast(R.string.no_sd_card);
                return;
              }
              Uri imageUri = Uri.fromFile(savedFile);
              intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
              intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);

              try {
                mContext.startActivityForResult(intent, REQUEST_CODE_CAPTURE);
              } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(mContext, R.string.permission_info_camara, Toast.LENGTH_SHORT).show();
              }
              break;
            case INDEX_ALBUM:
              intent = new Intent(Intent.ACTION_PICK, null);
              intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                  IMAGE_UNSPECIFIED);
              mContext.startActivityForResult(intent, REQUEST_CODE_ALBUM);
              break;
          }
          dialog.dismiss();
        }
      }).create();
    }
    if (mChooseAvatarDialog.isShowing()) {
      return;
    }
    mChooseAvatarDialog.show();
  }

  private File getSaveFile() {
    //SDk卡不存在
    if (!DeviceUtils.isExternalStorageAvailable()) {
      return null;
    }
    File file;
    String dir = mContext.getExternalFilesDir(null)
        + File.separator
        + getPackageName()
        + File.separator
        + SAVE_FOLDER
        + File.separator;
    if (!new File(dir).exists()) {
      new File(dir).mkdirs();
    }
    file = new File(dir + System.currentTimeMillis());
    mCurrentPicPath = file.getAbsolutePath();
    return file;
  }

  public void setPicPath(String filePath) {
    mCurrentPicPath = filePath;
  }

  public Bitmap scaleImg(int width, int height) {
    if (TextUtils.isEmpty(mCurrentPicPath)) {
      return null;
    }
    mBitmap = ScalingUtil.decodeFile(mCurrentPicPath, width, height, ScalingUtil.ScalingLogic.FIT);
    mBitmap = ScalingUtil.createScaledBitmap(mBitmap, width, height, ScalingUtil.ScalingLogic.FIT);
    return mBitmap;
  }

  private Resources getResources() {
    return mContext.getResources();
  }

  private String getPackageName() {
    return mContext.getPackageName();
  }

  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (Activity.RESULT_OK != resultCode) {
      return;
    }
    switch (requestCode) {
      case AvatarChoosePresenter.REQUEST_CODE_CAPTURE:
        break;
      case AvatarChoosePresenter.REQUEST_CODE_ALBUM:
        Uri selectImg = data.getData();
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = null;
        try {
          cursor = mContext.getContentResolver().query(selectImg, filePathColumn, null, null, null);
          cursor.moveToFirst();
          int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
          mCurrentPicPath = cursor.getString(columnIndex);
        } finally {
          if (null != cursor) {
            cursor.close();
          }
        }
        break;
    }
    mBitmap = scaleImg(getResources().getInteger(R.integer.avatar_width),
        getResources().getInteger(R.integer.avatar_height));
    // TODO 回调界面显示头像
    // TODO 上传头像
    uploadData();
  }

  public void onDestroy() {
    if (null != mBitmap && !mBitmap.isRecycled()) {
      mBitmap.recycle();
    }
    mSubscriptions.unsubscribe();
  }

  public void uploadData() {

    // create RequestBody instance from file
    byte[] bytes = ImageUtils.bitmap2Bytes(mBitmap, 100);

    File file = ImageUtils.byte2File(bytes, mContext.getExternalFilesDir(null) +
        AVATAR_FOLDER, FILE_NAME);

    RequestBody requestFile =
        RequestBody.create(MediaType.parse("multipart/form-data"), file);

    // MultipartBody.Part is used to send also the actual file name
    MultipartBody.Part body =
        MultipartBody.Part.createFormData("image", file.getName(), requestFile);
    String url = "api.php?s=public/uploadAvatar&session_id=" + Url.SESSIONID;
    Subscription subscription = mUserService.upload(url, body).subscribeOn(Schedulers.io())
        .observeOn
            (AndroidSchedulers.mainThread())
        .subscribe(new UiRpcSubscriberSimple<UploadModel>(mContext) {
          @Override
          protected void onSuccess(UploadModel model) {
            UserInfoModel info = BaseApplication.getApplication()
                .getGlobalComponent().loginSession().getUserInfo();
            info.setAvatar(NetConstant.BASE_URL + NetConstant.UPLOAD_AVATAR_URL + model.getPath());
            BaseApplication.getApplication()
                .getGlobalComponent().loginSession().saveUserInfoModel(info);
          }

          @Override
          protected void onEnd() {

          }
        });
    mSubscriptions.add(subscription);
  }
}
