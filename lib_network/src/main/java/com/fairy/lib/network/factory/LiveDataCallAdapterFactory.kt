package com.fairy.lib.network.factory

import androidx.lifecycle.LiveData
import com.fairy.lib.network.ThreadSchedulers
import retrofit2.CallAdapter
import retrofit2.Response
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * LiveData数据源适配器
 *
 * @author: Fairy.
 * @date  : 2020/10/14.
 */
class LiveDataCallAdapterFactory(
    /**
     * 返回函数运行的线程
     */
    private val threadSchedulers: ThreadSchedulers = ThreadSchedulers.MAIN_THREAD,
    private val onFailure: (t: Throwable) -> Unit
) :
    CallAdapter.Factory() {

    /**
     * Returns a call adapter for interface methods that return `returnType`, or null if it
     * cannot be handled by this factory.
     */
    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        val responseType: Type

        if (getRawType(returnType) != LiveData::class.java) {
            throw IllegalStateException("return type must be parameterized")
        }
        val observableType = getParameterUpperBound(0, returnType as ParameterizedType)
        val rawObservableType = getRawType(observableType)
        responseType = if (rawObservableType == Response::class.java) {
            if (observableType !is ParameterizedType) {
                throw IllegalArgumentException("Response must be parameterized")
            }
            getParameterUpperBound(0, observableType)
        } else {
            observableType
        }
        return LiveDataCallAdapter<Any>(responseType, threadSchedulers, onFailure)
    }
}

