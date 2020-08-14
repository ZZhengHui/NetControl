package com.zzh.netcontrol;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.zzh.netcontrol.fragment.TestFragment;

public class TestFragmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_fragment);

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        TestFragment testFragment = new TestFragment();

        findViewById(R.id.test_tv).setOnClickListener(v -> {

            if (testFragment.isAdded()) {
                transaction.show(testFragment);
            } else {
                transaction.add(R.id.add_fragment, testFragment);
                transaction.commit();
            }
        });

    }
}
