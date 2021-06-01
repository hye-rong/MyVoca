package com.example.myvoca

import java.io.Serializable

data class Voca(var word:String, var mean:String, var day:Int=0, var star:Int=0, var check:Int=1):Serializable {
}