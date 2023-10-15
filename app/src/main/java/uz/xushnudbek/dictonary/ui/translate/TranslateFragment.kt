package uz.xushnudbek.dictonary.ui.translate

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.dictonary.R
import com.example.dictonary.databinding.FragmentTranslateBinding
import uz.xushnudbek.dictonary.model.room.entity.WordModel
import uz.xushnudbek.dictonary.model.room.helper.RoomDbHelper
import uz.xushnudbek.dictonary.ui.global.BaseFragment
import uz.xushnudbek.dictonary.ui.word.WordAdapter
import uz.xushnudbek.dictonary.utils.CONSTANTS
import com.google.gson.Gson
import java.util.Locale

class TranslateFragment : BaseFragment(R.layout.fragment_translate), TextToSpeech.OnInitListener {

    private var _bn: FragmentTranslateBinding? = null
    private var tts: TextToSpeech? = null

    private val bn get() = _bn ?: throw NullPointerException("cannot inflate")
    private lateinit var word: WordModel
    private lateinit var nearestWords: ArrayList<WordModel>
    private var checkFavorites = 0
    private var langType = CONSTANTS.UZ

    private lateinit var adapter: WordAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        arguments?.apply {
            langType = getString(CONSTANTS.TYPE_LANG) ?: CONSTANTS.UZ
            word = Gson().fromJson(getString(CONSTANTS.WORD_MODEL), WordModel::class.java)
            checkFavorites = word.isFavourite!!
        }
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _bn = FragmentTranslateBinding.bind(view)
        setUpUI()
    }

    override fun setUpUI() {
        tts = TextToSpeech(requireContext(), this)
        nearestWords = arrayListOf()
        nearestWords.clear()
        adapter = WordAdapter(langType)
        bn.apply {
            setData()
            checkFavorite()
            saveFavorite()
            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    private fun saveFavorite() {
        bn.apply {
            btnFavourite.setOnClickListener {
                if (checkFavorites == 1) {
                    word.isFavourite = 0
                    btnFavourite.setImageResource(R.drawable.ic_unselect_favorite)
                } else {
                    word.isFavourite = 1
                    btnFavourite.setImageResource(R.drawable.ic_select_favorite)
                }
                checkFavorites = word.isFavourite!!
                RoomDbHelper.DatabaseBuilder.getInstance(requireContext())
                    .wordService().update(word)
            }
        }
    }

    private fun checkFavorite() {
        bn.apply {
            btnFavourite.setImageResource(if (checkFavorites == 1) R.drawable.ic_select_favorite else R.drawable.ic_unselect_favorite)
        }
    }

    private fun setData() {
        bn.apply {
            if (langType == CONSTANTS.UZ){
                txtWord.text = word.uzbek
                txtTrans.text = word.english
                tvNearestWords.text = "O`xshash so`zlar"
                tvActionbarWord.text = word.uzbek
            } else{
                tvActionbarWord.text = word.english
                txtWord.text = word.english
                txtTrans.text = word.uzbek
                tvNearestWords.text = "Nearest words"
            }
            txtTranscript.text = word.transcript
            txtType.text = word.type
            btnVolume.setOnClickListener {
                speakOut(word.english!!)
            }
        }

        val wordList = RoomDbHelper.DatabaseBuilder.getInstance(requireContext()).wordService().getWords()
        wordList.forEach {
            if (langType == CONSTANTS.EN) {
                if (word.english!!.take(3) == it.english!!.take(3) && word.id != it.id) {
                    nearestWords.add(it)
                }
            } else {
                if (word.uzbek!!.take(3) == it.uzbek!!.take(3) && word.id != it.id) {
                    nearestWords.add(it)
                }
            }
        }

        Log.d("TAG", "setData: $nearestWords")

        if (nearestWords.isNotEmpty()) {
            adapter.submitList(nearestWords)
            bn.rvNearest.adapter = adapter
        }

        adapter.setOnCLickListener {
            word = it
            checkFavorites = word.isFavourite!!
            setUpUI()
        }

        adapter.clickSound {
            speakOut(it.english!!)
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

}