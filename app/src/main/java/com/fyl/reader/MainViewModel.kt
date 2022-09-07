package com.fyl.reader

import android.content.Context
import android.view.Gravity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fyl.reader.data.*
import com.tencent.mmkv.MMKV
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    @ApplicationContext context: Context,
    private val mmkv: MMKV
): ViewModel() {

    val actionBarState: MutableLiveData<Int> = MutableLiveData<Int>().apply {
        value = STATE_ACTION_BAR_SHOW or STATE_ACTION_BAR_MENU_SHOW or STATE_ACTION_BAR_ADD_SHOW
    }

    val actionBarTitle: MutableLiveData<String> = MutableLiveData<String>().apply {
        value = context.getString(R.string.app_name)
    }

    val actionBarTitleGravity: MutableLiveData<Int> = MutableLiveData<Int>().apply {
        value = Gravity.CENTER_VERTICAL
    }

    fun updateActionBarState(state: Int) {
        actionBarState.value = state
    }

    fun updateActionBarTitle(actionBarTitle: String) {
        this.actionBarTitle.value = actionBarTitle
    }

    fun updateActionBarTitleGravity(gravity: Int) {
        actionBarTitleGravity.value = gravity
    }
}