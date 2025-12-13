package com.gg.aireader.ui.screens.navigation

import PdfViewerScreen
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.gg.aireader.ui.screens.home.HomeScreen
import com.gg.aireader.ui.screens.model.Routes
import com.gg.aireader.ui.screens.setting.SettingsScreen

@Composable
fun AppNavGraph(innerPadding: PaddingValues, navController: NavHostController){
    NavHost(
        navController = navController,
        startDestination = Routes.HOME,
        modifier = Modifier.padding(innerPadding)
    ) {

        composable(Routes.HOME) {
            HomeScreen(navController)
        }

        composable(Routes.SETTINGS) {
            SettingsScreen()
        }

        composable("${Routes.READER}?uri={uri}",
            arguments = listOf(navArgument(name = "uri"){
                type = NavType.StringType
                nullable = true
            })) {
PdfViewerScreen(pdfUri = it.arguments?.getString("uri"))
        }

    }
}