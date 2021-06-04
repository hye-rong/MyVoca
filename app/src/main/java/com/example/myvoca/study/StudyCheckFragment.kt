package com.example.myvoca.study

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.myvoca.MyDBHelper
import com.example.myvoca.Rate
import com.example.myvoca.Voca
import com.example.myvoca.databinding.FragmentStudyCheckBinding
import java.util.*


class StudyCheckFragment : Fragment() {

    lateinit var binding: FragmentStudyCheckBinding
    lateinit var myDBHelper: MyDBHelper
    lateinit var vocaList: MutableList<Voca>
    lateinit var tts: TextToSpeech
    var isTtsReady = false
    var day:Int = 0
    var position:Int = 0
    var check:Int = 0
    var starCheck:Int = 0
    var wordOrMean:Boolean = true
    val dayViewModel: DayViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStudyCheckBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        initView()
        initTTS()
    }

    private fun initTTS(){
        tts = TextToSpeech(requireContext(), TextToSpeech.OnInitListener {
            isTtsReady = true
            tts.language = Locale.ENGLISH
        })
    }
    private fun initData(){
        myDBHelper = MyDBHelper(requireContext())
        vocaList = mutableListOf()
        if(dayViewModel.currentDay.value==null){
            Log.d("aaa", "null")
        }
        day = dayViewModel.currentDay.value!!.day
        starCheck = dayViewModel.currentDay.value!!.rate
        vocaList = myDBHelper.getDayVoca(day, true)
        Log.d("aaa", "day : $day, starCheck : $starCheck, vocaListSize: ${vocaList.size}")
    }
    private fun initView(){
        binding.apply {
            positiveBtn.setOnClickListener {
                myDBHelper.updateCheck(vocaList[position], 0)
                check++
                position++
                wordOrMean = true
                if(position >= vocaList.size)
                {
                    dayViewModel.setCurrentDay(Rate(day, starCheck + check, true))
                    myDBHelper.updateRate(day, starCheck + check)
                }
                else{
                    dayViewModel.setCurrentDay(Rate(day, starCheck + check, false))
                    myDBHelper.updateRate(day, starCheck + check)
                    setView()
                }
            }
            negativeBtn.setOnClickListener {
                position++
                wordOrMean = true
                if(position>= vocaList.size){
                    dayViewModel.setCurrentDay(Rate(day, starCheck + check, true))
                    myDBHelper.updateRate(day, starCheck + check)
                }
                else{
                    dayViewModel.setCurrentDay(Rate(day, starCheck + check, false))
                    myDBHelper.updateRate(day, starCheck + check)
                    setView()
                }

            }
            starBtn.setOnCheckedChangeListener { buttonView, isChecked ->
                if(isChecked) {
                    myDBHelper.updateStar(vocaList[position], 1)
                    vocaList[position].star = 1
                }
                else {
                    myDBHelper.updateStar(vocaList[position], 0)
                    vocaList[position].star = 0
                }
            }
            ttsBtn.setOnClickListener {
                if(isTtsReady){
                    tts.speak(vocaList[position].word, TextToSpeech.QUEUE_ADD, null, null)
                }
            }
            wordView.setOnClickListener {
                wordOrMean = !wordOrMean
                setView()
            }
        }
        setView()
    }

    private fun setView(){
        binding.apply {
            if(wordOrMean)
                wordView.text = vocaList[position].word
            else
                wordView.text = vocaList[position].mean
            starBtn.isChecked = vocaList[position].star > 0
        }
    }
    override fun onStop() {
        super.onStop()
        tts.stop()
    }

    override fun onDestroy() {
        super.onDestroy()
        tts.shutdown()
    }

}