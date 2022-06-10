package com.example.cropspot.presentation.ui.crop

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cropspot.data.CropRepository
import com.example.cropspot.presentation.ui.Destination
import com.example.cropspot.presentation.ui.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CropScreenViewModel @Inject constructor(
    private val repository: CropRepository,
    savedStateHandle: SavedStateHandle,
    language: Flow<String>,
) : ViewModel() {

    private val _screenState = MutableStateFlow<CropScreenState>(CropScreenState.LOADING)
    val screenState = _screenState.asStateFlow()

    private val _cropDiseases = MutableStateFlow(emptyList<String>())
    val cropDiseases = _cropDiseases.asStateFlow()

    init {
        val id = savedStateHandle.get<String>("cropId")!!
        viewModelScope.launch(Dispatchers.IO) {
            language.collect { language ->
                val profile = repository.getCropProfile(id, language)
                _screenState.emit(CropScreenState.SUCCESS(profile))
                profile.collect { profileWith ->
                    val names = profileWith.diseases.map { it.diseaseId }
                    _cropDiseases.emit(names)
                }
            }
        }
    }

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(specialEvent: CropScreenEvent) {
        if (specialEvent is CropScreenEvent.OnDiseaseClick) {
            val route = buildString {
                append(Destination.DISEASE.route)
                append("?diseaseId=")
                append(specialEvent.disease)
            }
            sendUiEvent(UiEvent.Navigate(route))
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch { _uiEvent.send(event) }
    }
}