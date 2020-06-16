package com.zzh.netcontrol;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.zzh.netcontrol.receiver.MyReceiver;
import com.zzh.netcontrol.receiver.TestOrderReceiverOne;
import com.zzh.netcontrol.receiver.TestOrderReceiverThree;
import com.zzh.netcontrol.receiver.TestOrderReceiverTwo;
import com.zzh.netcontrol.service.TestService;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class TestActivity extends AppCompatActivity {
    private String TAG = getClass().getSimpleName();
    private TestService mService;
    private Intent startIntent;
    private MyReceiver myReceiver;
    private LocalBroadcastManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        manager = LocalBroadcastManager.getInstance(getApplicationContext());
        myReceiver = new MyReceiver();

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.HEADSET_PLUG");
        registerReceiver(myReceiver, intentFilter);

        IntentFilter intentFilter1 = new IntentFilter();
        intentFilter1.addAction(Intent.ACTION_SCREEN_ON);
        intentFilter1.addAction(Intent.ACTION_SCREEN_OFF);
        intentFilter1.addAction(Intent.ACTION_USER_PRESENT);
        registerReceiver(myReceiver, intentFilter1);

        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(myReceiver, intentFilter2);

//        有序广播
        IntentFilter intentFilter3 = new IntentFilter();
        intentFilter3.addAction("zzh.lz.love");
        intentFilter3.setPriority(100);
        manager.registerReceiver(new TestOrderReceiverOne(), intentFilter3);

        IntentFilter intentFilter4 = new IntentFilter();
        intentFilter4.addAction("zzh.lz.love");
        intentFilter4.setPriority(1000);
        manager.registerReceiver(new TestOrderReceiverTwo(), intentFilter4);

        IntentFilter intentFilter5 = new IntentFilter();
        intentFilter5.addAction("zzh.lz.love");
        intentFilter5.setPriority(800);
        manager.registerReceiver(new TestOrderReceiverThree(), intentFilter5);

        IntentFilter intentFilter6 = new IntentFilter();
        intentFilter6.addAction("zzh.lz.love");
        manager.registerReceiver(myReceiver, intentFilter6);


        findViewById(R.id.start).setOnClickListener(v -> {
            startIntent = new Intent(getApplicationContext(), TestService.class);
            startService(startIntent);
        });

        findViewById(R.id.bind).setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), TestService.class);
            bindService(intent, MyServiceConnection, Context.BIND_AUTO_CREATE);
        });

        findViewById(R.id.stopStartService).setOnClickListener(v -> {
            stopService(startIntent);
        });

        findViewById(R.id.stopBindService).setOnClickListener(v -> {
            if (mService != null) {
                mService.test("activity send string");
                unbindService(MyServiceConnection);
                mService = null;
            }
        });

//        发送自定义广播
        findViewById(R.id.createReceive).setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setAction("zzh.lz.love");
//            sendBroadcast(intent);  //发送标准广播
//            sendOrderedBroadcast(intent, null);     //发送有序广播
            manager.sendBroadcastSync(intent);

        });

        findViewById(R.id.testProvider).setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), TestProviderActivity.class)));
    }

    private ServiceConnection MyServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e(TAG, "onServiceConnected");
            TestService.TestBinder binder = (TestService.TestBinder) service;
            mService = binder.getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e(TAG, "onServiceDisconnected");
            mService = null;
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mService != null) {
            unbindService(MyServiceConnection);
        }

        unregisterReceiver(myReceiver);
        manager.unregisterReceiver(myReceiver);
    }
}
