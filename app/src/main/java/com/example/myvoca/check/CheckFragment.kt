package com.example.myvoca.check

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myvoca.MyDBHelper
import com.example.myvoca.R
import com.example.myvoca.databinding.FragmentCheckBinding



class CheckFragment : Fragment(), RecyclerItemTouchHelper.RecyclerITHListener {
    lateinit var binding: FragmentCheckBinding
    lateinit var adapter: CheckAdapter
    lateinit var myDBHelper: MyDBHelper
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCheckBinding.inflate(layoutInflater, container, false)
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

        adapter = CheckAdapter(requireContext(), myDBHelper.getWrongVoca())
        binding.starRecyclerView.adapter = adapter
        val itemTouchHelperCallback = RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this)
        ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(binding.starRecyclerView)

    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder?, direction: Int, position: Int) {
        if (viewHolder != null) {
            myDBHelper.deleteVoca(adapter.items[viewHolder.adapterPosition])
            adapter.removeItem(viewHolder.adapterPosition)
        }

    }

}