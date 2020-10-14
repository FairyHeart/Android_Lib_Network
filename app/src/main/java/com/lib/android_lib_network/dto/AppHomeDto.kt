package com.lib.android_lib_network.dto

/**
 *
 *
 * @author: Fairy.
 * @date  : 2020/9/14.
 */
data class AppHomeDto(
    val shopName: String?,
    val shopLogo: String?,
    val totalFee: Long = 0,//支付金额
    val totalFeeCommission: Long = 0,//佣金
    val totalShopFee: Long = 0,
    val memberTotalFee: Long = 0,
    val memberCount: Long = 0,
    val totalDisCountFee: Long = 0
)