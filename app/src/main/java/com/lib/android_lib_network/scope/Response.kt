package com.lib.android_lib_network.scope

/**
 * 将网络调用的状态传给UI层
 *
 * @author: GuaZi.
 * @date  : 2020/12/16.
 */
class Response<out T>(
    val status: Status,
    val data: T?,
    val errorCode: String? = null,
    val message: String? = null
) {
    companion object {
        fun <T> success(data: T?, message: String? = null) =
            Response(Status.SUCCESS, data, null, message)

        fun error(
            message: String?,
            errorCode: String? = ErrorCode.EXCEPTION.value
        ) =
            Response(Status.ERROR, null, message, errorCode)

        fun loading() = Response(Status.LOADING, null)
    }
}