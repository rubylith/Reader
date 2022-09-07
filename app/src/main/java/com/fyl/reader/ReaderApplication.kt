package com.fyl.reader

import android.app.Application
import android.util.Log.DEBUG
import android.util.Log.ERROR
import androidx.work.Configuration
import com.fyl.reader.utils.LogUtils
import com.tencent.mmkv.MMKV
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ReaderApplication : Application(), Configuration.Provider {

    override fun getWorkManagerConfiguration(): Configuration =
        Configuration.Builder()
            .setMinimumLoggingLevel(if (BuildConfig.DEBUG) DEBUG else ERROR)
            .build()

    override fun onCreate() {
        super.onCreate()
        LogUtils.d(TAG, "ReaderApplication is created")
        application = this
        MMKV.initialize(this)
    }

    companion object {
        private const val TAG = "ReaderApplication"
        private lateinit var application: ReaderApplication

        fun get(): ReaderApplication {
            return application
        }
    }
}