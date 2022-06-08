package com.example.cropspot.data

import com.example.cropspot.data.view.DiseaseItem
import com.example.cropspot.data.view.DiseaseProfile
import com.example.cropspot.data.view.DiseaseProfileWithInfoAndCrops
import kotlinx.coroutines.flow.Flow

interface DiseaseRepository {
    fun getDiseaseProfile(id: String, language: String) : Flow<DiseaseProfileWithInfoAndCrops>
    fun getDiseaseItems() : Flow<Map<Boolean, List<DiseaseItem>>>
}