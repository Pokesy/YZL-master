/*
 * 文件名: ITitleBarOpertion
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

import android.view.View;

/**
 * [一句话功  述]<BR>
 * [功 详 描述]
 *
 * @author zhaozeyang
 * @version [Taobei Client V20160411, 16/7/12]
 */
public interface ITitleBarOpertion {
  void setLeftImgMenu(int drawableId, View.OnClickListener listener);

  void setLeftImgMenuVisible(boolean visible);

  void setLogo(int drawableId);

  void setLogoVisible(boolean visible);

  void setMiddleTitle(String title);

  void setMiddleTitle(int title);

  void setMiddleTitleVisible(boolean visible);

  void setRightTextBtn(String text, View.OnClickListener listener);

  void setRightTextBtn(int textId, View.OnClickListener listener);

  void setRightTextBtnVisible(boolean visible);

  void setSearchBtn(int drawableId, View.OnClickListener listener);

  void setSearchBtnVisible(boolean visible);
}