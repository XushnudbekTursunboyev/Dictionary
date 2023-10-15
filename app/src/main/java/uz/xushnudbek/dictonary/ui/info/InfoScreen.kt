package uz.xushnudbek.dictonary.ui.info

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.dictonary.R
import com.example.dictonary.databinding.ScreenInfoBinding
import uz.xushnudbek.dictonary.ui.global.BaseFragment

class InfoScreen: BaseFragment(R.layout.screen_info) {

    private var _bn: ScreenInfoBinding? = null
    private val bn get() = _bn ?: throw NullPointerException("cannot inflate")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _bn = ScreenInfoBinding.bind(view)
        setUpUI()
    }
    override fun setUpUI() {
        bn.imgToolbarBtnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        bn.btnTelegram.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/Xushnudbek_developer"))
            intent.setPackage("org.telegram.messenger")
            startActivity(intent)
        }

        bn.btnInstagram.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/gita.uzofficial/"))
            intent.setPackage("com.instagram.android")
            startActivity(intent)
        }
    }
}