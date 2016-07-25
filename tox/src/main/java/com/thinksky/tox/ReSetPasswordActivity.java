package com.thinksky.tox;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.thinksky.holder.BaseBActivity;
import com.thinksky.rsen.RsenUrlUtil;
import com.thinksky.utils.MD5;
import com.tox.ToastHelper;
import com.tox.Url;
import com.tox.UserApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jiao on 2016/4/28.
 */
public class ReSetPasswordActivity extends BaseBActivity {
    private TimeCount time;
    private Button sendVerify;
    private TextView tv_qd;
    private RelativeLayout registClose;
    private EditText new_agin_name, verifyId, new_name, mName;
    private String verify, newname, newnameagin,names;
    private UserApi mUserapi = new UserApi();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        initview();
        initdata();

    }

    private void initdata() {

        verify = verifyId.getText().toString().trim();

        names = mName.getText().toString();

        newname = new_name.getText().toString().trim();
        newnameagin = new_agin_name.getText().toString().trim();
        registClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        sendVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mName.getText().toString().isEmpty()) {
                    ToastHelper.showToast("手机号不能为空", Url.context);
                } else {
                    time.start();//开始计时
                    mUserapi.setHandler(handler);
                    mUserapi.sendVerifymob(mName.getText().toString(), "mobile");
                }

            }
        });
        tv_qd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verify = verifyId.getText().toString().trim();

                names = mName.getText().toString();

                newname = MD5.md5(new_name.getText().toString().trim());
                newnameagin = MD5.md5(new_agin_name.getText().toString().trim());
                if (newname.isEmpty() || newnameagin.isEmpty()||names.isEmpty()||verify.isEmpty()) {
                    ToastHelper.showToast("手机号，密码或验证码不能为空", Url.context);
                } else {
                    if (!newname.equals(newnameagin)) {
                        ToastHelper.showToast("两次输入密码不一致，请重新输入", Url.context);
                    } else {
                        change();
                    }
                }
            }
        });

    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String result = (String) msg.obj;
            try {
                JSONObject jsonObject=new JSONObject(result);
                if (jsonObject.getString("success").equals("true")){
                    Toast.makeText(ReSetPasswordActivity.this,jsonObject.getString("message"),Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(ReSetPasswordActivity.this,jsonObject.getString("message"),Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };
    private void change() {
        RsenUrlUtil.execute(RsenUrlUtil.URL_NSPW, new RsenUrlUtil.OnJsonResultListener() {
            @Override
            public void onNoNetwork(String msg) {
                ToastHelper.showToast(msg, Url.context);
            }

            @Override
            public Map getMap() {
                Map map = new HashMap();

//                try {
//                    map.put("newpassword", MD5.getMD5(newname));
//                    map.put("session_id", Url.SESSIONID);
//                    map.put("oldpassword", MD5.getMD5(oldname));
//
//                } catch (NoSuchAlgorithmException e) {
//                    e.printStackTrace();
//                }
                map.put("account", names);
                map.put("newpassword",newname);
                //map.put("session_id", Url.SESSIONID);
                map.put("verify", verify);
                return map;
            }

            @Override
            public void onParseJsonBean(List beans, JSONObject jsonObject) {

            }

            @Override
            public void onResult(boolean state, List beans) {
                if (state) {
                    ToastHelper.showToast("修改成功", Url.context);
                    finish();
                } else {
                    ToastHelper.showToast("修改失败，请重新输入", Url.context);
                }
            }
        });
    }


    private void initview() {
        time = new TimeCount(60000, 1000);//构造CountDownTimer对象
        tv_qd = (TextView) findViewById(R.id.tv_qd);
        registClose = (RelativeLayout) findViewById(R.id.registClose);
        new_agin_name = (EditText) findViewById(R.id.new_agin_name);
        verifyId = (EditText) findViewById(R.id.verifyId);
        new_name = (EditText) findViewById(R.id.new_name);
        mName = (EditText) findViewById(R.id.name);
        sendVerify = (Button) findViewById(R.id.sendVerify);
    }

    /* 定义一个倒计时的内部类 */
    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {//计时完毕时触发
            sendVerify.setText("重新验证");
            sendVerify.setClickable(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {//计时过程显示
            sendVerify.setClickable(false);
            sendVerify.setText(millisUntilFinished / 1000 + "秒");
        }
    }
}
