package com.lib.android_lib_network.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 *
 *
 * @author: Fairy.
 * @date  : 2020/10/19.
 */
open class BaseViewModel(
    val loadingState: MutableLiveData<Boolean>? = null,
    val toastState: MutableLiveData<String?>? = null
) : ViewModel() {
}