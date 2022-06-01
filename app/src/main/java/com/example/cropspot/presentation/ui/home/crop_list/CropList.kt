package com.example.cropspot.presentation.ui.home.crop_list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.cropspot.R
import com.example.cropspot.domain.dto.CropItem
import kotlinx.coroutines.flow.Flow

@Composable
fun CropList(
    lazyListFlow: Flow<List<CropItem>>,
    onItemClick: (CropItem) -> Unit,
) {
    val lazyList = lazyListFlow.collectAsState(initial = emptyList())
    val lazyListState = rememberLazyListState()
    LazyColumn(
        modifier = Modifier.padding(12.dp),
        state = lazyListState,
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        items(items = lazyList.value) {
            CropListItem(crop = it, onCardClick = { onItemClick(it) })
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CropListItem(crop: CropItem, onCardClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        onClick = { onCardClick() }
    ) {
        Row(
            modifier = Modifier.padding(start = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Text(crop.name)
            if (!crop.isSupported) return@Row
            Icon(
                painter = painterResource(id = R.drawable.ic_verified),
                contentDescription = "Supported",
            )
        }
    }
}