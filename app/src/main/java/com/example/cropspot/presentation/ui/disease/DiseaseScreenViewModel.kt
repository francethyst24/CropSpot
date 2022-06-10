package com.example.cropspot.presentation.ui.disease

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cropspot.data.CropRepository
import com.example.cropspot.data.DiseaseRepository
import com.example.cropspot.presentation.ui.Destination
import com.example.cropspot.presentation.ui.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiseaseScreenViewModel @Inject constructor(
    private val diseaseRepository: DiseaseRepository,
    private val cropRepository: CropRepository,
    savedStateHandle: SavedStateHandle,
    private val language: Flow<String>,
) : ViewModel() {

    private val _screenState = MutableStateFlow<DiseaseScreenState>(DiseaseScreenState.LOADING)
    val screenState = _screenState.asStateFlow()

    private val _diseaseCropIds = MutableStateFlow(emptyList<String>())
    val diseaseCropIds = _diseaseCropIds.asStateFlow()

    private val _diseaseCropNames = MutableStateFlow(emptyList<String>())
    val diseaseCropNames = _diseaseCropNames.asStateFlow()

    init {
        val id = savedStateHandle.get<String>("diseaseId")!!
        viewModelScope.launch(Dispatchers.IO) {
            language.collect { language ->
                val profile = diseaseRepository.getDiseaseProfile(id, language)
                _screenState.emit(DiseaseScreenState.SUCCESS(profile))
                profile.collect { profile ->
                    val ids = profile.profile.crops.map { it.cropId }
                    _diseaseCropIds.emit(ids)
                }
            }
        }
    }


    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(specialEvent: DiseaseScreenEvent) {
        if (specialEvent is DiseaseScreenEvent.OnCropClick) {
            val route = buildString {
                append(Destination.CROP.route)
                append("?cropId=")
                append(specialEvent.crop)
            }
            sendUiEvent(UiEvent.Navigate(route))
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch { _uiEvent.send(event) }
    }

    fun loadLocalizedNames(ids: List<String>) {
        viewModelScope.launch(Dispatchers.IO) {
            language.collect { language ->
                val localizedCrops = mutableListOf<String>()
                ids.forEach { id ->
                    val name = cropRepository.getCropLocalized(id, language)
                    localizedCrops.add(name)
                    _diseaseCropNames.emit(localizedCrops)
                }
            }
        }
    }
}