package com.example.cropspot.presentation.ui.main.persistent

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import com.example.cropspot.domain.dto.AppBarState

@Composable
fun MainAppBar(
    appBarState: AppBarState,
    onNavIconClick: ((canPopBackStack: Boolean) -> Unit),
    updateLocale: (String) -> Unit,
) {
    TopAppBar(
        title = {
            Text(
                text = appBarState.title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        },
        navigationIcon = {
            if (appBarState.canPopBackStack) MainIconButton(
                onClick = { onNavIconClick(true) },
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "Back"
            ) else MainIconButton(
                onClick = { onNavIconClick(false) },
                imageVector = Icons.Filled.Menu,
                contentDescription = "Show Drawer"
            )
        },
        actions = {
            MainIconButton(
                onClick = { updateLocale("en") },
                imageVector = Icons.Filled.Search,
                contentDescription = "Search"
            )
            MainIconButton(
                onClick = { updateLocale("tl") },
                imageVector = Icons.Filled.Settings,
                contentDescription = "Settings"
            )
        },
    )
}

@Composable
fun MainIconButton(
    imageVector: ImageVector,
    contentDescription: String,
    onClick: () -> Unit,
) {
    IconButton(onClick = { onClick() }) {
        Icon(imageVector, contentDescription)
    }
}