package com.example.cropspot.presentation.ui.disease

import com.example.cropspot.data.view.DiseaseProfile
import com.example.cropspot.data.view.DiseaseProfileWithInfoAndCrops
import kotlinx.coroutines.flow.Flow

sealed class DiseaseScreenState {
    object LOADING: DiseaseScreenState()
    data class SUCCESS(
        val profile: Flow<DiseaseProfileWithInfoAndCrops>,
    ): DiseaseScreenState()
}
