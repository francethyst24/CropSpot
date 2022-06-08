package com.example.cropspot.presentation.ui.disease_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cropspot.data.DiseaseRepository
import com.example.cropspot.presentation.ui.Destination
import com.example.cropspot.presentation.ui.UiEvent
import com.example.cropspot.presentation.ui.home.CropListEvent
import com.example.cropspot.presentation.ui.home.crop_list.CropListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiseaseListViewModel @Inject constructor(
    private val repository: DiseaseRepository,
) : ViewModel() {

    private val _screenState = MutableStateFlow<DiseaseListState>(DiseaseListState.LOADING)
    val screenState = _screenState.asStateFlow()

    fun loadLocalizedList() {
        viewModelScope.launch {
            val groupedList = repository.getDiseaseItems()
            _screenState.emit(DiseaseListState.SUCCESS(groupedList))
        }
    }

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(specialEvent: DiseaseListEvent) {
        when (specialEvent) {
            is DiseaseListEvent.OnDiseaseClick -> {
                val generalEvent = UiEvent.Navigate(
                    buildString {
                        append(Destination.DISEASE.route)
                        append("?diseaseId=")
                        append(specialEvent.disease.id)
                    }
                )
                sendUiEvent(generalEvent)
            }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch { _uiEvent.send(event) }
    }

}