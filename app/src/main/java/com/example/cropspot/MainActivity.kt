package com.example.cropspot

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.DrawerValue
import androidx.compose.material.ModalDrawer
import androidx.compose.material.rememberDrawerState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.example.cropspot.common.utils.LocaleUtils
import com.example.cropspot.presentation.theme.CropSpotTheme
import com.example.cropspot.presentation.ui.main.MainScreen
import com.example.cropspot.presentation.ui.main.persistent.MainDrawerContent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val model: MainActivityViewModel by viewModels()

    private var currentLanguage: String
        get() = LocaleUtils.getLanguage(applicationContext)
        set(value) {
            LocaleUtils.setLocale(applicationContext, value)
            recreate()
        }

    override fun attachBaseContext(newBase: Context) {
        LocaleUtils.setLocale(newBase, LocaleUtils.getLanguage(newBase))
        super.attachBaseContext(LocaleUtils.onAttach(newBase))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContent {
            CropSpotTheme {
                model.setLanguage(this, currentLanguage)
                val navController = rememberNavController()
                val coroutineScope = rememberCoroutineScope()
                val drawerState = rememberDrawerState(
                    initialValue = DrawerValue.Closed
                )
                ModalDrawer(
                    drawerState = drawerState,
                    drawerContent = {
                        MainDrawerContent(navController, onNavigate = {
                            coroutineScope.launch { drawerState.close() }
                        })
                    }
                ) {
                    MainScreen(
                        navController, coroutineScope,
                        showNavDrawer = {
                            coroutineScope.launch { drawerState.open() }
                        },
                        updateLocale = {
                            // persist to ViewModel
                            model.setLanguage(this, it.language)
                            // persist to SharedPreferences
                            currentLanguage = it.language
                        },
                    )
                }
            }
        }
    }

}

