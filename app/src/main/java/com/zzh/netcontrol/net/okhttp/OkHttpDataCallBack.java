package com.zzh.netcontrol.net.okhttp;

/**
 * Time 2020/1/3
 * Author Zzh
 * Description
 */
public interface OkHttpDataCallBack {
    void onFailed();

    void onSuccess(String data);
}
