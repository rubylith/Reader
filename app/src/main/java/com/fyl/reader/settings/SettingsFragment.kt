package com.fyl.reader.settings

import androidx.fragment.app.viewModels
import com.fyl.reader.R
import com.fyl.reader.base.BaseFragment
import com.fyl.reader.databinding.FragmentSettingsBinding
import com.fyl.reader.utils.STATE_ACTION_BAR_SHOW
import com.fyl.reader.utils.STATE_ACTION_BAR_UP_SHOW
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : BaseFragment<FragmentSettingsBinding>() {

    private val viewModel: SettingsViewModel by viewModels()

    override fun getActionBarState(): Int {
        return STATE_ACTION_BAR_SHOW or STATE_ACTION_BAR_UP_SHOW
    }

    override fun getActionBarTitle(): String {
        return applicationContext.getString(R.string.settings)
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_settings
    }

    override fun observeViewModel() {

    }
}