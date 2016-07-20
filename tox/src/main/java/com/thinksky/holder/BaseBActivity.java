package com.thinksky.holder;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import com.bugtags.library.Bugtags;
import com.thinksky.injection.ActivityComponent;
import com.thinksky.injection.ActivityModule;
import com.thinksky.injection.DaggerActivityComponent;
import com.thinksky.net.RpcCallManager;
import rx.Observable;
import rx.Subscriber;


/**
 * Created by jiao on 2016/4/20.
 */
public class BaseBActivity extends AppCompatActivity {
  private ActivityComponent mActivityComponent;
  private RpcCallManager.RpcCallManagerImpl rpcCallManager =
      new RpcCallManager.RpcCallManagerImpl();

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    createActivityComponent();
    getComponent().getGlobalBus().register(this);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    getComponent().getGlobalBus().unregister(this);
    rpcCallManager.unsubscribeAll();
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

}
