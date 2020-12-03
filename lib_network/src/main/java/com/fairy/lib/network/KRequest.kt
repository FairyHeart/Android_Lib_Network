package com.fairy.lib.network

import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
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

private val gson = GsonBuilder()
//    .excludeFieldsWithoutExposeAnnotation() //不对没有用@Expose注解的属性进行操作
//    .enableComplexMapKeySerialization() //当Map的key为复杂对象时,需要开启该方法
//    .serializeNulls() //当字段值为空或null时，依然对该字段进行转换
//    .setDateFormat("yyyy-MM-dd HH:mm:ss:SSS") //时间转化为特定格式
//    .setPrettyPrinting() //对结果进行格式化，增加换行
    .disableHtmlEscaping() //防止特殊字符出现乱码
//    .registerTypeAdapter(User.class,new UserAdapter()) //为某特定对象设置固定的序列或反序列方式，自定义Adapter需实现JsonSerializer或者JsonDeserializer接口
    .create()

/**
 * Any拓展函数
 * post 请求定义json请求body
 */
fun Any.toBody(): RequestBody {
    val param = gson.toJson(this)
    if (RetrofitConfig.instance.debug) {
        Log.i(RetrofitConfig.instance.logName, param)
    }
    return param.toRequestBody(contentType.toMediaTypeOrNull())
}
