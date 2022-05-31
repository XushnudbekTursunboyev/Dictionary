package com.example.dictonary.ui.home

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.dictonary.R
import com.example.dictonary.databinding.FragmentHomeBinding
import com.example.dictonary.model.room.entity.Word
import com.example.dictonary.model.room.helper.RoomDbHelper
import com.example.dictonary.ui.global.BaseFragment
import com.example.dictonary.utils.CONSTANTS
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken
import com.google.gson.Gson
import java.io.IOException

class HomeFragment : BaseFragment(R.layout.fragment_home) {

    private var _bn: FragmentHomeBinding? = null
    private val bn get() = _bn ?: throw NullPointerException("cannot inflate")

    private val navController: NavController by lazy(LazyThreadSafetyMode.NONE) { NavHostFragment.findNavController(this) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _bn = FragmentHomeBinding.bind(view)
        setUpUI()
    }

    override fun setUpUI() {
        bn.apply {

            saveList()

            btnEngToUzb.setOnClickListener {
                navController.navigate(R.id.wordFragment, bundleOf(CONSTANTS.TYPE_LANG to CONSTANTS.EN))
            }

            btnUzbToEng.setOnClickListener {
                navController.navigate(R.id.wordFragment, bundleOf(CONSTANTS.TYPE_LANG to CONSTANTS.UZ))
            }
        }
    }

    private fun saveList() {
        val jsonFileString = getJsonDataFromAsset(requireContext()) ?: return

        val gson = Gson()
        val listPersonType = object : TypeToken<List<Word>>() {}.type
        val words: List<Word> = gson.fromJson(jsonFileString, listPersonType)

        if (RoomDbHelper.DatabaseBuilder.getInstance(requireContext()).wordService().getUsers().isEmpty()) {
            RoomDbHelper.DatabaseBuilder.getInstance(requireContext()).wordService().addListWord(words)
        }
    }

    private fun getJsonDataFromAsset(context: Context): String? {
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