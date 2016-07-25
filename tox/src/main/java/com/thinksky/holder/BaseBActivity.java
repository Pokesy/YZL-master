package com.thinksky.holder;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bugtags.library.Bugtags;
import com.squareup.otto.Subscribe;
import com.thinksky.injection.ActivityComponent;
import com.thinksky.injection.ActivityModule;
import com.thinksky.injection.DaggerActivityComponent;
import com.thinksky.net.RpcCallManager;
import com.thinksky.ui.login.LoginEvent;
import com.thinksky.ui.login.LogoutEvent;
import rx.Observable;
import rx.Subscriber;


/**
 * Created by jiao on 2016/4/20.
 */
public class BaseBActivity extends AppCompatActivity {
  private MaterialDialog mProgressDialog;

  private ActivityComponent mActivityComponent;
  private RpcCallManager.RpcCallManagerImpl rpcCallManager =
      new RpcCallManager.RpcCallManagerImpl();

  private boolean mIsDestroyed;
  private SessionHandler mSessionHandler = new SessionHandler();

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    createActivityComponent();
    getComponent().getGlobalBus().register(this);
    getComponent().getGlobalBus().register(mSessionHandler);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    mIsDestroyed = true;
    getComponent().getGlobalBus().unregister(this);
    getComponent().getGlobalBus().unregister(mSessionHandler);
    rpcCallManager.unsubscribeAll();
    if (null != mProgressDialog) {
      mProgressDialog.dismiss();
    }
  }

  @Override
  public void finish() {
    mIsDestroyed = true;
    super.finish();
  }

  @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
  public boolean hasDestroyed() {
    return isFinishing() || mIsDestroyed || ((Build.VERSION.SDK_INT >= Build.VERSION_CODES
        .JELLY_BEAN_MR1 && isDestroyed()));
  }

  private void createActivityComponent() {
    mActivityComponent = DaggerActivityComponent.builder().activityModule(new ActivityModule(this))
        .globalComponent(((BaseApplication) getApplication()).getGlobalComponent())
        .build();
  }

  protected ActivityComponent getComponent() {
    return mActivityComponent;
  }

  public <T> void manageRpcCall(Observable<T> observable, Subscriber<T> subscribe) {
    rpcCallManager.manageRpcCall(observable, subscribe);
  }

  @Override
  protected void onResume() {
    super.onResume();
    //注：回调 1
    Bugtags.onResume(this);
  }

  @Override
  protected void onPause() {
    super.onPause();
    //注：回调 2
    Bugtags.onPause(this);
  }

  @Override
  public boolean dispatchTouchEvent(MotionEvent event) {
    //注：回调 3
    Bugtags.onDispatchTouchEvent(this, event);
    return super.dispatchTouchEvent(event);
  }

  public void showProgressDialog(String text, boolean cancelable) {
    mProgressDialog = new MaterialDialog.Builder(this).content(text)
        .progress(true, 0)
        .cancelable(cancelable).build();
    mProgressDialog.show();
  }

  public void showProgressDialog(int text, boolean cancelable) {
    mProgressDialog = new MaterialDialog.Builder(this).content(text)
        .progress(true, 0)
        .cancelable(cancelable).build();
    mProgressDialog.show();
  }

  public void closeProgressDialog() {
    if (null != mProgressDialog) {
      mProgressDialog.dismiss();
    }
  }

  protected void onLogin() {

  }

  protected void onLogout() {

  }

  class SessionHandler {
    @Subscribe
    public void handleLoginEvent(LoginEvent event) {
      onLogin();
    }

    @Subscribe
    public void handleLogoutEvent(LogoutEvent event) {
      onLogout();
    }
  }

}
