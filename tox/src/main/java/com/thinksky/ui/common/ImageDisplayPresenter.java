/*
 * 文件名: ImageDisplayPresenter
 * 版    权：  Copyright Hengrtech Tech. Co. Ltd. All Rights Reserved.
 * 描    述: [该类的简要描述]
 * 创建人: zhaozeyang
 * 创建时间:2016/11/3
 * 
 * 修改人：
 * 修改时间:
 * 修改内容：[修改内容]
 */
package com.thinksky.ui.common;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.widget.Toast;
import com.thinksky.holder.BaseApplication;
import com.thinksky.injection.GlobalModule;
import com.thinksky.log.Logger;
import com.thinksky.model.ActivityModel;
import com.thinksky.net.rpc.model.UploadImageModel;
import com.thinksky.net.rpc.service.AppService;
import com.thinksky.serviceinjection.DaggerServiceComponent;
import com.thinksky.serviceinjection.ServiceModule;
import com.thinksky.tox.AlbumListActivity;
import com.thinksky.tox.ImageChooseListActivity;
import com.thinksky.tox.R;
import com.thinksky.utils.ImageUtils;
import com.thinksky.utils.ScalingUtil;
import com.tox.Url;
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

/**
 * [一句话功能简述]<BR>
 * [功能详细描述]
 *
 * @author zhaozeyang
 * @version [Taobei Client V20160411, 2016/11/3]
 */
public class ImageDisplayPresenter {
  private static final int REQUEST_CODE_SCAN = 9;
  private static final int REQUEST_CODE_SHOW_IMAGE = 8;
  private static final int REQUEST_CODE_CAPTURE = 10;
  private String mTempPhotoName;
  private ArrayList<String> mSelectedImgPath = new ArrayList<>();

  private IImageDisplayView iImageDisplayView;
  private Activity mContext;
  private int mMaxCount;

  private CompositeSubscription mSubscriptions = new CompositeSubscription();
  // 记录上传的个数 即 调用上传接口的次数
  private volatile int mUploadInvokeTimes = 0;
  // 有几张需要上传
  private int mNeedUploadCount = 0;

  private HashMap<String, String> mUploadedCache = new HashMap<>();
  private HashMap<String, Bitmap> mUploadBitmapCache = new HashMap<>();
  @Inject
  AppService mAppService;

  public ImageDisplayPresenter(Activity context, int maxCount, IImageDisplayView displayView) {
    inject();
    mContext = context;
    mMaxCount = maxCount;
    iImageDisplayView = displayView;
    Logger.d("ImageDisplayPresenter constructor", iImageDisplayView.toString());
    iImageDisplayView.setMaxCount(maxCount);

    iImageDisplayView.setOnAddImgClickListener(new IImageDisplayView.OnAddImgClickListener() {
      @Override
      public void onAddClicked() {
        showChooseDialog();
      }
    });

    iImageDisplayView.setOnItemDeleteClickListener(new IImageDisplayView
        .OnItemDeleteClickListener() {


      @Override
      public void onItemDeleteClicked(int index, String path) {
        mSelectedImgPath.remove(path);
        Iterator<Map.Entry<String, String>> it = mUploadedCache.entrySet().iterator();
        while (it.hasNext()) {
          Map.Entry<String, String> entry = it.next();
          if (TextUtils.equals(entry.getValue(), path)) {
            it.remove();
            break;
          }
        }
      }
    });
  }

  private void inject() {
    DaggerServiceComponent.builder().globalModule(new GlobalModule(BaseApplication.getApplication
        ())).serviceModule(new ServiceModule())
        .build().inject(this);
  }

  public void setUploadedImages(HashMap<String, String> uploadedCache) {
    mUploadedCache = uploadedCache;
    List<String> uploadedPath = new ArrayList<>();
    Iterator<Map.Entry<String, String>> it = uploadedCache.entrySet().iterator();
    while (it.hasNext()) {
      Map.Entry<String, String> entry = it.next();
      uploadedPath.add(entry.getValue());
    }
    iImageDisplayView.add(uploadedPath);
  }

  public List<String> getUploadedImages() {
    List<String> uploadedId = new ArrayList<>();
    Iterator<Map.Entry<String, String>> it = mUploadedCache.entrySet().iterator();
    while (it.hasNext()) {
      Map.Entry<String, String> entry = it.next();
      uploadedId.add(entry.getKey());
    }
    return uploadedId;
  }

