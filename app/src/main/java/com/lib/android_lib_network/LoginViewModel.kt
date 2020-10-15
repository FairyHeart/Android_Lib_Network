package com.lib.android_lib_network

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.fairy.lib.network.RetrofitManager
import com.fairy.lib.network.dto.ResultDto
import com.fairy.lib.network.toBody
import com.lib.android_lib_network.dto.LoginDto
import com.lib.android_lib_network.dto.PayDto
import com.lib.android_lib_network.param.EntityParam
import com.lib.android_lib_network.param.LoginParam

/**
 *
 *
 * @author: Fairy.
 * @date  : 2020/10/14.
 */
class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val remoteService = RetrofitManager.instance.create(IRemoteService::class.java)

    val loading = MutableLiveData<Boolean>()
    val toast = MutableLiveData<String>()

    fun login3(): LiveData<ResultDto<LoginDto?>> {
        val param = LoginParam("13777820327", "123456", "1001")
        return remoteService.loginByPhoneLiveData(param.toBody())
    }

    fun login2(): LiveData<LoginDto?>? {
        val param = LoginParam("13777820327", "1234567", "1001")
        val resultDto = remoteService.loginByPhoneLiveData(param.toBody())
        return Transformations.map(resultDto) {
            if (!it.isSuccess()) {
                toast.value = it.message
                return@map null
            }
            it.data
        }
    }

    fun login(): LiveData<LoginDto?>? {
        val param = LoginParam("13777820327", "1234567", "1001")
        return remoteService.loginByPhoneLiveData(param.toBody())
            .filterStatus(getApplication<TestApplication>())
    }

    fun getPays(entity: String): LiveData<ResultDto<MutableList<PayDto>?>> {
        val remoteService = RetrofitManager.instance.create(IRemoteService::class.java)
        val param = EntityParam(entity)
        return remoteService.getPays(param.toBody())
    }
}