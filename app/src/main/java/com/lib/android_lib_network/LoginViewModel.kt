package com.lib.android_lib_network

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.*
import com.fairy.lib.network.RetrofitManager
import com.fairy.lib.network.dto.ResultDto
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
class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val remoteService = RetrofitManager.instance.create(IRemoteService::class.java)

    val loading = MutableLiveData<Boolean>()

    fun login2(): LiveData<LoginDto?> {
        val param = LoginParam("13777820327", "1234567", "1001")
        val resultDto = remoteService.loginByPhoneLiveData(param.toBody())
        return Transformations.map(resultDto) {
            if (!it.isSuccess()) {
                Toast.makeText(getApplication(), it.message, Toast.LENGTH_LONG).show()
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
}