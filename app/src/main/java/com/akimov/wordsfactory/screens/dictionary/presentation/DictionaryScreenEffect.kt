package com.akimov.wordsfactory.screens.dictionary.presentation

sealed class DictionaryScreenEffect {
    data class ShowSnackbar(
        val message: String,
        val isSuccess: Boolean
    ) : DictionaryScreenEffect()

    data object HideKeyboard : DictionaryScreenEffect()
}