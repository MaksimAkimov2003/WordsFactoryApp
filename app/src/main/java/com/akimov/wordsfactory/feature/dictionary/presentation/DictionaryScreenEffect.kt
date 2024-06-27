package com.akimov.wordsfactory.feature.dictionary.presentation

sealed class DictionaryScreenEffect {
    data class ShowSnackbar(
        val message: String,
        val isSuccess: Boolean
    ) : DictionaryScreenEffect()

    data object HideKeyboard : DictionaryScreenEffect()
}