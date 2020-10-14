package com.lib.android_lib_network

import android.app.Application
import android.widget.Toast
import com.fairy.lib.network.RetrofitConfig
import com.fairy.lib.network.ThreadSchedulers
import com.fairy.lib.network.factory.LiveDataCallAdapterFactory
import com.lib.network.BuildConfig

/**
 *
 *
 * @author: Fairy.
 * @date  : 2020/10/14.
 */
class TestApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        RetrofitConfig.instance.debug(BuildConfig.DEBUG)
            .appName("TestLibNetwork")
            .baseUrl("https://api.lemoncoupon.cn")
            .addHeaderParams(
                mutableMapOf(
                    Pair("version_code", "100"),
                    Pair("version_name", "1.0.0")
                )
            )
            .addCallAdapterFactory(LiveDataCallAdapterFactory(ThreadSchedulers.MAIN_THREAD) {
                Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
            })
    }
}