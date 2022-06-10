package com.example.cropspot.presentation.ui.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cropspot.common.utils.LoadingBox
import com.example.cropspot.presentation.ui.UiEvent
import com.example.cropspot.presentation.ui.home.crop_list.CropList
import com.example.cropspot.presentation.ui.home.crop_list.CropListState
import com.example.cropspot.presentation.ui.home.diagnosis_list.DiagnosisList
import kotlinx.coroutines.flow.collect

@Suppress("UnnecessaryVariable")
@Composable
fun HomeScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    model: HomeScreenViewModel = hiltViewModel(),
) {
    LaunchedEffect(key1 = true) {
        model.uiEvent.collect { uiEvent ->
            when (uiEvent) {
                is UiEvent.Navigate -> onNavigate(uiEvent)
                else -> {}
            }
        }
    }
    model.loadLocalizedList()
    val screenState by model.screenState.collectAsState()
    when (val state = screenState) {
        CropListState.LOADING -> LoadingBox()
        is CropListState.SUCCESS -> {
            val groupedListState by state.groupedList.collectAsState(
                initial = emptyMap()
            )
            if (groupedListState.isEmpty()) return
            CropList(
                groupedItems = groupedListState,
                onItemClick = { id ->
                    model.onEvent(CropListEvent.OnCropClick(id))
                }
            )
        }
    }
    // TODO:
    DiagnosisList()
}