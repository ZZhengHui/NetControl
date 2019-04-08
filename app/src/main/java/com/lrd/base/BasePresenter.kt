package com.lrd.base

import android.view.View
import java.lang.ref.WeakReference

/**
 * Created By LRD
 * on 2019/4/8  notes：
 */
open class BasePresenter<V:IView> :IPresenter<V>{

    protected var iView: WeakReference<V>? = null;

    override fun register(view: V) {
        iView = WeakReference(view)
    }

    override fun unRegister() {
        iView?.clear()
    }
}