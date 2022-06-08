package com.example.cropspot.presentation.ui.main.persistent

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.cropspot.R

@Composable
fun MainDrawerContent(navController: NavHostController, onNavigate: () -> Unit) {
    val drawerItems = listOf(
        MainDrawerItem.Home,
        MainDrawerItem.Account,
        MainDrawerItem.History,
        MainDrawerItem.Diseases,
        MainDrawerItem.Settings,
        MainDrawerItem.Help,
    )
    Column {
        Row(
            modifier = Modifier
                .padding(start = 12.dp)
                .padding(vertical = 24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Image(
                modifier = Modifier.size(45.dp),
                imageVector = Icons.Filled.AccountCircle,
                contentDescription = "Drawer Icon",
                colorFilter = ColorFilter.tint(MaterialTheme.colors.primary),
            )
            Text(
                text = stringResource(id = R.string.ui_text_guest),
                style = MaterialTheme.typography.h6
            )
        }
        Divider(modifier = Modifier.fillMaxWidth())

        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        drawerItems.forEach { item ->
            DrawerItem(item,
                isSelected = currentRoute == item.route,
                onItemClick = {
                    navController.navigate(item.route) {
                        navController.graph.startDestinationRoute?.let {
                            popUpTo(it) { saveState = true }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                    onNavigate()
                }
            )
        }
    }
}

@Composable
fun DrawerItem(
    item: MainDrawerItem,
    isSelected: Boolean,
    onItemClick: (MainDrawerItem) -> Unit,
) {
    val bgColor = if (isSelected) {
        MaterialTheme.colors.primary
    } else Color.Transparent
    val contentColor = if (isSelected) {
        MaterialTheme.colors.onPrimary
    } else MaterialTheme.colors.onSurface
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick(item) }
            .height(60.dp)
            .background(bgColor)
            .padding(start = 24.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        when (val iconNotNull = item.iconRes ?: item.iconVec!!) {
            is Int -> Icon(
                painter = painterResource(id = iconNotNull),
                contentDescription = item.destination.route,
                tint = contentColor,
            )
            is ImageVector -> Icon(
                imageVector = iconNotNull,
                contentDescription = item.destination.route,
                tint = contentColor,
            )
        }
        Text(
            text = stringResource(item.titleRes),
            color = contentColor,
        )
    }
}