package com.fyl.reader.base

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<D, VH : BaseAdapter.ViewHolder> : RecyclerView.Adapter<VH>() {
    protected var data: List<D>? = null
    protected var callback: Callback<D>? = null

    fun updateData(data: List<D>) {
        this.data = data
        notifyItemRangeChanged(0, data.size)
    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    open fun addCallback(callback: Callback<D>) {
        this.callback = callback
    }

    abstract class ViewHolder(binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root)

    interface Callback<D> {
        fun onItemClick(data: D)
    }
}