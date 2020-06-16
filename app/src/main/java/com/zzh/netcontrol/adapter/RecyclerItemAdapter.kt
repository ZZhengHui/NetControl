package com.zzh.netcontrol.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zzh.netcontrol.R

/**
 * Time 2020/4/22
 * Author Zzh
 * Description
 */
class RecyclerItemAdapter(var context: Context, var datas: ArrayList<String>) : RecyclerView.Adapter<RecyclerItemAdapter.RecyclerItemHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerItemHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_recycler, null, false)
        return RecyclerItemHolder(view)
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(holder: RecyclerItemHolder, position: Int) {

    }

    class RecyclerItemHolder(view: View) : RecyclerView.ViewHolder(view) {

    }

}