package com.example.myvoca.dic

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
import com.example.myvoca.MyDBHelper
import com.example.myvoca.MyViewModel
import com.example.myvoca.R
import com.example.myvoca.Voca
import com.example.myvoca.databinding.FragmentNaverBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup


class NaverFragment : Fragment() {
    lateinit var binding: FragmentNaverBinding
    lateinit var myadapter:MyNaverAdapter
    lateinit var myDBHelper: MyDBHelper

    val myViewModel: MyViewModel by activityViewModels()
    val scope = CoroutineScope(Dispatchers.IO)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentNaverBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myDBHelper = MyDBHelper(requireContext())

        var data = mutableListOf<Voca>()
        myadapter = MyNaverAdapter(data)

        myadapter.btnClickListener = object : MyNaverAdapter.OnBtnClickListener{
            override fun OnBtnClick(holder: MyNaverAdapter.MyViewHolder, data: Voca, pos: Int) {
                val voc = Voca(data.word, data.mean)
                myDBHelper.insertVoca(voc)
                myViewModel.addVoca(voc)
                holder.binding.addImageView.setImageResource(R.drawable.ic_baseline_check_24)
                Toast.makeText(context, "단어 추가", Toast.LENGTH_LONG).show()
            }
        }

        binding.naverRecycler.apply {
            layoutManager = LinearLayoutManager(requireContext(),
                    LinearLayoutManager.VERTICAL, false)

            adapter = myadapter

        }

        binding.searchBtn.setOnClickListener {
            myadapter.items.clear()
            val query = binding.naverEdit.text.toString()
            getWord(query)
            binding.naverEdit.text.clear()
            binding.orientView.orientation = LinearLayout.HORIZONTAL
            binding.naverRecycler.visibility = View.VISIBLE
            val inputMethodManager = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(binding.naverEdit.windowToken, 0)
        }
        binding.naverEdit.setOnEditorActionListener{ textView, action, event ->
            var handled = false

            if (action == EditorInfo.IME_ACTION_DONE) {
                // 키보드 내리기
                val inputMethodManager = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(binding.naverEdit.windowToken, 0)
                handled = true
            }

            handled
        }

    }

    private fun getWord(data: String):MutableList<Voca>{
        val url = "https://dic.daum.net/search.do?q=" + data + "&dic=eng&search_first=Y"
        val dataList = mutableListOf<Voca>()
        scope.launch {
            val doc = Jsoup.connect(url).get()
            val elements = doc.select("div[data-target=word]")
            Log.d("HHH", "word: $elements")
            if(elements==null){
                Toast.makeText(context, "검색결과가 없습니다.", Toast.LENGTH_SHORT).show()
            }
            else {
                var mword = elements.select(".tit_cleansch").text()
                Log.d("HHH", "word: ${elements.select(".tit_cleansch")}")
                Log.d("HHH", "word: ${elements.select(".txt_search")}")
                var mmean = elements.select(".txt_search").first().text()
                Log.d("HHH", "word: $mword mean: $mmean")
                myadapter.items.add(Voca(mword, mmean))
                val words = elements.select("div[name=searchItem]")
                for (w in words) {
                    mword = w.select(".tit_searchword").text()
                    mmean = w.select(".txt_search").first().text()
                    //Log.d("HHH", "word: $mword mean: $mmean")
                    myadapter.items.add(Voca(mword, mmean))
                }
            }
            withContext(Dispatchers.Main){
                myadapter.notifyDataSetChanged()
            }

        }
        return dataList
    }

}