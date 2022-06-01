package com.example.cropspot.presentation.ui.home.crop_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cropspot.data.CropRepository
import com.example.cropspot.presentation.ui.Destination
import com.example.cropspot.presentation.ui.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CropListViewModel @Inject constructor(
    cropRepository: CropRepository,
) : ViewModel() {
    val cropItems = cropRepository.loadAllCropItems

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(specialEvent: CropListEvent) {
        when (specialEvent) {
            is CropListEvent.OnCropClick -> {
                val generalEvent = UiEvent.Navigate(
                    buildString {
                        append(Destination.Crop.route)
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