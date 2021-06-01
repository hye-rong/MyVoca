package com.example.myvoca.quiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.example.myvoca.MyDBHelper
import com.example.myvoca.R
import com.example.myvoca.Voca
import com.example.myvoca.databinding.ActivityQuizListeningBinding
import com.example.myvoca.databinding.DialogAnswerBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class QuizListeningActivity : AppCompatActivity() {

    lateinit var binding: ActivityQuizListeningBinding
    lateinit var tts: TextToSpeech
    lateinit var myDBHelper: MyDBHelper

    var isTtsReady = false
    var pos:Int = 0 //현재 문제 position
    var answer:Int = 0 // 현재 문제의 답의 위치
    var quizList = ArrayList<Voca>()
    val random = Random()
    var arraylist = ArrayList<Int>()
    val scope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizListeningBinding.inflate(layoutInflater)
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
            view1.setOnClickListener {
                if(answer==0)
                    quizResult(it, true, 0)
                else
                    quizResult(it, false,0)
            }
            view2.setOnClickListener {
                if(answer==1)
                    quizResult(it, true,1)
                else
                    quizResult(it, false,1)
            }
            view3.setOnClickListener {
                if(answer==2)
                    quizResult(it, true,2)
                else
                    quizResult(it, false,2)
            }
            view4.setOnClickListener {
                if(answer==3)
                    quizResult(it, true,3)
                else
                    quizResult(it, false,3)
            }
            soundBtn.setOnClickListener {
                if(isTtsReady){
                    tts.speak(quizList[pos].word, TextToSpeech.QUEUE_ADD, null, null)
                }
            }
        }
    }

    private fun quizResult(view: View, res:Boolean, wrong:Int){
        if(res){ //정답
            view.setBackgroundResource(R.drawable.card_blue)
        }
        else{   //오답
            view.setBackgroundResource(R.drawable.card_red)
            myDBHelper.insertWrong(quizList[pos])

        }
        binding.wordLayout.visibility = View.VISIBLE

        scope.launch {
            delay(3000L)
            binding.wordLayout.visibility = View.INVISIBLE
            delay(500L)
            view.setBackgroundResource(R.drawable.card_layout)
            pos++
            if(pos<quizList.size){
                playQuiz()
            }
            else{
                finish()
            }
        }

    }
    private fun playQuiz(){
        binding.apply {
            wordView.text = quizList[pos].word
            meanView.text = quizList[pos].mean
            quizPos.text = "$pos/${quizList.size}"
            answer = random.nextInt(4)
            var rand = 0
            arraylist.clear()
            for(i in 0 until 4){
                do {
                    rand = random.nextInt(quizList.size)
                }while (arraylist.contains(rand)||rand==pos)
                arraylist.add(rand)
            }
            arraylist.set(answer, pos)
            text1.text = quizList[arraylist[0]].mean
            text2.text = quizList[arraylist[1]].mean
            text3.text = quizList[arraylist[2]].mean
            text4.text = quizList[arraylist[3]].mean
        }
        if(isTtsReady){
            tts.speak(quizList[pos].word, TextToSpeech.QUEUE_ADD, null, null)
        }
    }
}