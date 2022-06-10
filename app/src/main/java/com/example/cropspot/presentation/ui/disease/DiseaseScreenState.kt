package com.example.cropspot.presentation.ui.disease

import com.example.cropspot.data.dto.DiseaseProfileWithCropsAndInfo
import kotlinx.coroutines.flow.Flow

sealed class DiseaseScreenState {
    object LOADING : DiseaseScreenState()
    data class SUCCESS(
        val profile: Flow<DiseaseProfileWithCropsAndInfo>,
    ) : DiseaseScreenState()
}
