package com.fairy.lib.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.fairy.lib.network.dto.ResultDto
import com.fairy.lib.network.exception.NetworkException
import com.fairy.lib.network.exception.ReturnNullException

/**
 * LiveData拓展函数
 */
@Throws
fun <T> LiveData<ResultDto<T>>.filterStatus(): LiveData<T?> {
    return Transformations.map(this) {
        if (!it.isSuccess()) {
            throw NetworkException(code = it.errorCode, message = it.message)
        }
        it.data
    }
}

/**
 * Observable拓展函数
 * @param throwException 是否抛出异常
 */
@Throws
fun <T> LiveData<ResultDto<T>>.filterBoolStatus(
    throwException: Boolean = true
): LiveData<Boolean> {
    return Transformations.map(this) { t ->
        if (!t.isSuccess()) {
            if (throwException) {
                throw NetworkException(code = t.errorCode, message = t.message)
            } else {
                false
            }
        } else {
            true
        }
    }
}

/**
 * Observable拓展函数
 * @param successValue 验证成功的枚举值
 * @param throwException 是否抛出异常
 */
@Throws
fun <T> LiveData<ResultDto<T>>.filterStatus(
    successValue: String,
    throwException: Boolean = false
): LiveData<Boolean> {
    return Transformations.map(this) { t ->
        if (!t.isSuccess()) {
            throw NetworkException(code = t.errorCode, message = t.message)
        }
        if (t.data == null) {
            return@map false
        }
        if (successValue == t.data.toString()) {
            return@map true
        }
        if (throwException) {
            throw NetworkException(code = t.data.toString(), message = t.message)
        } else {
            false
        }
    }
}
