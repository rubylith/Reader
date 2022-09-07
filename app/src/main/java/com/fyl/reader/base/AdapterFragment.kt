package com.fyl.reader.base

import androidx.databinding.ViewDataBinding

open class AdapterFragment<VDB : ViewDataBinding, D> : BaseFragment<VDB>(), AdapterCallback<D> {

    override fun onItemClick(data: D) {}
}