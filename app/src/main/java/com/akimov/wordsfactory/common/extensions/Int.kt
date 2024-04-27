package com.akimov.wordsfactory.common.extensions

fun Int.indexToLetter(): String {
    return when (this) {
        0 -> "A."
        1 -> "B."
        2 -> "C."
        else -> throw IllegalArgumentException("Index $this is not supported")
    }
}