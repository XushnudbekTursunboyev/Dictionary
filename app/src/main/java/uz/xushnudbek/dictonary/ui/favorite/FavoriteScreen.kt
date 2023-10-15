package uz.xushnudbek.dictonary.ui.favorite

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.dictonary.R
import com.example.dictonary.databinding.ScreenFavoriteBinding
import uz.xushnudbek.dictonary.model.room.entity.WordModel
import uz.xushnudbek.dictonary.model.room.helper.RoomDbHelper
import uz.xushnudbek.dictonary.ui.global.BaseFragment
import uz.xushnudbek.dictonary.ui.word.WordAdapter
import uz.xushnudbek.dictonary.utils.CONSTANTS
import uz.xushnudbek.dictonary.utils.toEditable
import com.google.gson.Gson


class FavoriteScreen : BaseFragment(R.layout.screen_favorite) {

    private var _bn: ScreenFavoriteBinding? = null
    private val bn get() = _bn ?: throw NullPointerException("cannot inflate")
    private var list = ArrayList<WordModel>()

    private var langType = CONSTANTS.UZ

    private val navController: NavController by lazy(LazyThreadSafetyMode.NONE) { NavHostFragment.findNavController(this) }

    private lateinit var adapter: WordAdapter
    override fun setUpUI() {
        bn.apply {
            setSearchText()
            setAdapter()
            loadData()

            addTextWatchers()

            btnBack.setOnClickListener {
                navController.popBackStack()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        arguments?.apply {
            langType = getString(CONSTANTS.TYPE_LANG) ?: CONSTANTS.UZ
        }

        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _bn = ScreenFavoriteBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)
        setUpUI()
    }


    private fun setSearchText() {
        bn.apply {
            etSearch.hint = if (langType == CONSTANTS.UZ) CONSTANTS.UZ_SEARCH.toEditable() else CONSTANTS.EN_SEARCH.toEditable()
        }
    }

    private fun setAdapter() {
        bn.apply {
            adapter = WordAdapter(langType)

            recyclerView.adapter = this@FavoriteScreen.adapter
            adapter.setOnCLickListener {
                navController.navigate(R.id.translateFragment, bundleOf(CONSTANTS.TYPE_LANG to langType, CONSTANTS.WORD_MODEL to Gson().toJson(it)))
            }
        }
    }

    private fun loadData() {
        list.clear()
        val roomList = RoomDbHelper.DatabaseBuilder.getInstance(requireContext()).wordService().getFavorite()
        list.addAll(if (langType == CONSTANTS.UZ) roomList.sortedBy { it.uzbek!!.lowercase() } else roomList.sortedBy { it.english!!.lowercase() })
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
            val searchedList = list.filter { if (langType == CONSTANTS.UZ) it.uzbek!!.contains(search) else it.english!!.contains(search) }
            adapter.submitList(searchedList)
        }
    }

    override fun onDestroyView() {
        _bn = null
        super.onDestroyView()
    }
}