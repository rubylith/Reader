package com.fyl.reader

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.fyl.reader.base.BaseFragment
import com.fyl.reader.databinding.ActionBarBinding
import com.fyl.reader.databinding.ActivityMainBinding
import com.fyl.reader.utils.*
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "MainActivity"

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), View.OnClickListener {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private lateinit var actionBarBinding: ActionBarBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        actionBarBinding = binding.actionBar
        setListener()
        observeViewModel()
    }

    override fun onStart() {
        super.onStart()
        navController = Navigation.findNavController(binding.navHostFragment)
    }

    private fun setListener() {
        binding.actionBar.actionBarUp.setOnClickListener(this)
        binding.actionBar.actionBarAdd.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v) {
            actionBarBinding.actionBarUp -> navController.navigateUp()
            actionBarBinding.actionBarAdd -> navController.navigate(R.id.action_bookshelfFragment_to_fileExplorerFragment)
            null -> LogUtils.d(TAG, "onClick(): View is null")
        }
    }

   private fun observeViewModel() {
        viewModel.actionBarState.observe(this) {
            updateActionBarState(it)
        }
        viewModel.actionBarTitle.observe(this) {
            actionBarBinding.actionBarTitle.text = it
        }
        viewModel.actionBarTitleGravity.observe(this) {
            actionBarBinding.actionBarTitle.gravity = it
        }
    }

    private fun updateActionBarState(actionBarState: Int) {
        actionBarBinding.root.visibility = if ((actionBarState and STATE_ACTION_BAR_SHOW) == STATE_ACTION_BAR_SHOW) View.VISIBLE else View.GONE
        actionBarBinding.actionBarUp.visibility = if ((actionBarState and STATE_ACTION_BAR_UP_SHOW) == STATE_ACTION_BAR_UP_SHOW) View.VISIBLE else View.GONE
        actionBarBinding.actionBarMenu.visibility = if ((actionBarState and STATE_ACTION_BAR_MENU_SHOW) == STATE_ACTION_BAR_MENU_SHOW) View.VISIBLE else View.GONE
        actionBarBinding.actionBarAdd.visibility = if ((actionBarState and STATE_ACTION_BAR_ADD_SHOW) == STATE_ACTION_BAR_ADD_SHOW) View.VISIBLE else View.GONE
    }

    override fun onBackPressed() {
        val fragment = binding.navHostFragment.getFragment<NavHostFragment>().childFragmentManager.primaryNavigationFragment
        if (fragment !is BaseFragment<*> || !fragment.onBackPressed()) {
            super.onBackPressed()
        }
    }
}