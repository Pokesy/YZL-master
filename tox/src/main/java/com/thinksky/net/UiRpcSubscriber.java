package com.thinksky.net;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;
import com.thinksky.holder.BaseApplication;
import com.thinksky.log.Logger;
import com.thinksky.net.rpc.model.ResponseModel;
import com.thinksky.net.rpc.service.NetConstant;
import retrofit2.Response;
import rx.Subscriber;

/**
 * 简化的，供UI调用网络接口使用的RpcSubscriber，统一处理HttpError提示
 */
public abstract class UiRpcSubscriber<T> extends Subscriber<Response<ResponseModel<T>>> {
  private static final String TAG = "SERVER_ERROR";
  HttpErrorUiNotifier httpErrorUiNotifier;
  SessionNotifier sessionNotifier;
  private Context mContext;

  public UiRpcSubscriber(Context context) {
    mContext = context;
    httpErrorUiNotifier =
        ((BaseApplication) context.getApplicationContext()).getGlobalComponent()
            .httpErrorUiNotifier();
    sessionNotifier = ((BaseApplication) context.getApplicationContext()).getGlobalComponent()
        .sessionNotifier();
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
  public final void onNext(Response<ResponseModel<T>> responseModelResponse) {
    if (null == responseModelResponse || null == responseModelResponse.body()) {
      onHttpError(new RpcHttpError(NetConstant.HttpCodeConstant.UNKNOWN_ERROR, ""));
      return;
    }

    if (responseModelResponse.body().getResult() == NetConstant.HttpCodeConstant.SESSION_EXPIRED) {
      onSessionExpired();
      return;
    }

    if (responseModelResponse.code() == NetConstant.HttpCodeConstant.SUCCESS
        && responseModelResponse.body().getResult() == 1) {
      // 存储token
      //if (!TextUtils.isEmpty(responseModelResponse.body().getToken())) {
      //  ((BaseApplication) mContext.getApplicationContext()).getGlobalComponent()
      // .appPreferences().put
      //      (CustomAppPreferences.KEY_COOKIE_SESSION_ID,
      //          responseModelResponse.body().getToken());
      //}
      if (!responseModelResponse.body().isSuccess()) {
        onApiError(new RpcApiError(responseModelResponse.body().getResult(), responseModelResponse
            .body().getMessage()));
        onCompleted();
        return;
      }
      onSuccess(responseModelResponse.body().getData());
      return;
    }
    if (responseModelResponse.code() == NetConstant.HttpCodeConstant.HTTP_ERROR_NOT_FOUND) {
      onHttpError(new RpcHttpError(responseModelResponse.code(), responseModelResponse.message()));
      return;
    }
    onApiError(new RpcApiError(responseModelResponse.body().getResult(), responseModelResponse
        .body().getMessage()));
    onCompleted();
  }

  public void onApiError(RpcApiError apiError) {
    if (!TextUtils.isEmpty(apiError.getMessage())) {
      Toast.makeText(mContext, apiError.getMessage(), Toast.LENGTH_SHORT).show();
    }
  }

  private void onSessionExpired() {
    sessionNotifier.notifySessionExpired();
    onCompleted();
  }

  public void onHttpError(RpcHttpError httpError) {
    httpErrorUiNotifier.notifyUi(httpError);
  }

  protected abstract void onSuccess(T t);

  protected abstract void onEnd();

}
