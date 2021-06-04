package com.example.myvoca.check

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myvoca.R
import com.example.myvoca.databinding.ActivityStarCheckBinding

class StarCheckActivity : AppCompatActivity() {
    lateinit var binding: ActivityStarCheckBinding
    val starFragment = StarFragment()
    val checkFragment = CheckFragment()
    var flag:Boolean = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStarCheckBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }
    fun init(){
        val fragment = supportFragmentManager.beginTransaction()
        //fragment.addToBackStack(null)
        fragment.replace(R.id.frameLayout, starFragment)
        fragment.commit()
        binding.apply {
            starBtn.setOnCheckedChangeListener { buttonView, isChecked ->
                if(isChecked){
                    checkBtn.isChecked = false
                    //checkBtn.setTextColor(getColorStateList(R.color.basic))
                    starBtn.setTextColor(getColorStateList(R.color.white))
                    if(!starFragment.isVisible){
                        val fragment = supportFragmentManager.beginTransaction()
                        fragment.replace(R.id.frameLayout, starFragment)
                        fragment.commit()
                    }
                }
                else{
                    starBtn.setTextColor(getColorStateList(R.color.basic))
                }
            }
            checkBtn.setOnCheckedChangeListener { buttonView, isChecked ->
                if(isChecked){
                    starBtn.isChecked = false
                    checkBtn.setTextColor(getColorStateList(R.color.white))
                    if(!checkFragment.isVisible){
                        val fragment = supportFragmentManager.beginTransaction()
                        fragment.replace(R.id.frameLayout, checkFragment)
                        fragment.commit()
                    }
                }
                else{
                    checkBtn.setTextColor(getColorStateList(R.color.basic))
                }
            }

        }

    }
}