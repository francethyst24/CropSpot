package com.example.cropspot.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.cropspot.R
import com.example.cropspot.presentation.ui.crop.CropScreen
import com.example.cropspot.presentation.ui.home.HomeScreen
import com.example.cropspot.presentation.ui.main.MainScreenViewModel

@Composable
fun Navigation(
    navController: NavHostController,
    model: MainScreenViewModel = hiltViewModel(),
) {
    val appName = stringResource(id = R.string.app_name)
    NavHost(
        navController = navController,
        startDestination = Destination.HOME.route
    ) {
        composable(Destination.HOME.route) {
            HomeScreen(
                onNavigate = { navController.navigate(it.route) }
            )
            model.setAppBar(appName, false)
        }
        composable(
            route = buildString {
                append(Destination.CROP.route)
                append("?cropId={cropId}")
            },
            arguments = listOf(
                navArgument(name = "cropId") {
                    type = NavType.StringType
                    nullable = false
                },
            ),
        ) {
            CropScreen(onCropNameCollect = { cropName ->
                model.setAppBar(cropName, true)
            })
        }
    }
}