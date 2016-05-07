package com.thinksky.tox;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.thinksky.holder.BaseBActivity;
import com.thinksky.utils.FileUtiles;
import com.thinksky.utils.MyJson;
import com.tox.BaseFunction;
import com.tox.ToastHelper;
import com.tox.Url;
import com.tox.WeiboApi;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.HttpHandler;

import java.io.File;

public class SettingActivity extends BaseBActivity {
    private CheckBox yejianmoshi, tongzhi, baocunjindu;
    private RelativeLayout tupianjiazai, zitidaxiao, qingchuhuancun,
            setting_chongzhimima, yijianfankui, guoduandianzan, banbenjiance,
            guanyuqiubai;
    private TextView tupiantext, zititext;
    private Builder builder;
    private ImageView back;
    private WeiboApi mWeiboApi = new WeiboApi();
    private HttpHandler<File> httpHandler;
    private ProgressDialog progressDialog,mProgressDialog, mProgressDialog1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);
        mWeiboApi.setHandler(handler);

        progressDialog = new ProgressDialog(this);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog1 = new ProgressDialog(this);
        initView();
    }
    private DialogInterface.OnCancelListener cancelListener = new DialogInterface.OnCancelListener() {

        @Override
        public void onCancel(DialogInterface dialog) {
            // TODO Auto-generated method stub
            httpHandler.stop();

        }
    };
    private void initView() {


        back = (ImageView) findViewById(R.id.back);

        qingchuhuancun = (RelativeLayout) findViewById(R.id.setting_qingchuhuancun);
        setting_chongzhimima = (RelativeLayout) findViewById(R.id.setting_chongzhimima);
        yijianfankui = (RelativeLayout) findViewById(R.id.setting_yijianfankui);

        banbenjiance = (RelativeLayout) findViewById(R.id.setting_banbenjiance);
        guanyuqiubai = (RelativeLayout) findViewById(R.id.setting_guanyuqiubai);
        MyOnclickListener mOnclickListener = new MyOnclickListener();
        back.setOnClickListener(mOnclickListener);


        qingchuhuancun.setOnClickListener(mOnclickListener);
        setting_chongzhimima.setOnClickListener(mOnclickListener);
        yijianfankui.setOnClickListener(mOnclickListener);

        banbenjiance.setOnClickListener(mOnclickListener);
        guanyuqiubai.setOnClickListener(mOnclickListener);
    }
    Handler handler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            mProgressDialog1.dismiss();
            if (msg.what == 0) {
                //有版本更新
                MyJson myJson = new MyJson();
                String result = (String) msg.obj;
                final String url = myJson.getApkUrl(result);
                Dialog dialog = new AlertDialog.Builder(SettingActivity.this)
                        .setTitle("更新")
                        .setMessage("当前版本v0.1.1,新版本v0.1.2，是否下载更新！")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                                mProgressDialog.setTitle("下载中...");
                                mProgressDialog.setCanceledOnTouchOutside(false);
                                mProgressDialog.setOnCancelListener(cancelListener);
                                mProgressDialog.setCancelable(true);
                                mProgressDialog.show();
                                //ToastHelper.showToast("开始下载"+url,MainActivity.this);

                                startDownApk(url);
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
            }else if(msg.what == 0x005){
                FileUtiles.DeleteTempFiles(Url.getDeleteFilesPath());
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                        ToastHelper.showToast("清除成功",SettingActivity.this);
                    }
                },2000);
            }else {
                //无版本更新
                Dialog dialog = new AlertDialog.Builder(SettingActivity.this)
                        .setTitle("更新")
                        .setMessage("当前已经是最新版本！")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).show();
            }
        }
    };
    private void startDownApk(String url) {
        FinalHttp fh = new FinalHttp();
        File file = new File(Environment.getExternalStorageDirectory() + "/tox/apk.apk");
        if (file.exists()) {
            file.delete();
        }
        httpHandler = fh.download(url, "/mnt/sdcard/tox/apk.apk", true, new AjaxCallBack<File>() {
            @Override
            public void onSuccess(File file) {
                mProgressDialog.dismiss();
                if (file != null) {
                    Toast.makeText(SettingActivity.this, "下载完成" + file.getAbsoluteFile().toString(), Toast.LENGTH_LONG).show();
                    installationApk(file);
                }

            }

            @Override
            public void onLoading(long count, long current) {
                mProgressDialog.setProgressNumberFormat("%1dKB/%2dKB");
                ;
                mProgressDialog.setMax((int) count / 1024);
                mProgressDialog.setProgress((int) (current / 1024));
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                super.onFailure(t, errorNo, strMsg);
                ToastHelper.showToast("下载失败：" + strMsg, SettingActivity.this);
            }
        });
    }
    public void installationApk(File file) {
        Log.e("OpenFile", file.getName());
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(android.content.Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file),
                "application/vnd.android.package-archive");
        startActivity(intent);

    }
    private class MyOnclickListener implements View.OnClickListener {
        public void onClick(View v) {
            int mID = v.getId();
            if (mID == R.id.back) {
                SettingActivity.this.finish();
            }

            if (mID == R.id.setting_qingchuhuancun) {
                progressDialog.setTitle("清除图片缓存");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.setCancelable(false);
                progressDialog.show();
                Message message = new Message();
                message.what = 0x005;
                handler.sendMessage(message);
//                Toast.makeText(SettingActivity.this, "点击清除缓存", Toast.LENGTH_LONG).show();
            }
            if (mID == R.id.setting_chongzhimima) {
                if (BaseFunction.isLogin()) {
                    Intent intent3 = new Intent(SettingActivity.this, ChangePasswordActivity.class);
                    startActivity(intent3);
                } else {
                    ToastHelper.showToast("请先登录", Url.context);

                }
            }
            if (mID == R.id.setting_yijianfankui) {
                Intent intent2 = new Intent(SettingActivity.this, Setting_yijianActivity.class);
                startActivity(intent2);
            }

            if (mID == R.id.setting_banbenjiance) {
                mProgressDialog1.setTitle("检查更新");
                mProgressDialog1.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                mProgressDialog1.setCancelable(false);
                mProgressDialog1.show();
                mWeiboApi.checkUpdate();
//                Toast.makeText(SettingActivity.this, "亲，已经是最新版本了", Toast.LENGTH_LONG).show();
            }
            if (mID == R.id.setting_guanyuqiubai) {
                Intent intent = new Intent(SettingActivity.this, AboutActivity.class);
                startActivity(intent);
            }

        }

    }
}
