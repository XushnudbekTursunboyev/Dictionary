package com.example.dictonary.utils

import android.view.View
import androidx.core.view.isVisible

fun String.toInt(): Int {
    return this.filter { it.isDigit() }.toIntOrNull() ?: 0
}

fun View.show(bool: Boolean) {
    this.isVisible = bool
}