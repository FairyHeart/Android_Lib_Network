package com.fairy.lib.network.factory

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import com.fairy.lib.network.ThreadSchedulers
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type
import java.util.concurrent.atomic.AtomicBoolean

/**
 * Retrofit 的Call适配成 LiveData
 *
 * @author: Fairy.
 * @date  : 2020/10/14.
 */
class LiveDataCallAdapter<T>(
    private val responseType: Type,
    private val threadSchedulers: ThreadSchedulers,//异常回调线程
    private val onFailure: (t: Throwable) -> Unit//异常回调函数
) : CallAdapter<T, LiveData<T>> {

    override fun responseType(): Type {
        return responseType
    }

    private val handler = Handler(Looper.getMainLooper())

    override fun adapt(call: Call<T>): LiveData<T> {
        return object : LiveData<T>() {
            private val started = AtomicBoolean(false)
            override fun onActive() {
                super.onActive()
                if (started.compareAndSet(false, true)) {//确保执行一次
                    call.enqueue(object : Callback<T> {
                        override fun onFailure(call: Call<T>, t: Throwable) {
                            when (threadSchedulers) {
                                ThreadSchedulers.CURRENT_THREAD -> {
                                    onFailure(t)
                                }
                                ThreadSchedulers.MAIN_THREAD -> {
                                    handler.post {
                                        onFailure(t)
                                    }
                                }
                                ThreadSchedulers.CHILD_THREAD -> {
                                    onFailure(t)
                                }
                            }
                        }

                        override fun onResponse(call: Call<T>, response: Response<T>) {
                            postValue(response.body())
                        }
                    })
                }
            }
        }
    }

}