package com.lrd.ui

import com.lrd.base.BaseActivity
import com.lrd.base.BasePresenter
import com.lrd.base.IView
import com.lrd.contract.WebContract
import com.lrd.presenter.WebPresenter
import com.zzh.netcontrol.R

/**
 * Created By LRD
 * on 2019/4/8  notes：
 */
class WebViewActivity: BaseActivity<WebPresenter>(),WebContract.View {
    override fun setTitle() {

    }

    override fun getLayoutId(): Int {
        return R.layout.my_web_layout
    }

    override fun getPresenter(): WebPresenter{
        return WebPresenter()
    }

    override fun initialize() {
        mPresenter.init()
    }

}