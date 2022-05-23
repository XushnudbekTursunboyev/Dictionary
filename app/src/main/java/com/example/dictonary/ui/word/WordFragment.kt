package com.example.dictonary.ui.word

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.dictonary.R
import com.example.dictonary.databinding.FragmentWordBinding
import com.example.dictonary.model.room.helper.RoomDbHelper
import com.example.dictonary.model.room.entity.Word
import com.example.dictonary.ui.global.BaseFragment


class WordFragment : BaseFragment(R.layout.fragment_word) {
    private lateinit var adaptereng: WordEngAdapter
    private lateinit var adapteruz: WordUzAdapter
    private var _bn: FragmentWordBinding? = null
    private val bn get() = _bn ?: throw NullPointerException("cannot inflate")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bn = FragmentWordBinding.inflate(inflater, container, false)

        setAdapter(RoomDbHelper.DatabaseBuilder.getInstance(requireContext()).wordService().getUsers())
        return bn.root
    }

    //salom

    private fun setAdapter(words: List<Word>) {
        val change = arguments?.getString("change")
        if (change.equals("0")){
        adaptereng = WordEngAdapter(words){
                val bundle = Bundle()
                bundle.putString("trans", "0")
                bundle.putSerializable("translate", it)
                findNavController().navigate(R.id.translateFragment, bundle)
            }
            bn.recyclerView.adapter = adaptereng

        }else if (change.equals("1")){
            adapteruz = WordUzAdapter(words){
                val bundle = Bundle()
                bundle.putString("trans", "1")
                bundle.putSerializable("translate", it)
                findNavController().navigate(R.id.translateFragment, bundle)            }
            bn.recyclerView.adapter = adapteruz
        }
    }


    override fun setUpUI() {
        bn.apply {
        }
    }

    override fun onDestroyView() {
        _bn = null
        super.onDestroyView()
    }

}