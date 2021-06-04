package com.example.myvoca.check


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myvoca.Voca
import com.example.myvoca.databinding.StarrowBinding

class StarAdapter(val items:MutableList<Voca>): RecyclerView.Adapter<StarAdapter.MyViewHolder>() {

    interface OnItemClickListener{
        fun OnItemClick(holder: RecyclerView.ViewHolder, data:Voca, pos:Int)
    }

    var itemClickListener: OnItemClickListener?=null


    inner class MyViewHolder(val binding: StarrowBinding): RecyclerView.ViewHolder(binding.root){
        init{

            binding.star.setOnClickListener {
                itemClickListener?.OnItemClick(this, items[adapterPosition], adapterPosition)
            }
        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = StarrowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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