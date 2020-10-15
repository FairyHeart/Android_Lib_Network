package com.fairy.lib.network

import okhttp3.Interceptor
import retrofit2.CallAdapter
import retrofit2.Converter
import kotlin.math.log

/**
 * app平台配置
 *
 * @author: Fairy.
 * @date  : 2019-08-06.
 */
class RetrofitConfig private constructor() {

    companion object {
        val instance: RetrofitConfig by lazy {
            RetrofitConfig()
        }
    }

    /**
     * 是否测试环境
     */
    var debug = false
        private set

    /**
     * 输出日志Tag
     */
    var logName = "network"
        private set

    /**
     * 请求URL
     */
    lateinit var baseUrl: String

    /**
     * 添加公共参数,添加到header中
     */
    var headerParams: MutableMap<String, String?> = mutableMapOf()
        private set

    /**
     * 添加公共参数,添加到URL末尾
     */
    var urlParams: MutableMap<String, String?> = mutableMapOf()
        private set

    /**
     * 添加到公共参数到消息体，适用 Post(contentType=application/x-www-form-urlencoded)、Get 请求
     */
    var bodyParams: MutableMap<String, String?> = mutableMapOf()
        private set

    /**
     * 添加网络拦截器
     */
    var networkInterceptors: MutableList<Interceptor> = mutableListOf()
        private set

    /**
     * Json转换器
     */
    var converterFactory: Converter.Factory? = null
        private set

    /**
     * 添加数据源适配器
     */
    var callAdapterFactory: CallAdapter.Factory? = null
        private set

    /**
     * 是否测试环境
     */
    fun debug(debug: Boolean): RetrofitConfig {
        this.debug = debug
        return this
    }

    /**
     * 输出日志Tag
     */
    fun appName(logName: String): RetrofitConfig {
        this.logName = logName
        return this
    }

    /**
     * 请求URL
     */
    fun baseUrl(baseUrl: String): RetrofitConfig {
        this.baseUrl = baseUrl
        return this
    }

    /**
     * 添加公共参数,添加到header中
     */
    fun addHeaderParams(key: String, value: String?): RetrofitConfig {
        this.headerParams[key] = value
        return this
    }

    /**
     * 添加公共参数,添加到参数中
     */
    fun addUrlParams(key: String, value: String?): RetrofitConfig {
        this.urlParams[key] = value
        return this
    }

    /**
     * 添加到公共参数到消息体，适用 Post(contentType=application/x-www-form-urlencoded)、Get 请求
     */
    fun addBodyParams(key: String, value: String?): RetrofitConfig {
        this.bodyParams[key] = value
        return this
    }

    /**
     * 添加拦截器
     */
    fun addNetworkInterceptor(interceptor: Interceptor): RetrofitConfig {
        if (!networkInterceptors.contains(interceptor)) {
            this.networkInterceptors.add(interceptor)
        }
        return this
    }

    /**
     * Json转换器
     */
    fun addConverterFactory(converterFactory: Converter.Factory): RetrofitConfig {
        this.converterFactory = converterFactory
        return this
    }

    /**
     * 添加数据源适配器
     */
    fun addCallAdapterFactory(callAdapterFactory: CallAdapter.Factory): RetrofitConfig {
        this.callAdapterFactory = callAdapterFactory
        return this
    }
}