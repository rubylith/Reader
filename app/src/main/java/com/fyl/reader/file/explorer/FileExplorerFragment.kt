package com.fyl.reader.file.explorer

import android.content.Intent
import android.provider.Settings
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.fyl.reader.R
import com.fyl.reader.base.AdapterFragment
import com.fyl.reader.databinding.FragmentFileExplorerBinding
import com.fyl.reader.utils.PermissionUtils
import com.fyl.reader.utils.STATE_ACTION_BAR_SHOW
import com.fyl.reader.utils.STATE_ACTION_BAR_UP_SHOW
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class FileExplorerFragment : AdapterFragment<FragmentFileExplorerBinding, File>() {

    private val viewModel: FileExplorerViewModel by viewModels()
    private var hasPermission = false
    private var adapter: FileExplorerAdapter? = null

    override fun onActivityResult(result: ActivityResult) {
        super.onActivityResult(result)
        hasPermission = PermissionUtils.checkStoragePermission(applicationContext)
    }

    override fun getActionBarState(): Int {
        return STATE_ACTION_BAR_SHOW or STATE_ACTION_BAR_UP_SHOW
    }

    override fun getActionBarTitle(): String {
        return applicationContext.getString(R.string.file_explorer)
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_file_explorer
    }

    override fun setupUI() {
        super.setupUI()
        hasPermission = PermissionUtils.checkStoragePermission(applicationContext)
        binding.permissionButton.setOnClickListener {
            activityResultLauncher?.launch(Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION))
        }
        adapter = FileExplorerAdapter().apply { addCallback(this@FileExplorerFragment) }
        binding.filesTreeView.layoutManager = LinearLayoutManager(applicationContext)
        binding.filesTreeView.adapter = adapter
    }

    override fun observeViewModel() {
        super.observeViewModel()
        viewModel.filesList.observe(this) {
            adapter?.updateParentPath(viewModel.currentDirectory?.parentFile?.path)
            adapter?.updateData(it)
        }
    }

    override fun onResume() {
        super.onResume()
        if (hasPermission) {
            binding.rationaleView.visibility = View.GONE
            binding.filesTreeView.visibility = View.VISIBLE
            viewModel.open(viewModel.topDirectory)
        } else {
            binding.rationaleView.visibility = View.VISIBLE
            binding.filesTreeView.visibility = View.GONE
        }
    }

    override fun onItemClick(data: File) {
        viewModel.open(data)
    }

    override fun onBackPressed(): Boolean {
        return if (viewModel.isInTopDirectory()) {
            false
        } else {
            viewModel.currentDirectory?.parentFile?.let {
                viewModel.open(it)
            }
            true
        }
    }
}