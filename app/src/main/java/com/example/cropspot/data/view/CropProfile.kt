package com.example.cropspot.data.view

import androidx.room.DatabaseView

@DatabaseView("SELECT crop.id" +
        ", cropInfo.language" +
        ", cropInfo.name" +
        ", crop.isSupported" +
        ", crop.scientificName" +
        ", cropInfo.description" +
        " FROM crop INNER JOIN cropInfo ON crop.id = cropInfo.cropId")
data class CropProfile(
    val id: String,
    val language: String,
    val name: String,
    val isSupported: Boolean,
    val scientificName: String,
    val description: String,
)
