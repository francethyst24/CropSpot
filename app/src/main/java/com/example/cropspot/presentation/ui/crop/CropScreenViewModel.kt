package com.example.cropspot.presentation.ui.crop

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cropspot.data.CropRepository
import dagger.hilt.android.lifecycle.HiltViewModel
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
    val screenState : StateFlow<CropScreenState> = _screenState

    init {
        val id = savedStateHandle.get<String>("cropId")!!
        viewModelScope.launch {
            language.collect {
                val profile = repository.getCropProfile(id, it)
                _screenState.emit(CropScreenState.SUCCESS(profile))
            }
        }
    }

}