package com.fairy.lib.network.interceptor

import okhttp3.FormBody
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * 添加公用参数
 *      Header参数所有请求都会添加
 *      Get请求公用参数都会添加，Post请求只有contentType = application/x-www-form-urlencoded 才会添加
 *
 * @author: Fairy.
 * @date  : 2020-01-07.
 */
class HttpCommonInterceptor : Interceptor {

    private var mHeaderParamsMap = mutableMapOf<String, String>()
    private var mUrlParamsMap = mutableMapOf<String, String>()

    override fun intercept(chain: Interceptor.Chain): Response {
        val oldRequest: Request = chain.request()

        // 新的请求
        val requestBuilder: Request.Builder = oldRequest.newBuilder()
        requestBuilder.method(oldRequest.method, oldRequest.body)

        //添加公共参数,添加到header中
        if (!mHeaderParamsMap.isNullOrEmpty()) {
            for ((key, value) in mHeaderParamsMap.entries) {
                requestBuilder.header(key, value)
            }
        }
        var newRequest: Request = requestBuilder.build()

        //添加公用参数，添加到参数中(Get添加到url上，Post添加到消息体里面)
        val method = oldRequest.method
        if (method.equals("Get", ignoreCase = true) && !mUrlParamsMap.isNullOrEmpty()) {
            var modifiedBuilder = oldRequest.url.newBuilder()
            for ((key, value) in mUrlParamsMap.entries) {
                modifiedBuilder.addQueryParameter(key, value)
            }
            var modifiedUrl = modifiedBuilder.build()
            newRequest = oldRequest.newBuilder().url(modifiedUrl).build()
        } else if (method.equals("Post", ignoreCase = true) && !mUrlParamsMap.isNullOrEmpty()) {
            if (oldRequest.body is FormBody) {//application/x-www-form-urlencoded 只有这一种格式下会添加公用参数，其他格式不会添加
                val bodyBuilder = FormBody.Builder()
                var formBody = oldRequest.body
                for ((key, value) in mUrlParamsMap.entries) {
                    formBody = bodyBuilder.add(key, value)
                        .build()
                }
                newRequest = if (formBody != null) {
                    oldRequest.newBuilder().post(formBody).build()
                } else {
                    requestBuilder.build()
                }
            }
        } else {
            newRequest = requestBuilder.build()
        }
        return chain.proceed(newRequest)
    }

    class Builder {
        private var mHttpCommonInterceptor: HttpCommonInterceptor =
            HttpCommonInterceptor()

        fun addHeaderParams(key: String, value: String): Builder {
            mHttpCommonInterceptor.mHeaderParamsMap[key] = value
            return this
        }

        fun addUrlParams(key: String, value: String): Builder {
            mHttpCommonInterceptor.mUrlParamsMap[key] = value
            return this
        }

        fun build(): HttpCommonInterceptor {
            return mHttpCommonInterceptor
        }

    }
}