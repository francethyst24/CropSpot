package com.example.cropspot.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.cropspot.data.view.CropItem
import com.example.cropspot.data.view.CropProfileWithDiseases
import kotlinx.coroutines.flow.Flow

@Dao
interface CropDao {
    @Query("SELECT * FROM cropItem WHERE language = :language" +
            " ORDER BY isSupported DESC, name")
    fun getCropItems(language: String): Flow<List<CropItem>>

    @Transaction
    @Query("SELECT * FROM cropProfile WHERE id = :id AND language = :language")
    fun getCropProfile(id: String, language: String): Flow<CropProfileWithDiseases>
}