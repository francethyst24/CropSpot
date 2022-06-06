package com.example.cropspot.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.cropspot.data.dao.CropDao
import com.example.cropspot.data.entity.Crop
import com.example.cropspot.data.entity.CropInfo
import com.example.cropspot.data.view.CropItem
import com.example.cropspot.data.view.CropProfile

@Database(
    version = 1,
    entities = [Crop::class, CropInfo::class],
    views = [CropItem::class, CropProfile::class],
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cropDao(): CropDao
}