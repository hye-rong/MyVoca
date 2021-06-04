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
import com.example.myvoca.VocaInfo
import com.example.myvoca.databinding.ActivityQuizEssayBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class QuizEssayActivity : AppCompatActivity() {
    lateinit var binding: ActivityQuizEssayBinding
    lateinit var myDBHelper: MyDBHelper

    var pos:Int = 0
    var quizList = ArrayList<Voca>()
    val scope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizEssayBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initData()
        initView()
        playQuiz()
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
                wordView.text = quizList[pos].word
                if(quizList[pos].word.trim().equals(editText.text.toString().trim(), ignoreCase = true)) {
                    editLayout.setBackgroundResource(R.drawable.card_blue)
                    wordView.setTextColor(getColor(R.color.hit))
                }
                else {
                    editLayout.setBackgroundResource(R.drawable.card_red)
                    wordView.setTextColor(getColor(R.color.nohit))
                    myDBHelper.insertWrong(VocaInfo(quizList[pos].word, quizList[pos].mean, 1, editText.text.toString()))
                }

                scope.launch {
                    delay(3000L)
                    wordView.setTextColor(getColor(R.color.textcolor))
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

        }
    }

    private fun playQuiz(){
        binding.apply {
            editText.text.clear()
            wordView.text = quizList[pos].mean
            quizPos.text = "$pos/${quizList.size}"
        }
    }
}