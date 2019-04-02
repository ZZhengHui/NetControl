package com.zzh.netcontrol.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Time 2019/4/1
 * Author 13651
 * Description
 */
public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity implements BaseView {
    private T presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        presenter = initPresenter();
        if (presenter == null) presenter.attachView(this);
        initViewAndEvent();
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
