package com.example.cropspot.presentation.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cropspot.domain.dto.AppBarState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor() : ViewModel() {
    private val _appBar = MutableStateFlow(AppBarState(String(), false))
    val appBar = _appBar.asStateFlow()

    fun setAppBar(title: String, canPopBackStack: Boolean) {
        viewModelScope.launch {
            val newState = AppBarState(title, canPopBackStack)
            _appBar.emit(newState)
        }
    }
}
