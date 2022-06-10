package com.example.cropspot.presentation.ui.disease

sealed class DiseaseScreenEvent {
    data class OnCropClick(val crop: String) : DiseaseScreenEvent()
}
