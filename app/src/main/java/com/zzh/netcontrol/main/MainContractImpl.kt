package com.zzh.netcontrol.main

/**
 * Time 2019/4/1
 * Author 13651
 * Description
 */
class MainContractImpl : MainContract.Presenter {

    private var view: MainContract.View? = null

    override fun requestNetData(data: String) {     //请求网络数据
        view!!.handleData()
        view!!.showMsg("日天~")
    }

    override fun attachView(view: MainContract.View) {
        this.view = view
    }

    override fun detachView() {
        this.view = null
    }
}