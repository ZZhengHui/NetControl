package com.zzh.netcontrol;

import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Date 2020/4/8
 * Author Zzh
 * Description 测试EventBus
 */
public class TestEventBusActivity extends AppCompatActivity implements View.OnClickListener {
    private String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_event_bus);
        initView();
    }

    private void initView() {
        findViewById(R.id.registerBtn).setOnClickListener(this);
        findViewById(R.id.sendNormalEvent).setOnClickListener(this);
        findViewById(R.id.sendStickyEvent).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.registerBtn:
                EventBus.getDefault().register(this);
                break;
            case R.id.sendNormalEvent:
                Student student = new Student();
                student.setName("李钢铁");
                EventBus.getDefault().post(student);
                break;
            case R.id.sendStickyEvent:
                Message msg = Message.obtain();
                msg.obj = "only you ~!";
                EventBus.getDefault().post(msg);
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveStickyEvent(Message message) {
        Log.e(TAG, "receiveStickyEvent " + Thread.currentThread().getName());
    }

    @Subscribe
    public void receiveNormalEvent(Student student) {
        Log.e(TAG, "receiveNormalEvent "+ Thread.currentThread().getName());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
