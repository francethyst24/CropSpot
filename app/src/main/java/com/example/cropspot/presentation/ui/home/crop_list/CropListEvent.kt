package com.example.cropspot.presentation.ui.home.crop_list

import com.example.cropspot.domain.dto.CropItem

sealed class CropListEvent {
    data class OnCropClick(val crop: CropItem): CropListEvent()
}
