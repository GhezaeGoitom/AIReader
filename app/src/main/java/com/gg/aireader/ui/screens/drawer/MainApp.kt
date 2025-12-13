package com.gg.aireader.ui.screens.drawer

import PdfViewerScreen
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.gg.aireader.ui.screens.model.Routes


@Composable
fun MainApp(){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.SHELL){

        composable(Routes.SHELL) {
            DrawerShell(parentNavController = navController)
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