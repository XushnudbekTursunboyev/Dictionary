package com.example.dictonary.ui.word

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.dictonary.R
import com.example.dictonary.databinding.FragmentWordBinding
import com.example.dictonary.model.room.entity.Word
import com.example.dictonary.model.room.helper.RoomDbHelper
import com.example.dictonary.ui.global.BaseFragment
import com.example.dictonary.utils.CONSTANTS
import com.google.gson.Gson


class WordFragment : BaseFragment(R.layout.fragment_word) {

    private var _bn: FragmentWordBinding? = null
    private val bn get() = _bn ?: throw NullPointerException("cannot inflate")

    private var isClicked = false

    private var list = ArrayList<Word>()

    private var langType = CONSTANTS.UZ

    private val navController: NavController by lazy(LazyThreadSafetyMode.NONE) { NavHostFragment.findNavController(this) }

    private lateinit var adapter: WordAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        arguments?.apply {
            langType = getString(CONSTANTS.TYPE_LANG) ?: CONSTANTS.UZ
        }

        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _bn = FragmentWordBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)
        setUpUI()
    }

    override fun setUpUI() {
        bn.apply {
            setAdapter()

            loadData()

            addTextWatchers()

            ivFavorite.setOnClickListener {
                isClicked = true
                adapter.submitList(list)
            }

            backPressedDispatcher()
        }
    }

    private fun setAdapter() {
        bn.apply {
            adapter = WordAdapter(langType)

            recyclerView.adapter = this@WordFragment.adapter
            adapter.setOnCLickListener {
                navController.navigate(R.id.translateFragment, bundleOf("trans" to "0", "translate" to Gson().toJson(it)))
            }
        }
    }

    private fun loadData() {
        list.clear()
        val roomList = RoomDbHelper.DatabaseBuilder.getInstance(requireContext()).wordService().getUsers()
        list.addAll(if (langType == CONSTANTS.UZ) roomList.sortedBy { it.uz.lowercase() } else roomList.sortedBy { it.en.lowercase() })
        adapter.submitList(list)
    }

    private fun addTextWatchers() {
        bn.apply {
            etSearch.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

                override fun afterTextChanged(s: Editable?) {
                    if (!s.isNullOrEmpty())
                        searchWord(s.toString())
                    else adapter.submitList(list)
                }
            })
        }
    }

    private fun searchWord(search: String) {
        bn.apply {
            val searchedList = list.filter { if (langType == CONSTANTS.UZ) it.uz.contains(search) else it.en.contains(search) }
            adapter.submitList(searchedList)
        }
    }

    private fun backPressedDispatcher() {
        activity?.onBackPressedDispatcher?.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (isClicked) {
                    isClicked = false
                    adapter.submitList(list)
                } else navController.popBackStack()
            }
        })
    }

    override fun onDestroyView() {
        _bn = null
        super.onDestroyView()
    }
}