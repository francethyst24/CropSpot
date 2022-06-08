package com.example.cropspot.data

import com.example.cropspot.data.view.CropItem
import com.example.cropspot.data.view.CropProfileWithDiseases
import kotlinx.coroutines.flow.Flow

interface CropRepository {
    fun getCropItems(language: String): Flow<Map<Boolean, List<CropItem>>>
    fun getCropProfile(id: String, language: String): Flow<CropProfileWithDiseases>
}