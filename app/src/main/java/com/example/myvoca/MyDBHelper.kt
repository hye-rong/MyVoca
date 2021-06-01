package com.example.myvoca

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.Color
import android.util.Log
import android.view.Gravity
import android.widget.TableRow
import android.widget.TextView
import java.util.*

class MyDBHelper(val context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    companion object{
        val DB_NAME = "vocdb2.db"
        val DB_VERSION = 3
        val VOC_TABLE_NAME = "voca"
        val DAY_TABLE_NAME = "dayvoc"
        val WRONG_TABLE_NAME = "wrongvoc"
        val VWORD = "vword"
        val VMEAN = "vmean"
        val VDAY = "vday"
        val VSTAR = "vstar"
        val VCHECK = "vcheck"
        val VRATE = "vrate"
    }

    fun getWrongVoca():MutableList<Voca>{
        val vList = mutableListOf<Voca>()
        val strsql = "select * from $WRONG_TABLE_NAME"
        val db = readableDatabase
        val cursor = db.rawQuery(strsql, null)
        cursor.moveToFirst()
        val attrCount = cursor.columnCount

        if (cursor.count == 0) return vList

        do {
            vList.add(
                    Voca(cursor.getString(0), cursor.getString(1))
            )
        } while (cursor.moveToNext())

        cursor.close()
        db.close()
        return vList
    }

    fun getDayVoca(day:Int, check:Boolean):MutableList<Voca>{
        val vList = mutableListOf<Voca>()
        val strsql:String = if(check)
            "select * from $VOC_TABLE_NAME where $VDAY=$day and $VCHECK=1"
        else
            "select * from $VOC_TABLE_NAME where $VDAY=$day"
        val db = readableDatabase
        val cursor = db.rawQuery(strsql, null)
        cursor.moveToFirst()
        val attrCount = cursor.columnCount

        if (cursor.count == 0) return vList
        if (attrCount != 5) {
            Log.d("QQQQ", "degree가 5가 아님")
            return vList
        }

        do {
            vList.add(
                    Voca(
                            cursor.getString(0), cursor.getString(1),
                            cursor.getInt(2), cursor.getInt(3), cursor.getInt(4)
                    )
            )
        } while (cursor.moveToNext())

        cursor.close()
        db.close()
        return vList
    }

    fun getAllStar():MutableList<Voca>{
        val vList = mutableListOf<Voca>()
        val strsql = "select * from $VOC_TABLE_NAME where $VSTAR=1"
        val db = readableDatabase
        val cursor = db.rawQuery(strsql, null)
        cursor.moveToFirst()
        val attrCount = cursor.columnCount

        if (cursor.count == 0) return vList
        if (attrCount != 5) {
            return vList
        }

        do {
            vList.add(
                    Voca(
                            cursor.getString(0), cursor.getString(1),
                            cursor.getInt(2), cursor.getInt(3), cursor.getInt(4)
                    )
            )
        } while (cursor.moveToNext())

        cursor.close()
        db.close()
        return vList
    }

    fun getAllRecord():MutableList<Voca> {
        val vList = mutableListOf<Voca>()
        val strsql = "select * from $VOC_TABLE_NAME;"
        val db = readableDatabase
        val cursor = db.rawQuery(strsql, null)
        cursor.moveToFirst()
        val attrCount = cursor.columnCount

        if (cursor.count == 0) return vList
        if (attrCount != 5) {
            return vList
        }

        do {
            vList.add(
                Voca(
                    cursor.getString(0), cursor.getString(1),
                    cursor.getInt(2), cursor.getInt(3), cursor.getInt(4)
                )
            )
        } while (cursor.moveToNext())

        cursor.close()
        db.close()
        return vList
    }
    fun updateStar(voca:Voca, star:Int):Boolean{
        val vword = voca.word
        val strsql = "select * from $VOC_TABLE_NAME where $VWORD='$vword'"
        val db = writableDatabase
        val cursor = db.rawQuery(strsql, null)
        val flag = cursor.count!=0
        if(flag){
            cursor.moveToFirst()
            val values = ContentValues()
            values.put(VSTAR, star)
            db.update(VOC_TABLE_NAME,values, "$VWORD=?", arrayOf(vword))
        }
        cursor.close()
        db.close()
        return flag
    }
    fun updateCheck(voca:Voca, check:Int):Boolean{
        val vword = voca.word
        val strsql = "select * from $VOC_TABLE_NAME where $VWORD='$vword'"
        val db = writableDatabase
        val cursor = db.rawQuery(strsql, null)
        val flag = cursor.count!=0
        if(flag){
            cursor.moveToFirst()
            val values = ContentValues()
            values.put(VCHECK, check)
            db.update(VOC_TABLE_NAME,values, "$VWORD=?", arrayOf(vword))
        }
        cursor.close()
        db.close()
        return flag
    }

    fun insert(voca: Voca, db: SQLiteDatabase):Boolean{
        var flag:Boolean

        val values = ContentValues()
        values.put(VWORD, voca.word)
        values.put(VMEAN, voca.mean)
        values.put(VDAY, voca.day)
        values.put(VSTAR, voca.star)
        values.put(VCHECK, voca.check)

        flag = db.insert(VOC_TABLE_NAME, null, values)!! >0
        return flag
    }

    fun insertVoca(voca: Voca):Boolean{
        val strsql = "select * from $VOC_TABLE_NAME where $VWORD='${voca.word}'"
        var db  = readableDatabase

        val cursor = db.rawQuery(strsql, null)
        var flag:Boolean

        val values = ContentValues()
        values.put(VWORD, voca.word)
        values.put(VMEAN, voca.mean)
        values.put(VDAY, voca.day)
        values.put(VSTAR, voca.star)
        values.put(VCHECK, voca.check)

        if(cursor.count!=0){
            flag = db.update(VOC_TABLE_NAME,values, "$VWORD=?", arrayOf(voca.word)) >0
        }else{
            flag = db.insert(VOC_TABLE_NAME, null, values) >0
        }
        return flag
    }


    fun deleteVoca(vword: String):Boolean{
        val strsql = "select * from $VOC_TABLE_NAME where $VWORD='$vword'"
        val db = writableDatabase
        val cursor = db.rawQuery(strsql, null)
        val flag = cursor.count!=0
        if(flag){
            cursor.moveToFirst()
            db.delete(VOC_TABLE_NAME, "$VWORD=?", arrayOf(vword))
        }
        cursor.close()
        db.close()
        return flag
    }

    fun updateRate(vday:Int, vrate:Int):Boolean{
        val strsql = "select * from $DAY_TABLE_NAME where $VDAY=$vday"
        val db = writableDatabase
        val cursor = db.rawQuery(strsql, null)
        val flag = cursor.count!=0
        if(flag){
            cursor.moveToFirst()
            val values = ContentValues()
            values.put(VRATE, vrate)
            db.update(DAY_TABLE_NAME,values, "$VDAY=$vday", null)
        }
        cursor.close()
        db.close()
        return flag
    }
    fun getRate():MutableList<Rate>{
        val rates = mutableListOf<Rate>()
        val strsql = "select * from $DAY_TABLE_NAME;"
        val db = readableDatabase
        val cursor = db.rawQuery(strsql, null)
        cursor.moveToFirst()

        if (cursor.count == 0) return rates

        do {
            rates.add(
                    Rate(cursor.getInt(0), cursor.getInt(1))
            )
        } while (cursor.moveToNext())

        cursor.close()
        db.close()
        return rates
    }
    fun getRateForDay(day:Int):Int{
        var rate:Int
        val strsql = "select * from $DAY_TABLE_NAME where $VDAY=$day"
        val db = readableDatabase
        val cursor = db.rawQuery(strsql, null)
        cursor.moveToFirst()

        if (cursor.count == 0) return 0

        rate = cursor.getInt(1)

        cursor.close()
        db.close()
        return rate
    }
    fun insertWrong(voca:Voca){
        val strsql = "select * from $WRONG_TABLE_NAME where $VWORD='${voca.word}'"
        var db  = readableDatabase

        val cursor = db.rawQuery(strsql, null)


        val values = ContentValues()
        values.put(VWORD, voca.word)
        values.put(VMEAN, voca.mean)

        if(cursor.count==0){
            db.insert(WRONG_TABLE_NAME, null, values)
        }

    }

    override fun onCreate(db: SQLiteDatabase?) {
        Log.d("QQQQ","onCreate")
        //table 만들기 SQL구문
        val create_table = "create table if not exists $VOC_TABLE_NAME("+
                "$VWORD text, "+
                "$VMEAN text, "+
                "$VDAY integer, "+
                "$VSTAR integer, "+
                "$VCHECK integer);"
        db!!.execSQL(create_table)

        val scan = Scanner(context.resources.openRawResource(R.raw.words))
        var num = 0
        while(scan.hasNextLine()){
            val v = Voca(scan.nextLine(), scan.nextLine(), num++/10+1)
            insert(v, db)
        }
        scan.close()


        val create_table2 = "create table if not exists $DAY_TABLE_NAME("+
                "$VDAY integer primary key, "+
                "$VRATE integer);"
        db.execSQL(create_table2)
        for(i in 1 until 7){
            val values = ContentValues()
            values.put(VDAY, i)
            values.put(VRATE, 0)

            db.insert(DAY_TABLE_NAME, null, values)
        }
        val create_table3 = "create table if not exists $WRONG_TABLE_NAME("+
                "$VWORD text, "+
                "$VMEAN text);"
        db.execSQL(create_table3)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        //table 삭제
        var drop_table = "drop table if exists $VOC_TABLE_NAME;"
        db!!.execSQL(drop_table)
        drop_table = "drop table if exists $DAY_TABLE_NAME;"
        db!!.execSQL(drop_table)
        drop_table = "drop table if exists $WRONG_TABLE_NAME;"
        db!!.execSQL(drop_table)
        onCreate(db)
    }


}