package com.lib.android_lib_network

import androidx.lifecycle.LiveData
import com.fairy.lib.network.dto.ResultDto
import com.lib.android_lib_network.dto.LoginDto
import com.lib.android_lib_network.dto.PayDto
import io.reactivex.Observable
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

/**
 *
 *
 * @author: Fairy.
 * @date  : 2020/9/10.
 */
internal interface IRemoteService {
    /**
     * 手机号密码登录
     */
    @POST("weChat/v1/mobile_login")
    fun loginByPhone(
        @Body info: RequestBody
    ): Observable<ResultDto<LoginDto?>>


    @POST("weChat/v1/mobile_login")
    fun loginByPhoneLiveData(
        @Body info: RequestBody
    ): LiveData<ResultDto<LoginDto?>>

    /**
     * 首页获取支付信息
     */
    @POST("order/v1/get_order_pay_always")
    fun getPays(
        @Body info: RequestBody,
        @Query("xtoken") token: String
    ): Observable<ResultDto<MutableList<PayDto>?>>

}