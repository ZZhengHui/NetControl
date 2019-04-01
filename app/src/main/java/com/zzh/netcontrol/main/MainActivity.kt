package com.zzh.netcontrol.main

import com.zzh.netcontrol.R
import com.zzh.netcontrol.base.BaseActivity

class MainActivity : BaseActivity<MainContractImpl>(), MainContract.View {
    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initViewAndEvent() {

    }

    override fun showProgressDialog() {
    }

    override fun handleData() {

    }

    override fun hidProgressDialog() {

    }

    override fun showMsg(msg: String) {

    }

    override fun initPresenter(): MainContractImpl {
        return MainContractImpl()
    }

}
