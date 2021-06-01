package com.example.myvoca.study

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import com.example.myvoca.MyDBHelper
import com.example.myvoca.MyViewModel
import com.example.myvoca.R
import com.example.myvoca.Rate
import com.example.myvoca.databinding.ActivityStudyBinding

class StudyActivity : AppCompatActivity() {
    lateinit var binding : ActivityStudyBinding
    lateinit var myDBHelper: MyDBHelper
    lateinit var dayRate:MutableList<Rate>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStudyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }
    private fun init(){
        binding.apply {
            day1.setOnClickListener { btnClick(1) }
            day2.setOnClickListener { btnClick(2) }
            day3.setOnClickListener { btnClick(3) }
            day4.setOnClickListener { btnClick(4) }
            day5.setOnClickListener { btnClick(5) }
            day6.setOnClickListener { btnClick(6) }
        }
    }
    private fun btnClick(day:Int){
        if(myDBHelper.getRateForDay(day)==10){
            Toast.makeText(this, "학습완료", Toast.LENGTH_LONG).show()
        }
        else {
            val intent = Intent(this, DayStudyActivity::class.java)
            intent.putExtra("day", day)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        myDBHelper = MyDBHelper(this)
        dayRate = myDBHelper.getRate()

        binding.apply {
            for(day in dayRate){
                when(day.day){
                    1-> progbar1.setProgress(day.rate*10)
                    2-> progbar2.setProgress(day.rate*10)
                    3-> progbar3.setProgress(day.rate*10)
                    4-> progbar4.setProgress(day.rate*10)
                    5-> progbar5.setProgress(day.rate*10)
                    6-> progbar6.setProgress(day.rate*10)
                }
            }
        }
    }
}