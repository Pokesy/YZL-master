package com.thinksky.myview;

import android.text.TextUtils;
import com.thinksky.utils.imageloader.ImageLoader;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.thinksky.tox.R;
import com.thinksky.info.AshamedInfo;
import com.tox.Url;
import com.thinksky.net.ThreadPoolUtils;
import com.thinksky.thread.HttpGetThread;
import com.thinksky.utils.LoadImg;
import com.thinksky.utils.MyJson;
import com.thinksky.utils.LoadImg.ImageDownloadCallBack;

public class AuditView {

    private Context ctx;
    private View mView = null;
    private TextView mText;
    private ImageView mImageView;
    private List<AshamedInfo> list = new ArrayList<AshamedInfo>();
    private int start = 0;
    private int end = 10;
    private MyJson myJson = new MyJson();
    private int ListFlag = 0;
    private boolean animFlag = true;
    private CallBack mCallBack = null;
    private LoadImg loadImgHeadImg;

    public AuditView(Context ctx) {
        this.ctx = ctx;
        loadImgHeadImg = new LoadImg(ctx);
    }

    public View createView() {
        mView = (View) View.inflate(ctx, R.layout.activity_auditcenter, null);
        mText = (TextView) mView.findViewById(R.id.AuditCenterText);
        mImageView = (ImageView) mView.findViewById(R.id.AuditCenterImg);
        // getNET();
        return mView;
    }

    // 请求服务器方法
    private void getNET() {
        String endurl = Url.AUDIT + "start=" + start + "&end=" + end;
        ThreadPoolUtils.execute(new HttpGetThread(hand, endurl));
    }

    public void NextView() {

        if (list == null) {
            mText.setText("查询中请等待");
            animFlag = false;
            mCallBack.callback(animFlag);
            getNET();
            return;
        }
        if (ListFlag >= list.size()) {
            mText.setText("查询中请等待");
            ListFlag = list.size();
            getNET();
            animFlag = false;
            mCallBack.callback(animFlag);
            return;
        } else {
            animFlag = true;
            mText.setText(list.get(ListFlag).getQvalue());
            mImageView.setImageBitmap(null);
            if (!TextUtils.isEmpty(list.get(ListFlag).getQimg())) {
                ImageLoader.loadOptimizedHttpImage(ctx, Url.QIMGURL + list.get(ListFlag).getQimg())
                    .placeholder(R.drawable.picture_no).into(mImageView);
            }
            ListFlag++;
            mCallBack.callback(animFlag);
        }

    }

    Handler hand = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            if (msg.what == 200) {
                String result = (String) msg.obj;
                Log.e("liuxiaowei", result);
                if (result == null)
                    return;
                if (result.equals("praise")) {
                    return;
                }
                if (result.equals("unpraise")) {
                    return;
                }

                Log.e("liuxiaowei", result);
                List<AshamedInfo> newList = myJson.getAshamedList(result);
                Log.e("liuxiaowei", "newList.size():" + newList.size());
                if (newList != null) {
                    start += 10;
                    end += 10;
                    list.addAll(newList);
                    Log.e("liuxiaowei", "list.size():" + list.size());
                    NextView();
                    return;
                } else if (newList == null) {
                    Toast.makeText(ctx, "最后一条", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        }

        ;
    };

    private void Praise() {

    }

    private void unPraise() {

    }

    public void setCallBack(CallBack call) {
        this.mCallBack = call;
    }

    public interface CallBack {
        public void callback(boolean animFlag);
    }
}
