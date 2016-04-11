package com.thinksky.tox;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.tox.BaseApi;


public class DiscoverSelectActivity extends FragmentActivity {
    private String session_id;
    private String userUid;
    private BaseApi baseApi;
    private final int FRIEND = 0;
    private final int FISH  = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseApi = new BaseApi();
        session_id = baseApi.getSeesionId();
        userUid = baseApi.getUid();
        initView();
    }

    private void initView() {
        setContentView(R.layout.activity_discover_select);
        ImageView back = (ImageView) findViewById(R.id.back_menu);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DiscoverSelectActivity.this.finish();
            }
        });
        //鱼友
        Button mark = (Button) findViewById(R.id.button_friend);
        mark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent intent = new Intent(DiscoverSelectActivity.this, DiscoverSendActivity.class);
                    intent.putExtra("fish",FRIEND);
                    startActivity(intent);

            }
        });
        // 渔场
        Button mark1 = (Button) findViewById(R.id.button_fishing);
        mark1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent intent = new Intent(DiscoverSelectActivity.this, DiscoverSendActivity.class);
                    intent.putExtra("fish",FISH);
                    startActivity(intent);

            }
        });
    }
}
