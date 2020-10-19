package com.lib.android_lib_network

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.fairy.lib.network.RetrofitConfig
import com.fairy.lib.network.RetrofitManager
import com.fairy.lib.network.filterStatus
import com.fairy.lib.network.observer.ResultObserver
import com.fairy.lib.network.rxjava.FilterSubscriber
import com.fairy.lib.network.toBody
import com.lib.android_lib_network.dto.LoginDto
import com.lib.android_lib_network.observer.LoadingObserver
import com.lib.android_lib_network.observer.ToastObserver
import com.lib.android_lib_network.param.LoginParam
import com.lib.network.R
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class TestActivity : AppCompatActivity() {

    private val remoteService = RetrofitManager.instance.create(IRemoteService::class.java)

    /**
     * 加载对话框显示值LiveData
     */
    val loadingState by lazy {
        MutableLiveData<Boolean>()
    }

    /**
     * 异常提示值LiveData
     */
    val toastState by lazy {
        MutableLiveData<String?>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        this.testRxJava()
//        this.testLiveData()
        this.testLiveData2()
    }

    private fun testRxJava() {
        val param = LoginParam("13777820327", "123456", "1001")
        val loginObservable = remoteService.loginByPhone(param.toBody()).filterStatus()
        loginObservable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : FilterSubscriber<LoginDto?>(this) {

                override fun onNull() {
                }

                override fun onNext(value: LoginDto) {
                    tv.text = "${tv.text.toString()}\n${value.toString()}"
                }

            })
    }

    private fun testLiveData() {

        val viewModel by viewModels<LoginViewModel>() {
            LoginViewModel.ViewModelFactory(loadingState, toastState)
        }

        loadingState.observe(this, LoadingObserver(this))
        toastState.observe(this, ToastObserver(this))
        loadingState.value = true

        viewModel.login3().observe(this,
            object : ResultObserver<LoginDto?>(
                loading = loadingState,
                toast = toastState
            ) {
                override fun onSuccess(value: LoginDto?) {
                    tv.text = value.toString()

                    loadingState.value = false

                    RetrofitConfig.instance.addUrlParams("xtoken", value?.token)
                    RetrofitManager.instance.reCreateRetrofit()

                    if (value != null) {
                        viewModel.getPays(value.entityId).observe(this@TestActivity, Observer {
                            tv.text = "${tv.text.toString()}\n${it.toString()}"
                        })
                    }
                }

                override fun onFailure(errorCode: String?, errorMessage: String?) {

                }
            })

    }

    private fun testLiveData2() {

//        val viewModel by viewModels<LoginViewModel>() {
//            BaseViewModel.ViewModelFactory(loadingState, toastState)
//        }
        val viewModel =
            ViewModelProvider(this, LoginViewModel.ViewModelFactory(loadingState, toastState)).get(
                LoginViewModel::class.java
            )
        loadingState.observe(this, LoadingObserver(this))
        toastState.observe(this, ToastObserver(this))
        loadingState.value = true

        try {
            viewModel.login()
            viewModel.loginLiveData?.observe(this, Observer {
                tv.text = it.toString()
            })
        } catch (e: Throwable) {
            toastState.value = e.message
        }


    }
}


