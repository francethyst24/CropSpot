package com.example.cropspot.di

import android.content.Context
import androidx.room.Room
import com.example.cropspot.data.AppDatabase
import com.example.cropspot.data.dao.CropDao
import com.example.cropspot.common.utils.LocaleUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {
    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "cropspot")
            .createFromAsset("cropspot.db")
            .build()
    }

    @Singleton
    @Provides
    fun provideCropDao(db: AppDatabase): CropDao = db.cropDao()

    @Singleton
    @Provides
    fun provideLanguage(@ApplicationContext context: Context): String {
        return LocaleUtils(context).getLanguage()
    }
}