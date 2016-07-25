package com.thinksky.ui.basic;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.Toast;
import com.squareup.otto.Subscribe;
import com.thinksky.holder.BaseApplication;
import com.thinksky.injection.ActivityComponent;
import com.thinksky.injection.ActivityModule;
import com.thinksky.injection.DaggerActivityComponent;
import com.thinksky.net.RpcCallManager;
import com.thinksky.ui.login.LoginEvent;
import com.thinksky.ui.login.LogoutEvent;
import rx.Observable;
import rx.Subscriber;

public abstract class BasicFragment extends Fragment implements RpcCallManager {
  private final String mClassName = getClass().getSimpleName();

  /**
   *   是否进入pause状态
   */
  private boolean mIsPaused;

  private ActivityComponent mActivityComponent;

  protected RpcCallManagerImpl rpcCallManager = new RpcCallManagerImpl();

  private Object mSessionEventsHandler = new SessionEventsHandler();

  public ActivityComponent createComponent(Activity activity) {
    return DaggerActivityComponent.builder().activityModule(new ActivityModule(activity))
        .globalComponent(((BaseApplication) activity.getApplication()).getGlobalComponent())
        .build();
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    mActivityComponent = createComponent(getActivity());
    super.onCreate(savedInstanceState);
    getComponent().getGlobalBus().register(this);
    getComponent().getGlobalBus().register(mSessionEventsHandler);
  }

  @Override
  public void onResume() {
    super.onResume();
    mIsPaused = false;
  }

  @Override
  public void onPause() {
    super.onPause();
    mIsPaused = true;
  }

  public ActivityComponent getComponent() {
    return mActivityComponent;
  }

  //protected UserComponent getUserComponent() {
  //  return ((CustomerApp) getActivity().getApplicationContext()).getUserComponent();
  //}

  /**
   * 是否为已经登录<BR>
   *
   * @return 是否为已经登录
   */
  //public boolean hasLogin() {
  //  return getComponent().authenticator().isLoggedIn();
  //}

  /**
   * 显示进度框<BR>
   *
   * @param message 对话框显示信息
   * @param cancelable 对话框可取消标志
   */
  protected void showProgressDialog(String message, boolean cancelable) {
    //if (mProgressDialog == null) {
    //  //mProDialog = new ProgressDialog(getActivity(), cancelable);
    //  mProgressDialog = new SpotsDialog(getActivity());
    //  mProgressDialog.setCancelable(cancelable);
    //  mProgressDialog.setCanceledOnTouchOutside(cancelable);
    //}
    //mProgressDialog.setMessage(message);
    //showProgressDialog(mProgressDialog);
  }

  /**
   * 弹出进度框<BR>
   *
   * @param proDialog 对话框显示信息
   */
  protected void showProgressDialog(AlertDialog proDialog) {
    if (!mIsPaused) {
      proDialog.show();
    }
  }

  protected boolean isPaused() {
    return mIsPaused;
  }

  /**
   * 显示进度框<BR>
   *
   * @param message 对话框显示信息
   */
  protected void showProgressDialog(String message) {
    // 默认可取消当前对话框
    showProgressDialog(message, true);
  }

  /**
   * 显示进度框<BR>
   *
   * @param msgResId 对话框显示信息
   */
  protected void showProgressDialog(int msgResId) {
    showProgressDialog(getResources().getString(msgResId), true);
  }

  /**
   * 显示进度框<BR>
   *
   * @param msgResId 对话框显示信息
   * @param cancelable 是否可取消的标志
   */
  protected void showProgressDialog(int msgResId, boolean cancelable) {
    showProgressDialog(getResources().getString(msgResId), cancelable);
  }

  /**
   * 关闭进度框<BR>
   */
  protected void closeProgressDialog() {
    // 关闭ProgressDialog
    //if (null != mProgressDialog) {
    //  mProgressDialog.dismiss();
    //  mProgressDialog = null;
    //}
  }

  /**
   * 显示对话框:有头部，只有确定按钮
   *
   * @param msg 信息
   * @param listener Dialog中按钮的点击监听
   */
  //public void showPromptDialog(String msg, final DialogUtil.OnDialogBtnClickListener listener) {
  //  closePromptDialog();
  //  mPromptDialog =
  //      DialogUtil.showPromptDialogWithButton(getActivity(), msg, getString(R.string
  // .dialog_btn_ok),
  //          null, new DialogUtil.OnDialogBtnClickListener() {
  //            @Override
  //            public void onPositiveBtnClick() {
  //              closePromptDialog();
  //              if (null != listener) {
  //                listener.onPositiveBtnClick();
  //              }
  //            }
  //
  //            @Override
  //            public void onNegativeBtnClick() {
  //            }
  //          });
  //}

