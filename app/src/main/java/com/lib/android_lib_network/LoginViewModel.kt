package com.lib.android_lib_network

import androidx.lifecycle.*
import com.fairy.lib.network.RetrofitManager
import com.fairy.lib.network.dto.ResultDto
import com.fairy.lib.network.toBody
import com.lib.android_lib_network.dto.LoginDto
import com.lib.android_lib_network.dto.PayDto
import com.lib.android_lib_network.param.EntityParam
import com.lib.android_lib_network.param.LoginParam
import com.lib.android_lib_network.vm.BaseViewModel

/**
 *
 *
 * @author: Fairy.
 * @date  : 2020/10/14.
 */
class LoginViewModel(
    loadingState: MutableLiveData<Boolean>? = null,
    toastState: MutableLiveData<String?>? = null
) : BaseViewModel(loadingState, toastState) {


    class ViewModelFactory(
        private val loadingState: MutableLiveData<Boolean>? = null,
        private val toastState: MutableLiveData<String?>? = null
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return LoginViewModel(loadingState, toastState) as T
        }

    }
    private val remoteService = RetrofitManager.instance.create(IRemoteService::class.java)

    var loginLiveData: LiveData<LoginDto?>? = null

    fun login3(): LiveData<ResultDto<LoginDto?>> {
        val param = LoginParam("13777820327", "123456", "1001")
        return remoteService.loginByPhoneLiveData(param.toBody())
    }

    fun login2(): LiveData<LoginDto?>? {
        val param = LoginParam("13777820327", "1234567", "1001")
        val resultDto = remoteService.loginByPhoneLiveData(param.toBody())
        return Transformations.map(resultDto) {
            if (!it.isSuccess()) {
                toastState?.value = it.message
                return@map null
            }
            it.data
        }
    }

    fun login() {
        val param = LoginParam("13777820327", "1234567", "1001")
        try {
            loginLiveData = remoteService.loginByPhoneLiveData(param.toBody()).filterStatus()
        } catch (e: Throwable) {
            toastState?.value = e.message
        }
        loadingState?.value = false
    }

    fun getPays(entity: String): LiveData<ResultDto<MutableList<PayDto>?>> {
        val remoteService = RetrofitManager.instance.create(IRemoteService::class.java)
        val param = EntityParam(entity)
        return remoteService.getPays(param.toBody())
    }

}