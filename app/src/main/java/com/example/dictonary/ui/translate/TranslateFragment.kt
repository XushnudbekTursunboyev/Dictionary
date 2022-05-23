package com.example.dictonary.ui.translate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.dictonary.R
import com.example.dictonary.databinding.FragmentTranslateBinding
import kotlinx.coroutines.flow.combine

class TranslateFragment : Fragment() {

    private var _bn: FragmentTranslateBinding? = null
    private val bn get() = _bn ?: throw NullPointerException("cannot inflate")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _bn = FragmentTranslateBinding.inflate(inflater, container, false)



        return bn.root
    }

}