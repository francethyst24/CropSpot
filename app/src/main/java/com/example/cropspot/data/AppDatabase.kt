package com.example.cropspot.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.cropspot.data.dao.CropDao
import com.example.cropspot.data.dao.DiseaseDao
import com.example.cropspot.data.entity.Crop
import com.example.cropspot.data.entity.CropInfo
import com.example.cropspot.data.entity.Disease
import com.example.cropspot.data.entity.DiseaseInfo

@Database(
    version = 1,
    entities = [
        Crop::class,
        Disease::class,
        CropInfo::class,
        DiseaseInfo::class,
    ],
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cropDao(): CropDao
    abstract fun diseaseDao(): DiseaseDao
}