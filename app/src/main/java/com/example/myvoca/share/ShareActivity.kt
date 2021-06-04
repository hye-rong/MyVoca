package com.example.myvoca.share

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myvoca.R
import com.example.myvoca.databinding.ActivityShareBinding

class ShareActivity : AppCompatActivity() {
    lateinit var binding:ActivityShareBinding
    val shareFragment = ShareFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShareBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initFragment()
    }
    private fun initFragment(){
        val fragment = supportFragmentManager.beginTransaction()
        fragment.replace(R.id.fragment, shareFragment)
        fragment.commit()
    }
}