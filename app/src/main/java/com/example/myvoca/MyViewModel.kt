package com.example.myvoca

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyViewModel : ViewModel() {
    val addList = MutableLiveData<Voca>()
    fun addVoca(voca:Voca){
        addList.value = voca
    }
    val vocaList = MutableLiveData<MutableList<Voca>>()
    fun setLiveData(voca: Voca){
        vocaList.value?.add(voca)
    }
}