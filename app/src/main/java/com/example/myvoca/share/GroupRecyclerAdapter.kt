package com.example.myvoca.share

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.myvoca.Voca
import com.example.myvoca.databinding.DicrowBinding
import com.example.myvoca.databinding.GrouprowBinding
import com.example.myvoca.dic.MyRecyclerAdapter

class GroupRecyclerAdapter(val groups:MutableList<Group>): RecyclerView.Adapter<GroupRecyclerAdapter.MyViewHolder>(){


    interface OnItemClickListener{
        fun OnItemClick(holder: RecyclerView.ViewHolder, data: Group, pos:Int)
    }

    var itemClickListener:OnItemClickListener?=null

    inner class MyViewHolder(val binding: GrouprowBinding): RecyclerView.ViewHolder(binding.root){

        init{
            binding.itemView.setOnClickListener {
                itemClickListener?.OnItemClick(this,groups[adapterPosition], adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = GrouprowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.apply {
            infoView.text = groups[position].gInfo
            nameView.text = groups[position].gName
        }
    }

    override fun getItemCount(): Int {
        return groups.size
    }

}