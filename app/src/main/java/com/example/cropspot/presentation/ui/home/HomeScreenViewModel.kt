package com.example.cropspot.presentation.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cropspot.data.CropRepository
import com.example.cropspot.presentation.ui.Destination
import com.example.cropspot.presentation.ui.UiEvent
import com.example.cropspot.presentation.ui.home.crop_list.CropListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val repository: CropRepository,
    val language: Flow<String>,
) : ViewModel() {

    private val _screenState = MutableStateFlow<CropListState>(CropListState.LOADING)
    val screenState = _screenState.asStateFlow()

    fun loadLocalizedList() {
        viewModelScope.launch(Dispatchers.IO) {
            language.collect {
                val groupedList = repository.getCropItems(it)
                _screenState.emit(CropListState.SUCCESS(groupedList))
            }
        }
    }

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(specialEvent: CropListEvent) {
        when (specialEvent) {
            is CropListEvent.OnCropClick -> {
                val generalEvent = UiEvent.Navigate(
                    buildString {
                        append(Destination.CROP.route)
                        append("?cropId=")
                        append(specialEvent.crop.id)
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