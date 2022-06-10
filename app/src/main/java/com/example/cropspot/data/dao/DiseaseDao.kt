package com.example.cropspot.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.cropspot.data.dto.DiseaseProfileWithCrops
import com.example.cropspot.data.view.DiseaseItem
import kotlinx.coroutines.flow.Flow

@Dao
interface DiseaseDao {
    @Transaction
    @Query("SELECT * FROM diseaseProfile WHERE id = :id AND language = :language")
    fun getDiseaseProfile(id: String, language: String): Flow<DiseaseProfileWithCrops>

    @Query("SELECT * FROM diseaseItem ORDER BY isSupported DESC, name")
    fun getDiseaseItems(): Flow<List<DiseaseItem>>

    @Query("SELECT cropId FROM cropDisease WHERE diseaseId = :id")
    fun getDiseaseCrops(id: String): Flow<List<String>>
}