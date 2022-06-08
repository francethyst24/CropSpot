package com.example.cropspot.presentation.ui.disease_list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cropspot.common.utils.LoadingBox
import com.example.cropspot.data.view.DiseaseItem
import com.example.cropspot.presentation.ui.UiEvent
import com.example.cropspot.presentation.ui.home.crop_list.ListItem
import com.example.cropspot.presentation.ui.home.crop_list.ListStickyHeader
import kotlinx.coroutines.flow.collect

@Suppress("UnnecessaryVariable")
@Composable
fun DiseaseListScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    model: DiseaseListViewModel = hiltViewModel(),
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
    val diseaseListState by model.screenState.collectAsState()
    when (val listState = diseaseListState) {
        DiseaseListState.LOADING -> LoadingBox()
        is DiseaseListState.SUCCESS -> {
            val items by listState.groupedList.collectAsState(emptyMap())
            DiseaseList(
                groupedItems = items,
                onItemClick = {
                    model.onEvent(DiseaseListEvent.OnDiseaseClick(it))
                }
            )
        }
    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DiseaseList(
    groupedItems: Map<Boolean, List<DiseaseItem>>,
    onItemClick: (DiseaseItem) -> Unit,
) {
    val lazyListState = rememberLazyListState()
    LazyColumn(
        modifier = Modifier.padding(bottom = 12.dp),
        state = lazyListState,
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        groupedItems.forEach { (isSupported, items) ->
            stickyHeader {
                ListStickyHeader(
                    label = if (isSupported) "Supported" else "Others"
                )
            }
            items(items = items) {
                ListItem(
                    text = it.name,
                    isSupported = it.isSupported,
                    onCardClick = { onItemClick(it) }
                )
            }
        }
    }
}