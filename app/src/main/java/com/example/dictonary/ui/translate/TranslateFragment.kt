package com.example.dictonary.ui.translate

import android.os.Bundle
import android.view.View
import com.example.dictonary.R
import com.example.dictonary.databinding.FragmentTranslateBinding
import com.example.dictonary.model.room.entity.Word
import com.example.dictonary.model.room.helper.RoomDbHelper
import com.example.dictonary.ui.global.BaseFragment
import com.example.dictonary.utils.CONSTANTS
import com.google.gson.Gson

class TranslateFragment : BaseFragment(R.layout.fragment_translate) {

    private var _bn: FragmentTranslateBinding? = null
    private val bn get() = _bn ?: throw NullPointerException("cannot inflate")
    private lateinit var word: Word
    private var checkFavorites = false
    private var langType = CONSTANTS.UZ

    override fun onCreate(savedInstanceState: Bundle?) {
        arguments?.apply {
            langType = getString(CONSTANTS.TYPE_LANG) ?: CONSTANTS.UZ
            word = Gson().fromJson(getString(CONSTANTS.WORD_MODEL), Word::class.java)
            checkFavorites = word.favorite
        }
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _bn = FragmentTranslateBinding.bind(view)
        setUpUI()
    }


    override fun setUpUI() {
        bn.apply {
            setData()

            checkFavorite()

            saveFavorite()
        }
    }

    private fun saveFavorite() {
        bn.apply {
            cardviewlike.setOnClickListener {
                if (checkFavorites) {
                    word.favorite = false
                    imageLike.setImageResource(R.drawable.ic_unselect_favorite)
                } else {
                    word.favorite = true
                    imageLike.setImageResource(R.drawable.ic_select_favorite)
                }
                checkFavorites = word.favorite
                RoomDbHelper.DatabaseBuilder.getInstance(requireContext())
                    .wordService().update(word)
            }
        }
    }

    private fun checkFavorite() {
        bn.apply {
            imageLike.setImageResource(if (checkFavorites) R.drawable.ic_select_favorite else R.drawable.ic_unselect_favorite)
        }
    }

    private fun setData() {
        bn.apply {
            tvWord.text = if (langType == CONSTANTS.UZ) word.en else word.uz
        }
    }

}