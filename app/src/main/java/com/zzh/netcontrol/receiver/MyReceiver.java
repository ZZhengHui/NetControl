package com.zzh.netcontrol.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Time 2020/4/1
 * Author Zzh
 * Description
 */
public class MyReceiver extends BroadcastReceiver {
    private String TAG = getClass().getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        String name = Thread.currentThread().getName();
        Log.e("onReceiveThread", name);
        if (null != intent) {
            String action = intent.getAction();
            if (action != null) {
                switch (action) {
                    case "android.intent.action.HEADSET_PLUG":  //插入耳机
                        if (intent.hasExtra("state")) {
                            if (intent.getIntExtra("state", 0) == 0) {
                                Log.e(TAG, "headset not connected");
                            } else if (intent.getIntExtra("state", 0) == 1) {
                                Log.e(TAG, "headset connected");
                            }
                        }
                        break;
                    case Intent.ACTION_SCREEN_ON:
                        Log.e(TAG, "手机开屏");
                        break;
                    case Intent.ACTION_SCREEN_OFF:
                        Log.e(TAG, "手机锁屏");
                        break;
                    case Intent.ACTION_USER_PRESENT:
                        Log.e(TAG, "手机解锁");
                        break;
                    case "android.intent.action.BOOT_COMPLETED":
                        Log.e(TAG, "手机开机");
                        break;
                    case "android.net.conn.CONNECTIVITY_CHANGE":
                        Log.e(TAG, "网络状态发生变化");
                        break;
                    case "zzh.lz.love":
                        Log.e(TAG, "我喜欢李珍妹妹");
                        break;
                }
            }
        }
    }
}
