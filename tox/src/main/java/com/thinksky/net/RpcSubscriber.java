package com.thinksky.net;

import rx.Subscriber;

public abstract class RpcSubscriber<T> extends Subscriber<T> {
  public RpcSubscriber() {
  }

  public void onCompleted() {
    this.onEnd();
  }

  public void onError(Throwable e) {
    this.onEnd();
  }

  public void onNext(T result) {
    this.onSucceed(result);
  }

  public void onEnd() {
  }

  public abstract void onSucceed(T var1);

  public abstract void onApiError(RpcApiError var1);

  public abstract void onHttpError(RpcHttpError var1);
}