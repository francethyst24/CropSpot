package com.example.cropspot.presentation.ui.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cropspot.presentation.ui.UiEvent
import com.example.cropspot.presentation.ui.home.crop_list.CropList
import com.example.cropspot.presentation.ui.home.crop_list.CropListEvent
import com.example.cropspot.presentation.ui.home.crop_list.CropListViewModel
import com.example.cropspot.presentation.ui.home.diagnosis_list.DiagnosisList
import kotlinx.coroutines.flow.collect

@Composable
fun HomeScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    model: CropListViewModel = hiltViewModel(),
) {
    LaunchedEffect(key1 = true) {
        model.uiEvent.collect { uiEvent ->
            when (uiEvent) {
                is UiEvent.Navigate -> onNavigate(uiEvent)
                else -> Unit
            }
        }
    }
    CropList(
        lazyListFlow = model.cropItems,
        onItemClick = {
            model.onEvent(CropListEvent.OnCropClick(it))
        }
    )
    DiagnosisList()
}