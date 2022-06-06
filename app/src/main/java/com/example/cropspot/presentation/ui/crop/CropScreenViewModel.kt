package com.example.cropspot.presentation.ui.crop

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cropspot.data.CropRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CropScreenViewModel @Inject constructor(
    private val repository: CropRepository,
    savedStateHandle: SavedStateHandle,
    language: Flow<String>,
) : ViewModel() {

    private val _screenState = MutableStateFlow<CropScreenState>(CropScreenState.LOADING)
    val screenState: StateFlow<CropScreenState> = _screenState

    private val _cropDiseases = MutableStateFlow(emptyList<String>())
    val cropDiseases: StateFlow<List<String>> = _cropDiseases

    init {
        val id = savedStateHandle.get<String>("cropId")!!
        viewModelScope.launch {
            language.collect { language ->
                val profile = repository.getCropProfile(id, language)
                _screenState.emit(CropScreenState.SUCCESS(profile))
                profile.collect { profileWith ->
                    val names = profileWith.diseases.map { it.disease }
                    _cropDiseases.emit(names)
                }
            }
        }
    }
}