package com.fairy.lib.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.fairy.lib.network.dto.ResultDto
import com.fairy.lib.network.exception.NetworkException
import com.fairy.lib.network.exception.ReturnNullException
import io.reactivex.Observable
import io.reactivex.functions.Function

/**
 * LiveData拓展函数
 */
@Throws
fun <T> LiveData<ResultDto<T>>.filterStatus(): LiveData<T> {
    return Transformations.map(this) {
        if (!it.isSuccess()) {
            throw NetworkException(code = it.errorCode, message = it.message)
        }
        if (it.data == null) {
            throw ReturnNullException()
        }
        it.data
    }
}
