package com.example.cropspot.presentation.ui

import android.util.Log
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
import com.example.cropspot.presentation.ui.disease.DiseaseScreen
import com.example.cropspot.presentation.ui.disease_list.DiseaseListScreen
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
            model.setAppBar(appName, Destination.HOME.canPopBackStack)
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
            CropScreen(
                onNavigate = { navController.navigate(it.route) },
                onCropCollect = { model.setAppBar(it, Destination.CROP.canPopBackStack) }
            )
        }
        composable(
            route = buildString {
                append(Destination.DISEASE.route)
                append("?diseaseId={diseaseId}")
            },
            arguments = listOf(
                navArgument(name = "diseaseId") {
                    type = NavType.StringType
                    nullable = false
                },
            ),
        ) {
            DiseaseScreen(
                onDiseaseCollect = {
                    model.setAppBar(it, Destination.DISEASE.canPopBackStack)
                },
            )
        }
        composable(Destination.DISEASE_LIST.route) {
            DiseaseListScreen(onNavigate = { navController.navigate(it.route) })
            model.setAppBar(appName, Destination.DISEASE_LIST.canPopBackStack)
        }
    }
}
