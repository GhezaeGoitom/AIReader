package com.gg.aireader.ui.screens.drawer

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.gg.aireader.R
import com.gg.aireader.ui.screens.model.Routes
import com.gg.aireader.ui.screens.navigation.AppNavGraph
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawerShell(parentNavController: NavHostController) {

    val drawerNavController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier =
                    Modifier.width(LocalConfiguration.current.screenWidthDp.dp * 0.50f)
            ) {

                Spacer(Modifier.height(5.dp))

                Image(
                    painter = painterResource(R.drawable.ic_launcher_round),
                    contentDescription = null,
                    modifier = Modifier
                        .align(alignment = Alignment.CenterHorizontally)
//                        .size(64.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .padding(8.dp)
                )

                Spacer(Modifier.height(15.dp))


                //HOME

                NavigationDrawerItem(
                    label = { Text("Home") },
                    selected = false,
                    onClick = {
                        scope.launch {
                            drawerState.close()
                            drawerNavController.navigate(Routes.HOME){
                                launchSingleTop = true
                                popUpTo(drawerNavController.graph.startDestinationId)
                            }
                        }
                    },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                )


                // SETTINGS

                NavigationDrawerItem(
                    label = { Text("Settings") },
                    selected = false,
                    onClick = {
                        scope.launch {
                            drawerState.close()
                            drawerNavController.navigate(Routes.SETTINGS){
                                launchSingleTop = true
                                popUpTo(drawerNavController.graph.startDestinationId)
                            }
                        }
                    },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                )



                // ABOUT

                NavigationDrawerItem(
                    label = { Text("About") },
                    selected = false,
                    onClick = {
                        scope.launch {
                            drawerState.close()
                            drawerNavController.navigate(Routes.ABOUT){
                                launchSingleTop = true
                                popUpTo(drawerNavController.graph.startDestinationId)
                            }
                        }
                    },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                )

            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
//                        Text("AI Reader")
                            },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                scope.launch {
                                    drawerState.open()
                                }
                            }
                        ) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu")
                        }
                    }
                )
            }
        ) { innerPadding ->
            AppNavGraph(innerPadding = innerPadding,
                drawerNavController =  drawerNavController,
                parentNavController = parentNavController)
        }
    }
}