package com.zzh.netcontrol.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zzh.netcontrol.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Time 2020/2/24
 * Author Zzh
 * Description
 */
public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuHolder> {

    private Context mContext;
    private ArrayList<String> datas;
    private OnItemClickListener mListener;

    public MenuAdapter(Context context) {
        mContext = context;
        datas = new ArrayList<>();
    }

    @NonNull
    @Override
    public MenuHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_menu, parent, false);
        return new MenuHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuHolder holder, int position) {
        holder.mMenuTv.setText(datas.get(position));
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public void addItem(String item) {
        datas.add(item);
        notifyDataSetChanged();
    }

    class MenuHolder extends RecyclerView.ViewHolder {

        private final TextView mMenuTv;

        MenuHolder(@NonNull View itemView) {
            super(itemView);
            mMenuTv = itemView.findViewById(R.id.menuTv);
            itemView.setOnClickListener(view -> mListener.onItemClick(datas, getLayoutPosition()));
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(ArrayList<String> datas, int positon);
    }
}
