package com.example.cropspot.data

import com.example.cropspot.data.dao.CropDao
import javax.inject.Inject

class CropRepository @Inject constructor(
    cropDao: CropDao,
    language: String,
) {
    val loadAllCropItems = cropDao.loadAllCropItemViews(language)
}