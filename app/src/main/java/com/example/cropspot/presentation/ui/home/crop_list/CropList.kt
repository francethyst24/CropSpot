package com.example.cropspot.presentation.ui.home.crop_list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.cropspot.R
import com.example.cropspot.data.view.CropItem
import com.example.cropspot.presentation.theme.Lime200
import com.example.cropspot.presentation.theme.Ochre100

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CropList(
    groupedItems: Map<Boolean, List<CropItem>>,
    onItemClick: (CropItem) -> Unit,
) {
    val lazyListState = rememberLazyListState()
    LazyColumn(
        modifier = Modifier.padding(bottom = 12.dp),
        state = lazyListState,
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        groupedItems.forEach { (isSupported, crops) ->
            stickyHeader {
                CropListHeader(
                    label = if (isSupported) "Supported" else "Others"
                )
            }
            items(items = crops) {
                CropListItem(crop = it, onCardClick = { onItemClick(it) })
            }
        }
    }
}

@Composable
fun CropListHeader(label: String) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(elevation = 1.dp)
            .background(MaterialTheme.colors.secondary)
            .padding(start = 24.dp)
            .padding(vertical = 6.dp),
        text = label,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colors.onSecondary,
        style = MaterialTheme.typography.subtitle2,
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CropListItem(
    crop: CropItem,
    onCardClick: () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
            .height(60.dp),
        onClick = { onCardClick() }
    ) {
        Row(
            modifier = Modifier.padding(start = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            Text(crop.name)
            if (!crop.isSupported) return@Row
            Icon(
                painter = painterResource(id = R.drawable.ic_verified),
                contentDescription = "Supported",
                tint = MaterialTheme.colors.secondary
            )
        }
    }
}