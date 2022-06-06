package com.example.cropspot.domain.dto

data class CropProfile(
    val name: String,
    val scientificName: String,
    val isSupported: Boolean,
    val description: String,
)
