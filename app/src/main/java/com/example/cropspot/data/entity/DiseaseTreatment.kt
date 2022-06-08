package com.example.cropspot.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DiseaseTreatment(
    @PrimaryKey val id: Int,
    val infoId: Int,
    val value: String,
)
