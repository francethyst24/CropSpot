package com.example.cropspot.presentation.ui.home.crop_list

import com.example.cropspot.domain.dto.CropItem
import kotlinx.coroutines.flow.Flow

sealed class CropListState {
    object LOADING: CropListState()
    data class SUCCESS(
        val groupedList: Flow<Map<Boolean, List<CropItem>>>
    ): CropListState()
}