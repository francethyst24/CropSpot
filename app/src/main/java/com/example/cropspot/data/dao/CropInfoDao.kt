package com.example.cropspot.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.cropspot.data.entity.CropInfo

@Dao
interface CropInfoDao {
    @Query("SELECT * FROM cropInfo" +
            " WHERE cropId = :id" +
            " AND language = :language")
    fun getLocalizedCropInfoById(id: String, language: String): CropInfo
}