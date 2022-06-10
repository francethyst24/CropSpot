package com.example.cropspot.data

import com.example.cropspot.data.dao.DiseaseDao
import com.example.cropspot.data.dao.DiseaseInfoDao
import com.example.cropspot.data.view.DiseaseItem
import com.example.cropspot.data.view.DiseaseProfile
import com.example.cropspot.data.dto.DiseaseProfileWithCropsAndInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.transform

class DiseaseRepositoryImpl(
    private val diseaseDao: DiseaseDao,
    private val diseaseInfoDao: DiseaseInfoDao,
) : DiseaseRepository {

    override fun getDiseaseProfile(
        id: String,
        language: String,
    ): Flow<DiseaseProfileWithCropsAndInfo> {
        return diseaseDao.getDiseaseProfile(id, language).transform { withCrops ->
            diseaseInfoDao.getInfoId(withCrops.info.id, language).collect { id ->
                val emitValue = DiseaseProfileWithCropsAndInfo(
                    profile = withCrops,
                    symptoms = fetchAllSymptoms(id, withCrops.info),
                    treatments = fetchAllTreatments(id, withCrops.info),
                )
                emit(emitValue)
            }
        }
    }

    private fun fetchAllSymptoms(infoId: Int, profile: DiseaseProfile): Flow<List<String>> {
        val commonInfo = diseaseInfoDao.getCommonInfo(
            type = "symptom",
            pathogen = profile.pathogen,
            language = profile.language,
        )
        return commonInfo.transform { generalInfo ->
            diseaseInfoDao.getDiseaseSymptoms(infoId).collect { specificInfo ->
                emit(generalInfo.plus(specificInfo))
            }
        }
    }

    private fun fetchAllTreatments(infoId: Int, profile: DiseaseProfile): Flow<List<String>> {
        val commonInfo = diseaseInfoDao.getCommonInfo(
            type = "treatment",
            pathogen = profile.pathogen,
            language = profile.language,
        )
        return commonInfo.transform { generalInfo ->
            diseaseInfoDao.getDiseaseTreatments(infoId).collect { specificInfo ->
                emit(generalInfo.plus(specificInfo))
            }
        }
    }

    override fun getDiseaseItems(): Flow<Map<Boolean, List<DiseaseItem>>> {
        return diseaseDao.getDiseaseItems().transform { value ->
            val grouped = value.groupBy { it.isSupported }
            emit(grouped)
        }
    }

}