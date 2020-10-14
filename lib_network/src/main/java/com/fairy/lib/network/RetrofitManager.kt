package com.fairy.lib.network

import com.fairy.lib.network.interceptor.GzipInterceptor
import com.fairy.lib.network.interceptor.HttpCommonInterceptor
import com.fairy.lib.network.interceptor.LoggingInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 *
 *
 * @author: Fairy.
 * @date  : 2020-01-07.
 */

class RetrofitManager private constructor() {

    companion object {
        val instance: RetrofitManager
            get() = RetrofitManager()
    }

    private var mRetrofit: Retrofit

    init {
        //添加公用参数到头部或者公用参数里面
        val commonBuilder = HttpCommonInterceptor.Builder()
        val headerParams = RetrofitConfig.instance.headerParams
        if (!headerParams.isNullOrEmpty()) {
            headerParams.keys.forEach {
                headerParams[it]?.let { value -> commonBuilder.addHeaderParams(it, value) }
            }
        }
        val urlParams = RetrofitConfig.instance.urlParams
        if (!urlParams.isNullOrEmpty()) {
            urlParams.keys.forEach {
                urlParams[it]?.let { it1 -> commonBuilder.addUrlParams(it, it1) }
            }
        }
        val commonInterceptor = commonBuilder.build()

        val builder = OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.SECONDS)
            .writeTimeout(5, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS)
            .addInterceptor(commonInterceptor)
            .addNetworkInterceptor(GzipInterceptor())
            .addNetworkInterceptor(LoggingInterceptor())

        //添加拦截器
        val networkInterceptors = RetrofitConfig.instance.networkInterceptors
        if (!networkInterceptors.isNullOrEmpty()) {
            networkInterceptors.forEach {
                builder.addNetworkInterceptor(it)
            }
        }
        val client = builder.build()

        val retrofitBuilder = Retrofit.Builder()
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(RetrofitConfig.instance.baseUrl)

        if (RetrofitConfig.instance.converterFactory != null) {
            retrofitBuilder.addConverterFactory(RetrofitConfig.instance.converterFactory!!)
        } else {
            retrofitBuilder.addConverterFactory(GsonConverterFactory.create())
        }
        if (RetrofitConfig.instance.callAdapterFactory != null) {
            retrofitBuilder.addCallAdapterFactory(RetrofitConfig.instance.callAdapterFactory!!)
        }
        mRetrofit = retrofitBuilder.build()
    }

    /**
     * 获取对应的Service
     * @param service Service 的 class
     * @param <T>
     * @return </T>
     */
    fun <T> create(service: Class<T>): T {
        return mRetrofit.create(service)
    }

}