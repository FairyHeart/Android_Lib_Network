package com.lib.android_lib_network

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.fairy.lib.network.RetrofitConfig
import com.fairy.lib.network.RetrofitManager
import com.fairy.lib.network.filterStatus
import com.fairy.lib.network.rxjava.FilterSubscriber
import com.fairy.lib.network.toBody
import com.lib.android_lib_network.dto.LoginDto
import com.lib.android_lib_network.observer.LoadingObserver
import com.lib.android_lib_network.observer.ResultObserver
import com.lib.android_lib_network.observer.ToastObserver
import com.lib.android_lib_network.param.LoginParam
import com.lib.network.R
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class TestActivity : AppCompatActivity() {

    private val remoteService = RetrofitManager.instance.create(IRemoteService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        this.testRxJava()
        this.testLiveData()
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

        val viewModel by viewModels<LoginViewModel>()

        viewModel.loading.observe(this, LoadingObserver(this))
        viewModel.toast.observe(this, ToastObserver(this))
        viewModel.loading.value = true

//        viewModel.login2().observe(this,
//            Observer<LoginDto?> {
//                tv.text = "${tv.text.toString()}\n${it.toString()}"
//                viewModel.loading.value = false
//
//            })

        viewModel.login3().observe(this,
            object : ResultObserver<LoginDto?>(
                context = this,
                loading = viewModel.loading,
                toast = viewModel.toast
            ) {
                override fun onSuccess(value: LoginDto?) {
                    tv.text = value.toString()

                    viewModel.loading.value = false

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

//        viewModel.login().let {
//            if (it == null) {
//                viewModel.loading.value = false
//                return@let
//            }
//            it?.observe(this,
//                Observer<LoginDto?> {
//                    tv.text = "${tv.text.toString()}\n${it.toString()}"
//                    viewModel.loading.value = false
//
//                })
//        }

    }
}


