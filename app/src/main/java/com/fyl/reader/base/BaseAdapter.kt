package com.fyl.reader.base

import android.annotation.SuppressLint
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<D, VH : BaseViewHolder> : RecyclerView.Adapter<VH>() {
    protected var data: List<D>? = null
    protected var callback: AdapterCallback<D>? = null

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(data: List<D>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    open fun addCallback(callback: AdapterCallback<D>) {
        this.callback = callback
    }
}

abstract class BaseViewHolder(binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root)

interface AdapterCallback<D> {
    fun onItemClick(data: D)
}