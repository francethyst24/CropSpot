package com.example.cropspot.presentation.ui.home

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cropspot.MainActivityViewModel
import com.example.cropspot.R
import com.example.cropspot.presentation.ui.UiEvent
import com.example.cropspot.presentation.ui.home.crop_list.CropList
import com.example.cropspot.presentation.ui.home.crop_list.CropListState
import com.example.cropspot.presentation.ui.home.diagnosis_list.DiagnosisList
import com.example.cropspot.presentation.ui.main.MainScreenViewModel
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
                else -> Unit
            }
        }
    }
    model.loadLocalizedCropList()
    val screenState by model.screenState.collectAsState()
    when (val state = screenState) {
        CropListState.LOADING -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) { CircularProgressIndicator() }
        }
        is CropListState.SUCCESS -> {
            val groupedListState by state.groupedList.collectAsState(
                initial = emptyMap()
            )
            if (groupedListState.isEmpty()) return
            CropList(
                groupedItems = groupedListState,
                onItemClick = { crop ->
                    model.onEvent(CropListEvent.OnCropClick(crop))
                }
            )
        }
    }
    // TODO:
    DiagnosisList()
}