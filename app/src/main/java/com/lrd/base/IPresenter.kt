package com.lrd.base

import android.view.View

/**
 * Created By LRD
 * on 2019/4/8  notes：
 */
interface IPresenter<V :IView> {
    /**
     * bind View
     */
    fun register(view:V)
    /**
     * unBind View
     */
    fun unRegister()
}