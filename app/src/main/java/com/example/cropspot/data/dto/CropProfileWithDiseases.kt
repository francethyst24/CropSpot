package com.example.cropspot.data.dto

import androidx.room.Embedded
import androidx.room.Relation
import com.example.cropspot.data.entity.CropDisease
import com.example.cropspot.data.view.CropProfile

data class CropProfileWithDiseases(
    @Embedded val profile: CropProfile,
    @Relation(parentColumn = "id", entityColumn = "cropId")
    val diseases: List<CropDisease>,
)
