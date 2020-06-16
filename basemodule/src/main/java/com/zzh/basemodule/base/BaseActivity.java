package com.zzh.basemodule.base;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

    protected void showToast(String text) {
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }

    protected <T extends View> T getView(@IdRes int id) {
        return (T) findViewById(id);
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
