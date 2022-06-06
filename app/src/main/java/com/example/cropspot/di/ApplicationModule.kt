package com.example.cropspot.di

import android.content.Context
import androidx.room.Room
import com.example.cropspot.common.utils.LocaleUtils
import com.example.cropspot.data.AppDatabase
import com.example.cropspot.data.CropRepository
import com.example.cropspot.data.CropRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {
    private const val DB_NAME = "cropspot"

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room
            .databaseBuilder(context, AppDatabase::class.java, DB_NAME)
            .createFromAsset("$DB_NAME.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideCropRepository(db: AppDatabase): CropRepository {
        return CropRepositoryImpl(cropDao = db.cropDao())
    }

    @Provides
    @Singleton
    fun provideLanguageFlow(@ApplicationContext context: Context): Flow<String> {
        return LocaleUtils.getLanguageFlow(context)
    }

}