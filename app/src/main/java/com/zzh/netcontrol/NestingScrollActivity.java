package com.zzh.netcontrol;

import android.os.Bundle;

import com.zzh.netcontrol.adapter.NestingScrollAdapter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Time 2020/5/20
 * Author Zzh
 * Description
 */
public class NestingScrollActivity extends AppCompatActivity {

    private RecyclerView nsRecycler;
    private NestingScrollAdapter nestingScrollAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_nest_scroll);
        initView();
    }

    private void initView() {
        nsRecycler = findViewById(R.id.nestingScrollRecycler);
        nsRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

        if (nestingScrollAdapter == null) {
            nestingScrollAdapter = new NestingScrollAdapter(getApplicationContext());
            nsRecycler.setAdapter(nestingScrollAdapter);
        } else {
            nestingScrollAdapter.notifyDataSetChanged();
        }
    }
}
