package com.lib.android_lib_network

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.fairy.lib.network.RetrofitManager
import com.fairy.lib.network.filterStatus
import com.fairy.lib.network.rxjava.FilterSubscriber
import com.fairy.lib.network.toBody
import com.lib.android_lib_network.dto.LoginDto
import com.lib.android_lib_network.param.LoginParam
import com.lib.network.R
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val remoteService = RetrofitManager.instance.create(IRemoteService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewModel by viewModels<LoginViewModel>()

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

        viewModel.login().observe(this,
            Observer<LoginDto?> {
                tv.text = "${tv.text.toString()}\n${it.toString()}"
            })
    }
}