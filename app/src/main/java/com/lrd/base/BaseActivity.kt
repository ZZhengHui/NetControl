package com.lrd.base

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast

/**
 * Created By LRD
 * on 2019/4/8  notes：
 */
abstract class BaseActivity<P : BasePresenter<IView>> : AppCompatActivity(),IView{
    lateinit var mPresenter : P

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(getLayoutId())

        bindView()
    }

    abstract fun getLayoutId(): Int

    abstract fun getPresenter(): P

    abstract fun initialize()

    override fun bindView() {
        mPresenter = getPresenter()
        mPresenter.register(this)
        initialize()
    }

    override fun unBindView() {
        mPresenter.unRegister()
    }

    fun showToast(str:String){
        Toast.makeText(this,str, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        unBindView()
    }
}