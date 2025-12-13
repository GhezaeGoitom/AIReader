package com.gg.aireader

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.gg.aireader.ui.screens.drawer.MainApp
import com.gg.aireader.ui.theme.AIreaderTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AIreaderTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                 Box(modifier = Modifier
                     .fillMaxSize()
                     .padding(innerPadding)) {
                     MainApp()
                 }
                }
            }
        }
    }
}