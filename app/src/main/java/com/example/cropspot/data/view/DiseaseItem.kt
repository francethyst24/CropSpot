package com.example.cropspot.data.view

import androidx.room.DatabaseView

@DatabaseView("SELECT disease.id AS name, disease.isSupported FROM disease")
data class DiseaseItem(
    val name: String,
    val isSupported: Boolean,
) {
    val id get() = name
}
