package com.example.myvoca.dic

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myvoca.MyDBHelper
import com.example.myvoca.MyViewModel
import com.example.myvoca.R
import com.example.myvoca.Voca
import com.example.myvoca.databinding.FragmentSearchBinding


class SearchFragment : Fragment() {
    var binding:FragmentSearchBinding?=null
    lateinit var adapter:MyRecyclerAdapter
    lateinit var myDBHelper: MyDBHelper
    val myViewModel: MyViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentSearchBinding.inflate(layoutInflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myDBHelper = MyDBHelper(requireContext())
        binding!!.searchRecyclerView.layoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.VERTICAL, false)


        adapter = MyRecyclerAdapter(myDBHelper.getAllRecord())
        //Toast.makeText(context, "지금 어댑터 새로 만든다!!!!!!!", Toast.LENGTH_SHORT).show()
        adapter.itemClickListener = object: MyRecyclerAdapter.OnItemClickListener{
            override fun OnStarChecked(holder: RecyclerView.ViewHolder,data:Voca, pos:Int, star:Int) {
                myDBHelper.updateStar(data, star)
            }
        }
        binding!!.searchRecyclerView.adapter = adapter

        binding!!.searchEdit.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                val str = s.toString()
                adapter.filter.filter(str)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        myViewModel.addList.observe(viewLifecycleOwner, Observer<Voca>{
            adapter.addVoca(it)
        })
    }

}