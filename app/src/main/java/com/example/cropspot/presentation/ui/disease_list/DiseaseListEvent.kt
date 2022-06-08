package com.example.cropspot.presentation.ui.disease_list

import com.example.cropspot.data.view.DiseaseItem

sealed class DiseaseListEvent {
    data class OnDiseaseClick(val disease: DiseaseItem): DiseaseListEvent()
}
