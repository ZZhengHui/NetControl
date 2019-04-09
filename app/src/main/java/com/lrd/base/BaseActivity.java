package com.lrd.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;


/**
 * Created By LRD
 * on 2019/4/9  notes：
 */
public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity implements BaseView {
	protected T mPresenter;
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(getLayoutId());
		bindView();
	}

	protected abstract int getLayoutId();

	protected abstract T getPresenter();

	protected abstract void initialize();

	@Override
	public void bindView() {
		mPresenter = getPresenter();
		mPresenter.register(this);
		initialize();
	}

	@Override
	public void unBindView() {
		mPresenter.unRegister();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unBindView();
	}

	public void showToast(String text) {
		Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
	}
}
