package com.example.cropspot.data

import com.example.cropspot.data.dao.DiseaseDao
import com.example.cropspot.data.view.DiseaseItem
import com.example.cropspot.data.view.DiseaseProfile
import com.example.cropspot.data.view.DiseaseProfileWithInfoAndCrops
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform

class DiseaseRepositoryImpl constructor(
    private val diseaseDao: DiseaseDao
): DiseaseRepository {
    override fun getDiseaseProfile(id: String, language: String): Flow<DiseaseProfileWithInfoAndCrops> {
        return diseaseDao.getDiseaseProfile(id, language)
    }

    override fun getDiseaseItems(): Flow<Map<Boolean, List<DiseaseItem>>> {
        return diseaseDao.getDiseaseItems().transform { value ->
            val grouped = value.groupBy { it.isSupported }
            emit(grouped)
        }
    }
}