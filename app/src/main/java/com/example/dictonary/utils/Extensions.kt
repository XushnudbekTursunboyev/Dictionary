package com.example.dictonary.utils

import android.view.View
import androidx.core.view.isVisible

fun String.toInt(): Int {
    return this.filter { it.isDigit() }.toIntOrNull() ?: 0
}

fun View.show(bool: Boolean) {
    this.isVisible = bool
}

typealias SingleBlock<T> = (T) -> Unit
typealias MultiBlock<T, K> = (T, K) -> Unit
typealias ThreeBlock<T, K, M> = (T, K, M) -> Unit