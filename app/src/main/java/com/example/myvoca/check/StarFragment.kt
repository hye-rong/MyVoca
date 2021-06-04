package com.example.myvoca.check

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myvoca.MyDBHelper
import com.example.myvoca.R
import com.example.myvoca.Voca
import com.example.myvoca.databinding.FragmentStarBinding


class StarFragment : Fragment() {
    lateinit var binding: FragmentStarBinding
    lateinit var adapter: StarAdapter
    lateinit var myDBHelper: MyDBHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStarBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }
    private fun init(){
        myDBHelper = MyDBHelper(requireContext())
        binding.starRecyclerView.layoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.VERTICAL, false)


        adapter = StarAdapter(myDBHelper.getAllStar())
        //Toast.makeText(context, "지금 어댑터 새로 만든다!!!!!!!", Toast.LENGTH_SHORT).show()
        adapter.itemClickListener = object: StarAdapter.OnItemClickListener {
            override fun OnItemClick(holder: RecyclerView.ViewHolder, data: Voca, pos:Int) {
                adapter.items.removeAt(pos)
                adapter.notifyItemRemoved(pos)
                myDBHelper.updateStar(data, 0)
            }
        }
        binding.starRecyclerView.adapter = adapter
    }


}