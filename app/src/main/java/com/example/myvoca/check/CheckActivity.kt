package com.example.myvoca.check

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myvoca.MyDBHelper
import com.example.myvoca.R
import com.example.myvoca.Voca
import com.example.myvoca.databinding.ActivityCheckBinding
import com.example.myvoca.star.StarAdapter

class CheckActivity : AppCompatActivity() {
    lateinit var binding: ActivityCheckBinding
    lateinit var adapter: CheckAdapter
    lateinit var myDBHelper: MyDBHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }
    private fun init(){
        myDBHelper = MyDBHelper(this)
        binding.starRecyclerView.layoutManager = LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false)

        adapter = CheckAdapter(myDBHelper.getWrongVoca())
        binding.starRecyclerView.adapter = adapter
    }
}