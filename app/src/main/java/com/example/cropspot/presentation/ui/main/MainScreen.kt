@file:OptIn(ExperimentalMaterialApi::class)

package com.example.cropspot.presentation.ui.main

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.cropspot.R
import com.example.cropspot.domain.dto.AppBarState
import com.example.cropspot.presentation.theme.Ochre100
import com.example.cropspot.presentation.ui.Navigation
import com.example.cropspot.presentation.ui.UiEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    navController: NavHostController,
    coroutineScope: CoroutineScope,
    showNavDrawer: () -> Unit,
    updateLocale: (UiEvent.UpdateLocale) -> Unit,
    mainModel: MainScreenViewModel = hiltViewModel(),
) { // Persist Sheet -------------------------------------------------------------------------------
    val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    // Persist AppBar Title ------------------------------------------------------------------------
    val appBarState by mainModel.appBar.collectAsState()
    // Sheet ---------------------------------------------------------------------------------------
    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = { MainSheetList() },
    ) { // Persisting Screen -----------------------------------------------------------------------
        val scaffoldState = rememberScaffoldState()
        Scaffold(
            scaffoldState = scaffoldState,
            topBar = {
                MainAppBar(
                    appBarState = appBarState,
                    onNavIconClick = { canPopBackStack ->
                        if (canPopBackStack) navController.navigateUp()
                        else showNavDrawer()
                    },
                    updateLocale = { updateLocale(UiEvent.UpdateLocale(it)) }
                )
            },
            floatingActionButton = {
                MainFAB(onClick = { coroutineScope.launch { sheetState.show() } })
            },
        ) { // Content Screen ----------------------------------------------------------------------
            Navigation(navController = navController)
            // -------------------------------------------------------------------------------------
        }
    }
}

@Composable
fun MainAppBar(
    appBarState: AppBarState,
    onNavIconClick: ((canPopBackStack: Boolean) -> Unit),
    updateLocale: (String) -> Unit,
) {
    TopAppBar(
        title = { Text(appBarState.title) },
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
fun MainFAB(onClick: () -> Unit) {
    FloatingActionButton(
        backgroundColor = MaterialTheme.colors.secondary,
        contentColor = MaterialTheme.colors.onSecondary,
        onClick = { onClick() }
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            val text = stringResource(id = R.string.ui_text_diagnose)
            Icon(
                painter = painterResource(id = R.drawable.ic_image_search),
                contentDescription = text
            )
            Text(text = text, fontWeight = FontWeight.Bold)
        }
    }
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