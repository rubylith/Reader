package com.fyl.reader.base

import androidx.databinding.ViewDataBinding

open class AdapterFragment<VDB : ViewDataBinding, D> : BaseFragment<VDB>(), BaseAdapter.Callback<D> {

    override fun onItemClick(data: D) {}
}