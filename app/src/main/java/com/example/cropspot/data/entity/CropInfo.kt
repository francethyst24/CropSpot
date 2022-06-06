package com.example.cropspot.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CropInfo(
    @PrimaryKey val id: Int,
    val cropId: String,
    val name: String,
    val language: String,
    val description: String,
)
