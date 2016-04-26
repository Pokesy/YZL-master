package com.thinksky.tox;

import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.thinksky.holder.BaseBActivity;

public class SettingActivity extends BaseBActivity {
    private CheckBox yejianmoshi, tongzhi, baocunjindu;
    private RelativeLayout tupianjiazai, zitidaxiao, qingchuhuancun,
            setting_chongzhimima, yijianfankui, guoduandianzan, banbenjiance,
            guanyuqiubai;
    private TextView tupiantext, zititext;
    private Builder builder;
private ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);
        initView();
    }

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

    private class MyOnclickListener implements View.OnClickListener {
        public void onClick(View v) {
            int mID = v.getId();
            if (mID == R.id.back) {
                SettingActivity.this.finish();
            }

            if (mID == R.id.setting_qingchuhuancun) {
                Toast.makeText(SettingActivity.this, "点击清除缓存", Toast.LENGTH_LONG).show();
            }
            if (mID == R.id.setting_chongzhimima) {
                Intent intent3 = new Intent(SettingActivity.this, LoginActivity.class);
                startActivity(intent3);
            }
            if (mID == R.id.setting_yijianfankui) {
                Intent intent2 = new Intent(SettingActivity.this, Setting_yijianActivity.class);
                startActivity(intent2);
            }

            if (mID == R.id.setting_banbenjiance) {
                Toast.makeText(SettingActivity.this, "亲，已经是最新版本了", Toast.LENGTH_LONG).show();
            }
            if (mID == R.id.setting_guanyuqiubai) {
                Intent intent = new Intent(SettingActivity.this, AboutActivity.class);
                startActivity(intent);
            }
        }

    }
}
