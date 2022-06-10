package com.example.cropspot.data.dto

import kotlinx.coroutines.flow.Flow

data class DiseaseProfileWithCropsAndInfo(
    val profile: DiseaseProfileWithCrops,
    val symptoms: Flow<List<String>>,
    val treatments: Flow<List<String>>,
)
