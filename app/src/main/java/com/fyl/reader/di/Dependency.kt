package com.fyl.reader.di

import android.content.Context
import android.content.res.Resources
import com.fyl.reader.ReaderApplication
import com.tencent.mmkv.MMKV
import dagger.hilt.EntryPoint
import dagger.hilt.EntryPoints
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface Dependency {
    fun getContext(): Context
    fun getResource(): Resources
    fun getMMKV(): MMKV
}

fun getDependency(): Dependency {
    return EntryPoints.get(ReaderApplication.get(), Dependency::class.java)
}