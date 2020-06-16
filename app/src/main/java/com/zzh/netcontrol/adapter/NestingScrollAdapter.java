package com.zzh.netcontrol.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zzh.netcontrol.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Time 2020/5/20
 * Author Zzh
 * Description
 */
public class NestingScrollAdapter extends RecyclerView.Adapter<NestingScrollAdapter.NestingScrollHolder> {

    private Context mContext;
    private ArrayList<String> datas = new ArrayList<>();

    public NestingScrollAdapter(Context context) {
        mContext = context.getApplicationContext();
        for (int i = 0; i < 20; i++) {
            datas.add("校长" + i);
        }
    }

    @NonNull
    @Override
    public NestingScrollHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_nesting_scroll, null, false);
        return new NestingScrollHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NestingScrollHolder holder, int position) {
        holder.nestingTv.setText(datas.get(position));
        Log.e(getClass().getSimpleName(), datas.get(position));
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class NestingScrollHolder extends RecyclerView.ViewHolder {

        TextView nestingTv;

        public NestingScrollHolder(@NonNull View itemView) {
            super(itemView);

            nestingTv = itemView.findViewById(R.id.nestingTv);
        }
    }
}
