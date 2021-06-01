package com.example.myvoca.study

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import com.example.myvoca.*
import com.example.myvoca.databinding.ActivityDayStudyBinding

class DayStudyActivity : AppCompatActivity() {
    lateinit var binding: ActivityDayStudyBinding
    lateinit var myDBHelper: MyDBHelper
    var rate:Int = 0
    val dayViewModel: DayViewModel by viewModels()
    val studyCheckFragment = StudyCheckFragment()
    val studyResultFragment = StudyResultFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDayStudyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        initFragment()
    }
    fun init(){
        myDBHelper = MyDBHelper(this)
        val dayRate = myDBHelper.getRate()
        dayViewModel.dayInfo.value = mutableListOf()

        for(d in dayRate){
            dayViewModel.setDayData(d)
        }
        val day = intent.getIntExtra("day",1)
        rate = dayViewModel.dayInfo.value!![day-1].rate
        dayViewModel.setCurrentDay(Rate(day, rate))
        binding.backBtn.setOnClickListener {
            finish()
        }
    }
    private fun initFragment(){
        val fragment = supportFragmentManager.beginTransaction()
        //fragment.addToBackStack(null)
        fragment.replace(R.id.study_fragment, studyCheckFragment)
        fragment.commit()
        dayViewModel.currentDay.observe(this, Observer{
            if(it.isfinished){
                it.isfinished = false
                val fragment = supportFragmentManager.beginTransaction()
                fragment.replace(R.id.study_fragment, studyResultFragment)
                fragment.commit()
            }
        })

    }

}