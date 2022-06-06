package com.example.cropspot.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(primaryKeys = ["cropId", "diseaseId"])
data class CropDisease(
    val cropId: String,
    @ColumnInfo(name = "diseaseId") val disease: String,
)
