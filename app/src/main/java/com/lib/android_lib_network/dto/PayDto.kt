package com.lib.android_lib_network.dto

/**
 *
 *
 * @author: Fairy.
 * @date  : 2020/9/14.
 */
class PayDto(
    val type: Int = 0,//1：微信，2：支付宝
    val fee: Long = 0,//付款金额
    val commission: Long = 0,// 抽佣金额
    val shopFee: Long = 0,//入账金额
    val memo: String?,
    val createTime: Long = 0
)