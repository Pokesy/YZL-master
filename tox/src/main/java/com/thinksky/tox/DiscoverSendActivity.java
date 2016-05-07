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
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.alibaba.fastjson.JSON;
import com.thinksky.fragment.DisLocationActivity;
import com.thinksky.fragment.DiscoverFragment;
import com.thinksky.holder.BaseBActivity;
import com.thinksky.info.PostInfo;
import com.thinksky.rsen.RsenUrlUtil;
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

import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import org.json.JSONException;
import org.json.JSONObject;
import org.kymjs.aframe.bitmap.KJBitmap;
import org.kymjs.aframe.http.KJHttp;
import org.kymjs.aframe.ui.widget.HorizontalListView;
import org.kymjs.aframe.utils.FileUtils;
import org.kymjs.kjframe.http.HttpParams;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DiscoverSendActivity extends BaseBActivity implements View.OnClickListener {
    private LinearLayout photoLayout;
    private String mTempPhotoName;
    private EditText mTitleEdit, mContentEdit;

    private RelativeLayout postSendLayout;
    /**
     * 已选择准备上传的图片数量
     */
    private int img_num = 0;
    private ImageView img1, img2, img3, img4, img5, img6, img7, img8, img9, img10;

    private List<RelativeLayout> imgLayList = new ArrayList<RelativeLayout>();
    private RelativeLayout imgLay1, imgLay2, imgLay3, imgLay4, imgLay5, imgLay6, imgLay7, imgLay8, imgLay9;
    private ProgressDialog progressDialog, progressDialog1;
    /**
     * 用来保存准备选择上传的图片路径
     */
    private List<String> scrollImg = new ArrayList<String>();
    private List<ImageView> imgList = new ArrayList<ImageView>();
    private MyJson myJson = new MyJson();
    private ForumApi forumApi = new ForumApi();
    private List<String> attachIds = new ArrayList<String>();

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
    private int fishid = 1;

    private String session_id;
    private String userUid;
    private BaseApi baseApi;
    private String l;
    private LinearLayout location;
    private String longitude;
    private String latitude;
    private String address;
    private TextView dizhi;
    private String isfactory = "2";
    private ToggleButton mTogBtn;
    private String isdisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_discover);
        initView();
    }

    private void initView() {
        kjBitmap = KJBitmap.create();
        finalBitmap = FinalBitmap.create(this);
//        forumApi.setHandler(hand);
        Intent intent = getIntent();
        mTogBtn = (ToggleButton) findViewById(R.id.mTogBtn); // 获取到控件
        mTogBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                // TODO Auto-generated method stub
                if (isChecked) {
                    isdisplay = "0";
                } else {
                    //未选中
                    isdisplay = "1";
                }
            }
        });// 添加监听事件
