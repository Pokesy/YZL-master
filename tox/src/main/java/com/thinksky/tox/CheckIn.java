package com.thinksky.tox;

import android.os.Bundle;
import android.view.Window;

import com.thinksky.holder.BaseBActivity;

/**
 * Created by 王杰 on 2015/8/27.
 */
public class CheckIn extends BaseBActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.check_in);
    }
}
