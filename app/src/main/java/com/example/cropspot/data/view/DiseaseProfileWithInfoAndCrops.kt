package com.example.cropspot.data.view

import androidx.room.Embedded
import androidx.room.Relation
import com.example.cropspot.data.entity.CropDisease
import com.example.cropspot.data.entity.DiseaseSymptom
import com.example.cropspot.data.entity.DiseaseTreatment

data class DiseaseProfileWithInfoAndCrops(
    @Embedded val profile: DiseaseProfile,
    @Relation(parentColumn = "id", entityColumn = "diseaseId")
    val crops: List<CropDisease>,
    @Relation(parentColumn = "infoId", entityColumn = "infoId")
    val symptoms: List<DiseaseSymptom>,
    @Relation(parentColumn = "infoId", entityColumn = "infoId")
    val treatments: List<DiseaseTreatment>,
)