  /**
   * 显示对话框:有头部，有确定和取消按钮
   *
   * @param msg 信息
   * @param listener 信息
   */
  //public void showPromptDialogWithButton(String msg,
  //                                       final DialogUtil.OnDialogBtnClickListener listener) {
  //  closePromptDialog();
  //  mPromptDialog = DialogUtil.showPromptDialogWithButton(getActivity(), msg,
  //      new DialogUtil.OnDialogBtnClickListener() {
  //        @Override
  //        public void onPositiveBtnClick() {
  //          closePromptDialog();
  //          if (null != listener) {
  //            listener.onPositiveBtnClick();
  //          }
  //        }
  //
  //        @Override
  //        public void onNegativeBtnClick() {
  //          closePromptDialog();
  //          if (null != listener) {
  //            listener.onNegativeBtnClick();
  //          }
  //        }
  //      });
  //}

  /**
   * 显示对话框:有头部，有按钮
   *
   * @param prompt 提示信息
   * @param okBtnStr 确定按钮的文本显示
   * @param cancelBtnStr 取消按钮的文本显示
   * @param listener Dialog中按钮的点击监听
   */
  //public void showPromptDialogWithButton(String prompt, String okBtnStr, String cancelBtnStr,
  //                                       final DialogUtil.OnDialogBtnClickListener listener) {
  //  closePromptDialog();
  //  mPromptDialog =
  //      DialogUtil.showPromptDialogWithButton(getActivity(), prompt, okBtnStr, cancelBtnStr,
  //          new DialogUtil.OnDialogBtnClickListener() {
  //            @Override
  //            public void onPositiveBtnClick() {
  //              closePromptDialog();
  //              if (null != listener) {
  //                listener.onPositiveBtnClick();
  //              }
  //            }
  //
  //            @Override
  //            public void onNegativeBtnClick() {
  //              closePromptDialog();
  //              if (null != listener) {
  //                listener.onNegativeBtnClick();
  //              }
  //            }
  //          });
  //}

  /**
   * 显示对话框:有头部，有内容，有按钮
   *
   * @param prompt 提示信息头部
   * @param content 提示信息内容
   * @param okBtnStr 确定按钮的文本显示
   * @param cancelBtnStr 取消按钮的文本显示
   * @param listener Dialog中按钮的点击监听
   */
  //public void showPromptDialogWithButton(String prompt, String content, String okBtnStr,
  //                                       String cancelBtnStr, final DialogUtil
  // .OnDialogBtnClickListener listener) {
  //  closePromptDialog();
  //  mPromptDialog = DialogUtil.showPromptDialogWithButton(getActivity(), prompt, content,
  // okBtnStr,
  //      cancelBtnStr, new DialogUtil.OnDialogBtnClickListener() {
  //        @Override
  //        public void onPositiveBtnClick() {
  //          closePromptDialog();
  //          if (null != listener) {
  //            listener.onPositiveBtnClick();
  //          }
  //        }
  //
  //        @Override
  //        public void onNegativeBtnClick() {
  //          closePromptDialog();
  //          if (null != listener) {
  //            listener.onNegativeBtnClick();
  //          }
  //        }
  //      });
  //}

  /**
   * 关闭提示框<BR>
   */
  //protected void closePromptDialog() {
  //  if (null != mPromptDialog) {
  //    mPromptDialog.dismiss();
  //    mPromptDialog = null;
  //  }
  //}

  /**
   * 显示短时间的提示内容<BR>
   *
   * @param content 提示内容
   */
  protected void showShortToast(String content) {
    Toast.makeText(getActivity(), content, Toast.LENGTH_SHORT).show();
  }

  /**
   * 显示toast<BR>
   *
   * @param resId 消息资源Id
   */
  protected void showShortToast(int resId) {
    Toast.makeText(getActivity(), resId, Toast.LENGTH_SHORT).show();
  }

  //protected boolean logInIfNot() {
  //  if (!hasLogin()) {
  //    startActivity(LoginActivity.makeLoginIntent(getActivity()));
  //    return false;
  //  }
  //  return true;
  //}

  public <T> void manageRpcCall(Observable<T> observable, Subscriber<T> subscribe) {
    rpcCallManager.manageRpcCall(observable, subscribe);
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    rpcCallManager.unsubscribeAll();
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    closeProgressDialog();
    getComponent().getGlobalBus().unregister(mSessionEventsHandler);
    getComponent().getGlobalBus().unregister(this);
  }


  protected void onLogin() {

  }

  protected void onLogout() {

  }

  private class SessionEventsHandler {
    @Subscribe
    public void dispatchLogin(LoginEvent event) {
      onLogin();
    }

    @Subscribe
    public void dispatchLogout(LogoutEvent event) {
      onLogout();
    }
  }
}