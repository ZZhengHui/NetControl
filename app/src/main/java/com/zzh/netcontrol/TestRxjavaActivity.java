package com.zzh.netcontrol;

import android.os.Bundle;
import android.util.Log;

import com.zzh.netcontrol.jpack.lifeCycle.MyObserver;

import java.util.concurrent.TimeUnit;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Time 2020/5/7
 * Author Zzh
 * Description  Rxjava的使用
 */
public class TestRxjavaActivity extends AppCompatActivity {
    String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_rx_layout);
        getLifecycle().addObserver(new MyObserver());
        initView();
    }

    private void initView() {
        findViewById(R.id.sendEventTv).setOnClickListener(v -> {
            testRxjava();
        });
    }

    private void testRxjava() {

        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("1");
            }
        })
                .map(new Function<String, Integer>() {
                    @Override
                    public Integer apply(String s) throws Exception {
                        return Integer.valueOf(s) * 10;
                    }
                })
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer s) throws Exception {
                        Log.e(TAG, s.toString());
                    }
                });

        Observable.create((ObservableOnSubscribe<Long>) emitter -> {
            emitter.onNext(1L);

            TimeUnit.MILLISECONDS.sleep(405);
            emitter.onNext(2L);

            TimeUnit.MILLISECONDS.sleep(401);
            emitter.onNext(3L);

            TimeUnit.MILLISECONDS.sleep(100);
            emitter.onNext(4L);

            TimeUnit.MILLISECONDS.sleep(200);
            emitter.onNext(5L);
        })
                .debounce(400, TimeUnit.MILLISECONDS)   //取每400ms内发出的第一个事件
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Log.d(TAG, aLong.toString());
                    }
                });

    }
}
