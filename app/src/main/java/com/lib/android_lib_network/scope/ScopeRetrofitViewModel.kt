package com.lib.android_lib_network.scope

import android.app.Application
import androidx.lifecycle.*
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

    var result: LiveData<Response<LoginDto>>? = null

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

    fun login3() {
        result = liveData(Dispatchers.IO) {
            emit(Response.loading())
            try {
                val param = LoginParam("13777820327", "123456", "1001")
                val result = remoteService.loginByPhoneScope2(param.toBody())
                if (result.isSuccess()) {
                    emit(Response.success(result.data))
                } else {
                    emit(Response.error(result.message, result.errorCode))
                }
            } catch (e: Throwable) {
                e.printStackTrace()
                emit(Response.error(e.message, null))
            }
        }
    }


}