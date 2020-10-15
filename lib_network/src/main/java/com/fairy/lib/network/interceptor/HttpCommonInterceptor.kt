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

    //header公用参数
    private var mHeaderParamsMap = mutableMapOf<String, String>()

    //添加到URL末尾
    private var mUrlParamsMap = mutableMapOf<String, String>()

    //添加到公共参数到消息体，适用 Post(contentType=application/x-www-form-urlencoded)、Get 请求
    private var mBodyParamsMap = mutableMapOf<String, String>()

    override fun intercept(chain: Interceptor.Chain): Response {
        var oldRequest: Request = chain.request()

        // 新的请求
        var requestBuilder: Request.Builder = oldRequest.newBuilder()
        requestBuilder.method(oldRequest.method, oldRequest.body)

        //添加公共参数,添加到header中
        this.addHeaderParams(requestBuilder)

        //添加到 URL 末尾，Get Post 方法都试用
        this.addUrlParams(oldRequest).let {
            if (it != null) {
                requestBuilder = it.newBuilder()
            }
        }
        var newRequest: Request = requestBuilder.build()

        //添加公用参数，添加到参数中(Get添加到url上，Post添加到消息体里面)
        addBodyParams(oldRequest, requestBuilder).let {
            if (it != null) {
                newRequest = it
            }
        }

        return chain.proceed(newRequest)
    }

    /**
     * 添加公共参数,添加到header中
     */
    private fun addHeaderParams(requestBuilder: Request.Builder) {
        if (!mHeaderParamsMap.isNullOrEmpty()) {
            for ((key, value) in mHeaderParamsMap.entries) {
                requestBuilder.header(key, value)
            }
        }
    }

    /**
     * 添加到 URL 末尾，Get Post 方法都试用
     */
    private fun addUrlParams(oldRequest: Request): Request? {
        if (!mUrlParamsMap.isNullOrEmpty()) {
            val modifiedBuilder = oldRequest.url.newBuilder()
            for ((key, value) in mUrlParamsMap.entries) {
                modifiedBuilder.addQueryParameter(key, value)
            }
            val modifiedUrl = modifiedBuilder.build()
            return oldRequest.newBuilder().url(modifiedUrl).build()
        }
        return null
    }

    /**
     * 添加到公共参数到消息体，适用 Post(contentType=application/x-www-form-urlencoded)、Get 请求
     */
    private fun addBodyParams(
        oldRequest: Request,
        requestBuilder: Request.Builder
    ): Request? {
        var newRequest1: Request? = null
        val method = oldRequest.method
        if (method.equals("Get", ignoreCase = true) && !mBodyParamsMap.isNullOrEmpty()) {
            val modifiedBuilder = oldRequest.url.newBuilder()
            for ((key, value) in mBodyParamsMap.entries) {
                modifiedBuilder.addQueryParameter(key, value)
            }
            val modifiedUrl = modifiedBuilder.build()
            newRequest1 = oldRequest.newBuilder().url(modifiedUrl).build()
        } else if (method.equals("Post", ignoreCase = true) && !mBodyParamsMap.isNullOrEmpty()) {
            if (oldRequest.body is FormBody) {//contentType = application/x-www-form-urlencoded 只有这一种格式下会添加公用参数，其他格式不会添加
                val bodyBuilder = FormBody.Builder()
                var formBody = oldRequest.body
                for ((key, value) in mBodyParamsMap.entries) {
                    formBody = bodyBuilder.add(key, value)
                        .build()
                }
                newRequest1 = if (formBody != null) {
                    oldRequest.newBuilder().post(formBody).build()
                } else {
                    requestBuilder.build()
                }
            }
        }
        return newRequest1
    }

    class Builder {
        private var mHttpCommonInterceptor: HttpCommonInterceptor =
            HttpCommonInterceptor()

        fun addHeaderParams(key: String, value: String): Builder {
            mHttpCommonInterceptor.mHeaderParamsMap[key] = value
            return this
        }

        fun addBodyParams(key: String, value: String): Builder {
            mHttpCommonInterceptor.mBodyParamsMap[key] = value
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