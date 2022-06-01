package com.example.cropspot.presentation.ui.main

sealed class MainScreenEvent {
    object OnSearchItemClick: MainScreenEvent()
    object OnSettingsItemClick: MainScreenEvent()
    object OnCameraOptionClick: MainScreenEvent()
    object OnGalleryOptionClick: MainScreenEvent()
}
