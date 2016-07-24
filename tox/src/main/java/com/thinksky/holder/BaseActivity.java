package com.thinksky.holder;

import android.app.Activity;
import android.os.Bundle;

import com.bugtags.library.Bugtags;
import com.thinksky.injection.DaggerGlobalComponent;
import com.thinksky.injection.GlobalModule;

public abstract class BaseActivity extends BaseBActivity {

	// 获取到前台进程的Activity
	private static Activity mForegroundActivity = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		init();
		initView();
		initActionbar();
	}

	/**
	 * 初始化actionbar
	 */
	protected abstract void initActionbar();

	/**
	 * 初始化界面
	 */
	protected abstract void initView();
	protected abstract void setLeftMenu();
	/**
	 * 初始化数据
	 */
	protected abstract void init();

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Bugtags.onResume(this);
		this.mForegroundActivity = this;
	}

	@Override
	protected void onPause() {
		super.onPause();
		Bugtags.onPause(this);
		this.mForegroundActivity = null;
	}
	@Override
	protected void onRestart() {

		setLeftMenu();
		super.onRestart();
		// createFargment(fragmentFlag);
	}

	@Override
	protected void onStart() {
		setLeftMenu();

		super.onStart();
	}
	public static Activity getForegroundActivity() {
		return mForegroundActivity;
	}
}
