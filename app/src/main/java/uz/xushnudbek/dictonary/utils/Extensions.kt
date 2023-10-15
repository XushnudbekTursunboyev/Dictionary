package uz.xushnudbek.dictonary.utils

import android.text.Editable
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment

fun String.toInt(): Int {
    return this.filter { it.isDigit() }.toIntOrNull() ?: 0
}

fun View.show(bool: Boolean) {
    this.isVisible = bool
}

fun AppCompatEditText.getString(): String {
    return this.text.toString().trim()
}

fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)

fun Fragment.playStoreUrl() =
    "Hoziroq yuklab oling!: "  + "https://play.google.com/store/apps/details?id=${context?.applicationContext?.packageName}"

typealias SingleBlock<T> = (T) -> Unit
typealias MultiBlock<T, K> = (T, K) -> Unit
typealias ThreeBlock<T, K, M> = (T, K, M) -> Unit