package com.example.cropspot.data

import com.example.cropspot.data.view.DiseaseItem
import com.example.cropspot.data.dto.DiseaseProfileWithCropsAndInfo
import kotlinx.coroutines.flow.Flow

interface DiseaseRepository {
    fun getDiseaseProfile(id: String, language: String): Flow<DiseaseProfileWithCropsAndInfo>
    fun getDiseaseItems(): Flow<Map<Boolean, List<DiseaseItem>>>
}