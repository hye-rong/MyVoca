package com.example.myvoca.study

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.myvoca.R
import com.example.myvoca.databinding.FragmentStudyCheckBinding
import com.example.myvoca.databinding.FragmentStudyResultBinding


class StudyResultFragment : Fragment() {

    lateinit var binding: FragmentStudyResultBinding
    val dayViewModel: DayViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStudyResultBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rateView.setText((dayViewModel.currentDay.value!!.rate*10).toString()+"%")
        binding.progbar.progress = dayViewModel.currentDay.value!!.rate*10
    }

}