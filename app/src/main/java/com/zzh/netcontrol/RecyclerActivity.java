package com.zzh.netcontrol;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.zzh.netcontrol.adapter.RecyclerAdapter;
import com.zzh.netcontrol.service.JobSchedulerService;
import com.zzh.netcontrol.widget.DeleteItemManager;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Time 2020/2/24
 * Author Zzh
 * Description
 */
public class RecyclerActivity extends AppCompatActivity {

    private RecyclerView mRecycler;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
        initView();
    }

    private void initView() {

//        findViewById(R.id.testScroll).setNestedScrollingEnabled(false);

        ArrayList<String> datas = new ArrayList<>();
        for (int i = 1; i < 20; i++) {
            datas.add("抗疫" + i + "队");
        }
        int spacing12 = getResources().getDimensionPixelOffset(R.dimen.dp12);
        mRecycler = findViewById(R.id.recycler);
        mRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        mRecycler.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.top = spacing12;
            }
        });
        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(getApplicationContext(), datas);
        mRecycler.setAdapter(recyclerAdapter);

        recyclerAdapter.setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ArrayList<String> datas, int position) {
                Toast.makeText(getApplicationContext(), datas.get(position), Toast.LENGTH_SHORT).show();

                //创建JobScheduler对象
                JobScheduler scheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
                JobInfo.Builder builder = new JobInfo.Builder(1, new ComponentName(getApplicationContext(), JobSchedulerService.class));
                builder.setPeriodic(3000);  //每隔一段时间触发
                builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED);     //不是移动网络的情况下触发 (默认为NONE)
                builder.setRequiresCharging(true);      //充电情况下
                if (scheduler != null) {
                    scheduler.schedule(builder.build());
                }
            }

            @Override
            public void onOneClick(ArrayList<String> datas, int position) {
                Toast.makeText(getApplicationContext(), "top", Toast.LENGTH_SHORT).show();
                String curItem = datas.get(position);
                datas.remove(position);
                datas.add(0, curItem);
                mRecycler.scrollToPosition(0);
                DeleteItemManager.getInstance().closeItem();
                recyclerAdapter.notifyItemMoved(position, 0);
            }

            @Override
            public void onTwoClick(ArrayList<String> datas, int position) {
                Toast.makeText(getApplicationContext(), "delete", Toast.LENGTH_SHORT).show();
                datas.remove(position);
                recyclerAdapter.notifyItemRemoved(position);
            }
        });

        mRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                DeleteItemManager.getInstance().closeItem();
            }
        });

    }
}
