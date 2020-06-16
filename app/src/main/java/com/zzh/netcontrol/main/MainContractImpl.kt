package com.zzh.netcontrol.main

import com.zzh.netcontrol.net.okhttp.OkHttpDataCallBack
import com.zzh.netcontrol.net.okhttp.OkHttpManager


/**
 * Time 2019/4/1
 * Author 13651/
 * Description
 */
class MainContractImpl : MainContract.Presenter {

    private var view: MainContract.View? = null

    override fun requestNetData(data: String) {     //请求网络数据
        OkHttpManager.getInstance().asyncGetString("http://", object :OkHttpDataCallBack {
            override fun onFailed() {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onSuccess(data: String?) {
                view!!.handleData()
                view!!.showMsg("日天~")
            }
        })
    }

    override fun attachView(view: MainContract.View) {
        this.view = view
    }

    override fun detachView() {
        this.view = null
    }
}