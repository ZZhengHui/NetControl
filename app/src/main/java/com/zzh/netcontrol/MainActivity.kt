package com.zzh.netcontrol

import com.zzh.netcontrol.base.BaseActivity
import com.zzh.netcontrol.main.MainContract
import com.zzh.netcontrol.main.MainContractImpl
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<MainContractImpl>(), MainContract.View {
    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initViewAndEvent() {
        clickTv.setOnClickListener {
            presenter.requestNetData("")
        }
    }

    override fun showProgressDialog() {

    }

    override fun handleData() {

    }

    override fun hideProgressDialog() {

    }

    override fun showMsg(msg: String) {
        showToast(msg)
    }

    override fun initPresenter(): MainContractImpl {
        return MainContractImpl()
    }

}
