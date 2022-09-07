package com.fyl.reader.di

import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Resources
import com.fyl.reader.data.ReaderDatabase
import com.fyl.reader.data.UserDao
import com.tencent.mmkv.MMKV
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class ReaderModule {

    @Singleton
    @Provides
    fun provideReaderDatabase(@ApplicationContext context: Context): ReaderDatabase {
        return ReaderDatabase.getInstance(context)
    }

    @Provides
    fun provideUserDao(readerDatabase: ReaderDatabase): UserDao {
        return readerDatabase.userDao()
    }

    @Provides
    fun provideApplicationContext(@ApplicationContext context: Context): Context {
        return context
    }

    @Provides
    fun providePackageManager(@ApplicationContext context: Context): PackageManager {
        return context.packageManager
    }

    @Provides
    fun provideResources(@ApplicationContext context: Context): Resources {
        return context.resources
    }

    @Provides
    fun provideMMKV(): MMKV {
        return MMKV.defaultMMKV()
    }
}
