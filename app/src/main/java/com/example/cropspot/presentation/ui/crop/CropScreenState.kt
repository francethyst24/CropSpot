package com.example.cropspot.presentation.ui.crop

import com.example.cropspot.data.view.CropProfileWithDiseases
import kotlinx.coroutines.flow.Flow

sealed class CropScreenState {
    object LOADING : CropScreenState()
    data class SUCCESS(
        val profile: Flow<CropProfileWithDiseases>,
    ) : CropScreenState()
}
