package com.example.dictonary.ui.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.dictonary.R

import com.example.dictonary.databinding.FragmentHomeBinding
import com.example.dictonary.model.room.helper.RoomDbHelper
import com.example.dictonary.model.room.entity.Word
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken
import com.google.gson.Gson
import java.io.IOException

class HomeFragment : Fragment() {

    private var _bn: FragmentHomeBinding? = null
    private val bn get() = _bn ?: throw NullPointerException("cannot inflate")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bn = FragmentHomeBinding.inflate(inflater, container, false)
        saveList()
        bn.btnEngToUzb.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("change", "0")
            findNavController().navigate(R.id.wordFragment, bundle)
        }
        bn.btnUzbToEng.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("change", "1")
            findNavController().navigate(R.id.wordFragment, bundle)
        }

        return bn.root
    }

    fun saveList() {
        val jsonFileString = getJsonDataFromAsset(requireContext(), "word.json")
        if (jsonFileString != null) {
            Log.d("data", jsonFileString)
        }
        val gson = Gson()
        val listPersonType = object : TypeToken<List<Word>>() {}.type
        val words: List<Word> = gson.fromJson(jsonFileString, listPersonType)
        words.forEachIndexed { idx, word -> Log.i("data", "> Item $idx:\n$word") }
        if (RoomDbHelper.DatabaseBuilder.getInstance(requireContext()).wordService().getUsers().isEmpty()){
            RoomDbHelper.DatabaseBuilder.getInstance(requireContext()).wordService().addListWord(words)
        }
    }


    fun getJsonDataFromAsset(context: Context, fileName: String): String? {
        val jsonString: String
        try {
            jsonString = context.assets.open("word.json").bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }

}