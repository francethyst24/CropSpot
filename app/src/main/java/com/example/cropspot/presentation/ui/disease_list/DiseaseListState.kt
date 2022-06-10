package com.example.cropspot.presentation.ui.disease_list

import com.example.cropspot.data.view.DiseaseItem
import kotlinx.coroutines.flow.Flow

sealed class DiseaseListState {
    object LOADING : DiseaseListState()
    data class SUCCESS(
        val groupedList: Flow<Map<Boolean, List<DiseaseItem>>>,
    ) : DiseaseListState()
}
