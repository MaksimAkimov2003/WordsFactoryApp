package com.akimov.wordsfactory.screens.dictionary.viewModel

sealed class DictionaryScreenEffect {
    data class ShowSnackbar(
        val message: String,
        val isSuccess: Boolean
    ) : DictionaryScreenEffect()

    data object HideKeyboard : DictionaryScreenEffect()
}