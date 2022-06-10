package com.example.cropspot.data.dao

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface DiseaseInfoDao {
    @Query("SELECT id FROM diseaseInfo WHERE diseaseId = :id" +
            " AND language = :language")
    fun getInfoId(id: String, language: String): Flow<Int>

    @Query("SELECT value FROM diseaseSymptom WHERE infoId = :id")
    fun getDiseaseSymptoms(id: Int): Flow<List<String>>

    @Query("SELECT value FROM commonInfo WHERE type = :type" +
            " AND pathogen = :pathogen AND language = :language")
    fun getCommonInfo(type: String, pathogen: String, language: String): Flow<List<String>>

    @Query("SELECT value FROM diseaseTreatment WHERE infoId = :id")
    fun getDiseaseTreatments(id: Int): Flow<List<String>>

}