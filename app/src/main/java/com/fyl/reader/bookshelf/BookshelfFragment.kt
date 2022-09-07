package com.fyl.reader.bookshelf

import android.view.Gravity
import androidx.fragment.app.viewModels
import com.fyl.reader.R
import com.fyl.reader.base.BaseFragment
import com.fyl.reader.databinding.FragmentBookshelfBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookshelfFragment : BaseFragment<FragmentBookshelfBinding>() {

    private val viewModel: BookshelfViewModel by viewModels()

    override fun getActionBarTitle(): String {
        return applicationContext.getString(R.string.bookshelf)
    }

    override fun getActionBarTitleGravity(): Int {
        return Gravity.CENTER
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_bookshelf
    }

    override fun observeViewModel() {

    }
}