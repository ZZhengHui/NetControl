package com.lrd.net

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created By LRD
 * on 2019/4/2  notes：
 */
class NetWorkControl {

    private val mRetrofit:Retrofit
    var mBaseUrl:String = "http://www.baidu.com"
    companion object{
        @Volatile
        private var instance: NetWorkControl? = null

        val getInstance: NetWorkControl?  get() {
            if (instance == null){
                synchronized(NetWorkControl::class.java) {
                    if (instance == null) {
                        instance = NetWorkControl()
                    }
                }
            }
            return instance
        }
    }

    init {
        mRetrofit = Retrofit.Builder()
                            .client(OkHttpManager.getInstance!!.getOkHttpClient())
                            .baseUrl(mBaseUrl)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build()
    }

    fun getApi(): Url{
        return mRetrofit.create(Url::class.java)
    }

}