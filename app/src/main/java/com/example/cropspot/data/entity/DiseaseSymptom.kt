package com.example.cropspot.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DiseaseSymptom(
    @PrimaryKey val id: Int,
    val infoId: Int,
    val value: String,
)
