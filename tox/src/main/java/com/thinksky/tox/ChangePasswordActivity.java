package com.thinksky.tox;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.thinksky.holder.BaseBActivity;
import com.thinksky.rsen.RsenUrlUtil;
import com.thinksky.utils.MD5;
import com.tox.ToastHelper;
import com.tox.Url;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jiao on 2016/4/28.
 */
public class ChangePasswordActivity extends BaseBActivity {

    private TextView tv_qd;
    private RelativeLayout registClose;
    private EditText new_agin_name, old_name, new_name;
    private String oldname, newname, newnameagin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        initview();
        initdata();

    }

    private void initdata() {

        registClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tv_qd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                oldname = old_name.getText().toString().trim();

                newname = new_name.getText().toString().trim();
                newnameagin = new_agin_name.getText().toString().trim();
                if (oldname.isEmpty() || newname.isEmpty() || newnameagin.isEmpty()) {
                    ToastHelper.showToast("新密码旧密码不能为空", Url.context);
                } else {
                    if (oldname.equals(newname)) {
                        ToastHelper.showToast("新密码不能跟旧密码一致", Url.context);
                    } else if (!newname.equals(newnameagin)) {
                        ToastHelper.showToast("两次输入密码不一致，请重新输入", Url.context);
                    } else {
                        change();
                    }
                }
            }
        });

    }

    private void change() {
        RsenUrlUtil.execute(RsenUrlUtil.URL_RSPW, new RsenUrlUtil.OnJsonResultListener() {
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
                map.put("newpassword", MD5.md5(newname));
                map.put("session_id", Url.SESSIONID);
                map.put("oldpassword", MD5.md5(oldname));
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
        tv_qd = (TextView) findViewById(R.id.tv_qd);
        registClose = (RelativeLayout) findViewById(R.id.registClose);
        new_agin_name = (EditText) findViewById(R.id.new_agin_name);
        old_name = (EditText) findViewById(R.id.old_name);
        new_name = (EditText) findViewById(R.id.new_name);
    }
}
