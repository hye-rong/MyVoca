package com.example.myvoca.share

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.myvoca.Voca
import com.example.myvoca.databinding.DicrowBinding
import com.example.myvoca.databinding.GrouprowBinding
import com.example.myvoca.databinding.NaverrowBinding
import com.example.myvoca.databinding.VocarowBinding
import com.example.myvoca.dic.MyNaverAdapter
import com.example.myvoca.dic.MyRecyclerAdapter

class VocaAdapter(val groups:MutableList<Voca>): RecyclerView.Adapter<VocaAdapter.MyViewHolder>(){

    interface OnBtnClickListener{
        fun OnBtnClick(holder: VocaAdapter.MyViewHolder, data:Voca, pos:Int)
    }

    var btnClickListener:OnBtnClickListener?=null

    inner class MyViewHolder(val binding: NaverrowBinding): RecyclerView.ViewHolder(binding.root){
        init {
            binding.addNaverBtn.setOnClickListener {
                btnClickListener?.OnBtnClick(this, groups[adapterPosition], adapterPosition)
                Log.d("qqq", "setOnClickListener")
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = NaverrowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.apply {
            wordView.text = groups[position].word
            meanView.text = groups[position].mean
        }
    }

    override fun getItemCount(): Int {
        return groups.size
    }

}