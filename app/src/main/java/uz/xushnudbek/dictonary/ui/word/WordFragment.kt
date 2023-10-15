package uz.xushnudbek.dictonary.ui.word

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.dictonary.R
import com.example.dictonary.databinding.FragmentWordBinding
import uz.xushnudbek.dictonary.model.room.entity.WordModel
import uz.xushnudbek.dictonary.model.room.helper.RoomDbHelper
import uz.xushnudbek.dictonary.ui.global.BaseFragment
import uz.xushnudbek.dictonary.utils.CONSTANTS
import uz.xushnudbek.dictonary.utils.toEditable
import com.google.gson.Gson
import java.util.Locale


class WordFragment : BaseFragment(R.layout.fragment_word) , TextToSpeech.OnInitListener{

    private var _bn: FragmentWordBinding? = null
    private val bn get() = _bn ?: throw NullPointerException("cannot inflate")
    private var tts: TextToSpeech? = null

    private var isClicked = false

    private var list = ArrayList<WordModel>()

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

            tts = TextToSpeech(requireContext(), this@WordFragment)

            setSearchText()

            setAdapter()

            loadData()

            addTextWatchers()

            ivFavorite.setOnClickListener {
                navController.navigate(R.id.favoriteScreen, bundleOf(CONSTANTS.TYPE_LANG to langType))
            }

            ivChange.setOnClickListener {
                if (langType == CONSTANTS.UZ){
                    langType = CONSTANTS.EN
                }else{
                    langType = CONSTANTS.UZ
                }
                setUpUI()
            }
        }
    }


    private fun setSearchText() {
        bn.apply {
            etSearch.hint = if (langType == CONSTANTS.UZ) CONSTANTS.UZ_SEARCH.toEditable() else CONSTANTS.EN_SEARCH.toEditable()
        }
    }

    private fun setAdapter() {
        bn.apply {
            adapter = WordAdapter(langType)

            recyclerView.adapter = this@WordFragment.adapter
            adapter.setOnCLickListener {
                navController.navigate(R.id.translateFragment, bundleOf(CONSTANTS.TYPE_LANG to langType, CONSTANTS.WORD_MODEL to Gson().toJson(it)))
            }

            adapter.clickSound {
                speakOut(it.english!!)
            }
        }
    }

    private fun loadData() {
        list.clear()
        val roomList = RoomDbHelper.DatabaseBuilder.getInstance(requireContext()).wordService().getWords()
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

    private fun speakOut(text: String) {
        tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts!!.setLanguage(Locale.US)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "The Language not supported!")
            }
        }
    }


    override fun onDestroyView() {
        _bn = null
        super.onDestroyView()
    }
}