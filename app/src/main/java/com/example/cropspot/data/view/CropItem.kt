package com.example.cropspot.data.view

import androidx.room.DatabaseView

@DatabaseView("SELECT crop.id" +
        ", cropInfo.name" +
        ", crop.isSupported" +
        ", cropInfo.language" +
        " FROM crop INNER JOIN cropInfo ON crop.id = cropInfo.cropId")
data class CropItem(
    val id: String,
    val name: String,
    val isSupported: Boolean,
    val language: String,
)
