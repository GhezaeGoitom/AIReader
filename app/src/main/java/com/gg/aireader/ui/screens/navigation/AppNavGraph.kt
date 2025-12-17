package com.gg.aireader.ui.screens.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.gg.aireader.ui.screens.about.About
import com.gg.aireader.ui.screens.home.HomeScreen
import com.gg.aireader.ui.screens.model.Routes
import com.gg.aireader.ui.screens.setting.SettingsScreen

@Composable
fun AppNavGraph(innerPadding: PaddingValues,
                drawerNavController: NavHostController,
                parentNavController: NavHostController){
    NavHost(
        navController = drawerNavController,
        startDestination = Routes.HOME,
        modifier = Modifier.padding(innerPadding)
    ) {

        composable(Routes.HOME) {
            HomeScreen(drawerNavController = drawerNavController,
                parentNavController = parentNavController)
        }

        composable(Routes.SETTINGS) {
            SettingsScreen()
        }

        composable(Routes.ABOUT) {
            About()
        }

    }
}