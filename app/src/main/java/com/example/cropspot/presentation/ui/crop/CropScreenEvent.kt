package com.example.cropspot.presentation.ui.crop

sealed class CropScreenEvent {
    data class OnDiseaseClick(val disease: String) : CropScreenEvent()
}
