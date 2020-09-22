package com.fairy.lib.network

import com.fairy.lib.network.dto.ResultDto
import com.fairy.lib.network.exception.NetworkException
import com.fairy.lib.network.exception.ReturnNullException
import io.reactivex.Observable
import io.reactivex.functions.Function

/**
 *
 *
 * @author: Fairy.
 * @date  : 2020/8/28.
 */

/**
 * Observable拓展函数
 */
@Throws
fun <T> Observable<ResultDto<T>>.filterStatus(): Observable<T> {
    return this.map { t ->
        if (!t.isSuccess()) {
            throw NetworkException(code = t.errorCode, message = t.message)
        }
        if (t.data == null) {
            throw ReturnNullException()
        }
        t.data
    }
}

/**
 * Observable拓展函数
 * @param successValue 验证成功的枚举值
 * @param throwException 是否抛出异常
 */
@Throws
fun <T> Observable<ResultDto<T>>.filterStatus(
    successValue: String,
    throwException: Boolean = false
): Observable<Boolean> {
    return this.map(object : Function<ResultDto<T>, Boolean> {
        override fun apply(t: ResultDto<T>): Boolean {
            if (!t.isSuccess()) {
                throw NetworkException(code = t.errorCode, message = t.message)
            }
            if (t.data == null) {
                return false
            }
            if (successValue == t.data.toString()) {
                return true
            }
            if (throwException) {
                throw NetworkException(code = t.data.toString(), message = t.message)
            } else {
                return false
            }
        }

    })
}

/**
 * Observable拓展函数
 * @param throwException 是否抛出异常
 */
@Throws
fun <T> Observable<ResultDto<T>>.filterBoolStatus(
    throwException: Boolean = true
): Observable<Boolean?> {
    return this.map { t ->
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