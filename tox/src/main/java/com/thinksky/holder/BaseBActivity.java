package com.thinksky.holder;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import com.bugtags.library.Bugtags;
import com.squareup.otto.Subscribe;
import com.thinksky.injection.ActivityComponent;
import com.thinksky.injection.ActivityModule;
import com.thinksky.injection.DaggerActivityComponent;
import com.thinksky.net.RpcCallManager;
import com.thinksky.ui.login.LoginEvent;
import com.thinksky.ui.login.LogoutEvent;
import dmax.dialog.SpotsDialog;
import rx.Observable;
import rx.Subscriber;


/**
 * Created by jiao on 2016/4/20.
 */
public class BaseBActivity extends AppCompatActivity {
  private SpotsDialog mProgressDialog;

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

  protected boolean isLogin() {
    return getComponent().loginSession().isLogin();
  }

  @Override
  public boolean dispatchTouchEvent(MotionEvent event) {
    //注：回调 3
    Bugtags.onDispatchTouchEvent(this, event);
    return super.dispatchTouchEvent(event);
  }

  public void showProgressDialog(String text, boolean cancelable) {
    mProgressDialog = new SpotsDialog(this);
    mProgressDialog.setTitle(text);
    mProgressDialog.setCancelable(cancelable);
    mProgressDialog.show();
  }

  public void showProgressDialog(int text, boolean cancelable) {
    mProgressDialog = new SpotsDialog(this);
    mProgressDialog.setTitle(text);
    mProgressDialog.setCancelable(cancelable);
    mProgressDialog.show();
  }

  public void closeProgressDialog() {
    if (null != mProgressDialog) {
      mProgressDialog.dismiss();
    }
  }

  /**
   * 当焦点停留在view上时，隐藏输入法栏
   *
   * @param view view
   */
  protected void hideInputWindow(View view) {
    InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

    if (null != imm && null != view) {
      imm.hideSoftInputFromWindow(view.getWindowToken(),
          InputMethodManager.HIDE_NOT_ALWAYS);
    }
  }

  /**
   * 当焦点停留在view上时，显示输入法栏
   *
   * @param view view
   */
  protected void showInputWindow(View view) {
    InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

    if (null != imm && null != view) {
      imm.showSoftInput(view, 0);
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
