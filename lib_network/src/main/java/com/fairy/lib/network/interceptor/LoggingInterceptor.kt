package com.fairy.lib.network.interceptor

import android.util.Log
import com.fairy.lib.network.RetrofitConfig
import okhttp3.Interceptor
import okhttp3.Response

/**
 * 输入日志拦截
 *
 * @author: Fairy.
 * @date  : 2020-01-07.
 */
class LoggingInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        if (RetrofitConfig.instance.debug) {
            Log.i(RetrofitConfig.instance.logName, request.url.toString())
            val body = response.peekBody(1024 * 1024)
            Log.i(RetrofitConfig.instance.logName, body.string())
        }
        return response
    }
}