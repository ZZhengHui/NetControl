package com.lrd.contract

import com.lrd.base.IView

/**
 * Created By LRD
 * on 2019/4/8  notes：web
 */
interface WebContract {
    interface View:IView{
        fun setTitle()
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