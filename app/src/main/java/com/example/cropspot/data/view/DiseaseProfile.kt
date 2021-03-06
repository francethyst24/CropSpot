package com.example.cropspot.data.view

import androidx.room.DatabaseView

@DatabaseView("SELECT disease.id" +
        ", disease.pathogen" +
        ", disease.isSupported" +
        ", diseaseInfo.language" +
        ", diseaseInfo.cause" +
        " FROM disease INNER JOIN diseaseInfo ON disease.id = diseaseInfo.diseaseId")
data class DiseaseProfile(
    val id: String,
    val pathogen: String,
    val isSupported: Boolean,
    val language: String,
    val cause: String,
)
