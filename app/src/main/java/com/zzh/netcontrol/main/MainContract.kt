package com.zzh.netcontrol.main

import com.zzh.netcontrol.base.BasePresenter
import com.zzh.netcontrol.base.BaseView

/**
 * Time 2019/4/1
 * Author 13651
 * Description
 */
class MainContract {
    interface Presenter : BasePresenter<View> {
        fun requestNetData(data: String)
    }

    interface View : BaseView {
        fun showMsg(msg: String)
    }
}