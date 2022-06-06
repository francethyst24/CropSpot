package com.example.cropspot.data

import com.example.cropspot.data.dao.CropDao
import com.example.cropspot.data.view.CropItem
import com.example.cropspot.data.view.CropProfile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform
import javax.inject.Inject

class CropRepositoryImpl constructor(
    private val cropDao: CropDao,
): CropRepository {
    override fun getCropItems(language: String): Flow<Map<Boolean, List<CropItem>>> {
        return cropDao.getCropItems(language).transform { value ->
            val grouped = value.groupBy { it.isSupported }
            emit(grouped)
        }
    }

    override fun getCropProfile(id: String, language: String): Flow<CropProfile> {
        return cropDao.getCropProfileById(id, language)
    }

}