package com.thinksky.tox;


import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;
import com.thinksky.adapter.GroupAdapter;
import com.thinksky.holder.BaseBActivity;
import com.thinksky.info.ImageBean;
import com.thinksky.ui.common.TitleBar;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class AlbumListActivity extends BaseBActivity {
  public static final int RESULT_CODE_CHOOSE_IMG = 99;
  private static final int REQUEST_CODE_IMG_LIST = 10;
  private static final int SCAN_OK = 1;
  private static final int MAX_IMG_NUM = 9;
  private HashMap<String, List<String>> mGroupMap = new HashMap<String, List<String>>();
  private List<ImageBean> list = new ArrayList<ImageBean>();
  private ProgressDialog mProgressDialog;
  private GroupAdapter adapter;
  private GridView mGroupGridView;
  private List<String> selectedImg = new ArrayList<String>();
  private List<String> lists;

  private TitleBar mTitleBar;

  private Handler mHandler = new Handler() {

    @Override
    public void handleMessage(Message msg) {
      super.handleMessage(msg);
      switch (msg.what) {
        case SCAN_OK:
          //关闭进度条
          mProgressDialog.dismiss();
          adapter = new GroupAdapter(AlbumListActivity.this, list = subGroupOfImage(mGroupMap),
              mGroupGridView);
          mGroupGridView.setAdapter(adapter);
          break;
      }
    }

  };

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.photo_scan_main);
    mTitleBar = (TitleBar) findViewById(R.id.title_bar);
    mTitleBar.setMiddleTitle("相册");
    mTitleBar.setLeftImgMenu(R.drawable.icon_title_bar_back, new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });
    mGroupGridView = (GridView) findViewById(R.id.main_grid);
    getImages();
    mGroupGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

      @Override
      public void onItemClick(AdapterView<?> parent, View view,
                              int position, long id) {
        List<String> childList = mGroupMap.get(list.get(position).getFolderName());
        Intent mIntent = new Intent(AlbumListActivity.this, ImageChooseListActivity.class);
        mIntent.putStringArrayListExtra("data", (ArrayList<String>) childList);
        startActivityForResult(mIntent, REQUEST_CODE_IMG_LIST);
      }
    });

  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == REQUEST_CODE_IMG_LIST) {
      if (resultCode != ImageChooseListActivity.RESULT_CODE_CHOOSE_IMG_SUCCESS) {
        return;
      }
      lists = data.getStringArrayListExtra("data");
      if (list.size() == 0) {
        return;
      }
      if (lists.size() > MAX_IMG_NUM) {
        Toast.makeText(AlbumListActivity.this, "图片不能超过" + MAX_IMG_NUM + "张哟", Toast.LENGTH_SHORT)
            .show();
        return;
      }
      for (int i = 0; i < lists.size(); i++) {
        selectedImg.add(lists.get(i));
        Log.e("选择图片", selectedImg.get(i));
      }
      back();
    }
  }

  public void back() {
    Intent data = new Intent();
    data.putStringArrayListExtra("data", (ArrayList<String>) selectedImg);
    setResult(RESULT_CODE_CHOOSE_IMG, data);
    finish();
  }

  /**
   * 利用ContentProvider扫描手机中的图片，此方法在运行在子线程中
   */
  private void getImages() {
    if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
      Toast.makeText(this, "暂无外部存储", Toast.LENGTH_SHORT).show();
      return;
    }

    //显示进度条
    mProgressDialog = ProgressDialog.show(this, null, "正在加载...");

    new Thread(new Runnable() {

      @Override
      public void run() {
        Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        ContentResolver mContentResolver = AlbumListActivity.this.getContentResolver();

        //只查询jpeg和png的图片
        Cursor mCursor = mContentResolver.query(mImageUri, null,
            MediaStore.Images.Media.MIME_TYPE + "=? or "
                + MediaStore.Images.Media.MIME_TYPE + "=?",
            new String[]{"image/jpeg", "image/png"}, MediaStore.Images.Media.DATE_MODIFIED
        );

        while (mCursor.moveToNext()) {
          //获取图片的路径
          String path = mCursor.getString(mCursor
              .getColumnIndex(MediaStore.Images.Media.DATA));

          //获取该图片的父路径名
          String parentName = new File(path).getParentFile().getName();

          //根据父路径名将图片放入到mGruopMap中
          if (!mGroupMap.containsKey(parentName)) {
            List<String> childList = new ArrayList<String>();
            childList.add(path);
            mGroupMap.put(parentName, childList);
          } else {
            mGroupMap.get(parentName).add(path);
          }
        }

        mCursor.close();
        //通知Handler扫描图片完成
        mHandler.sendEmptyMessage(SCAN_OK);

      }
    }).start();

  }


  /**
   * 组装分组界面GridView的数据源，因为我们扫描手机的时候将图片信息放在HashMap中
   * 所以需要遍历HashMap将数据组装成List
   */
  private List<ImageBean> subGroupOfImage(HashMap<String, List<String>> mGruopMap) {
    if (mGruopMap.size() == 0) {
      return null;
    }
    List<ImageBean> list = new ArrayList<ImageBean>();

    Iterator<Map.Entry<String, List<String>>> it = mGruopMap.entrySet().iterator();
    while (it.hasNext()) {
      Map.Entry<String, List<String>> entry = it.next();
      ImageBean mImageBean = new ImageBean();
      String key = entry.getKey();
      List<String> value = entry.getValue();

      mImageBean.setFolderName(key);
      mImageBean.setImageCounts(value.size());
      mImageBean.setTopImagePath(value.get(0));//获取该组的第一张图片

      list.add(mImageBean);
    }

    return list;

  }


}
