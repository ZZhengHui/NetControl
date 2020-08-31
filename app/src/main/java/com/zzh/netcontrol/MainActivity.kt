package com.zzh.netcontrol

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.lrd.ui.WebViewActivity
import com.zzh.basemodule.base.KotlinBaseActivity
import com.zzh.netcontrol.adapter.MenuAdapter
import com.zzh.netcontrol.main.MainContract
import com.zzh.netcontrol.main.MainContractImpl
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : KotlinBaseActivity<MainContract.View, MainContractImpl>(), MainContract.View {
    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initViewAndEvent() {
        menuRecycler.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        val menuAdapter = MenuAdapter(applicationContext)
        menuRecycler.adapter = menuAdapter
        menuAdapter.addItem("跳转RecyclerActivity")
        menuAdapter.addItem("联动滑动选择")
        menuAdapter.addItem("测试EventBus")
        menuAdapter.addItem("嵌套滑动")
        menuAdapter.addItem("Rxjava学习")
        menuAdapter.addItem("wheelView")
        menuAdapter.addItem("Fragment Test")
        menuAdapter.addItem("TestGitVersion")

        //测试tag2
        menuAdapter.addItem("TestGitVersion2")

        //Tag : 新版本开发
        menuAdapter.addItem("新版本v1.0.1 开发完成了")
        Log.e("Bug", "这里又一个没有发现的bug～")
        Log.e("fixed Bug", "bug 修复了～")

        menuAdapter.setOnItemClickListener { _, positon ->
            run {
                when (positon) {
                    0 -> {
                        startActivity(Intent(this, RecyclerActivity::class.java))
                    }
                    1 -> {
                        startActivity(Intent(this, ScrollSelectActivity::class.java))
                    }
                    2 -> {
                        startActivity(Intent(this, TestEventBusActivity::class.java))
                    }
                    3 -> {
                        startActivity(Intent(this, NestingScrollActivity::class.java))
                    }
                    4 -> {
                        startActivity(Intent(this, TestRxjavaActivity::class.java))
                    }
                    5 -> {
                        startActivity(Intent(this, WheelViewActivity::class.java))
                    }
                    6 -> {
                        startActivity(Intent(this, TestFragmentActivity::class.java))
                    }
                }
            }
        }

        clickTv.setOnClickListener {
            presenter!!.requestNetData("")
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
        startActivity(Intent(applicationContext, WebViewActivity::class.java))      //跳转webview界面
    }

    override fun createPresenter(): MainContractImpl {
        return MainContractImpl()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        //界面加载完成
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

}
