package com.lib.android_lib_network

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.lib.android_lib_network.scope.ScopeRetrofitActivity
import com.lib.network.R
import com.lib.network.databinding.ActivityMain2Binding
import kotlinx.android.synthetic.main.activity_main2.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding =
            DataBindingUtil.setContentView<ActivityMain2Binding>(this, R.layout.activity_main2)
        binding.activity = this
    }

    fun goToLoginActivity() {
        val intent = Intent(this, TestActivity::class.java)
        startActivity(intent)
    }

    fun goToScopeActivity() {
        val intent = Intent(this, ScopeRetrofitActivity::class.java)
        startActivity(intent)
    }
}