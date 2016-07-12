/*
 * 文件名: TitleBar
 * 版    权：  Copyright Hengrtech Tech. Co. Ltd. All Rights Reserved.
 * 描    述: [该类的简要描述]
 * 创建人: zhaozeyang
 * 创建时间:16/7/12
 * 
 * 修改人：
 * 修改时间:
 * 修改内容：[修改内容]
 */
package com.thinksky.ui.common;

import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.thinksky.tox.R;

/**
 * [一句话功  述]<BR>
 * [功 详 描述]
 *
 * @author zhaozeyang
 * @version [Taobei Client V20160411, 16/7/12]
 */
public class TitleBar extends FrameLayout implements ITitleBarOpertion {

  @Bind(R.id.title_bg)
  FrameLayout mTitleBgView;
  @Bind(R.id.left_img_menu)
  ImageView mLeftImgMenuView;
  @Bind(R.id.logo)
  ImageView mLogoView;
  @Bind(R.id.title)
  TextView mTitleView;
  @Bind(R.id.search)
  ImageView mSearchView;
  @Bind(R.id.text_btn_right)
  TextView mTextBtnRight;
  @Bind(R.id.toolbar)
  Toolbar mToolbar;

  public TitleBar(Context context, AttributeSet attrs) {
    super(context, attrs);
    initView();
  }

  private void initView() {
    View view = LayoutInflater.from(getContext()).inflate(R.layout.title_bar, this, false);
    ButterKnife.bind(this, view);
    addView(view);
  }

  public FrameLayout getTitleBgView() {
    return mTitleBgView;
  }

  public ImageView getMenuView() {
    return mLeftImgMenuView;
  }

  public ImageView getLogoView() {
    return mLogoView;
  }

  public TextView getTitleView() {
    return mTitleView;
  }

  public ImageView getSearchView() {
    return mSearchView;
  }

  public TextView getTextBtnRight() {
    return mTextBtnRight;
  }

  public Toolbar getToolbar() {
    return mToolbar;
  }

  @Override
  public void setLeftImgMenu(int drawableId, OnClickListener listener) {
    mLeftImgMenuView.setImageResource(drawableId);
    mLeftImgMenuView.setOnClickListener(listener);
    setLeftImgMenuVisible(true);
  }

  @Override
  public void setLeftImgMenuVisible(boolean visible) {
    mLeftImgMenuView.setVisibility(visible ? View.VISIBLE : View.GONE);
  }

  @Override
  public void setLogo(int drawableId) {
    mLogoView.setImageResource(drawableId);
    setLogoVisible(true);
  }

  @Override
  public void setLogoVisible(boolean visible) {
    mLogoView.setVisibility(visible ? View.VISIBLE : View.GONE);
  }

  @Override
  public void setMiddleTitle(String title) {
    mTitleView.setText(title);
    setMiddleTitleVisible(true);
  }

  @Override
  public void setMiddleTitle(int title) {
    mTitleView.setText(title);
    setMiddleTitleVisible(true);
  }

  @Override
  public void setMiddleTitleVisible(boolean visible) {
    mTitleView.setVisibility(visible ? View.VISIBLE : View.GONE);
  }

  @Override
  public void setRightTextBtn(String text, OnClickListener listener) {
    mTextBtnRight.setText(text);
    mTextBtnRight.setOnClickListener(listener);
    setRightTextBtnVisible(true);
  }

  @Override
  public void setRightTextBtn(int textId, OnClickListener listener) {
    mTextBtnRight.setText(textId);
    mTextBtnRight.setOnClickListener(listener);
    setRightTextBtnVisible(true);
  }

  @Override
  public void setRightTextBtnVisible(boolean visible) {
    mTextBtnRight.setVisibility(visible ? View.VISIBLE : View.GONE);
  }

  @Override
  public void setSearchBtn(int drawableId, OnClickListener listener) {
    mSearchView.setImageResource(drawableId);
    mSearchView.setOnClickListener(listener);
    setSearchBtnVisible(true);
  }

  @Override
  public void setSearchBtnVisible(boolean visible) {
    mSearchView.setVisibility(visible ? View.VISIBLE : View.GONE);
  }
}
