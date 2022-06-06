package com.example.cropspot.presentation.ui.main

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.cropspot.R
import com.example.cropspot.presentation.ui.Destination

sealed class MainNavDrawerItem(
    val destination: Destination,
    @DrawableRes val iconRes: Int? = null,
    val iconVec: ImageVector? = null,
    @StringRes val titleRes: Int,
) {
    val route get() = destination.route
    object Home: MainNavDrawerItem(
        destination = Destination.HOME,
        iconVec = Icons.Filled.Home,
        titleRes = R.string.ui_text_home,
    )
    object Account: MainNavDrawerItem(
        destination = Destination.ACCOUNT,
        iconVec = Icons.Filled.AccountCircle,
        titleRes = R.string.ui_text_account,
    )
    object History: MainNavDrawerItem(
        destination = Destination.HISTORY,
        iconRes = R.drawable.ic_history,
        titleRes = R.string.ui_text_history,
    )
    object Diseases: MainNavDrawerItem(
        destination = Destination.DISEASES,
        iconRes = R.drawable.ic_leaf,
        titleRes = R.string.ui_text_diseases,
    )
    object Settings: MainNavDrawerItem(
        destination = Destination.SETTINGS,
        iconVec = Icons.Filled.Settings,
        titleRes = R.string.ui_text_settings,
    )
    object Help: MainNavDrawerItem(
        destination = Destination.HELP,
        iconRes = R.drawable.ic_help,
        titleRes = R.string.ui_text_help,
    )
}
