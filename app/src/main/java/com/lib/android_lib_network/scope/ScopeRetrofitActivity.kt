package com.lib.android_lib_network.scope

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.lib.network.R
import com.lib.network.databinding.ActivityScopeRetrofitBinding

/**
 * 协程+retrofit
 */
class ScopeRetrofitActivity : AppCompatActivity() {

    lateinit var binding: ActivityScopeRetrofitBinding

    val vm by viewModels<ScopeRetrofitViewModel> {
        ViewModelProvider.AndroidViewModelFactory(this.application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(
            this, R.layout.activity_scope_retrofit
        )
        binding.vm = vm
        binding.lifecycleOwner = this
        binding.executePendingBindings()
        
        vm.login()
//        vm.login2()
    }
}