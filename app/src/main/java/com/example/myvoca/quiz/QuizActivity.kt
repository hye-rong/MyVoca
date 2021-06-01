package com.example.myvoca.quiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.myvoca.MyDBHelper
import com.example.myvoca.MyViewModel
import com.example.myvoca.R
import com.example.myvoca.Voca
import com.example.myvoca.databinding.ActivityQuizBinding
import com.example.myvoca.databinding.DicrowBinding
import com.example.myvoca.databinding.PagerrowBinding

class QuizActivity : AppCompatActivity() {
    val items = arrayListOf<String>("day1","day2","day3","day4","day5","day6")
    lateinit var binding: ActivityQuizBinding
    lateinit var quizList: ArrayList<Voca>
    lateinit var spinnerAdapter:ArrayAdapter<String>
    lateinit var myDBHelper: MyDBHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        initView()
    }
    private fun init(){
        myDBHelper = MyDBHelper(this)
        quizList = arrayListOf()
        spinnerAdapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item,items)
        spinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
        binding.quizSpinner.adapter = spinnerAdapter

    }
    private fun initView(){
        binding.apply {
            viewPager.adapter = RecyclerViewAdapter()
            viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
            viewPager.offscreenPageLimit = 3
            //viewPager2.currentItem = 1000
            val pageMargin = resources.getDimensionPixelOffset(R.dimen.pageMargin).toFloat()
            val pageOffset = resources.getDimensionPixelOffset(R.dimen.offset).toFloat()
            viewPager.setPageTransformer { page, position ->
                val myOffset = position * -(2 * pageOffset + pageMargin)
                if (position < -1) {
                    page.alpha = 0f
                    page.translationX = -myOffset
                } else if (position <= 1) {
                    val scaleFactor = Math.max(0.7f, 1 - Math.abs(position - 0.14285715f))
                    page.translationX = myOffset
                    page.scaleY = scaleFactor
                    page.alpha = scaleFactor
                } else {
                    page.alpha = 0f
                    page.translationX = myOffset
                }
            }
            quizSpinner.onItemSelectedListener = object:AdapterView.OnItemSelectedListener{
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    quizList = myDBHelper.getDayVoca(position+1, false) as ArrayList<Voca>
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
            }

        }
    }
    inner class RecyclerViewAdapter() : RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() {
        val items = arrayListOf<String>("Mutliple Choice Quiz", "Short Answer Quiz", "Listening Quiz", "Dictation Quiz", "Puzzle Quiz")
        val images = arrayListOf(R.drawable.ic_baseline_format_list_numbered_24,R.drawable.ic_baseline_article_24,
            R.drawable.ic_baseline_headphones_24,R.drawable.ic_baseline_border_color_24,R.drawable.ic_baseline_filter_none_24)
        val itemInfos = arrayListOf("객관식 문제", "주관식 문제", "듣기 문제", "받아 쓰기", "퍼즐 문제")
        val colors = arrayListOf(R.color.first, R.color.second, R.color.third, R.color.fourth, R.color.fifth)

        inner class MyViewHolder(val binding: PagerrowBinding): RecyclerView.ViewHolder(binding.root){
            init {
                binding.itemview.setOnClickListener {
                    when(adapterPosition){
                        0->{
                            if(quizList.size==0)
                                quizList = myDBHelper.getDayVoca(1, false) as ArrayList<Voca>
                            val intent = Intent(this@QuizActivity, QuizMutlipleActivity::class.java)
                            intent.putExtra("quiz", quizList)
                            startActivity(intent)
                        }
                        1->{
                            if(quizList.size==0)
                                quizList = myDBHelper.getDayVoca(1, false) as ArrayList<Voca>
                            val intent = Intent(this@QuizActivity, QuizEssayActivity::class.java)
                            intent.putExtra("quiz", quizList)
                            startActivity(intent)
                        }
                        2->{
                            if(quizList.size==0)
                                quizList = myDBHelper.getDayVoca(1, false) as ArrayList<Voca>
                            val intent = Intent(this@QuizActivity, QuizListeningActivity::class.java)
                            intent.putExtra("quiz", quizList)
                            startActivity(intent)
                        }
                        3->{
                            if(quizList.size==0)
                                quizList = myDBHelper.getDayVoca(1, false) as ArrayList<Voca>
                            val intent = Intent(this@QuizActivity, QuizDictationActivity::class.java)
                            intent.putExtra("quiz", quizList)
                            startActivity(intent)
                        }
                        4->{
                            if(quizList.size==0)
                                quizList = myDBHelper.getDayVoca(1, false) as ArrayList<Voca>
                            val intent = Intent(this@QuizActivity, QuizPuzzleActivity::class.java)
                            intent.putExtra("quiz", quizList)
                            startActivity(intent)
                        }

                    }
                }

            }
        }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewAdapter.MyViewHolder {
            val view = PagerrowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return MyViewHolder(view)
        }

        override fun onBindViewHolder(holder: RecyclerViewAdapter.MyViewHolder, position: Int) {
            holder.binding.apply {
                imageView.setImageDrawable(getDrawable(images[position]))
                itemview.setBackgroundColor(getColor(colors[position]))
                textView.text = items[position]
                textView2.text = itemInfos[position]
            }
        }

        override fun getItemCount(): Int = items.size

    }

}