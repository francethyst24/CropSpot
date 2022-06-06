package com.example.cropspot.presentation.ui

sealed class UiEvent {
    object PopBackStack : UiEvent()
    data class Navigate(val route: String) : UiEvent()
    data class ShowSnackbar(
        val message: String,
        val action: String? = null,
    ) : UiEvent()

    data class UpdateLocale(
        val language: String,
    ) : UiEvent()
}