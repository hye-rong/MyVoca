package com.example.myvoca.share

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myvoca.MyDBHelper
import com.example.myvoca.R
import com.example.myvoca.Voca
import com.example.myvoca.databinding.ActivityShareBinding
import com.example.myvoca.databinding.ActivityShareVocaBinding
import com.example.myvoca.dic.MyNaverAdapter
import com.google.firebase.firestore.FirebaseFirestore

class ShareVocaActivity : AppCompatActivity() {
    lateinit var binding: ActivityShareVocaBinding
    lateinit var adapter: VocaAdapter
    lateinit var myDBHelper: MyDBHelper
    var shareList= mutableListOf<Voca>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShareVocaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecycler()
        initView()
    }
    fun initRecycler(){
        shareList = intent.getSerializableExtra("voca") as MutableList<Voca>
        adapter = VocaAdapter(shareList)

        binding.apply {
            recyclerView.layoutManager = LinearLayoutManager(this@ShareVocaActivity,
                LinearLayoutManager.VERTICAL, false)
            recyclerView.adapter = adapter
        }
        adapter.btnClickListener = object : VocaAdapter.OnBtnClickListener{
            override fun OnBtnClick(holder: VocaAdapter.MyViewHolder, data: Voca, pos: Int) {
                val voc = Voca(data.word, data.mean)
                myDBHelper.insertVoca(voc)
                holder.binding.addImageView.setImageResource(R.drawable.ic_baseline_check_24)
                //Toast.makeText(context, "단어 추가", Toast.LENGTH_LONG).show()
            }
        }
    }
    fun initView(){
        myDBHelper = MyDBHelper(this)
        binding.loadBtn.setOnClickListener {
            Log.d("FFF", "setOnClickListener")
            Toast.makeText(this, "다운로드 완료", Toast.LENGTH_SHORT).show()
            for(v in shareList)
                myDBHelper.insertVoca(v)
            finish()
        }
    }
}