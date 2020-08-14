package com.zzh.netcontrol.net.retrofit;

//import com.zzh.netcontrol.AndroidScheduler;

import com.zzh.netcontrol.net.Api;

import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Time 2019/11/7
 * Author Zzh
 * Description
 */
public class RetrofitUtil {

    private static volatile RetrofitUtil instance = null;
    private final Retrofit retrofit;


    private RetrofitUtil() {
        retrofit = new Retrofit.Builder()
                .baseUrl(Api.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static RetrofitUtil getInstance() {
        synchronized (RetrofitUtil.class) {
            if (instance == null) {
                instance = new RetrofitUtil();
            }
        }
        return instance;
    }

    private Api getApi() {
        return retrofit.create(Api.class);
    }

    public void get() {
        getApi().getData()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

}
