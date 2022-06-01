package com.example.cropspot.data.entity

import androidx.room.Entity

@Entity(primaryKeys = ["cropId", "language"])
data class CropInfo (
    val cropId: String,
    val language: String,
    val name: String,
    val description: String,
)