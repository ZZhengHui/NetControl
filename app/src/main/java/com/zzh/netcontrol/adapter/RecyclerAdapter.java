package com.zzh.netcontrol.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zzh.netcontrol.R;
import com.zzh.netcontrol.widget.DeleteItemLayout;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Time 2020/2/24
 * Author Zzh
 * Description
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerHolder> {
    private OnItemClickListener mListener;
    private Context mContext;
    private ArrayList<String> mDatas;

    public RecyclerAdapter(Context context, ArrayList<String> datas) {
        mContext = context;
        mDatas = datas;
    }

    @NonNull
    @Override
    public RecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        DeleteItemLayout view = (DeleteItemLayout) LayoutInflater.from(mContext).inflate(R.layout.item_recycler, parent, false);
        return new RecyclerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerHolder holder, int position) {
        holder.mRecyclerTv.setText(mDatas.get(position));
        Log.e("RecyclerConvert", mDatas.get(position));
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class RecyclerHolder extends RecyclerView.ViewHolder {

        private final TextView mRecyclerTv;

        RecyclerHolder(@NonNull DeleteItemLayout itemView) {
            super(itemView);
            mRecyclerTv = itemView.findViewById(R.id.recyclerTv);
            TextView mEditTv = itemView.findViewById(R.id.editTv);
            TextView mDelTv = itemView.findViewById(R.id.delTv);

            mRecyclerTv.setOnClickListener(view -> mListener.onItemClick(mDatas, getLayoutPosition()));
            mEditTv.setOnClickListener(v -> mListener.onOneClick(mDatas, getLayoutPosition()));
            mDelTv.setOnClickListener(v -> mListener.onTwoClick(mDatas, getLayoutPosition()));
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(ArrayList<String> datas, int position);

        void onOneClick(ArrayList<String> datas, int position);

        void onTwoClick(ArrayList<String> datas, int position);
    }

}
