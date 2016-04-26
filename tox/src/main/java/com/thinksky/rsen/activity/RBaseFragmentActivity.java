package com.thinksky.rsen.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.thinksky.holder.BaseBActivity;
import com.thinksky.rsen.fragment.RBaseFragment;
import com.thinksky.tox.R;

/**
 * Created by dajiao on 16-03-02-002.
 */
public abstract class RBaseFragmentActivity extends BaseBActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_r_layout);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        RBaseFragment baseFragment = getBaseFragment();
        fragmentTransaction.add(R.id.contain, baseFragment, baseFragment.getClass().getSimpleName()).commit();
    }

    protected abstract RBaseFragment getBaseFragment();
}
