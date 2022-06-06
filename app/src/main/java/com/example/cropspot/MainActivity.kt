package com.example.cropspot

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.cropspot.common.utils.LocaleUtils
import com.example.cropspot.presentation.theme.CropSpotTheme
import com.example.cropspot.presentation.ui.main.MainNavDrawerItem
import com.example.cropspot.presentation.ui.main.MainScreen
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

@Composable
fun MainDrawerContent(navController: NavHostController, onNavigate: () -> Unit) {
    val drawerItems = listOf(
        MainNavDrawerItem.Home,
        MainNavDrawerItem.Account,
        MainNavDrawerItem.History,
        MainNavDrawerItem.Diseases,
        MainNavDrawerItem.Settings,
        MainNavDrawerItem.Help,
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
                contentDescription = "Drawer Icon"
            )
            Text(
                text = stringResource(id = R.string.ui_text_guest),
                style = MaterialTheme.typography.h6
            )
        }
        Divider(modifier = Modifier
            .fillMaxWidth()
        )
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        drawerItems.forEach { item ->
            DrawerItem(item,
                isSelected = currentRoute == item.destination.route,
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
    item: MainNavDrawerItem,
    isSelected: Boolean,
    onItemClick: (MainNavDrawerItem) -> Unit,
) {
    val bgColor = if (isSelected) {
        MaterialTheme.colors.primary
    } else Color.Transparent
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
                contentDescription = item.destination.route
            )
            is ImageVector -> Icon(
                imageVector = iconNotNull,
                contentDescription = item.destination.route
            )
        }
        Text(text = stringResource(item.titleRes))
    }
}