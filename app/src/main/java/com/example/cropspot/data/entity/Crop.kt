package com.example.cropspot.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Crop(
    @PrimaryKey val id: String,
    val scientificName: String,
    val isSupported: Boolean,
)
