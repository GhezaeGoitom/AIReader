package com.gg.aireader.ui.screens.reader

import android.R
import android.content.Context
import android.graphics.Bitmap
import android.media.browse.MediaBrowser
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.gg.aireader.data.repo.PdfRepo
import com.gg.aireader.utils.PdfBitmapConverter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReaderViewModel @Inject constructor(private val pdfRepo: PdfRepo, private val player: ExoPlayer): ViewModel() {
    private val _pageText = MutableStateFlow("")
    val pageText = _pageText.asStateFlow()

    private val _renderedPages = MutableStateFlow<List<Bitmap>>(emptyList())
    val renderedPages = _renderedPages.asStateFlow()

    private val _isRendered = MutableStateFlow(false)
    val isRendered = _isRendered.asStateFlow()

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying = _isPlaying.asStateFlow()

    // exoplayer
    fun play(uri: String){
        val mediaItem = MediaItem.fromUri(uri)
        player.setMediaItem(mediaItem)
        player.prepare()
        player.play()
    }


    fun pause() = player.pause()
    fun resume() = player.play()
    fun stop() = player.stop()

    fun togglePlay(){
        _isPlaying.value = !_isPlaying.value
    }

    fun setPlaying(value: Boolean){

    }


    fun loadPdf(uri: Uri, context: Context){
        viewModelScope.launch {
            val pdfConverter = PdfBitmapConverter(context)
            val pages = pdfConverter.pdfToBitmap(uri)
            _renderedPages.value = pages
            _isRendered.value = true
        }
    }

    fun extractTextFromPage(pageIndex: Int, context: Context){
        viewModelScope.launch(Dispatchers.Default) {
            try {
                val page = _renderedPages.value[pageIndex]
                val text = pdfRepo.extractTextFromBitmap(page)
                Log.d("GG_RENDER", "Bitmap size: ${page.width}x${page.height}")
                Log.d("GG_RENDER2", "Bitmap text: ${text.substring(0,30)}")
                _pageText.value = text.substring(5, 10)
                val result = pdfRepo.analyzeMood(text)
                Log.d("gg_render_2220", result.toString())
            }catch (e: Exception){
                Log.e("gg err", e.message.toString())
                _pageText.value = "error ${e.message}"
            }
        }
    }


    override fun onCleared() {
        super.onCleared()
        player.release()
    }


}