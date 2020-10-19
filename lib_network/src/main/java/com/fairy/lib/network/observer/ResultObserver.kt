package com.fairy.lib.network.observer

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.fairy.lib.network.dto.ResultDto


/**
 * 返回结果统一处理
 *
 * @author: Fairy.
 * @date  : 2020/10/14.
 */
abstract class ResultObserver<T>(
    private val loading: MutableLiveData<Boolean>? = null,
    private val toast: MutableLiveData<String?>? = null
) : Observer<ResultDto<T>> {
    /**
     * Called when the data is changed.
     * @param it  The new data
     */
    override fun onChanged(it: ResultDto<T>?) {
        loading?.value = false
        if (it == null) {
            return
        }
        if (!it.isSuccess()) {
            this.onFailure(it.errorCode, it.message)
            toast?.value = it.message
            return
        }
        onSuccess(it.data)
    }


    abstract fun onSuccess(it: T?)

    open fun onFailure(errorCode: String?, errorMessage: String?) {}
}
