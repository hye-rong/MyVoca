package com.example.myvoca.dic

import android.os.Bundle
import android.text.InputFilter
import android.text.Spanned
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import com.example.myvoca.MyDBHelper
import com.example.myvoca.MyViewModel
import com.example.myvoca.R
import com.example.myvoca.Voca
import com.example.myvoca.databinding.FragmentAddBinding
import com.example.myvoca.databinding.FragmentSearchBinding
import java.util.Locale.filter
import java.util.regex.Pattern


class AddFragment : Fragment() {
    lateinit var binding: FragmentAddBinding
    lateinit var myDBHelper: MyDBHelper
    val myViewModel: MyViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentAddBinding.inflate(layoutInflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myDBHelper = MyDBHelper(requireContext())
        binding.apply {
            wordInputView.editText?.addTextChangedListener {
                val ps = Pattern.compile("^[a-zA-Z]*$");
                if (!ps.matcher(it.toString()).matches()) {
                    wordInputView.error = "영어만 입력이 가능합니다."
                }
                else{
                    wordInputView.error = null
                }
            }
            meanInputView.editText?.addTextChangedListener {
                val ps = Pattern.compile("^[ㄱ-ㅣ가-힣]*$");
                if (!ps.matcher(it.toString()).matches()) {
                    meanInputView.error = "한글만 입력이 가능합니다."
                }
                else{
                    meanInputView.error = null
                }
            }
            addBtn.setOnClickListener {
                if(wordInputView.error==null&&meanInputView.error==null&&
                        wordInputView.editText!!.text.isNotEmpty()&&meanInputView.editText!!.text.isNotEmpty()){
                    Toast.makeText(context, "단어 추가", Toast.LENGTH_LONG).show()
                    //db에 단어 추가해준다.
                    //recyclerview에 알려준다.
                    val voc = Voca(wordInputView.editText!!.text.toString(),meanInputView.editText!!.text.toString())
                    Toast.makeText(context, "단어 추가", Toast.LENGTH_LONG).show()
                    myDBHelper.insertVoca(voc)
                    myViewModel.addVoca(voc)
                    wordInputView.editText!!.text.clear()
                    meanInputView.editText!!.text.clear()
                }
                else{
                    Toast.makeText(context, "단어를 추가할 수 없습니다.", Toast.LENGTH_LONG).show()
                }
            }
        }
    }





}