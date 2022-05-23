package com.example.dictonary.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.dictonary.R

import com.example.dictonary.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _bn: FragmentHomeBinding? = null
    private val bn get() = _bn ?: throw NullPointerException("cannot inflate")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bn = FragmentHomeBinding.inflate(inflater, container, false)

        bn.btnEngToUzb.setOnClickListener {
            findNavController().navigate(R.id.wordFragment)
        }

        return bn.root
    }
}