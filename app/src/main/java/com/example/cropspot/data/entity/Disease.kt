package com.example.cropspot.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Disease(
    @PrimaryKey val id: String,
    val pathogenId: String,
    val isSupported: Boolean,
)