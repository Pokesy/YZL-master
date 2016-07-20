/*
 * 文件名: ActivityScope
 * 版    权：  Copyright Hengrtech Tech. Co. Ltd. All Rights Reserved.
 * 描    述: [该类的简要描述]
 * 创建人: zhaozeyang
 * 创建时间:16/4/18
 * 
 * 修改人：
 * 修改时间:
 * 修改内容：[修改内容]
 */
package com.thinksky.injection;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import javax.inject.Scope;

/*
 * 文件名: ActivityScope
 * 版    权：  Copyright Hengrtech Tech. Co. Ltd. All Rights Reserved.
 * 描    述: [该类的简要描述]
 * 创建人: zhaozeyang
 * 创建时间:16/4/18
 * 
 * 修改人：
 * 修改时间:
 * 修改内容：[修改内容]
 */
@Scope
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface ActivityScope {
}
