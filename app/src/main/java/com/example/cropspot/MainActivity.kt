package com.example.cropspot

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.compose.rememberNavController
import com.example.cropspot.common.utils.LocaleUtils
import com.example.cropspot.presentation.ui.main.MainScreen
import com.example.cropspot.presentation.theme.CropSpotTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun attachBaseContext(newBase: Context?) {
        if (newBase != null) super.attachBaseContext(LocaleUtils(newBase))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CropSpotTheme {
                val navController = rememberNavController()
                val coroutineScope = rememberCoroutineScope()
                MainScreen(navController, coroutineScope)
            }
        }
    }
}