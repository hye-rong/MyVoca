package com.example.myvoca.check

import android.content.ClipData.Item
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myvoca.R
import com.example.myvoca.VocaInfo
import com.example.myvoca.databinding.CheckrowBinding


class CheckAdapter(val context: Context, val items: MutableList<VocaInfo>): RecyclerView.Adapter<CheckAdapter.MyViewHolder>() {

    val colors = arrayListOf(R.color.first, R.color.second, R.color.third, R.color.fourth, R.color.fifth)
    val parts = arrayListOf("Mutliple Choice", "Short Answer", "Listening", "Dictation", "Puzzle")

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
            wrongView.text = "오답: ${items[position].wInput}"
            partView.text = "PART ${parts[items[position].part]}"
            viewForeground.setBackgroundColor(context.getColor(colors[items[position].part]))
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun removeItem(position: Int) {
        items.removeAt(position)
        notifyItemRemoved(position)
    }



}