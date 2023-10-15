package uz.xushnudbek.dictonary.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.dictonary.R
import com.example.dictonary.databinding.FragmentHomeBinding
import uz.xushnudbek.dictonary.model.room.entity.WordModel
import uz.xushnudbek.dictonary.model.room.helper.RoomDbHelper
import uz.xushnudbek.dictonary.ui.global.BaseFragment
import uz.xushnudbek.dictonary.utils.CONSTANTS
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

           // saveList()

            btnEngToUzb.setOnClickListener {
                navController.navigate(R.id.wordFragment, bundleOf(CONSTANTS.TYPE_LANG to CONSTANTS.EN))
            }

            btnUzbToEng.setOnClickListener {
                navController.navigate(R.id.wordFragment, bundleOf(CONSTANTS.TYPE_LANG to CONSTANTS.UZ))
            }

            btnInfo.setOnClickListener {
                navController.navigate(R.id.infoScreen)
            }

            btnShare.setOnClickListener {
                shareProject(requireContext())
            }

        }
    }

    private fun shareProject(context: Context) {
        val sendIntent = Intent()
        sendIntent.action = Intent.ACTION_SEND
        sendIntent.putExtra(
            Intent.EXTRA_TEXT,
            "Dowloand now!: " + "https://play.google.com/store/apps/details?id=${context.applicationContext?.packageName}"
        )
        sendIntent.type = "text/plain"
        context.startActivity(sendIntent)
    }

    private fun saveList() {
        val jsonFileString = getJsonDataFromAsset(requireContext()) ?: return

        val gson = Gson()
        val listPersonType = object : TypeToken<List<WordModel>>() {}.type
        val words: List<WordModel> = gson.fromJson(jsonFileString, listPersonType)

        if (RoomDbHelper.DatabaseBuilder.getInstance(requireContext()).wordService().getWords().isEmpty()) {
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