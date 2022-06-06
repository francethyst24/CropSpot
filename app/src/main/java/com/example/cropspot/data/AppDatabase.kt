package com.example.cropspot.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.cropspot.data.dao.CropDao
import com.example.cropspot.data.dao.CropInfoDao
import com.example.cropspot.data.entity.Crop
import com.example.cropspot.data.entity.CropInfo

@Database(
    version = 1,
    entities = [Crop::class, CropInfo::class],
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cropDao(): CropDao
    abstract fun cropInfoDao(): CropInfoDao
}