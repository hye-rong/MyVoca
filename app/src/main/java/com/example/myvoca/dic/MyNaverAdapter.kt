package com.example.myvoca.dic

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.myvoca.Voca
import com.example.myvoca.databinding.DicrowBinding
import com.example.myvoca.databinding.NaverrowBinding

class MyNaverAdapter(val items:MutableList<Voca>): RecyclerView.Adapter<MyNaverAdapter.MyViewHolder>() {

    interface OnBtnClickListener{
        fun OnBtnClick(holder: MyNaverAdapter.MyViewHolder, data:Voca, pos:Int)
    }

    var btnClickListener:OnBtnClickListener?=null

    inner class MyViewHolder(val binding: NaverrowBinding): RecyclerView.ViewHolder(binding.root){
        init {
            binding.addNaverBtn.setOnClickListener {
                btnClickListener?.OnBtnClick(this, items[adapterPosition], adapterPosition)
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
            wordView.text = items[position].word
            meanView.text = items[position].mean
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }


}