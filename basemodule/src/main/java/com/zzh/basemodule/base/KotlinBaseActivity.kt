package com.zzh.basemodule.base

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

/**
 * Time 2019/4/9
 * Author 13651
 * Description
 */
public abstract class KotlinBaseActivity<V : BaseView, P : BasePresenter<V>> : AppCompatActivity(), BaseView {

    protected var presenter: P? = null

    @Suppress("UNCHECKED_CAST")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        initViewAndEvent()
        presenter = createPresenter()
        if (null != presenter) presenter!!.attachView(this as V)
    }

    abstract fun getLayoutId(): Int
    abstract fun initViewAndEvent()
    abstract fun createPresenter(): P

    fun showToast(text: String) {
        Toast.makeText(applicationContext, text, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (null != presenter) presenter!!.detachView()
    }
}