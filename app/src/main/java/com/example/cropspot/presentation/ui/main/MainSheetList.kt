package com.example.cropspot.presentation.ui.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.cropspot.R

@Composable
fun MainSheetList() {
    Column {
        MainSheetListItem(
            painter = painterResource(id = R.drawable.ic_photo_camera),
            title = "Camera",
            onClick = { /*TODO*/ },
        )
        MainSheetListItem(
            painter = painterResource(id = R.drawable.ic_photo_library),
            title = "Gallery",
            onClick = { /*TODO*/ },
        )
    }
}

@Composable
fun MainSheetListItem(
    painter: Painter,
    title: String,
    onClick: (String) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(title) }
            .height(60.dp)
            .padding(start = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Icon(painter = painter, contentDescription = title)
        Text(title)
    }
}