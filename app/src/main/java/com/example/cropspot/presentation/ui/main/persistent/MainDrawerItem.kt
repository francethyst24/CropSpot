package com.example.cropspot.presentation.ui.main.persistent

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.cropspot.R
import com.example.cropspot.presentation.ui.Destination

sealed class MainDrawerItem(
    val destination: Destination,
    @DrawableRes val iconRes: Int? = null,
    val iconVec: ImageVector? = null,
    @StringRes val titleRes: Int,
) {
    val route get() = destination.route
    object Home: MainDrawerItem(
        destination = Destination.HOME,
        iconVec = Icons.Filled.Home,
        titleRes = R.string.ui_text_home,
    )
    object Account: MainDrawerItem(
        destination = Destination.ACCOUNT,
        iconVec = Icons.Filled.AccountCircle,
        titleRes = R.string.ui_text_account,
    )
    object History: MainDrawerItem(
        destination = Destination.HISTORY,
        iconRes = R.drawable.ic_history,
        titleRes = R.string.ui_text_history,
    )
    object Diseases: MainDrawerItem(
        destination = Destination.DISEASE_LIST,
        iconRes = R.drawable.ic_leaf,
        titleRes = R.string.ui_text_diseases,
    )
    object Settings: MainDrawerItem(
        destination = Destination.SETTINGS,
        iconVec = Icons.Filled.Settings,
        titleRes = R.string.ui_text_settings,
    )
    object Help: MainDrawerItem(
        destination = Destination.HELP,
        iconRes = R.drawable.ic_help,
        titleRes = R.string.ui_text_help,
    )
}