//        forumId=intent.getStringExtra("forumId");

        photoLayout = (LinearLayout) findViewById(R.id.Post_send_photo);
        location = (LinearLayout) findViewById(R.id.location);
        dizhi = (TextView) findViewById(R.id.dizhi);

        photoLayout.setOnClickListener(this);
        photoLayout.setOnTouchListener(new TouchHelper(this, R.drawable.borderradius_postsend + "", R.drawable.borderradius_postsend_touched + "", "drawable"));
        baseApi = new BaseApi();
        session_id = baseApi.getSeesionId();
        userUid = baseApi.getUid();
        mContentEdit = (EditText) findViewById(R.id.Post_send_contentEdit);
        mTitleEdit = (EditText) findViewById(R.id.Post_send_titleEdit);
        postSendLayout = (RelativeLayout) findViewById(R.id.Post_send);
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
        backBtn.setOnClickListener(this);
        mFaceBtn.setOnClickListener(this);
        mAttachBtn.setOnClickListener(this);
        postSendLayout.setOnClickListener(this);
        location.setOnClickListener(this);
        fishid = intent.getIntExtra("fish", 0);
        if (intent.getIntExtra("fish", 0) == 0) {

            title.setVisibility(View.GONE);
            isfactory = "1";

        }
        progressDialog = new ProgressDialog(this);
        photoCount = (TextView) findViewById(R.id.photo_count);
        initList();
        address = intent.getStringExtra("address");
        longitude = intent.getStringExtra("longitude");
        latitude = intent.getStringExtra("latitude");
    }

    private void initList() {
        Map map = new HashMap();
        map.put("uid", userUid);

        RsenUrlUtil.executeGetWidthMap(this, RsenUrlUtil.URL_FXU, map, new RsenUrlUtil.OnJsonResultListener<FUBean>() {
            @Override
            public void onNoNetwork(String msg) {
                ToastHelper.showToast(msg, Url.context);
            }


            @Override
            public void onParseJsonBean(List<FUBean> beans, JSONObject jsonObject) {

                try {

                    FUBean bean = new FUBean();
                    bean.address = jsonObject.getString("address");
                    bean.mobile1 = jsonObject.getString("mobile1");
                    bean.isdisplay = jsonObject.getString("isdisplay");
                    bean.isfactory = jsonObject.getString("isfactory");
                    beans.add(bean);


                } catch (JSONException e) {
                }

            }

            @Override
            public void onResult(boolean state, List<FUBean> beans) {
                if (state) {
                    if (!beans.get(0).address.equals(null) && !beans.get(0).address.equals("")) {
                        dizhi.setText(beans.get(0).address);
                    }
                    if (!beans.get(0).mobile1.equals(null) && !beans.get(0).mobile1.equals("")) {
                        mContentEdit.setText(beans.get(0).mobile1);
                    }
                    if (beans.get(0).isdisplay.equals("0")) {
                        mTogBtn.setChecked(true);
                    }

                } else {
                    ToastHelper.showToast("请求失败", Url.context);
                }
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
            case R.id.Post_send:
                if (scrollImg.size() != 0) {
                    if (mTitleEdit.getText().toString().equals("") && fishid == 1) {
                        ToastHelper.showToast("请填写标题", this);
                        return;
                    }
                    if (mContentEdit.getText().toString().length() != 11) {
                        ToastHelper.showToast("手号码必须是11位", this);
                        return;
                    }
                    if (BaseFunction.isLogin()) {
                        uploadImages();
                    } else {
                        ToastHelper.showToast("未登录", this);
                    }
                } else {
                    if (mTitleEdit.getText().toString().equals("") && fishid == 1) {
                        Toast.makeText(DiscoverSendActivity.this, "请填写标题", Toast.LENGTH_LONG).show();
                        return;
                    }
                    if (!mContentEdit.getText().toString().matches("^(13|15|17|18)\\d{9}$")) {
                        Toast.makeText(DiscoverSendActivity.this, "号码填写不正确", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (BaseFunction.isLogin()) {


                        sendWeibo();
                    } else {
                        Toast.makeText(DiscoverSendActivity.this, "未登入", Toast.LENGTH_LONG).show();
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
                        if (img_num <= 9) {
                            scrollImg.add(imgPathList.get(i));
                            imgList.get(img_num).setVisibility(View.VISIBLE);
                            imgList.get(img_num).setTag(imgPathList.get(i));
                            imgList.get(img_num).setBackgroundDrawable(new BitmapDrawable(BitmapUtiles.loadBitmap(imgPathList.get(i), 4)));
                            /*imgList.get(img_num).setImageBitmap(BitmapUtiles.loadBitmap(imgPathList.get(i), 4));
                            imgList.get(img_num).setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
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
                if (photo_num > 9) {
                    Toast.makeText(DiscoverSendActivity.this, "不能超过9张哟", Toast.LENGTH_SHORT).show();
                    photo_num = photo_num - imgPathList.size();
                    scrollImg.add(img_num, "add");
                    return;
                }
                for (int i = 0; i < imgPathList.size(); i++) {
                    if (!BaseFunction.isExistsInList(imgPathList.get(i), scrollImg)) {
                        if (img_num <= 9) {
                            Log.d("Andy", img_num + "");
                            scrollImg.add(imgPathList.get(i));
                            Log.e(">>", scrollImg.get(i));
                           /* imgList.get(img_num).setVisibility(View.VISIBLE);
                            imgLayList.get(getAbleLocation()).setVisibility(View.VISIBLE);
                            imgList.get(getAbleLocation()).setImageBitmap(BitmapUtiles.loadBitmap(imgPathList.get(i), 2));*/
                            img_num++;
                        }
                    }
                }
                photoCount.setText("已选" + img_num + "张，还剩" + (9 - img_num) + "张");
                scrollImg.add(img_num, "add");
                photoAdapter.notifyDataSetChanged();
            }
            /**
             * 表示从拍照的Activity返回
             */
        } else if (requestCode == 1 && resultCode == RESULT_OK) {
            File temFile = new File(Environment.getExternalStorageDirectory() + "/tox/photos/" + mTempPhotoName);
            if (temFile.exists()) {
                mAttachLayout.setVisibility(View.GONE);
                mPhotoShowLayout.setVisibility(View.VISIBLE);
                if (scrollImg.size() != 0) {
                    scrollImg.remove((scrollImg.size() - 1));
                }
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 3;

                if (!BaseFunction.isExistsInList(temFile.getPath(), scrollImg)) {
                    if (img_num <= 9) {

                        scrollImg.add(temFile.getPath());
                        img_num++;
                    }
                }
                photoCount.setText("已选" + img_num + "张，还剩" + (9 - img_num) + "张");
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
        if (img_num >= 9) {

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
                    DiscoverSendActivity.this.finish();

                }
            }
        }

        ;
    };

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
                            //progressDialog1.show(UploadActivity.this,"提示","发布中...",true,false);
                            l = WeiboApi.getAttachIds(attachIds);
                            sendWeibo();
                        }
                    }

                    @Override
                    public void onFailure(Throwable t, int errorNo, String strMsg) {
                        Log.e("上传照片失败", "");
                        progressDialog.dismiss();
                        Toast.makeText(DiscoverSendActivity.this, "上传照片失败！", Toast.LENGTH_LONG).show();
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
            params.put("image", FileUtils.getSaveFile(Url.UPLOADTEMPORARYPATH, path.substring(path.lastIndexOf("/") + 1, path.length())));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void sendWeibo() {

        Map map = new HashMap();
        map.put("session_id", session_id);
        map.put("factory_name", mTitleEdit.getText().toString().trim());
        map.put("mobile", mContentEdit.getText().toString().trim());
        map.put("data", l);
        if (!"".equals(latitude) && !"".equals(longitude)) {
            map.put("latitude", latitude);
            map.put("longitude", longitude);
        }
        map.put("address", address);

        map.put("isfactory", isfactory);
        map.put("isdisplay", isdisplay);
        RsenUrlUtil.executeGetWidthMap(this, RsenUrlUtil.SENDDISCOVER, map, new RsenUrlUtil.OnJsonResultListener<DiscoverFragment.FXBean>() {
            @Override
            public void onNoNetwork(String msg) {
                ToastHelper.showToast(msg, Url.context);
            }

            @Override
            public void onParseJsonBean(List<DiscoverFragment.FXBean> beans, JSONObject jsonObject) {
                String result = jsonObject.toString();
                DiscoverFragment.FXBean discoverInfo = JSON.parseObject(result, DiscoverFragment.FXBean.class);
                beans.add(discoverInfo);
            }

            @Override
            public void onResult(boolean state, List beans) {
                progressDialog.dismiss();
                if (state) {
                    ToastHelper.showToast("发送成功", DiscoverSendActivity.this);
                }
            }
        });

        DiscoverSendActivity.this.finish();
    }

    public class PhotoAdapter extends BaseAdapter {
        private KJBitmap kjBitmap;
        private List<String> imgUrl = new ArrayList<String>();
        private Context ctx;
        private FinalBitmap finalBitmap;
        private LoadImg loadImg;

        public PhotoAdapter(Context ctx, FinalBitmap finalBitmap, KJBitmap kjBitmap, List<String> list) {
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
                    BitmapDrawable bitmapDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.add_post_photo);
                    holder.imageView.setImageBitmap(bitmapDrawable.getBitmap());
                    holder.delImg.setVisibility(View.GONE);
                } else {
                    holder.imageView.setImageBitmap(BitmapUtiles.loadBitmap(imgUrl.get(position), 3));
                    holder.delImg.setVisibility(View.VISIBLE);
                }

            } else {
                if (imgUrl.get(position).equals("add")) {
                    holder.imageView.setTag(imgUrl.get(position));
                    BitmapDrawable bitmapDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.add_post_photo);
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
                        if (img_num < 9) {
                            //ToastHelper.showToast("点击了罗",ctx);
                            String[] items = {"相册", "拍照"};
                            AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
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
                                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                            File file = new File(Environment.getExternalStorageDirectory() + "/tox/photos");
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
                            ToastHelper.showToast("最多上传9张图片", ctx);
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
                photoCount.setText("已选" + img_num + "张，还剩" + (9 - img_num) + "张");
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

    public static class FUBean implements Serializable {

        /**
         * success : true
         * error_code : 0
         * message : 获取成功
         * avatar32 : Uploads/Avatar/2016-04-21/57188d24f35f3_32_32.png
         * avatar64 : Uploads/Avatar/2016-04-21/57188d24f35f3_64_64.png
         * avatar128 : Uploads/Avatar/2016-04-21/57188d24f35f3_128_128.png
         * avatar256 : Uploads/Avatar/2016-04-21/57188d24f35f3_256_256.png
         * avatar512 : Uploads/Avatar/2016-04-21/57188d24f35f3_512_512.png
         * sex : 1
         * nickname : admin
         * username : admin
         * rank_link : []
         * expand_info : {"qq":"","生日":"2016-04-21"}
         * fans : 3
         * following : 4
         * title : Lv5 经理
         * signature : 走自己的路，说别人去吧
         * birthday : 0000-00-00
         * pos_city : null
         * pos_district : null
         * pos_province : {"id":"110000","name":"北京市","level":"1","upid":"0"}
         * isfactory : 1
         * longitude : 114.1013555031497
         * latitude : 22.737535613155853
         * isdisplay : 0
         * mobile1 : 15822024827
         * address : 广东省深圳市宝安区X231(高尔夫大道)
         * score1 : 404
         * qq :
         * factory_name :
         * email :
         * real_nickname : admin
         * score : 404
         * uid : 1
         * mobile : 15822024827
         * data : 357
         * cover_url : ["357"]
         * images : ["Uploads/Picture/2016-04-21/57184876dee2b_100_100.jpg"]
         * is_follow : 0
         */

        private boolean success;
        private int error_code;
        private String message;
        private String avatar32;
        private String avatar64;
        private String avatar128;
        private String avatar256;
        private String avatar512;
        private String sex;
        private String nickname;
        private String username;
        /**
         * qq :
         * 生日 : 2016-04-21
         */

        private ExpandInfoEntity expand_info;
        private String fans;
        private String following;
        private String title;
        private String signature;
        private String birthday;
        private Object pos_city;
        private Object pos_district;
        /**
         * id : 110000
         * name : 北京市
         * level : 1
         * upid : 0
         */

        private PosProvinceEntity pos_province;
        private String isfactory;
        private String longitude;
        private String latitude;
        private String isdisplay;
        private String mobile1;
        private String address;
        private String score1;
        private String qq;
        private String factory_name;
        private String email;
        private String real_nickname;
        private String score;
        private String uid;
        private String mobile;
        private String data;
        private int is_follow;
        private List<?> rank_link;
        private List<String> cover_url;
        private List<String> images;

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public int getError_code() {
            return error_code;
        }

        public void setError_code(int error_code) {
            this.error_code = error_code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getAvatar32() {
            return avatar32;
        }

        public void setAvatar32(String avatar32) {
            this.avatar32 = avatar32;
        }

        public String getAvatar64() {
            return avatar64;
        }

        public void setAvatar64(String avatar64) {
            this.avatar64 = avatar64;
        }

        public String getAvatar128() {
            return avatar128;
        }

        public void setAvatar128(String avatar128) {
            this.avatar128 = avatar128;
        }

        public String getAvatar256() {
            return avatar256;
        }

        public void setAvatar256(String avatar256) {
            this.avatar256 = avatar256;
        }

        public String getAvatar512() {
            return avatar512;
        }

        public void setAvatar512(String avatar512) {
            this.avatar512 = avatar512;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public ExpandInfoEntity getExpand_info() {
            return expand_info;
        }

        public void setExpand_info(ExpandInfoEntity expand_info) {
            this.expand_info = expand_info;
        }

        public String getFans() {
            return fans;
        }

        public void setFans(String fans) {
            this.fans = fans;
        }

        public String getFollowing() {
            return following;
        }

        public void setFollowing(String following) {
            this.following = following;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSignature() {
            return signature;
        }

        public void setSignature(String signature) {
            this.signature = signature;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public Object getPos_city() {
            return pos_city;
        }

        public void setPos_city(Object pos_city) {
            this.pos_city = pos_city;
        }

        public Object getPos_district() {
            return pos_district;
        }

        public void setPos_district(Object pos_district) {
            this.pos_district = pos_district;
        }

        public PosProvinceEntity getPos_province() {
            return pos_province;
        }

        public void setPos_province(PosProvinceEntity pos_province) {
            this.pos_province = pos_province;
        }

        public String getIsfactory() {
            return isfactory;
        }

        public void setIsfactory(String isfactory) {
            this.isfactory = isfactory;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getIsdisplay() {
            return isdisplay;
        }

        public void setIsdisplay(String isdisplay) {
            this.isdisplay = isdisplay;
        }

        public String getMobile1() {
            return mobile1;
        }

        public void setMobile1(String mobile1) {
            this.mobile1 = mobile1;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getScore1() {
            return score1;
        }

        public void setScore1(String score1) {
            this.score1 = score1;
        }

        public String getQq() {
            return qq;
        }

        public void setQq(String qq) {
            this.qq = qq;
        }

        public String getFactory_name() {
            return factory_name;
        }

        public void setFactory_name(String factory_name) {
            this.factory_name = factory_name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getReal_nickname() {
            return real_nickname;
        }

        public void setReal_nickname(String real_nickname) {
            this.real_nickname = real_nickname;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }

        public int getIs_follow() {
            return is_follow;
        }

        public void setIs_follow(int is_follow) {
            this.is_follow = is_follow;
        }

        public List<?> getRank_link() {
            return rank_link;
        }

        public void setRank_link(List<?> rank_link) {
            this.rank_link = rank_link;
        }

        public List<String> getCover_url() {
            return cover_url;
        }

        public void setCover_url(List<String> cover_url) {
            this.cover_url = cover_url;
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }

        public static class ExpandInfoEntity {
            private String qq;
            private String 生日;

            public String getQq() {
                return qq;
            }

            public void setQq(String qq) {
                this.qq = qq;
            }

            public String get生日() {
                return 生日;
            }

            public void set生日(String 生日) {
                this.生日 = 生日;
            }
        }

        public static class PosProvinceEntity {
            private String id;
            private String name;
            private String level;
            private String upid;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getLevel() {
                return level;
            }

            public void setLevel(String level) {
                this.level = level;
            }

            public String getUpid() {
                return upid;
            }

            public void setUpid(String upid) {
                this.upid = upid;
            }
        }
    }
}
