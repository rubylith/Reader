package com.fyl.reader

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.fyl.plugin.core.PluginListener
import com.fyl.plugin.core.PluginManagerWrapper
import com.fyl.plugin.test.IPluginTest
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

    private val pluginListener: PluginListener<IPluginTest> =
        object : PluginListener<IPluginTest> {

            override fun onPluginConnected(plugin: IPluginTest, pluginContext: Context) {
                Log.d(TAG, "onPluginConnected: sum=${plugin.getSum()} view=${plugin.getView()}")
            }

            override fun onPluginDisconnected(plugin: IPluginTest) {
                super.onPluginDisconnected(plugin)
                Log.d(TAG, "onPluginDisconnected: ")
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        actionBarBinding = binding.actionBar
        setListener()
        observeViewModel()
        PluginManagerWrapper.getInstance(this).addPluginListener(IPluginTest::class.java, pluginListener)
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

    override fun onDestroy() {
        super.onDestroy()
        PluginManagerWrapper.getInstance(this).removePluginListener(pluginListener)
    }
}