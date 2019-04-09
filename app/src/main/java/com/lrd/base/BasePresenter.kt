package com.lrd.base

import java.lang.ref.WeakReference

/**
 * Created By LRD
 * on 2019/4/8  notes：
 */
open class BasePresenter<V:BaseView> {

    protected var iView: WeakReference<V>? = null;

    fun register(view: V) {
        iView = WeakReference(view)
    }

    fun unRegister() {
        iView?.clear()
    }
}