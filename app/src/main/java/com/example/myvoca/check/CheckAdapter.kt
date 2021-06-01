package com.example.myvoca.check

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myvoca.Voca
import com.example.myvoca.databinding.CheckrowBinding


class CheckAdapter(val items:MutableList<Voca>): RecyclerView.Adapter<CheckAdapter.MyViewHolder>() {


    inner class MyViewHolder(val binding: CheckrowBinding): RecyclerView.ViewHolder(binding.root){

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = CheckrowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.apply {
            wordView.text = items[position].word
            meanView.text = items[position].mean
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }


}