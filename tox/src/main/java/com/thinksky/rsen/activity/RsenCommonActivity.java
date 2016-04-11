package com.thinksky.rsen.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.thinksky.rsen.RViewHolder;
import com.thinksky.tox.R;

public class RsenCommonActivity extends AppCompatActivity {

    public static final String KEY_TYPE = "key_type";//
    public static final int TYPE_QDETAIL = 1;//问题详情

    RViewHolder viewHolder;
    View containLayout;

    public static void showActivity(Context context, int type , Bundle bundle) {
        Intent intent = new Intent("com.angcyo.rsen");
        if (bundle == null) {
            bundle = new Bundle();
        }
        switch (type) {
            case TYPE_QDETAIL:
                bundle.putInt(KEY_TYPE, TYPE_QDETAIL);
                break;
            default:
                break;
        }
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rsen_common);
        containLayout = findViewById(R.id.contain);
        viewHolder = new RViewHolder(containLayout);

        initActivity();
    }

    private void initActivity() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            switch (extras.getInt(KEY_TYPE)) {
                case TYPE_QDETAIL:
                    break;
                default:
                    break;
            }
        }
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        //fragmentTransaction.setCustomAnimations();//动画效果
        fragmentTransaction.replace(R.id.contain, fragment, fragment.getClass().getSimpleName());
        fragmentTransaction.commit();
    }
}
