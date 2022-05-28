package com.example.dictonary.ui.translate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.dictonary.R
import com.example.dictonary.databinding.FragmentTranslateBinding
import com.example.dictonary.model.room.entity.Word
import com.example.dictonary.model.room.helper.RoomDbHelper

class TranslateFragment : Fragment() {

    private var _bn: FragmentTranslateBinding? = null
    private val bn get() = _bn ?: throw NullPointerException("cannot inflate")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bn = FragmentTranslateBinding.inflate(inflater, container, false)
        val trans = arguments?.getString("trans")
        val word = arguments?.getSerializable("translate") as Word
        var checkFavorites = word.favorite

        bn.apply {

            if (checkFavorites) {
                imageLike.setImageResource(R.drawable.ic_baseline_star_24)
            } else {
                imageLike.setImageResource(R.drawable.ic_baseline_star_border_24)
            }

            if (trans.equals("0")) {
                tvWord.text = word.uz
            } else {
                tvWord.text = word.en
            }

            cardviewlike.setOnClickListener {
                if (checkFavorites) {
                    word.favorite = false
                    imageLike.setImageResource(R.drawable.ic_baseline_star_border_24)
                } else {
                    word.favorite = true
                    imageLike . setImageResource (R.drawable.ic_baseline_star_24)
                }
                checkFavorites = word.favorite
                RoomDbHelper.DatabaseBuilder.getInstance(requireContext())
                    .wordService().update(word)

            }
        }

        return bn.root
    }

}