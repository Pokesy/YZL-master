package com.thinksky.net;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;
import com.thinksky.holder.BaseApplication;
import com.thinksky.log.Logger;
import com.thinksky.net.rpc.model.BaseModel;
import com.thinksky.net.rpc.service.NetConstant;
import rx.Subscriber;

/**
 * 简化的，供UI调用网络接口使用的RpcSubscriber，统一处理HttpError提示
 */
public abstract class UiRpcSubscriber1<T extends BaseModel> extends Subscriber<T> {
  private static final String TAG = "SERVER_ERROR";
  HttpErrorUiNotifier httpErrorUiNotifier;
  private Context mContext;

  public UiRpcSubscriber1(Context context) {
    mContext = context;
    httpErrorUiNotifier =
        ((BaseApplication) context.getApplicationContext()).getGlobalComponent()
            .httpErrorUiNotifier();
  }

  @Override
  public final void onCompleted() {
    onEnd();
  }

  @Override
  public final void onError(Throwable e) {
    onHttpError(new RpcHttpError(NetConstant.HttpCodeConstant.UNKNOWN_ERROR, ""));
    Logger.e(TAG, e, e.getMessage());
    onCompleted();
  }


  @Override
  public final void onNext(T t) {
    if (t.isSuccess()) {
      onSuccess(t);
      onCompleted();
    } else {
      onApiError(new RpcApiError(t.getError_code(), t.getMessage()));
      onCompleted();
    }
  }

  public void onApiError(RpcApiError apiError) {
    if (!TextUtils.isEmpty(apiError.getMessage())) {
      Toast.makeText(mContext, apiError.getMessage(), Toast.LENGTH_SHORT).show();
    }
  }

  public void onHttpError(RpcHttpError httpError) {
    httpErrorUiNotifier.notifyUi(httpError);
  }

  protected abstract void onSuccess(T t);

  protected abstract void onEnd();

}
