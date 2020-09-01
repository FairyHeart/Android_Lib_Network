package com.fairy.lib.network

import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

/**
 *
 *
 * @author: Fairy.
 * @date  : 2020/9/1.
 */
const val contentType = "application/json"

private val gson = Gson()

/**
 * Any拓展函数
 * post 请求定义json请求body
 */
fun Any.toBody(): RequestBody {
    val param = gson.toJson(this)
    return param.toRequestBody(contentType.toMediaTypeOrNull())
}
