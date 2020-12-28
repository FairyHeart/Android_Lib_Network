package com.lib.android_lib_network.scope

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
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

//        vm.login()
//        vm.login2()

        vm.result?.observe(this, Observer {
            it?.let {
                when (it.status) {
                    Status.LOADING -> {
                        Toast.makeText(this, "loading", Toast.LENGTH_SHORT).show()
                    }
                    Status.ERROR -> {
                        Toast.makeText(this, "error", Toast.LENGTH_SHORT).show()
                    }
                    Status.SUCCESS -> {
                        Toast.makeText(this, "success", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }
}