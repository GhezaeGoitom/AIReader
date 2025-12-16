import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import com.gg.aireader.ui.screens.common.PdfPage
import com.gg.aireader.ui.screens.reader.ReaderViewModel
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged

@Composable
fun PdfViewerScreen(
    pdfUri: String?,
    viewModel: ReaderViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val pages by viewModel.renderedPages.collectAsState()
    val isRendered by viewModel.isRendered.collectAsState()
    val isPlaying by viewModel.isPlaying.collectAsState()
    val isMoodAvailable by viewModel.isMoodAvailable.collectAsState()
    val mood by viewModel.mood.collectAsState()

    val listState = rememberLazyListState()

    LaunchedEffect(pdfUri) {
        pdfUri?.let {
            val uri = it.toUri()
            viewModel.loadPdf(uri, context)
        }
    }

    // text extraction + mood detection
    LaunchedEffect(isRendered) {
        if (isRendered) {
            viewModel.extractTextFromPage(10)
        }
    }

    // Music
    LaunchedEffect(isMoodAvailable) {
        if (isMoodAvailable) {
            val music = viewModel.getMusicUrlByMood(mood)
            if (music.isNotEmpty()) viewModel.play(music[0].audio)
        }
    }

    // Page scroll detection
    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemIndex }
            .distinctUntilChanged()
            .debounce(600)
            .collect { page ->
                // TODO: trigger OCR here if needed
            }
    }

    Column(Modifier.fillMaxSize()) {

        LazyColumn(
            modifier = Modifier.weight(1f),
            state = listState
        ) {
            itemsIndexed(pages) { index, bmp ->
                PdfPage(page = bmp)
            }
        }

//        Button(onClick = {
//            if (isPlaying) viewModel.pause() else viewModel.resume()
//            viewModel.togglePlay()
//        }) {
//            Text(if (isPlaying) "Pause" else "Play")
//        }
    }
}