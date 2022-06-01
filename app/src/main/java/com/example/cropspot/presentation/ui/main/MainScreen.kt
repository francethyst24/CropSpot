@file:OptIn(ExperimentalMaterialApi::class)

package com.example.cropspot.presentation.ui.main

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.cropspot.R
import com.example.cropspot.presentation.ui.Destination
import com.example.cropspot.presentation.ui.crop.CropScreen
import com.example.cropspot.presentation.ui.home.HomeScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    navController: NavHostController,
    coroutineScope: CoroutineScope,
) {
    val bottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden
    )
    ModalBottomSheetLayout(
        sheetState = bottomSheetState,
        sheetContent = { MainSheetList() },
    ) {
        Scaffold(
            topBar = { MainTopAppBar() },
            floatingActionButton = {
                MainFloatingActionButton {
                    coroutineScope.launch { bottomSheetState.show() }
                }
            },
        ) {
            NavHost(
                navController = navController,
                startDestination = Destination.Home.route
            ) {
                composable(Destination.Home.route) {
                    HomeScreen(onNavigate = {
                        navController.navigate(it.route)
                    })
                }
                composable(
                    route = buildString {
                        append(Destination.Crop.route)
                        append("?cropId={cropId}")
                    },
                    arguments = listOf(
                        navArgument(name = "cropId") {
                            type = NavType.StringType
                            nullable = false
                        },
                    ),
                ) { entry ->
                    CropScreen(
                        cropId = entry.arguments?.getString("cropId") ?: "",
                        onPopBackStack = { navController.popBackStack() },
                    )
                }
            }
        }
    }
}

@Composable
fun MainTopAppBar() {
    TopAppBar(
        title = {
            Text(stringResource(id = R.string.app_name))
        },
        actions = {
            IconButton(onClick = {/*TODO*/ }) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Search",
                )
            }
            IconButton(onClick = {/*TODO*/ }) {
                Icon(
                    imageVector = Icons.Filled.Settings,
                    contentDescription = "Settings",
                )
            }
        },
    )
}

@Composable
fun MainFloatingActionButton(onFabClick: () -> Unit) {
    FloatingActionButton(onClick = { onFabClick() }) {
        Icon(
            painter = painterResource(id = R.drawable.ic_image_search),
            contentDescription = "Diagnose"
        )
    }
}