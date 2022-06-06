package com.example.cropspot.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.cropspot.data.entity.Crop
import com.example.cropspot.data.view.CropItem
import com.example.cropspot.data.view.CropProfile
import kotlinx.coroutines.flow.Flow

@Dao
interface CropDao {
    @Query("SELECT crop.id, cropInfo.name, crop.isSupported FROM crop" +
            " INNER JOIN cropInfo ON cropInfo.cropId = crop.id" +
            " WHERE cropInfo.language = :language" +
            " ORDER BY crop.isSupported DESC, cropInfo.name")
    fun getCropItems(language: String): Flow<List<CropItem>>

    @Query("SELECT cropInfo.name, crop.scientificName, crop.isSupported, cropInfo.description" +
            " FROM crop INNER JOIN cropInfo ON cropInfo.cropId = crop.id" +
            " WHERE crop.id = :id AND cropInfo.language = :language")
    fun getCropProfileById(id: String, language: String): Flow<CropProfile>

}