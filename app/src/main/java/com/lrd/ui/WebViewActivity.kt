package com.lrd.ui

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Build
import android.view.KeyEvent
import android.view.View
import android.webkit.*
import android.widget.FrameLayout
import com.lrd.base.BaseActivity
import com.lrd.contract.WebContract
import com.lrd.presenter.WebPresenter
import com.zzh.netcontrol.R
import kotlinx.android.synthetic.main.my_web_layout.*
import kotlinx.android.synthetic.main.toolbar_layout.*

/**
 * Created By LRD
 * on 2019/4/10  notes：
 */
class WebViewActivity : BaseActivity<WebPresenter>(),WebContract.View{
    private lateinit var mWebView: WebView

    override fun getLayoutId(): Int {
        return R.layout.my_web_layout
    }

    override fun getPresenter(): WebPresenter {
        return WebPresenter()
    }

    override fun initialize() {
        init()
    }

    @SuppressLint("SetJavaScriptEnabled", "ObsoleteSdkInt")
    override fun init() {
        mWebView = WebView(this)
        val layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
        mWebView.layoutParams = layoutParams
        webFL.addView(mWebView)
        title_box.visibility = View.GONE

        //一些设置
        val settings = mWebView.settings
        settings.javaScriptEnabled = true//支持js
        settings.cacheMode = WebSettings.LOAD_NO_CACHE//设置缓存方式,不使用缓存，只从网络获取数据。
        settings.defaultTextEncodingName = "utf-8"//设置默认编码
        settings.useWideViewPort = false//将图片调整到适合webview的大小
        settings.domStorageEnabled = true
        settings.setSupportZoom(true)//支持缩放
        settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN//支持内容重新布局
        //settings.supportMultipleWindows();//多窗口
        settings.allowFileAccess = true//设置可以访问文件
        settings.setNeedInitialFocus(true)//当webview调用requestFocus时为webview设置节点
        settings.builtInZoomControls = false//设置支持缩放按钮
        settings.javaScriptCanOpenWindowsAutomatically = true//支持通过JS打开新窗口
        settings.loadWithOverviewMode = true//缩放至屏幕的大小
        settings.loadsImagesAutomatically = true//支持自动加载图片
        settings.textZoom = 100//布局缩放限定
//        settings.blockNetworkImage = false//处理6.0以上http图片不显示
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.mixedContentMode = WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE
        }

        mWebView.webChromeClient = object : WebChromeClient() {
            override fun onReceivedTitle(view: WebView?, title: String?) {
                super.onReceivedTitle(view, title)
                if (title != null) setToolbarTitle(title)
            }

            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
            }
        }
        mWebView.webViewClient = object : WebViewClient() {
            override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
                super.onReceivedError(view, request, error)
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
            }
        }
        mWebView.loadUrl("https://www.baidu.com")
    }

    override fun setToolbarTitle(title: String) {
        title_tv.text = title
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
            mWebView.goBack()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}