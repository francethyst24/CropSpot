package com.example.cropspot.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.cropspot.domain.dto.CropItem
import kotlinx.coroutines.flow.Flow

@Dao
interface CropDao {
    @Query("SELECT cropInfo.name, crop.isSupported FROM crop" +
            " INNER JOIN cropInfo ON cropInfo.cropId = crop.id" +
            " WHERE cropInfo.language = :language")
    fun loadAllCropItemViews(language: String): Flow<List<CropItem>>

}