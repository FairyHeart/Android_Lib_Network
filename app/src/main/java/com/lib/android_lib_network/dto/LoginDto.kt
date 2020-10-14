package com.lib.android_lib_network.dto

/**
 *
 *
 * @author: Fairy.
 * @date  : 2020/8/5.
 */
data class LoginDto(
    val wx: String? = null,
    val token: String = "",
    val information: String? = null,
    val isUserInformation: Int = 0,
    val isMobile: Int = 0,
    val entityId: String = "",
    val mobile: String?


) {
    override fun toString(): String {
        return "LoginDto(isMobile=$isMobile, entityId='$entityId', mobile=$mobile)"
    }
}