package com.thinksky.holder;

import android.app.Application;
import android.app.Service;
import android.os.Handler;
import android.os.Looper;
import android.os.Vibrator;

import com.baidu.mapapi.SDKInitializer;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.thinksky.Face.LocationService;


public class BaseApplication extends Application {

	// 获取到主线程的上下文
	private static BaseApplication mContext = null;
	// 获取到主线程的handler
	private static Handler mMainThreadHandler = null;

	// 获取到主线程
	private static Thread mMainThread = null;
	// 获取到主线程的id
	private static int mMainThreadId;
	// 获取到主线程的looper
	private static Looper mMainThreadLooper = null;
	public LocationService locationService;
	public Vibrator mVibrator;
	@Override
	public void onCreate() {
		super.onCreate();
		this.mContext = this;
		this.mMainThreadHandler = new Handler();
		this.mMainThread = Thread.currentThread();
		this.mMainThreadId = android.os.Process.myTid();
		this.mMainThreadLooper = getMainLooper();

		ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(this));
//		//初始化百度地图
//		//  SDK初始化
//		IntentFilter filter = new IntentFilter();
//		filter.addAction(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR);
//		filter.addAction(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR);
//        registerReceiver(mSDKInitReceiver, filter);
		locationService = new LocationService(getApplicationContext());
		mVibrator =(Vibrator)getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
		SDKInitializer.initialize(this);
	}



	// 对外暴露上下文
	public static BaseApplication getApplication() {
		return mContext;
	}

	// 对外暴露主线程的handler
	public static Handler getMainThreadHandler() {
		return mMainThreadHandler;
	}

	// 对外暴露主线程
	public static Thread getMainThread() {
		return mMainThread;
	}

	// 对外暴露主线程id
	public static int getMainThreadId() {
		return mMainThreadId;
	}

	// 对外暴露主线程的looper
	public static Looper getMainThreadLooper() {
		return mMainThreadLooper;
	}
}
