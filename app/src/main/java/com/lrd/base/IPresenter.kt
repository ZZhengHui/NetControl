package com.lrd.base

/**
 * Created By LRD
 * on 2019/4/8  notes：
 */
interface IPresenter<in V/* :IView*/> {
    /**
     * bind View
     */
    fun register(view:V)
    /**
     * unBind View
     */
    fun unRegister()
}