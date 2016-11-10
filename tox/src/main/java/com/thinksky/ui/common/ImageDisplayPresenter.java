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
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import com.thinksky.model.ActivityModel;
import com.thinksky.tox.R;
import com.thinksky.tox.ScanPhotoActivity;
import com.thinksky.tox.ShowImageActivity;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
  private static final int REQUEST_CODE_CAPTURE = 1;
  private String mTempPhotoName;
  private ArrayList<String> mSelectedImgPath = new ArrayList<>();

  private IImageDisplayView iImageDisplayView;
  private Activity mContext;
  private int mMaxCount;

  public ImageDisplayPresenter(Activity context, int maxCount, IImageDisplayView displayView) {
    mContext = context;
    mMaxCount = maxCount;
    iImageDisplayView = displayView;

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

      }
    });
  }

  public void showChooseDialog() {
    String[] items = mContext.getResources().getStringArray(R.array.choose_image_items);
    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
    builder.setItems(items, new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {
        switch (which) {
          case 0:
            Intent intent3 = new Intent(mContext, ScanPhotoActivity.class);
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

  public void enterImgList() {
    Intent mIntent = new Intent(mContext, ShowImageActivity.class);
    mIntent.putStringArrayListExtra("data", mSelectedImgPath);
    mIntent.putExtra("fromActivity", ActivityModel.UPLOADACTIVITY);
    mContext.startActivityForResult(mIntent, REQUEST_CODE_SHOW_IMAGE);

  }

  //判断图片路径是否已是选中的图片
  private boolean isExistsInList(String path, List<String> pathList) {
    if (pathList == null || pathList.size() == 0) {
      return false;
    } else {
      for (int i = 0; i < pathList.size(); i++) {
        if (path.equalsIgnoreCase(pathList.get(i))) {
          return true;
        }
      }
    }
    return false;
  }

  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    /**
     * 对ShowImageActivity直接返回的
     */
    if (requestCode == REQUEST_CODE_SHOW_IMAGE && resultCode == ShowImageActivity
        .RESULT_CODE_CHOOSE_IMG_SUCCESS) {
      Log.e("scroll返回", "");
      List<String> imgPathList = data.getStringArrayListExtra("data");
      if (imgPathList.size() <= 0) {
        iImageDisplayView.clear();
        iImageDisplayView.hide();
      } else {
        iImageDisplayView.clear();
        iImageDisplayView.show();
        for (int i = 0; i < imgPathList.size(); i++) {
          if (!isExistsInList(imgPathList.get(i), mSelectedImgPath)) {
            if (mSelectedImgPath.size() < mMaxCount) {
              mSelectedImgPath.add(imgPathList.get(i));
            }
          }
        }
        iImageDisplayView.add(mSelectedImgPath);
      }
    }
    /**
     * 表示从ScanPhotoActivity返回
     */
    else if (resultCode == ScanPhotoActivity.RESULT_CODE_CHOOSE_IMG && requestCode ==
        REQUEST_CODE_SCAN) {
      List<String> imgPathList = data.getStringArrayListExtra("data");
      Log.e("选图返回", "");
      if (imgPathList.size() > 0) {
        iImageDisplayView.show();
        for (int i = 0; i < imgPathList.size(); i++) {
          Log.e("图片路径", imgPathList.get(i));
          if (!isExistsInList(imgPathList.get(i), mSelectedImgPath)) {
            if (mSelectedImgPath.size() < mMaxCount) {
              mSelectedImgPath.add(imgPathList.get(i));
            }
          }
        }
      }
      iImageDisplayView.clear();
      iImageDisplayView.add(mSelectedImgPath);
      /**
       * 表示从拍照的Activity返回
       */
    } else if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
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
          }
        }
      }
    }
  }
}

