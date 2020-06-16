package com.zzh.basemodule.base

/**
 * Time 2019/4/1
 * Author 13651
 * Description
 */
interface BasePresenter<T> {
    fun attachView(view: T)
    fun detachView()
}