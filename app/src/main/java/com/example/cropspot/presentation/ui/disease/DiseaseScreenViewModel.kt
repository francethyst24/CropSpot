package com.example.cropspot.presentation.ui.disease

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cropspot.data.DiseaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiseaseScreenViewModel @Inject constructor(
    private val repository: DiseaseRepository,
    savedStateHandle: SavedStateHandle,
    language: Flow<String>,
) : ViewModel() {

    private val _screenState = MutableStateFlow<DiseaseScreenState>(DiseaseScreenState.LOADING)
    val screenState = _screenState.asStateFlow()

    private val _diseaseCrops = MutableStateFlow(emptyList<String>())
    val diseaseCrops = _diseaseCrops.asStateFlow()

    init {
        val id = savedStateHandle.get<String>("diseaseId")!!
        viewModelScope.launch {
            language.collect { language ->
                val profile = repository.getDiseaseProfile(id, language)
                _screenState.emit(DiseaseScreenState.SUCCESS(profile))
                profile.collect {}
            }
        }
    }
}