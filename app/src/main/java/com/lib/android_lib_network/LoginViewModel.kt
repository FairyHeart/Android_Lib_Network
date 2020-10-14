package com.lib.android_lib_network

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.fairy.lib.network.RetrofitManager
import com.fairy.lib.network.filterStatus
import com.fairy.lib.network.toBody
import com.lib.android_lib_network.dto.LoginDto
import com.lib.android_lib_network.param.LoginParam

/**
 *
 *
 * @author: Fairy.
 * @date  : 2020/10/14.
 */
class LoginViewModel : ViewModel() {

    private val remoteService = RetrofitManager.instance.create(IRemoteService::class.java)

    fun login(): LiveData<LoginDto?> {
        val param = LoginParam("13777820327", "1234567", "1001")
        return remoteService.loginByPhoneLiveData(param.toBody()).filterStatus()
    }
}