package com.fyl.reader.utils

import android.app.Activity
import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

object Utils {

    @JvmStatic
    fun getActivityByView(v: View): Activity? {
        return v.context.let {
            if (it is Activity) it else null
        }
    }

    @JvmStatic
    fun getDisplaySize(context: Context): Point {
        return Point().also {
            context.resources.displayMetrics.run {
                it.x = widthPixels
                it.y = heightPixels
            }
        }
    }

    @JvmStatic
    fun getWindowsSize(context: Context): Point {
        return Point().also {
            (context.getSystemService(Context.WINDOW_SERVICE) as? WindowManager)?.run {
                currentWindowMetrics.bounds.let { bounds ->
                    it.x = bounds.width()
                    it.y = bounds.height()
                }
            }
        }
    }

    @JvmStatic
    fun getWindowsBounds(context: Context): Rect {
        return (context.getSystemService(Context.WINDOW_SERVICE) as? WindowManager)?.currentWindowMetrics?.bounds ?: Rect()
    }

    @JvmStatic
    fun getWindowInsets(context: Context): Insets {
        return (context.getSystemService(Context.WINDOW_SERVICE) as? WindowManager)?.currentWindowMetrics?.windowInsets
            ?.getInsetsIgnoringVisibility(WindowInsets.Type.navigationBars() or WindowInsets.Type.displayCutout())
            ?: Insets.NONE
    }

    @JvmStatic
    fun getStatusBarHeight(context: Context): Int {
        return context.resources.run {
            getIdentifier("status_bar_height", "dimen", "android").takeIf { it > 0 }?.let {
                getDimensionPixelSize(it)
            } ?: 0
        }
    }

    @JvmStatic
    fun getNavigationBarHeight(context: Context): Int {
        return context.resources.run {
            getIdentifier("navigation_bar_height", "dimen", "android").takeIf { it > 0 }?.let {
                getDimensionPixelSize(it)
            } ?: 0
        }
    }

    @JvmStatic
    suspend fun saveIconToCacheDir(context: Context, icon: Bitmap): Result<File> = withContext(Dispatchers.IO) {
        runCatching {
            File(context.cacheDir, "icon.png").also {
                val fileOS = FileOutputStream(it)
                icon.compress(Bitmap.CompressFormat.PNG, 100, fileOS)
                fileOS.flush()
                fileOS.close()
            }
        }
    }

    @JvmStatic
    fun drawableToBitmap(drawable: Drawable): Bitmap {
        return if (drawable is BitmapDrawable) {
            drawable.bitmap
        } else {
            Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888).also {
                drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
                drawable.draw(Canvas(it))
            }
        }
    }
}