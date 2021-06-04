package com.example.myvoca

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myvoca.databinding.ActivityMainBinding
import com.example.myvoca.dic.DicActivity
import com.example.myvoca.quiz.QuizActivity
import com.example.myvoca.check.StarCheckActivity
import com.example.myvoca.share.ShareActivity
import com.example.myvoca.study.StudyActivity
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var myDBHelper: MyDBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }
    private fun init(){
        myDBHelper = MyDBHelper(this)
        binding.apply {
            dicView.setOnClickListener {
                val intent = Intent(this@MainActivity, DicActivity::class.java)
                startActivity(intent)
            }
            studyView.setOnClickListener {
                val intent = Intent(this@MainActivity, StudyActivity::class.java)
                startActivity(intent)
            }
            quizView.setOnClickListener {
                val intent = Intent(this@MainActivity, QuizActivity::class.java)
                startActivity(intent)
            }
            starView.setOnClickListener {
                val intent = Intent(this@MainActivity, StarCheckActivity::class.java)
                startActivity(intent)
            }
            shareView.setOnClickListener {
                val intent = Intent(this@MainActivity, ShareActivity::class.java)
                startActivity(intent)
            }
        }
    }
}