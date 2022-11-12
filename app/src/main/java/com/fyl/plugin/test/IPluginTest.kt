package com.fyl.plugin.test

import android.view.View
import com.fyl.plugin.core.Plugin
import com.fyl.plugin.core.ProvidesActionInterface

/**
 * PackageManager扫描用的Action字串必须要有，不能为空
 */
const val action = "com.fyl.plugin.test.plugintest"

/**
 * 版本号一般大于1
 */
const val version = 1

@ProvidesActionInterface(action = action, version = version)
interface IPluginTest : Plugin {
    /**
     * 根据接口业务逻辑自定义方法
     * @return
     */
    fun getSum(): Int

    /**
     * 根据接口业务逻辑自定义方法
     * @return
     */
    fun getView(): View?
}