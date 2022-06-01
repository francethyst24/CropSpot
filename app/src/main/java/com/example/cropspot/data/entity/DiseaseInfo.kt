package com.example.cropspot.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DiseaseInfo(
    @PrimaryKey val id: Int,
    val cause: String,
)
