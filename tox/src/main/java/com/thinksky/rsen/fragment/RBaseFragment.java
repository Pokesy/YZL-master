package com.thinksky.rsen.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thinksky.fragment.BaseFragment;
import com.thinksky.rsen.RViewHolder;
import com.thinksky.ui.basic.BasicFragment;


/**
 * Created by dajiao on 15-08-31-031.
 */
public abstract class RBaseFragment extends BasicFragment {

    protected AppCompatActivity mBaseActivity;
    protected ViewGroup rootView;
    protected boolean isCreate = false;
    protected RViewHolder mViewHolder;
    protected LayoutInflater mLayoutInflater;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onInitData(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mLayoutInflater = inflater;
        rootView = (ViewGroup) inflater.inflate(getBaseLayoutId(), container, false);
        mViewHolder = new RViewHolder(rootView);
        initView(rootView);
        initAfter();
        isCreate = true;
        initViewData();
        return rootView;
    }

    protected abstract int getBaseLayoutId();

    protected abstract void initViewData();

    protected void onInitData(Bundle savedInstanceState) {

    }

    protected abstract void initView(View rootView);

    protected void initAfter() {
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isCreate = false;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isCreate) {
            if (isVisibleToUser) {
                onShow();
            } else {
                onHide();
            }
        }
    }

    protected void onShow() {

    }

    protected void onHide() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mBaseActivity = (AppCompatActivity) context;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mBaseActivity = (AppCompatActivity) activity;
    }

    protected void e(String log) {
        Log.e(new Exception().getStackTrace()[0].getClassName(), log);
    }
}
