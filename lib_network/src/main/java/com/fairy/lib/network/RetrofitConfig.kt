package com.fairy.lib.network

import okhttp3.Interceptor
import retrofit2.Converter

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
     * 应用名字，输出日志使用
     */
    var appName = "network"
        private set

    /**
     * 请求URL
     */
    lateinit var baseUrl: String

    /**
     * 添加公共参数,添加到header中
     */
    var headerParams: MutableMap<String, String>? = null
        private set

    /**
     * 添加公共参数,添加到参数中
     */
    var urlParams: MutableMap<String, String>? = null
        private set

    /**
     * 添加拦截器
     */
    var networkInterceptors: MutableList<Interceptor>? = null
        private set

    /**
     * Json转换器
     */
    var converterFactory: Converter.Factory? = null
        private set

    /**
     * 是否测试环境
     */
    fun debug(debug: Boolean): RetrofitConfig {
        this.debug = debug
        return this
    }

    /**
     * 应用名字，输出日志使用
     */
    fun appName(appName: String): RetrofitConfig {
        this.appName = appName
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
    fun addHeaderParams(headerParams: MutableMap<String, String>): RetrofitConfig {
        this.headerParams = headerParams
        return this
    }

    /**
     * 添加公共参数,添加到参数中
     */
    fun addUrlParams(urlParams: MutableMap<String, String>): RetrofitConfig {
        this.urlParams = urlParams
        return this
    }

    /**
     * 添加拦截器
     */
    fun addNetworkInterceptor(networkInterceptors: MutableList<Interceptor>): RetrofitConfig {
        this.networkInterceptors = networkInterceptors
        return this
    }

    /**
     * Json转换器
     */
    fun addConverterFactory(converterFactory: Converter.Factory): RetrofitConfig {
        this.converterFactory = converterFactory
        return this
    }
}