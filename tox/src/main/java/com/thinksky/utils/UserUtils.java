/*
 * 文件名: UserUtils
 * 版    权：  Copyright Hengrtech Tech. Co. Ltd. All Rights Reserved.
 * 描    述: [该类的简要描述]
 * 创建人: zhaozeyang
 * 创建时间:16/9/1
 * 
 * 修改人：
 * 修改时间:
 * 修改内容：[修改内容]
 */
package com.thinksky.utils;

import android.content.Context;
import android.text.TextUtils;
import com.thinksky.tox.R;

/**
 * [一句话功能简述]<BR>
 * [功能详细描述]
 *
 * @author zhaozeyang
 * @version [Taobei Client V20160411, 16/9/1]
 */
public class UserUtils {
  public static String getUserName(Context context, String userId, String userName) {
    return TextUtils.isEmpty(userId) ? context.getString(R.string.activity_user_not_exists) :
        userName;
  }
}
