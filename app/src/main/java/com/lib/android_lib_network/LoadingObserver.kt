package com.lib.android_lib_network

import android.content.Context
import androidx.lifecycle.Observer
import com.kaopiz.kprogresshud.KProgressHUD


/**
 *
 *
 * @author: Fairy.
 * @date  : 2020/10/14.
 */
class LoadingObserver(context: Context) : Observer<Boolean> {

    private val dialog = KProgressHUD(context)
        .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
        .setCancellable(false)
        .setAnimationSpeed(2)
        .setDimAmount(0.5f)

    override fun onChanged(t: Boolean?) {
        if (t == null) {
            return
        }
        if (t) {
            dialog.show()
        } else {
            dialog.dismiss()
        }
    }
}