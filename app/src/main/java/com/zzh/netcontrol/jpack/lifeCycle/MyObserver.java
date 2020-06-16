package com.zzh.netcontrol.jpack.lifeCycle;

import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

/**
 * Time 2020/5/13
 * Author Zzh
 * Description
 */
public class MyObserver implements LifecycleObserver {

    private String TAG = getClass().getSimpleName();

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void connectListener() {
        Log.d(TAG, "connectListener");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void disconnectListener() {
        Log.d(TAG, "disconnectListener");
    }

}
