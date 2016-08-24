/*
 * 文件名: ProfileIntentFactory
 * 版    权：  Copyright Hengrtech Tech. Co. Ltd. All Rights Reserved.
 * 描    述: [该类的简要描述]
 * 创建人: zhaozeyang
 * 创建时间:16/8/5
 * 
 * 修改人：
 * 修改时间:
 * 修改内容：[修改内容]
 */
package com.thinksky.ui.profile;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.tox.Url;

/**
 * [一句话功能简述]<BR>
 * [功能详细描述]
 *
 * @author zhaozeyang
 * @version [Taobei Client V20160411, 16/8/5]
 */
public class ProfileIntentFactory {
  public static Intent makeIntent(Context context, String userId) {
    if (TextUtils.equals(userId, Url.USERID)) {
      return new Intent(context, MyProfileActivity.class);
    } else {
      return OtherProfileActivity.makeIntent(context, userId);
    }
  }
}
