package com.lib.android_lib_network.scope

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.fairy.lib.network.RetrofitManager
import com.fairy.lib.network.toBody
import com.lib.android_lib_network.IRemoteService
import com.lib.android_lib_network.dto.LoginDto
import com.lib.android_lib_network.param.LoginParam
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 *
 *
 * @author: Fairy.
 * @date  : 2020/12/15.
 */
class ScopeRetrofitViewModel(application: Application) : AndroidViewModel(application) {

    private val remoteService = RetrofitManager.instance.create(IRemoteService::class.java)

    val loginDtoLiveData = MutableLiveData<LoginDto?>()

    fun login() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val param = LoginParam("13777820327", "123456", "1001")
                val response = remoteService.loginByPhoneScope(param.toBody())
                if (response.isSuccessful) {
                    val result = response.body()
                    if (result != null) {
                        if (result.isSuccess()) {
                            loginDtoLiveData.postValue(result.data)
                        }
                    }
                } else {

                }
            } catch (e: Throwable) {
                e.printStackTrace()
            }
        }
    }

    fun login2() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val param = LoginParam("13777820327", "123456", "1001")
                val result = remoteService.loginByPhoneScope2(param.toBody())
                if (result.isSuccess()) {
                    loginDtoLiveData.postValue(result.data)
                }
            } catch (e: Throwable) {
                e.printStackTrace()
            }
        }
    }

    val liveData = liveData<LoginDto?>(Dispatchers.IO) {
        try {
            val param = LoginParam("13777820327", "123456", "1001")
            val result = remoteService.loginByPhoneScope2(param.toBody())
            if (result.isSuccess()) {
                emit(result.data)
            }
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }

}