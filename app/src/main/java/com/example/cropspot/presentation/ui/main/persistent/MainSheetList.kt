package com.example.cropspot.presentation.ui.main

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.cropspot.R

@Composable
fun MainSheetList() {
    Column {
        MainSheetListItem(
            drawableRes = R.drawable.ic_photo_camera,
            contentDescription = "Camera",
            onClick = { /*TODO*/ },
        )
        MainSheetListItem(
            drawableRes = R.drawable.ic_photo_library,
            contentDescription = "Gallery",
            onClick = { /*TODO*/ },
        )
    }
}

@Composable
fun MainSheetListItem(
    contentDescription: String,
    onClick: (String) -> Unit,
    imageVector: ImageVector? = null,
    @DrawableRes drawableRes: Int? = null,
    @StringRes stringRes: Int? = null,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(contentDescription) }
            .height(60.dp)
            .padding(start = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        if (drawableRes != null) {
            Icon(
                painter = painterResource(id = drawableRes),
                contentDescription = contentDescription
            )
        } else if (imageVector != null) {
            Icon(
                imageVector = imageVector,
                contentDescription = contentDescription
            )
        }
        Text(
            text = stringRes?.let { stringResource(it) }
                ?: contentDescription
        )
    }
}