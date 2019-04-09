package com.zzh.netcontrol.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Time 2019/4/1
 * Author 13651
 * Description
 */
public abstract class BaseActivity<V extends BaseView, T extends BasePresenter<V>> extends AppCompatActivity implements BaseView {
    protected T presenter;

    @Override
    @SuppressWarnings("unchecked")
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        presenter = initPresenter();
        if (presenter != null) presenter.attachView((V) this);
        initViewAndEvent();
    }

    public void showToast(String text) {
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    public abstract int getLayoutId();

    public abstract void initViewAndEvent();

    public abstract T initPresenter();
}
