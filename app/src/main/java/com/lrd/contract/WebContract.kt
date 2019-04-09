package com.lrd.contract

import com.lrd.base.BaseView

/**
 * Created By LRD
 * on 2019/4/8  notes：web
 */
interface WebContract {
    interface View:BaseView{
        fun init()
        fun setToolbarTitle(title:String )
    }
    interface Presenter {
        fun init()
    }

    interface Model{
        fun loadData(listener: ModelListener)
        interface ModelListener{
            fun completed(title:String)
        }
    }
}