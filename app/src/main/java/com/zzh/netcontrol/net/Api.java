package com.zzh.netcontrol.net;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Time 2020/5/13
 * Author Zzh
 * Description
 */
public interface Api {
    String baseUrl = "";

    @GET("")
    Observable<String> getData();
}
