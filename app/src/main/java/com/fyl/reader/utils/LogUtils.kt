package com.fyl.reader.utils

import android.util.Log

private const val TAG = "Reader"
private var DEBUG = Log.isLoggable(TAG, Log.DEBUG)

object LogUtils {
    @JvmStatic
    fun d(msg: String, tr: Throwable? = null) {
        Log.d(TAG, msg, tr)
    }

    @JvmStatic
    fun d(tag: String, msg: String, tr: Throwable? = null) {
        Log.d(TAG, "$tag # $msg", tr)
    }

    @JvmStatic
    fun w(msg: String, tr: Throwable? = null) {
        Log.w(TAG, msg, tr)
    }

    @JvmStatic
    fun w(tag: String, msg: String, tr: Throwable? = null) {
        Log.w(TAG, "$tag # $msg", tr)
    }

    @JvmStatic
    fun e(msg: String, tr: Throwable? = null) {
        Log.e(TAG, msg, tr)
    }

    @JvmStatic
    fun e(tag: String, msg: String, tr: Throwable? = null) {
        Log.e(TAG, "$tag # $msg", tr)
    }
}

object Loggable {
    @JvmStatic
    fun d(msg: String, tr: Throwable? = null) {
        if (DEBUG) Log.d(TAG, msg, tr)
    }

    @JvmStatic
    fun d(tag: String, msg: String, tr: Throwable? = null) {
        if (DEBUG) Log.d(TAG, "$tag # $msg", tr)
    }

    @JvmStatic
    fun w(msg: String, tr: Throwable? = null) {
        if (DEBUG) Log.w(TAG, msg, tr)
    }

    @JvmStatic
    fun w(tag: String, msg: String, tr: Throwable? = null) {
        if (DEBUG) Log.w(TAG, "$tag # $msg", tr)
    }

    @JvmStatic
    fun e(msg: String, tr: Throwable? = null) {
        if (DEBUG) Log.e(TAG, msg, tr)
    }

    @JvmStatic
    fun e(tag: String, msg: String, tr: Throwable? = null) {
        if (DEBUG) Log.e(TAG, "$tag # $msg", tr)
    }
}