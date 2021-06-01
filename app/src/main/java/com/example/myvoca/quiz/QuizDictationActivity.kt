package com.example.myvoca.quiz

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.example.myvoca.MyDBHelper
import com.example.myvoca.R
import com.example.myvoca.Voca
import com.example.myvoca.databinding.ActivityQuizDictationBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class QuizDictationActivity : AppCompatActivity() {
    lateinit var binding: ActivityQuizDictationBinding
    lateinit var tts: TextToSpeech
    lateinit var myDBHelper: MyDBHelper

    var isTtsReady = false
    var pos:Int = 0 //현재 문제 position
    var quizList = ArrayList<Voca>()
    val scope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizDictationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initTTS()
        initData()
        initView()
        playQuiz()
    }
    private fun initTTS(){
        tts = TextToSpeech(this, TextToSpeech.OnInitListener {
            isTtsReady = true
            tts.language = Locale.ENGLISH
        })
    }
    private fun initData(){
        myDBHelper = MyDBHelper(this)
        quizList = intent.getSerializableExtra("quiz") as ArrayList<Voca>
    }
    private fun initView(){
        binding.apply {
            enterBtn.setOnClickListener {
                val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(binding.editText.windowToken, 0)
                if(quizList[pos].word.trim().equals(editText.text.toString().trim(), ignoreCase = true)) {
                    editLayout.setBackgroundResource(R.drawable.card_blue)
                    wordView.setTextColor(getColor(R.color.hit))
                }
                else {
                    editLayout.setBackgroundResource(R.drawable.card_red)
                    wordView.setTextColor(getColor(R.color.nohit))
                    myDBHelper.insertWrong(quizList[pos])
                }
                binding.wordLayout.visibility = View.VISIBLE

                scope.launch {
                    delay(3000L)
                    binding.wordLayout.visibility = View.INVISIBLE
                    delay(500L)
                    editLayout.setBackgroundResource(R.drawable.card_layout)
                    pos++
                    if(pos<quizList.size){
                        playQuiz()
                    }
                    else{
                        finish()
                    }
                }
            }
            soundBtn.setOnClickListener {
                if(isTtsReady){
                    tts.speak(quizList[pos].word, TextToSpeech.QUEUE_ADD, null, null)
                }
            }
        }
    }

    private fun playQuiz(){
        binding.apply {
            editText.text.clear()
            wordView.text = quizList[pos].word
            meanView.text = quizList[pos].mean
            quizPos.text = "$pos/${quizList.size}"
        }
        if(isTtsReady){
            tts.speak(quizList[pos].word, TextToSpeech.QUEUE_ADD, null, null)
        }
    }
}