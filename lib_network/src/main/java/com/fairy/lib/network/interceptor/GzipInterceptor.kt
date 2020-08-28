package com.fairy.lib.network.interceptor

import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.Response

/**
 *
 *
 * @author: Fairy.
 * @date  : 2019-08-12.
 */
class GzipInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        return if ("gzip".equals(
                response.header("Content-Encoding"),
                ignoreCase = true
            ) && contentLength(response.headers) == 0.toLong()
        ) {
            response.newBuilder().removeHeader("Content-Encoding").build()
        } else response
    }

    private fun contentLength(headers: Headers): Long {
        val s = headers["Content-Length"] ?: return -1
        return try {
            java.lang.Long.parseLong(s)
        } catch (e: NumberFormatException) {
            -1
        }
    }
}