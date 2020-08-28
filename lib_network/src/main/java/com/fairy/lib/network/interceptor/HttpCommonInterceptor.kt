package com.fairy.lib.network.interceptor

import okhttp3.FormBody
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 *
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
        // 添加新的参数，添加到url 中
        /*HttpUrl.Builder authorizedUrlBuilder = oldRequest.url().newBuilder()
        .scheme(oldRequest.url().scheme())
        .host(oldRequest.url().host());*/
        // 新的请求
        val requestBuilder: Request.Builder = oldRequest.newBuilder()
        requestBuilder.method(oldRequest.method, oldRequest.body)

        //添加公共参数,添加到header中
        if (!mHeaderParamsMap.isNullOrEmpty()) {
            for ((key, value) in mHeaderParamsMap.entries) {
                requestBuilder.header(key, value)
            }
        }
//        requestBuilder.header("xtoken", AppPlatform.token)
        var newRequest: Request = requestBuilder.build()
        //添加公用参数，添加到参数中
        val method = oldRequest.method
        if (method.equals("Get", ignoreCase = true) && !mUrlParamsMap.isNullOrEmpty()) {
            var modifiedBuilder = oldRequest.url.newBuilder()
            for ((key, value) in mUrlParamsMap.entries) {
                modifiedBuilder.addQueryParameter(key, value)
            }
            var modifiedUrl = modifiedBuilder.build()
            newRequest = if (modifiedUrl != null) {
                oldRequest.newBuilder().url(modifiedUrl).build()
            } else {
                requestBuilder.build()
            }
        } else if (method.equals("Post", ignoreCase = true) && !mUrlParamsMap.isNullOrEmpty()) {
            if (oldRequest.body is FormBody) {
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