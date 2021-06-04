package com.example.myvoca.share

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myvoca.Voca
import com.example.myvoca.databinding.FragmentShareBinding
import com.google.firebase.firestore.FirebaseFirestore


class ShareFragment : Fragment() {
    lateinit var binding: FragmentShareBinding
    lateinit var adapter: GroupRecyclerAdapter
    var db = FirebaseFirestore.getInstance()
    val shareList= mutableListOf<Group>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentShareBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        //initRecycler()
    }

    fun initData(){
        db.collection("voca")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result!!) {
                        Log.d("FFF", document.id + " => " + document.data)
                        shareList.add(Group(document.id, document.data["info"].toString()))
                    }
                    initRecycler()
                } else {
                    Log.w("FFF", "Error getting documents.", task.exception)
                }
            }
    }

    fun initRecycler(){
        Log.w("FFF", "shareList ${shareList.size}")
        adapter = GroupRecyclerAdapter(shareList)
        adapter.itemClickListener = object: GroupRecyclerAdapter.OnItemClickListener{
            override fun OnItemClick(
                holder: RecyclerView.ViewHolder,
                data: Group,
                pos: Int
            ) {
                db.collection("voca").document(data.gName).collection("wordList")
                .get()
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val vocaList = arrayListOf<Voca>()
                            for (document in task.result!!) {
                                Log.d("FFF", document.id + " => " + document.data)
                                vocaList.add(Voca(document.id, document.data["mean"].toString()))
                            }
                            val intent = Intent(requireContext(), ShareVocaActivity::class.java)
                            intent.putExtra("voca", vocaList)
                            startActivity(intent)

                        } else {
                            Log.w("FFF", "Error getting documents.", task.exception)
                        }
                    }
            }
        }

        binding.apply {
            recyclerView.layoutManager = LinearLayoutManager(requireContext(),
                LinearLayoutManager.VERTICAL, false)
            recyclerView.adapter = adapter
        }
    }


}