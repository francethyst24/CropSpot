package com.example.cropspot.data.entity

import androidx.room.Entity

@Entity(primaryKeys = ["cropId", "diseaseId"])
data class CropDisease(
    val cropId: String,
    val diseaseId: String,
)
