package com.example.cropspot.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CommonInfo(
    @PrimaryKey val id: Int,
    val type: String,
    val language: String,
    val pathogen: String,
    val value: String,
)
