package com.example.cropspot.data.dto

import androidx.room.Embedded
import androidx.room.Relation
import com.example.cropspot.data.entity.CropDisease
import com.example.cropspot.data.view.DiseaseProfile

data class DiseaseProfileWithCrops(
    @Embedded val info: DiseaseProfile,
    @Relation(
        parentColumn = "id",
        entityColumn = "diseaseId")
    val crops: List<CropDisease>,
)
