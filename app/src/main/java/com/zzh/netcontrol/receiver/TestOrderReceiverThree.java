package com.zzh.netcontrol.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Time 2020/4/2
 * Author Zzh
 * Description
 */
public class TestOrderReceiverThree extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("TestOrder", "three receive");
    }
}
