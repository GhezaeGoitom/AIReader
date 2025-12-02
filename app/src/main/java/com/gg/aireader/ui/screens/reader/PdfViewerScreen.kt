package com.gg.aireader.ui.screens.reader

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.gg.aireader.utils.PdfBitmapConverter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged


@Composable
fun PdfViewerScreen(modifier: Modifier = Modifier, viewModel: ReaderViewModel = hiltViewModel()) {


    val pages by viewModel.renderedPages.collectAsState()
    val isRendered by viewModel.isRendered.collectAsState()
    val isPlaying by viewModel.isPlaying.collectAsState()
    val isMoodAvailable by viewModel.isMoodAvailable.collectAsState()
    val mood by viewModel.mood.collectAsState()
    val context = LocalContext.current
    val listState = rememberLazyListState()

    // store rendered page for later catch
    val processedPages = remember { mutableSetOf<Int>() }


    val choosePdfLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()) { uri ->
         uri?.let { viewModel.loadPdf(uri, context) }
    }

    LaunchedEffect(isRendered) {
        if (isRendered){
            viewModel.extractTextFromPage(10)
        }
    }

    LaunchedEffect(isMoodAvailable) {
        if (isMoodAvailable){
            val musicList = viewModel.getMusicUrlByMood(mood)
            viewModel.play(musicList[0].audio)
        }
    }

    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemIndex }
            .distinctUntilChanged()
            .debounce(600)
            .collect { page ->
                // skip if already processed
//                if (!processedPages.contains(page)) {
//                    processedPages.add(page)
//
////                    processPage(page)
//                }
                Log.e("gg_page",page.toString())
            }


    }

    if (pages.isEmpty()){
        LaunchedEffect(Unit) {
        choosePdfLauncher.launch("application/pdf")}
    }else{
        Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            LazyColumn(modifier = Modifier.weight(1f).fillMaxSize(), state = listState) {
                itemsIndexed(pages){ index, page ->
                    PdfPage(page = page)
                }
            }
            Button(onClick = {
                if(!isPlaying){
                    viewModel.resume()
                }else{
                    viewModel.pause()
                }
                viewModel.togglePlay()
            }) { }
        }
    }
}


@Composable
fun PdfPage(page: Bitmap,
            modifier: Modifier = Modifier
){
    AsyncImage(model = page, contentDescription = null, modifier = Modifier.fillMaxSize().aspectRatio(page.width.toFloat() / page.height.toFloat()))
}