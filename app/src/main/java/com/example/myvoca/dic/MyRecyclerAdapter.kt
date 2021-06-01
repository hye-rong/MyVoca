package com.example.myvoca.dic

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.myvoca.Voca
import com.example.myvoca.databinding.DicrowBinding

class MyRecyclerAdapter(val items:MutableList<Voca>): RecyclerView.Adapter<MyRecyclerAdapter.MyViewHolder>(),Filterable {

    private var searchItems: MutableList<Voca>?=null

    init {
        searchItems = items
    }

    interface OnItemClickListener{
        fun OnStarChecked(holder: RecyclerView.ViewHolder, data:Voca, pos:Int, star: Int)
    }

    var itemClickListener:OnItemClickListener?=null


    inner class MyViewHolder(val binding: DicrowBinding): RecyclerView.ViewHolder(binding.root){

        init{

            binding.toggleBtn.setOnCheckedChangeListener { buttonView, isChecked ->
                if(isChecked) itemClickListener?.OnStarChecked(this, searchItems!![adapterPosition], adapterPosition, 1)
                else itemClickListener?.OnStarChecked(this, searchItems!![adapterPosition], adapterPosition, 0)
            }
        }
    }

    override fun getFilter(): Filter {
        return object : Filter(){
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint.toString()
                if (charString.isEmpty()) {
                    searchItems = items
                } else {
                    val filteredList = mutableListOf<Voca>()
                    //이부분에서 원하는 데이터를 검색할 수 있음
                    for (row in items) {
                        if (row.word.toLowerCase().contains(charString.toLowerCase()) || row.mean.toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row)
                        }
                    }
                    searchItems = filteredList
                }
                val filterResults = FilterResults()
                filterResults.values = searchItems
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                searchItems = results?.values as MutableList<Voca>
                notifyDataSetChanged()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        //뷰바인딩 사용하려면 ViewHolder에 넘겨줄 view를 Binding을 보내줘야함
        val view = DicrowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.apply {
            wordView.text = searchItems!![position].word
            meanView.text = searchItems!![position].mean
            toggleBtn.isChecked = searchItems!![position].star > 0
        }
    }

    override fun getItemCount(): Int {
        return searchItems!!.size
    }

    fun addVoca(voca:Voca){
        val position = items.size
        items.add(position, voca)
        notifyItemInserted(position)
    }

}