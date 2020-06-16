package com.zzh.netcontrol.net.okhttp;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Time 2020/1/3
 * Author Zzh
 * Description
 */
public class OkHttpManager {

    private static volatile OkHttpManager instance = null;
    private OkHttpClient okHttpClient;

    private OkHttpManager() {
        okHttpClient = new OkHttpClient();
    }

    public static OkHttpManager getInstance() {
        synchronized (OkHttpManager.class) {
            if (instance == null) {
                instance = new OkHttpManager();
            }
        }
        return instance;
    }

    /**
     * 同步get请求
     *
     * @param url
     * @return string
     */
    public String syncGetString(String url) {
        Request request = new Request.Builder().url(url).build();
        Call call = okHttpClient.newCall(request);
        try {
            Response response = call.execute();
            ResponseBody body = response.body();
            if (body != null) {
                return body.string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param url
     * @return
     */
    public byte[] sygnGetByteArray(String url) {
        Request request = new Request.Builder().url(url).build();
        Call call = okHttpClient.newCall(request);
        try {
            Response response = call.execute();
            ResponseBody body = response.body();
            if (body != null) {
                return body.bytes();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param url
     * @return
     */
    public InputStream syncGetStream(String url) {
        Request request = new Request.Builder().url(url).build();
        Call call = okHttpClient.newCall(request);
        try {
            Response response = call.execute();
            ResponseBody body = response.body();
            if (body != null) {
                return body.byteStream();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param url
     * @return
     */
    public Reader sygnGetByteCharReader(String url) {
        Request request = new Request.Builder().url(url).build();
        Call call = okHttpClient.newCall(request);
        try {
            Response response = call.execute();
            ResponseBody body = response.body();
            if (body != null) {
                return body.charStream();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //异步get请求
    public void asyncGetString(String url, OkHttpDataCallBack callBack) {
        Request request = new Request.Builder().url(url).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callBack.onFailed();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseStr = response.body().string();
                callBack.onSuccess(responseStr);
            }
        });
    }
}
