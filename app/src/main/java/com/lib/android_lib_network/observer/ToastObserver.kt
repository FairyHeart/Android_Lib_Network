package com.lib.android_lib_network.observer

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.Observer


/**
 * 异常展示Toast提示框
 *
 * @author: Fairy.
 * @date  : 2020/10/14.
 */
class ToastObserver(private val context: Context) : Observer<String?> {

    /**
     * Called when the data is changed.
     * @param t  The new data
     */
    override fun onChanged(t: String?) {
        if (t.isNullOrBlank()) {
            return
        }
        Toast.makeText(context, t, Toast.LENGTH_LONG).show()
    }
}