package com.example.myvoca.star

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myvoca.MyDBHelper
import com.example.myvoca.R
import com.example.myvoca.Voca
import com.example.myvoca.databinding.ActivityStarBinding
import com.example.myvoca.dic.MyRecyclerAdapter

class StarActivity : AppCompatActivity() {
    lateinit var binding: ActivityStarBinding
    lateinit var adapter:StarAdapter
    lateinit var myDBHelper: MyDBHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStarBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }
    private fun init(){
        myDBHelper = MyDBHelper(this)
        binding.starRecyclerView.layoutManager = LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false)


        adapter = StarAdapter(myDBHelper.getAllStar())
        //Toast.makeText(context, "지금 어댑터 새로 만든다!!!!!!!", Toast.LENGTH_SHORT).show()
        adapter.itemClickListener = object: StarAdapter.OnItemClickListener{
            override fun OnItemClick(holder: RecyclerView.ViewHolder, data: Voca, pos:Int) {
                adapter.items.removeAt(pos)
                adapter.notifyItemRemoved(pos)
                myDBHelper.updateStar(data, 0)
            }
        }
        binding.starRecyclerView.adapter = adapter
    }
}