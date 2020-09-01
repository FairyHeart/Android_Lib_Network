package com.fairy.lib.network.dto

/**
 *
 *
 * @author: Fairy.
 * @date  : 2020/8/3.
 */
data class ResultDto<T>(
    var code: Int = 0,
    var data: T? = null,
    val errorCode: String?,
    val message: String?
) {
    companion object {
        const val Success = 1
    }

    fun isSuccess(): Boolean {
        return Success == code
    }
}