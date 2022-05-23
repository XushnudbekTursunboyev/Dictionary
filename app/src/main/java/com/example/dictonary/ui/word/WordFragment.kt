package com.example.dictonary.ui.word

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.dictonary.R
import com.example.dictonary.databinding.FragmentWordBinding
import com.example.dictonary.model.room.RoomDbHelper
import com.example.dictonary.model.room.entity.Word
import com.example.dictonary.ui.global.BaseFragment
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken
import com.google.gson.Gson
import java.io.IOException


class WordFragment : BaseFragment(R.layout.fragment_word) {
    private lateinit var list: List<Word>
    private lateinit var adapter: WordAdapter
    private var _bn: FragmentWordBinding? = null
    private val bn get() = _bn ?: throw NullPointerException("cannot inflate")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bn = FragmentWordBinding.inflate(inflater, container, false)
        saveList()
        bn.btnCheck.setOnClickListener {
            Log.d("data", "salom")

        }

        return bn.root
    }

    //salom
    fun saveList() {
        val jsonFileString = getJsonDataFromAsset(requireContext(), "word.json")
        if (jsonFileString != null) {
            Log.d("data", jsonFileString)
        }
        val gson = Gson()
        val listPersonType = object : TypeToken<List<Word>>() {}.type
        var words: List<Word> = gson.fromJson(jsonFileString, listPersonType)
        words.forEachIndexed { idx, word -> Log.i("data", "> Item $idx:\n$word") }
        if (RoomDbHelper.DatabaseBuilder.getInstance(requireContext()).wordService().getUsers().isEmpty()){
        RoomDbHelper.DatabaseBuilder.getInstance(requireContext()).wordService().addListWord(words)
        }
        setAdapter(RoomDbHelper.DatabaseBuilder.getInstance(requireContext()).wordService().getUsers())
    }

    fun setAdapter(words: List<Word>) {
        adapter = WordAdapter(words){
            Toast.makeText(requireContext(), it.uz, Toast.LENGTH_SHORT).show()
        }
        bn.recyclerView.adapter = adapter
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

    override fun setUpUI() {
        bn.apply {
        }
    }

    override fun onDestroyView() {
        _bn = null
        super.onDestroyView()
    }

}