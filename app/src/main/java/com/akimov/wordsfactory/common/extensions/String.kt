package com.akimov.wordsfactory.common.extensions

fun String.replaceSlashesWithBrackets(): String {
    return this.replaceFirst("/", "[").replaceFirst("/", "]")
}