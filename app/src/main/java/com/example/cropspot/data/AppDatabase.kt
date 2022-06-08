package com.example.cropspot.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.cropspot.data.dao.CropDao
import com.example.cropspot.data.dao.DiseaseDao
import com.example.cropspot.data.entity.*
import com.example.cropspot.data.view.CropItem
import com.example.cropspot.data.view.CropProfile
import com.example.cropspot.data.view.DiseaseItem
import com.example.cropspot.data.view.DiseaseProfile

@Database(
    version = 1,
    entities = [
        Crop::class,
        CropInfo::class,
        CropDisease::class,
        Disease::class,
        DiseaseInfo::class,
        DiseaseSymptom::class,
        DiseaseTreatment::class,
        CommonInfo::class,
    ],
    views = [
        CropItem::class,
        CropProfile::class,
        DiseaseItem::class,
        DiseaseProfile::class,
    ],
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cropDao(): CropDao
    abstract fun diseaseDao(): DiseaseDao
}