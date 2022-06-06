package com.example.cropspot.presentation.ui.home

import com.example.cropspot.domain.dto.CropItem

sealed interface HomeScreenEvent

sealed class CropListEvent : HomeScreenEvent {
    data class OnCropClick(val crop: CropItem) : CropListEvent()
}

sealed class DiagnosisListEvent : HomeScreenEvent {
    data class OnDiagnosisClick(val crop: CropItem) : DiagnosisListEvent()
}