package com.lrd.net

import android.util.Log
import com.zzh.netcontrol.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

/**
 * Created By LRD
 * on 2019/4/2  notes：
 */
class OkHttpManager {
    private var mTimeOut = 60L
    var mBuilder : OkHttpClient.Builder;

    companion object{
        @Volatile
        private var instance: OkHttpManager? = null

        val getInstance: OkHttpManager?  get() {
            if (instance == null){
                synchronized(OkHttpManager::class.java) {
                    if (instance == null) {
                        instance = OkHttpManager()
                    }
                }
            }
            return instance
        }
    }

    init{
        mBuilder = OkHttpClient.Builder()
        mBuilder.connectTimeout(mTimeOut, TimeUnit.SECONDS)
                .readTimeout(mTimeOut,TimeUnit.SECONDS)
                .writeTimeout(mTimeOut,TimeUnit.SECONDS)
                .addInterceptor(getLog())
    }

    fun getOkHttpBuilder(): OkHttpClient.Builder {
        return mBuilder
    }

    fun getOkHttpClient(): OkHttpClient{
        return mBuilder.build()
    }

    fun setTimeOut(timeOut:Long){
        this.mTimeOut = timeOut
    }

    private fun getLog():HttpLoggingInterceptor{
        val loggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message ->
            if (BuildConfig.DEBUG) {
                Log.i("NetWork==", message)
            }
        })
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }
}