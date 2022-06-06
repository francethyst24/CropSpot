package com.example.cropspot.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.cropspot.data.view.CropItem
import com.example.cropspot.domain.dto.CropProfileWithDiseases
import kotlinx.coroutines.flow.Flow

@Dao
interface CropDao {
    @Query("SELECT * FROM cropItem WHERE language = :language" +
            " ORDER BY isSupported DESC, name")
    fun getCropItems(language: String): Flow<List<CropItem>>

    /*@Query("SELECT * FROM cropProfile WHERE id = :id AND language = :language")
    fun getCropProfileById(id: String, language: String): Flow<CropProfile>*/

    @Query("SELECT * FROM cropProfile WHERE id = :id AND language = :language")
    fun getCropProfileById(id: String, language: String): Flow<CropProfileWithDiseases>
}