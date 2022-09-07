package com.fyl.reader.file.explorer

import android.view.LayoutInflater
import android.view.ViewGroup
import com.fyl.reader.R
import com.fyl.reader.base.BaseAdapter
import com.fyl.reader.base.BaseViewHolder
import com.fyl.reader.databinding.FileExplorerListItemBinding
import java.io.File

class FileExplorerAdapter : BaseAdapter<File, FileExplorerViewHolder>() {

    private var parentPath: String? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileExplorerViewHolder {
        return FileExplorerViewHolder(FileExplorerListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)).apply {
            binding.root.setOnClickListener {
                data?.get(layoutPosition)?.let {
                    callback?.onItemClick(it)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: FileExplorerViewHolder, position: Int) {
        holder.onBind(parentPath, data?.get(position))
    }

    fun updateParentPath(path: String?) {
        parentPath = path
    }
}

class FileExplorerViewHolder(
    val binding: FileExplorerListItemBinding
) : BaseViewHolder(binding) {

    private val context = binding.root.context

    fun onBind(parentPath: String?, file: File?) {
        file?.let {
            if (it.path == parentPath) {
                binding.text.text = context.getString(R.string.go_parent_label)
            } else {
                binding.text.text = if (it.isDirectory) {
                    context.getString(R.string.folder_item, it.name)
                } else {
                    context.getString(R.string.file_item, it.name)
                }
            }
        }
    }
}