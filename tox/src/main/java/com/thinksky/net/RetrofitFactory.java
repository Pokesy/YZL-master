/*
 * 文件名: RetrofitFactory
 * 版    权：  Copyright Hengrtech Tech. Co. Ltd. All Rights Reserved.
 * 描    述: [该类的简要描述]
 * 创建人: zhaozeyang
 * 创建时间:16/4/19
 *
 * 修改人：
 * 修改时间:
 * 修改内容：[修改内容]
 */
package com.thinksky.net;

import android.util.Log;
import com.thinksky.net.rpc.service.AppService;
import com.thinksky.net.rpc.service.NetConstant;
import java.io.IOException;
import java.lang.reflect.Field;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.schedulers.Schedulers;

/**
 * Retrofit 工厂类<BR>
 *
 * @author zhaozeyang
 * @version [Taobei Client V20160411, 16/4/19]
 */
public class RetrofitFactory {
  public static final String TAG = "RetrofitFactory";

  public static AppService createAppService() {
    Retrofit retrofit = new Retrofit.Builder().baseUrl(NetConstant.BASE_URL)
        .client(createNormalClient()).addConverterFactory(GsonConverterFactory.create()).
            addCallAdapterFactory(RxJavaCallAdapterFactory.create().createWithScheduler
                (Schedulers.newThread())).
            build();
    return retrofit.create(AppService.class);
  }

  private static OkHttpClient createClient() {
    HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor
        .Logger() {

      @Override
      public void log(String message) {
        Log.i(TAG, message);
      }
    });
    interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
    OkHttpClient client = new OkHttpClient.Builder()
        .addInterceptor(new Interceptor() {
          @Override
          public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            MediaType mediaType = request.body().contentType();
            try {
              Field field = mediaType.getClass().getDeclaredField("mediaType");
              field.setAccessible(true);
              field.set(mediaType, "application/json");
            } catch (NoSuchFieldException e) {
              e.printStackTrace();
            } catch (IllegalAccessException e) {
              e.printStackTrace();
            }
            return chain.proceed(request);
          }
        })
        .addInterceptor(interceptor).build();
    return client;
  }

  private static OkHttpClient createNormalClient() {
    HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor
        .Logger() {

      @Override
      public void log(String message) {
        Log.i(TAG, message);
      }
    });
    interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
    OkHttpClient client = new OkHttpClient.Builder()
        .addInterceptor(interceptor).build();
    return client;
  }

}
