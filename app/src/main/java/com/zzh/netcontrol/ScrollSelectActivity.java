package com.zzh.netcontrol;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.zzh.basemodule.base.util.ThreadPoolUtils;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class ScrollSelectActivity extends AppCompatActivity {
    private String THREAD_NAME = "SS";
    private String TAG = ScrollSelectActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll_select);
        Log.e(TAG, "onCreate");

        findViewById(R.id.testIntentService).setOnClickListener(v -> {
//            startService(new Intent(getApplicationContext(), TestIntentService.class));
//            startService(new Intent(getApplicationContext(), TestIntentService.class));
//            startService(new Intent(getApplicationContext(), TestIntentService.class));

            /*for (int i = 0; i < 10; i++) {
                final int count = i;
                *//*Runnable runnable = () -> {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String time = sdf.format(new Date(Long.parseLong(System.currentTimeMillis() + "")));
//                    Log.e("ThreadPool Runnable----" + Thread.currentThread().getName(), count + "  " + time);
                };*//*
                ThreadPoolUtils.getInstance().executeTask(THREAD_NAME, new Runnable() {
                    @Override
                    public void run() {
                        Log.e("ThreadPoolName", Thread.currentThread().getName());
                    }
                });
            }*/
            ThreadPoolUtils.getInstance().executeTask(THREAD_NAME, new Runnable() {
                @Override
                public void run() {
                    Log.e("ThreadPoolName", Thread.currentThread().getName());
                }
            });
        });

        findViewById(R.id.testTv).setOnClickListener(v -> {
            int orientation = getResources().getConfiguration().orientation;
            if (orientation == 1) {     //竖屏
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            } else if (orientation == 2) {      //横屏
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }

            ThreadPoolUtils.getInstance().quitThreadPool(THREAD_NAME);
            if (ThreadPoolUtils.getInstance().isShutDown(THREAD_NAME)) {
                Toast.makeText(this, "pool is shutdown", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.testJump).setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), TestActivity.class));
        });
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.e(TAG, "onRestoreInstanceState");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(TAG, "onPause");
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.e(TAG, "onSaveInstanceState");
        outState.putString("123", "hello");
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.e(TAG, "onConfigurationChanged");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(TAG, "onStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e(TAG, "onRestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy");
    }
}
