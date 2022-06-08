package com.example.cropspot.data

import com.example.cropspot.data.dao.CropDao
import com.example.cropspot.data.view.CropItem
import com.example.cropspot.data.view.CropProfileWithDiseases
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform

class CropRepositoryImpl constructor(
    private val cropDao: CropDao,
) : CropRepository {
    override fun getCropItems(language: String): Flow<Map<Boolean, List<CropItem>>> {
        return cropDao.getCropItems(language).transform { value ->
            val grouped = value.groupBy { it.isSupported }
            emit(grouped)
        }
    }

    override fun getCropProfile(id: String, language: String): Flow<CropProfileWithDiseases> {
        return cropDao.getCropProfile(id, language)
    }

}