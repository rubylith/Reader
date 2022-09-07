package com.fyl.reader.base

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.fyl.reader.*
import com.fyl.reader.utils.LogUtils
import javax.inject.Inject

private const val TAG = "BaseFragment"

open class BaseFragment<VDB : ViewDataBinding> : Fragment(), DefaultLifecycleObserver, FragmentCallback {

    protected val sharedViewModel: MainViewModel by activityViewModels()
    protected lateinit var binding: VDB
    @Inject lateinit var applicationContext: Context
    protected var activityResultLauncher: ActivityResultLauncher<Intent>? = null
    protected var permissionLauncher: ActivityResultLauncher<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super<Fragment>.onCreate(savedInstanceState)
        activityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            onActivityResult(it)
        }
        permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            onPermissionResult(it)
        }
    }

    open fun onActivityResult(result: ActivityResult) {}
    open fun onPermissionResult(result: Boolean) {}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sharedViewModel.updateActionBarState(getActionBarState())
        sharedViewModel.updateActionBarTitle(getActionBarTitle())
        sharedViewModel.updateActionBarTitleGravity(getActionBarTitleGravity())
        return getLayoutRes().takeIf { it > 0 }?.let {
            binding = DataBindingUtil.inflate(inflater, it, container, false)
            binding.root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        observeViewModel()
    }

    override fun onCreate(owner: LifecycleOwner) {
        LogUtils.d(TAG, "Activity is created")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity?.lifecycle?.addObserver(this)
    }

    override fun onDetach() {
        super.onDetach()
        activity?.lifecycle?.removeObserver(this)
    }

    open fun getActionBarState(): Int {
        return STATE_ACTION_BAR_SHOW or STATE_ACTION_BAR_MENU_SHOW or STATE_ACTION_BAR_ADD_SHOW
    }

    open fun getActionBarTitle(): String {
        return applicationContext.getString(R.string.app_name)
    }

    open fun getActionBarTitleGravity(): Int {
        return Gravity.CENTER_VERTICAL
    }

    open fun getLayoutRes(): Int {
        return 0
    }

    open fun setupUI() {}
    open fun observeViewModel() {}
    override fun onBackPressed(): Boolean { return false; }
}

interface FragmentCallback {
    fun onBackPressed(): Boolean
}