  public void showChooseDialog() {
    String[] items = mContext.getResources().getStringArray(R.array.choose_image_items);
    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
    builder.setItems(items, new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {
        switch (which) {
          case 0:
            Intent intent3 = new Intent(mContext, AlbumListActivity.class);
            mContext.startActivityForResult(intent3, REQUEST_CODE_SCAN);
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
            mContext.startActivityForResult(intent1, REQUEST_CODE_CAPTURE);
            break;
        }
      }
    });
    builder.setCancelable(true);
    builder.show();
  }

  public void uploadImg(final UploadCallback callback) {
    if (null == callback) {
      return;
    }
    final List<String> paths = iImageDisplayView.getSelectedImgPaths();
    final List<String> ids = new ArrayList<>();
    mNeedUploadCount = paths.size();
    if (paths.size() == 0) {
      // 如果未选取图片
      Iterator<Map.Entry<String, String>> it = mUploadedCache.entrySet().iterator();
      List<String> uploadedIds = new ArrayList<>();
      while (it.hasNext()) {
        Map.Entry<String, String> entry = it.next();
        uploadedIds.add(entry.getKey());
      }
      callback.onUploadCompleted(uploadedIds);
      callback.onNetInvokeCompleted();
      return;
    }

    for (int i = 0; i < paths.size(); i++) {

      String path = paths.get(i);
      if (TextUtils.isEmpty(path)) {
        mNeedUploadCount--;
        continue;
      }
      if (path.startsWith("http")) {
        mNeedUploadCount--;
        continue;
      }
      File file;
      if (mUploadBitmapCache.containsKey(path)) {
        byte[] bytes = ImageUtils.bitmap2Bytes(mUploadBitmapCache.get(path), 100);
        file = ImageUtils.byte2File(bytes, mContext.getExternalFilesDir(null) +
            "uploads", "temp_" + System.currentTimeMillis() + ".jpg");
      } else {
        file = new File(path);
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

              mUploadInvokeTimes += 1;
              ids.add(uploadImageModelResponse.body().getMessage().getId());
              if (mUploadInvokeTimes == mNeedUploadCount) {
                List<String> uploadedIds = new ArrayList<>();
                Iterator<Map.Entry<String, String>> it = mUploadedCache.entrySet().iterator();
                while (it.hasNext()) {
                  Map.Entry<String, String> entry = it.next();
                  uploadedIds.add(entry.getKey());
                }
                uploadedIds.addAll(ids);
                callback.onUploadCompleted(uploadedIds);
                mUploadInvokeTimes = 0;
              }
            }
          }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
              mUploadInvokeTimes = 0;
              Logger.e("YZZ", throwable.getMessage(), throwable);
              Toast.makeText(mContext, "上传图片失败", Toast.LENGTH_SHORT).show();
              callback.onNetInvokeCompleted();
            }
          }, new Action0() {
            @Override
            public void call() {

            }
          });
      mSubscriptions.add(subscription);
    }
  }

  public void enterImgList() {
    Intent mIntent = new Intent(mContext, ImageChooseListActivity.class);
    mIntent.putStringArrayListExtra("data", mSelectedImgPath);
    mIntent.putExtra("fromActivity", ActivityModel.UPLOADACTIVITY);
    mContext.startActivityForResult(mIntent, REQUEST_CODE_SHOW_IMAGE);

  }

  public int getUpLoadImgCount() {
    return mSelectedImgPath.size();
  }

  //判断图片路径是否已是选中的图片
  private boolean isExistsInList(String path, List<String> pathList) {
    if (pathList == null || pathList.size() == 0) {
      return false;
    } else {
      return pathList.contains(path);
    }
  }

  public void onDestroy() {
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

  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    /**
     * 对ShowImageActivity直接返回的
     */
    if (requestCode == REQUEST_CODE_SHOW_IMAGE && resultCode == ImageChooseListActivity
        .RESULT_CODE_CHOOSE_IMG_SUCCESS) {
      List<String> imgPathList = data.getStringArrayListExtra("data");
      if (imgPathList.size() == 0) {
        iImageDisplayView.clear();
        iImageDisplayView.hide();
      } else {
        iImageDisplayView.show();
        List<String> mAddPaths = new ArrayList<>();
        for (int i = 0; i < imgPathList.size(); i++) {
          if (!isExistsInList(imgPathList.get(i), mSelectedImgPath)) {
            if (mSelectedImgPath.size() < mMaxCount) {
              mSelectedImgPath.add(imgPathList.get(i));
              mUploadBitmapCache.put(imgPathList.get(i), scaleImg(imgPathList.get(i), 800, 800));
            }
          }
        }
        if (mAddPaths.size() > 0) {
          iImageDisplayView.add(mAddPaths);
        }
      }
    }
    /**
     * 表示从ScanPhotoActivity返回
     */
    else if (resultCode == AlbumListActivity.RESULT_CODE_CHOOSE_IMG && requestCode ==
        REQUEST_CODE_SCAN) {
      List<String> imgPathList = data.getStringArrayListExtra("data");
      List<String> mAddPaths = new ArrayList<>();
      if (imgPathList.size() > 0) {
        iImageDisplayView.show();
        for (int i = 0; i < imgPathList.size(); i++) {
          if (!isExistsInList(imgPathList.get(i), mSelectedImgPath)) {
            if (mSelectedImgPath.size() < mMaxCount) {
              mSelectedImgPath.add(imgPathList.get(i));
              mAddPaths.add(imgPathList.get(i));
              mUploadBitmapCache.put(imgPathList.get(i), scaleImg(imgPathList.get(i), 800, 800));
            }
          }
        }
      }
      if (mAddPaths.size() > 0) {
        iImageDisplayView.add(mAddPaths);
      }
      /**
       * 表示从拍照的Activity返回
       */
    } else if (requestCode == REQUEST_CODE_CAPTURE && resultCode == Activity.RESULT_OK) {
      File temFile = new File(Environment.getExternalStorageDirectory() + "/tox/photos/" +
          mTempPhotoName);
      if (temFile.exists()) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 3;
        iImageDisplayView.show();
        if (!isExistsInList(temFile.getPath(), mSelectedImgPath)) {
          if (mSelectedImgPath.size() < mMaxCount) {
            mSelectedImgPath.add(temFile.getPath());
            List<String> mAddPath = new ArrayList<>();
            mAddPath.add(temFile.getPath());
            iImageDisplayView.add(mAddPath);
            mUploadBitmapCache.put(temFile.getPath(), scaleImg(temFile.getPath(), 800, 800));
            Logger.d("ImageDisplayPresenter camera", iImageDisplayView.toString());
          }
        }
      }
    }
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

  public interface UploadCallback {
    void onUploadCompleted(List<String> ids);

    void onNetInvokeCompleted();
  }
}

