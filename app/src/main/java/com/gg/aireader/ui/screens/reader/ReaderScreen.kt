package com.gg.aireader.ui.screens.reader

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.github.barteksc.pdfviewer.PDFView
import java.io.File


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReaderScreen(navController: NavController, pdfViewModel: ReaderViewModel = viewModel()){

  val context = LocalContext.current




    Scaffold(topBar = {TopAppBar(title = { Text("Reader") })} ) {

            AndroidView(modifier = Modifier.fillMaxSize(), factory = { ctx ->
                PDFView(ctx, null).apply {
                    fromAsset("motg.pdf")
                        .enableSwipe(true)
                        .load()
                }
            })
        }
//            Box(
//                modifier = Modifier
//                    .fillMaxSize()
//                    , contentAlignment = Alignment.Center
//            ) {
//                Text("coming soon...")
//            }
//        }
//
}